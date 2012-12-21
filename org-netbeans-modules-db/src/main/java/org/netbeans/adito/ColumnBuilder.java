package org.netbeans.adito;

import org.netbeans.modules.db.explorer.dlg.*;

import java.lang.reflect.Field;
import java.sql.Types;

/**
 * Unter Anwendung des Builder Patterns wird ein ColumnItem erstellt.
 *
 * @author Thomas Tasior 13.12.12, 07:41
 */
public class ColumnBuilder
{
  private ColumnItem i;

  private void _init()
  {
    if (i == null)
      i = new ColumnItem();
  }

  /**
   * Setzt den Spaltennamen.
   *
   * @param pName der  Spaltenname.
   * @return sich selbst.
   */
  public ColumnBuilder name(String pName)
  {
    _init();
    i.setProperty(ColumnItem.NAME, pName);
    return this;
  }

  /**
   * Setzt den Spaltentyp.
   *
   * @param pType eine Konstante aus java.sql.Types.
   * @return sich selbst.
   * @see java.sql.Types
   */
  public ColumnBuilder type(int pType)
  {
    _init();
    i.setProperty(ColumnItem.TYPE, create(pType));
    return this;
  }

  /**
   * Setzt die Spaltengröße.
   *
   * @param pSize ein positiver int Wert.
   * @return sich selbst.
   */
  public ColumnBuilder size(int pSize)
  {
    _init();
    i.setProperty(ColumnItem.SIZE, pSize);
    return this;
  }

  /**
   * Setzt die Dezimalstellen.
   *
   * @param pScale ein positiver int Wert.
   * @return sich selbst.
   */
  public ColumnBuilder scale(int pScale)
  {
    _init();
    i.setProperty(ColumnItem.SCALE, pScale);
    return this;
  }

  /**
   * Nach dem Aufruf dieser Methode ist die Spalte als
   * Primärschlüssel definiert.
   *
   * @return sich selbst.
   */
  public ColumnBuilder primaryKey()
  {
    _init();
    i.setProperty(ColumnItem.PRIMARY_KEY, true);
    i.setProperty(ColumnItem.INDEX, true);
    i.setProperty(ColumnItem.UNIQUE, true);
    return this;
  }

  /**
   * Nach dem Aufruf dieser Methode ist die Spalte als
   * eindeutig definiert.
   *
   * @return sich selbst.
   */
  public ColumnBuilder unique()
  {
    _init();
    i.setProperty(ColumnItem.UNIQUE, true);
    return this;
  }

  /**
   * Nach dem Aufruf dieser Methode ist die Spalte als
   * not null definiert.
   *
   * @return sich selbst.
   */
  public ColumnBuilder notNull()
  {
    _init();
    i.setProperty(ColumnItem.NULLABLE, false);
    return this;
  }

  //public ColumnBuilder index(boolean pFlag)
  //{
  //  _init();
  //  i.setProperty(ColumnItem.INDEX, pFlag);
  //  return this;
  //}

  /**
   * Setzt der Spalte den String einer Funktion.
   *
   * @param pVal eine DB Funktion o.Ä.
   * @return sich selbst.
   */
  public ColumnBuilder defVal(String pVal)
  {
    _init();
    i.setProperty(ColumnItem.DEFVAL, pVal);
    return this;
  }

  /**
   * Liefert das vorher definierte Spaltenobjekt zurück.
   * Der Builder ist nach dem Verlassen dieser Methode
   * für das Erstellen einer weiteren Spalte vorbereitet.
   *
   * @return das vorher definierte Spaltenobjekt.
   */
  public ColumnItem build()
  {
    ColumnItem handoff = i;
    i = null;
    return handoff;
  }

  /**
   * Erstellt ein TypeElement.
   * @param pSqlType eine Konstante aus java.sql.Types.
   * @return das erstellte Objekt.
   * @see java.sql.Types
   */
  public static TypeElement create(int pSqlType)
  {
    Integer param = new Integer(pSqlType);
    TypeElement typeElement = null;
    Field[] fields = Types.class.getFields();
    for (int i = 0; i < fields.length; i++)
    {
      Field field = fields[i];

      try
      {
        if (param.equals(field.get(null)))
        {
          String name = field.getName();
          String className = Types.class.getName();
          typeElement = new TypeElement(className + "." + name, name);
        }
      }
      catch (IllegalAccessException e)
      {
        //tut nichts
      }
    }
    return typeElement;
  }
}
