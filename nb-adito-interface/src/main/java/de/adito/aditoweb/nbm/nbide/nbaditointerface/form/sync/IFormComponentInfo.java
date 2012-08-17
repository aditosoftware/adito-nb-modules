package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync;


import org.jetbrains.annotations.Nullable;
import org.openide.nodes.*;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.*;

/**
 * @author J. Boesl, 16.05.11
 */
public interface IFormComponentInfo
{

  public static final String PROP_VALUE_CHANGED = "propValueChanged";
  public static final String PROP_CHILD_ADDED = "propChildAdded";
  public static final String PROP_CHILD_REMOVED = "propChildRemoved";

  Sheet createSheet();

  Map<String, Object> getInitialValues();

  EContainerType getContainerType();

  Class<?> getLayoutClass();

  LayoutManager createLayout();

  Object createConstraints();

  /**
   * @return statische Informationen über die durch das FileObject identifizierte Komponente.
   */
  @Nullable
  IFormComponentPropertyMapping getFormPropertyMapping();

  List<String> getPropertyNames();

  Node.Property getProperty(String pPropertyName);

  void addPropertyListener(PropertyChangeListener pListener);

  void removePropertyListener(PropertyChangeListener pListener);

}
