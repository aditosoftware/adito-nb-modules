package de.adito.aditoweb.nbm.nbide.nbaditointerface.common;

import org.jetbrains.annotations.Nullable;
import org.openide.filesystems.FileObject;

/**
 * Liefert Informationen über den EditorContext
 *
 * @author j.boesl, 22.07.16
 */
public interface IEditorContextInfo
{

  /**
   * @param pFoFromContext eine Datei aus einem EditorContext.
   * @return das Wurzelverzeichnis des EditorContexts.
   */
  @Nullable
  FileObject getEditorContextRoot(FileObject pFoFromContext);

}
