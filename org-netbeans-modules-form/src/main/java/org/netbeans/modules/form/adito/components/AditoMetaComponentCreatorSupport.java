package org.netbeans.modules.form.adito.components;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync.*;
import de.adito.propertly.core.spi.IPropertyPitProvider;
import org.netbeans.modules.form.*;
import org.netbeans.modules.form.adito.perstistencemanager.*;

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

  public static Class<?> getComponentClass(IPropertyPitProvider<?, ?, ?> pModel)
  {
    IFormComponentInfoProvider propertyInfo = NbAditoInterface.lookup(IFormComponentInfoProvider.class);
    IFormComponentInfo modelPropProvider = propertyInfo.createComponentInfo(pModel);

    IFormComponentPropertyMapping formPropertyMapping = modelPropProvider.getFormPropertyMapping();
    if (formPropertyMapping == null)
      throw new RuntimeException("no form property mapping for '" + pModel.getClass().getCanonicalName() + "'.");

    return formPropertyMapping.getComponentClass();
  }

  public static RADComponent createComponent(Class pBeanCls) throws PersistenceException
  {
    EContainerType containerType = getContainerType(pBeanCls);

    // create a new metacomponent
    RADComponent newComponent;
    switch (containerType)
    {
      case NONE:
        if (FormUtils.isVisualizableClass(pBeanCls))
          newComponent = new RADVisualComponent();
        else
          newComponent = new RADComponent();
        break;
      case VISUAL:
        newComponent = new RADVisualContainer();
        break;
      case NONVISUAL:
        if (FormUtils.isVisualizableClass(pBeanCls))
          newComponent = new NonvisContainerRADVisualComponent();
        else
          newComponent = new NonvisContainerRADComponent();
        break;
      default:
        throw new PersistenceException("Unknown component element: " + pBeanCls.toString()); // NOI18N
    }

    if (pBeanCls == InvalidComponent.class)
      newComponent.setValid(false);

    return newComponent;
  }

}
