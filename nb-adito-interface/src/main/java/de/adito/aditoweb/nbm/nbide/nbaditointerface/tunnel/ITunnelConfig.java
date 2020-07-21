package de.adito.aditoweb.nbm.nbide.nbaditointerface.tunnel;

/**
 * Defines the methods a class, that defines a configuration for a tunnel, has to provide
 *
 * @author m.kaspera, 20.07.2020
 */
public interface ITunnelConfig
{

  /**
   * Name for this config
   *
   * @return name
   */
  String getName();

  /**
   * The adress of the server that creates the tunnel to the target
   *
   * @return adress
   */
  String getTunnelHostAddress();

  /**
   * Adress to which the tunnel should lead to
   *
   * @return adress
   */
  String getTargetServerAddress();

  /**
   * Port, under which the tunnel host should be contacted
   *
   * @return port number
   */
  Integer getTunnelHostPort();

  /**
   * Port of the server that the tunnel should lead to. If the tunnel is to e.g. a Database, this port number should be the one used for contacting
   * said database
   *
   * @return port number
   */
  Integer getTargetServerPort();

  /**
   * user name for authentication
   *
   * @return name of the user
   */
  String getUser();

}
