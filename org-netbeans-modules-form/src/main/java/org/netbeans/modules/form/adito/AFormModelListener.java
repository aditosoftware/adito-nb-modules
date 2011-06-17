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
      try
      {
        switch (event.getChangeType())
        {
          case FormModelEvent.COMPONENT_LAYOUT_CHANGED:
            eventComponent.getARADComponentHandler().layoutPropertiesChanged();
            break;
          case FormModelEvent.COMPONENT_REMOVED:
            eventComponent.getARADComponentHandler().delete();
            clearProperties(eventComponent);
            break;
          case FormModelEvent.COMPONENT_ADDED:
            eventComponent.getARADComponentHandler().add();
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
      catch (Exception e)  // TODO: nur temporär ... testing
      {
        e.printStackTrace(); // TODO: stacktrace
        if (event.isModifying())
          eventComponent.getFormModel().forceUndoOfCompoundEdit();

      }
    }
  }

  protected abstract void clearProperties(RADComponent pRADComponent);

}
