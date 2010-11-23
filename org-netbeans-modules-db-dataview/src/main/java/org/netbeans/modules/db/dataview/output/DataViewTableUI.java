/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
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
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
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
 */
package org.netbeans.modules.db.dataview.output;

import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import org.netbeans.modules.db.dataview.meta.DBColumn;
import org.netbeans.modules.db.dataview.meta.DBException;
import org.netbeans.modules.db.dataview.meta.DBTable;
import org.netbeans.modules.db.dataview.table.ResultSetCellRenderer;
import org.netbeans.modules.db.dataview.table.ResultSetJXTable;
import org.netbeans.modules.db.dataview.table.ResultSetTableModel;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.datatransfer.ExClipboard;
import org.openide.windows.WindowManager;

/**
 * Renders the current result page
 *
 * @author Ahimanikya Satapathy
 */
class DataViewTableUI extends ResultSetJXTable {

    private JPopupMenu tablePopupMenu;
    private final DataViewTablePanel tablePanel;
    private DataViewActionHandler handler;
    private int selectedRow = -1;
    private int selectedColumn = -1;

    public DataViewTableUI(final DataViewTablePanel tablePanel, final DataViewActionHandler handler, final DataView dataView) {
        super(dataView);

        this.tablePanel = tablePanel;
        this.handler = handler;

        TableSelectionListener listener = new TableSelectionListener(this);
        this.getSelectionModel().addListSelectionListener(listener);
        this.getColumnModel().getSelectionModel().addListSelectionListener(listener);

        addKeyListener(createControKeyListener());
        createPopupMenu(handler, dataView);
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        if (dView.getUpdatedRowContext().hasUpdates(row, column)) {
            return new UpdatedResultSetCellRenderer(dView);
        }
        return super.getCellRenderer(row, column);
    }

    @Override
    protected KeyListener createControKeyListener() {
        return new Control0KeyListener();
    }

    @Override
    protected DefaultTableModel getDefaultTableModel() {
        return new DataViewTableUIModel(this);
    }

    private class DataViewTableUIModel extends ResultSetTableModel {

        protected DataViewTableUIModel(ResultSetJXTable table) {
            super(table);
        }

        @Override
        protected void handleColumnUpdated(int row, int col, Object value) {
            dView.getUpdatedRowContext().addUpdates(row, col, value, getModel());
            tablePanel.handleColumnUpdated();
        }
    }

    private static class UpdatedResultSetCellRenderer extends ResultSetCellRenderer {

        static Color green = new Color(0, 128, 0);
        static Color gray = new Color(245, 245, 245);
        DataView dataView;

        public UpdatedResultSetCellRenderer(DataView dView) {
            dataView = dView;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            Object obj = dataView.getDataViewPageContext().getColumnData(row, column);
            if (value == null) {
                return c;
            }

            if (isSelected) {
                if (obj != null && value.equals(obj)) {
                    c.setForeground(gray);
                } else {
                    c.setForeground(Color.ORANGE);
                }
            } else {
                if (obj != null && value.equals(obj)) {
                    c.setForeground(table.getForeground());
                } else {
                    c.setForeground(green);
                }
            }
            return c;
        }
    }

    private class Control0KeyListener implements KeyListener {

