package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * @author J. Boesl, 29.06.11
 */
public interface INonSwingContainer
{

  Rectangle getBoundsNonSwing();

  void executeMouseClick(MouseEvent e);

  boolean addCompNonSwing(INonSwingComponent pComp);

  boolean removeCompNonSwing(INonSwingComponent pComp);

}
