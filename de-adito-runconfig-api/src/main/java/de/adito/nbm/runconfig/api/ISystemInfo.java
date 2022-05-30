package de.adito.nbm.runconfig.api;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.tunnel.ITunnelConfigProvider;
import de.adito.aditoweb.properties.PropertyAlias;
import de.adito.nbm.runconfig.exception.PropertyNotFoundException;
import io.reactivex.rxjava3.core.Observable;
import org.jetbrains.annotations.NotNull;
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
  String CLOUD_ID_PREF_KEY_PEFIX = "ssp.cloudSystemId.";
  String SYSTEM_ADDRESS = "system.host.adress";

  /**
   * @return true if the system is declared as productive system
   */
  boolean isProductive();

  /**
   * get an Observable of the cloud property of a system
   *
   * @return Observable
   */
  Observable<String> getCloudId();

  /**
   * Sets the CloudId for the current system, making it a cloud system in the process
   *
   * @param pCloudId the CloudId to be set
   */
  void setCloudId(@NotNull String pCloudId);

  /**
   * Sets the requested value for the given property. Some Property names are available in de.adito.properties.PropertyNames found in
   * de-adito-netbeans-utilities
   *
   * @param pPropertyAlias name of the property to be set
   * @param pPropertyValue value to be set
   * @throws PropertyNotFoundException if no property for the given name could be found
   */
  void setProperty(@NotNull PropertyAlias pPropertyAlias, @NotNull String pPropertyValue) throws PropertyNotFoundException;

  /**
   * @return absolute Path that of the $ADITODATA path property
   */
  String getResolvedAditoDataPath();

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
