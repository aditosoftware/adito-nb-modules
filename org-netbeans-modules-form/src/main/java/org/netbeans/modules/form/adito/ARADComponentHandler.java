package org.netbeans.modules.form.adito;

import de.adito.aditoweb.core.util.Utility;
import de.adito.aditoweb.designer.filetype.PropertiesCookie;
import de.adito.aditoweb.filesystem.datamodelfs.access.DataAccessHelper;
import de.adito.aditoweb.filesystem.datamodelfs.access.mechanics.field.IFieldAccess;
import de.adito.aditoweb.filesystem.datamodelfs.access.model.*;
import org.netbeans.modules.form.*;
import org.netbeans.modules.form.adito.mapping.EModelComponentMapping;
import org.netbeans.modules.form.layoutsupport.LayoutSupportDelegate;
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
  private EModelComponentMapping mapping;


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

    _registerListeners();
  }

  public void layoutPropertiesChanged()
  {
    if (radComponent instanceof RADVisualComponent)
    {
      RADVisualComponent radVisualComponent = (RADVisualComponent) radComponent;
      for (Node.Property formProperty : radVisualComponent.getConstraintsProperties())
      {
        Node.Property modelProperty = getModelPropertyByFormPropertyName(formProperty.getName());
        if (modelProperty != null)
        {
          try
          {
            Object modelPropertyValue = modelProperty.getValue();
            if (modelPropertyValue == null || !modelProperty.getValue().equals(formProperty.getValue()))
              formProperty.setValue(modelPropertyValue);
          }
          catch (IllegalAccessException e)
          {
            e.printStackTrace(); // TODO: errorHandling
          }
          catch (InvocationTargetException e)
          {
            e.printStackTrace(); // TODO: errorHandling
          }
        }
      }
    }
  }

  public EModelComponentMapping getComponentMapping()
  {
    if (mapping == null)
    {
      Integer type = FieldConst.TYPE.accessField(modelDataObject.getPrimaryFile()).getValue();
      EModelAccessType modelAccessType = EModelAccessType.get(type);
      mapping = EModelComponentMapping.get(modelAccessType);
    }
    return mapping;
  }

  public void getPropertySets(List<Node.PropertySet> propSets)
  {
    if (sheet == null && modelDataObject != null)
      sheet = _createPropertySheet();
    if (sheet != null)
    {
      Node.PropertySet[] sets = sheet.toArray();
      propSets.addAll(Arrays.asList(sets));
    }
  }

  public FormProperty getFormPropertyByModelPropertyName(String pModelPropertyName)
  {
    if (getComponentMapping() != null)
    {
      String mappedName = getComponentMapping().getComponentInfo().getFormPropertyName(
          getLayoutSupportDelegateClass(), pModelPropertyName);
      if (mappedName != null)
      {
        Node.Property mappedProperty = radComponent.getPropertyByName(mappedName);
        if (mappedProperty instanceof FormProperty)
          return (FormProperty) mappedProperty;
      }
    }
    return null;
  }

  public Node.Property getModelPropertyByFormPropertyName(String pFormPropertyName)
  {
    String mappedName = getComponentMapping().getComponentInfo().getModelPropertyName(
        getLayoutSupportDelegateClass(), pFormPropertyName);
    if (mappedName != null)
    {
      Node.Property mappedProperty = radComponent.getPropertyByName(mappedName);
      if (mappedProperty instanceof FormProperty)
        return mappedProperty;
    }
    return null;
  }

  public Class<? extends LayoutSupportDelegate> getLayoutSupportDelegateClass()
  {
    Class<? extends LayoutSupportDelegate> layoutSupportClass = null;
    if (radComponent.getParentComponent() instanceof RADVisualContainer)
    {
      RADVisualContainer container = (RADVisualContainer) radComponent.getParentComponent();
      LayoutSupportDelegate layoutDelegate = container.getLayoutSupport().getLayoutDelegate();
      if (layoutDelegate != null)
        layoutSupportClass = layoutDelegate.getClass();
    }
    return layoutSupportClass;
  }

  private Sheet _createPropertySheet()
  {
    PropertiesCookie propsCookie = modelDataObject.getCookie(PropertiesCookie.class);
    if (propsCookie == null)
      return null;
    Sheet propSheet = new Sheet();
    propsCookie.applyAditoPropertiesSync(propSheet);
    return propSheet;
  }

  private void _registerListeners()
  {
    for (FileObject fo : modelDataObject.getPrimaryFile().getChildren())
    {
      String modelPropertyName = fo.getNameExt();
      fo.addFileChangeListener(_getFileChangeListener());
      FormProperty formProperty = getFormPropertyByModelPropertyName(modelPropertyName);
      if (formProperty != null)
        formProperty.addPropertyChangeListener(_createFormPropertyChangeListener(modelPropertyName));
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

  private PropertyChangeListener _createFormPropertyChangeListener(final String pModelPropertyName)
  {
    return new PropertyChangeListener()
    {
      @Override
      public void propertyChange(PropertyChangeEvent evt)
      {
        IFieldAccess<Object> fieldAccess = _getFieldAccess(pModelPropertyName);
        if (fieldAccess != null)
        {
          try
          {
            FormProperty formProperty = getFormPropertyByModelPropertyName(pModelPropertyName);
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
            e.printStackTrace(); // TODO: errorHandling
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
          String modelPropertyName = fo.getNameExt();
          IFieldAccess<Object> fieldAccess = _getFieldAccess(modelPropertyName);
          if (fieldAccess != null)
          {
            try
            {
              FormProperty formProperty = getFormPropertyByModelPropertyName(modelPropertyName);
              if (formProperty != null)
              {
                Object oldValue = formProperty.getValue();
                Object newValue = fieldAccess.getValue();
                if (!Utility.isEqual(oldValue, newValue))
                  formProperty.setValue(newValue);
              }
            }
            catch (IllegalAccessException e)
            {
              e.printStackTrace(); // TODO: errorHandling
            }
            catch (InvocationTargetException e)
            {
              e.printStackTrace(); // TODO: errorHandling
            }
          }
        }
      };
    }
    return fileChangeListener;
  }

  private IFieldAccess<Object> _getFieldAccess(String pModelPropertyName)
  {
    if (modelDataObject != null)
      return DataAccessHelper.accessField(modelDataObject.getPrimaryFile().getFileObject(pModelPropertyName));
    return null;
  }

}
