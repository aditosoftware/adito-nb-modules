package org.netbeans.modules.form.adito;

import com.google.common.base.*;
import com.google.common.collect.Lists;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync.*;
import org.jetbrains.annotations.NotNull;
import org.netbeans.modules.form.*;
import org.netbeans.modules.form.project.ClassSource;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataFolder;
import org.openide.nodes.Node;
import org.openide.util.NotImplementedException;

import java.beans.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.*;

/**
 * @author J. Boesl, 31.03.11
 */
public class FormDataBridge
{

  private final RADComponent radComponent;
  private final IFormComponentInfo componentInfo;
  private final IFormComponentPropertyMapping componentPropertyMapping;

  private PropertyChangeListener aditoPropertyChangeListener;
  private PropertyChangeListener formPropertyChangeListener;


  public FormDataBridge(@NotNull RADComponent pRadComponent, @NotNull IFormComponentInfo pComponentInfo,
                        @NotNull IFormComponentPropertyMapping pComponentPropertyMapping)
  {
    radComponent = pRadComponent;
    componentInfo = pComponentInfo;
    componentPropertyMapping = pComponentPropertyMapping;
  }

  void layoutPropertiesChanged()
  {
    if (radComponent instanceof RADVisualComponent)
    {
      RADVisualComponent radVisualComponent = (RADVisualComponent) radComponent;
      _radPropertiesChanged(radVisualComponent.getConstraintsProperties());
    }
  }

  void newComponentAdded()
  {
    if (radComponent instanceof RADVisualComponent)
    {
      RADVisualComponent radVisualComponent = (RADVisualComponent) radComponent;
      for (Node.Property property : radVisualComponent.getConstraintsProperties())
      {
        try
        {
          _alignAditoToFormProp(property);
        }
        catch (InvocationTargetException e)
        {
          // ung�ltige Werte werden ignoriert. (z.B. -1 f�r 'width')
        }
      }
    }
    for (String s : componentInfo.getPropertyNames())
      _alignFormToAditoProperty(s);
  }

  void registerListeners()
  {
    if (aditoPropertyChangeListener == null)
    {
      aditoPropertyChangeListener = _createAditoPropertyChangeListener();
      formPropertyChangeListener = _createFormPropertyChangeListener();
      componentInfo.addPropertyListener(aditoPropertyChangeListener);
      for (String aditoPropName : componentInfo.getPropertyNames())
      {
        String radPropName = componentPropertyMapping.getRadPropName(aditoPropName);
        if (!Strings.isNullOrEmpty(radPropName))
        {
          FormProperty formProperty = _getFormProperty(radPropName);
          if (formProperty != null)
            formProperty.addPropertyChangeListener(formPropertyChangeListener);
        }
      }
    }
  }

  void unregisterAll()
  {
    componentInfo.removePropertyListener(aditoPropertyChangeListener);
    for (String aditoPropName : componentInfo.getPropertyNames())
    {
      String radPropName = componentPropertyMapping.getRadPropName(aditoPropName);
      if (!Strings.isNullOrEmpty(radPropName))
      {
        FormProperty formProperty = _getFormProperty(radPropName);
        if (formProperty != null)
          formProperty.removePropertyChangeListener(formPropertyChangeListener);
      }
    }
    aditoPropertyChangeListener = null;
    formPropertyChangeListener = null;
  }

  private PropertyChangeListener _createFormPropertyChangeListener()
  {
    return new PropertyChangeListener()
    {
      @Override
      public void propertyChange(PropertyChangeEvent evt)
      {
        Node.Property property = (Node.Property) evt.getSource();
        _radPropertiesChanged(property);
      }
    };
  }

  private void _radPropertiesChanged(Node.Property... pProperties)
  {
    try
    {
      for (Node.Property formProperty : pProperties)
        _alignAditoToFormProp(formProperty);
    }
    catch (InvocationTargetException e)
    {
      radComponent.getFormModel().forceUndoOfCompoundEdit(); // TODO: Fehler dem User mitteilen (output)
    }
  }

  private void _alignAditoToFormProp(Node.Property pFormProperty) throws InvocationTargetException
  {
    try
    {
      String formPropName = pFormProperty.getName();
      String aditoPropName = componentPropertyMapping.getAditoPropName(formPropName);
      if (Strings.isNullOrEmpty(aditoPropName))
        return; // not a mapped value
      Node.Property aditoProperty = componentInfo.getProperty(aditoPropName);
      if (aditoProperty == null)
      {
        _logInvalidMapping(aditoPropName, formPropName, false);
        return;
      }
      Object fieldValue = aditoProperty.getValue();
      Object formPropertyValue = pFormProperty.getValue();
      if (!Objects.equal(fieldValue, formPropertyValue) &&
          (formPropertyValue != null || !aditoProperty.isDefaultValue()))
      {
        aditoProperty.setValue(formPropertyValue);
      }
    }
    catch (IllegalAccessException e)
    {
      throw new RuntimeException(e); // TODO: runtimeEx
    }
  }

