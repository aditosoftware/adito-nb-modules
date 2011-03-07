package org.netbeans.modules.form.adito.layout;

import de.adito.aditoweb.swingcommon.layout.aditolayout.*;
import org.netbeans.modules.form.FormProperty;
import org.netbeans.modules.form.codestructure.*;
import org.netbeans.modules.form.layoutsupport.*;
import org.openide.nodes.Node;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;

/**
 * @author J. Boesl, 07.03.11
 */
public class AditoLayoutSupport extends AbstractLayoutSupport
{

  public AditoLayoutSupport()
  {
  }

  @Override
  public Class getSupportedClass()
  {
    return AditoAnchorLayout.class;
  }


  @Override
  public LayoutConstraints getNewConstraints(Container container,
                                             Container containerDelegate,
                                             Component component,
                                             int index,
                                             Point posInCont,
                                             Point posInComp)
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

      if (constr instanceof AALComponentConstraints)
      {
        currentW = ((AALComponentConstraints) constr).getBounds().width;
        currentH = ((AALComponentConstraints) constr).getBounds().height;
      }
      else
      {
        currentW = -1;
        currentH = -1;
      }

      Dimension size = component.getSize();
      Dimension prefSize = component.getPreferredSize();

//      w = computeConstraintSize(size.width, currentW, prefSize.width);
//      h = computeConstraintSize(size.height, currentH, prefSize.height);
    }

//    if (posInComp != null)
//    {
//      x -= posInComp.x;
//      y -= posInComp.y;
//    }
//
//    if (formSettings.getApplyGridToPosition())
//    {
//      x = computeGridSize(x, formSettings.getGridX());
//      y = computeGridSize(y, formSettings.getGridY());
//    }
//
//    assistantParams = new Object[]{x, y};
    return new AditoComponentConstraints(new Rectangle(x, y, w, h));
  }


  @Override
  protected LayoutConstraints createDefaultConstraints()
  {
    return new AditoComponentConstraints();
  }

  @Override
  protected CodeExpression createConstraintsCode(CodeGroup constrCode, LayoutConstraints constr, CodeExpression compExp, int index)
  {
    return super.createConstraintsCode(constrCode, constr, compExp, index);    //To change body of overridden methods use File | Settings | File Templates.
  }

  @Override
  protected void createComponentCode(CodeGroup componentCode, CodeExpression compExp, int index)
  {
    super.createComponentCode(componentCode, compExp, index);    //To change body of overridden methods use File | Settings | File Templates.
  }


  static class AditoComponentConstraints implements LayoutConstraints
  {
    private AALComponentConstraints constraints;
    private Node.Property[] properties;

    private AditoComponentConstraints()
    {
      this(new Rectangle());
    }

    private AditoComponentConstraints(Rectangle pBounds)
    {
      this(pBounds, true, false, false, true);
    }

    private AditoComponentConstraints(Rectangle pBounds, boolean pAnchorLeft, boolean pAnchorBottom,
                                      boolean pAnchorRight, boolean pAnchorTop)
    {
      constraints = new AALComponentConstraints(pBounds, pAnchorLeft, pAnchorBottom, pAnchorRight, pAnchorTop, false);
    }

    private AditoComponentConstraints(Rectangle pBounds, boolean pAnchorLeft, boolean pAnchorBottom,
                                      boolean pAnchorRight, boolean pAnchorTop, boolean pIsBordered)
    {
      constraints = new AALComponentConstraints(pBounds, pAnchorLeft, pAnchorBottom, pAnchorRight, pAnchorTop,
                                                pIsBordered);
    }

    @Override
    public Node.Property[] getProperties()
    {
      if (properties != null)
        properties = _createProperties();
      return properties;
    }

    @Override
    public Object getConstraintsObject()
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
          new FormProperty("x", Integer.class, "x", "the x position")
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
          new FormProperty("y", Integer.class, "y", "the y position")
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
          new FormProperty("width", Integer.class, "width", "the width")
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
          new FormProperty("height", Integer.class, "height", "the height")
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

}
