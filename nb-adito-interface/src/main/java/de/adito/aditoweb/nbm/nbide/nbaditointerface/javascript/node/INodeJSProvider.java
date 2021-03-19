package de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript.node;

import io.reactivex.rxjava3.core.Observable;
import org.jetbrains.annotations.*;
import org.netbeans.api.project.Project;

import java.util.Optional;

/**
 * Provider for nodeJS
 *
 * @author w.glanzer, 05.03.2021
 */
public interface INodeJSProvider
{

  /**
   * Returns the provider for the given project
   *
   * @param pProject Project to search provider for
   * @return the provider
   */
  @NotNull
  static Optional<INodeJSProvider> findInstance(@NotNull Project pProject)
  {
    return Optional.ofNullable(pProject.getLookup().lookup(INodeJSProvider.class));
  }

  /**
   * Observes the current nodejs environment of the project
   *
   * @return observable with the current environment
   */
  @NotNull
  Observable<Optional<INodeJSEnvironment>> current();

}
