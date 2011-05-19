package de.adito.aditoweb.nbm.nbide.nbaditointerface.form;

/**
 * Interface von Netbeans zu Adito (Interface-Sammlung)
 *
 * @author J. Boesl, 11.05.11
 */
public interface INetbeansAditoInterface
{

  <T> T lookup(Class<T> pClass);

}