  private PropertyChangeListener _createAditoPropertyChangeListener()
  {
    return new PropertyChangeListener()
    {
      @Override
      public void propertyChange(PropertyChangeEvent evt)
      {
        String propertyName = evt.getPropertyName();
        if (propertyName.equals(IFormComponentInfo.PROP_CHILD_ADDED))
        {
          FileObject created = (FileObject) evt.getNewValue();

          if (radComponent instanceof ComponentContainer)
          {
            ComponentContainer container = (ComponentContainer) radComponent;
            for (RADComponent childComp : container.getSubBeans())
            {
              // Komponente mit dem Namen existiert bereits. Muss nicht synchronisiert werden.
              if (created.getName().equals(childComp.getName()))
                return;
            }

            IFormComponentInfoProvider compInfoProvider = NbAditoInterface.lookup(IFormComponentInfoProvider.class);
            IFormComponentInfo componentInfo = compInfoProvider.createComponentInfo(created);
            Class<?> createdBean = componentInfo.getFormPropertyMapping().getComponentClass();

            RADComponent component = radComponent.getFormModel().getComponentCreator().createComponent(
                new ClassSource(createdBean.getCanonicalName()), radComponent, null);
            component.setStoredName(created.getName());
            component.getARADComponentHandler().setModelDataObject(DataFolder.findFolder(created));
            try
            {
              AditoFormUtils.copyValuesFromModelToComponent(component);
            }
            catch (Exception e)
            {
              throw new RuntimeException("can't copy values for: '" + component + "'.", e); // TODO: error-id
            }
          }
        }
        else if (propertyName.equals(IFormComponentInfo.PROP_CHILD_REMOVED))
        {
          if (radComponent instanceof ComponentContainer)
          {
            ComponentContainer container = (ComponentContainer) radComponent;
            String removedName = ((FileObject) evt.getOldValue()).getNameExt();
            for (RADComponent component : Lists.newArrayList(container.getSubBeans()))
            {
              if (Objects.equal(component.getName(), removedName))
              {
                try
                {
                  component.getNodeReference().destroy();
                }
                catch (IOException e)
                {
                  throw new RuntimeException("node could not be destroyed: " + component.getNodeReference(), e);
                }
              }
            }
          }
        }
        else if (propertyName.equals(IFormComponentInfo.PROP_VALUE_CHANGED))
        {
          _alignFormToAditoProperty((String) evt.getNewValue());
        }
        else
          throw new NotImplementedException(propertyName);
      }
    };
  }

  private void _alignFormToAditoProperty(String pAditoPropName)
  {
    try
    {
      String mappedName = componentPropertyMapping.getRadPropName(pAditoPropName);
      if (Strings.isNullOrEmpty(mappedName))
        return; // not a mapped value
      Node.Property aditoProperty = componentInfo.getProperty(pAditoPropName);
      FormProperty formProperty = _getFormProperty(mappedName);
      if (formProperty == null)
      {
        _logInvalidMapping(pAditoPropName, mappedName, true);
        return;
      }
      Object newValue = aditoProperty.getValue();
      if (!Objects.equal(newValue, formProperty.getValue()))
        formProperty.setValue(newValue);
    }
    catch (IllegalAccessException e)
    {
      throw new RuntimeException(e); // TODO: runtimeEx
    }
    catch (InvocationTargetException e)
    {
      throw new RuntimeException(e); // TODO: runtimeEx
    }
  }

  private FormProperty _getFormProperty(String pRadPropName)
  {
    if (pRadPropName == null)
      return null;
    Node.Property prop = radComponent.getPropertyByName(pRadPropName);
    return prop instanceof FormProperty ? (FormProperty) prop : null;
  }

  private void _logInvalidMapping(String pAditoProp, String pFormProp, boolean pAditoToForm)
  {
    String aditoProp = "AditoProperty: " + pAditoProp;
    String formProp = "FormProperty: " + pFormProp;
    String prop1 = pAditoToForm ? aditoProp : formProp;
    String prop2 = pAditoToForm ? formProp : aditoProp;
    String compDetail = "component " + radComponent.getBeanClass().getSimpleName();
    DataFolder modelDataObject = radComponent.getARADComponentHandler().getModelDataObject();
    assert modelDataObject != null;
    String pathDetail = "path " + modelDataObject.getPrimaryFile().getPath();
    String detail = pAditoToForm ? compDetail + " with " + pathDetail : pathDetail + " with " + compDetail;
    Logger.getLogger(FormDataBridge.class.getSimpleName()).log(
        Level.WARNING,
        prop1 + " is mapped to " + prop2 + " but doesn't exist at " + detail);
  }

}
