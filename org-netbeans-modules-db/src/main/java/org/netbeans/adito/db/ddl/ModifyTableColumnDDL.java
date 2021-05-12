package org.netbeans.adito.db.ddl;

import org.netbeans.lib.ddl.impl.*;

/**
 * Creates a DDL-String for modifying an existing column of a table
 *
 * @author s.seemann, 16.02.2021
 */
class ModifyTableColumnDDL
{
  /**
   * @return the Command for the DDL to modify an existing column
   */
  public String getDDL(Specification pSpec, String pSchema, String pTableName, String pColumnName, int pType, int pSize, int pDecSize,
                       String pDefaultValue, boolean pNullAllowed) throws Exception
  {
    ModifyColumn modifyColumn = pSpec.createCommandModifyColumn(pTableName);
    modifyColumn.setObjectOwner(pSchema);
    modifyColumn.setNewObject(false);

    TableColumn column = (TableColumn) modifyColumn.specifyColumn("COLUMN", pColumnName);
    column.setNewColumn(false);
    column.setNewObject(false);

    column.setColumnType(pType);
    column.setColumnSize(pSize);
    column.setDecimalSize(pDecSize);
    column.setColumnName(pColumnName);
    column.setDefaultValue(pDefaultValue);
    column.setNullAllowed(pNullAllowed);

    return modifyColumn.getCommand();
  }
}
