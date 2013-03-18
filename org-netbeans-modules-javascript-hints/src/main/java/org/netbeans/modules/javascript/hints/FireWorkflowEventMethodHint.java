package org.netbeans.modules.javascript.hints;

import org.mozilla.nb.javascript.Node;
import org.netbeans.modules.csl.api.*;
import org.netbeans.modules.javascript.editing.*;
import org.netbeans.modules.javascript.hints.infrastructure.JsRuleContext;

import java.util.*;

/**
 * @author d.poellath, 06.12.12
 */
public class FireWorkflowEventMethodHint
    extends AbstractAditoMethodHint
{

  public HintSeverity getDefaultSeverity()
  {
    return HintSeverity.ERROR;
  }

  // Method name, where we are listning to
  public Set<String> getMethods()
  {
    return Collections.singleton("a.fireWorkflowEvent");
  }

  protected void contextRun(JsRuleContext context, List<Hint> result)
  {
    Node node = context.node;
    JsParseResult info = AstUtilities.getParseResult(context.parserResult);

    // -- best way with String manipulation? // or using ast?
    // method source
    String source = info.getSource().substring(node.getSourceStart(), node.getSourceEnd());
    // only apply if 2 parameters
    if (!source.contains(","))
      return;
    // first parameter
    String eventIdentifier = source.substring(source.indexOf("(") + 1, source.indexOf(","));
    // just testable if a String not a var
    if (!eventIdentifier.contains("\""))
      return;
    eventIdentifier = eventIdentifier.substring(eventIdentifier.indexOf("\"") + 1, eventIdentifier.lastIndexOf("\"")).trim();


    // already in properties defined?
    String[] definedEvents = hintSupply.getAditoProperty(info.getSnapshot().getSource().getFileObject().getParent(), "workflowEvents", String[].class);
    if (definedEvents != null)
      for (String event : definedEvents)
        if (event.equals(eventIdentifier))
          return;

    // not defined, show hint
    int start = node.getSourceStart() + source.indexOf(eventIdentifier);
    OffsetRange range = new OffsetRange(start, start + eventIdentifier.length());
    List<HintFix> fixList = Collections.<HintFix>singletonList(new FireWorkflowEventMethodHintFix(context, eventIdentifier));
    Hint desc = new Hint(this, getDisplayName(),
                         info.getSnapshot().getSource().getFileObject(),
                         range, fixList, 1500);
    result.add(desc);
  }

  public String getDescription()
  {
    return "Event nicht registriert";
  }
  public String getDisplayName()
  {
    return "FireWorkflowEvent";
  }
}
