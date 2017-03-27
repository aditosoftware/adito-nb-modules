package org.netbeans.modules.form.adito.layout.neon;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.IAditoLayoutProvider;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.common.IAditoLayoutConstraints;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.neon.ICellInfoPropertyTypes;
import org.netbeans.modules.form.adito.layout.*;
import org.netbeans.modules.form.layoutsupport.LayoutConstraints;
import org.openide.nodes.Node;

import java.awt.*;
import java.util.*;

/**
 *
 */
public class NeonConstraints extends AbstractComponentConstraints<ICellInfoPropertyTypes>
{
  private Rectangle bounds = new Rectangle();

  public NeonConstraints()
  {
  }


  public NeonConstraints(IAditoLayoutConstraints<ICellInfoPropertyTypes> pConstraints)
  {
    super(pConstraints);
  }

  @Override
  protected IAditoLayoutConstraints<ICellInfoPropertyTypes> createConstraints()
  {
    IAditoLayoutProvider layoutProvider = NbAditoInterface.lookup(IAditoLayoutProvider.class);
    return layoutProvider.getNeonTableLayout().createLayoutConstraints();
  }

  public Rectangle getBounds()
  {
    return bounds;
  }

  public void setBounds(Rectangle pBounds)
  {
    bounds.setBounds(pBounds);
  }


  @Override
  public LayoutConstraints cloneConstraints()
  {
    return new NeonConstraints(getConstraintsObject().cloneConstraints());
  }

  @Override
  protected Node.Property[] createProperties()
  {
    Collection<Node.Property> constraintProps = getConstraintsObject().getProperties();
    int size = constraintProps == null ? 0 : constraintProps.size();
    java.util.List<Node.Property> newProps = new ArrayList<Node.Property>(size);
    if (size != 0)
      for (Node.Property property : constraintProps)
        newProps.add(new SimpleFormProperty(property));
    return newProps.toArray(new Node.Property[size]);
  }
}
