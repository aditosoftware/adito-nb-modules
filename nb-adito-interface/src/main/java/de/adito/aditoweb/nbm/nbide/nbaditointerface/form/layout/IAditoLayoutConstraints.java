package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout;

import org.openide.nodes.Node;

import java.util.*;

/**
 * @author J. Boesl, 20.05.11
 */
public interface IAditoLayoutConstraints<TypeInfo>
{

  Collection<Node.Property> getProperties();

  <T> Node.Property<T> get(IAditoLayoutPropertyType<T> pType);

  <T> T getValue(IAditoLayoutPropertyType<T> pType);

  <T> void setValue(IAditoLayoutPropertyType<T> pType, T pValue);

  TypeInfo getTypeInfo();

  IAditoLayoutConstraints<TypeInfo> cloneConstraints();

}
