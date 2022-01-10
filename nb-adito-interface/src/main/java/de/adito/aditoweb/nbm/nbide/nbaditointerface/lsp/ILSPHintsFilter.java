package de.adito.aditoweb.nbm.nbide.nbaditointerface.lsp;

import org.jetbrains.annotations.*;
import org.openide.filesystems.FileObject;
import org.openide.text.PositionBounds;

/**
 * Filter for hints of the lsp server
 *
 * @author s.seemann, 10.01.2022
 */
public interface ILSPHintsFilter
{
  /**
   * If this filter can filter this file
   *
   * @param pFileObject the file object of the current file
   * @return true, if the filter can filter, else flase
   */
  boolean canFilter(@NotNull FileObject pFileObject);

  /**
   * Filters this hint
   *
   * @param pFileObject  the file object of the current file
   * @param pId          the id specified or null if none was specified
   * @param pDescription description of the hint that is displayed to the user.
   * @param pSeverity    the severity of the hint
   * @param pRange       where the error will be marked in the document or <code>null</code> if no place to mark
   * @return true, if this hint should be shown to the ui, else false
   */
  boolean filter(@NotNull FileObject pFileObject, @Nullable String pId, @NotNull String pDescription, @NotNull String pSeverity, @Nullable PositionBounds pRange);
}
