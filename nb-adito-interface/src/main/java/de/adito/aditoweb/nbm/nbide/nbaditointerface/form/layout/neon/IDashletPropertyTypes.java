package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.neon;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.common.IAditoLayoutPropertyType;

/**
 * Repräsentiert Position und Ausdehnung eines Dashlets
 * im zugehörigen Gitter-Layout.
 * Position und Ausdehnung sind Gitter-Einheiten.
 */
public interface IDashletPropertyTypes
{
  /**
   * Koordinatenursprung auf der X-Achse.
   * x=1 wäre der Ursprung in der 2. Gitterzelle.
   */
  IAditoLayoutPropertyType<Integer> xPos();

  /**
   * Koordinatenursprung auf der Y-Achse.
   * y=0 wäre der Ursprung in der obersten Gitterzelle.
   */
  IAditoLayoutPropertyType<Integer> yPos();

  /**
   * Ausdehnung entlang der X-Achse relativ zum Ursprung in x.
   * colspan=3 -> das Dashlet überdeckt 3 Gitterzellen horizontal
   */
  IAditoLayoutPropertyType<Integer> colspan();

  /**
   * Ausdehnung entlang der Y-Achse relativ zum Ursprung in y.
   * rowspan=6 -> das Dashlet überdeckt 6 Gitterzellen vertikal.
   */
  IAditoLayoutPropertyType<Integer> rowspan();
}
