package de.adito.aditoweb.nbm.nbide.nbaditointerface.database;

import org.jetbrains.annotations.*;

import java.sql.Connection;

/**
 * Implementierungen erstellen für eine Tabelle vordefinierte Standardeinträge.
 *
 * @author J. Boesl, 12.02.13
 */
public interface IAditoDbTableCreator
{

  /**
   * Erstellt optional für eine Tabelle, beschrieben durch ihren Namen, Standardeinträge.
   *
   * @param pConnection benötigt, um etwas in die Datenbank zu schreiben.
   * @param pSchema     ein optionales Schema, in dem sich die Tabelle befindet.
   * @param pTableName  der Name der Tabelle, die evtl. mit Standardeinträge angereichert wird.
   */
  void createDefaultEntries(@NotNull Connection pConnection, @Nullable String pSchema, @NotNull String pTableName);

}
