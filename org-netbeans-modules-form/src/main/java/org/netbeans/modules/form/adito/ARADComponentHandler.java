package org.netbeans.modules.form.adito;

import de.adito.aditoweb.core.util.Utility;
import de.adito.aditoweb.designer.filetype.PropertiesCookie;
import de.adito.aditoweb.filesystem.datamodelfs.access.DataAccessHelper;
import de.adito.aditoweb.filesystem.datamodelfs.access.mechanics.field.IFieldAccess;
import de.adito.aditoweb.filesystem.datamodelfs.access.model.*;
import de.adito.aditoweb.filesystem.datamodelfs.access.verification.ResultOfVerification;
import org.jetbrains.annotations.*;
import org.netbeans.modules.form.*;
import org.netbeans.modules.form.adito.mapping.EModelComponentMapping;
import org.netbeans.modules.form.layoutsupport.LayoutSupportDelegate;
import org.openide.filesystems.*;
import org.openide.loaders.DataFolder;
import org.openide.nodes.*;

import java.beans.*;
import java.lang.reflect.InvocationTargetException;

/**
 * @author J. Boesl, 17.02.11
 */
public class ARADComponentHandler
{

  private RADComponent radComponent;
  @NotNull
  private DataFolder modelDataObject;

  private FileChangeListener fileChangeListener;

  private Sheet sheet;
  private EModelComponentMapping mapping;


  public ARADComponentHandler(@NotNull DataFolder pModelDataObject)
  {
    modelDataObject = pModelDataObject;
  }

  @NotNull
  public DataFolder getModelDataObject()
  {
    return modelDataObject;
  }

  public void initRADComponent(@NotNull RADComponent pRADComponent) throws InvocationTargetException, IllegalAccessException
  {
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
      try
      {
        for (Node.Property formProperty : radVisualComponent.getConstraintsProperties())
        {
          IFieldAccess fieldAccess = getFieldAccessByFormPropertyName(formProperty.getName());
          if (fieldAccess != null)
          {
            Object fieldValue = fieldAccess.getValue();
            Object formPropertyValue = formProperty.getValue();
            if (!Utility.isEqual(fieldValue, formPropertyValue))
            {
              @SuppressWarnings({"unchecked"})
              ResultOfVerification resultOfVerification = fieldAccess.setValue(formPropertyValue);
              radComponent.getNodeReference().firePropertyChangeHelper(fieldAccess.getName(), null, null);
              if (resultOfVerification != null && resultOfVerification.getType() != ResultOfVerification.OK)
                throw resultOfVerification.getException();
            }
          }
//          Node.Property modelProperty = getModelFieldByFormPropertyName(formProperty.getName());
//          if (modelProperty != null)
//          {
//            Object modelPropertyValue = modelProperty.getValue();
//            Object formPropertyValue = formProperty.getValue();
//            if (!Utility.isEqual(modelPropertyValue, formPropertyValue))
//            {
//              //noinspection unchecked
//              modelProperty.setValue(formPropertyValue);
//              radComponent.getNodeReference().firePropertyChangeHelper(modelProperty.getName(), null, null);
//            }
//          }
        }
      }
      catch (Exception e)
      {
        radComponent.getFormModel().forceUndoOfCompoundEdit();
      }
    }
  }

  @Nullable
  private EModelComponentMapping _getComponentMapping()
  {
    if (mapping == null)
    {
      Integer type = FieldConst.TYPE.accessField(modelDataObject.getPrimaryFile()).getValue();
      EModelAccessType modelAccessType = EModelAccessType.get(type);
      mapping = EModelComponentMapping.get(modelAccessType);
    }
    return mapping;
  }

  @NotNull
  public Node.PropertySet[] getPropertySets()
  {
    if (sheet == null)
      sheet = _createPropertySheet();
    if (sheet != null)
      return sheet.toArray();
    return new Node.PropertySet[0];
  }

  @Nullable
  public FormProperty getFormPropertyByModelPropertyName(@NotNull String pModelPropertyName)
  {
    EModelComponentMapping componentMapping = _getComponentMapping();
    if (componentMapping != null)
    {
      String mappedName = componentMapping.getComponentInfo().getFormPropertyName(
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

  @Nullable
  public IFieldAccess getFieldAccessByFormPropertyName(@NotNull String pFormPropertyName)
  {
    EModelComponentMapping componentMapping = _getComponentMapping();
    if (componentMapping != null)
    {
      String mappedName = componentMapping.getComponentInfo().getModelPropertyName(
          getLayoutSupportDelegateClass(), pFormPropertyName);
      if (mappedName != null)
      {
        return DataAccessHelper.accessField(modelDataObject.getPrimaryFile().getFileObject(mappedName));
//        Node.PropertySet[] propertySets = getPropertySets();
//        for (Node.PropertySet propertySet : propertySets)
//        {
//          for (Node.Property<?> property : propertySet.getProperties())
//          {
//            if (property.getName().equals(mappedName))
//              return property;
//          }
//        }
      }
    }
    return null;
  }

  @Nullable
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

  @Nullable
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
    radComponent = null;
  }

  @NotNull
  private PropertyChangeListener _createFormPropertyChangeListener(@NotNull final String pModelPropertyName)
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
            if (formProperty != null)
            {
              Object oldValue = fieldAccess.getValue();
              Object newValue = formProperty.getValue();
              if (!Utility.isEqual(oldValue, newValue))
                fieldAccess.setValue(newValue);
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

  @NotNull
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

  @Nullable
  private IFieldAccess<Object> _getFieldAccess(@NotNull String pModelPropertyName)
  {
    return DataAccessHelper.accessField(modelDataObject.getPrimaryFile().getFileObject(pModelPropertyName));
  }

}
