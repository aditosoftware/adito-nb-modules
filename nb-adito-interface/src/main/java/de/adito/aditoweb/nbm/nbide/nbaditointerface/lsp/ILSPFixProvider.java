package de.adito.aditoweb.nbm.nbide.nbaditointerface.lsp;

import org.jetbrains.annotations.*;
import org.netbeans.spi.editor.hints.Fix;
import org.openide.filesystems.FileObject;

import java.util.List;

/**
 * @author m.kaspera, 26.08.2022
 */
public interface ILSPFixProvider
{

  /**
   * Provide a list of possible fixes for a hint/problem
   *
   * @param pFileObject  the file object of the current file
   * @param pId          the id specified or -1 if none was specified
   * @param pDescription description of the hint that is displayed to the user.
   * @param pSeverity    the severity of the hint
   * @param pRange       where the error will be marked in the document or <code>null</code> if no place to mark
   * @return List of possible fixes for this hint/problem
   */
  List<Fix> provideFixes(@NotNull FileObject pFileObject, int pId, @NotNull String pDescription, @NotNull String pSeverity,
                         @Nullable Range pRange);

  /**
   * denotes a range of characters in a text. Based on the range of the org.eclipse.lsp4j module, but as this module is contains primarily interfaces,
   * the dependency is omitted to keep the dependencies small -> This interface contains the same information as the lsp4j Range
   */
  interface Range
  {

    /**
     * @return index of the starting line of this Range, index is 0-based
     */
    int getStartLine();

    /**
     * @return index of the first character of this range inside the starting line. Index is 0-based
     */
    int getStartCharacter();

    /**
     * @return index of the last line of this Range, index is 0-based
     */
    int getEndLine();

    /**
     * @return index of the last character of this range inside the ending line. Index is 0-based
     */
    int getEndCharacter();

  }

}
