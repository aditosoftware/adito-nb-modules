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

package org.netbeans.modules.db.explorer.action;

import org.netbeans.adito.db.*;
import org.netbeans.api.db.explorer.node.BaseNode;
import org.netbeans.lib.ddl.impl.Specification;
import org.netbeans.modules.db.explorer.DatabaseConnection;
import org.netbeans.modules.db.explorer.dlg.*;
import org.openide.nodes.Node;
import org.openide.util.*;
import org.openide.util.actions.SystemAction;

import java.util.List;

/**
 *
 * @author Rob Englander
 */
public class CreateTableAction extends BaseAction {

    @Override
    protected boolean enable(Node[] activatedNodes) {
        if (activatedNodes == null || activatedNodes.length != 1) {
            return false;
        }

        boolean enabled = false;
        DatabaseConnection dbconn = activatedNodes[0].getLookup().lookup(DatabaseConnection.class);

        if (dbconn != null) {
            enabled = dbconn.isVitalConnection();
        }

        return enabled;
    }

    @Override
    public HelpCtx getHelpCtx() {
        return new HelpCtx(CreateTableAction.class);
    }

    @Override
    public void performAction (Node[] activatedNodes) {
        final BaseNode node = activatedNodes[0].getLookup().lookup(BaseNode.class);
        RequestProcessor.getDefault().post(
            new Runnable() {
            @Override
                public void run() {
                    perform(node);
                }
            }
        );
    }

    private void perform(final BaseNode node) {
        String schema = findSchemaWorkingName(node.getLookup());

        // ADITO
        //boolean tableCreated = CreateTableDialog.showDialogAndCreate(node, schema);
        DatabaseConnection connection = node.getLookup().lookup(DatabaseConnection.class);
        Specification spec = connection.getConnector().getDatabaseSpecification();

        List<ColumnItem> items = Lookup.getDefault().lookup(IDefaultSystemStructureProvider.class).getDefaultColumnItems(spec);
        boolean tableCreated = CreateTableDialog.showDialogAndCreate(connection.getConnector().getDatabaseSpecification(),
                                                                     schema, items, null);
        if (tableCreated) {
            SystemAction.get(RefreshAction.class).performAction(new Node[] { node });
        }
    }

    @Override
    public String getName() {
        return NbBundle.getMessage (CreateTableAction.class, "CreateTable"); // NOI18N
    }

}
