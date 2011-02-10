package org.netbeans.modules.form.adito;

import de.adito.aditoweb.filesystem.datamodelfs.access.DataAccessHelper;
import de.adito.aditoweb.filesystem.datamodelfs.access.mechanics.model.IModelAccess;
import de.adito.aditoweb.filesystem.datamodelfs.access.model.EModelAccessType;
import org.openide.loaders.DataFolder;

/**
 * @author J. Boesl, 10.02.11
 */
public class DMHelper
{

  public static DataFolder createDataFolder()
  {
    return DataFolder.findFolder(DataAccessHelper.<IModelAccess>createModelAccess(EModelAccessType.EDITFIELD)
                                     .getFileObject());
  }

}
