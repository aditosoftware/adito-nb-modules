package org.netbeans.adito;

/**
 * Eine Implementierung definiert die DB spezifischen Tabellen.
 * @author Thomas Tasior 20.12.12, 12:15
 */
public interface ISystemTables
{
  /**
   * Liefert ein Tabellenmodell mit dem entsprechenden Schlüssel.
   *
   * @param pSystemTable der Schlüssel.
   * @return das Tabellenmodell.
   */
  public TableModel getTable(ESystemTable pSystemTable);


}
