package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout;

import java.awt.*;

/**
 * @author J. Boesl, 05.08.11
 */
public interface INonSwingComponent extends INonSwingContainer
{

  /**
   * Komponente soll hergeschaltet werden, wenn sie versteckt sein kann. Kann sonst ignoriert werden.
   */
  void setActive();

  void paintSelection(Graphics2D pG);

}
