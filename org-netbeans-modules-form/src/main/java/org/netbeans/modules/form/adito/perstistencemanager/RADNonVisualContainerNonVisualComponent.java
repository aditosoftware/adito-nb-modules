package org.netbeans.modules.form.adito.perstistencemanager;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.INonSwingComponent;
import org.netbeans.modules.form.*;

/**
 * @author J. Boesl, 26.07.11
 */
public class RADNonVisualContainerNonVisualComponent extends RADComponent implements ComponentContainer
{

  private NonVisComponentContainer<INonSwingComponent> nonvisContainer = new NonVisComponentContainer<INonSwingComponent>(
      INonSwingComponent.class)
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
    nonvisContainer.setBeanInstance(beanInstance);
    super.setBeanInstance(beanInstance);
  }

  INonSwingComponent getBeanInstanceTyped()
  {
    return nonvisContainer.getBeanInstance();
  }

  @Override
  public RADNonVisualContainerNonVisualComponent[] getSubBeans()
  {
    return nonvisContainer.getSubBeans();
  }

  @Override
  public void initSubComponents(RADComponent[] initComponents)
  {
    nonvisContainer.initSubComponents(initComponents);
  }

  @Override
  public void reorderSubComponents(int[] perm)
  {
    nonvisContainer.reorderSubComponents(perm);
  }

  @Override
  public void add(RADComponent comp)
  {
    nonvisContainer.add(comp);
  }

  @Override
  public void remove(RADComponent comp)
  {
    nonvisContainer.remove(comp);
  }

  @Override
  public int getIndexOf(RADComponent comp)
  {
    return nonvisContainer.getIndexOf(comp);
  }

}
