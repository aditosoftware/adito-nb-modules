package org.netbeans.modules.form.adito;

import com.google.common.base.Objects;
import com.google.common.base.*;
import com.google.common.collect.Lists;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.IAditoFormConstants;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.model.IAditoModelDataProvider;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync.*;
import de.adito.propertly.core.common.path.PropertyPath;
import de.adito.propertly.core.spi.*;
import org.jetbrains.annotations.NotNull;
import org.netbeans.modules.form.*;
import org.netbeans.modules.form.project.ClassSource;
import org.openide.nodes.Node;
import org.openide.util.NotImplementedException;

import java.beans.*;
import java.io.IOException;
import java.lang.ref.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.logging.*;

/**
 * Synchronisiert das Adito-Datenmodell mit dem Form-Datenmodell.
 *
 * @author J. Boesl, 31.03.11
 */
public class FormDataBridge
{

  private final RADComponent radComponent;
  private final IFormComponentInfo componentInfo;

  private PropertyChangeListener aditoPropertyChangeListener;
  private PropertyChangeListener formPropertyChangeListener;


  public FormDataBridge(@NotNull RADComponent pRadComponent, @NotNull IFormComponentInfo pComponentInfo)
  {
    radComponent = pRadComponent;
    componentInfo = pComponentInfo;
  }

