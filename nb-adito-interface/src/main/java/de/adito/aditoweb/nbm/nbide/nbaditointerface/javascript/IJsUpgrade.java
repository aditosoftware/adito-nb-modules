package de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript;

import org.jetbrains.annotations.*;

import javax.swing.text.*;
import java.util.Map;

/**
 * Br�ckte zwischen NetBeans und ADITO f�r den JavaScript-Upgrader/Converter des JavaScripts
 *
 * @author W.Glanzer, 27.09.2017
 */
public interface IJsUpgrade
{

  boolean upgrade(@NotNull Object pNode, @NotNull IDocumentModification<?> pDocument, boolean pGenerateTodo) throws Exception;

  /**
   * Das DocumentModification-Interface beschreibt ein Objekt, das die Positionen seiner
   * UserObjects speichert und beim insert/remove so anpasst, dass der gleiche Text + der neu eingef�gte Text
   * im Objekt present ist.
   */
  interface IDocumentModification<USEROBJECT>
  {
    @NotNull
    Map.Entry<Integer, Integer> getPositionOfObj(USEROBJECT pObject);

    void insertString(int pIndex, @Nullable USEROBJECT pObject, String pString) throws BadLocationException;

    void remove(int pIndex, int pLength) throws BadLocationException;

    int getLength();

    String getText(int pIndex, int pLength) throws BadLocationException;
  }

}
