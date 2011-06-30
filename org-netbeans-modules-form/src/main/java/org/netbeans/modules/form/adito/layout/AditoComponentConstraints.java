package org.netbeans.modules.form.adito.layout;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.IAditoLayoutProvider;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.anchor.*;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.common.IAditoLayoutConstraints;
import org.netbeans.modules.form.layoutsupport.LayoutConstraints;

import java.awt.*;

/**
 * @author J. Boesl, 10.03.11
 */
public class AditoComponentConstraints extends AbstractComponentConstraints<IAnchorLayoutPropertyTypes>
{

  public AditoComponentConstraints()
  {
  }

  public AditoComponentConstraints(IAditoLayoutConstraints<IAnchorLayoutPropertyTypes> pConstraints)
  {
    super(pConstraints);
  }

  @Override
  protected IAditoLayoutConstraints<IAnchorLayoutPropertyTypes> createConstraints()
  {
    IAditoLayoutProvider layoutProvider = NbAditoInterface.lookup(IAditoLayoutProvider.class);
    return layoutProvider.getAnchorLayout().createLayoutConstraints();
  }

  public Rectangle getBounds()
  {
    IAditoLayoutConstraints<IAnchorLayoutPropertyTypes> constraints = getConstraintsObject();
    return new Rectangle(constraints.getValue(type().x()), constraints.getValue(type().y()),
                         constraints.getValue(type().width()), constraints.getValue(type().height()));
  }

  public void setBounds(Rectangle pBounds)
  {
    IAditoLayoutConstraints<IAnchorLayoutPropertyTypes> constraints = getConstraintsObject();
    constraints.setValue(type().x(), pBounds.x);
    constraints.setValue(type().y(), pBounds.y);
    constraints.setValue(type().width(), pBounds.width);
    constraints.setValue(type().height(), pBounds.height);
  }


  @Override
  public LayoutConstraints cloneConstraints()
  {
    return new AditoComponentConstraints(getConstraintsObject().cloneConstraints());
  }

}
