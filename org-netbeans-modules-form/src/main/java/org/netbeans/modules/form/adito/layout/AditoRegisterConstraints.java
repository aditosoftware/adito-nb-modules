package org.netbeans.modules.form.adito.layout;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.IAditoLayoutProvider;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.common.IAditoLayoutConstraints;
import org.netbeans.modules.form.layoutsupport.LayoutConstraints;

/**
 * @author J. Boesl, 22.06.11
 */
public class AditoRegisterConstraints extends AbstractComponentConstraints
{

  public AditoRegisterConstraints()
  {
  }

  @Override
  protected IAditoLayoutConstraints createConstraints()
  {
    IAditoLayoutProvider layoutProvider = NbAditoInterface.lookup(IAditoLayoutProvider.class);
    return layoutProvider.getRegisterLayout().createLayoutConstraints();
  }

  @Override
  public LayoutConstraints cloneConstraints()
  {
    return new AditoRegisterConstraints();
  }
}
