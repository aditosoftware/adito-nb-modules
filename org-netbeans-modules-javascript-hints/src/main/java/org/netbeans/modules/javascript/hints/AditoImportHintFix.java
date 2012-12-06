package org.netbeans.modules.javascript.hints;

import org.netbeans.modules.javascript.editing.JsParseResult;

/**
 * @author d.poellath, 06.12.12
 */
public class AditoImportHintFix
    extends AbstractAditoHintFix
{
  private String importStatement = null;
  private JsParseResult info = null;

  public AditoImportHintFix(JsParseResult pInfo, String pImportStatement)
  {
    super();
    importStatement = pImportStatement;
    info = pInfo;
  }

  public void implement() throws Exception
  {
    // TODO #4023 - need to be implemented
    //if (info == null)
    //  return;
    //Snapshot snapshot = info.getSnapshot();
    //final Document doc = snapshot.getSource().getDocument(false);
    //if (doc == null)
    //  return;
    //SwingUtilities.invokeLater(new Runnable()
    //{
    //  public void run()
    //  {
    //    final NbEditorDocument nbdoc = (NbEditorDocument) doc;
    //    nbdoc.runAtomic(new Runnable()
    //    {
    //
    //      public void run()
    //      {
    //        MutableTextInput mti = (MutableTextInput) doc.getProperty(MutableTextInput.class);
    //        if (mti != null)
    //        {
    //          mti.tokenHierarchyControl().rebuild();
    //        }
    //      }
    //    });
    //  }
    //});
  }


  public boolean isSafe()
  {
    return false;
  }

  public boolean isInteractive()
  {
    return true;
  }
}
