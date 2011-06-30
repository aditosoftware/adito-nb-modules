package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout;

import java.beans.PropertyChangeListener;

/**
 * @author J. Boesl, 29.06.11
 */
public interface INonVisualLayoutComponent
{

  public void addNonVisComp(Object pNonVisualComponent);

  public void removeNonVisComp(Object pNonVisualComponent);

  public void addPropertyChangeListener(PropertyChangeListener pPropertyChangeListener);

}
