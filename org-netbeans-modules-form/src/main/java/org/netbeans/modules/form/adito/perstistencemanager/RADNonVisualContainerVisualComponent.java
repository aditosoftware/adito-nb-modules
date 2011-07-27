package org.netbeans.modules.form.adito.perstistencemanager;


import org.netbeans.modules.form.*;

/**
 * Container der nicht-sichtbare Komponenten enthält.
 *
 * @author J. Boesl, 28.06.11
 */
public class RADNonVisualContainerVisualComponent extends RADVisualComponent implements ComponentContainer
{

  private NonVisComponentContainer nonVisContainer = new NonVisComponentContainer()
  {
    @Override
    void assignParentComponent(RADComponent pComp)
    {
      pComp.setParentComponent(RADNonVisualContainerVisualComponent.this);
    }
  };

  @Override
  public void setName(String name)
  {
    nonVisContainer.setName(name);
    super.setName(name);
  }

  @Override
  protected void setBeanInstance(Object pBeanInstance)
  {
    nonVisContainer.setBeanInstance(pBeanInstance);
    super.setBeanInstance(pBeanInstance);
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
