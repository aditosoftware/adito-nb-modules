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

  private OracleTableColumnHack()
  {
  }

  public static TableColumn fixUniqueColumn(Specification pSpecification, TableColumn pCol, String pTableName,
                                            String pColumnName)
  {
    return _fix(pSpecification, pCol, pTableName, pColumnName, "UQ");
  }

  public static TableColumn fixPrimaryKeyColumn(Specification pSpecification, TableColumn pCol, String pTableName,
                                                String pColumnName)
  {
    return _fix(pSpecification, pCol, pTableName, pColumnName, "PK");
  }

  public static TableColumn fixForeignKeyColumn(Specification pSpecification, TableColumn pCol, String pTableName,
                                                String pColumnName)
  {
    return _fix(pSpecification, pCol, pTableName, pColumnName, "FK");
  }

  public static TableColumn fixCheckColumn(Specification pSpecification, TableColumn pCol, String pTableName,
                                           String pColumnName)
  {
    return _fix(pSpecification, pCol, pTableName, pColumnName, "CH");
  }

  public static TableColumn fixUniqueConstraint(Specification pSpecification, TableColumn pCol, String pTableName,
                                                String pColumnName)
  {
    return _fix(pSpecification, pCol, pTableName, pColumnName, "UQ");
  }

  public static TableColumn fixCheckConstraint(Specification pSpecification, TableColumn pCol, String pTableName,
                                               String pColumnName)
  {
    return _fix(pSpecification, pCol, pTableName, pColumnName, "CH");
  }

  public static TableColumn fixPrimaryKeyConstraint(Specification pSpecification, TableColumn pCol, String pTableName,
                                                    String pColumnName)
  {
    return _fix(pSpecification, pCol, pTableName, pColumnName, "PK");
  }

  public static TableColumn fixForeignKeyConstraint(Specification pSpecification, TableColumn pCol, String pTableName,
                                                    String pColumnName)
  {
    return _fix(pSpecification, pCol, pTableName, pColumnName, "FK");
  }

  private static TableColumn _fix(Specification pSpecification, TableColumn pCol, String pTableName, String pColumnName,
                                  String pPostFix)
  {
    String db = pSpecification.getConnection().getDatabase();
    if (db != null && db.toLowerCase().contains("oracle"))
    {
      String name = pTableName + "_" + pColumnName + "_" + pPostFix;
      if (name.length() > 30)
        name = "C" + Math.abs((pTableName + "_" + pColumnName).hashCode()) + "_" + pPostFix;
      pCol.setObjectName(name);
    }
    return pCol;
  }

}
