package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.neon;

import java.awt.*;

/**
 * Ein Komponentencontainer liefert anhand einer Positionsangabe
 * den Einfügebereich für die Komponente zurück.
 *
 * @author Thomas Tasior 18.04.2016, 13:42
 */
public interface INeonFrame
{
  /**
   * Ein Aufrufer liefert eine Position im Container und
   * erhält dafür Einfügeinformationen.
   * @param pPosInCont X/Y Koordinaten auf einem Komponentencontainer.
   *
   * @return Einfügeinformationen für einen Container.
   */
  IDropArea getDropArea(Point pPosInCont);
}
