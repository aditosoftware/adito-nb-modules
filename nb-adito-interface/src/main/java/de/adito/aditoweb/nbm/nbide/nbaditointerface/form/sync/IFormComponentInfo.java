package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync;


import org.jetbrains.annotations.*;
import org.openide.nodes.Node;
import org.openide.util.Lookup;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.*;

/**
 * Bietet Informationen f�r Form-Komponenten �ber Adito-Objekte an.
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
  String PROP_SET_CHANGED = "propSetChanged";

  String IS_MIXIN_PROPERTY = "isMixinProperty";

  Map<String, Object> getInitialValues();

  EContainerType getContainerType();

  Class<?> getLayoutClass();

  LayoutManager createLayout();

  Object createConstraints();

  /**
   * @return statische Informationen �ber die durch das FileObject identifizierte Komponente.
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
   * Gibt die Attribute f�r ein beliebiges Property in Form einer Map zur�ck z.B. isMixin
   * @param pPropertyName der Name des Properties
   * @return die Attribute in Form einer Map
   */
  @Nullable
  Map<Object, Object> getPropertyAttributes(String pPropertyName);

  /**
   * @return Node dieser IFormComponentInfo
   */
  Node getNode();

  /**
   * @return das DataObject-Lookup dieser IFormComponentInfo
   */
  Lookup getDataObjectLookup();

  /**
   * Gibt zur�ck, ob dieser Container mit "Design this container" editiert werden kann
   */
  boolean isDesignableContainer();

}
