package org.netbeans.modules.masterfs.watcher;

import org.jetbrains.annotations.NotNull;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileObject;

/**
 * Interface for detecting the usage of symbolic links.
 * <p>
 * The interface need to implemented as a {@link org.openide.util.lookup.ServiceProvider}.
 *
 * @author r.hartinger, 02.01.2023
 */
@SuppressWarnings("unused") // implementation will be in the designer project
public interface IADITOWatcherSymlinkProvider
{
  /**
   * Checks if symbolic links should be included. This method will not always scan the project, but only the first time a new {@link Project} will be found.
   * Otherwise, a rescan needs to be triggered via {@link #rescanProject(Project)}.
   *
   * @param pFileObject the file object which needed to be checked
   * @return {@code true} if symbolic links should be included
   */
  boolean isIncludeSymlinks(@NotNull FileObject pFileObject);

  /**
   * Forces a rescan of a given {@link Project}. The result of the rescan needs to be stored in the implementation of the class.
   *
   * @param pProject the project that should be rescanned
   */
  void rescanProject(@NotNull Project pProject);

}
