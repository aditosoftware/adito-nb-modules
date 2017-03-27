package org.netbeans.modules.form.adito.layout;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.common.IAditoLayoutConstraints;
import org.netbeans.modules.form.layoutsupport.LayoutConstraints;
import org.openide.nodes.Node;

import java.util.*;

/**
 * @author J. Boesl, 22.06.11
 */
public abstract class AbstractComponentConstraints<T> implements LayoutConstraints
{
  private IAditoLayoutConstraints<T> constraints;
  private Node.Property[] properties;

  public AbstractComponentConstraints()
  {
    constraints = createConstraints();
  }

  public AbstractComponentConstraints(IAditoLayoutConstraints<T> pConstraints)
  {
    constraints = pConstraints;
  }

  protected abstract IAditoLayoutConstraints<T> createConstraints();


  @Override
  public Node.Property[] getProperties()
  {
    if (properties == null)
      properties = createProperties();
    return properties;
  }

  @Override
  public IAditoLayoutConstraints<T> getConstraintsObject()
  {
    return constraints;
  }

  protected Node.Property[] createProperties()
  {
    Collection<Node.Property> constraintProps = constraints.getProperties();
    int size = constraintProps == null ? 0 : constraintProps.size();
    java.util.List<Node.Property> newProps = new ArrayList<Node.Property>(size);
    if (size != 0)
      for (Node.Property property : constraintProps)
        newProps.add(new SimpleFormProperty(property, IAditoLayoutConstraints.ADITO_LAYOUT));
    return newProps.toArray(new Node.Property[size]);
  }

  protected T type()
  {
    return constraints == null ? null : constraints.getTypeInfo();
  }

}
