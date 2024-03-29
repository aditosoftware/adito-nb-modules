/*
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

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.TextUI;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyleConstants;
import org.eclipse.lsp4j.DocumentHighlight;
import org.eclipse.lsp4j.DocumentHighlightParams;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.ReferenceContext;
import org.eclipse.lsp4j.ReferenceParams;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.netbeans.api.editor.mimelookup.MimeLookup;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.api.editor.settings.AttributesUtilities;
import org.netbeans.api.editor.settings.FontColorSettings;
import org.netbeans.editor.BaseTextUI;
import org.netbeans.modules.editor.NbEditorUtilities;
import org.netbeans.modules.lsp.client.LSPBindingFactory;
import org.netbeans.modules.lsp.client.LSPBindings;
import org.netbeans.modules.lsp.client.LSPWorkingPool;
import org.netbeans.modules.lsp.client.LSPWorkingPool.BackgroundTask;
import org.netbeans.modules.lsp.client.Utils;
import org.netbeans.spi.editor.highlighting.HighlightsLayer;
import org.netbeans.spi.editor.highlighting.HighlightsLayerFactory;
import org.netbeans.spi.editor.highlighting.ZOrder;
import org.netbeans.spi.editor.highlighting.support.OffsetsBag;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.util.RequestProcessor;

/**
 *
 * @author lahvac
 */
public class MarkOccurrences implements BackgroundTask, CaretListener, PropertyChangeListener {

    public static final String KEY_VIRTUAL_TEXT_PREPEND = "virtual-text-prepend"; //NOI18N

    private static final RequestProcessor WORKER = new RequestProcessor(MarkOccurrences.class.getName(), 1, false, false);
    private final JTextComponent component;
    private final BaseTextUI baseUI;
    private Document doc;
    private int caretPos;

    private List<? extends Location> last;

    public MarkOccurrences(JTextComponent component) {
        this.component = component;
        TextUI ui = component.getUI();
        if (ui instanceof BaseTextUI) {
            baseUI = (BaseTextUI)ui;
        } else {
            baseUI = null;
        }

        try {
            doc = component.getDocument();
            caretPos = component.getCaretPosition();
            component.addCaretListener(this);
            component.addPropertyChangeListener(this);

        } catch (Exception e) {
        }
    }

    @Override
    public void run(LSPBindings bindings, FileObject file) {
        Document document;
        int latestCaretPos;
        synchronized (this) {
            document = this.doc;
            latestCaretPos = this.caretPos;
        }
        getHighlightsBag(document).setHighlights(computeHighlights(document, latestCaretPos));

        // this is experimental and should test the 'prepend' text feature
        if (last != null) {
            OffsetsBag preTextBag = new OffsetsBag(document, false);

            for(Location location : last) {
                    int s = Utils.getOffset(document, location.getRange().getStart());
                    preTextBag.addHighlight(s, s+1,
                            AttributesUtilities.createImmutable(
                                    KEY_VIRTUAL_TEXT_PREPEND, "foo:"
//                                    StyleConstants.Foreground, Color.red,
//                                    StyleConstants.FontFamily, "arial"
                                    ));
            }
            getPreTextBag(document).setHighlights(preTextBag);
        }




    }

    private OffsetsBag computeHighlights(Document doc, int caretPos) {
        AttributeSet attr = getColoring(doc);
        OffsetsBag result = new OffsetsBag(doc);
        FileObject file = NbEditorUtilities.getFileObject(doc);
        if (file == null) {
            return result;
        }
        LSPBindings server = LSPBindingFactory.getBindingForFile(file);
        if (server == null) {
            return result;
        }
        String uri = Utils.toURI(file);

        boolean hasDocumentHighlight = server.getInitResult().getCapabilities().hasDocumentHighlightSupport();
        if (hasDocumentHighlight) {
            addHighlightings(server, result, attr, uri);
        } else {
            // fallback - if highlighting is not supported...
            boolean hasReferences = server.getInitResult().getCapabilities().hasReferenceSupport();
            if (hasReferences) {
                highlightingBasedOnReferences(server, result, attr, uri);
            }
        }
        return result;
    }

    private void addHighlightings(LSPBindings server, OffsetsBag result, AttributeSet attr, String fileUri) {
        try {
            DocumentHighlightParams params = new DocumentHighlightParams(new TextDocumentIdentifier(fileUri),
                                                                  Utils.createPosition(doc, caretPos));
            List<? extends DocumentHighlight> highlights = server.getTextDocumentService()
                                                                    .documentHighlight( params)
                                                                    .get();
            if (highlights != null) {
                for (DocumentHighlight h : highlights) {
                    result.addHighlight(Utils.getOffset(doc, h.getRange().getStart()),
                                          Utils.getOffset(doc, h.getRange().getEnd()),
                                         attr);
                }
            }
        } catch (BadLocationException | InterruptedException | ExecutionException ex) {
        }
    }

