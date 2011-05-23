package org.netbeans.modules.form.adito;

import com.google.common.base.Objects;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync.*;
import org.jetbrains.annotations.NotNull;
import org.netbeans.modules.form.*;
import org.openide.nodes.Node;

import java.beans.*;
import java.lang.reflect.InvocationTargetException;

/**
 * @author J. Boesl, 31.03.11
 */
public class FormDataBridge
{

  private final RADComponent radComponent;
  private final IAditoPropertyProvider aditoModelPropProvider;
  private final IAditoComponentDetailProvider componentDetailProvider;

  private PropertyChangeListener propertyChangeListener;


  public FormDataBridge(@NotNull RADComponent pRadComponent, @NotNull IAditoPropertyProvider pAditoModelPropProvider,
                        @NotNull IAditoComponentDetailProvider pComponentDetailProvider)
  {
    radComponent = pRadComponent;
    aditoModelPropProvider = pAditoModelPropProvider;
    componentDetailProvider = pComponentDetailProvider;
  }

  IAditoPropertyProvider getAditoModelPropProvider()
  {
    return aditoModelPropProvider;
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
          String aditoPropName = componentDetailProvider.getAditoPropName(formProperty.getName());
          Node.Property aditoProperty = aditoModelPropProvider.getProperty(aditoPropName);
          if (aditoProperty != null)
          {
            Object fieldValue = aditoProperty.getValue();
            Object formPropertyValue = formProperty.getValue();
            if (fieldValue == null || !fieldValue.equals(formPropertyValue))
            {
              aditoProperty.setValue(formPropertyValue);
              radComponent.getNodeReference().firePropertyChangeHelper(formProperty.getName(), null, null);
            }
          }
        }
      }
      catch (InvocationTargetException e)
      {
        radComponent.getFormModel().forceUndoOfCompoundEdit(); // TODO: Fehler dem User mitteilen (output)
      }
      catch (IllegalAccessException e)
      {
        throw new RuntimeException(e); // TODO: runtimeEx
      }
    }
  }

  void registerListeners()
  {
    if (propertyChangeListener == null)
    {
      propertyChangeListener = _createAditoPropertyChangeListener();
      aditoModelPropProvider.addPropertyListener(propertyChangeListener);
      for (String aditoPropName : aditoModelPropProvider.getPropertyNames())
      {
        String radPropName = componentDetailProvider.getRadPropName(aditoPropName);
        if (radPropName != null && !radPropName.isEmpty())
        {
          FormProperty formProperty = _getFormProperty(radPropName);
          if (formProperty != null)
            formProperty.addPropertyChangeListener(_createFormPropertyChangeListener(radPropName, aditoPropName));
        }
      }
    }
  }

  void unregisterAll()
  {
    aditoModelPropProvider.removePropertyListener(propertyChangeListener);
    propertyChangeListener = null;
    //for (FileObject fileObject : modelDataObject.getPrimaryFile().getChildren())
    //fileObject.removeFileChangeListener(propertyChangeListener);
  }

  private PropertyChangeListener _createFormPropertyChangeListener(final String pRadPropName, final String pAditoPropName)
  {
    return new PropertyChangeListener()
    {
      @Override
      public void propertyChange(PropertyChangeEvent evt)
      {
        Node.Property aditoProperty = aditoModelPropProvider.getProperty(pAditoPropName);
        if (aditoProperty != null)
        {
          try
          {
            FormProperty formProperty = _getFormProperty(pRadPropName);
            if (formProperty != null)
            {
              Object oldValue = aditoProperty.getValue();
              Object newValue = formProperty.getValue();
              if (!Objects.equal(oldValue, newValue))
                aditoProperty.setValue(newValue);
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
          Node.Property aditoProperty = aditoModelPropProvider.getProperty(aditoPropName);
          FormProperty formProperty = _getFormProperty(componentDetailProvider.getRadPropName(aditoPropName));
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

  private IAditoComponentInfoProvider getPropertyInfo()
  {
    return NbAditoInterface.lookup(IAditoComponentInfoProvider.class);
  }

}
