package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout;

import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * @author J. Boesl, 29.06.11
 */
public interface INonSwingContainer
{

  @Nullable
  Rectangle getBoundsNonSwing();

  void executeMouseClick(MouseEvent e);

  @Nullable
  String getSubComponentName(MouseEvent e);

  boolean addCompNonSwing(INonSwingComponent pComp);

  boolean removeCompNonSwing(INonSwingComponent pComp);

}
