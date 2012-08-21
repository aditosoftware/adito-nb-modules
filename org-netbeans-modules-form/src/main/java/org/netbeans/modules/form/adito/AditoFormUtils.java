package org.netbeans.modules.form.adito;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync.*;
import org.netbeans.modules.form.RADComponent;
import org.openide.filesystems.FileObject;
import org.openide.nodes.Node;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author J. Boesl, 27.06.11
 */
public final class AditoFormUtils
{

  private AditoFormUtils()
  {
  }

  public static void invokeLater(final Runnable pRunnable, final int pCount)
  {
    if (pCount < 1)
    {
      pRunnable.run();
    }
    else
    {
      SwingUtilities.invokeLater(new Runnable()
      {
        public void run()
        {
          invokeLater(pRunnable, pCount - 1);
        }
      });
    }
  }

  public static void copyValuesFromModelToComponent(RADComponent pComponent)
      throws InvocationTargetException, IllegalAccessException
  {
    FileObject modelFileObject = pComponent.getARADComponentHandler().getModelFileObject();
    if (modelFileObject == null)
      throw new IllegalStateException(pComponent.toString());

    IFormComponentInfoProvider compInfoProvider = NbAditoInterface.lookup(IFormComponentInfoProvider.class);
    IFormComponentInfo componentInfo = compInfoProvider.createComponentInfo(modelFileObject);
    for (Map.Entry<String, Object> entry : componentInfo.getInitialValues().entrySet())
    {
      Node.Property radProperty = pComponent.getPropertyByName(entry.getKey());
      if (radProperty != null)
        radProperty.setValue(entry.getValue());
    }
  }

}
