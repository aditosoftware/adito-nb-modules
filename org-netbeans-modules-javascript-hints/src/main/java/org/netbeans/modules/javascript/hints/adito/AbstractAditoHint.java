package org.netbeans.modules.javascript.hints.adito;

import org.netbeans.modules.csl.api.RuleContext;
import org.netbeans.modules.javascript.hints.infrastructure.*;

import javax.swing.*;
import java.util.prefs.Preferences;

/**
 * @author d.poellath, 06.12.12
 */
public abstract class AbstractAditoHint extends JsAstRule
{
  public String getId()
  {
    return getClass().getSimpleName();
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
    return context instanceof JsRuleContext;
  }

  public JComponent getCustomizer(Preferences node)
  {
    return null;
  }
}
