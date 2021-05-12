package org.netbeans.adito.db.ddl;

import org.netbeans.lib.ddl.impl.*;

/**
 * Creates a DDL-String for adding a column to a table
 *
 * @author s.seemann, 18.02.2021
 */
public class AddTableColumnDDL
{
  public String getDDL(Specification pSpec, String pSchema, String pTableName, String pColumnName, int pType, int pSize, int pDecSize)
      throws Exception
  {
    AddColumn column = pSpec.createCommandAddColumn(pTableName);

    column.setObjectOwner(pSchema);
    column.setNewObject(true);
    TableColumn tableColumn = (TableColumn) column.specifyColumn("COLUMN", pColumnName);
    tableColumn.setColumnType(pType);
    tableColumn.setColumnSize(pSize);
    tableColumn.setDecimalSize(pDecSize);
    tableColumn.setColumnName(pColumnName);

    return column.getCommand();
  }
}
