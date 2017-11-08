package org.netbeans.adito.db.spec;

import java.util.Map;

/**
 * Definiert Methoden um DBMS-spezifisches Verhalten abzubilden
 * Hier wird alles erledigt, was nicht in der Datei dbspec.plist hinterlegt werden kann.
 * Die Entities im AliasDefinitionEditor müssen DBMS-unabhängig sein.
 *
 * @author c.stadler, 07.11.2017
 */
public interface ITableColumnSpecifier
{
  String COLUMN_TYPE = "column.type"; // NOI18N // org/netbeans/lib/ddl/impl/TableColumn.java:225
  String COLUMN_SIZE = "column.size"; // NOI18N // org/netbeans/lib/ddl/impl/TableColumn.java:203

  Map optimizeColumnProps(Map<String, String> pProps);
}
