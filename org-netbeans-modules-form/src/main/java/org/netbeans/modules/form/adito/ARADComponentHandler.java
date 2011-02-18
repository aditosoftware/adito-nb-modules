package org.netbeans.modules.form.adito;

import de.adito.aditoweb.core.util.Utility;
import de.adito.aditoweb.designer.filetype.PropertiesCookie;
import de.adito.aditoweb.filesystem.datamodelfs.access.DataAccessHelper;
import de.adito.aditoweb.filesystem.datamodelfs.access.mechanics.field.IFieldAccess;
import org.netbeans.modules.form.*;
import org.openide.filesystems.*;
import org.openide.loaders.DataFolder;
import org.openide.nodes.*;

import java.beans.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author J. Boesl, 17.02.11
 */
public class ARADComponentHandler
{

  private RADComponent radComponent;
  private DataFolder modelDataObject;

  private FileChangeListener fileChangeListener;


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
    if (modelDataObject == null)
      return;

    if (radComponent != null)
      throw new RuntimeException("Can't init with: " + pRADComponent + ". Another component is already set: "
                                     + radComponent + ".");
    radComponent = pRADComponent;

    _copyProperties();

    _registerListeners();
  }

  public void createPropertySets(List<Node.PropertySet> propSets)
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
//        for (Node.PropertySet set : sets)
//        {
//          Sheet.Set setCopy = new Sheet.Set();
//          setCopy.setName(set.getName());
//          setCopy.setDisplayName(set.getDisplayName());
//          setCopy.setShortDescription(set.getShortDescription());
//          if (set.getValue("tabName") != null)
//            setCopy.setValue("tabName", set.getValue("tabName"));
//          for (Node.Property property : set.getProperties())
//          {
//            Node.Property found = pRADComponent.getPropertyByName(property.getName());
//            if (found != null)
//            {
//              setCopy.put(AConnectionProperty.create(property, found));
//            }
//            else
//              setCopy.put(property);
//          }
//          propSets.add(setCopy);
//        }
        propSets.addAll(Arrays.asList(sets));
      }
    }
  }

  private void _copyProperties() throws InvocationTargetException, IllegalAccessException
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
          RADProperty beanProperty = radComponent.getBeanProperty(prop.getName());
          if (beanProperty != null)
            beanProperty.setValue(prop.getValue());
        }
      }
    }
  }

  private void _registerListeners()
  {
    for (FileObject fo : modelDataObject.getPrimaryFile().getChildren())
    {
      String propertyName = fo.getNameExt();
      RADProperty beanProperty = radComponent.getBeanProperty(propertyName);
      if (beanProperty != null)
      {
        beanProperty.addPropertyChangeListener(_createBeanPropertyChangeListener(propertyName));
        fo.addFileChangeListener(_getFileChangeListener());
      }
    }
    radComponent.getFormModel().addFormModelListener(new FormModelListener()
    {
      @Override
      public void formChanged(FormModelEvent[] events)
      {
        for (FormModelEvent event : events)
        {
          if (event.getChangeType() == FormModelEvent.FORM_TO_BE_CLOSED)
          {
            radComponent.getFormModel().removeFormModelListener(this);
            _unregisterAll();
            break;
          }
        }
      }
    });
  }

  private void _unregisterAll()
  {
    for (FileObject fileObject : modelDataObject.getPrimaryFile().getChildren())
      fileObject.removeFileChangeListener(fileChangeListener);
    modelDataObject = null;
    radComponent = null;
  }

  private PropertyChangeListener _createBeanPropertyChangeListener(final String pPropertyName)
  {
    return new PropertyChangeListener()
    {
      @Override
      public void propertyChange(PropertyChangeEvent evt)
      {
        IFieldAccess<Object> fieldAccess = _getFieldAccess(pPropertyName);
        if (fieldAccess != null)
        {
          try
          {
            RADProperty beanProperty = _getBeanProperty(pPropertyName);
            Object oldValue = fieldAccess.getValue();
            Object newValue = beanProperty.getValue();
            if (!Utility.isEqual(oldValue, newValue))
              fieldAccess.setValue(newValue);
          }
          catch (IllegalAccessException e)
          {
            e.printStackTrace(); // TODO: errorHandling
          }
          catch (InvocationTargetException e)
          {
            e.printStackTrace();  // TODO: errorHandling
          }
        }
      }
    };
  }

  private FileChangeListener _getFileChangeListener()
  {
    if (fileChangeListener == null)
    {
      fileChangeListener = new FileChangeAdapter()
      {
        @Override
        public void fileChanged(FileEvent fe)
        {
          FileObject fo = fe.getFile();
          String propertyName = fo.getNameExt();
          IFieldAccess<Object> fieldAccess = _getFieldAccess(propertyName);
          if (fieldAccess != null)
          {
            try
            {
              RADProperty beanProperty = _getBeanProperty(propertyName);
              Object oldValue = beanProperty.getValue();
              Object newValue = fieldAccess.getValue();
              if (!Utility.isEqual(oldValue, newValue))
                beanProperty.setValue(newValue);
            }
            catch (IllegalAccessException e)
            {
              e.printStackTrace();  // TODO: errorHandling
            }
            catch (InvocationTargetException e)
            {
              e.printStackTrace();  // TODO: errorHandling
            }
          }
        }
      };
    }
    return fileChangeListener;
  }

  private RADProperty _getBeanProperty(String pPropertyName)
  {
    if (radComponent != null)
      return radComponent.getBeanProperty(pPropertyName);
    return null;
  }

  private IFieldAccess<Object> _getFieldAccess(String pPropertyName)
  {
    if (modelDataObject != null)
      return DataAccessHelper.accessField(modelDataObject.getPrimaryFile().getFileObject(pPropertyName));
    return null;
  }

}
