package org.netbeans.core.output2;

/**
 * Filterbeschreibung für OutputTab.
 *
 * Created by c.einsiedler on 11.03.14.
 */
public interface IOutputTabFilterDescription
{

  void setup();

  boolean accepts(String pStr);

}
