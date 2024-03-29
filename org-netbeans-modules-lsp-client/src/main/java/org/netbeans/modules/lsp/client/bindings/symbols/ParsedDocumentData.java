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
package org.netbeans.modules.lsp.client.bindings.symbols;

import javax.swing.text.Document;
import org.eclipse.lsp4j.SemanticTokensWithRegistrationOptions;
import org.netbeans.modules.editor.NbEditorUtilities;
import org.netbeans.modules.lsp.client.LSPBindingFactory;
import org.netbeans.modules.lsp.client.LSPBindings;
import org.openide.filesystems.FileObject;

import java.util.logging.Logger;

/**
 *
 * @author ranSprd
 */
public class ParsedDocumentData {

    private static final Logger LOG = Logger.getLogger(ParsedDocumentData.class.getName());

    private final Document document;
    private final String fileUri;

    public ParsedDocumentData(Document document, String fileUri) {
        this.document = document;
        this.fileUri = fileUri;
    }
    
    /** close all open workers */
    public void close() {
        
    }
    
    public ParsedDocumentData refresh() {
        FileObject file = NbEditorUtilities.getFileObject(document);
        if (file == null) {
            return this;
        }
        LOG.info(() -> "[LSP]: refresh triggered for file " + file);
        LSPBindings server = LSPBindingFactory.getBindingForFile(file);
        if (server == null) {
            return this;
        }
        
//        SemanticTokensWithRegistrationOptions o = server.getInitResult().getCapabilities().getSemanticTokensProvider();
//        System.out.println("sematic tokens\n" +o);
        
        return this;
    }
    
    
}
