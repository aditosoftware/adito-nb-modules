package org.netbeans.modules.form.adito.components;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.INonSwingContainer;
import org.netbeans.modules.form.*;
import org.netbeans.modules.form.adito.perstistencemanager.*;

/**
 * @author J. Boesl, 01.08.11
 */
public final class AditoFormDesignerExtension
{

  private AditoFormDesignerExtension()
  {
  }

  public static boolean canHandle(RADComponent pMetaComp)
  {
    return pMetaComp != null && (pMetaComp instanceof RADNonVisualContainerVisualComponent ||
        pMetaComp instanceof RADNonVisualContainerNonVisualComponent || canHandle(pMetaComp.getParentComponent()));
  }

  public static RADVisualComponent handle(FormDesigner pFormDesigner, RADComponent pMetaComp)
  {
    if (pMetaComp == null)
      return null;
    if (pMetaComp instanceof RADVisualComponent)
      return (RADVisualComponent) pMetaComp;
    RADVisualComponent handled = handle(pFormDesigner, pMetaComp.getParentComponent());
    if (handled != null)
    {
      Object component = pFormDesigner.getComponent(pMetaComp);
      if (component instanceof INonSwingContainer)
        ((INonSwingContainer) component).setActive();
    }
    return handled;
  }

}
