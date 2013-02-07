package de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript;

import org.openide.filesystems.FileObject;

import java.util.*;

/**
 * Stellt f�r JavaScript Daten �ber Adito bereit.
 *
 * @author J. Boesl, 21.08.12
 */
public interface IJsDataSupply
{

  /**
   * @return die <tt>$sys.</tt>-Variablen.
   */
  List<String> getSysVars();

  /**
   * @return die <tt>$local.</tt>-Variablen.
   */
  List<String> getLocalVars();

  /**
   * @param pCompletionFile die Datei, in der gerade 'Auto-Vervollst�ndigt' werden soll.
   * @return die <tt>$comp.</tt>-Variablen.
   */
  Collection<String> getCompVars(FileObject pCompletionFile);

}
