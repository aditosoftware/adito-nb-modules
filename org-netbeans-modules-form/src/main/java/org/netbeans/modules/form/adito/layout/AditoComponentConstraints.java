package org.netbeans.modules.form.adito.layout;

import de.adito.aditoweb.swingcommon.layout.aditolayout.AALComponentConstraints;
import org.netbeans.modules.form.FormProperty;
import org.netbeans.modules.form.codestructure.*;
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

  public AditoComponentConstraints(int pX, int pY, int pWidth, int pHeight)
  {
    this(new Rectangle(pX, pY, pWidth, pHeight));
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

//    private void _reinstateProperties()
//    {
//      try
//      {
//        for (Node.Property property : properties)
//        {
//          FormProperty prop = (FormProperty) property;
//          prop.reinstateProperty();
//        }
//      }
//      catch (IllegalAccessException e1)
//      {
//      } // should not happen
//      catch (java.lang.reflect.InvocationTargetException e2)
//      {
//      } // should not happen
//    }

  protected final CodeExpression[] createPropertyExpressions(CodeStructure codeStructure)
  {
    // first make sure properties are created...
    getProperties();
    int shift = 0;
    // ...then create code expressions based on the properties
    CodeExpression xEl = codeStructure.createExpression(
        FormCodeSupport.createOrigin(properties[shift++]));
    CodeExpression yEl = codeStructure.createExpression(
        FormCodeSupport.createOrigin(properties[shift++]));
    CodeExpression wEl = codeStructure.createExpression(
        FormCodeSupport.createOrigin(properties[shift++]));
    CodeExpression hEl = codeStructure.createExpression(
        FormCodeSupport.createOrigin(properties[shift]));
    return new CodeExpression[]{xEl, yEl, wEl, hEl};
  }

  protected final void readPropertyExpressions(CodeExpression[] exps, int shift)
  {
    // first make sure properties are created...
    getProperties();

    // ...then map the properties to the code expressions
    for (int i = 0; i < exps.length; i++)
      FormCodeSupport.readPropertyExpression(exps[i], properties[i + shift], false);
  }
}
