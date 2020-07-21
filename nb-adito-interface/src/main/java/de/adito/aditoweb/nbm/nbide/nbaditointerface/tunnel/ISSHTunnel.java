package de.adito.aditoweb.nbm.nbide.nbaditointerface.tunnel;

import org.jetbrains.annotations.*;
import org.openide.nodes.Node;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

/**
 * Defines methods und capabilites of a SSH tunnel
 *
 * @author m.kaspera, 28.11.2019
 */
public interface ISSHTunnel extends AutoCloseable
{

  /**
   * Port of the server that the tunnel should lead to. If the tunnel is to e.g. a Database, this port number should be the one used for contacting
   * said database
   *
   * @return port number
   */
  int getRemoteTargetPort();

  /**
   * Adress to which the tunnel should lead to
   *
   * @return adress
   */
  String getRemoteTarget();

  /**
   * Adds a listener that gets notified if the ConnectionStatus of a connection changes
   *
   * @param pListener Listener to be added
   */
  void addConnectionListener(ISSHTunnelConnectionListener pListener);

  /**
   * The adress of the server that creates the tunnel to the target
   *
   * @return adress
   */
  String getTunnelHost();

  /**
   * Port, under which the tunnel host should be contacted
   *
   * @return port number
   */
  int getPort();

  /**
   * Returns the adress with which the tunnel is reached, should be in the localhost adress range
   *
   * @return adress of the "entry" of the tunnel
   */
  @Nullable
  String getLocalTarget();

  /**
   * Returns the current connection state, see ConnectionState
   *
   * @return the current connection state
   */
  ConnectionState getConnectionState();

  /**
   * checks if the tunnel is connected and active
   *
   * @return true if the connection is established and active
   */
  boolean isConnected();

  /**
   * Establishes the tunnel connection
   *
   * @return CompletableFuture with the local target adress
   */
  @NotNull
  CompletableFuture<String> connect();

  void removeConnectionListener(ISSHTunnelConnectionListener pListener);

  /**
   * sets the behaviour in case of an exception (e.g. connection failure)
   *
   * @param pExceptionHandler BiConsumer that is called if an exception occurrs, passes the session and the exception that occurred
   */
  void setExceptionHandler(BiConsumer<ISSHTunnel, Exception> pExceptionHandler);

  /**
   * @return Node that represents the tunnel
   */
  Node getNodeDelegate();

  /**
   * Possible states of a connection
   */
  enum ConnectionState
  {
    /**
     * Connection is not initialised yet
     */
    NOT_CONNECTED,
    /**
     * Connection is being established
     */
    CONNECTING,
    /**
     * Connection is active
     */
    CONNECTED,
    /**
     * Connection was active and has already been closed as part of a normal operation
     */
    DISCONNECTED,
    /**
     * Connection has failed
     */
    CONNECTION_FAILURE
  }
}
