package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync;

import org.openide.filesystems.FileObject;

/**
 * Zugriff auf Model-Properties und in dem Kontext relevante Daten.
 * Liefert Infos über Properties aus den Datenmodellen.
 *
 * @author J. Boesl, 16.05.11
 */
public interface IAditoComponentInfoProvider
{

  /**
   * @param pBeanClass die Klasse der Komponente.
   * @return statische Informationen über die Komponente.
   */
  IAditoComponentDetailProvider getComponentDetailProvider(Class<?> pBeanClass);

  /**
   * @param pModelFo ein FileObject mit Modelinformationen.
   * @return Informationen über die durch das FilObject identifizierte Komponente.
   */
  IAditoPropertyProvider createModelPropProvider(FileObject pModelFo);


}
