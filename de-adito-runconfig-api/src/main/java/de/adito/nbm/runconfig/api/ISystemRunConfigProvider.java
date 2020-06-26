package de.adito.nbm.runconfig.api;

import io.reactivex.rxjava3.core.Observable;
import org.jetbrains.annotations.NotNull;

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
  Observable<List<IRunConfig>> runConfigurations();

}
