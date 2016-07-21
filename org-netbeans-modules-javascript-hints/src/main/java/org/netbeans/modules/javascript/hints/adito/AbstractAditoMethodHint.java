package org.netbeans.modules.javascript.hints.adito;

import org.mozilla.nb.javascript.Node;
import org.netbeans.modules.csl.api.Hint;
import org.netbeans.modules.javascript.editing.AstUtilities;
import org.netbeans.modules.javascript.hints.infrastructure.JsRuleContext;

import java.util.*;

/**
 * @author d.poellath, 06.12.12
 */
public abstract class AbstractAditoMethodHint extends AbstractAditoHint
{
  public void run(JsRuleContext context, List<Hint> result)
  {
    // only listing to Token.CALL, because of getKinds()
    Node node = context.node;
    // apply rule if id equals method name
    if (getMethods().contains(AstUtilities.getCallName(node, true)))
      contextRun(context, result);
  }

  protected abstract void contextRun(JsRuleContext context, List<Hint> result);

  protected abstract Set<String> getMethods();
}
