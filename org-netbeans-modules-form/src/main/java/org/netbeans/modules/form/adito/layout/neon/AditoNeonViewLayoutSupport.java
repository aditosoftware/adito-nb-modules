package org.netbeans.modules.form.adito.layout.neon;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.IAditoLayoutProvider;
import org.netbeans.modules.form.layoutsupport.AbstractLayoutSupport;

/**
 * @author W.Glanzer, 13.03.2018
 */
@SuppressWarnings("unused") //Reflection
public class AditoNeonViewLayoutSupport extends AbstractLayoutSupport
{

  @Override
  public Class getSupportedClass()
  {
    IAditoLayoutProvider layoutProvider = NbAditoInterface.lookup(IAditoLayoutProvider.class);
    return layoutProvider.getNeonViewLayout().getLayoutClass();
  }

  @Override
  public boolean shouldHaveNode()
  {
    return false;
  }

}
