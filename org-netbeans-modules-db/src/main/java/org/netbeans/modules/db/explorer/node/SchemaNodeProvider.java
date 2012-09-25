/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010-2011 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2008 Sun Microsystems, Inc.
 */

package org.netbeans.modules.db.explorer.node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import org.netbeans.api.db.explorer.node.NodeProvider;
import org.netbeans.api.db.explorer.node.NodeProviderFactory;
import org.netbeans.modules.db.explorer.DatabaseConnection;
import org.netbeans.modules.db.metadata.model.api.Action;
import org.netbeans.modules.db.metadata.model.api.Catalog;
import org.netbeans.modules.db.metadata.model.api.Metadata;
import org.netbeans.modules.db.metadata.model.api.MetadataElementHandle;
import org.netbeans.modules.db.metadata.model.api.MetadataModel;
import org.netbeans.modules.db.metadata.model.api.MetadataModelException;
import org.netbeans.modules.db.metadata.model.api.Schema;
import org.openide.nodes.Node;
import org.openide.util.Lookup;

/**
 *
 * @author Rob Englander
 */
public class SchemaNodeProvider extends NodeProvider {

    // lazy initialization holder class idiom for static fields is used
    // for retrieving the factory
    public static NodeProviderFactory getFactory() {
        return FactoryHolder.FACTORY;
    }

    private static class FactoryHolder {
        static final NodeProviderFactory FACTORY = new NodeProviderFactory() {
            @Override
            public SchemaNodeProvider createInstance(Lookup lookup) {
                SchemaNodeProvider provider = new SchemaNodeProvider(lookup);
                return provider;
            }
        };
    }

    private final DatabaseConnection connection;
    private final MetadataElementHandle<Catalog> catalogHandle;

    private SchemaNodeProvider(Lookup lookup) {
        super(lookup, new SchemaComparator());
        connection = getLookup().lookup(DatabaseConnection.class);
        catalogHandle = getLookup().lookup(MetadataElementHandle.class);
    }

    @Override
    protected synchronized void initialize() {
        final List<Node> newList = new ArrayList<Node>();

        boolean connected = !connection.getConnector().isDisconnected();
        MetadataModel metaDataModel = connection.getMetadataModel();
        if (connected && metaDataModel != null) {
            try {
                metaDataModel.runReadAction(
                    new Action<Metadata>() {
                        @Override
                        public void run(Metadata metaData) {
                            Catalog cat = catalogHandle.resolve(metaData);
                            if (cat != null) {
                                Schema syntheticSchema = cat.getSyntheticSchema();

                                if (syntheticSchema != null) {
                                    updateNode(newList, syntheticSchema);
                                } else {
                                    Collection<Schema> schemas = cat.getSchemas();
                                    for (Schema schema : schemas) {
                                        updateNode(newList, schema);
                                    }
                                }

                                if (syntheticSchema != null) {
                                    setProxyNodes(newList);
                                } else {
                                    setNodes(newList);
                                }
                            }
                        }
                    }
                );
            } catch (MetadataModelException e) {
                NodeRegistry.handleMetadataModelException(this.getClass(), connection, e, true);
            }
        } else {
            setNodes(newList);
        }
    }

    private void updateNode(List<Node> newList, Schema schema) {
        MetadataElementHandle<Schema> schemaHandle = MetadataElementHandle.create(schema);
        Collection<Node> matches = getNodes(schemaHandle);
        if (matches.size() > 0) {
            newList.addAll(matches);
        } else {
            NodeDataLookup lookup = new NodeDataLookup();
            lookup.add(connection);
            lookup.add(schemaHandle);

            newList.add(SchemaNode.create(lookup, this));
        }
    }

    static class SchemaComparator implements Comparator<Node> {

        @Override
        public int compare(Node node1, Node node2) {
            assert node1.getDisplayName() != null : node1 + " has display name.";
            assert node2.getDisplayName() != null : node2 + " has display name.";
            return node1.getDisplayName().compareToIgnoreCase(node2.getDisplayName());
        }

    }
}
