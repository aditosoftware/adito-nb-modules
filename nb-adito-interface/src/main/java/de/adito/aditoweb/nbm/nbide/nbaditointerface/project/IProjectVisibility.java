package de.adito.aditoweb.nbm.nbide.nbaditointerface.project;

import org.jetbrains.annotations.*;
import org.netbeans.api.project.Project;

import java.util.Objects;

/**
 * Determines, if a project is visible to the user or not.
 *
 * @author w.glanzer, 28.03.2022
 */
public interface IProjectVisibility
{

  /**
   * @return true, if the project is visible to user
   */
  @Nullable
  Boolean isVisible();

  /**
   * Determines, if a project is visible to the user
   *
   * @param pProject Project to check
   * @return true, if it is visible
   */
  static boolean isVisible(@NotNull Project pProject)
  {
    return pProject.getLookup().lookupAll(IProjectVisibility.class).stream()
        .map(IProjectVisibility::isVisible)
        .filter(Objects::nonNull)
        .allMatch(Boolean.TRUE::equals);
  }

}
