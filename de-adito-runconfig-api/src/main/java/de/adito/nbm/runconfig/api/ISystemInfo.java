package de.adito.nbm.runconfig.api;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.tunnel.ITunnelConfigProvider;
import io.reactivex.rxjava3.core.Observable;
import org.netbeans.api.project.Project;

import java.util.Map;

/**
 * Provides access to information about a system
 *
 * @author m.kaspera, 10.07.2020
 */
public interface ISystemInfo extends ITunnelConfigProvider
{

  String WEBCLIENT_URL_KEY = "neonWebClientUrl";
  String TELNET_LOGGING_ENABLED_KEY = "loggingTelnetEnabled";
  String TELNET_HOST_EXTERNAL_ADRESS_KEY = "loggingTelnetExternalAddress";
  String TELNET_PORT_KEY = "loggingTelnetPort";

  /**
   *
   *
   * @return true if the system is declared as productive system
   */
  boolean isProductive();

  /**
   * get an Observable of the cloud property of a system
   *
   * @return Observable
   */
  Observable<Boolean> isCloud();

  /**
   * Get a map with parameter settings
   *
   * @return Map
   */
  Map<String, String> getParameters();

  /**
   * Get the project that this System belongs to
   *
   * @return Project
   */
  Project getProject();

}
