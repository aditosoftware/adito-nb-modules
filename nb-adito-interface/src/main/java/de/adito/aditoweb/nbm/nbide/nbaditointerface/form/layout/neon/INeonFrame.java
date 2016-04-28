package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.neon;

import java.awt.*;

/**
 * Ein Komponentencontainer liefert anhand einer Positionsangabe
 * den Einf�gebereich f�r die Komponente zur�ck.
 *
 * @author Thomas Tasior 18.04.2016, 13:42
 */
public interface INeonFrame
{
  /**
   * Ein Aufrufer liefert eine Position im Container und
   * erh�lt daf�r Einf�geinformationen.
   * @param pPosInCont X/Y Koordinaten auf einem Komponentencontainer.
   *
   * @return Einf�geinformationen f�r einen Container.
   */
  IDropArea getDropArea(Point pPosInCont);
}
