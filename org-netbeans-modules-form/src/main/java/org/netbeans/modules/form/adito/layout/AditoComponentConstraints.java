package org.netbeans.modules.form.adito.layout;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.*;
import org.netbeans.modules.form.layoutsupport.LayoutConstraints;
import org.openide.nodes.Node;

import java.awt.*;
import java.util.*;

/**
 * @author J. Boesl, 10.03.11
 */
public class AditoComponentConstraints implements LayoutConstraints
{
  private IAditoLayoutConstraints<IAnchorLayoutPropertyTypes> constraints;
  private Node.Property[] properties;

  public AditoComponentConstraints()
  {
    constraints = NbAditoInterface.lookup(IAditoLayoutProvider.class).createLayoutConstraints();
  }

  public AditoComponentConstraints(Rectangle pRectangle)
  {
    constraints = NbAditoInterface.lookup(IAditoLayoutProvider.class).createLayoutConstraints();
    constraints.setValue(type().x(), pRectangle.x);
    constraints.setValue(type().y(), pRectangle.y);
    constraints.setValue(type().width(), pRectangle.width);
    constraints.setValue(type().height(), pRectangle.height);
  }

  public AditoComponentConstraints(IAditoLayoutConstraints<IAnchorLayoutPropertyTypes> pConstraints)
  {
    constraints = pConstraints;
  }

  public Rectangle getBounds()
  {
    return new Rectangle(constraints.getValue(type().x()), constraints.getValue(type().y()),
                         constraints.getValue(type().width()), constraints.getValue(type().height()));
  }

  @Override
  public Node.Property[] getProperties()
  {
    if (properties == null)
      properties = _createProperties();
    return properties;
  }

  @Override
  public IAditoLayoutConstraints<IAnchorLayoutPropertyTypes> getConstraintsObject()
  {
    return constraints;
  }

  @Override
  public LayoutConstraints cloneConstraints()
  {
    return new AditoComponentConstraints(constraints.cloneConstraints());
  }

  private Node.Property[] _createProperties()
  {
    Collection<Node.Property> constraintProps = constraints.getProperties();
    java.util.List<Node.Property> newProps = new ArrayList<Node.Property>(constraintProps.size());
    for (Node.Property property : constraintProps)
      newProps.add(new SimpleFormProperty(property));
    return newProps.toArray(new Node.Property[constraintProps.size()]);
  }

  private IAnchorLayoutPropertyTypes type()
  {
    return constraints.getTypeInfo();
  }

}
