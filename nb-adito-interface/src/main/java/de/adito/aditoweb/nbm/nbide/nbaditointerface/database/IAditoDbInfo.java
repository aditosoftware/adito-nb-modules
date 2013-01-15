package de.adito.aditoweb.nbm.nbide.nbaditointerface.database;

import java.util.List;

/**
 * Interface für
 *
 * @author J. Boesl, 14.01.13
 */
public interface IAditoDbInfo
{

  /**
   * @return die Namen aller SystemTabellen.
   */
  String[] getSystemTableNames();

  /**
   * Liefert anhand der Namen von Treiber und Tabelle eine Beschreibung für diese Tabelle.
   *
   * @param pDriverName      Name des Treibers.
   * @param pSystemTableName Name der Systemtabelle.
   * @return
   */
  IAditoDbTable getTable(String pDriverName, String pSystemTableName);

  /**
   * Zeigt einen Ballon mit der übergebenen Nachricht an.
   *
   * @param pMessage die anzuzeigende Nachricht.
   */
  void notify(String pMessage);

  List<IAditoDbColumn> createDefaultSystemColumns();

}
