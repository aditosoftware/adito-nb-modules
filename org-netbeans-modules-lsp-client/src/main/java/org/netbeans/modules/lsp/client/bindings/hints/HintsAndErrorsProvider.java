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
package org.netbeans.modules.lsp.client.bindings.hints;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.text.Document;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.lsp.*;
import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.netbeans.modules.lsp.client.LSPBindings;
import org.netbeans.modules.lsp.client.LSPWorkingPool;
import org.netbeans.modules.lsp.client.Utils;
import org.netbeans.spi.editor.hints.ChangeInfo;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.ErrorDescriptionFactory;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.editor.hints.LazyFixList;
import org.netbeans.spi.editor.hints.Severity;
import org.openide.filesystems.FileObject;
import org.openide.util.Lookup;

/**
 *
 * @author ranSprd
 */
public class HintsAndErrorsProvider {

    private static final Map<DiagnosticSeverity, Severity> severityMap = new EnumMap<>(DiagnosticSeverity.class);
    private static final Collection<? extends ILSPHintsFilter> _HINTS_FILTERS = Lookup.getDefault().lookupAll(ILSPHintsFilter.class);
    private static final Collection<? extends ILSPFixesFilter> _FIXES_FILTERS = Lookup.getDefault().lookupAll(ILSPFixesFilter.class);

    static {
        severityMap.put(DiagnosticSeverity.Error, Severity.ERROR);
        severityMap.put(DiagnosticSeverity.Hint, Severity.HINT);
        severityMap.put(DiagnosticSeverity.Information, Severity.HINT);
        severityMap.put(DiagnosticSeverity.Warning, Severity.WARNING);
    }

    private final LSPBindings bindings;
    private final Map<String, ListMerger> cachedLists = new HashMap<>();

    public HintsAndErrorsProvider(LSPBindings bindings) {
        this.bindings = bindings;
    }

    /**
     * Build a list of ErrorDescription from given LSP struct.
     *
     * @param diagnostics data from LSP
     * @param file
     * @param doc
     * @return
     */
    public List<ErrorDescription> transform(PublishDiagnosticsParams diagnostics, FileObject file, Document doc) {
        List<ErrorDescription> errorDescriptions = get(diagnostics).combinedStream(diagnostics.getDiagnostics())
//        List<ErrorDescription> errorDescriptions = diagnostics.getDiagnostics().stream()
                .map(d -> createHintsAndErrors(doc, file, diagnostics.getUri(), d))
            .filter(pDesc -> _HINTS_FILTERS.stream()
                .filter(pFilter -> pFilter.canFilter(file))
                .allMatch(pFilter -> pFilter.filter(file, pDesc.getId(), pDesc.getDescription(), pDesc.getSeverity().name(), pDesc.getRange())))
                .collect(Collectors.toList());
        return errorDescriptions;
    }

    private ErrorDescription createHintsAndErrors(Document doc, FileObject file, String uri, org.eclipse.lsp4j.Diagnostic d) {
        LazyFixList fixList = new DiagnosticFixList(uri, d, file);
        Either<String, Integer> code = d.getCode();
        String id = code.isLeft() ? code.getLeft() : code.getRight().toString();
        return ErrorDescriptionFactory.createErrorDescription(id, severityMap.get(d.getSeverity()), d.getMessage(), null, fixList, file, Utils.getOffset(doc, d.getRange().getStart()), Utils.getOffset(doc, d.getRange().getEnd()));
    }

    /**
     *  Server can send several lists (with different content) for the same file version, for that reason
     *  previous send lists are cached and combined with later arrived lists.
     */
    private ListMerger get(PublishDiagnosticsParams diagnostics) {
        int diagnosticVersion = (diagnostics.getVersion() == null)?-1:diagnostics.getVersion();

        ListMerger listMerger = cachedLists.get(diagnostics.getUri());
        if (listMerger == null || diagnosticVersion == 0) {
            // sometimes version 0 contains broken infos, for that reason we use only the last version 0
            listMerger = new ListMerger(diagnosticVersion);
            cachedLists.put(diagnostics.getUri(), listMerger);
        } else if (listMerger.version <= diagnosticVersion) {
            // incoming data for a newer version, throw the old stuff away
            listMerger = new ListMerger(diagnosticVersion);
            cachedLists.put(diagnostics.getUri(), listMerger);
        } else if (listMerger.version > diagnosticVersion) {
            // uuupppps, that is crazy
        }

        return listMerger;
    }

    /** merge list of the same version */
    private class ListMerger {

