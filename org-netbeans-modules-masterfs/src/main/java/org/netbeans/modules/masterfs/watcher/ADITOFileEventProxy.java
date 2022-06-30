package org.netbeans.modules.masterfs.watcher;

import org.jetbrains.annotations.NotNull;
import org.openide.filesystems.FileObject;

/**
 * Proxies requests for watcher
 *
 * @author w.glanzer, 29.06.2022
 */
public interface ADITOFileEventProxy
{

  /**
   * Determines, if pFileObject is able to register for file events
   *
   * @param pFileObject FileObject to check
   * @return true if file events should be fired, otherwise false
   */
  boolean canRegisterForFileEvents(@NotNull FileObject pFileObject);

}
