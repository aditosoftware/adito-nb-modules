package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout;

/**
 * LayoutProvider mit Informationen �ber verf�gbare Layouts.
 *
 * @author J. Boesl, 16.05.11
 */
public interface IAditoLayoutProvider
{

  IAditoLayout<IAnchorLayoutPropertyTypes> getAnchorLayout();

  IAditoLayout<IRegisterLayoutPropertyTypes> getRegisterLayout();

}
