package org.netbeans.modules.db.explorer.dlg;

import org.netbeans.lib.ddl.impl.*;

/**
 * This ADITO-class factors out the logic of actually modifying a column to
 * the database. It is responsible for interacting with the DDL package.
 *
 * @author s.seemann, 16.02.2021
 */
public class ModifyTableColumnDDL
{

  /**
   * // ADITO
   *
   * @return the Command for the DDL to modify an existing column
   */
  public ModifyColumn getDDL(Specification pSpec, String pSchema, String pTableName, String pColumnName, int pType, int pSize, int pDecSize,
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

    return modifyColumn;
  }
}
