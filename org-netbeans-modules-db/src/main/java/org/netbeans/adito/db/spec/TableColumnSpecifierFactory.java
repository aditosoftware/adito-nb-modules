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
    mssql = new TableColumnSpecifierMS_SQL();
  }

  public static ITableColumnSpecifier getTableColumnSpecifier(DatabaseSpecification pDbSpec)
  {
    if (pDbSpec == null)
      return def;

    String dbName = (String) pDbSpec.getProperties().get("DatabaseProductName");  // NOI18N
    switch (dbName)
    {
      case TableColumnSpecifierMS_SQL.DBMS_NAME:
        return mssql;
      default:
        return def;
    }
  }
}
