package org.netbeans.modules.form.adito;

import org.netbeans.modules.form.*;

import java.util.Collection;

/**
 * @author J. Boesl, 18.05.11
 */
public abstract class AFormModelListener implements FormModelListener
{

  @Override
  public void formChanged(FormModelEvent[] events)
  {
    for (FormModelEvent event : events)
    {
      RADComponent eventComponent = event.getComponent();
      if (eventComponent == null)
      {
        ComponentContainer container = event.getContainer();
        if (container instanceof RADComponent)
          eventComponent = (RADComponent) container;
      }
      try
      {
        switch (event.getChangeType())
        {
          case FormModelEvent.COMPONENT_LAYOUT_CHANGED:
            if (eventComponent != null)
              eventComponent.getARADComponentHandler().layoutPropertiesChanged();
            break;
          case FormModelEvent.COMPONENT_REMOVED:
            if (eventComponent != null)
              eventComponent.getARADComponentHandler().deleted();
            clearProperties(eventComponent);
            break;
          case FormModelEvent.COMPONENTS_REORDERED:
            if (eventComponent != null)
              eventComponent.getARADComponentHandler().reordered();
            break;
          case FormModelEvent.COMPONENT_ADDED:
            if (eventComponent != null)
              eventComponent.getARADComponentHandler().added();
            break;
          case FormModelEvent.FORM_TO_BE_CLOSED:
            Collection<RADComponent> allComponents = event.getFormModel().getAllComponents();
            for (RADComponent component : allComponents)
            {
              ARADComponentHandler aradComponentHandler = component.getARADComponentHandler();
              if (aradComponentHandler != null)
                aradComponentHandler.deinitialize();
            }
            event.getFormModel().removeFormModelListener(this);
          default:
            break;
        }
      }
      catch (Exception e)
      {
        e.printStackTrace(); // TODO
        if (event.isModifying())
        {
          FormModel formModel = event.getFormModel();
          if (formModel == null && eventComponent != null)
            formModel = eventComponent.getFormModel();
          if (formModel != null)
            formModel.forceUndoOfCompoundEdit();
        }
      }
    }
  }

  protected abstract void clearProperties(RADComponent pRADComponent);

}
