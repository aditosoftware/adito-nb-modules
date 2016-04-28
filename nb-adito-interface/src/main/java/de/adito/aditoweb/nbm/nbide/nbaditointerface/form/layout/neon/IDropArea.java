package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.neon;

import java.awt.*;

/**
 * Beschreibt einen Bereich auf einem GUI Container,
 * auf dem eine Komponente platziert werden kann.
 * @see de.adito.aditoweb.system.crmcomponents.datamodels.CellDataModel
 * @author Thomas Tasior 19.04.2016, 08:15
 */
public interface IDropArea
{
  /**
   * Kann benutzt werden um den Einfügebereich optisch zu markieren.
   * @return immer ein Rechteck.
   */
  Rectangle getPaintingRect();

  /**
   * Gibt an in welcher Zelle entlang der X-Achse eines Layouts die Komponente beginnt.
   * @return 0 oder eine positve Zahl;
   */
  int startX();

  /**
   * Gibt an in welcher Zelle entlang der Y-Achse eines Layouts die Komponente beginnt.
   *
   * @return 0 oder eine positve Zahl;
   */
  int startY();

  /**
   * Gibt an in welcher Zelle entlang der X-Achse eines Layouts die Komponente endet.
   *
   * @return 0 oder eine positve Zahl;
   */
  int endX();

  /**
   * Gibt an in welcher Zelle entlang der Y-Achse eines Layouts die Komponente endet.
   *
   * @return 0 oder eine positve Zahl;
   */
  int endY();

  /**
   * Liefert die Position einer Komponente innerhalb der okkupierten Zellen.
   *
   * @return eine der EJustify Konstanten.
   * @see Justify
   */
  String justify();
}
