package de.adito.aditoweb.nbm.nbide.nbaditointerface;

import java.awt.*;

/**
 * @author J. Boesl, 11.05.11
 */
public interface INetbeansAditoInterface
{

  IAditoAnchorLayoutComponentConstaints createAditoAnchoLayoutComponentConstraints(
      Rectangle pBounds, boolean pAnchorLeft, boolean pAnchorBottom, boolean pAnchorRight, boolean pAnchorTop,
      boolean pIsBordered);

  Class getAditoAnchoLayoutClass();

}
