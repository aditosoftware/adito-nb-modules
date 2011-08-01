package org.netbeans.modules.form.adito.perstistencemanager;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.INonVisualsContainer;
import org.netbeans.modules.form.*;

import java.util.*;

/**
 * @author J. Boesl, 26.07.11
 */
abstract class NonVisComponentContainer implements ComponentContainer
{

  private Set<RADComponent> subComponents;
  private INonVisualsContainer beanInstance;


  abstract void assignParentComponent(RADComponent pComp);


  void setBeanInstance(Object pBeanInstance)
  {
    if (!(pBeanInstance instanceof INonVisualsContainer))
      throw new IllegalArgumentException("invalid bean component for " + getClass().getSimpleName() + ": " + pBeanInstance);
    beanInstance = (INonVisualsContainer) pBeanInstance;
  }

  @Override
  public RADComponent[] getSubBeans()
  {
    RADComponent[] components = new RADComponent[subComponents.size()];
    subComponents.toArray(components);
    return components;
  }

  @Override
  public void initSubComponents(RADComponent[] initComponents)
  {
    subComponents = new LinkedHashSet<RADComponent>(initComponents.length);
    for (RADComponent initComponent : initComponents)
      _add(initComponent);
  }

  @Override
  public void reorderSubComponents(int[] perm)
  {
    RADComponent[] subs = getSubBeans();
    RADComponent[] components = new RADComponent[subs.length];
    for (int i = 0; i < perm.length; i++)
      components[perm[i]] = subs[i];

    subComponents.clear();
    subComponents.addAll(Arrays.asList(components));
  }

  @Override
  public void add(RADComponent comp)
  {
    _add(comp);
  }

  @Override
  public void remove(RADComponent comp)
  {
    beanInstance.removeNonVisComp(comp.getBeanInstance());
    if (subComponents.remove(comp))
      comp.setParentComponent(null);
  }

  @Override
  public int getIndexOf(RADComponent comp)
  {
    RADComponent[] subs = getSubBeans();
    for (int i = 0; i < subs.length; i++)
      if (subs[i].equals(comp))
        return i;
    return -1;
  }

  private void _add(RADComponent comp)
  {
    beanInstance.addNonVisComp(comp.getBeanInstance());
    subComponents.add(comp);
    assignParentComponent(comp);
  }

}
