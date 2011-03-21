package org.netbeans.modules.form.adito;

import de.adito.aditoweb.core.util.Utility;
import de.adito.aditoweb.core.util.debug.Debug;
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

  private Sheet sheet;


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

    radComponent.getFormModel().addFormModelListener(new FormModelListener()
    {
      @Override
      public void formChanged(FormModelEvent[] events)
      {
        for (FormModelEvent event : events)
        {
          RADComponent eventComponent = event.getComponent();
          if (radComponent.equals(eventComponent))
          {
            switch (event.getChangeType())
            {
              case FormModelEvent.COMPONENT_LAYOUT_CHANGED:
                Debug.write(eventComponent.getName()); // DEBUG: remove it!
                if (eventComponent instanceof RADVisualComponent)
                {
                  RADVisualComponent radVisualComponent = (RADVisualComponent) eventComponent;
                  for (Node.Property property : radVisualComponent.getConstraintsProperties())
                  {
                    try
                    {
                      Debug.write(property.getName(), property.getValue()); // DEBUG: remove it!
                    }
                    catch (IllegalAccessException e)
                    {
                      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    catch (InvocationTargetException e)
                    {
                      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                  }
                }
                break;
              default:
                break;
            }
          }
        }
      }
    });

    _copyProperties();

    _registerListeners();
  }

  public void layoutPropertiesChanged(Node.Property[] pOldProperties, Node.Property[] pNewProperties)
  {
//    Debug.write("old", pOldProperties); // DEBUG: remove it!
//    Debug.write("new", pNewProperties); // DEBUG: remove it!
//    if (radComponent instanceof RADVisualComponent)
//    {
//      RADVisualComponent radVisualComponent = (RADVisualComponent) radComponent;
//      RADVisualContainer radVisualContainer = radVisualComponent.getParentContainer();
//      if (radVisualContainer != null)
//      {
//        LayoutSupportManager layoutSupport = radVisualContainer.getLayoutSupport();
//        if (layoutSupport != null)
//        {
//          LayoutSupportDelegate layoutDelegate = layoutSupport.getLayoutDelegate();
//          if (layoutDelegate != null)
//            Debug.write("supported class is", layoutDelegate.getSupportedClass()); // DEBUG: remove it!
//        }
//      }
//      Debug.write("the node is", radVisualComponent.getNodeReference()); // DEBUG: remove it!
//    }
  }

  public void createPropertySets(List<Node.PropertySet> propSets)
  {
    if (sheet == null && modelDataObject != null)
    {
      PropertiesCookie propsCookie = modelDataObject.getCookie(PropertiesCookie.class);
      if (propsCookie != null)
      {
        sheet = new Sheet();
        propsCookie.applyAditoPropertiesSync(sheet);
      }
    }
    if (sheet != null)
    {
      Node.PropertySet[] sets = sheet.toArray();
      propSets.addAll(Arrays.asList(sets));
    }
  }

  private void _copyProperties() throws InvocationTargetException, IllegalAccessException
  {
    List<Node.PropertySet> propSets = new ArrayList<Node.PropertySet>();
    createPropertySets(propSets);
    for (Node.PropertySet set : propSets)
    {
      for (Node.Property prop : set.getProperties())
      {
        FormProperty formProperty = _getFormProperty(prop.getName());
        if (formProperty != null)
          formProperty.setValue(prop.getValue());
      }
    }
  }

  private void _registerListeners()
  {
    for (FileObject fo : modelDataObject.getPrimaryFile().getChildren())
    {
      String propertyName = fo.getNameExt();
      FormProperty formProperty = _getFormProperty(propertyName);
      if (formProperty != null)
      {
        formProperty.addPropertyChangeListener(_createFormPropertyChangeListener(propertyName));
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

  private PropertyChangeListener _createFormPropertyChangeListener(final String pPropertyName)
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
            FormProperty formProperty = _getFormProperty(pPropertyName);
            Object oldValue = fieldAccess.getValue();
            Object newValue = formProperty.getValue();
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
              FormProperty formProperty = _getFormProperty(propertyName);
              Object oldValue = formProperty.getValue();
              Object newValue = fieldAccess.getValue();
              if (!Utility.isEqual(oldValue, newValue))
                formProperty.setValue(newValue);
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

  private FormProperty _getFormProperty(String pPropertyName)
  {
    if (radComponent != null)
    {
      RADComponentNode nodeReference = radComponent.getNodeReference();
      if (nodeReference != null)
        return nodeReference.getProperty(pPropertyName);
      return radComponent.getBeanProperty(pPropertyName);
    }
    return null;
  }

  private IFieldAccess<Object> _getFieldAccess(String pPropertyName)
  {
    if (modelDataObject != null)
      return DataAccessHelper.accessField(modelDataObject.getPrimaryFile().getFileObject(pPropertyName));
    return null;
  }

}
