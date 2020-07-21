package de.adito.aditoweb.nbm.nbide.nbaditointerface.tunnel;

/**
 * Interface definition for a listener that get notified if the connectionState of a tunnel changes
 *
 * @author m.kaspera, 16.12.2019
 */
public interface ISSHTunnelConnectionListener
{

  void connectionStateChanged(ISSHTunnel pTunnel, ISSHTunnel.ConnectionState pNewState);

}