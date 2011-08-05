package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout;

import java.awt.*;

/**
 * @author J. Boesl, 29.06.11
 */
public interface INonSwingContainer
{

  Rectangle getBoundsNonSwing();

  boolean addCompNonSwing(INonSwingComponent pComp);

  boolean removeCompNonSwing(INonSwingComponent pComp);

}
