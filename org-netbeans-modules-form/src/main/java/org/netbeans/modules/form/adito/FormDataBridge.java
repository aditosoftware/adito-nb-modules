package org.netbeans.modules.form.adito;

import com.google.common.base.*;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync.*;
import org.jetbrains.annotations.NotNull;
import org.netbeans.modules.form.*;
import org.openide.nodes.Node;

import java.beans.*;
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

  IFormComponentInfo getComponentInfo()
  {
    return componentInfo;
  }

  void layoutPropertiesChanged()
  {
    if (radComponent instanceof RADVisualComponent)
    {
      RADVisualComponent radVisualComponent = (RADVisualComponent) radComponent;
      _radPropertiesChanged(radVisualComponent.getConstraintsProperties());
    }
  }

  private void _radPropertiesChanged(Node.Property... pProperties)
  {
    try
    {
      for (Node.Property formProperty : pProperties)
        _alignFormToAditoProp(formProperty);
    }
    catch (InvocationTargetException e)
    {
      radComponent.getFormModel().forceUndoOfCompoundEdit(); // TODO: Fehler dem User mitteilen (output)
    }
  }

  private void _alignFormToAditoProp(Node.Property pFormProperty) throws InvocationTargetException
  {
    try
    {
      String formPropName = pFormProperty.getName();
      String aditoPropName = componentPropertyMapping.getAditoPropName(formPropName);
      if (Strings.isNullOrEmpty(aditoPropName))
        return;
      Node.Property aditoProperty = componentInfo.getProperty(aditoPropName);
      if (aditoProperty == null)
      {
        _logInvalidMapping(aditoPropName, formPropName, false);
        return;
      }
      Object fieldValue = aditoProperty.getValue();
      Object formPropertyValue = pFormProperty.getValue();
      if (!Objects.equal(fieldValue, formPropertyValue))
      {
        try
        {
          aditoProperty.setValue(formPropertyValue);
          // TODO: es kann sein, dass andere Properties sich auch ‰ndern.
          RADComponentNode nodeReference = radComponent.getNodeReference();
          if (nodeReference != null)
            nodeReference.firePropertyChangeHelper(formPropName, null, null);
        }
        catch (InvocationTargetException e)
        {
          // TODO TODO TODO TODO TODO
          // wenn die Property im Sheet noch nicht initialisiert ist schmeiﬂts ihn wenn primitive Daten
          // abgeglichen werden sollen.
        }
      }
    }
    catch (IllegalAccessException e)
    {
      throw new RuntimeException(e); // TODO: runtimeEx
    }
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
    aditoPropertyChangeListener = null;
    formPropertyChangeListener = null;
    //for (FileObject fileObject : modelDataObject.getPrimaryFile().getChildren())
    //fileObject.removeFileChangeListener(propertyChangeListener);
  }

  private PropertyChangeListener _createFormPropertyChangeListener(/*final String pRadPropName,
                                                                   final String pAditoPropName*/)
  {
    return new PropertyChangeListener()
    {
      @Override
      public void propertyChange(PropertyChangeEvent evt)
      {
        Node.Property property = (Node.Property) evt.getSource();
        _radPropertiesChanged(property);
        //System.out.println(((RADProperty)).getName());
        //Node.Property aditoProperty = componentInfo.getProperty(pAditoPropName);
        //if (aditoProperty != null)
        //{
        //  try
        //  {
        //    FormProperty formProperty = _getFormProperty(pRadPropName);
        //    if (formProperty != null)
        //    {
        //      Object oldValue = aditoProperty.getValue();
        //      Object newValue = formProperty.getValue();
        //      if (!Objects.equal(oldValue, newValue))
        //        aditoProperty.setValue(newValue);
        //    }
        //  }
        //  catch (IllegalAccessException e)
        //  {
        //    e.printStackTrace(); // TODO: errorHandling
        //  }
        //  catch (InvocationTargetException e)
        //  {
        //    e.printStackTrace(); // TODO: errorHandling
        //  }
        //}
      }
    };
  }

  private PropertyChangeListener _createAditoPropertyChangeListener()
  {
    return new PropertyChangeListener()
    {
      @Override
      public void propertyChange(PropertyChangeEvent evt)
      {
        try
        {
          String aditoPropName = evt.getPropertyName();
          Node.Property aditoProperty = componentInfo.getProperty(aditoPropName);
          String mappedName = componentPropertyMapping.getRadPropName(aditoPropName);
          FormProperty formProperty = _getFormProperty(mappedName);
          if (formProperty == null)
          {
            _logInvalidMapping(aditoPropName, mappedName, true);
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
        catch (NullPointerException e)
        {
          throw new RuntimeException(e); // TODO: runtimeEx
        }
      }
    };
  }

  private FormProperty _getFormProperty(String pRadPropName)
  {
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
    String pathDetail = "path " + radComponent.getARADComponentHandler().getModelDataObject().getPrimaryFile().getPath();
    String detail = pAditoToForm ? compDetail + " with " + pathDetail : pathDetail + " with " + compDetail;
    Logger.getLogger(FormDataBridge.class.getSimpleName()).log(
        Level.WARNING,
        prop1 + " is mapped to " + prop2 + " but doesn't exist at " + detail);
  }

}
