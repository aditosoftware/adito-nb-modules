package de.adito.aditoweb.nbm.nbide.nbaditointerface;

import java.awt.*;

/**
 * @author J. Boesl, 11.05.11
 */
public interface IAditoAnchorLayoutComponentConstaints
{

  Rectangle getBounds();

  boolean isAnchorLeft();

  boolean isAnchorBottom();

  boolean isAnchorRight();

  boolean isAnchorTop();

  boolean isBordered();

}
