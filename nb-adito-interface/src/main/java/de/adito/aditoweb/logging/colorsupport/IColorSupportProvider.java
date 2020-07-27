package de.adito.aditoweb.logging.colorsupport;

import org.openide.windows.InputOutput;

/**
 * @author m.kaspera, 27.07.2020
 */
public interface IColorSupportProvider
{

  IColorSupport getColorSupport(InputOutput pInputOutput);

}
