package org.netbeans.adito.db;

import org.netbeans.lib.ddl.impl.*;

/**
 * Passt die TableColumns der netbeans.ddl für Oracle an.<br/>
 * Probleme wurden verursacht, weil die Namen für Constraints, PrimaryKeys, ... bei Oracle pro Schema eindeutig sein
 * müssen.
 *
 * @author J. Boesl, 08.05.13
 */
public class OracleTableColumnHack
{
  private static final int MAX_INDEX_NAME_LENGTH = 30;

  private OracleTableColumnHack()
  {
  }

  public static TableColumn fixUniqueColumn(Specification pSpecification, TableColumn pCol, String pTableName,
                                            String pColumnName, String pDbName)
  {
    return _fix(pSpecification, pCol, pTableName, pColumnName, "UQ", pDbName);
  }

  public static TableColumn fixPrimaryKeyColumn(Specification pSpecification, TableColumn pCol, String pTableName,
                                                String pColumnName, String pDbName)
  {
    return _fix(pSpecification, pCol, pTableName, pColumnName, "PK", pDbName);
  }

  public static TableColumn fixForeignKeyColumn(Specification pSpecification, TableColumn pCol, String pTableName,
                                                String pColumnName, String pDbName)
  {
    return _fix(pSpecification, pCol, pTableName, pColumnName, "FK", pDbName);
  }

  public static TableColumn fixCheckColumn(Specification pSpecification, TableColumn pCol, String pTableName,
                                           String pColumnName, String pDbName)
  {
    return _fix(pSpecification, pCol, pTableName, pColumnName, "CH", pDbName);
  }

  public static TableColumn fixUniqueConstraint(Specification pSpecification, TableColumn pCol, String pTableName,
                                                String pColumnName, String pDbName)
  {
    return _fix(pSpecification, pCol, pTableName, pColumnName, "UQ", pDbName);
  }

  public static TableColumn fixCheckConstraint(Specification pSpecification, TableColumn pCol, String pTableName,
                                               String pColumnName, String pDbName)
  {
    return _fix(pSpecification, pCol, pTableName, pColumnName, "CH", pDbName);
  }

  public static TableColumn fixPrimaryKeyConstraint(Specification pSpecification, TableColumn pCol, String pTableName,
                                                    String pColumnName, String pDbName)
  {
    return _fix(pSpecification, pCol, pTableName, pColumnName, "PK", pDbName);
  }

  public static TableColumn fixForeignKeyConstraint(Specification pSpecification, TableColumn pCol, String pTableName,
                                                    String pColumnName, String pDbName)
  {
    return _fix(pSpecification, pCol, pTableName, pColumnName, "FK", pDbName);
  }

  private static TableColumn _fix(Specification pSpecification, TableColumn pCol, String pTableName, String pColumnName,
                                  String pPostFix, String pDbName)
  {
    if (pDbName != null && pDbName.toLowerCase().contains("oracle"))
    {
      String name = getValidName(pTableName, pColumnName, pPostFix);
      pCol.setObjectName(name);
    }
    return pCol;
  }

  public static String getValidName(String pTableName, String pColumName, String pPostFix)
  {
    String name = pTableName + "_" + pColumName + "_" + pPostFix;
    if (name.length() > MAX_INDEX_NAME_LENGTH)
      name = "C" + Math.abs((pTableName + "_" + pColumName).hashCode()) + "_" + pPostFix;

    return name;
  }
}
