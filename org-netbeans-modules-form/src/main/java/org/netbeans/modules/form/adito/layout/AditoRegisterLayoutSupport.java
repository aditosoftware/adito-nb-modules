package org.netbeans.modules.form.adito.layout;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.IAditoLayoutProvider;
import org.netbeans.modules.form.RADVisualComponent;
import org.netbeans.modules.form.layoutsupport.*;

import javax.swing.*;
import java.awt.*;

/**
 * @author J. Boesl, 22.06.11
 */
public class AditoRegisterLayoutSupport extends AbstractLayoutSupport
{

  private static final String ALLOWED_SUB_NAME = "ARegisterTab";

  private int selectedTab = -1;

  /**
   * Gets the supported layout manager class - JTabbedPane.
   *
   * @return the class supported by this delegate
   */
  @Override
  public Class getSupportedClass()
  {
    IAditoLayoutProvider layoutProvider = NbAditoInterface.lookup(IAditoLayoutProvider.class);
    return layoutProvider.getRegisterLayout().getLayoutClass();
  }

  /**
   * Removes one component from the layout (at metadata level).
   * The code structures describing the layout is updated immediately.
   *
   * @param index index of the component in the layout
   */
  @Override
  public void removeComponent(int index)
  {
    super.removeComponent(index);
    if (selectedTab >= getComponentCount())
      selectedTab = getComponentCount() - 1;
  }

  /**
   * This method is called when user clicks on the container in form
   * designer. For JTabbedPane, we it switch the selected TAB.
   *
   * @param p                 Point of click in the container
   * @param container         instance of the container when the click occurred
   * @param containerDelegate effective container delegate of the container
   */
  @Override
  public void processMouseClick(Point p,
                                Container container,
                                Container containerDelegate)
  {
    JTabbedPane tabbedPane = _getTabbedPane(container);
    if (tabbedPane == null)
      return;

    int n = tabbedPane.getTabCount();
    for (int i = 0; i < n; i++)
    {
      Rectangle rect = tabbedPane.getBoundsAt(i);
      if ((rect != null) && rect.contains(p))
      {
        selectedTab = i;
        tabbedPane.setSelectedIndex(i);
        break;
      }
    }
  }

  /**
   * This method is called when a component is selected in Component
   * Inspector.
   *
   * @param index position (index) of the selected component in container
   */
  @Override
  public void selectComponent(int index)
  {
    selectedTab = index; // remember as selected tab
  }

  /**
   * In this method, the layout delegate has a chance to "arrange" real
   * container instance additionally - some other way that cannot be
   * done through layout properties and added components.
   *
   * @param container         instance of a real container to be arranged
   * @param containerDelegate effective container delegate of the container
   */
  @Override
  public void arrangeContainer(Container container,
                               Container containerDelegate)
  {
    JTabbedPane tabbedPane = _getTabbedPane(container);
    if (tabbedPane == null)
      return;

    if (selectedTab >= 0)
    {
      if (tabbedPane.getTabCount() > selectedTab)
      {
        // select the tab
        tabbedPane.setSelectedIndex(selectedTab);

        // workaround for JTabbedPane bug 4190719
        Component comp = tabbedPane.getSelectedComponent();
        if (comp != null)
          comp.setVisible(true);
        tabbedPane.repaint();
      }
    }
    else if (tabbedPane.getTabCount() > 0)
    {
      // workaround for JTabbedPane bug 4190719
      tabbedPane.getComponentAt(0).setVisible(true);
    }
  }

  /**
   * This method should calculate position (index) for a component dragged
   * over a container (or just for mouse cursor being moved over container,
   * without any component).
   *
   * @param container         instance of a real container over/in which the
   *                          component is dragged
   * @param containerDelegate effective container delegate of the container
   * @param component         the real component being dragged; not needed here
   * @param index             position (index) of the component in its current container;
   *                          not needed here
   * @param posInCont         position of mouse in the container delegate; not needed
   * @param posInComp         position of mouse in the dragged component; not needed
   * @return index corresponding to the position of the component in the
   *         container
   */
  @Override
  public int getNewIndex(Container container,
                         Container containerDelegate,
                         Component component,
                         int index,
                         Point posInCont,
                         Point posInComp)
  {
    JTabbedPane tabbedPane = _getTabbedPane(container);
    if (tabbedPane == null)
      return -1;

    return tabbedPane.getTabCount();
  }

