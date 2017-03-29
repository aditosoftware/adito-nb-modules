package org.netbeans.modules.form.adito.layout.neon;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.IAditoLayoutProvider;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.common.IAditoLayoutConstraints;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.neon.ICellInfoPropertyTypes;
import org.netbeans.modules.form.adito.layout.*;
import org.netbeans.modules.form.layoutsupport.LayoutConstraints;
import org.openide.nodes.Node;

import java.awt.*;

/**
 * ComponentConstraints für Neon
 *
 * @author t.tasior, 02.03.2016 (original)
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

  @Override
  public LayoutConstraints cloneConstraints()
  {
    return new NeonConstraints(getConstraintsObject().cloneConstraints());
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
