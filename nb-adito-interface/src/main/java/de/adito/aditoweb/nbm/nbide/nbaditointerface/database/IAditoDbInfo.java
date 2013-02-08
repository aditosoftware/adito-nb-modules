package de.adito.aditoweb.nbm.nbide.nbaditointerface.database;

import java.util.List;

/**
 * Interface f�r spezifische Daten die im NetBeans-Modul nicht bezogen werden k�nnen.
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
   * Liefert anhand der Namen von Treiber und Tabelle eine Beschreibung f�r diese Tabelle.
   *
   * @param pDriverName      Name des Treibers.
   * @param pSystemTableName Name der Systemtabelle.
   * @return IAditoDbTable
   */
  IAditoDbTable getTable(String pDriverName, String pSystemTableName);

  /**
   * Zeigt einen Ballon mit der �bergebenen Nachricht an.
   *
   * @param pMessage die anzuzeigende Nachricht.
   */
  void notify(String pMessage);

  /**
   * Registriert einen Fehler.
   *
   * @param pThrowable der Fehler.
   */
  void notifyException(Throwable pThrowable);

  /**
   * @return die Standardspalten die in Tabellen vorhanden sein sollen.
   */
  List<IAditoDbColumn> createDefaultSystemColumns(String pDriverName);

}
