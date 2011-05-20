package org.netbeans.modules.form.adito.layout;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.NetbeansAditoInterfaceProvider;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.IAditoLayoutProvider;
import org.netbeans.modules.form.FormLoaderSettings;
import org.netbeans.modules.form.layoutsupport.*;
import org.openide.util.ImageUtilities;

import java.awt.*;
import java.beans.BeanInfo;

/**
 * @author J. Boesl, 07.03.11
 */
public class AditoLayoutSupport extends AbstractLayoutSupport
{

  private static FormLoaderSettings formSettings = FormLoaderSettings.getInstance();


  @Override
  public Class getSupportedClass()
  {
    return NetbeansAditoInterfaceProvider.lookup(IAditoLayoutProvider.class).getAditoAnchorLayoutClass();
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
    int x = posInCont.x;
    int y = posInCont.y;
    int w = -1;
    int h = -1;

    LayoutConstraints constr = getConstraints(index);

    if (component != null)
    {
      int currentW;
      int currentH;

      if (constr instanceof AditoComponentConstraints)
      {
        currentW = ((AditoComponentConstraints) constr).getConstraintsObject().getBounds().width;
        currentH = ((AditoComponentConstraints) constr).getConstraintsObject().getBounds().height;
      }
      else
      {
        currentW = -1;
        currentH = -1;
      }

      Dimension size = component.getSize();
      Dimension prefSize = component.getPreferredSize();

      w = computeConstraintSize(size.width, currentW, prefSize.width);
      h = computeConstraintSize(size.height, currentH, prefSize.height);
    }

    if (posInComp != null)
    {
      x -= posInComp.x;
      y -= posInComp.y;
    }

    if (formSettings.getApplyGridToPosition())
    {
      x = computeGridSize(x, formSettings.getGridX());
      y = computeGridSize(y, formSettings.getGridY());
    }

    return new AditoComponentConstraints(new Rectangle(x, y, w, h));
  }

  @Override
  public boolean paintDragFeedback(Container container, Container containerDelegate, Component component,
                                   LayoutConstraints newConstraints, int newIndex, Graphics g)
  {
    Rectangle r = ((AditoComponentConstraints) newConstraints).getConstraintsObject().getBounds();
    int w = r.width;
    int h = r.height;

    if (w == -1 || h == -1)
    {
      // JInternalFrame.getPreferredSize() behaves suspiciously
      Dimension pref = component instanceof javax.swing.JInternalFrame ?
          component.getSize() : component.getPreferredSize();
      if (w == -1) w = pref.width;
      if (h == -1) h = pref.height;
    }

    if (w < 4) w = 4;
    if (h < 4) h = 4;

    g.drawRect(r.x, r.y, w, h);

    return true;
  }

  @Override
  public int getResizableDirections(Container container, Container containerDelegate, Component component, int index)
  {
    return RESIZE_UP | RESIZE_DOWN | RESIZE_LEFT | RESIZE_RIGHT;
  }

  @Override
  public LayoutConstraints getResizedConstraints(Container container, Container containerDelegate, Component component,
                                                 int index, Rectangle originalBounds, Insets sizeChanges,
                                                 Point posInCont)
  {
    int x, y, w, h;
    x = originalBounds.x;
    y = originalBounds.y;
    w = originalBounds.width;
    h = originalBounds.height;

    Dimension prefSize = component.getPreferredSize();
    int currentW, currentH;

    LayoutConstraints constr = getConstraints(index);
    if (constr instanceof AditoComponentConstraints)
    {
      Rectangle r = ((AditoComponentConstraints) constr).getConstraintsObject().getBounds();
      currentW = r.width;
      currentH = r.height;
    }
    else
    {
      currentW = computeConstraintSize(w, -1, prefSize.width);
      currentH = computeConstraintSize(h, -1, prefSize.height);
    }

    int x2 = x + w;
    int y2 = y + h;

    if (sizeChanges.left + sizeChanges.right == 0)
      w = currentW; // no change
    else
    { // compute resized width and x coordinate
      w += sizeChanges.left + sizeChanges.right;
      w = w <= 0 ? -1 : computeConstraintSize(w, currentW, prefSize.width);

      if (w > 0)
      {
        if (formSettings.getApplyGridToSize())
        {
          int gridW = computeGridSize(w, formSettings.getGridX());
          x -= sizeChanges.left +
              (gridW - w) * sizeChanges.left
                  / (sizeChanges.left + sizeChanges.right);
          w = gridW;
        }
      }
      else if (sizeChanges.left != 0)
        x = x2 - prefSize.width;
    }

    if (sizeChanges.top + sizeChanges.bottom == 0)
      h = currentH; // no change
    else
    { // compute resized height and y coordinate
      h += sizeChanges.top + sizeChanges.bottom;
      h = h <= 0 ? -1 : computeConstraintSize(h, currentH, prefSize.height);

      if (h > 0)
      {
        if (formSettings.getApplyGridToSize())
        {
          int gridH = computeGridSize(h, formSettings.getGridY());
          y -= sizeChanges.top +
              (gridH - h) * sizeChanges.top
                  / (sizeChanges.top + sizeChanges.bottom);
          h = gridH;
        }
      }
      else if (sizeChanges.top != 0)
        y = y2 - prefSize.height;
    }

    return new AditoComponentConstraints(new Rectangle(x, y, w, h));
  }

  @Override
  public void addComponentsToContainer(Container container, Container containerDelegate, Component[] components, int index)
  {
    super.addComponentsToContainer(container, containerDelegate, components, index);
  }

  @Override
  protected LayoutConstraints createDefaultConstraints()
  {
    return new AditoComponentConstraints();
  }

  private static int computeConstraintSize(int newSize, int currSize, int prefSize)
  {
    return newSize != -1 && (newSize != prefSize
        || (currSize != -1 && currSize == prefSize)) ?
        newSize : -1;
  }

  private static int computeGridSize(int size, int step)
  {
    if (step <= 0)
      return size;
    int mod = size % step;
    return mod >= step / 2 ? size + step - mod : size - mod;
  }

}
