package de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript;

import org.openide.filesystems.FileObject;

import java.util.Set;

/**
 * Stellt für JavaScript Daten über Adito bereit.
 *
 * @author J. Boesl, 21.08.12
 */
public interface IJsDataSupply
{

  /**
   * @return die <tt>$sys.</tt>-Variablen.
   */
  Set<String> getSysVars();

  /**
   * @return die <tt>$local.</tt>-Variablen.
   */
  Set<String> getLocalVars();

  /**
   * @param pCompletionFile die Datei, in der gerade 'Auto-Vervollständigt' werden soll.
   * @return die <tt>$comp.</tt>-Variablen.
   */
  Set<String> getCompVars(FileObject pCompletionFile);

  /**
   * @param pCompletionFile die Datei, in der gerade 'Auto-Vervollständigt' werden soll.
   * @return die <tt>$field.</tt>-Variablen.
   */
  Set<String> getEntityFieldVars(FileObject pCompletionFile);

  /**
   * Liefert die Namen von Parameter Entities.
   *
   * @param pCompletionFile FileObject eines Adito Prozesses.
   * @return eine Liste mit Namen von Parameter Entities erweitert
   * um das Prafix "$param.", oder eine leere Liste.
   */
  Set<String> parameterVars(FileObject pCompletionFile);

}
