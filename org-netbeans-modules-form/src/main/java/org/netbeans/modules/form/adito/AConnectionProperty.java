package org.netbeans.modules.form.adito;

import org.openide.nodes.*;

import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author J. Boesl, 15.02.11
 */
public class AConnectionProperty extends PropertySupport.ReadWrite
{

  private Node.Property aditoProperty;
  private Node.Property beanProperty;


  private AConnectionProperty(Node.Property pAditoProperty, Node.Property pBeanProperty)
  {
    super(pAditoProperty.getName(), pAditoProperty.getValueType(), pAditoProperty.getDisplayName(),
          pAditoProperty.getShortDescription());
    aditoProperty = pAditoProperty;
    beanProperty = pBeanProperty;
  }

  @Override
  public boolean canRead()
  {
    return aditoProperty.canRead();
  }

  @Override
  public boolean canWrite()
  {
    return aditoProperty.canRead();
  }

  @Override
  public Object getValue() throws IllegalAccessException, InvocationTargetException
  {
    return aditoProperty.getValue();
  }

  @Override
  public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
  {
    aditoProperty.setValue(val);
    beanProperty.setValue(val);
  }

  @Override
  public Class getValueType()
  {
    return aditoProperty.getValueType();
  }

  @Override
  public boolean supportsDefaultValue()
  {
    return aditoProperty.supportsDefaultValue();
  }

  @Override
  public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException
  {
    aditoProperty.restoreDefaultValue();
  }

  @Override
  public boolean isDefaultValue()
  {
    return aditoProperty.isDefaultValue();
  }

  @Override
  public String getHtmlDisplayName()
  {
    return aditoProperty.getHtmlDisplayName();
  }

  @Override
  public PropertyEditor getPropertyEditor()
  {
    return aditoProperty.getPropertyEditor();
  }


  public static AConnectionProperty create(Node.Property pAdito, Node.Property pBean)
  {
    AConnectionProperty property = new AConnectionProperty(pAdito, pBean);
    return property;
  }

}
