package org.netbeans.adito;

import org.netbeans.modules.db.explorer.dlg.ColumnItem;

import java.sql.Types;
import java.util.*;

/**
 * Definiert eine Systemtabelle im Adito Projekt.
 *
 * @author Thomas Tasior 12.12.12, 10:51
 */
public abstract class TableModel
{
  private ESystemTable table;
  private List<ColumnItem> columnItems;

  /**
   * Initialisiert das Modell mit der Konstanten die die Tabelle beschreibt.
   *
   * @param pConstant eine Konstante aus ESystemTable.
   * @see ESystemTable
   */
  public TableModel(ESystemTable pConstant)
  {
    table = pConstant;
    columnItems = new ArrayList<>();
    defineColumns(columnItems);
  }

  /**
   * In dieser Methode werden die Spalten der Tabelle definiert und der Liste hinzugefügt.
   * @param pList nimmt die Spalten entgegen.
   */
  protected abstract void defineColumns(List<ColumnItem> pList);

  public List<ColumnItem> getColumns()
  {
    return columnItems;
  }

  /**
   * Erstellt die Definition der ID Spalte.
   */
  public ColumnItem build_ID()
  {
    return new ColumnBuilder()
        .name("ID")
        .type(Types.CHAR)
        .size(36)
        .notNull()
        .primaryKey()
        .build();
  }

  /**
   * Erstellt die Definition der DATE_EDIT Spalte.
   */
  public ColumnItem build_DATE_EDIT()
  {
    return new ColumnBuilder()
        .name("DATE_EDIT")
        .type(Types.TIMESTAMP)
        .build();
  }

  /**
   * Erstellt die Definition der DATE_NEW Spalte.
   */
  public ColumnItem build_DATE_NEW()
  {
    return new ColumnBuilder()
        .name("DATE_NEW")
        .type(Types.TIMESTAMP)
        .build();
  }

  /**
   * Erstellt die Definition der USER_EDIT Spalte.
   */
  public ColumnItem build_USER_EDIT()
  {
    return new ColumnBuilder()
        .name("USER_EDIT")
        .type(Types.VARCHAR)
        .size(63)
        .build();
  }
  /**
   * Erstellt die Definition der USER_NEW Spalte.
   */
  public ColumnItem build_USER_NEW()
  {
    return new ColumnBuilder()
        .name("USER_NEW")
        .type(Types.VARCHAR)
        .size(63)
        .build();
  }

  /**
   * Liefert den Tabellennamen.
   * @return das Literal der Konstanten die im Konstruktor übergeben wurde.
   */
  public String getName()
  {
    return table.name();
  }

  /**
   * Liefert das gleiche wie getName().
   * @return das gleiche wie getName().
   */
  @Override
  public String toString()
  {
    return getName();
  }
}
