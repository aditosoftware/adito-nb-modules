package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout;

import java.awt.*;

/**
 * @author J. Boesl, 29.06.11
 */
public interface INonVisualLayoutComponent
{

  void addNonVisComp(Object pNonVisualComponent);

  void removeNonVisComp(Object pNonVisualComponent);

  Rectangle getBounds(Object pNonVisualComponent);

}
