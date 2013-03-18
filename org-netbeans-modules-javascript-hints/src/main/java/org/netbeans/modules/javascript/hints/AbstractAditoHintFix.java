package org.netbeans.modules.javascript.hints;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript.IJsHintFixSupply;
import org.netbeans.modules.csl.api.HintFix;

/**
 * @author d.poellath, 06.12.12
 */
public abstract class AbstractAditoHintFix
    implements HintFix
{
  protected IJsHintFixSupply hintFixSupply;

  public AbstractAditoHintFix()
  {
    hintFixSupply = NbAditoInterface.lookup(IJsHintFixSupply.class);
  }

  public String getDescription()
  {
    return hintFixSupply == null ? getClass().getSimpleName() : hintFixSupply.getDescription(getClass().getSimpleName());
  }
}
