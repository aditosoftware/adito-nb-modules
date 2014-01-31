package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.model;

import org.jetbrains.annotations.NotNull;
import org.openide.filesystems.*;
import org.openide.loaders.*;

import java.io.IOException;
import java.util.*;

/**
 * @author W. Glanzer, 27.01.2014
 */
public interface IFormComponentChildContainer
{

  void addFileChangeListener(FileChangeListener pChangeListener);

  void removeFileChangeListener(FileChangeListener pChangeListener);

  boolean canAdd(Class pSource);

  boolean reorder(Comparator<String> pChildComparator);

  FileObject createDataModel(Class<?> pComponentClass, String pCreatedName);

  List<FileObject> getAllChildren();

  DataObject copy(FileObject pSource) throws IOException;

  /**
   * Verschiebt ein Datenmodell.
   *
   * @param pSource das Datenmodell.
   */
  void moveDataModel(FileObject pSource);

  /**
   * @param pSource das Datenmodell.
   * @return ob ein Datenmodell verschoben werden kann.
   */
  boolean canMove(FileObject pSource);

}
