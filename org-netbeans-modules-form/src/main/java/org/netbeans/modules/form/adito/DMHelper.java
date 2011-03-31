package org.netbeans.modules.form.adito;

import de.adito.aditoweb.filesystem.datamodelfs.access.DataAccessHelper;
import de.adito.aditoweb.filesystem.datamodelfs.access.mechanics.model.IModelAccess;
import de.adito.aditoweb.filesystem.datamodelfs.access.model.EModelAccessType;
import org.openide.filesystems.FileObject;
import org.openide.loaders.*;

/**
 * @author J. Boesl, 10.02.11
 */
public class DMHelper
{

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

  private static FileObject createDataFo()
  {
    return DataAccessHelper.<IModelAccess>createModelAccess(EModelAccessType.EDITFIELD).getFileObject();
  }

}
