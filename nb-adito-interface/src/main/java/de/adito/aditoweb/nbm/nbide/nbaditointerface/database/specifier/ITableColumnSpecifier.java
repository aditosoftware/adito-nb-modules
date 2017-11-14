package de.adito.aditoweb.nbm.nbide.nbaditointerface.database.specifier;

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

  default Map<String, String> optimizeColumnProps(Map<String, String> pProps)
  {
    return pProps;
  }

  default int getDisplySize(int pTypeCode, int pDisplaySize, int pPrecision)
  {
    return pDisplaySize;
  }

  default int getPrecision(int pTypeCode, int pDisplaySize, int pPrecision)
  {
    return pPrecision;
  }

  default String getTypeString(int pTypeCode, String pTypeString)
  {
    return pTypeString;
  }

  default int getTypeCode(int pTypeCode, String pTypeString)
  {
    return pTypeCode;
  }

  default Map<String, String> reduceTypeMap(Map<String, String> pTypeMap) { return pTypeMap; }
}
