package de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript;

import javax.swing.text.Document;

/**
 * Brückte zwischen NetBeans und ADITO für den JavaScript-Upgrader/Converter des JavaScripts
 *
 * @author W.Glanzer, 27.09.2017
 */
public interface IJsUpgrade
{

  void upgrade(Object pNode, Document pDocument, boolean pGenerateTodo) throws Exception;

}