  void layoutPropertiesChanged(String pPropertyName)
  {
    if (pPropertyName == null)
    {
      if (radComponent instanceof RADVisualComponent)
      {
        RADVisualComponent radVisualComponent = (RADVisualComponent) radComponent;
        _radPropertiesChanged(radVisualComponent.getConstraintsProperties());
      }
    }
    else
    {
      try
      {
        alignAditoToFormProp(radComponent.getPropertyByName(pPropertyName));
      }
      catch (InvocationTargetException e)
      {
        radComponent.getFormModel().forceUndoOfCompoundEdit(); // TODO: Fehler dem User mitteilen (output)
      }
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
          alignAditoToFormProp(property);
        }
        catch (InvocationTargetException e)
        {
          // ungültige Werte werden ignoriert. (z.B. -1 für 'width')
        }
      }
    }
    copyValuesFromAdito();
  }

  void registerListeners()
  {
    if (aditoPropertyChangeListener == null)
    {
      aditoPropertyChangeListener = new _AditoPropertyChangeListener(this);
      formPropertyChangeListener = new _FormPropertyChangeListener(this);
      componentInfo.addPropertyListener(aditoPropertyChangeListener);
      for (String aditoPropName : componentInfo.getPropertyNames())
      {
        String radPropName = componentInfo.getRadPropName(aditoPropName);
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
      String radPropName = componentInfo.getRadPropName(aditoPropName);
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

  private void _radPropertiesChanged(Node.Property... pProperties)
  {
    try
    {
      for (Node.Property formProperty : pProperties)
        alignAditoToFormProp(formProperty);
    }
    catch (InvocationTargetException e)
    {
      radComponent.getFormModel().forceUndoOfCompoundEdit(); // TODO: Fehler dem User mitteilen (output)
    }
  }

  void alignAditoToFormProp(Node.Property pFormProperty) throws InvocationTargetException
  {
    try
    {
      String formPropName = pFormProperty.getName();
      String aditoPropName = componentInfo.getAditoPropName(formPropName);
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
          !(formPropertyValue == null && aditoProperty.isDefaultValue()))
      {
        Object oldFailVal = aditoProperty.getValue(IAditoFormConstants.ATR_FAILONVERIFICATIONERROR);
        try
        {
          aditoProperty.setValue(IAditoFormConstants.ATR_FAILONVERIFICATIONERROR, true); //#13779
          aditoProperty.setValue(formPropertyValue);
        }
        finally
        {
          aditoProperty.setValue(IAditoFormConstants.ATR_FAILONVERIFICATIONERROR, oldFailVal != null ? oldFailVal : false);
        }
      }
    }
    catch (IllegalAccessException e)
    {
      throw new RuntimeException(e); // TODO: runtimeEx
    }
  }

  void syncChildren()
  {
    ComponentContainer cont = (ComponentContainer) radComponent;

    IAditoModelDataProvider modelDataProvider = NbAditoInterface.lookup(IAditoModelDataProvider.class);
    List<IPropertyPitProvider<?, ?, ?>> childModels = modelDataProvider.getChildModels(
        radComponent.getARADComponentHandler().getModel());
    RADComponent[] subBeans = cont.getSubBeans();
    int[] perm = new int[subBeans.length];
    if (childModels.size() != subBeans.length)
    {
      throw new IllegalStateException("Size of subBeans and childModels not equal.");
    }
    for (int i = 0; i < subBeans.length; i++)
    {
      RADComponent subBean = subBeans[i];
      int index = -1;
      for (int j = 0; j < childModels.size(); j++)
      {
        IPropertyPitProvider<?, ?, ?> childModel = childModels.get(j);
        if (subBean.getName().equals(childModel.getPit().getOwnProperty().getName()))
        {
          index = j;
          break;
        }
      }
      if (index == -1)
      {
        throw new IllegalStateException("SubBean '" + subBean.getName() + "' could not be found in dataModels.");
      }
      perm[i] = index;
    }
    for (int i : perm)
    {
      if (perm[i] != i)
      {
        // Sortierung ist verschieden
        cont.reorderSubComponents(perm);
        radComponent.getFormModel().fireComponentsReordered(cont, perm);
        break;
      }
    }
  }

  void copyValuesFromAdito()
  {
    componentInfo.getPropertyNames().forEach(this::_alignFormToAditoProperty);

    IAditoModelDataProvider modelDataProvider = NbAditoInterface.lookup(IAditoModelDataProvider.class);
    List<IPropertyPitProvider<?, ?, ?>> childModels = modelDataProvider.getChildModels(
        radComponent.getARADComponentHandler().getModel());
    childModels.forEach(this::_addChild);
  }

  private void _alignFormToAditoProperty(final String pAditoPropName)
  {
    try
    {
      String mappedName = componentInfo.getRadPropName(pAditoPropName);
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
    catch (IllegalAccessException | InvocationTargetException e)
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
    IPropertyPitProvider<?, ?, ?> model = radComponent.getARADComponentHandler().getModel();
    assert model != null;
    String pathDetail = "path " + new PropertyPath(model.getPit().getOwnProperty()).asString();
    String detail = pAditoToForm ? compDetail + " with " + pathDetail : pathDetail + " with " + compDetail;
    Logger.getLogger(FormDataBridge.class.getSimpleName()).log(
        Level.WARNING,
        prop1 + " is mapped to " + prop2 + " but doesn't exist at " + detail);
  }

  private void _addChild(IPropertyPitProvider<?, ?, ?> pCreated)
  {
    ComponentContainer container = (ComponentContainer) radComponent;
    for (RADComponent childComp : container.getSubBeans())
    {
      // Komponente mit dem Namen existiert bereits. Muss nicht synchronisiert werden.
      if (pCreated.getPit().getOwnProperty().getName().equals(childComp.getName()))
        return;
    }

    IAditoModelDataProvider modelDataProvider = NbAditoInterface.lookup(IAditoModelDataProvider.class);
    List<IPropertyPitProvider<?, ?, ?>> childModels = modelDataProvider.getChildModels(
        radComponent.getARADComponentHandler().getModel());
    // wenn das erstellte Objekt nicht bei den 'childModels' dabei ist muss es für die GUI nicht erzeugt werden.
    if (!childModels.contains(pCreated))
      return;

    IFormComponentInfoProvider compInfoProvider = NbAditoInterface.lookup(IFormComponentInfoProvider.class);
    IFormComponentInfo componentInfo = compInfoProvider.createComponentInfo(pCreated);
    IFormComponentPropertyMapping formPropertyMapping = componentInfo.getFormPropertyMapping();
    if (formPropertyMapping == null)
      throw new RuntimeException("No 'IFormComponentProvider' available for '" +
                                     new PropertyPath(pCreated.getPit().getOwnProperty()).asString() + "'.");
    Class<?> createdBean = formPropertyMapping.getComponentClass();

    RADComponent component = radComponent.getFormModel().getComponentCreator().createComponent(
        new ClassSource(createdBean.getCanonicalName()), radComponent, null);
    if (component == null)
      throw new RuntimeException("component could not be pCreated! (Internal Error)");
    if (component.equals(radComponent))
      return;

    component.setStoredName(pCreated.getPit().getOwnProperty().getName());
    component.getARADComponentHandler().setModel(pCreated);

    try
    {
      component.getARADComponentHandler().applyValuesFromAditoModel();
    }
    catch (Exception e)
    {
      throw new RuntimeException("can't copy values for: '" + component + "'.", e);
    }
  }

  private void _removeChild(String pRemovedName)
  {
    ComponentContainer container = (ComponentContainer) radComponent;
    for (RADComponent component : Lists.newArrayList(container.getSubBeans()))
    {
      if (Objects.equal(component.getName(), pRemovedName))
      {
        RADComponentNode nodeReference = component.getNodeReference();
        if (nodeReference != null)
          try
          {
            nodeReference.destroy();
          }
          catch (IOException e)
          {
            throw new RuntimeException("node could not be destroyed: " + nodeReference, e);
          }
      }
    }
  }

  /**
   * PropertyChangeListener-Impl
   */
  private class _FormPropertyChangeListener implements PropertyChangeListener
  {
    private Reference<FormDataBridge> formDataBridgeRef;

    public _FormPropertyChangeListener(FormDataBridge pFormDataBridge)
    {
      formDataBridgeRef = new WeakReference<>(pFormDataBridge);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
      FormDataBridge formDataBridge = formDataBridgeRef.get();
      if (formDataBridge == null)
        return;

      Node.Property property = (Node.Property) evt.getSource();
      formDataBridge._radPropertiesChanged(property);
    }
  }

  /**
   * PropertyChangeListener-Impl
   */
  private class _AditoPropertyChangeListener implements PropertyChangeListener
  {
    private Reference<FormDataBridge> formDataBridgeRef;

    public _AditoPropertyChangeListener(FormDataBridge pFormDataBridge)
    {
      formDataBridgeRef = new WeakReference<>(pFormDataBridge);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt)
    {
      final FormDataBridge formDataBridge = formDataBridgeRef.get();
      if (formDataBridge != null)
        _apply(formDataBridge, evt);

      Map<Object, Object> attributes = componentInfo.getPropertyAttributes(String.valueOf(evt.getNewValue()));
      if(attributes != null)
      {
        Object isMixin = attributes.get(IFormComponentInfo.IS_MIXIN_PROPERTY);
        if (isMixin != null && isMixin instanceof Boolean && ((Boolean) isMixin))
          copyValuesFromAdito();
      }
    }

    private void _apply(final FormDataBridge pFormDataBridge, PropertyChangeEvent pEvt)
    {
      String propertyName = pEvt.getPropertyName();
      RADComponent radComponent = pFormDataBridge.radComponent;
      switch (propertyName)
      {
        case IFormComponentInfo.PROP_CHILD_ADDED:
          IPropertyPitProvider<?, ?, ?> created = (IPropertyPitProvider<?, ?, ?>) pEvt.getNewValue();
          if (radComponent instanceof ComponentContainer)
            _addChild(created);
          break;
        case IFormComponentInfo.PROP_CHILD_REMOVED:
          // Delete ist problematisch: dieses Event wird erst erhalten NACHDEM die Datei gelöscht wurde. Das bedeutet
          // die Daten für 'undo' können nicht hinterlegt werden und das kann zu Folgefehlern führen.
          if (radComponent instanceof ComponentContainer)
            _removeChild(((IPropertyDescription) pEvt.getSource()).getName());
          break;
        case IFormComponentInfo.PROP_POSITION_CHANGED:
          // per 'invokeLater', da das Event gefeuert wurde, bevor der Wert im FileSystem steht.
          syncChildren();
          break;
        case IFormComponentInfo.PROP_NAME_CHANGED:
          radComponent.rename((String) pEvt.getNewValue());
          break;
        case IFormComponentInfo.PROP_VALUE_CHANGED:
          pFormDataBridge._alignFormToAditoProperty((String) pEvt.getNewValue());
          break;
        default:
          throw new NotImplementedException(propertyName);
      }
    }
  }

}
