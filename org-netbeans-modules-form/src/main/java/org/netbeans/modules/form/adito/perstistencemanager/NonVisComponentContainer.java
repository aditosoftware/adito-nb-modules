package org.netbeans.modules.form.adito.perstistencemanager;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.INonSwingContainer;
import org.netbeans.modules.form.*;

import java.util.*;

/**
 * @author J. Boesl, 26.07.11
 */
abstract class NonVisComponentContainer<T extends INonSwingContainer> implements ComponentContainer
{

  private final Class<T> childType;
  private Set<RADNonVisualContainerNonVisualComponent> subComponents;
  private T beanInstance;


  abstract void assignParentComponent(RADComponent pComp);


  protected NonVisComponentContainer(Class<T> pChildType)
  {
    childType = pChildType;
  }

  void setBeanInstance(Object pBeanInstance)
  {
    T t = _check(pBeanInstance, childType);
    if (t == null)
      throw new IllegalArgumentException("invalid bean component for " + getClass() + ": " + pBeanInstance);
    beanInstance = t;
  }

  public T getBeanInstance()
  {
    return beanInstance;
  }

  @Override
  public RADNonVisualContainerNonVisualComponent[] getSubBeans()
  {
    RADNonVisualContainerNonVisualComponent[] components = new RADNonVisualContainerNonVisualComponent[subComponents.size()];
    subComponents.toArray(components);
    return components;
  }

  @Override
  public void initSubComponents(RADComponent[] initComponents)
  {
    subComponents = new LinkedHashSet<RADNonVisualContainerNonVisualComponent>(initComponents.length);
    for (RADComponent initComponent : initComponents)
      _add(initComponent);
  }

  @Override
  public void reorderSubComponents(int[] perm)
  {
    RADNonVisualContainerNonVisualComponent[] subs = getSubBeans();
    RADNonVisualContainerNonVisualComponent[] components = new RADNonVisualContainerNonVisualComponent[subs.length];
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
  public void remove(RADComponent pComp)
  {
    RADNonVisualContainerNonVisualComponent nonvis = _check(pComp, RADNonVisualContainerNonVisualComponent.class);
    if (nonvis == null)
      return;
    beanInstance.removeNonSwingComp(nonvis.getBeanInstanceTyped());
    if (subComponents.remove(nonvis))
      //noinspection NullableProblems
      nonvis.setParentComponent(null);
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

  private void _add(RADComponent pComp)
  {
    RADNonVisualContainerNonVisualComponent nonvis = _check(pComp, RADNonVisualContainerNonVisualComponent.class);
    if (nonvis == null)
      return;
    beanInstance.addNonSwingComp(nonvis.getBeanInstanceTyped());
    if (subComponents.add(nonvis))
      assignParentComponent(nonvis);
  }

  private <T> T _check(Object pBeanInstance, Class<T> pChildType)
  {
    if (pBeanInstance == null || !(pChildType.isAssignableFrom(pBeanInstance.getClass())))
      return null;
    //noinspection unchecked
    return (T) pBeanInstance;
  }

}
