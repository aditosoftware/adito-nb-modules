package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync;


import org.openide.nodes.*;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.List;

/**
 * @author J. Boesl, 16.05.11
 */
public interface IFormComponentInfo
{

  Sheet createSheet();

  Map<String, Object> getInitialValues();

  EContainerType getContainerType();

  Class<?> getParentLayoutClass();

  Class<?> getLayoutClass();

  LayoutManager createLayout();

  Object createConstraints();

  /**
   * @return statische Informationen über die durch das FileObject identifizierte Komponente.
   */
  IFormComponentPropertyMapping getFormPropertyMapping();

  List<String> getPropertyNames();

  Node.Property getProperty(String pPropertyName);

  void addPropertyListener(PropertyChangeListener pListener);

  void removePropertyListener(PropertyChangeListener pListener);

}
