package org.netbeans.modules.form.adito.layout.neon;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.IAditoLayoutProvider;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.anchor.IAnchorLayoutPropertyTypes;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.common.IAditoLayoutConstraints;
import org.netbeans.modules.form.adito.layout.AbstractComponentConstraints;
import org.netbeans.modules.form.layoutsupport.LayoutConstraints;

import java.awt.*;

/**
 *
 */
public class NeonConstraints extends AbstractComponentConstraints<IAnchorLayoutPropertyTypes>
{

  public NeonConstraints()
  {
  }

  public NeonConstraints(IAditoLayoutConstraints<IAnchorLayoutPropertyTypes> pConstraints)
  {
    super(pConstraints);
  }

  @Override
  protected IAditoLayoutConstraints<IAnchorLayoutPropertyTypes> createConstraints()
  {
    IAditoLayoutProvider layoutProvider = NbAditoInterface.lookup(IAditoLayoutProvider.class);
    return layoutProvider.getNeonTableLayout().createLayoutConstraints();
  }

  public Rectangle getBounds()
  {

    IAditoLayoutConstraints<IAnchorLayoutPropertyTypes> co = getConstraintsObject();

    //IAditoLayoutConstraints<IAnchorLayoutPropertyTypes> constraints = getConstraintsObject();
    //Integer x = constraints.getValue(type().x());
    //Integer y = constraints.getValue(type().y());
    //Integer width = constraints.getValue(type().width());
    //Integer height = constraints.getValue(type().height());
    //return new Rectangle(x == null ? 0 : x, y == null ? 0 : y,
    //                     width == null ? -1 : width, height == null ? -1 : height);

    //System.err.println("NeonConstraints.getBounds -> fest codiert");
    return new Rectangle(10,10,32,32);
  }

  public void setBounds(Rectangle pBounds)
  {
    //System.err.println("NeonConstraints.setBounds ->leer");
    //IAditoLayoutConstraints<IAnchorLayoutPropertyTypes> constraints = getConstraintsObject();
    //constraints.setValue(type().x(), pBounds.x == -1 ? null : pBounds.x);
    //constraints.setValue(type().y(), pBounds.y == -1 ? null : pBounds.y);
    //constraints.setValue(type().width(), pBounds.width);
    //constraints.setValue(type().height(), pBounds.height);
  }


  @Override
  public LayoutConstraints cloneConstraints()
  {
    return new NeonConstraints(getConstraintsObject().cloneConstraints());
  }

}
