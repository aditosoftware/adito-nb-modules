package de.adito.aditoweb.nbm.nbide.nbaditointerface.project;

import org.jetbrains.annotations.*;
import org.netbeans.api.progress.ProgressHandle;
import org.openide.filesystems.FileObject;

import java.io.IOException;

/**
 * Manager for project creation
 *
 * @author s.seemann, 14.07.2021
 */
public interface IProjectCreationManager
{
  /**
   * Creates a new project from system db
   *
   * @param pHandle            progress handle
   * @param pTargetProjectPath path, where the project should be stored
   * @param pProjectName       name of the project
   * @param pServerConfigPath  path to the server config
   * @return the project root as file object
   */
  FileObject createProject(@NotNull ProgressHandle pHandle, @NotNull String pTargetProjectPath, @NotNull String pProjectName,
                           @NotNull String pServerConfigPath) throws IOException;

  /**
   * Creates a new project from system db
   *
   * @param pHandle            progress handle
   * @param pTargetProjectPath path, where the project should be stored
   * @param pProjectName       name of the project
   * @param pProjectVersion    Version, with which the project should be created. Pass null for the current designer project version
   * @param pServerConfigPath  path to the server config
   * @return the project root as file object
   */
  FileObject createProject(@NotNull ProgressHandle pHandle, @NotNull String pTargetProjectPath, @NotNull String pProjectName,
                           @Nullable String pProjectVersion, @NotNull String pServerConfigPath) throws IOException;
}
