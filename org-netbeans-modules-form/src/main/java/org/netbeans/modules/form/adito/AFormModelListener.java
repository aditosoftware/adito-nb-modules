package org.netbeans.modules.form.adito;

import org.netbeans.modules.form.*;
import org.openide.util.RequestProcessor;

import java.util.Collection;
import java.util.logging.*;

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
          case FormModelEvent.CONTAINER_LAYOUT_CHANGED:
            if (eventComponent != null && eventComponent instanceof RADVisualFormContainer)
            {
              RADComponent layoutRadComponent = ((RADVisualFormContainer) eventComponent).getLayoutSupport().getLayoutRadComponent();
              if (layoutRadComponent != null)
                layoutRadComponent.getARADComponentHandler().layoutPropertiesChanged(event.getPropertyName());
            }
            break;
          case FormModelEvent.COMPONENT_LAYOUT_CHANGED:
            if (eventComponent != null)
              eventComponent.getARADComponentHandler().layoutPropertiesChanged(event.getPropertyName());
            break;
          case FormModelEvent.COMPONENT_REMOVED:
            if (eventComponent != null)
              eventComponent.getARADComponentHandler().deleted();
            clearProperties(eventComponent);
            break;
          case FormModelEvent.COMPONENTS_REORDERED:
            if (eventComponent != null)
              eventComponent.getARADComponentHandler().childrenReordered();
            break;
          case FormModelEvent.COMPONENT_ADDED:
            if (eventComponent != null)
              eventComponent.getARADComponentHandler().added();
            break;
          case FormModelEvent.FORM_TO_BE_CLOSED:
            RequestProcessor.getDefault().submit(() -> {
              Collection<RADComponent> allComponents = event.getFormModel().getAllComponents();
              for (RADComponent component : allComponents)
              {
                ARADComponentHandler aradComponentHandler = component.getARADComponentHandler();
                if (aradComponentHandler != null)
                  aradComponentHandler.deinitialize();
              }
            });
            event.getFormModel().removeFormModelListener(this);
          default:
            break;
        }
      }
      catch (EventCouldNotBeProcessedException e)
      {
        Logger.getLogger(AFormModelListener.class.getCanonicalName()).log(Level.WARNING, "", e);
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
