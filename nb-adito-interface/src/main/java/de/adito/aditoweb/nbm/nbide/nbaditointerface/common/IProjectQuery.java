package de.adito.aditoweb.nbm.nbide.nbaditointerface.common;

import lombok.NonNull;
import org.jetbrains.annotations.*;
import org.netbeans.api.project.Project;
import org.openide.util.*;

import java.util.*;
import java.util.function.Function;

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
  @NonNull
  static IProjectQuery getInstance()
  {
    return Lookup.getDefault().lookup(IProjectQuery.class);
  }

  /**
   * Returns all projects that exist in current context
   *
   * @return all (currently selected) projects
   */
  @Nullable
  default <T> T findProjectsInCurrentContext(@NonNull ReturnType<T> pType)
  {
    return findProjects(Utilities.actionsGlobalContext(), pType);
  }

  /**
   * Returns all projects that the given lookup contains
   *
   * @param pLookup Lookup as search base
   * @return all found projects
   */
  @Nullable
  default <T> T findProjects(@NonNull Lookup.Provider pLookup, @NonNull ReturnType<T> pType)
  {
    Lookup lookup = pLookup.getLookup();
    if(lookup == null)
      return null;
    return findProjects(lookup, pType);
  }

  /**
   * Returns all projects that the given lookup contains
   *
   * @param pLookup Lookup as search base
   * @return all found projects
   */
  @Nullable
  <T> T findProjects(@NonNull Lookup pLookup, @NonNull ReturnType<T> pType);

  /**
   * Defines, what findProjects() should return
   */
  class ReturnType<T>
  {
    /**
     * Transforms all currently available projects into a single set. NULL if no project was found.
     */
    public static final ReturnType<Set<Project>> MULTIPLE_TO_SET = new ReturnType<>(pProj -> pProj.isEmpty() ? null : pProj);

    /**
     * If only one project is found, then it will be returned. NULL if none or mulitple found.
     */
    public static final ReturnType<Project> MULTIPLE_TO_NULL = new ReturnType<>(pProj -> {
      if(pProj.size() == 1)
        return pProj.iterator().next();
      return null;
    });

    /**
     * Finds any project in the current lookup. NULL if nothing was found.
     */
    public static final ReturnType<Project> FIND_FIRST = new ReturnType<>(pProj -> pProj.stream()
        .findFirst()
        .orElse(null));

    private final Function<Set<Project>, T> returnValueFn;

    public ReturnType(@NonNull Function<Set<Project>, T> pReturnValueFn)
    {
      returnValueFn = pReturnValueFn;
    }

    /**
     * Converts the given set of projects to the correct return value
     *
     * @param pProjects Projects
     * @return return value
     */
    @Nullable
    public T toReturnValue(@NonNull Set<Project> pProjects)
    {
      return returnValueFn.apply(pProjects);
    }
  }

}
