package de.adito.aditoweb.nbm.nbide.nbaditointerface.tunnel;

import org.jetbrains.annotations.NotNull;

/**
 * Specifies methods that a provider of SSHTunnels has to have
 *
 * Used Terminology
 * Local target: Adress in localhost adress range, this is the "entry" of the tunnel
 * Tunnel host: Adresse of the server, that establishes the tunnelled connection to the target
 * Remote target: Adress of the server/host to which a connection should be established
 *
 * @author m.kaspera, 28.11.2019
 */
public interface ISSHTunnelProvider
{

  /**
   * @param pTunnelConfig TunnelConfigDataModel containing the tunnel connection details
   * @return Instance of a SSHTunnel, has to be started via a call to "connect"
   */
  ISSHTunnel createTunnel(@NotNull ITunnelConfig pTunnelConfig);

}