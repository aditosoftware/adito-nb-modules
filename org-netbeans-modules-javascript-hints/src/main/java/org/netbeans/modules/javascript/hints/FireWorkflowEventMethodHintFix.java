package org.netbeans.modules.javascript.hints;

import org.netbeans.editor.BaseDocument;
import org.netbeans.modules.csl.api.EditList;
import org.netbeans.modules.editor.indent.TaskHandler;
import org.netbeans.modules.editor.indent.api.Reformat;
import org.netbeans.modules.javascript.editing.*;
import org.netbeans.modules.javascript.hints.infrastructure.JsRuleContext;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;

import javax.swing.text.BadLocationException;
import java.util.*;

/**
 * @author d.poellath, 06.12.12
 */
public class FireWorkflowEventMethodHintFix
    extends AbstractAditoHintFix
{
  final static String PROP_NAME = "workflowEvents";
  JsRuleContext context;
  String eventIdentifier;

  public FireWorkflowEventMethodHintFix(JsRuleContext pcontext, String pEventIdentifier)
  {
    context = pcontext;
    eventIdentifier = pEventIdentifier;
  }

  public void implement() throws Exception
  {
    JsParseResult info = AstUtilities.getParseResult(context.parserResult);
    FileObject fo = info.getSnapshot().getSource().getFileObject().getParent();
    String[] definedEvents = hintFixSupply.getAditoProperty(fo, PROP_NAME, String[].class);
    List<String> redefinedEvents = (definedEvents == null) ?
        new ArrayList<String>() :
        new ArrayList<String>(Arrays.asList(definedEvents));
    redefinedEvents.add(eventIdentifier);
    hintFixSupply.setAditoProperty(fo, PROP_NAME, redefinedEvents.toArray(new String[redefinedEvents.size()]));

    // DIRTY HACK
    // apply to doc, reformat
    EditList edits = getEditList();
    edits.replace(0, 0, "\n", false, 0);
    edits.apply();
    edits = getEditList();
    edits.replace(0, 1, null, false, 0);
    edits.apply();
  }

  public EditList getEditList() throws Exception
  {
    BaseDocument doc = context.doc;
    EditList edits = new EditList(doc);
    return edits;
  }
  public boolean isSafe()
  {
    return true;
  }

  public boolean isInteractive()
  {
    return false;
  }

  public String getDescription()
  {
    return "Diesen Event registrieren.";
  }
}
