package org.netbeans.modules.javascript.hints.adito;

import org.netbeans.editor.BaseDocument;
import org.netbeans.modules.csl.api.*;
import org.netbeans.modules.javascript.hints.infrastructure.JsRuleContext;

/**
 * @author d.poellath, 06.12.12
 */
public class AditoImportHintFix implements HintFix, PreviewableFix
{
  private final JsRuleContext context;
  private String importStatement = null;

  public AditoImportHintFix(JsRuleContext pContext, String pImportStatement)
  {
    super();
    context = pContext;
    importStatement = pImportStatement;
  }

  public void implement() throws Exception
  {
    // TODO #4023 - need to be implemented
    if (context == null)
      return;
    EditList edits = getEditList();
    edits.apply();
  }

  @Override
  public String getDescription()
  {
    return importStatement.replaceAll(";", "");
  }

  public EditList getEditList() throws Exception
  {
    BaseDocument doc = context.doc;
    EditList edits = new EditList(doc);
    edits.replace(0, 0, importStatement, false, 0);
    return edits;
  }

  public boolean canPreview()
  {
    return false;
  }

  public boolean isSafe()
  {
    return false;
  }

  public boolean isInteractive()
  {
    return false;
  }

  public JsRuleContext getContext()
  {
    return context;
  }

}
