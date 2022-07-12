/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.modules.lsp.client.bindings;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.*;
import javax.swing.SwingUtilities;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.undo.UndoableEdit;

import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentItem;
import org.eclipse.lsp4j.TextDocumentSyncKind;
import org.eclipse.lsp4j.TextDocumentSyncOptions;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.netbeans.api.editor.EditorRegistry;
import org.netbeans.editor.BaseDocumentEvent;
import org.netbeans.lib.editor.util.swing.DocumentUtilities;
import org.netbeans.modules.editor.*;
import org.netbeans.modules.lsp.client.LSPBindingFactory;
import org.netbeans.modules.lsp.client.LSPBindings;
import org.netbeans.modules.lsp.client.LSPWorkingPool;
import org.netbeans.modules.lsp.client.Utils;
import org.netbeans.modules.lsp.client.bindings.symbols.DocumentStructureProvider;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.modules.OnStart;
import org.openide.text.*;
import org.openide.util.Exceptions;
import org.openide.util.RequestProcessor;

/**
 *
 * @author lahvac
 */
public class TextDocumentSyncServerCapabilityHandler {

    private final RequestProcessor WORKER = new RequestProcessor(TextDocumentSyncServerCapabilityHandler.class.getName(), 1, false, false);
    private final Set<JTextComponent> lastOpened = Collections.newSetFromMap(new IdentityHashMap<>());

    private void handleChange() {
        assert SwingUtilities.isEventDispatchThread();
        Set<JTextComponent> currentOpened = Collections.newSetFromMap(new IdentityHashMap<>());
        currentOpened.addAll(EditorRegistry.componentList());
        Set<JTextComponent> newOpened = Collections.newSetFromMap(new IdentityHashMap<>());
        newOpened.addAll(currentOpened);
        newOpened.removeAll(lastOpened);
        Set<JTextComponent> newClosed = Collections.newSetFromMap(new IdentityHashMap<>());
        newClosed.addAll(lastOpened);
        newClosed.removeAll(currentOpened);
        lastOpened.clear();
        lastOpened.addAll(currentOpened);

        for (JTextComponent opened : newOpened) {
            editorOpened(opened);
        }

        for (JTextComponent closed : newClosed) {
            editorClosed(closed);
        }
    }

    private void ensureOpenedInServer(JTextComponent opened) {
        FileObject file = NbEditorUtilities.getFileObject(opened.getDocument());

        if (file == null)
            return; //ignore

        Document doc = opened.getDocument();
        ensureDidOpenSent(doc);
        registerBackgroundTasks(opened);
    }

    public static void refreshOpenedFilesInServers() {
        SwingUtilities.invokeLater(() -> {
            assert SwingUtilities.isEventDispatchThread();
            for (JTextComponent c : EditorRegistry.componentList()) {
                h.ensureOpenedInServer(c);
            }
        });
    }

    private static final TextDocumentSyncServerCapabilityHandler h = new TextDocumentSyncServerCapabilityHandler();
    @OnStart
    public static class Init implements Runnable {

        @Override
        public void run() {
            EditorRegistry.addPropertyChangeListener(evt -> h.handleChange());
            SwingUtilities.invokeLater(() -> h.handleChange());
        }

    }

    private final Map<Document, Integer> openDocument2PanesCount = new HashMap<>();

