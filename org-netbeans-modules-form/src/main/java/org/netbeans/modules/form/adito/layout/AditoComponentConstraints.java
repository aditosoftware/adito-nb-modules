package org.netbeans.modules.form.adito.layout;

import de.adito.aditoweb.swingcommon.layout.aditolayout.AALComponentConstraints;
import org.netbeans.modules.form.FormProperty;
import org.netbeans.modules.form.layoutsupport.LayoutConstraints;
import org.openide.nodes.Node;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;

/**
 * @author J. Boesl, 10.03.11
 */
public class AditoComponentConstraints implements LayoutConstraints
{
  private AALComponentConstraints constraints;
  private Node.Property[] properties;

  public AditoComponentConstraints()
  {
    this(new Rectangle());
  }

  public AditoComponentConstraints(Rectangle pBounds)
  {
    this(pBounds, true, false, false, true);
  }

  public AditoComponentConstraints(Rectangle pBounds, boolean pAnchorLeft, boolean pAnchorBottom,
                                   boolean pAnchorRight, boolean pAnchorTop)
  {
    constraints = new AALComponentConstraints(pBounds, pAnchorLeft, pAnchorBottom, pAnchorRight, pAnchorTop, false);
  }

  public AditoComponentConstraints(Rectangle pBounds, boolean pAnchorLeft, boolean pAnchorBottom,
                                   boolean pAnchorRight, boolean pAnchorTop, boolean pIsBordered)
  {
    constraints = new AALComponentConstraints(pBounds, pAnchorLeft, pAnchorBottom, pAnchorRight, pAnchorTop,
                                              pIsBordered);
  }

  @Override
  public Node.Property[] getProperties()
  {
    if (properties == null)
      properties = _createProperties();
    return properties;
  }

  @Override
  public AALComponentConstraints getConstraintsObject()
  {
    return constraints;
  }

  @Override
  public LayoutConstraints cloneConstraints()
  {
    return new AditoComponentConstraints(constraints.getBounds(), constraints.isAnchorLeft(),
                                         constraints.isAnchorBottom(), constraints.isAnchorRight(),
                                         constraints.isAnchorTop(), constraints.isBordered());
  }

  private Node.Property[] _createProperties()
  {
    return new Node.Property[]{
        new FormProperty("AALC_x", Integer.class, "x", "the x position")
        {
          @Override
          public Object getTargetValue() throws IllegalAccessException, InvocationTargetException
          {
            return constraints.getBounds().x;
          }

          @Override
          public void setTargetValue(Object value) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
          {
            constraints.getBounds().x = (Integer) value;
          }
        },
        new FormProperty("AALC_y", Integer.class, "y", "the y position")
        {
          @Override
          public Object getTargetValue() throws IllegalAccessException, InvocationTargetException
          {
            return constraints.getBounds().y;
          }

          @Override
          public void setTargetValue(Object value) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
          {
            constraints.getBounds().y = (Integer) value;
          }
        },
        new FormProperty("AALC_width", Integer.class, "width", "the width")
        {
          @Override
          public Object getTargetValue() throws IllegalAccessException, InvocationTargetException
          {
            return constraints.getBounds().width;
          }

          @Override
          public void setTargetValue(Object value) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
          {
            constraints.getBounds().width = (Integer) value;
          }
        },
        new FormProperty("AALC_height", Integer.class, "height", "the height")
        {
          @Override
          public Object getTargetValue() throws IllegalAccessException, InvocationTargetException
          {
            return constraints.getBounds().height;
          }

          @Override
          public void setTargetValue(Object value) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
          {
            constraints.getBounds().height = (Integer) value;
          }
        }
    };
  }

}
