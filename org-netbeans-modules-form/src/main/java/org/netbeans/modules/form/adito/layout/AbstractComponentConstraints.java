package org.netbeans.modules.form.adito.layout;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.common.IAditoLayoutConstraints;
import org.netbeans.modules.form.layoutsupport.LayoutConstraints;
import org.openide.nodes.Node;

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
    //noinspection RedundantCast todo WTF JAVA?!
    return (Node.Property[]) getConstraintsObject().getProperties().stream()
        .map(pConstrProp -> new SimpleFormProperty(pConstrProp, IAditoLayoutConstraints.ADITO_LAYOUT))
        .toArray(Node.Property[]::new);
  }

  protected T type()
  {
    return constraints == null ? null : constraints.getTypeInfo();
  }

}
