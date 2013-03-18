package org.netbeans.modules.javascript.hints;

import org.mozilla.nb.javascript.*;
import org.netbeans.modules.csl.api.*;
import org.netbeans.modules.javascript.editing.*;
import org.netbeans.modules.javascript.hints.infrastructure.JsRuleContext;
import org.netbeans.modules.parsing.spi.Parser;
import org.openide.filesystems.FileObject;

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

    FileObject fileObject = null;
    if (info != null)
      fileObject = info.getSnapshot().getSource().getFileObject();

    // finde zugehörige JS process heraus
    Set<String> process = new HashSet<>();
    if (fileObject != null)
    {
      for (Parser.Result parserResult : hintSupply.findProcessWithMethod(fileObject))
      {
        JsParseResult jsParseResult = (JsParseResult) parserResult;
        ScriptOrFnNode root = (ScriptOrFnNode) jsParseResult.getRootNode();
        Node child = root.getFirstChild();
        while (root != null)
        {
          while (child != null)
          {
            if (child instanceof FunctionNode)
              if ((((FunctionNode) child).getFunctionName() + "()").equals(callName))
                process.add(hintSupply.getAditoLibName(jsParseResult.getSnapshot().getSource().getFileObject()));

            child = child.getNext();
          }
          root = (ScriptOrFnNode) root.getNext();
        }
      }
    }


    // nicht gefunden --> ggf. trotzdem warnen, ohne fix?
    if (process.size() == 0)
      return;

      // prüfe ob bereits importiert
      // ggf. hint anzeigen und
    else
    {
      fixList = new ArrayList<>();
      for (String imports : process)
      {
        String importStatement = "import(\"" + imports + "\")";
        if (source.contains(importStatement) || source.contains("__po__(\"" + imports + "\")"))
          return;

        fixList.add(new AditoImportHintFix(context, importStatement + ";\n"));
      }

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
        || pCallName.startsWith("__po__")
        || hintSupply.isMethodDeclaredInAditoProcess(pInfo.getSnapshot().getSource().getFileObject(), pCallName);
  }
}
