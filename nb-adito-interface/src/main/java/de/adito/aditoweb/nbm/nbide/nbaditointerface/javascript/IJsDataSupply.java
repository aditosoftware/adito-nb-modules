package de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript;

import java.util.List;

/**
 * Stellt für JavaScript Daten über Adito bereit.
 *
 * @author J. Boesl, 21.08.12
 */
public interface IJsDataSupply
{

  /**
   *
   * @return die <tt>$sys.</tt>-Konstanten
   */
  List<String> getSysConstants();

}
