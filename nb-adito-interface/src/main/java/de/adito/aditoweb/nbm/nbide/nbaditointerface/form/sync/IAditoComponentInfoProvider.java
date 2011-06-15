package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync;

import org.openide.filesystems.FileObject;
import org.openide.loaders.DataFolder;

/**
 * Zugriff auf Model-Properties und in dem Kontext relevante Daten.
 * Liefert Infos �ber Properties aus den Datenmodellen.
 *
 * @author J. Boesl, 16.05.11
 */
public interface IAditoComponentInfoProvider
{

  /**
   * @param pBeanClass die Klasse der Komponente.
   * @return statische Informationen �ber die Komponente.
   */
  IAditoComponentDetailProvider getComponentDetailProvider(Class<?> pBeanClass);

  /**
   * @param pModelDataObject DataFolder der das Model repr�sentiert.
   * @return Informationen �ber die durch den DataFolder identifizierte Komponente.
   */
  IAditoPropertyProvider createModelPropProvider(DataFolder pModelDataObject);

  /**
   * @param pModelFileObject FilObject das das Model repr�sentiert.
   * @return Informationen �ber die durch das FilObject identifizierte Komponente.
   */
  IAditoPropertyProvider createModelPropProvider(FileObject pModelFileObject);

}
