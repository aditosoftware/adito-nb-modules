package de.adito.aditoweb.nbm.nbide.nbaditointerface.database;

import org.jetbrains.annotations.*;

import java.sql.Connection;

/**
 * Implementierungen erstellen f�r eine Tabelle vordefinierte Standardeintr�ge.
 *
 * @author J. Boesl, 12.02.13
 */
public interface IAditoDbTableCreator
{

  /**
   * Erstellt optional f�r eine Tabelle, beschrieben durch ihren Namen, Standardeintr�ge.
   *
   * @param pConnection ben�tigt, um etwas in die Datenbank zu schreiben.
   * @param pSchema     ein optionales Schema, in dem sich die Tabelle befindet.
   * @param pTableName  der Name der Tabelle, die evtl. mit Standardeintr�ge angereichert wird.
   */
  void createDefaultEntries(@NotNull Connection pConnection, @Nullable String pSchema, @NotNull String pTableName);

}
