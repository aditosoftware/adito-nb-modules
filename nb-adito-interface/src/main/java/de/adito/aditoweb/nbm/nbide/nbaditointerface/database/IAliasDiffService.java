package de.adito.aditoweb.nbm.nbide.nbaditointerface.database;

import org.jetbrains.annotations.NotNull;
import org.netbeans.api.project.Project;

/**
 * Shows the difference between the locale database and the liquibase manipulated remote database.
 *
 * @author t.tasior, 18.04.2019
 */
public interface IAliasDiffService
{

  /**
   * Executes the "diff with DB" action on the given alias
   *
   * @param pProject   Project
   * @param pAliasName Alias Name
   */
  void executeDiffWithDB(@NotNull Project pProject, @NotNull String pAliasName);

}
