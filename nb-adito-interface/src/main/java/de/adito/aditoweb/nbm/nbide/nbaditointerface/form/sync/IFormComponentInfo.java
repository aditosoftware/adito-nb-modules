package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync;


import org.jetbrains.annotations.Nullable;
import org.openide.nodes.*;
import org.openide.util.Lookup;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.*;

/**
 * Bietet Informationen für Form-Komponenten über Adito-Objekte an.
 *
 * @author J. Boesl, 16.05.11
 */
public interface IFormComponentInfo
{

  public static final String PROP_NAME_CHANGED = "propNameChanged";
  public static final String PROP_VALUE_CHANGED = "propValueChanged";
  public static final String PROP_CHILD_ADDED = "propChildAdded";
  public static final String PROP_CHILD_REMOVED = "propChildRemoved";
  public static final String PROP_POSITION_CHANGED = "propPositionChanged";

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

  /**
   * @return Node dieser IFormComponentInfo
   */
  Node getNode();

  /**
   * @return das DataObject-Lookup dieser IFormComponentInfo
   */
  Lookup getDataObjectLookup();

}
