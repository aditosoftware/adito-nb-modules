package org.netbeans.modules.javascript.hints;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript.IJsHintFixSupply;
import org.netbeans.modules.csl.api.HintFix;

/**
 * @author d.poellath, 06.12.12
 */
public class FireWorkflowEventMethodHintFix
    extends AbstractAditoHintFix
{
  public void implement() throws Exception
  {
    System.out.println("apply fix ...");
  }

  public boolean isSafe()
  {
    return false;
  }

  public boolean isInteractive()
  {
    return false;
  }
}
