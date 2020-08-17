package de.adito.aditoweb.nbm.nbide.nbaditointerface.common;

import org.jetbrains.annotations.*;
import org.netbeans.api.project.Project;
import org.openide.util.Lookup;

import java.util.*;

/**
 * A service, accessible via default Lookup, that gives information about
 * the project, that a lookup / lookup-provider contains.
 *
 * @author w.glanzer, 11.08.2020
 */
public interface IProjectQuery
{

  /**
   * @return the currently usable instance
   */
  @NotNull
  static IProjectQuery getInstance()
  {
    return Lookup.getDefault().lookup(IProjectQuery.class);
  }

  /**
   * Returns all projects that the given lookup contains
   *
   * @param pLookup Lookup as search base
   * @return all found projects
   */
  @NotNull
  default Set<Project> findProjects(@NotNull Lookup.Provider pLookup)
  {
    Lookup lookup = pLookup.getLookup();
    if(lookup == null)
      return new HashSet<>();
    return findProjects(lookup);
  }

  /**
   * Returns all projects that the given lookup contains
   *
   * @param pLookup Lookup as search base
   * @return all found projects
   */
  @NotNull
  Set<Project> findProjects(@NotNull Lookup pLookup);

}
