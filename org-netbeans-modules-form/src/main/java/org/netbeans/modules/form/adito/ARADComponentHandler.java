package org.netbeans.modules.form.adito;

import de.adito.aditoweb.designer.filetype.PropertiesCookie;
import org.netbeans.modules.form.*;
import org.openide.loaders.DataFolder;
import org.openide.nodes.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author J. Boesl, 17.02.11
 */
public class ARADComponentHandler
{

  private DataFolder modelDataObject;

  public ARADComponentHandler(DataFolder pModelDataObject)
  {
    modelDataObject = pModelDataObject;
  }

  public DataFolder getModelDataObject()
  {
    return modelDataObject;
  }

  public void initRADComponent(RADComponent pRADComponent) throws InvocationTargetException, IllegalAccessException
  {
    if (modelDataObject != null)
    {
      PropertiesCookie propsCookie = modelDataObject.getCookie(PropertiesCookie.class);
      if (propsCookie != null)
      {
        Sheet sheet = new Sheet();
        propsCookie.applyAditoPropertiesSync(sheet);
        Node.PropertySet[] sets = sheet.toArray();
        for (Node.PropertySet set : sets)
        {
          for (Node.Property prop : set.getProperties())
          {
            RADProperty beanProperty = pRADComponent.getBeanProperty(prop.getName());
            if (beanProperty != null)
              beanProperty.setValue(prop.getValue());
          }
        }
      }
    }
  }

  public void createPropertySets(RADComponent pRADComponent, List<Node.PropertySet> propSets)
  {
    if (modelDataObject != null)
    {
//          return modelDataObject.getNodeDelegate().getPropertySets(); // klappt nicht weil asynchron!
      PropertiesCookie propsCookie = modelDataObject.getCookie(PropertiesCookie.class);
      if (propsCookie != null)
      {
        Sheet sheet = new Sheet();
        propsCookie.applyAditoPropertiesSync(sheet);
        Node.PropertySet[] sets = sheet.toArray();
        for (Node.PropertySet set : sets)
        {
          Sheet.Set setCopy = new Sheet.Set();
          setCopy.setName(set.getName());
          setCopy.setDisplayName(set.getDisplayName());
          setCopy.setShortDescription(set.getShortDescription());
          if (set.getValue("tabName") != null)
            setCopy.setValue("tabName", set.getValue("tabName"));
          for (Node.Property property : set.getProperties())
          {
            Node.Property found = pRADComponent.getPropertyByName(property.getName());
            if (found != null)
            {
              setCopy.put(AConnectionProperty.create(property, found));
            }
            else
              setCopy.put(property);
          }
          propSets.add(setCopy);
        }
      }
    }
  }
}
