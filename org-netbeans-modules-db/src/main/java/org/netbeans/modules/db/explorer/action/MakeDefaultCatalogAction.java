/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2009-2010 Sun Microsystems, Inc.
 */

package org.netbeans.modules.db.explorer.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.lib.ddl.CommandNotSupportedException;
import org.netbeans.lib.ddl.DDLException;
import org.netbeans.lib.ddl.impl.Specification;
import org.netbeans.modules.db.explorer.DatabaseConnection;
import org.netbeans.modules.db.explorer.DatabaseConnector;
import org.netbeans.modules.db.explorer.node.CatalogNode;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;

/**
 *
 * @author Rob Englander
 */
public class MakeDefaultCatalogAction extends BaseAction {
    private static final Logger LOGGER = Logger.getLogger(MakeDefaultCatalogAction.class.getName());

    @Override
    public String getName() {
        return NbBundle.getMessage (MakeDefaultCatalogAction.class, "MakeDefaultCatalog"); // NOI18N
    }

    @Override
    protected boolean enable(Node[] activatedNodes) {
        boolean result = false;

        if (activatedNodes.length == 1) {
            CatalogNode node = activatedNodes[0].getLookup().lookup(CatalogNode.class);
            if (node != null) {
                DatabaseConnector connector = node.getLookup().lookup(DatabaseConnection.class).getConnector();
                result = connector.supportsCommand(Specification.DEFAULT_DATABASE);
            }
        }

        return result;
    }

    @Override
    protected void performAction(final Node[] activatedNodes) {
        RequestProcessor.getDefault().post(
            new Runnable() {
                @Override
                public void run() {
                    DatabaseConnection connection = activatedNodes[0].getLookup().lookup(DatabaseConnection.class);
                    String name = activatedNodes[0].getLookup().lookup(CatalogNode.class).getName();

                    try {
                        connection.setDefaultCatalog(name);
                    } catch (CommandNotSupportedException ex) {
                        Exceptions.printStackTrace(ex);
                    } catch (DDLException e) {
                        try {
                            handleDLLException(connection, e);
                        } catch (SQLException ex) {
                            Exceptions.printStackTrace(e);
                            Exceptions.printStackTrace(ex);
                        }
                    }
                }
            }
        );
    }

    /**
     * If DDL exception was caused by a closed connection, log info and display
     * a simple error dialog. Otherwise let users report the exception.
     */
    private void handleDLLException(DatabaseConnection dbConn,
            DDLException e) throws SQLException, MissingResourceException {
        Connection conn = dbConn == null ? null : dbConn.getJDBCConnection();
        if (conn != null && !conn.isValid(1000)) {
            LOGGER.log(Level.INFO, e.getMessage(), e);
            NotifyDescriptor nd = new NotifyDescriptor.Message(
                    NbBundle.getMessage(
                    MakeDefaultCatalogAction.class,
                    "ERR_ConnectionToServerClosed"), //NOI18N
                    NotifyDescriptor.ERROR_MESSAGE);
            DialogDisplayer.getDefault().notifyLater(nd);
        } else {
            Exceptions.printStackTrace(e);
        }
    }

    @Override
    public HelpCtx getHelpCtx() {
        return new HelpCtx(MakeDefaultCatalogAction.class);
    }


}