        private final int version;
        private final Set<org.eclipse.lsp4j.Diagnostic> allDiagnosticItems = new HashSet<>();

        public ListMerger(int version) {
            this.version = version;
        }

        public int getVersion() {
            return version;
        }

        public Stream<Diagnostic> combinedStream(List<Diagnostic> list) {
            allDiagnosticItems.addAll(list);
            return allDiagnosticItems.stream();
        }

    }

    private final class DiagnosticFixList implements LazyFixList {

        private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
        private final String fileUri;
        private final FileObject fileObject;
        private final Diagnostic diagnostic;
        private List<Fix> fixes;
        private boolean computing;
        private boolean computed;

        public DiagnosticFixList(String fileUri, Diagnostic diagnostic, FileObject fileObject) {
            this.fileUri = fileUri;
            this.diagnostic = diagnostic;
            this.fileObject = fileObject;
        }

        @Override
        public void addPropertyChangeListener(PropertyChangeListener l) {
            pcs.addPropertyChangeListener(l);
        }

        @Override
        public void removePropertyChangeListener(PropertyChangeListener l) {
            pcs.removePropertyChangeListener(l);
        }

        @Override
        public boolean probablyContainsFixes() {
            return true;
        }

        @Override
        public synchronized List<Fix> getFixes() {
            if (!computing && !computed) {
                computing = true;
                LSPWorkingPool.runOnBackground(() -> {
                    try {
                        List<Either<Command, CodeAction>> commands =
                                bindings.getTextDocumentService().codeAction(new CodeActionParams(new TextDocumentIdentifier(fileUri),
                                        diagnostic.getRange(),
                                        new CodeActionContext(Collections.singletonList(diagnostic)))).get();
                        List<Fix> newFixes = Stream.concat(commands.stream()
                                                               .map(cmd -> new CommandBasedFix(cmd))
                                                               .filter(pFix -> _FIXES_FILTERS.stream()
                                                                   .filter(pFilter -> pFilter.canFilter(fileObject))
                                                                   .allMatch(pFilter -> pFilter.filter(fileObject, pFix.getText()))),
                                                           getAdditionalFixes())
                            .collect(Collectors.toList());
                        synchronized (this) {
                            this.fixes = Collections.unmodifiableList(newFixes);
                            this.computed = true;
                            this.computing = false;
                        }
                        pcs.firePropertyChange(PROP_COMPUTED, null, null);
                        pcs.firePropertyChange(PROP_FIXES, null, null);
                    } catch (InterruptedException | ExecutionException ex) {
//                        Exceptions.printStackTrace(ex);
                    }
                });
            }
            return fixes;
        }

        @Override
        public synchronized boolean isComputed() {
            return computed;
        }

// additional ADITO Lines
        private Stream<Fix> getAdditionalFixes() {
            return Lookup.getDefault().lookupAll(ILSPFixProvider.class)
                                    .stream()
                .flatMap(pProvider -> pProvider.provideFixes(fileObject, diagnostic.getCode().getRight(), diagnostic.getMessage(),
                                                             diagnostic.getSeverity().toString(), new RangeImpl(diagnostic.getRange())).stream());
        }

        /**
         * Translate the Range given from the diagnostics to the Range needed for the interface. Interface has its own Range due to dependency  reasons
         */
        private class RangeImpl implements ILSPFixProvider.Range {

            private final int startLine;
            private final int startCharacter;
            private final int endLine;
            private final int endCharacter;

            public RangeImpl(Range pRange)
            {
                startLine = pRange.getStart().getLine();
                startCharacter = pRange.getStart().getCharacter();
                endLine = pRange.getEnd().getLine();
                endCharacter = pRange.getEnd().getCharacter();
            }

            @Override
            public int getStartLine()
            {
                return startLine;
            }

            @Override
            public int getStartCharacter()
            {
                return startCharacter;
            }

            @Override
            public int getEndLine()
            {
                return endLine;
            }

            @Override
            public int getEndCharacter()
            {
                return endCharacter;
            }
        }

// END additional ADITO Lines

        private class CommandBasedFix implements Fix {

            private final Either<Command, CodeAction> cmd;

            public CommandBasedFix(Either<Command, CodeAction> cmd) {
                this.cmd = cmd;
            }

            @Override
            public String getText() {
                return cmd.isLeft() ? cmd.getLeft().getTitle() : cmd.getRight().getTitle();
            }

            @Override
            public ChangeInfo implement() throws Exception {
                Utils.applyCodeAction(bindings, cmd);
                return null;
            }
        }

    }

}
