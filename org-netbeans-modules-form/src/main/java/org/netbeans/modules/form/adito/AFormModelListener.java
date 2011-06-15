package org.netbeans.modules.form.adito;

import org.netbeans.modules.form.*;
import org.openide.awt.StatusDisplayer;
import org.openide.windows.*;

import java.awt.*;
import java.io.IOException;
import java.util.Collection;

/**
 * @author J. Boesl, 18.05.11
 */
public class AFormModelListener implements FormModelListener
{

  private FormModelListenerCallback callback;

  public AFormModelListener(FormModelListenerCallback pCallback)
  {
    callback = pCallback;
  }

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
            callback.clearProperties(eventComponent);
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
        InputOutput io = IOProvider.getDefault().getIO("My Window", false);
        io.select();
        OutputWriter w = io.getOut();
        OutputListener listener = new OutputListener()
        {
          public void outputLineAction(OutputEvent ev)
          {
            StatusDisplayer.getDefault().setStatusText("Hyperlink clicked!");
          }

          public void outputLineSelected(OutputEvent ev)
          {
            // Let's not do anything special.
          }

          public void outputLineCleared(OutputEvent ev)
          {
            // Leave it blank, no state to remove.
          }
        };
        try
        {
          IOColorPrint.print(io, "Exception: " + e.getMessage(), Color.RED);
          IOColorPrint.print(io, " - just for test - ", Color.GREEN);
          w.println("Line of hyperlinked text.", listener, true);
        }
        catch (IOException e1)
        {
          throw new RuntimeException(e1);
        }
      }
    }
  }

  /**
   * Callback damit Packageprotected-Methoden erreicht werden.
   */
  public interface FormModelListenerCallback
  {
    void clearProperties(RADComponent pRADComponent);
  }

}
