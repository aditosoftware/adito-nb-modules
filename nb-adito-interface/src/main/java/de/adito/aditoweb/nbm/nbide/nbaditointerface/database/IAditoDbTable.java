package de.adito.aditoweb.nbm.nbide.nbaditointerface.database;

import java.util.List;

/**
 * Liefert den Namen und die Spalten einer Tabelle f�r eine Datenbank.
 *
 * @author J. Boesl, 14.01.13
 */
public interface IAditoDbTable
{

  /**
   * @return Name.
   */
  String getName();

  /**
   * @return die Spalten.
   */
  List<IAditoDbColumn> getColumns();

  /**
   * Liefert die Prim�rschl�ssel dieser Tabelle.
   * @return in jedem Fall eine Liste.
   */
  List<IAditoDbColumn> getPrimaryKeyColumns();
}
