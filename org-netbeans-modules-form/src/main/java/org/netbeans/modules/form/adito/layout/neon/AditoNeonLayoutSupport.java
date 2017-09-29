package org.netbeans.modules.form.adito.layout.neon;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.IAditoLayoutProvider;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.common.*;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.neon.*;
import org.netbeans.modules.form.FormLoaderSettings;
import org.netbeans.modules.form.layoutsupport.*;
import org.openide.util.ImageUtilities;

import javax.swing.*;
import java.awt.*;
import java.beans.BeanInfo;
import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("unused") //LayoutSupportRegistry
public class AditoNeonLayoutSupport extends AbstractLayoutSupport
{
  private static FormLoaderSettings formSettings = FormLoaderSettings.getInstance();

  @Override
  public Class getSupportedClass()
  {
    IAditoLayoutProvider layoutProvider = NbAditoInterface.lookup(IAditoLayoutProvider.class);
    return layoutProvider.getNeonTableLayout().getLayoutClass();
  }

  @Override
  public Image getIcon(int type)
  {
    switch (type)
    {
      case BeanInfo.ICON_COLOR_16x16:
      case BeanInfo.ICON_MONO_16x16:
        String iconURL = "org/netbeans/modules/form/layoutsupport/resources/AbsoluteLayout.gif";
        return ImageUtilities.loadImage(iconURL);
      default:
        String icon32URL = "org/netbeans/modules/form/layoutsupport/resources/AbsoluteLayout32.gif";
        return ImageUtilities.loadImage(icon32URL);
    }
  }

  @Override
  public boolean shouldHaveNode()
  {
    return false;
  }

  @Override
  public LayoutConstraints getNewConstraints(Container container, Container containerDelegate, Component component,
                                             int index, Point posInCont, Point posInComp)
  {
    return _buildConstraints(container, component, null, posInCont, index, posInComp);
  }

  @Override
  public LayoutConstraints getResizedConstraints(Container container, Container containerDelegate, Component component,
                                                 int index, Rectangle originalBounds, Insets sizeChanges,
                                                 Point posInCont)
  {
    return _buildConstraints(container, component, sizeChanges, posInCont, index, null);
  }

  @Override
  public boolean paintDragFeedback(Container container, Container containerDelegate, Component component,
                                   LayoutConstraints newConstraints, int newIndex, Graphics g)
  {
    Rectangle r = ((NeonConstraints) newConstraints).getBounds();
    g.drawRect(r.x, r.y, r.width, r.height);
    return true;
  }

  @Override
  public int getResizableDirections(Container container, Container containerDelegate, Component component, int index)
  {
    Object directions = ((JComponent) component).getClientProperty(IResizeConstants.class);
    if (directions != null)
    {
      Integer aditoDirections = (Integer) directions;
      Integer layoutDirections = 0;

      if ((aditoDirections & IResizeConstants.RESIZE_LEFT) != 0)
        layoutDirections = layoutDirections | RESIZE_LEFT;
      if ((aditoDirections & IResizeConstants.RESIZE_RIGHT) != 0)
        layoutDirections = layoutDirections | RESIZE_RIGHT;
      if ((aditoDirections & IResizeConstants.RESIZE_UP) != 0)
        layoutDirections = layoutDirections | RESIZE_UP;
      if ((aditoDirections & IResizeConstants.RESIZE_DOWN) != 0)
        layoutDirections = layoutDirections | RESIZE_DOWN;

      return layoutDirections;
    }

    return RESIZE_UP | RESIZE_DOWN | RESIZE_LEFT | RESIZE_RIGHT;
  }

  @Override
  protected NeonConstraints createDefaultConstraints()
  {
    return new NeonConstraints();
  }

  private LayoutConstraints _buildConstraints(Container container, Component component,
                                              Insets sizeChanges, Point posInCont, int index, Point pPosInComp)
  {
    JComponent neonComp = (JComponent) component;
    IDropAreaSupport support = (IDropAreaSupport) container;
    IDropArea area = support.getDropArea(posInCont, neonComp, sizeChanges, pPosInComp);
    NeonConstraints constr = (NeonConstraints) getConstraints(index);
    constr = constr == null ? createDefaultConstraints() : (NeonConstraints) constr.cloneConstraints();
    IAditoLayoutConstraints<ICellInfoPropertyTypes> co = constr.getConstraintsObject();
    ICellInfoPropertyTypes typeInfo = co.getTypeInfo();

    try
    {
      co.get(typeInfo.startX()).setValue(area.startX());
      co.get(typeInfo.endX()).setValue(area.endX());
      co.get(typeInfo.startY()).setValue(area.startY());
      co.get(typeInfo.endY()).setValue(area.endY());
      co.get(typeInfo.justify()).setValue(area.justify());
    }
    catch (IllegalAccessException | InvocationTargetException pE)
    {
      //Kein Zugriff auf Adito-Exceptions
      throw new RuntimeException("Cannot fill Constraints Object.", pE);
    }

    constr.setBounds(area.getPaintingRect());
    return constr;
  }
}
