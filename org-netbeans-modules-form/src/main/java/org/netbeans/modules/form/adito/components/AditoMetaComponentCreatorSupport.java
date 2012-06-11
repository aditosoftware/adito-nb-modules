package org.netbeans.modules.form.adito.components;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync.*;

/**
 * @author J. Boesl, 11.06.12
 */
public class AditoMetaComponentCreatorSupport
{

  private AditoMetaComponentCreatorSupport()
  {
  }

  public static EContainerType getContainerType(Class pBeanCls)
  {
    IFormComponentInfoProvider propertyInfo = NbAditoInterface.lookup(IFormComponentInfoProvider.class);
    return propertyInfo.getFormPropertyMapping(pBeanCls).getContainerType();
  }

}
