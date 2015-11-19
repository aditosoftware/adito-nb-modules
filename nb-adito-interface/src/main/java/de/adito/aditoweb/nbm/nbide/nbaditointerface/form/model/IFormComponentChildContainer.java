package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.model;

import de.adito.propertly.core.spi.*;
import org.openide.filesystems.*;
import org.openide.loaders.DataObject;

import java.io.IOException;
import java.util.*;

/**
 * @author W. Glanzer, 27.01.2014
 */
public interface IFormComponentChildContainer
{

  void addChangeListener(IPropertyPitEventListener pChangeListener);

  void removeChangeListener(IPropertyPitEventListener pChangeListener);

  boolean canAdd(Class pSource);

  boolean reorder(Comparator<String> pChildComparator);

  IPropertyPitProvider<?, ?, ?> createDataModel(String pName, Class<?> pComponentClass);

  List<IPropertyPitProvider<?, ?, ?>> getAllChildren();

  IPropertyPitProvider<?, ?, ?> copy(IPropertyPitProvider<?, ?, ?> pSource) throws IOException;

  /**
   * Verschiebt ein Datenmodell.
   *
   * @param pSource das Datenmodell.
   */
  void moveDataModel(IPropertyPitProvider<?,?,?> pSource);

  /**
   * @param pSource das Datenmodell.
   * @return ob ein Datenmodell verschoben werden kann.
   */
  boolean canMove(IPropertyPitProvider<?,?,?> pSource);

}