    private void documentOpened(Document doc) {
        FileObject file = NbEditorUtilities.getFileObject(doc);

        if (file == null) {
            return; //ignore
        }

        openDocument2PanesCount.computeIfAbsent(doc, d -> {
            doc.putProperty(TextDocumentSyncServerCapabilityHandler.class, true);
            ensureDidOpenSent(doc);
            doc.addDocumentListener(new DocumentListener() { //XXX: listener
                int version; //XXX: proper versioning!
                @Override
                public void insertUpdate(DocumentEvent e) {
                    try {
                        fireEvent(e.getOffset(), e.getDocument().getText(e.getOffset(), e.getLength()), "");
                    } catch (BadLocationException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    fireEvent(e.getOffset(), "", ((BaseDocumentEvent) e).getText());
                }
                private void fireEvent(int start, String newText, String oldText) {
                    try {
                        Position startPos = Utils.createPosition(doc, start);
                        Position endPos = Utils.computeEndPositionForRemovedText(startPos, oldText);
                        TextDocumentContentChangeEvent[] event = new TextDocumentContentChangeEvent[1];
                        event[0] = new TextDocumentContentChangeEvent(new Range(startPos,
                                                                             endPos),
                                                                   oldText.length(),
                                                                   newText);

                        boolean typingModification = DocumentUtilities.isTypingModification(doc);
                        long documentVersion = DocumentUtilities.getDocumentVersion(doc);

                        WORKER.post(() -> {
                            LSPBindings server = LSPBindingFactory.getBindingForFile(file);

                            if (server == null)
                                return ; //ignore

                            TextDocumentSyncKind syncKind = TextDocumentSyncKind.None;
                            Either<TextDocumentSyncKind, TextDocumentSyncOptions> sync = server.getInitResult().getCapabilities().getTextDocumentSync();
                            if (sync != null) {
                                if (sync.isLeft()) {
                                    syncKind = sync.getLeft();
                                } else {
                                    TextDocumentSyncKind change = sync.getRight().getChange();
                                    if (change != null)
                                        syncKind = change;
                                }
                            }
                            switch (syncKind) {
                                case None:
                                    return ;
                                case Full:
                                    doc.render(() -> {
                                        try {
                                            event[0] = new TextDocumentContentChangeEvent(doc.getText(0, doc.getLength()));
                                        } catch (BadLocationException ex) {
                                            Exceptions.printStackTrace(ex);
                                            event[0] = new TextDocumentContentChangeEvent("");
                                        }
                                    });
                                    break;
                                case Incremental:
                                    //event already filled
                                    break;
                            }

                            VersionedTextDocumentIdentifier di = new VersionedTextDocumentIdentifier(org.netbeans.modules.lsp.client.Utils.toURI(file), ++version);
                            DidChangeTextDocumentParams params = new DidChangeTextDocumentParams(di, Arrays.asList(event));

                            server.getTextDocumentService().didChange(params);

                            if (typingModification && oldText.isEmpty() && event.length == 1) {
                                if (newText.equals("}") || newText.equals("\n")) {
                                    List<TextEdit> edits = new ArrayList<>();
                                    doc.render(() -> {
                                        if (documentVersion != DocumentUtilities.getDocumentVersion(doc))
                                            return ;
                                        edits.addAll(Utils.computeDefaultOnTypeIndent(doc, start, startPos, newText));
                                    });
                                    NbDocument.runAtomic((StyledDocument) doc, () -> {
                                        if (documentVersion == DocumentUtilities.getDocumentVersion(doc)) {
                                            Utils.applyEditsNoLock(doc, edits);
                                        }
                                    });
                                }
                            }
                            LSPWorkingPool.scheduleBackgroundTasks(file);
                        });
                    } catch (BadLocationException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {}
            });
            return 0;
        });
    }

    private synchronized void editorOpened(JTextComponent c) {
        Document doc = c.getDocument();
        FileObject file = NbEditorUtilities.getFileObject(c.getDocument());

        if (file == null)
            return; //ignore

        documentOpened(doc);
        DocumentStructureProvider.INSTANCE.register(doc, file);
        registerBackgroundTasks(c);
        openDocument2PanesCount.compute(doc, (d, count) -> count + 1);
    }

    private synchronized void editorClosed(JTextComponent c) {
        Document doc = c.getDocument();
        Integer count = openDocument2PanesCount.getOrDefault(doc, -1);
        if (count > 0) {
            openDocument2PanesCount.put(doc, --count);
        }
        if (count == 0) {
            //TODO modified!
            WORKER.post(() -> {
                FileObject file = NbEditorUtilities.getFileObject(doc);

                if (file == null)
                    return; //ignore

                LSPBindings server = LSPBindingFactory.getBindingForFile(file);

                if (server == null)
                    return ; //ignore

                DocumentStructureProvider.INSTANCE.unregister(file);

                TextDocumentIdentifier di = new TextDocumentIdentifier();
                di.setUri(Utils.toURI(file));
                DidCloseTextDocumentParams params = new DidCloseTextDocumentParams(di);

                server.getTextDocumentService().didClose(params);
                server.getOpenedFiles().remove(file);
            });
            openDocument2PanesCount.remove(doc);
        }
    }

    private void ensureDidOpenSent(Document doc) {
        WORKER.post(() -> {
            FileObject file = NbEditorUtilities.getFileObject(doc);

            if (file == null)
                return; //ignore

            LSPBindings server = LSPBindingFactory.getBindingForFile(file);

            if (server == null)
                return ; //ignore

            if (!server.getOpenedFiles().add(file)) {
                //already opened:
                return ;
            }

            doc.putProperty(HyperlinkProviderImpl.class, true);

            String uri = Utils.toURI(file);
            String[] text = new String[1];

            doc.render(() -> {
                try {
                    text[0] = doc.getText(0, doc.getLength());
                } catch (BadLocationException ex) {
                    Exceptions.printStackTrace(ex);
                    text[0] = "";
                }
            });

            // languageId sollte csharp sein
            TextDocumentItem textDocumentItem = new TextDocumentItem(uri,
                                                                     FileUtil.getMIMEType(file),
                                                                     0,
                                                                     text[0]);

            server.getTextDocumentService().didOpen(new DidOpenTextDocumentParams(textDocumentItem));
            LSPWorkingPool.scheduleBackgroundTasks(file);
        });
    }

    private void registerBackgroundTasks(JTextComponent textComponent) {
        Document doc = textComponent.getDocument();
        WORKER.post(() -> {
            FileObject file = NbEditorUtilities.getFileObject(doc);

            if (file == null)
                return; //ignore

            LSPBindings server = LSPBindingFactory.getBindingForFile(file);

            if (server == null)
                return ; //ignore

            SwingUtilities.invokeLater(() -> {
                if (textComponent.getClientProperty(MarkOccurrences.class) == null) {
                    MarkOccurrences mo = new MarkOccurrences(textComponent);
                    LSPWorkingPool.addBackgroundTask(file, mo);
                    textComponent.putClientProperty(MarkOccurrences.class, mo);
                }
                if (textComponent.getClientProperty(BreadcrumbsImpl.class) == null) {
                    BreadcrumbsImpl bi = new BreadcrumbsImpl(textComponent);
                    LSPWorkingPool.addBackgroundTask(file, bi);
                    textComponent.putClientProperty(BreadcrumbsImpl.class, bi);
                }
                if (textComponent.getClientProperty(ProgressHandleTask.class) == null) {
                    ProgressHandleTask handle = new ProgressHandleTask(file);
                    LSPWorkingPool.addBackgroundTask(file, handle);
                    textComponent.putClientProperty(ProgressHandleTask.class, handle);
                }

                // Custom Property, avoids that the listener is added twice
                if (textComponent.getClientProperty(_Object.class) == null) {
                    textComponent.addKeyListener(new _KeyListener());
                    textComponent.putClientProperty(_Object.class, new _Object());
                }
//                if (textComponent.getClientProperty(HoverImpl.class) == null) {
//                    HoverImpl hover = new HoverImpl(textComponent);
//                    LSPWorkingPool.addBackgroundTask(file, hover);
//                    textComponent.putClientProperty(HoverImpl.class, hover);
//                }
//                if (textComponent.getClientProperty(SignatureHelpImpl.class) == null) {
//                    SignatureHelpImpl signatureHelper = new SignatureHelpImpl(textComponent);
//                    LSPWorkingPool.addBackgroundTask(file, signatureHelper);
//                    textComponent.putClientProperty(SignatureHelpImpl.class, signatureHelper);
//                }
//                CodeLensImpl cl = new CodeLensImpl();
//                LSPWorkingPool.addBackgroundTask(file, cl);
            });
        });
    }

    /**
     * KeyListener: If a text is selected and special character is typed, the corresponding "closing" character should be inserted around the
     * selected text. Example: Opening and closing bracket around a selected text
     */
    private static class _KeyListener extends KeyAdapter
    {
        private static final Map<Character, Character> _MAPPING = new HashMap<>();

        static {
            _MAPPING.put('"', '"');
            _MAPPING.put('\'', '\'');
            _MAPPING.put('(', ')');
            _MAPPING.put('[', ']');
            _MAPPING.put('{', '}');
        }
        @Override
        public void keyPressed(KeyEvent e)
        {
            Component component = e.getComponent();
            if(!(component instanceof JTextComponent))
                return;

            JTextComponent textComponent = (JTextComponent) component;
            int selectionStart = textComponent.getSelectionStart();
            int selectionEnd = textComponent.getSelectionEnd();

            // if no selection, return
            if(selectionEnd - selectionStart <= 0)
                return;

            // check, if one of the relevant characters was typed
            char keyChar = e.getKeyChar();
            if(!_MAPPING.containsKey(keyChar))
                return;

            try
            {
                //noinspection unchecked
                List<javax.swing.text.Position> positions = (List<javax.swing.text.Position>) textComponent.getClientProperty("rectangular-selection-regions");
                if (positions == null || positions.size() == 2)
                    doModification(textComponent, selectionStart, selectionEnd, keyChar, 1);
                else
                {
                    for (int i = 0; i < positions.size(); i = i + 2)
                    {
                        doModification(textComponent, positions.get(i).getOffset(), positions.get(i + 1).getOffset(), keyChar, 1 + i);
                    }
                }
            }
            catch (Throwable t)
            {
                Logger.getLogger(TextDocumentSyncServerCapabilityHandler.class.getName()).log(Level.WARNING, "Could not insert into document.", t);
            }
            e.consume();
        }

        private void doModification(JTextComponent pTextComponent, int pSelectionStart, int pSelectionEnd, char pChar, int pOffset)
        {
            try
            {
                Document document = pTextComponent.getDocument();
                String text = document.getText(pSelectionStart, pSelectionEnd - pSelectionStart);
                int caretPosition = pTextComponent.getCaretPosition();
                sendUndoableEdit(document, CloneableEditorSupport.BEGIN_COMMIT_GROUP);

                SwingUtilities.invokeLater(() -> {
                    try
                    {
                        // insert the text and the "closing" character
                        // the "opening" character is handled by the component. Unfortunately, the selected text gets overridden => selected text must
                        // be inserted again
                        document.insertString(pSelectionStart + pOffset, text + _MAPPING.get(pChar), null);

                        // set initial caret position again
                        pTextComponent.setCaretPosition(caretPosition + pOffset);

                        // set initial selection again
                        pTextComponent.setSelectionStart(pSelectionStart + pOffset);
                        pTextComponent.setSelectionEnd(pSelectionEnd + pOffset);
                        sendUndoableEdit(document, CloneableEditorSupport.END_COMMIT_GROUP);
                    }
                    catch (Exception ex)
                    {
                        Logger.getLogger(TextDocumentSyncServerCapabilityHandler.class.getName()).log(Level.WARNING, "Could not insert into document.", ex);
                    }

                });
            }
            catch (Exception ex)
            {
                Logger.getLogger(TextDocumentSyncServerCapabilityHandler.class.getName()).log(Level.WARNING, "Could not insert into document.", ex);
            }
        }

        static void sendUndoableEdit(Document d, UndoableEdit ue) {
            if(d instanceof AbstractDocument) {
                UndoableEditListener[] uels = ((AbstractDocument)d).getUndoableEditListeners();
                UndoableEditEvent ev = new UndoableEditEvent(d, ue);
                for(UndoableEditListener uel : uels) {
                    uel.undoableEditHappened(ev);
                }
            }
        }
    }

    private static class _Object
    {
    }
}
