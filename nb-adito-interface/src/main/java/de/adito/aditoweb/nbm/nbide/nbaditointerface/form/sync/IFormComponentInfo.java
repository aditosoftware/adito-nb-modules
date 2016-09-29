package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync;


import org.jetbrains.annotations.*;
import org.openide.nodes.Node;
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

  String PROP_NAME_CHANGED = "propNameChanged";
  String PROP_VALUE_CHANGED = "propValueChanged";
  String PROP_CHILD_ADDED = "propChildAdded";
  String PROP_CHILD_REMOVED = "propChildRemoved";
  String PROP_POSITION_CHANGED = "propPositionChanged";


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

  String getAditoPropName(String pRadPropName);

  String getRadPropName(String pAditoPropName);

  String getPropContext(@NotNull String pPropertyName);

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