    private void highlightingBasedOnReferences(LSPBindings server, OffsetsBag result, AttributeSet attr, String fileUri) {
        try {
            ReferenceParams params = new ReferenceParams( new TextDocumentIdentifier(fileUri),
                                                          Utils.createPosition(doc, caretPos),
                                                          new ReferenceContext(true));
            List<? extends Location> references = server.getTextDocumentService()
                                                            .references( params)
                                                            .get();
            last = references;
            for(Location location : references) {
                if (fileUri.equals(location.getUri())) {
                    result.addHighlight(Utils.getOffset(doc, location.getRange().getStart()),
                                          Utils.getOffset(doc, location.getRange().getEnd()),
                                         attr);
                }
            }
        } catch (Exception ex) {
        }
    }

    private AttributeSet getColoring(Document doc) {
        FontColorSettings fcs = MimeLookup.getLookup(NbEditorUtilities.getMimeType(doc)).lookup(FontColorSettings.class);

        if (fcs == null) {
            //in tests:
            return AttributesUtilities.createImmutable();
        }

        assert fcs != null;

        return fcs.getTokenFontColors("mark-occurrences");
    }

    @Override
    public synchronized void caretUpdate(CaretEvent e) {
        caretPos = e.getDot();
        WORKER.post(() -> {
            FileObject file = NbEditorUtilities.getFileObject(doc);

            if (file != null) {
                LSPWorkingPool.rescheduleBackgroundTask(file, this);
            }
        });
    }

    @Override
    public synchronized void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == null || "document".equals(evt.getPropertyName())) {
            doc = component.getDocument();
        }
    }

    static OffsetsBag getHighlightsBag(Document doc) {
        OffsetsBag bag = (OffsetsBag) doc.getProperty(MarkOccurrences.class.getName());

        if (bag == null) {
//            doc.putProperty(MarkOccurrences.class, bag = new OffsetsBag(doc, false));
            doc.putProperty(MarkOccurrences.class.getName(), bag = new OffsetsBag(doc, true));

            Object stream = doc.getProperty(Document.StreamDescriptionProperty);
            final OffsetsBag bagFin = bag;
            DocumentListener l = new DocumentListener() {
                public void insertUpdate(DocumentEvent e) {
                    bagFin.removeHighlights(e.getOffset(), e.getOffset(), false);
                }

                public void removeUpdate(DocumentEvent e) {
                    bagFin.removeHighlights(e.getOffset(), e.getOffset(), false);
                }

                public void changedUpdate(DocumentEvent e) {
                }
            };

            doc.addDocumentListener(l);

            if (stream instanceof DataObject) {
                Logger.getLogger("TIMER").log(Level.FINE, "LSP Client MarkOccurrences Highlights Bag", new Object[]{((DataObject) stream).getPrimaryFile(), bag}); //NOI18N
                Logger.getLogger("TIMER").log(Level.FINE, "LSP Client MarkOccurrences Highlights Bag Listener", new Object[]{((DataObject) stream).getPrimaryFile(), l}); //NOI18N
            }
        }

        return bag;
    }

    //    private static final Object KEY_PRE_TEXT = new Object();
    private static final String KEY_PRE_TEXT = MarkOccurrences.class.getName() + "-prepend";
    static OffsetsBag getPreTextBag(Document doc) {
        OffsetsBag bag = (OffsetsBag) doc.getProperty(KEY_PRE_TEXT);

        if (bag == null) {
            doc.putProperty(KEY_PRE_TEXT, bag = new OffsetsBag(doc, false));

//            Object stream = doc.getProperty(Document.StreamDescriptionProperty);

//            if (stream instanceof DataObject) {
//                TimesCollector.getDefault().reportReference(((DataObject) stream).getPrimaryFile(), "ImportsHighlightsBag", "[M] Imports Highlights Bag", bag);
//            }
        }

        return bag;
    }

    @MimeRegistration(mimeType="", service=HighlightsLayerFactory.class)
    public static class HighlightsLayerFactoryImpl implements HighlightsLayerFactory {

        public HighlightsLayer[] createLayers(HighlightsLayerFactory.Context context) {
            return new HighlightsLayer[]{
                //the mark occurrences layer should be "above" current row and "below" the search layers:
                HighlightsLayer.create(MarkOccurrences.class.getName()+"-2", ZOrder.SHOW_OFF_RACK.forPosition(20), true, MarkOccurrences.getHighlightsBag(context.getDocument())),
//                HighlightsLayer.create(MarkOccurrences.class.getName()+"-3", ZOrder.SHOW_OFF_RACK.forPosition(30), true, MarkOccurrences.getPreTextBag(context.getDocument())),
//            HighlightsLayer.create(MarkOccurrences.class.getName() + "-2", ZOrder.SYNTAX_RACK.forPosition(1500), false, MarkOccurrences.getHighlightsBag(context.getDocument())),
            HighlightsLayer.create(MarkOccurrences.class.getName() + "-3", ZOrder.SYNTAX_RACK.forPosition(1600), false, MarkOccurrences.getPreTextBag(context.getDocument())),

            };
        }

    }

}
