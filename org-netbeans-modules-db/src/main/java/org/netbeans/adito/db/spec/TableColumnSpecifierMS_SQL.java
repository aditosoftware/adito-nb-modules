package org.netbeans.adito.db.spec;

import java.util.Map;

/**
 * Behandelt die Spezifika vom MS-SQL-Server
 *
 * @author c.stadler, 07.11.2017
 */
public class TableColumnSpecifierMS_SQL implements ITableColumnSpecifier
{
  final static String DBMS_NAME = "Microsoft SQL Server"; // NOI18N // kommt aus der dbspec.plist stehen, unter "DatabaseProductName" beim "Microsoft SQL Server"

  @Override
  public Map optimizeColumnProps(Map<String, String> pProps)
  {
    return _fixVarcharMax(pProps);
  }

  /**
   * Der MS_SQL-Server akzeptiert f�r VARCHAR die L�ngenangabe 1..8000 und "max" f�r die L�nge 2147483647 (=> 2GB)
   * https://docs.microsoft.com/de-de/sql/t-sql/data-types/char-and-varchar-transact-sql
   *
   * L�ngenangaben ungleich 2147483647 werden ignoriert, es wird akzeptiert dass dadurch ein Fehler beim anlegen der Spate auftreten.
   * Weil: Die L�nge k�nnte beabsichtigt sein. Eine K�rzung der Spalte k�nnte dann beim einf�gen von Daten neue Probleme bereiten.
   * Da ist es sinnvoller, die Spalte nicht anzuelegen.
   * Im Falle von 2147483647 wird mit "max" nur ein Synonym verwendet und ver�ndert nicht die Spaltenl�nge.
   *
   * @param pProps Spaltenparameter
   * @return die optimierten Spaltenparameter
   */
  private Map<String, String> _fixVarcharMax(Map<String, String> pProps)
  {
    String type = pProps.get(COLUMN_TYPE);
    String size = pProps.get(COLUMN_SIZE);

    if (type == null || size == null)
      return pProps;

    // MSSQL nimmt die L�nge 2147483647 nicht und will stattdessen "max"
    if (type.equals("VARCHAR") && size.equals("2147483647"))  // NOI18N
    {
      pProps.put(COLUMN_SIZE, "max"); // NOI18N
    }

    return pProps;
  }
}
