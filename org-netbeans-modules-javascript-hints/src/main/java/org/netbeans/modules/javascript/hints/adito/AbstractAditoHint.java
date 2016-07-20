package org.netbeans.modules.javascript.hints.adito;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript.IJsHintSupply;
import org.mozilla.nb.javascript.Token;
import org.netbeans.modules.csl.api.*;
import org.netbeans.modules.javascript.hints.infrastructure.JsAstRule;

import javax.swing.*;
import java.util.*;
import java.util.prefs.Preferences;

/**
 * @author d.poellath, 06.12.12
 */
public abstract class AbstractAditoHint
    extends JsAstRule
{
  private IJsHintSupply hintSupply;

  public AbstractAditoHint()
  {
    hintSupply = NbAditoInterface.lookup(IJsHintSupply.class);
  }

  protected IJsHintSupply getHintSupply()
  {
    return hintSupply;
  }

  public String getId()
  {
    return getClass().getSimpleName();
  }

  public String getDescription()
  {
    return (hintSupply == null ? getClass().getSimpleName() : hintSupply.getDescription(getId()));
  }

  public String getDisplayName()
  {
    return (hintSupply == null ? getClass().getSimpleName() : hintSupply.getDisplayName(getId()));
  }

  public boolean getDefaultEnabled()
  {
    return true;
  }

  public boolean showInTasklist()
  {
    return true;
  }

  public boolean appliesTo(RuleContext context)
  {
    return true;
  }

  public Set<Integer> getKinds()
  {
    return Collections.singleton(Token.CALL);
  }

  public JComponent getCustomizer(Preferences node)
  {
    return null;
  }

  public HintSeverity getDefaultSeverity()
  {
    return HintSeverity.WARNING;
  }
}
