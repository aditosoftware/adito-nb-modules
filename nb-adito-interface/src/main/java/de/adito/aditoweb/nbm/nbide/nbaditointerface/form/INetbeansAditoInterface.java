package de.adito.aditoweb.nbm.nbide.nbaditointerface.form;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.*;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.model.IAditoModelProvider;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync.IAditoPropertyInfo;

import java.awt.*;

/**
 * @author J. Boesl, 11.05.11
 */
public interface INetbeansAditoInterface
{

  IAditoLayoutInfo getAditoLayoutInfo();

  IAditoPropertyInfo getAditoPropertyInfo();

  IAditoModelProvider getAditoModelProvider();

}
