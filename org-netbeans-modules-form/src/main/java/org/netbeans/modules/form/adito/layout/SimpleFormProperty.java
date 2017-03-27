package org.netbeans.modules.form.adito.layout;

import org.jetbrains.annotations.Nullable;
import org.netbeans.modules.form.FormProperty;
import org.openide.nodes.Node;

import java.lang.reflect.InvocationTargetException;


/**
 * @author J. Boesl, 20.05.11
 */
public class SimpleFormProperty extends FormProperty
{
  private Node.Property<Object> property;

  public SimpleFormProperty(Node.Property<Object> pProperty)
  {
    this(pProperty, null);
  }

  public SimpleFormProperty(Node.Property<Object> pProperty, @Nullable String pPostfix)
  {
    super(pProperty.getName() + (pPostfix == null ? "" : pPostfix),
          pProperty.getValueType(), pProperty.getDisplayName(),
          pProperty.getShortDescription());
    property = pProperty;
  }

  @Override
  public Object getTargetValue() throws IllegalAccessException, InvocationTargetException
  {
    return property.getValue();
  }

  @Override
  public void setTargetValue(Object value) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
  {
    property.setValue(value);
  }

  @Override
  public String toString()
  {
    return "SimpleFormProperty{" +
        "property=" + property +
        "} " + super.hashCode();
  }
}
