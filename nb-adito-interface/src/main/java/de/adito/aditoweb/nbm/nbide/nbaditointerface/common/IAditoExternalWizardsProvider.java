package de.adito.aditoweb.nbm.nbide.nbaditointerface.common;

import org.openide.util.Lookup;

/**
 * Interface für von außen erreichbare Wizards
 *
 * @author W.Glanzer, 17.07.2017
 */
public interface IAditoExternalWizardsProvider
{

  void createSystemTableWizard(Lookup pLookup, String pSchema);

}
