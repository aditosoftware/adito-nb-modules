package org.netbeans.modules.form.adito.components;

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
    return pMetaComp != null && (pMetaComp instanceof NonvisContainerRADVisualComponent ||
        pMetaComp instanceof NonvisContainerRADComponent || canHandle(pMetaComp.getParentComponent()));
  }

  public static RADVisualComponent handle(RADComponent pMetaComp)
  {
    if (pMetaComp == null)
      return null;
    if (pMetaComp instanceof RADVisualComponent)
      return (RADVisualComponent) pMetaComp;
    return handle(pMetaComp.getParentComponent());
  }

}