  @Override
  public String getAssistantContext()
  {
    return "tabbedPaneLayout"; // NOI18N
  }

  /**
   * This method paints a dragging feedback for a component dragged over
   * a container (or just for mouse cursor being moved over container,
   * without any component).
   *
   * @param container         instance of a real container over/in which the
   *                          component is dragged
   * @param containerDelegate effective container delegate of the container
   * @param component         the real component being dragged; not needed here
   * @param newConstraints    component layout constraints to be presented;
   *                          not used for JTabbedPane
   * @param newIndex          component's index position to be presented; not needed
   * @param g                 Graphics object for painting (with color and line style set)
   * @return whether any feedback was painted (true in this case)
   */
  @Override
  public boolean paintDragFeedback(Container container,
                                   Container containerDelegate,
                                   Component component,
                                   LayoutConstraints newConstraints,
                                   int newIndex,
                                   Graphics g)
  {
    JTabbedPane tabbedPane = _getTabbedPane(container);
    if (tabbedPane == null || !_isAllowedComponent(component))
      return false;

    if ((tabbedPane.getTabCount() == 0) || (component == tabbedPane.getComponentAt(0)))
    {
      Dimension sz = container.getSize();
      Insets insets = container.getInsets();
      sz.width -= insets.left + insets.right;
      sz.height -= insets.top + insets.bottom;
      g.drawRect(0, 0, sz.width, sz.height);
    }
    else
    {
      Rectangle rect = tabbedPane.getComponentAt(0).getBounds();
      g.drawRect(rect.x, rect.y, rect.width, rect.height);
    }
    return true;
  }

  @Override
  public void acceptNewComponents(RADVisualComponent[] components, LayoutConstraints[] constraints, int index)
  {
    for (RADVisualComponent component : components)
    {
      if (!_isAllowed(component.getBeanClass()))
        throw new IllegalArgumentException("Only registertabs can be added to a register component.");
    }
  }

  /**
   * Adds real components to given container (according to layout
   * constraints stored for the components).
   *
   * @param container         instance of a real container to be added to
   * @param containerDelegate effective container delegate of the container
   * @param components        components to be added
   * @param index             position at which to add the components to container
   */
  @Override
  public void addComponentsToContainer(Container container,
                                       Container containerDelegate,
                                       Component[] components,
                                       int index)
  {
    JTabbedPane tabbedPane = _getTabbedPane(container);
    if (tabbedPane == null)
      return;


    for (int i = 0; i < components.length; i++)
    {
      container.add(components[i]);
      //LayoutConstraints constraints = getConstraints(i + index);
      //if (constraints instanceof AditoRegisterConstraints)
      //{
      //  try
      //  {
      //    Object title =
      //        ((FormProperty) constraints.getProperties()[0])
      //            .getRealValue();
      //    Object icon =
      //        ((FormProperty) constraints.getProperties()[1])
      //            .getRealValue();
      //    Object tooltip =
      //        ((FormProperty) constraints.getProperties()[2])
      //            .getRealValue();
      //    tabbedPane.insertTab(
      //        title instanceof String ? (String) title : null,
      //        icon instanceof Icon ? (Icon) icon : null,
      //        components[i],
      //        tooltip instanceof String ? (String) tooltip : null,
      //        index + i);
      //  }
      //  catch (Exception ex)
      //  {
      //    org.openide.ErrorManager.getDefault().notify(org.openide.ErrorManager.INFORMATIONAL, ex);
      //  }
      //}
    }
  }


  /**
   * This method is called to get a default component layout constraints
   * metaobject in case it is not provided (e.g. in addComponents method).
   *
   * @return the default LayoutConstraints object for the supported layout;
   *         null if no component constraints are used
   */
  @Override
  protected LayoutConstraints createDefaultConstraints()
  {
    return new AditoRegisterConstraints(); // NOI18N
  }

  private JTabbedPane _getTabbedPane(Container pContainer)
  {
    if (pContainer != null)
    {
      Component component = pContainer.getComponent(0);
      if (component instanceof JTabbedPane)
        return (JTabbedPane) component;
    }
    return null;
  }

  private static boolean _isAllowedComponent(Component pComponent)
  {
    return pComponent != null && _isAllowed(pComponent.getClass());
  }

  private static boolean _isAllowed(Class pBeanClass)
  {
    return pBeanClass.getSimpleName().equals(ALLOWED_SUB_NAME);
  }

}