        public Control0KeyListener() {
        }

        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_DELETE) {
                TableCellEditor editor = getCellEditor();
                if (editor != null) {
                    editor.stopCellEditing();
                }
                handler.deleteRecordActionPerformed();
            } else if (e.isControlDown() && e.getKeyChar() == KeyEvent.VK_0) {
                int row = getSelectedRow();
                int col = getSelectedColumn();
                if (row == -1) {
                    return;
                }
                editCellAt(row, col);

                TableCellEditor editor = getCellEditor();
                if (editor != null) {
                    DBColumn dbcol = getDBColumn(col);
                    if (dbcol.isGenerated() || !dbcol.isNullable()) {
                        Toolkit.getDefaultToolkit().beep();
                        editor.stopCellEditing();
                    } else {
                        editor.getTableCellEditorComponent(DataViewTableUI.this, null, rowSelectionAllowed, row, col);
                        setValueAt(null, row, col);
                        editor.stopCellEditing();
                    }
                    setRowSelectionInterval(row, row);
                }
            } else if (e.isControlDown() && e.getKeyChar() == KeyEvent.VK_1) {
                int row = getSelectedRow();
                int col = getSelectedColumn();
                if (row == -1) {
                    return;
                }

                editCellAt(row, col);
                TableCellEditor editor = getCellEditor();
                if (editor != null) {
                    DBColumn dbcol = getDBColumn(col);
                    Object val = getValueAt(row, col);
                    if (dbcol.isGenerated() || !dbcol.hasDefault()) {
                        Toolkit.getDefaultToolkit().beep();
                        editor.stopCellEditing();
                    } else if (val != null && val instanceof String && ((String) val).equals("<DEFAULT>")) {
                        editor.getTableCellEditorComponent(DataViewTableUI.this, "", rowSelectionAllowed, row, col);
                        setValueAt(null, row, col);
                        editor.stopCellEditing();
                    } else {
                        editor.getTableCellEditorComponent(DataViewTableUI.this, "<DEFAULT>", rowSelectionAllowed, row, col);
                        setValueAt("<DEFAULT>", row, col);
                        editor.stopCellEditing();
                    }
                    setRowSelectionInterval(row, row);
                }
            }
        }

        public void keyReleased(KeyEvent e) {
        }
    }

    private class TableSelectionListener implements ListSelectionListener {

        JTable table;

        TableSelectionListener(JTable table) {
            this.table = table;
        }

        public void valueChanged(ListSelectionEvent e) {
            if (tablePanel == null) {
                return;
            }

            if (e.getSource() == table.getSelectionModel() && table.getRowSelectionAllowed()) {
                int first = e.getFirstIndex();
                if (first >= 0 && tablePanel.isEditable()) {
                    tablePanel.enableDeleteBtn(true);
                } else {
                    tablePanel.enableDeleteBtn(false);
                }
            }
        }
    }

    private void createPopupMenu(final DataViewActionHandler handler, final DataView dataView) {
        // content popup menu on table with results
        tablePopupMenu = new JPopupMenu();
        final JMenuItem miInsertAction = new JMenuItem(NbBundle.getMessage(DataViewTableUI.class, "TOOLTIP_insert"));
        miInsertAction.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                handler.insertActionPerformed();
            }
        });
        tablePopupMenu.add(miInsertAction);


        final JMenuItem miDeleteAction = new JMenuItem(NbBundle.getMessage(DataViewTableUI.class, "TOOLTIP_deleterow"));
        miDeleteAction.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                handler.deleteRecordActionPerformed();
            }
        });
        tablePopupMenu.add(miDeleteAction);

        final JMenuItem miCommitAction = new JMenuItem(NbBundle.getMessage(DataViewTableUI.class, "TOOLTIP_commit"));
        miCommitAction.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                handler.commitActionPerformed(true);
            }
        });
        tablePopupMenu.add(miCommitAction);

        final JMenuItem miCancelEdits = new JMenuItem(NbBundle.getMessage(DataViewTableUI.class, "TOOLTIP_cancel_edits"));
        miCancelEdits.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                handler.cancelEditPerformed(true);
            }
        });
        tablePopupMenu.add(miCancelEdits);

        final JMenuItem miTruncateRecord = new JMenuItem(NbBundle.getMessage(DataViewTableUI.class, "TOOLTIP_truncate_table"));
        miTruncateRecord.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                handler.truncateActionPerformed();
            }
        });
        tablePopupMenu.add(miTruncateRecord);
        tablePopupMenu.addSeparator();

        final JMenuItem miCopyValue = new JMenuItem(NbBundle.getMessage(DataViewTableUI.class, "TOOLTIP_copy_cell_value"));
        miCopyValue.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    Object o = getValueAt(selectedRow, selectedColumn);
                    String output = (o != null) ? o.toString() : ""; //NOI18N

                    ExClipboard clipboard = Lookup.getDefault().lookup(ExClipboard.class);
                    StringSelection strSel = new StringSelection(output);
                    clipboard.setContents(strSel, strSel);
                } catch (ArrayIndexOutOfBoundsException exc) {
                }
            }
        });
        tablePopupMenu.add(miCopyValue);

        final JMenuItem miCopyRowValues = new JMenuItem(NbBundle.getMessage(DataViewTableUI.class, "TOOLTIP_copy_row_value"));
        miCopyRowValues.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                copyRowValues(false);
            }
        });
        tablePopupMenu.add(miCopyRowValues);

        final JMenuItem miCopyRowValuesH = new JMenuItem(NbBundle.getMessage(DataViewTableUI.class, "TOOLTIP_copy_row_header"));
        miCopyRowValuesH.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                copyRowValues(true);
            }
        });
        tablePopupMenu.add(miCopyRowValuesH);
        tablePopupMenu.addSeparator();

        final JMenuItem miCreateSQLScript = new JMenuItem(NbBundle.getMessage(DataViewTableUI.class, "TOOLTIP_show_create_sql"));
        miCreateSQLScript.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    DBTable table = dataView.getDataViewDBTable().geTable(0);
                    String createSQL = dataView.getSQLStatementGenerator().generateCreateStatement(table);
                    ShowSQLDialog dialog = new ShowSQLDialog();
                    dialog.setLocationRelativeTo(WindowManager.getDefault().getMainWindow());
                    dialog.setText(createSQL + ";\n"); // NOI18N
                    dialog.setVisible(true);
                } catch (Exception ex) {
                    NotifyDescriptor nd = new NotifyDescriptor.Message(ex.getMessage());
                    DialogDisplayer.getDefault().notify(nd);
                }
            }
        });
        tablePopupMenu.add(miCreateSQLScript);

        final JMenuItem miInsertSQLScript = new JMenuItem(NbBundle.getMessage(DataViewTableUI.class, "TOOLTIP_show_insert_sql"));
        miInsertSQLScript.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    int[] rows = getSelectedRows();
                    String insertSQL = "";
                    for (int j = 0; j < rows.length; j++) {
                        Object[] insertRow = dataView.getDataViewPageContext().getCurrentRows().get(rows[j]);
                        String sql = dataView.getSQLStatementGenerator().generateRawInsertStatement(insertRow);
                        insertSQL += sql.replaceAll("\n", "").replaceAll("\t", "") + ";\n"; // NOI18N
                    }
                    ShowSQLDialog dialog = new ShowSQLDialog();
                    dialog.setLocationRelativeTo(WindowManager.getDefault().getMainWindow());
                    dialog.setText(insertSQL);
                    dialog.setVisible(true);
                } catch (DBException ex) {
                    NotifyDescriptor nd = new NotifyDescriptor.Message(ex.getMessage());
                    DialogDisplayer.getDefault().notify(nd);
                }
            }
        });
        tablePopupMenu.add(miInsertSQLScript);

        final JMenuItem miDeleteSQLScript = new JMenuItem(NbBundle.getMessage(DataViewTableUI.class, "TOOLTIP_show_delete_sql"));
        miDeleteSQLScript.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int[] rows = getSelectedRows();
                String rawDeleteStmt = "";
                for (int j = 0; j < rows.length; j++) {
                    SQLStatementGenerator generator = dataView.getSQLStatementGenerator();
                    final String deleteStmt = generator.generateDeleteStatement(rows[j], getModel());
                    rawDeleteStmt += deleteStmt + ";\n"; // NOI18N
                }
                ShowSQLDialog dialog = new ShowSQLDialog();
                dialog.setLocationRelativeTo(WindowManager.getDefault().getMainWindow());
                dialog.setText(rawDeleteStmt);
                dialog.setVisible(true);
            }
        });
        tablePopupMenu.add(miDeleteSQLScript);

        final JMenuItem miCommitSQLScript = new JMenuItem(NbBundle.getMessage(DataViewTableUI.class, "TOOLTIP_show_update_sql"));
        miCommitSQLScript.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String rawUpdateStmt = "";
                UpdatedRowContext updatedRowCtx = dataView.getUpdatedRowContext();
                SQLStatementGenerator generator = dataView.getSQLStatementGenerator();

                try {
                    for (Integer row : updatedRowCtx.getUpdateKeys()) {
                        Map<Integer, Object> changedData = updatedRowCtx.getChangedData(row);
                        rawUpdateStmt += generator.generateUpdateStatement(row, changedData, dataModel) + ";\n"; // NOI18N
                    }
                    ShowSQLDialog dialog = new ShowSQLDialog();
                    dialog.setLocationRelativeTo(WindowManager.getDefault().getMainWindow());
                    dialog.setText(rawUpdateStmt);
                    dialog.setVisible(true);
                } catch (DBException ex) {
                    NotifyDescriptor nd = new NotifyDescriptor.Message(ex.getMessage());
                    DialogDisplayer.getDefault().notify(nd);
                }
            }
        });
        tablePopupMenu.add(miCommitSQLScript);

        tablePopupMenu.addSeparator();

        JMenuItem printTable = new JMenuItem(NbBundle.getMessage(DataViewTableUI.class, "TOOLTIP_print_data"));

        printTable.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Logger mLogger = Logger.getLogger(DataViewTableUI.class.getName());
                try {
                    if (!print()) {
                        mLogger.log(Level.INFO, NbBundle.getMessage(DataViewTableUI.class, "MSG_cancel_printing"));
                    }
                } catch (java.awt.print.PrinterException ex) {
                    mLogger.log(Level.INFO, NbBundle.getMessage(DataViewTableUI.class, "MSG_failure_to_print" + ex.getMessage()));
                }
            }
        });
        tablePopupMenu.add(printTable);

        JMenuItem miRefreshAction = new JMenuItem(NbBundle.getMessage(DataViewTableUI.class, "TOOLTIP_refresh"));
        miRefreshAction.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                handler.refreshActionPerformed();
            }
        });
        tablePopupMenu.add(miRefreshAction);

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    selectedRow = rowAtPoint(e.getPoint());
                    selectedColumn = columnAtPoint(e.getPoint());
                    boolean inSelection = false;
                    int[] rows = getSelectedRows();
                    for (int a = 0; a < rows.length; a++) {
                        if (rows[a] == selectedRow) {
                            inSelection = true;
                            break;
                        }
                    }
                    if (!getRowSelectionAllowed()) {
                        inSelection = false;
                        int[] columns = getSelectedColumns();
                        for (int a = 0; a < columns.length; a++) {
                            if (columns[a] == selectedColumn) {
                                inSelection = true;
                                break;
                            }
                        }
                    }
                    if (!inSelection) {
                        changeSelection(selectedRow, selectedColumn, false, false);
                    }
                    if (!tablePanel.isEditable()) {
                        miInsertAction.setEnabled(false);
                        miDeleteAction.setEnabled(false);
                        miTruncateRecord.setEnabled(false);
                        miInsertSQLScript.setEnabled(false);
                        miCreateSQLScript.setEnabled(false);
                        miDeleteSQLScript.setEnabled(false);
                    }

                    if (!tablePanel.isCommitEnabled()) {
                        miCommitAction.setEnabled(false);
                        miCancelEdits.setEnabled(false);
                        miCommitSQLScript.setEnabled(false);
                    } else {
                        miCommitAction.setEnabled(true);
                        miCancelEdits.setEnabled(true);
                        miCommitSQLScript.setEnabled(true);
                    }
                    tablePopupMenu.show(DataViewTableUI.this, e.getX(), e.getY());
                }
            }
        });
    }

    private void copyRowValues(boolean withHeader) {
        try {
            int[] rows = getSelectedRows();
            int[] columns;
            if (getRowSelectionAllowed()) {
                columns = new int[getColumnCount()];
                for (int a = 0; a < columns.length; a++) {
                    columns[a] = a;
                }
            } else {
                columns = getSelectedColumns();
            }
            if (rows != null && columns != null) {
                StringBuffer output = new StringBuffer();

                if (withHeader) {
                    for (int column = 0; column < columns.length; column++) {
                        if (column > 0) {
                            output.append('\t'); //NOI18N

                        }
                        Object o = getColumnModel().getColumn(column).getHeaderValue();
                        output.append(o != null ? o.toString() : ""); //NOI18N

                    }
                    output.append('\n'); //NOI18N

                }

                for (int row = 0; row < rows.length; row++) {
                    for (int column = 0; column < columns.length; column++) {
                        if (column > 0) {
                            output.append('\t'); //NOI18N

                        }
                        Object o = getValueAt(rows[row], columns[column]);
                        output.append(o != null ? o.toString() : ""); //NOI18N

                    }
                    output.append('\n'); //NOI18N

                }
                ExClipboard clipboard = Lookup.getDefault().lookup(ExClipboard.class);
                StringSelection strSel = new StringSelection(output.toString());
                clipboard.setContents(strSel, strSel);
            }
        } catch (ArrayIndexOutOfBoundsException exc) {
            Exceptions.printStackTrace(exc);
        }
    }
}
