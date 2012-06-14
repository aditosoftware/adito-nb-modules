package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.model;

import org.openide.awt.UndoRedo;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.nodes.*;
import org.openide.text.DataEditorSupport;

import java.util.List;

/**
 * Stellt Daten über Modelle zur Verfügung.
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

  ICookieLookupHelper getContainerLookupHelper(DataObject pDataObject, CookieSet pCookieSet);

  public Object installUpdateListeners(final DataObject pDataObject, final DataEditorSupport pDataEditorSupport,
                                       final UndoRedo.Manager pUndoRedoManager, Runnable pOnChange);

}
