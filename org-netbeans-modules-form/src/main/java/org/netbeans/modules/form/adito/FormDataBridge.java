package org.netbeans.modules.form.adito;

import com.google.common.base.*;
import com.google.common.collect.Lists;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.model.IAditoModelDataProvider;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync.*;
import org.jetbrains.annotations.NotNull;
import org.netbeans.modules.form.*;
import org.netbeans.modules.form.project.ClassSource;
import org.openide.filesystems.FileObject;
import org.openide.nodes.Node;
import org.openide.util.NotImplementedException;

import javax.swing.*;
import java.awt.*;
import java.beans.*;
import java.io.IOException;
import java.lang.ref.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
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
  private final IFormComponentPropertyMapping componentPropertyMapping;

  private PropertyChangeListener aditoPropertyChangeListener;
  private PropertyChangeListener formPropertyChangeListener;

  // #6286: undo nicht komplett möglich.
  // Hier wird gemerkt, wenn eine Synchronisation von Adito kommt. Die anschließende Änderung im FormEditor soll nicht
  // wieder nach Adito synchronisiert werden.
  private final AtomicInteger isProcessingFromAdito;


  public FormDataBridge(@NotNull RADComponent pRadComponent, @NotNull IFormComponentInfo pComponentInfo,
                        @NotNull IFormComponentPropertyMapping pComponentPropertyMapping)
  {
    radComponent = pRadComponent;
    componentInfo = pComponentInfo;
    componentPropertyMapping = pComponentPropertyMapping;
    isProcessingFromAdito = new AtomicInteger(0);
  }

  void layoutPropertiesChanged(String pPropertyName)
  {
    if (_isProcessingFromAdito())
      return;

    if (radComponent instanceof RADVisualComponent)
    {
      if (pPropertyName == null)
      {
        RADVisualComponent radVisualComponent = (RADVisualComponent) radComponent;
        _radPropertiesChanged(radVisualComponent.getConstraintsProperties());
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
    if (isProcessingFromAdito.get() > 0)
      return;

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
          !(formPropertyValue == null && aditoProperty.isDefaultValue()))
      {
        //noinspection unchecked
        aditoProperty.setValue(formPropertyValue);
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
    List<FileObject> childModels = modelDataProvider.getChildModels(
        radComponent.getARADComponentHandler().getModelFileObject());
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
        FileObject childModel = childModels.get(j);
        if (subBean.getName().equals(childModel.getNameExt()))
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
    cont.reorderSubComponents(perm);
    radComponent.getFormModel().fireComponentsReordered(cont, perm);
  }

  void copyValuesFromAdito()
  {
    _processFromAdito(new Runnable()
    {
      @Override
      public void run()
      {
        for (String s : componentInfo.getPropertyNames())
          _alignFormToAditoProperty(s);
      }
    });
  }

  private void _alignFormToAditoProperty(final String pAditoPropName)
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
    FileObject modelFileObject = radComponent.getARADComponentHandler().getModelFileObject();
    assert modelFileObject != null;
    String pathDetail = "path " + modelFileObject.getPath();
    String detail = pAditoToForm ? compDetail + " with " + pathDetail : pathDetail + " with " + compDetail;
    Logger.getLogger(FormDataBridge.class.getSimpleName()).log(
        Level.WARNING,
        prop1 + " is mapped to " + prop2 + " but doesn't exist at " + detail);
  }

  private boolean _isProcessingFromAdito()
  {
    return isProcessingFromAdito.get() > 0;
  }

  private void _processFromAdito(Runnable pRunnable)
  {
    try
    {
      isProcessingFromAdito.incrementAndGet();
      pRunnable.run();
    }
    finally
    {
      SwingUtilities.invokeLater(new Runnable()
      {
        @Override
        public void run()
        {
          isProcessingFromAdito.decrementAndGet();
        }
      });
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
      if (formDataBridge == null || formDataBridge.isProcessingFromAdito.get() > 0)
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
        formDataBridge._processFromAdito(new Runnable()
        {
          @Override
          public void run()
          {
            _apply(formDataBridge, evt);
          }
        });
    }

    private void _apply(final FormDataBridge pFormDataBridge, PropertyChangeEvent evt)
    {
      String propertyName = evt.getPropertyName();
      RADComponent radComponent = pFormDataBridge.radComponent;
      switch (propertyName)
      {
        case IFormComponentInfo.PROP_CHILD_ADDED:
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

            IAditoModelDataProvider modelDataProvider = NbAditoInterface.lookup(IAditoModelDataProvider.class);
            List<FileObject> childModels = modelDataProvider.getChildModels(
                radComponent.getARADComponentHandler().getModelFileObject());
            // wenn das erstellte Objekt nicht bei den 'childModels' dabei ist muss es für die GUI nicht erzeugt werden.
            if (!childModels.contains(created))
              return;

            IFormComponentInfoProvider compInfoProvider = NbAditoInterface.lookup(IFormComponentInfoProvider.class);
            IFormComponentInfo componentInfo = compInfoProvider.createComponentInfo(created);
            IFormComponentPropertyMapping formPropertyMapping = componentInfo.getFormPropertyMapping();
            if (formPropertyMapping == null)
              throw new RuntimeException("No 'IFormComponentProvider' available for '" + created.getPath() + "'.");
            Class<?> createdBean = formPropertyMapping.getComponentClass();

            RADComponent component = radComponent.getFormModel().getComponentCreator().createComponent(
                new ClassSource(createdBean.getCanonicalName()), radComponent, null);
            if (component == null)
              throw new RuntimeException("component could not be created! (Internal Error)");
            component.setStoredName(created.getName());
            component.getARADComponentHandler().setModelFileObject(created);

            try
            {
              component.getARADComponentHandler().applyValuesFromAditoModel();
            }
            catch (Exception e)
            {
              throw new RuntimeException("can't copy values for: '" + component + "'.", e);
            }
          }
          break;
        case IFormComponentInfo.PROP_CHILD_REMOVED:
          // Delete ist problematisch: dieses Event wird erst erhalten NACHDEM die Datei gelöscht wurde. Das bedeutet
          // die Daten für 'undo' können nicht hinterlegt werden und das kann zu Folgefehlern führen.
          if (radComponent instanceof ComponentContainer)
          {
            ComponentContainer container = (ComponentContainer) radComponent;
            String removedName = ((FileObject) evt.getOldValue()).getNameExt();
            for (RADComponent component : Lists.newArrayList(container.getSubBeans()))
            {
              if (Objects.equal(component.getName(), removedName))
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
          break;
        case IFormComponentInfo.PROP_POSITION_CHANGED:
          // per 'invokeLater', da das Event gefeuert wurde, bevor der Wert im FileSystem steht.
          EventQueue.invokeLater(new Runnable()
          {
            public void run()
            {
              pFormDataBridge.syncChildren();
            }
          });
          break;
        case IFormComponentInfo.PROP_NAME_CHANGED:
          radComponent.rename((String) evt.getNewValue());
          break;
        case IFormComponentInfo.PROP_VALUE_CHANGED:
          pFormDataBridge._alignFormToAditoProperty((String) evt.getNewValue());
          break;
        default:
          throw new NotImplementedException(propertyName);
      }
    }
  }

}
