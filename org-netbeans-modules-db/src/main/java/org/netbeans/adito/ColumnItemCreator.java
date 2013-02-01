package org.netbeans.adito;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.database.*;
import org.netbeans.lib.ddl.impl.Specification;
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

  public static List<ColumnItem> getDefaultSystemColumnItems(String pDriverName, Specification pSpecification)
  {

    List<IAditoDbColumn> defaultSystemColumns = NbAditoInterface.lookup(IAditoDbInfo.class).createDefaultSystemColumns(pDriverName);
    return toColumnItems(defaultSystemColumns, pSpecification);
  }


  public static List<ColumnItem> toColumnItems(Iterable<IAditoDbColumn> pCols, Specification pSpecification)
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
      columnItem.setProperty(ColumnItem.TYPE, _create(col.getType(), pSpecification));
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
   * @param pSqlType       eine Konstante aus java.sql.Types.
   * @param pSpecification liefert das DB spezifische Mapping der Datentypen.
   * @return das erstellte Objekt.
   * @see java.sql.Types
   */
  private static TypeElement _create(int pSqlType, Specification pSpecification)
  {
    Integer sqlType = pSqlType;
    TypeElement typeElement = null;
    Field[] fields = Types.class.getFields();
    Map<String, String> tmap = pSpecification.getTypeMap();
    for (Field field : fields)
    {
      try
      {
        if (sqlType.equals(field.get(null)))
        {
          String className = Types.class.getName();
          String fullName = className + "." + field.getName();

          String mapping = tmap.get(fullName);
          typeElement = new TypeElement(fullName, mapping);
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
