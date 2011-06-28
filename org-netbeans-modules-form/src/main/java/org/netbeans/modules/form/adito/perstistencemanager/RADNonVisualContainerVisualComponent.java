package org.netbeans.modules.form.adito.perstistencemanager;


import org.netbeans.modules.form.*;

import java.util.*;

/**
 * @author J. Boesl, 28.06.11
 */
public class RADNonVisualContainerVisualComponent extends RADVisualComponent implements ComponentContainer
{

  private Set<RADComponent> subComponents;

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
    {
      subComponents.add(initComponent);
      initComponent.setParentComponent(this);
    }
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
    subComponents.add(comp);
    comp.setParentComponent(this);
  }

  @Override
  public void remove(RADComponent comp)
  {
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

}
