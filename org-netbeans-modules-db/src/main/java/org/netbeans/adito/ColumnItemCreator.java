package org.netbeans.adito;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.database.*;
import org.netbeans.modules.db.explorer.dlg.*;

import java.lang.reflect.Field;
import java.sql.Types;
import java.util.*;

/**
 * Liefert ColumnItems.
 *
 * @author J. Boesl, 14.01.13
 */
public class ColumnItemCreator
{

  private ColumnItemCreator()
  {
  }

  public static List<ColumnItem> getDefaultSystemColumnItems()
  {
    List<IAditoDbColumn> defaultSystemColumns = NbAditoInterface.lookup(IAditoDbInfo.class).createDefaultSystemColumns();
    return toColumnItems(defaultSystemColumns);
  }


  public static List<ColumnItem> toColumnItems(Iterable<IAditoDbColumn> pCols)
  {
    List<ColumnItem> columnItemList = new ArrayList<>();
    for (IAditoDbColumn col : pCols)
    {
      ColumnItem columnItem = new ColumnItem();
      columnItem.setProperty(ColumnItem.NAME, col.getName());
      columnItem.setProperty(ColumnItem.NULLABLE, col.isNullable());
      columnItem.setProperty(ColumnItem.PRIMARY_KEY, col.isPrimaryKey());
      columnItem.setProperty(ColumnItem.SCALE, col.getScale());
      columnItem.setProperty(ColumnItem.SIZE, col.getSize());
      columnItem.setProperty(ColumnItem.TYPE, _create(col.getType()));
      columnItem.setProperty(ColumnItem.UNIQUE, col.isUnique());
      columnItem.setProperty(ColumnItem.INDEX, col.isIndex());
      String defVal = col.getDefVal();
      columnItem.setProperty(ColumnItem.DEFVAL, defVal == null ? "" : defVal);
      columnItemList.add(columnItem);
    }
    return columnItemList;
  }

  /**
   * Erstellt ein TypeElement.
   *
   * @param pSqlType eine Konstante aus java.sql.Types.
   * @return das erstellte Objekt.
   * @see java.sql.Types
   */
  private static TypeElement _create(int pSqlType)
  {
    Integer param = pSqlType;
    TypeElement typeElement = null;
    Field[] fields = Types.class.getFields();
    for (Field field : fields)
    {
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
