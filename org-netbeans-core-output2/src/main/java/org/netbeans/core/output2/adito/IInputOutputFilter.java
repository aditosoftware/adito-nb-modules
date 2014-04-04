package org.netbeans.core.output2.adito;

import org.openide.windows.InputOutput;

/**
 * Filter-Erweiterung des InputOutput.
 *
 * Created by c.einsiedler on 11.03.14.
 */
public interface IInputOutputFilter  extends InputOutput
{
  void setFilterDescription(IOutputTabFilterDescription pFilterDescription);
}
