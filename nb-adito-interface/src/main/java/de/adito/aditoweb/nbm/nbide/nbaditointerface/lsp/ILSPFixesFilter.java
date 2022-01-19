package de.adito.aditoweb.nbm.nbide.nbaditointerface.lsp;

import org.jetbrains.annotations.*;
import org.openide.filesystems.FileObject;

/**
 * Filter for fixes of hints
 *
 * @author s.seemann, 10.01.2022
 */
public interface ILSPFixesFilter
{
  /**
   * If this filter can filter this file
   *
   * @param pFileObject the file object of the current file
   * @return true, if the filter can filter, else flase
   */
  boolean canFilter(@NotNull FileObject pFileObject);

  /**
   * Filters the fixes
   *
   * @param pFileObject the file object of the current file
   * @param pFixText    the text of the fix
   * @return true, if this fix should be shown to the ui, else false
   */
  boolean filter(@NotNull FileObject pFileObject, @NotNull String pFixText);
}
