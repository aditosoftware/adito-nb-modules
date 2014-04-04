/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR parent HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.

Oracle and Java are registered trademarks of Oracle and/or its affiliates.
Other names may be trademarks of their respective owners.
 *
 * The contents of parent file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use parent file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include parent License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Sun designates parent
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied parent code. If applicable, add the following below the
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
 * If you wish your version of parent file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include parent software in parent distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of parent file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */
package org.netbeans.modules.db.dataview.table;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import org.netbeans.modules.db.dataview.meta.DBColumn;
import org.netbeans.modules.db.dataview.util.DBReadWriteHelper;
import org.netbeans.modules.db.dataview.util.DataViewUtils;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 * @author Ahimanikya Satapathy
 */
public class ResultSetTableModel extends AbstractTableModel {

    private boolean editable = false;
    private DBColumn[] columns;
    private final List<Object[]> data = new ArrayList<Object[]>();
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    protected static Class<? extends Object> getTypeClass(DBColumn col) {
        int colType = col.getJdbcType();

        if (colType == Types.BIT && col.getPrecision() <= 1) {
            colType = Types.BOOLEAN;
        }

        switch (colType) {
            case Types.BOOLEAN:
                return Boolean.class;
            case Types.TIME:
                return Time.class;
            case Types.DATE:
                return Date.class;
            case Types.TIMESTAMP:
            case -100:
                return Timestamp.class;
            case Types.BIGINT:
                return BigInteger.class;
            case Types.DOUBLE:
                return Double.class;
            case Types.FLOAT:
            case Types.REAL:
                return Float.class;
            case Types.DECIMAL:
            case Types.NUMERIC:
                return BigDecimal.class;
            case Types.INTEGER:
            case Types.SMALLINT:
            case Types.TINYINT:
                return Long.class;

            case Types.CHAR:
            case Types.VARCHAR:
            case -15:
            case -9:
            case -8:
                return String.class;

            case Types.BIT:
            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY:
            case Types.BLOB:
                return Blob.class;
            case Types.LONGVARCHAR:
            case -16:
            case Types.CLOB:
            case 2011: /*NCLOB */
                return Clob.class;
            case Types.OTHER:
            default:
                return Object.class;
        }
    }

    @SuppressWarnings("rawtypes")
    public ResultSetTableModel(DBColumn[] columns) {
        super();
        this.columns = columns;
    }

    public void setColumns(DBColumn[] columns) {
        assert SwingUtilities.isEventDispatchThread() : "Not on EDT";
        this.data.clear();
        this.columns = columns;
        fireTableStructureChanged();
    }

    public DBColumn[] getColumns() {
        assert SwingUtilities.isEventDispatchThread() : "Not on EDT";
        return Arrays.copyOf(columns, columns.length);
    }

    public void setEditable(boolean editable) {
        assert SwingUtilities.isEventDispatchThread() : "Not on EDT";
        boolean old = this.editable;
        this.editable = editable;
        pcs.firePropertyChange("editable", old, editable);
    }

    public boolean isEditable() {
        assert SwingUtilities.isEventDispatchThread() : "Not on EDT";
        return editable;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        assert SwingUtilities.isEventDispatchThread() : "Not on EDT";
        if (!editable) {
            return false;
        }
        DBColumn col = this.columns[column];
        return (!col.isGenerated()) && col.isEditable();
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        assert SwingUtilities.isEventDispatchThread() : "Not on EDT";
        Object oldVal = getValueAt(row, col);
        if (noUpdateRequired(oldVal, value)) {
            return;
        }
        try {
            if (!DataViewUtils.isSQLConstantString(value, columns[col])) {
                value = DBReadWriteHelper.validate(value, columns[col]);
            }
            data.get(row)[col] = value;
            fireTableCellUpdated(row, col);
        } catch (Exception dbe) {
            NotifyDescriptor nd = new NotifyDescriptor.Message(dbe.getMessage(),
                    NotifyDescriptor.ERROR_MESSAGE);
            DialogDisplayer.getDefault().notify(nd);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends Object> getColumnClass(int columnIndex) {
        assert SwingUtilities.isEventDispatchThread() : "Not on EDT";
        if (columns[columnIndex] == null) {
            return super.getColumnClass(columnIndex);
        } else {
            return getTypeClass(columns[columnIndex]);
        }
    }

    public DBColumn getColumn(int columnIndex) {
        assert SwingUtilities.isEventDispatchThread() : "Not on EDT";
        return columns[columnIndex];
    }

    @Override
    public int getColumnCount() {
        assert SwingUtilities.isEventDispatchThread() : "Not on EDT";
        return columns.length;
    }

    public String getColumnTooltip(int columnIndex) {
        assert SwingUtilities.isEventDispatchThread() : "Not on EDT";
        return DataViewUtils.getColumnToolTip(columns[columnIndex]);
    }

    @Override
    public String getColumnName(int columnIndex) {
        assert SwingUtilities.isEventDispatchThread() : "Not on EDT";
        String displayName = columns[columnIndex].getDisplayName();
        return displayName != null ? displayName : "COL_" + columnIndex;
    }

    protected boolean noUpdateRequired(Object oldVal, Object value) {
        if (oldVal == null && value == null) {
            return true;
        } else if (oldVal != null) {
            return oldVal.equals(value);
        }
        return false;
    }

    @Override
    public int getRowCount() {
        assert SwingUtilities.isEventDispatchThread() : "Not on EDT";
        return data.size();
    }
        
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        assert SwingUtilities.isEventDispatchThread() : "Not on EDT";
        Object[] dataRow = data.get(rowIndex);
        return dataRow[columnIndex];
    }

    public Object[] getRowData(int rowIndex) {
        assert SwingUtilities.isEventDispatchThread() : "Not on EDT";
        Object[] dataRow = data.get(rowIndex);
        return Arrays.copyOf(dataRow, dataRow.length);
    }

    public void setData(List<Object[]> data) {
        assert SwingUtilities.isEventDispatchThread() : "Not on EDT";
        this.data.clear();
        for (Object[] dataRow : data) {
            this.data.add(Arrays.copyOf(dataRow, dataRow.length));
        }
        fireTableDataChanged();
    }

    public List<Object[]> getData() {
        assert SwingUtilities.isEventDispatchThread() : "Not on EDT";
        ArrayList<Object[]> result = new ArrayList<Object[]>();
        for (Object[] dataRow : this.data) {
            result.add(Arrays.copyOf(dataRow, dataRow.length));
        }
        return result;
    }

    public void addRow(Object[] dataRow) {
        assert SwingUtilities.isEventDispatchThread() : "Not on EDT";
        int addedRowIndex = this.data.size();
        this.data.add(Arrays.copyOf(dataRow, dataRow.length));
        fireTableRowsInserted(addedRowIndex, addedRowIndex);
    }

    public void removeRow(int row) {
        assert SwingUtilities.isEventDispatchThread() : "Not on EDT";
        this.data.remove(row);
        fireTableRowsDeleted(row, row);
    }

    public void clear() {
        assert SwingUtilities.isEventDispatchThread() : "Not on EDT";
        this.data.clear();
        fireTableDataChanged();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(propertyName, listener);
    }
}
