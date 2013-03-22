package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.model;

import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;

import java.util.*;

/**
 * Stellt Daten über Modelle zur Verfügung.
 *
 * @author J. Boesl, 16.05.11
 */
public interface IAditoModelDataProvider
{

  Node getBaseNode(DataObject pFormDataObject);

  EModelFormType getModelFormType(FileObject pAodFile);

  FileObject loadModel(FileObject pAodFile);

  List<FileObject> getChildModels(FileObject pFileObject);

  FileObject getDefaultChildContainer(FileObject pFileObject);

  List<FileObject> getOthers(FileObject pFileObject);

  FileObject createDataModel(FileObject pParentData, Class<?> pComponentClass, String pCreatedName);

  void removeDataModel(FileObject pModelFileObject);

  /**
   * Positioniert die übergebenen Komponenten im linken oberen Bereich
   * des Containers.
   * @param pChildren Komponenten die mit CTRL + V eingefügt werden.
   */
  void calcDropLocation(List<DataObject> pChildren);

  /**
   * Organisiert die interne Auflistung so um, dass die übergebenen
   * Komponenten über den anderen im gleichen Container gezeichnet werden.
   * @param pParent der Container
   * @param pChildren Komponenten die in den Vordergrund kommen sollen.
   */
  void toFront(FileObject pParent, List<DataObject> pChildren);

  /**
   * Verschiebt ein Datenmodell.
   *
   * @param pSource das Datenmodell.
   * @param pTarget das Ziel.
   */
  void moveDataModel(FileObject pSource, FileObject pTarget);

  /**
   * @param pSource das Datenmodell.
   * @param pTarget das Ziel.
   * @return ob ein Datenmodell verschoben werden kann.
   */
  public boolean canMove(FileObject pSource, FileObject pTarget);

  void reorder(FileObject pModelFo, Comparator<String> pChildComparator);

}
