package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync;

import de.adito.propertly.core.spi.IPropertyPitProvider;
import org.openide.loaders.DataFolder;

/**
 * Zugriff auf Model-Properties und in dem Kontext relevante Daten.
 * Liefert Infos über Properties aus den Datenmodellen.
 *
 * @author J. Boesl, 16.05.11
 */
public interface IFormComponentInfoProvider
{

  /**
   * @param pBeanClass die Klasse der Komponente.
   * @return statische Informationen über die Komponente.
   */
  IFormComponentPropertyMapping getFormPropertyMapping(Class<?> pBeanClass);

  /**
   * @param pModel FilObject das das Model repräsentiert.
   * @return Informationen über die durch das FileObject identifizierte Komponente.
   */
  IFormComponentInfo createComponentInfo(IPropertyPitProvider<?, ?, ?> pModel) throws IllegalArgumentException;

}
