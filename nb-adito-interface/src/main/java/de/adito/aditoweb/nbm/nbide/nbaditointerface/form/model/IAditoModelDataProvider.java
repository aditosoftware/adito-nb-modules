package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.model;

import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;

import java.util.*;

/**
 * Stellt Daten �ber Modelle zur Verf�gung.
 *
 * @author J. Boesl, 16.05.11
 */
public interface IAditoModelDataProvider
{

  Node getBaseNode(DataObject pFormDataObject);

  EModelFormType getModelFormType(FileObject pAodFile);

  FileObject loadModel(FileObject pAodFile);

  List<FileObject> getChildModels(FileObject pFileObject);

  IFormComponentChildContainer getChildContainer(FileObject pFileObject);

  List<FileObject> getOthers(FileObject pFileObject);

  FileObject createDataModel(FileObject pParentData, Class<?> pComponentClass, String pCreatedName);

  void removeDataModel(FileObject pModelFileObject);

  /**
   * Positioniert die �bergebenen Komponenten im linken oberen Bereich
   * des Containers.
   * @param pChildren Komponenten die mit CTRL + V eingef�gt werden.
   */
  void calcDropLocation(List<DataObject> pChildren);

  /**
   * Organisiert die interne Auflistung so um, dass die �bergebenen
   * Komponenten �ber den anderen im gleichen Container gezeichnet werden.
   * @param pParent der Container
   * @param pChildren Komponenten die in den Vordergrund kommen sollen.
   */
  void toFront(FileObject pParent, List<DataObject> pChildren);

  /**
   * Pr�ft ob ein Objekt vom Typ <tt>pSource</tt> unter <tt>pTarget</tt> erstellt werden kann.
   *
   * @param pTarget das Ziel.
   * @param pSource die Quelle.
   * @return ob 'add' m�glich ist.
   */
  public boolean canAdd(FileObject pTarget, Class pSource);

  boolean reorder(FileObject pModelFo, Comparator<String> pChildComparator);
}
