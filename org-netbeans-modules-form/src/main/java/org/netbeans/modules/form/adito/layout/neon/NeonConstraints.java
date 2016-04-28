package org.netbeans.modules.form.adito.layout.neon;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.IAditoLayoutProvider;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.common.IAditoLayoutConstraints;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.neon.ICellInfoPropertyTypes;
import org.netbeans.modules.form.adito.layout.AbstractComponentConstraints;
import org.netbeans.modules.form.layoutsupport.LayoutConstraints;

import java.awt.*;

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

}
