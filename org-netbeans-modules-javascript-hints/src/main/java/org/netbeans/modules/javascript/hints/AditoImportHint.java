package org.netbeans.modules.javascript.hints;

import org.mozilla.nb.javascript.Node;
import org.netbeans.modules.csl.api.*;
import org.netbeans.modules.javascript.editing.*;
import org.netbeans.modules.javascript.hints.infrastructure.JsRuleContext;

import java.util.*;

/**
 * @author d.poellath, 06.12.12
 */
public class AditoImportHint
    extends AbstractAditoHint
{
  public void run(JsRuleContext context, List<Hint> result)
  {
    //result.clear();
    Node node = context.node;
    JsParseResult info = AstUtilities.getParseResult(context.parserResult);
    String source = info.getSource();
    List<HintFix> fixList = null;

    // unwichtige methoden aufrufe filtern
    // AstUtilities.getCallName(node, true);
    String callName = info.getSource().substring(node.getSourceStart(), node.getSourceEnd());
    if (isMethodKnown(context, info, node, callName))
      return;

    // finde zugehörige JS process heraus
    String process = hintSupply.findProcessWithMethod(callName);
    // nicht gefunden --> ggf. trotzdem warnen, ohne fix?
    if (process == null)
      fixList = Collections.emptyList();

      // prüfe ob bereits importiert
      // ggf. hint anzeigen und
    else
    {
      String importStatement = "import(\"" + process + "\")";
      if (source.contains(importStatement))
        return;
      fixList = new ArrayList<>();
      fixList.add(new AditoImportHintFix(info, importStatement));
    }

    // hinweis anzeigen
    Hint desc = new Hint(this, getDisplayName(),
                         info.getSnapshot().getSource().getFileObject(),
                         AstUtilities.getNameRange(node), fixList, 1500);
    result.add(desc);
  }

  private boolean isMethodKnown(JsRuleContext pContext, JsParseResult pInfo, Node pNode, String pCallName)
  {
    // Context: pJavaScriptFileObject
    return pCallName.contains(".")
        || pCallName.startsWith("import")
        || hintSupply.isMethodDeclaredInAditoProcess(pInfo.getSnapshot().getSource().getFileObject(), pCallName);
  }
}
