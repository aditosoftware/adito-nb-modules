package org.netbeans.adito;

import org.netbeans.modules.db.explorer.dlg.*;

import java.util.ArrayList;

/**
 * DefaultSpalten im Tabelle erstellen Dialog hinzufügen
 *
 * @author T. Feldmann, 25.09.12
 */
public class CreateDefaultColumns
{
  private TypeElement VARCHAR = new TypeElement("java.sql.Types.VARCHAR", "VARCHAR");
  private TypeElement CHAR = new TypeElement("java.sql.Types.CHAR", "CHAR");
  private TypeElement TIMESTAMP = new TypeElement("java.sql.Types.TIMESTAMP", "TIMESTAMP");

  ArrayList<ColumnModel> columnItems = new ArrayList<ColumnModel>();


  public CreateDefaultColumns()
  {
    columnItems.add(new ColumnModel(CHAR, "ID", "36", "0", Boolean.FALSE, Boolean.TRUE));
    columnItems.add(new ColumnModel(TIMESTAMP, "DATE_EDIT", "26", "6", Boolean.TRUE, Boolean.FALSE));
    columnItems.add(new ColumnModel(TIMESTAMP, "DATE_NEW", "26", "6", Boolean.TRUE, Boolean.FALSE));
    columnItems.add(new ColumnModel(VARCHAR, "USER_EDIT", "30", "0", Boolean.TRUE, Boolean.FALSE));
    columnItems.add(new ColumnModel(VARCHAR, "USER_NEW", "30", "0", Boolean.TRUE, Boolean.FALSE));
  }

  public void addDefaultColumns(DataModel pDataModel)
  {

    for (ColumnModel model : columnItems)
    {
      ColumnItem columnItem = new ColumnItem();
      columnItem.setProperty(ColumnItem.NAME, model.getColumnName());
      columnItem.setProperty(ColumnItem.TYPE, model.getColumnType());
      columnItem.setProperty(ColumnItem.SIZE, model.getColumnSize());
      columnItem.setProperty(ColumnItem.SCALE, model.getColumnScale());
      columnItem.setProperty(ColumnItem.NULLABLE, model.isColumnNullable());
      columnItem.setProperty(ColumnItem.PRIMARY_KEY, model.isColumnPrimaryKey());
      columnItem.setProperty(ColumnItem.INDEX, model.isColumnPrimaryKey());
      columnItem.setProperty(ColumnItem.UNIQUE, model.isColumnPrimaryKey());

      pDataModel.addRow(columnItem);

    }

  }


}
