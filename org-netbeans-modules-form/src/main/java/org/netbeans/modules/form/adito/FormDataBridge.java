package org.netbeans.modules.form.adito;

import de.adito.aditoweb.core.util.Utility;
import de.adito.aditoweb.filesystem.datamodelfs.access.DataAccessHelper;
import de.adito.aditoweb.filesystem.datamodelfs.access.mechanics.field.IFieldAccess;
import de.adito.aditoweb.filesystem.datamodelfs.access.verification.ResultOfVerification;
import org.jetbrains.annotations.*;
import org.netbeans.modules.form.*;
import org.netbeans.modules.form.adito.mapping.EModelComponentMapping;
import org.netbeans.modules.form.layoutsupport.LayoutSupportDelegate;
import org.openide.filesystems.*;
import org.openide.loaders.DataFolder;
import org.openide.nodes.Node;

import java.beans.*;
import java.lang.reflect.InvocationTargetException;

/**
 * @author J. Boesl, 31.03.11
 */
public class FormDataBridge
{

  @NotNull
  private RADComponent radComponent;
  @NotNull
  private DataFolder modelDataObject;
  @Nullable
  private IFormDataInfo fdInfo;
  @Nullable
  private FileChangeListener fileChangeListener;


  public FormDataBridge(@NotNull RADComponent pRadComponent, @NotNull DataFolder pModelDataObject)
  {
    radComponent = pRadComponent;
    modelDataObject = pModelDataObject;

  }

  @NotNull
  IFormDataInfo getFormDataInfo()
  {
    if (fdInfo == null)
      fdInfo = new _FormDataInfo();
    return fdInfo;
  }

  void layoutPropertiesChanged()
  {
    if (radComponent instanceof RADVisualComponent)
    {
      RADVisualComponent radVisualComponent = (RADVisualComponent) radComponent;
      try
      {
        for (Node.Property formProperty : radVisualComponent.getConstraintsProperties())
        {
          IFieldAccess fieldAccess = getFormDataInfo().getFieldAccessByFormPropertyName(formProperty.getName());
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
        }
      }
      catch (Exception e)
      {
        radComponent.getFormModel().forceUndoOfCompoundEdit();
      }
    }
  }

  void registerListeners()
  {
    if (fileChangeListener == null)
    {
      fileChangeListener = createFileChangeListener();
      for (FileObject fo : modelDataObject.getPrimaryFile().getChildren())
      {
        String modelPropertyName = fo.getNameExt();
        fo.addFileChangeListener(fileChangeListener);
        FormProperty formProperty = getFormDataInfo().getFormPropertyByModelPropertyName(modelPropertyName);
        if (formProperty != null)
          formProperty.addPropertyChangeListener(_createFormPropertyChangeListener(modelPropertyName));
      }
    }
  }

  void unregisterAll()
  {
    for (FileObject fileObject : modelDataObject.getPrimaryFile().getChildren())
      fileObject.removeFileChangeListener(fileChangeListener);
  }

  @NotNull
  private PropertyChangeListener _createFormPropertyChangeListener(@NotNull final String pModelPropertyName)
  {
    return new PropertyChangeListener()
    {
      @Override
      public void propertyChange(PropertyChangeEvent evt)
      {
        IFieldAccess<Object> fieldAccess = getFormDataInfo().getFieldAccess(pModelPropertyName);
        if (fieldAccess != null)
        {
          try
          {
            FormProperty formProperty = getFormDataInfo().getFormPropertyByModelPropertyName(pModelPropertyName);
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
  private FileChangeListener createFileChangeListener()
  {
    return new FileChangeAdapter()
    {
      @Override
      public void fileChanged(FileEvent fe)
      {
        FileObject fo = fe.getFile();
        String modelPropertyName = fo.getNameExt();
        IFieldAccess<Object> fieldAccess = getFormDataInfo().getFieldAccess(modelPropertyName);
        if (fieldAccess != null)
        {
          try
          {
            FormProperty formProperty = getFormDataInfo().getFormPropertyByModelPropertyName(modelPropertyName);
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


  /**
   * IFormDataInfo
   */
  private class _FormDataInfo implements IFormDataInfo
  {
    private EModelComponentMapping mapping;


    @Override
    public IFieldAccess getFieldAccessByFormPropertyName(@NotNull String pFormPropertyName)
    {
      EModelComponentMapping componentMapping = _getComponentMapping();
      if (componentMapping != null)
      {
        String mappedName = componentMapping.getComponentInfo().getModelPropertyName(
            _getLayoutSupportDelegateClass(), pFormPropertyName);
        if (mappedName != null)
          return getFieldAccess(mappedName);
      }
      return null;
    }

    @Override
    public IFieldAccess<Object> getFieldAccess(@NotNull String pModelPropertyName)
    {
      return DataAccessHelper.accessField(modelDataObject.getPrimaryFile().getFileObject(pModelPropertyName));
    }

    @Override
    public FormProperty getFormPropertyByModelPropertyName(@NotNull String pModelPropertyName)
    {
      EModelComponentMapping componentMapping = _getComponentMapping();
      if (componentMapping != null)
      {
        String mappedName = componentMapping.getComponentInfo().getFormPropertyName(
            _getLayoutSupportDelegateClass(), pModelPropertyName);
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
    private EModelComponentMapping _getComponentMapping()
    {
      if (mapping == null)
        mapping = EModelComponentMapping.get(radComponent);
      return mapping;
    }

    @Nullable
    private Class<? extends LayoutSupportDelegate> _getLayoutSupportDelegateClass()
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
  }

}
