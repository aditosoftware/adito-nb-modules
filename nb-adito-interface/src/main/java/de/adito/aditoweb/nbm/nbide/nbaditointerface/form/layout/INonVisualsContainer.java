package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout;

import java.awt.*;

/**
 * @author J. Boesl, 29.06.11
 */
public interface INonVisualsContainer
{

  void setName(String pName);

  /**
   * Komponente soll hergeschaltet werden, wenn sie versteckt sein kann. Kann sonst ignoriert werden.
   */
  void setActive();

  boolean addNonSwingComp(Object pComp);

  boolean removeNonSwingComp(Object pComp);

  Rectangle getChildBounds(Object pComp);

}
