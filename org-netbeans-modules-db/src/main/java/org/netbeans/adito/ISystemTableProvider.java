package org.netbeans.adito;

/**
 * @author Thomas Tasior 20.12.12, 12:18
 */
public interface ISystemTableProvider
{
  /**
   * Liefert die Systemtabellen anhand des Treibernamens.
   *
   * @param pDriverName der Treibername.
   * @return die Systemtabellen
   * @throws IllegalArgumentException wenn dabe ein Fehler auftritt.
   */
  ISystemTables get(String pDriverName) throws IllegalArgumentException;

  /**
   * Zeigt einen Ballon mit der übergebenen Nachricht an.
   * @param pMessage die anzuzeigende Nachricht.
   */
  void notify(String pMessage);
}
