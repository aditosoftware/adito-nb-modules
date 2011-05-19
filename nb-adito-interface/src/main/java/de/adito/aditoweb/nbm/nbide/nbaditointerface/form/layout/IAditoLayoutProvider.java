package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout;

import java.awt.*;

/**
 * LayoutProvider mit Informationen über verfügbare Layouts.
 *
 * @author J. Boesl, 16.05.11
 */
public interface IAditoLayoutProvider
{

  IAditoAnchorLayoutComponentConstaints createAditoAnchoLayoutComponentConstraints(
      Rectangle pBounds, boolean pAnchorLeft, boolean pAnchorBottom, boolean pAnchorRight, boolean pAnchorTop,
      boolean pIsBordered);

  Class getAditoAnchoLayoutClass();

  LayoutManager createAditoAnchorLayout();

}
