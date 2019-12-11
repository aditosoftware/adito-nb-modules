package org.netbeans.adito.db;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.netbeans.lib.ddl.impl.Specification;
import org.netbeans.modules.db.explorer.dlg.ColumnItem;
import org.netbeans.modules.db.explorer.dlg.TypeElement;

import java.lang.reflect.Field;
import java.sql.Types;
import java.util.Map;
import java.util.Vector;

/**
 * Liefert ColumnItems.
 * Factory, da die originalen ColumnItems nicht einfach so erstellt werden können.
 *
 * @author J. Boesl, 14.01.13
 */
@SuppressWarnings("unused")
public class ColumnItemFactory
{

  private ColumnItemFactory()
  {
  }

  @SuppressWarnings("unused")
  public static ColumnItem createColumnItem(@NotNull String pName, boolean pNullable, int pScale, int pSize, int pType, boolean pPrimaryKey, boolean pUnique,
                                            boolean pIndex, @Nullable String pDefVal, @NotNull Specification pSpecification)
  {
    ColumnItem columnItem = new ColumnItem();
    columnItem.setProperty(ColumnItem.NAME, pName);
    columnItem.setProperty(ColumnItem.NULLABLE, pNullable);
    columnItem.setProperty(ColumnItem.SCALE, pScale);
    columnItem.setProperty(ColumnItem.SIZE, pSize);

    TypeElement typeElement = _create(pType, pSpecification);
    columnItem.setProperty(ColumnItem.TYPE,typeElement);

    Vector<String> noPrimaryKeyTypes = (Vector<String>) pSpecification.getProperties().get("NoPrimaryKeyTypes");
    if(noPrimaryKeyTypes == null || !noPrimaryKeyTypes.contains(typeElement.getName()))
      columnItem.setProperty(ColumnItem.PRIMARY_KEY, pPrimaryKey);
    else
      columnItem.setProperty(ColumnItem.PRIMARY_KEY, false);

    Vector<String> noIndexTypes = (Vector<String>) pSpecification.getProperties().get("NoIndexTypes");
    if(noIndexTypes == null || !noIndexTypes.contains(typeElement.getName()))
    {
      columnItem.setProperty(ColumnItem.UNIQUE, pUnique);
      columnItem.setProperty(ColumnItem.INDEX, pIndex);
    }else
    {
      columnItem.setProperty(ColumnItem.UNIQUE, false);
      columnItem.setProperty(ColumnItem.INDEX, false);
    }

    columnItem.setProperty(ColumnItem.DEFVAL, pDefVal == null ? "" : pDefVal);
    return columnItem;
  }

  /**
   * Erstellt ein TypeElement.
   *
   * @param pSqlType       eine Konstante aus java.sql.Types.
   * @param pSpecification liefert das DB spezifische Mapping der Datentypen.
   * @return das erstellte Objekt.
   * @see java.sql.Types
   */
  private static TypeElement _create(Integer pSqlType, Specification pSpecification)
  {
    TypeElement typeElement = null;
    Field[] fields = Types.class.getFields();
    @SuppressWarnings("unchecked")
    Map<String, String> tmap = pSpecification.getTypeMap();
    for (Field field : fields)
    {
      try
      {
        if (pSqlType.equals(field.get(null)))
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
