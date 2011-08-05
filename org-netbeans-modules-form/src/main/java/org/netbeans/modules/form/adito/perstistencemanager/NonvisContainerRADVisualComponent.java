package org.netbeans.modules.form.adito.perstistencemanager;


import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.INonSwingContainer;
import org.netbeans.modules.form.*;

/**
 * Container der nicht-sichtbare Komponenten enthält.
 *
 * @author J. Boesl, 28.06.11
 */
public class NonvisContainerRADVisualComponent extends RADVisualComponent implements ComponentContainer
{

  private NonvisContainer<INonSwingContainer> nonvisContainer = new NonvisContainer<INonSwingContainer>(
      INonSwingContainer.class)
  {
    @Override
    void assignParentComponent(RADComponent pComp)
    {
      pComp.setParentComponent(NonvisContainerRADVisualComponent.this);
    }
  };


  @Override
  protected void setBeanInstance(Object pBeanInstance)
  {
    nonvisContainer.setBeanInstance(pBeanInstance);
    super.setBeanInstance(pBeanInstance);
  }

  INonSwingContainer getBeanInstanceTyped()
  {
    return nonvisContainer.getBeanInstance();
  }

  @Override
  public NonvisContainerRADComponent[] getSubBeans()
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
