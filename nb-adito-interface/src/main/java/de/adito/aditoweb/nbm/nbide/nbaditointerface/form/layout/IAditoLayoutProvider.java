package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout;

/**
 * LayoutProvider mit Informationen über verfügbare Layouts.
 *
 * @author J. Boesl, 16.05.11
 */
public interface IAditoLayoutProvider
{

  IAditoLayoutConstraints<IAnchorLayoutPropertyTypes> createLayoutConstraints();

  Class getLayoutClass();

}
