package org.netbeans.modules.javascript.hints.adito;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript.IAditoSupply;
import org.netbeans.editor.BaseDocument;
import org.netbeans.modules.csl.api.*;
import org.netbeans.modules.javascript.editing.*;
import org.netbeans.modules.javascript.hints.infrastructure.JsRuleContext;
import org.openide.filesystems.FileObject;

import java.util.*;

/**
 * @author d.poellath, 06.12.12
 */
public class FireWorkflowEventMethodHintFix implements HintFix
{
  private final static String PROP_NAME = "workflowEvents";
  private JsRuleContext context;
  private String eventIdentifier;
  private IAditoSupply aditoSupply;

  public FireWorkflowEventMethodHintFix(JsRuleContext pcontext, String pEventIdentifier, IAditoSupply pAditoSupply)
  {
    context = pcontext;
    eventIdentifier = pEventIdentifier;
    aditoSupply = pAditoSupply;
  }

  public void implement() throws Exception
  {
    JsParseResult info = AstUtilities.getParseResult(context.parserResult);
    FileObject fo = info.getSnapshot().getSource().getFileObject().getParent();
    String[] definedEvents = aditoSupply.getAditoProperty(fo, PROP_NAME, String[].class);
    List<String> redefinedEvents = (definedEvents == null) ?
        new ArrayList<>() :
        new ArrayList<>(Arrays.asList(definedEvents));
    redefinedEvents.add(eventIdentifier);
    aditoSupply.setAditoProperty(fo, PROP_NAME, redefinedEvents.toArray(new String[redefinedEvents.size()]));

    // DIRTY HACK
    // apply to doc, reformat
    EditList edits = _getEditList();
    edits.replace(0, 0, "\n", false, 0);
    edits.apply();
    edits = _getEditList();
    edits.replace(0, 1, null, false, 0);
    edits.apply();
  }

  private EditList _getEditList() throws Exception
  {
    BaseDocument doc = context.doc;
    return new EditList(doc);
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
