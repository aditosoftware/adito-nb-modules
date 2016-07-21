package org.netbeans.modules.javascript.hints.adito;

import org.netbeans.modules.csl.api.*;
import org.netbeans.modules.javascript.hints.infrastructure.JsAstRule;

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

  public String getDescription()
  {
    return getId();
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

  public JComponent getCustomizer(Preferences node)
  {
    return null;
  }

  public HintSeverity getDefaultSeverity()
  {
    return HintSeverity.WARNING;
  }
}
