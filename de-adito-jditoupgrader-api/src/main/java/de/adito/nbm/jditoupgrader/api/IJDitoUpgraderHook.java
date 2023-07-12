package de.adito.nbm.jditoupgrader.api;

import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

/**
 * Hook to intercept the JDitoUpgrader mechanism.
 * Has to be placed in the global default lookup in order to be called correctly
 *
 * @author w.glanzer, 11.07.2023
 */
public interface IJDitoUpgraderHook
{

  /**
   * Determines, if the given file can be upgraded
   *
   * @param pFileToUpgrade File that should be upgraded
   * @return true, if this hook says, that the given file can be upgraded
   */
  default boolean canUpgrade(@NonNull File pFileToUpgrade)
  {
    return true;
  }

  /**
   * Gets called before any upgrader will be caled.
   *
   * @param pFilesToUpgrade All files that may be upgraded
   */
  default void beforeAllUpgrade(@NonNull List<File> pFilesToUpgrade)
  {
  }

  /**
   * Gets called before the given file gets upgraded
   *
   * @param pFileToUpgrade File that should be upgraded
   */
  default void beforeUpgrade(@NonNull File pFileToUpgrade)
  {
  }

  /**
   * Gets called after the given file was upgraded
   *
   * @param pUpgradedFile File that has been upgraded
   * @param pException    Exception that happened during upgrade process
   */
  default void afterUpgrade(@NonNull File pUpgradedFile, @Nullable Exception pException)
  {
  }

  /**
   * Gets called after the given files were upgraded
   *
   * @param pUpgradedFiles Files that have been upgraded
   * @param pException     Exception that happened during upgrade process
   */
  default void afterAllUpgrade(@NonNull List<File> pUpgradedFiles, @Nullable Exception pException)
  {
  }

}
