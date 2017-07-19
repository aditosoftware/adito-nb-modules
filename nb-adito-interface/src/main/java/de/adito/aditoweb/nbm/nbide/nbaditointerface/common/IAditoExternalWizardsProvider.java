package de.adito.aditoweb.nbm.nbide.nbaditointerface.common;

import org.openide.util.Lookup;

import java.util.function.Function;

/**
 * Interface f�r von au�en erreichbare Wizards
 *
 * @author W.Glanzer, 17.07.2017
 */
public interface IAditoExternalWizardsProvider
{

  /**
   * @param pTableExistsFn Function um festzustellen, ob eine Tabelle bereits im Schema existiert
   */
  void createSystemTableWizard(Lookup pLookup, String pSchema, Function<String, Boolean> pTableExistsFn);

}
