package org.netbeans.adito.db.spec;

import org.netbeans.lib.ddl.DatabaseSpecification;

/**
 * Erzeugt die Specifier und stellt diese bereit.
 *
 * @author c.stadler, 07.11.2017
 */
public class TableColumnSpecifierFactory
{
  private static ITableColumnSpecifier def;   // StandardSpecifier, ändert nichts und gibt die Props einfach zurück
  private static ITableColumnSpecifier mssql; // Specifier für MS-SQL-Server

  static
  {
    def = pProps -> pProps;
    mssql = null;
  }

  static ITableColumnSpecifier getTableColumnSpecifier(String pDbName)
  {
    switch (pDbName)
    {
      case TableColumnSpecifierMS_SQL.DBMS_NAME:
      {
        if (mssql == null)
          mssql = new TableColumnSpecifierMS_SQL();
        return mssql;
      }
      default:
        return def;
    }
  }

  public static ITableColumnSpecifier getTableColumnSpecifier(DatabaseSpecification pDbSpec)
  {
    if (pDbSpec == null)
      return def;

    String dbName = (String) pDbSpec.getProperties().get("DatabaseProductName");  // NOI18N
    return getTableColumnSpecifier(dbName);
  }
}
