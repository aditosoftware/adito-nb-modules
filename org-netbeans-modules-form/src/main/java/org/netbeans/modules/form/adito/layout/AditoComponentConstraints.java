package org.netbeans.modules.form.adito.layout;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.IAditoLayoutProvider;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.anchor.IAnchorLayoutPropertyTypes;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.common.IAditoLayoutConstraints;

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
    Integer x = constraints.getValue(type().x());
    Integer y = constraints.getValue(type().y());
    Integer width = constraints.getValue(type().width());
    Integer height = constraints.getValue(type().height());
    return new Rectangle(x == null ? 0 : x, y == null ? 0 : y,
                         width == null ? -1 : width, height == null ? -1 : height);
  }

  public void setBounds(Rectangle pBounds)
  {
    IAditoLayoutConstraints<IAnchorLayoutPropertyTypes> constraints = getConstraintsObject();
    constraints.setValue(type().x(), pBounds.x == -1 ? null : pBounds.x);
    constraints.setValue(type().y(), pBounds.y == -1 ? null : pBounds.y);
    constraints.setValue(type().width(), pBounds.width);
    constraints.setValue(type().height(), pBounds.height);
  }


  @Override
  public AditoComponentConstraints cloneConstraints()
  {
    return new AditoComponentConstraints(getConstraintsObject().cloneConstraints());
  }

}
