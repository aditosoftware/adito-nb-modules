package org.netbeans.modules.form.adito.layout.neon;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.IAditoLayoutProvider;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.common.IAditoLayoutConstraints;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.neon.IDashletPropertyTypes;
import org.netbeans.modules.form.adito.layout.*;
import org.netbeans.modules.form.layoutsupport.LayoutConstraints;
import org.openide.nodes.Node;

import java.awt.*;

/**
 * Constraints für eine Dashlet Komponente.
 */
public class AditoDashboardConstraints extends AbstractComponentConstraints<IDashletPropertyTypes>
{
  private Rectangle bounds = new Rectangle();

  public AditoDashboardConstraints()
  {
    super();
  }

  public AditoDashboardConstraints(IAditoLayoutConstraints<IDashletPropertyTypes> pConstraints)
  {
    super(pConstraints);
  }

  @Override
  protected IAditoLayoutConstraints<IDashletPropertyTypes> createConstraints()
  {
    IAditoLayoutProvider layoutProvider = NbAditoInterface.lookup(IAditoLayoutProvider.class);
    return layoutProvider.getDashboardLayout().createLayoutConstraints();
  }

  @Override
  public LayoutConstraints cloneConstraints()
  {
    return new AditoDashboardConstraints(getConstraintsObject().cloneConstraints());
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
  protected Node.Property[] createProperties()
  {
    //noinspection RedundantCast todo WTF JAVA?!
    return (Node.Property[]) getConstraintsObject().getProperties().stream()
        .map(SimpleFormProperty::new)
        .toArray(Node.Property[]::new);
  }
}
