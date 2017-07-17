package de.adito.aditoweb.nbm.nbide.nbaditointerface.common;

import org.openide.util.Lookup;

/**
 * Interface f�r von au�en erreichbare Wizards
 *
 * @author W.Glanzer, 17.07.2017
 */
public interface IAditoExternalWizardsProvider
{

  void createSystemTableWizard(Lookup pLookup, String pSchema);

}
