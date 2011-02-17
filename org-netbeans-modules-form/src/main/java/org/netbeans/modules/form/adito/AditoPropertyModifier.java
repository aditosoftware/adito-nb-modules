package org.netbeans.modules.form.adito;

import de.adito.aditoweb.core.util.debug.Debug;
import de.adito.aditoweb.designer.filetype.PropertiesCookie;
import org.netbeans.modules.form.*;
import org.openide.loaders.DataFolder;
import org.openide.nodes.*;
import org.openide.util.lookup.ServiceProvider;

import java.beans.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author J. Boesl, 15.02.11
 */
@ServiceProvider(service = PropertyModifier.class)
public class AditoPropertyModifier implements PropertyModifier
{

  @Override
  public boolean modifyProperties(RADComponent metacomp, List<RADProperty> prefProps, List<RADProperty> normalProps,
                                  List<RADProperty> expertProps)
  {
//    boolean modified = _modifiyProperty(metacomp, prefProps);
//    modified |= _modifiyProperty(metacomp, normalProps);
//    modified |= _modifiyProperty(metacomp, expertProps);
//    return modified;
    return false;
  }

  private boolean _modifiyProperty(RADComponent pMetacomp, List<RADProperty> pProperties)
  {
    boolean modified = false;
    List<RADProperty> loopList = new ArrayList<RADProperty>();
    loopList.addAll(pProperties);
    for (RADProperty prop : loopList)
    {
      RADProperty modifiedProperty = _modifiyProperty(pMetacomp, prop);
      if (modifiedProperty != null)
      {
        int index = pProperties.indexOf(prop);
        pProperties.set(index, modifiedProperty);
        modified = true;
      }
    }
    return modified;
  }

  private RADProperty _modifiyProperty(RADComponent pMetacomp, RADProperty pProperty)
  {
    if (pProperty.getName().equals("bgColor"))
    {
      System.out.print("");
    }
    ARADComponentHandler aRADComponentHandler = pMetacomp.getaRADComponentHandler();
    if (aRADComponentHandler != null)
    {
      DataFolder modelDataObject = aRADComponentHandler.getModelDataObject();
      if (modelDataObject != null)
      {
        PropertiesCookie cookie = modelDataObject.getCookie(PropertiesCookie.class);
        if (cookie != null)
        {
          Sheet sheet = new Sheet();
          cookie.applyAditoPropertiesSync(sheet);
          Node.PropertySet[] propertySets = sheet.toArray();
          for (Node.PropertySet propertySet : propertySets)
          {
            Node.Property<?>[] properties = propertySet.getProperties();
            for (Node.Property<?> prop : properties)
            {
              if (prop.getName().equals(pProperty.getName()))
              {
                try
                {
                  return _createProperty(pMetacomp, prop, pProperty);
                }
                catch (Exception e)
                {
                  throw new RuntimeException(e);
                }
              }
            }
          }
        }
      }
    }
    return null;
  }

  private RADProperty _createProperty(RADComponent pMetacomp, final Node.Property pAditoProp,
                                      final RADProperty pRadProperty)
      throws IntrospectionException, InvocationTargetException, IllegalAccessException
  {
    RADProperty radProperty = new RADProperty(pMetacomp, new FakePropertyDescriptor(pAditoProp.getName(),
                                                                                    pAditoProp.getValueType()))
    {
      @Override
      public Object getTargetValue() throws IllegalAccessException, InvocationTargetException
      {
        return pRadProperty.getValue();
      }

      @Override
      public void setTargetValue(Object value) throws IllegalAccessException, IllegalArgumentException,
          InvocationTargetException
      {
        pAditoProp.setValue(value);
        pRadProperty.setValue(value);
      }

      @Override
      public boolean supportsDefaultValue()
      {
        return pAditoProp.supportsDefaultValue();
      }

      @Override
      public PropertyEditor getExpliciteEditor()
      {
        return pAditoProp.getPropertyEditor();
      }

      @Override
      public PropertyEditor getPropertyEditor()
      {
        return pAditoProp.getPropertyEditor();
      }

      @Override
      public boolean isDefaultValue()
      {
        return pAditoProp.isDefaultValue();
      }

      @Override
      public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException
      {
        pAditoProp.restoreDefaultValue();
      }
    };
//    radProperty.setAccessType(RADProperty.DETACHED_READ | RADProperty.DETACHED_WRITE);
    radProperty.setDisplayName(pAditoProp.getDisplayName());
    radProperty.setShortDescription(pAditoProp.getShortDescription());
    radProperty.setValue(pAditoProp.getValue());
    Debug.write("created for", pAditoProp.getName()); // DEBUG: remove it!
    return radProperty;

  }


  static class FakePropertyDescriptor extends PropertyDescriptor
  {
    Class propType;

    FakePropertyDescriptor(String name, Class type) throws IntrospectionException
    {
      super(name, null, null);
      propType = type;
    }

    @Override
    public Class getPropertyType()
    {
      return propType;
    }
  }

}
