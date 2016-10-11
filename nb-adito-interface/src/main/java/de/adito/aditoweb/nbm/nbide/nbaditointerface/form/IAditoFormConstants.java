package de.adito.aditoweb.nbm.nbide.nbaditointerface.form;

/**
 * Konstanten, die f�r die Zusammenarbeit des FormEditors mit ADITO ben�tigt werden
 *
 * @author W.Glanzer, 11.10.2016
 */
public interface IAditoFormConstants
{

  /**
   * Dieses Attribut wird dem jeweiligen Property gesetzt und gibt an,
   * ob bei einem verificationError innerhalb von setValue() eine Exception geworfen
   * oder das ROV geloggt wird.
   */
  String ATR_FAILONVERIFICATIONERROR = "failOnVerificationError";

}
