package org.netbeans.modules.form.adito;

import org.openide.filesystems.FileObject;
import org.openide.loaders.DataFolder;

/**
 * @author J. Boesl, 10.02.11
 */
public class DMHelper
{

  private DMHelper()
  {
  }

  public static ARADComponentHandler getHandler()
  {
    return new ARADComponentHandler();
  }

  public static ARADComponentHandler getHandler(FileObject pFo)
  {
    ARADComponentHandler aradComponentHandler = new ARADComponentHandler();
    aradComponentHandler.setModelDataObject(DataFolder.findFolder(pFo));
    return aradComponentHandler;
  }

}
