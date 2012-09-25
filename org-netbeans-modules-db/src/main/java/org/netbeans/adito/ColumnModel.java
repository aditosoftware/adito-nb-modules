package org.netbeans.adito;

import org.netbeans.modules.db.explorer.dlg.TypeElement;

/**
 * SpaltenModel
 *
 * @author T. Feldmann, 25.09.12
 */
public class ColumnModel
{
  private TypeElement columnType;
  private String columnName;
  private String columnSize;
  private String columnScale;
  private boolean columnNullable;
  private boolean columnPrimaryKey;


  public ColumnModel(TypeElement pColumnType, String pColumnName, String pColumnSize, String pColumnScale, boolean pColumnNullable, boolean pColumnPrimaryKey)
  {
    columnType = pColumnType;
    columnName = pColumnName;
    columnSize = pColumnSize;
    columnScale = pColumnScale;
    columnNullable = pColumnNullable;
    columnPrimaryKey = pColumnPrimaryKey;
  }


  public TypeElement getColumnType()
  {
    return columnType;
  }

  public void setColumnType(TypeElement pColumnType)
  {
    columnType = pColumnType;
  }

  public String getColumnName()
  {
    return columnName;
  }

  public void setColumnName(String pColumnName)
  {
    columnName = pColumnName;
  }

  public String getColumnSize()
  {
    return columnSize;
  }

  public void setColumnSize(String pColumnSize)
  {
    columnSize = pColumnSize;
  }

  public String getColumnScale()
  {
    return columnScale;
  }

  public void setColumnScale(String pColumnScale)
  {
    columnScale = pColumnScale;
  }

  public boolean isColumnNullable()
  {
    return columnNullable;
  }

  public void setColumnNullable(boolean pColumnNullable)
  {
    columnNullable = pColumnNullable;
  }

  public boolean isColumnPrimaryKey()
  {
    return columnPrimaryKey;
  }

  public void setColumnPrimaryKey(boolean pColumnPrimaryKey)
  {
    columnPrimaryKey = pColumnPrimaryKey;
  }
}
