package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.anchor.IAnchorLayoutPropertyTypes;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.common.IAditoLayout;

/**
 * LayoutProvider mit Informationen über verfügbare Layouts.
 *
 * @author J. Boesl, 16.05.11
 */
public interface IAditoLayoutProvider
{

  IAditoLayout<IAnchorLayoutPropertyTypes> getAnchorLayout();

  IAditoLayout getNeonTableLayout();

  IAditoLayout getRegisterLayout();

  
  IAditoLayout getDashboardLayout();

}
