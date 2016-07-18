package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.model;

import de.adito.propertly.core.spi.*;
import org.jetbrains.annotations.Nullable;
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

  IPropertyPitProvider<?, ?, ?> loadModel(FileObject pAodFile);

  List<IPropertyPitProvider<?, ?, ?>> getChildModels(IPropertyPitProvider<?, ?, ?> pPropertyPitProvider);

  @Nullable
  IFormComponentChildContainer getChildContainer(IPropertyPitProvider<?, ?, ?> pModel);

  List<IPropertyPitProvider<?, ?, ?>> getOthers(IPropertyPitProvider<?, ?, ?> pModel);

  IPropertyPitProvider<?, ?, ?> createDataModel(IPropertyPitProvider<?, ?, ?> pParentModel, Class<?> pComponentClass, String pCreatedName);

  void removeDataModel(IPropertyPitProvider<?, ?, ?> pModel);

  void renameDataModel(IPropertyPitProvider<?, ?, ?> pModel, String pNewName);

  /**
   * Positioniert die �bergebenen Komponenten im linken oberen Bereich
   * des Containers.
   * @param pChildren Komponenten die mit CTRL + V eingef�gt werden.
   */
  void calcDropLocation(List<IPropertyPitProvider<?, ?, ?>> pChildren);

  /**
   * Organisiert die interne Auflistung so um, dass die �bergebenen
   * Komponenten �ber den anderen im gleichen Container gezeichnet werden.
   * @param pParent der Container
   * @param pChildren Komponenten die in den Vordergrund kommen sollen.
   */
  void toFront(IPropertyPitProvider<?, ?, ?> pParent, List<IPropertyPitProvider<?, ?, ?>> pChildren);

  /**
   * Pr�ft ob ein Objekt vom Typ <tt>pSource</tt> unter <tt>pTarget</tt> erstellt werden kann.
   *
   * @param pModel das Ziel.
   * @param pSource die Quelle.
   * @return ob 'add' m�glich ist.
   */
  boolean canAdd(IPropertyPitProvider<?, ?, ?> pModel, Class pSource);

  boolean reorder(IPropertyPitProvider<?, ?, ?> pModel, Comparator<String> pChildComparator);
}
