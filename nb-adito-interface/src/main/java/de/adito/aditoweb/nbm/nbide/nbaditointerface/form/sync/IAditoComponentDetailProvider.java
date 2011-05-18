package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync;

/**
 * @author J. Boesl, 16.05.11
 */
public interface IAditoComponentDetailProvider
{

  Class<?> getComponentClass();

  String getAditoPropName(String pRadPropName);

  String getRadPropName(String pAditoPropName);

}
