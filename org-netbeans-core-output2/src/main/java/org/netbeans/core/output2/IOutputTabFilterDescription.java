package org.netbeans.core.output2;

/**
 * Created by c.einsiedler on 11.03.14.
 */
public interface IOutputTabFilterDescription
{

  void setup();

  boolean accepts(String pStr);

}
