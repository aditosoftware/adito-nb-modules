package org.netbeans.modules.form.adito.perstistencemanager;

import org.netbeans.modules.form.RADComponent;

/**
 * @author J. Boesl, 26.07.11
 */
public class RADNonVisualContainerNonVisualComponent extends RADComponent implements INonVisComponentContainer
{

  private NonVisComponentContainer nonVisContainer = new NonVisComponentContainer()
  {
    @Override
    void assignParentComponent(RADComponent pComp)
    {
      pComp.setParentComponent(RADNonVisualContainerNonVisualComponent.this);
    }
  };

  @Override
  protected void setBeanInstance(Object beanInstance)
  {
    nonVisContainer.setBeanInstance(beanInstance);
    super.setBeanInstance(beanInstance);
  }

  public void setActive()
  {
    nonVisContainer.setActive();
  }

  @Override
  public RADComponent[] getSubBeans()
  {
    return nonVisContainer.getSubBeans();
  }

  @Override
  public void initSubComponents(RADComponent[] initComponents)
  {
    nonVisContainer.initSubComponents(initComponents);
  }

  @Override
  public void reorderSubComponents(int[] perm)
  {
    nonVisContainer.reorderSubComponents(perm);
  }

  @Override
  public void add(RADComponent comp)
  {
    nonVisContainer.add(comp);
  }

  @Override
  public void remove(RADComponent comp)
  {
    nonVisContainer.remove(comp);
  }

  @Override
  public int getIndexOf(RADComponent comp)
  {
    return nonVisContainer.getIndexOf(comp);
  }

}
