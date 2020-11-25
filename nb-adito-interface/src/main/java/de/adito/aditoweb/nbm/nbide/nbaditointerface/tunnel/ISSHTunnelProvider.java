package de.adito.aditoweb.nbm.nbide.nbaditointerface.tunnel;

import org.jetbrains.annotations.NotNull;

import javax.xml.transform.TransformerException;
import java.io.InputStream;
import java.util.List;

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

  /**
   * @param pInputStream InputStream with the contents of the config
   * @return List of ITunnelConfigs contained in the config file
   * @throws TransformerException If an exception occurrs while reading the xml
   */
  @NotNull
  List<ITunnelConfig> readTunnelsFromConfig(@NotNull InputStream pInputStream) throws TransformerException;

}