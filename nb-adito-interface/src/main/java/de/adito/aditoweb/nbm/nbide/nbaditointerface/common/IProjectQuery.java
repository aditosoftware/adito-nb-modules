package de.adito.aditoweb.nbm.nbide.nbaditointerface.common;

import org.jetbrains.annotations.*;
import org.netbeans.api.project.Project;
import org.openide.util.Lookup;

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
   * Returns the project that the given lookup contains
   *
   * @param pLookup Lookup as search base
   * @return the project or null, if no project was found
   */
  @Nullable
  default Project findProject(@NotNull Lookup.Provider pLookup)
  {
    Lookup lookup = pLookup.getLookup();
    if(lookup == null)
      return null;
    return findProject(lookup);
  }

  /**
   * Returns the project that the given lookup contains
   *
   * @param pLookup Lookup as search base
   * @return the project or null, if no project was found
   */
  @Nullable
  Project findProject(@NotNull Lookup pLookup);

}
