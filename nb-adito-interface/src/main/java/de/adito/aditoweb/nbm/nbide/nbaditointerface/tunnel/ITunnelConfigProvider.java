package de.adito.aditoweb.nbm.nbide.nbaditointerface.tunnel;

import io.reactivex.rxjava3.core.Observable;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

/**
 * Defines methods for classes that provide accecss to tunnel configs
 *
 * @author m.kaspera, 09.12.2019
 */
public interface ITunnelConfigProvider
{

  /**
   * Name of the system
   *
   * @return name of the system
   */
  @NotNull
  Observable<String> getSystemName();

  /**
   * Observes the list of tunnelConfigs defined for this provider
   *
   * @return Observable with the list of tunnelConfigs defined for this provider
   */
  @NotNull
  Observable<List<ITunnelConfig>> observeTunnelConfigs();

  /**
   * Observes the config file set for this provider. There are some places where this is still needed, but
   * in general the observeTunnelConfigs method should be used to get the tunnels that belong to this provider
   *
   * @return Observable Optional of the config file that defines the tunnels for this provider
   */
  @Deprecated
  @NotNull
  Observable<Optional<File>> observeTunnelConfigFile();

}
