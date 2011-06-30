package org.netbeans.modules.form.adito.perstistencemanager;


import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.INonVisualLayoutComponent;
import org.netbeans.modules.form.*;

import java.util.*;

/**
 * Container der nicht-sichtbare Komponenten enth�lt.
 *
 * @author J. Boesl, 28.06.11
 */
public class RADNonVisualContainerVisualComponent extends RADVisualComponent implements ComponentContainer
{

  private Set<RADComponent> subComponents;
  private INonVisualLayoutComponent beanInstance;


  @Override
  protected void setBeanInstance(Object pBeanInstance)
  {
    if (!(pBeanInstance instanceof INonVisualLayoutComponent))
      throw new IllegalArgumentException("invalid bean component for " + getClass().getSimpleName() + ": " + pBeanInstance);
    super.setBeanInstance(pBeanInstance);
    beanInstance = (INonVisualLayoutComponent) pBeanInstance;
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
    comp.setParentComponent(this);
  }

}