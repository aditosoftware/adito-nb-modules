package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync;


import org.openide.nodes.*;

import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * @author J. Boesl, 16.05.11
 */
public interface IAditoPropertyProvider
{

  Sheet createSheet();

  List<String> getPropertyNames();

  Node.Property getProperty(String pPropertyName);

  void addPropertyListener(PropertyChangeListener pListener);

  void removePropertyListener(PropertyChangeListener pListener);

}
