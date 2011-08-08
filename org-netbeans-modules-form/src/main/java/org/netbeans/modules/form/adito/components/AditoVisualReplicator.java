package org.netbeans.modules.form.adito.components;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.*;
import org.netbeans.modules.form.*;
import org.netbeans.modules.form.adito.perstistencemanager.*;

import java.util.List;

/**
 * @author J. Boesl, 08.08.11
 */
public abstract class AditoVisualReplicator
{

  protected AditoVisualReplicator()
  {
  }

  public boolean canHandle(ComponentContainer pMetacomp)
  {
    return _canHandle(pMetacomp);
  }

  public boolean canHandle(RADComponent pMetacomp)
  {
    return _canHandle(pMetacomp);
  }

  public void cloneComponent(RADComponent pMetacomp, Object pCompClone, List<RADProperty> relativeProperties)
      throws Exception
  {
    ComponentContainer metacont = (ComponentContainer) pMetacomp;
    INonSwingContainer nonVisLayoutCompClone = (INonSwingContainer) pCompClone;
    for (RADComponent sub : metacont.getSubBeans())
    {
      Object subClone = getClonedComponent(sub);
      if (subClone == null)
        subClone = cloneComponent(sub, relativeProperties);
      nonVisLayoutCompClone.addCompNonSwing((INonSwingComponent) subClone);
    }
  }

  public void removeComponent(RADComponent pMetacomp, ComponentContainer pMetacont)
  {
    INonSwingContainer instance = _getContainer(pMetacont);
    instance.removeCompNonSwing((INonSwingComponent) getClonedComponent(pMetacomp));
  }

  public void reorder(ComponentContainer pCompContainer)
  {
    INonSwingContainer nonSwingContainer = _getContainer(pCompContainer);
    RADComponent[] subBeans = pCompContainer.getSubBeans();
    for (RADComponent radComponent : subBeans)
      removeComponent(radComponent, pCompContainer);
    for (RADComponent radComponent : subBeans)
      nonSwingContainer.addCompNonSwing((INonSwingComponent) getClonedComponent(radComponent));
  }

  private INonSwingContainer _getContainer(ComponentContainer pContainer)
  {
    RADComponent container = (RADComponent) pContainer;
    return (INonSwingContainer) getClonedComponent(container);
  }

  private boolean _canHandle(Object pMetacomp)
  {
    return pMetacomp instanceof NonvisContainerRADVisualComponent || pMetacomp instanceof NonvisContainerRADComponent;
  }


  protected abstract Object cloneComponent(RADComponent metacomp, List<RADProperty> relativeProperties) throws Exception;

  protected abstract Object getClonedComponent(RADComponent metacomp);

}
