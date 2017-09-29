package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.neon;

import javax.swing.*;
import java.awt.*;

/**
 * Ein Komponentencontainer liefert anhand einer Positionsangabe
 * den Einf�gebereich f�r die Komponente zur�ck.
 *
 * @author Thomas Tasior 18.04.2016, 13:42
 */
public interface IDropAreaSupport
{
  /**
   * @param pPosInCont X/Y Koordinaten auf einem Komponentencontainer.
   * @param pComponent die einzuf�gende Komponente
   * @param pSizeChanges zeigt an, welche Kante einer Komponente mit der Maus
   *                    verschoben wird. Ist null, wenn die gesamte Komponente
   *                    verschoben wird.
   * @param pPosInComp  X/Y Koordinaten in einer Komponente.
   * @return Einf�geinformationen f�r einen Container.
   */
  IDropArea getDropArea(Point pPosInCont, JComponent pComponent, Insets pSizeChanges, Point pPosInComp);
}
