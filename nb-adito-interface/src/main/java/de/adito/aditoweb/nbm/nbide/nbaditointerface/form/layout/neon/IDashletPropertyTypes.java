package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.neon;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.common.IAditoLayoutPropertyType;

/**
 * Repr�sentiert Position und Ausdehnung eines Dashlets
 * im zugeh�rigen Gitter-Layout.
 * Position und Ausdehnung sind Gitter-Einheiten.
 */
public interface IDashletPropertyTypes
{
  /**
   * Koordinatenursprung auf der X-Achse.
   * x=1 w�re der Ursprung in der 2. Gitterzelle.
   */
  IAditoLayoutPropertyType<Integer> xPos();

  /**
   * Koordinatenursprung auf der Y-Achse.
   * y=0 w�re der Ursprung in der obersten Gitterzelle.
   */
  IAditoLayoutPropertyType<Integer> yPos();

  /**
   * Ausdehnung entlang der X-Achse relativ zum Ursprung in x.
   * colspan=3 -> das Dashlet �berdeckt 3 Gitterzellen horizontal
   */
  IAditoLayoutPropertyType<Integer> colspan();

  /**
   * Ausdehnung entlang der Y-Achse relativ zum Ursprung in y.
   * rowspan=6 -> das Dashlet �berdeckt 6 Gitterzellen vertikal.
   */
  IAditoLayoutPropertyType<Integer> rowspan();
}
