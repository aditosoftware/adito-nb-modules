package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.model;

import org.jetbrains.annotations.NotNull;
import org.openide.filesystems.FileObject;
import org.openide.loaders.*;
import org.openide.nodes.Node;

import java.util.List;

/**
 * Stellt Daten �ber Modelle zur Verf�gung.
 *
 * @author J. Boesl, 16.05.11
 */
public interface IAditoModelDataProvider
{

  Node getBaseNode(DataObject pFormDataObject);

  boolean isFrameModel(FileObject pAodFile);

  FileObject loadModel(FileObject pAodFile);

  List<FileObject> getChildModels(FileObject pFileObject);

  FileObject getDefaultChildContainer(FileObject pFileObject);

  List<FileObject> getOthers(FileObject pFileObject);

  FileObject createOrRestoreDataModel(FileObject pParentData, Class<?> pComponentClass, String pCreatedName,
                                      FileObject pDeleted);

  FileObject removeDataModel(FileObject pModelFileObject);

  /**
   * Versucht die Komponente umzubenennen.
   *
   * @param pFileObject das ModelFileObject, das umbenannt werden soll.
   * @param pOldName    der alte Name.
   * @param pNewName    der neue Name.
   */
  void rename(@NotNull FileObject pFileObject, String pOldName, @NotNull String pNewName);

}
