package de.adito.nbm.runconfig.api;

import io.reactivex.rxjava3.core.Observable;
import org.jetbrains.annotations.NotNull;
import org.netbeans.api.project.Project;

import java.util.List;

/**
 * Provider to find "system"-RunConfigs.
 * Those are available by default and can not be deleted/added by user.
 *
 * @author w.glanzer, 24.12.2018
 */
public interface ISystemRunConfigProvider
{

  /**
   * @return Returns an Observable for all available RunConfigurations.
   */
  @NotNull
  Observable<List<IRunConfig>> runConfigurations(List<ISystemInfo> pSystemInfos);

  /**
   * Get an instance for a project. Can be a specialised instance for each project, or can also return itself if the instance
   * can handle more than one project
   *
   * @param pProject Project for which an instance of an ISystemRunConfigProvider should be retrieved
   * @return ISystemRunConfigProvider for the project
   */
  ISystemRunConfigProvider getInstance(Project pProject);

}
