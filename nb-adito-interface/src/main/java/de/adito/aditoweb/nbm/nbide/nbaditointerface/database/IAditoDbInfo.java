package de.adito.aditoweb.nbm.nbide.nbaditointerface.database;

import java.util.*;

/**
 * Interface für spezifische Daten die im NetBeans-Modul nicht bezogen werden können.
 *
 * @author J. Boesl, 14.01.13
 */
public interface IAditoDbInfo
{
  //Gruppennamen
  public final String OTHER = "OTHER";
  public final String CALENDAR = "CALENDAR";
  public final String FARM = "FARM";
  public final String MAILREPOSIT = "MAILREPOSIT";
  public final String WORKFLOW = "WORKFLOW";

  /**
   * @return die Namen aller SystemTabellen.
   */
  List<String> getSystemTableNames();

  /**
   * Liefert die Namen der Systemtabellen in Gruppen aufgeteilt.
   * Die Schlüssel der Map sind die Gruppennamen, die in diesem Interface definiert sind.
   * Die Liste enthält die Tabellennamen.
   *
   * @return Tabellennamen nach Gruppen geordnet.
   */
  Map<String, List<String>> getSystemTableNamesGrouped();

  /**
   * Liefert anhand der Namen von Treiber und Tabelle eine Beschreibung für diese Tabelle.
   *
   * @param pDriverName      Name des Treibers.
   * @param pSystemTableName Name der Systemtabelle.
   * @return IAditoDbTable
   */
  IAditoDbTable getTable(String pDriverName, String pSystemTableName);

  /**
   * Zeigt einen Ballon mit der übergebenen Nachricht an.
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
