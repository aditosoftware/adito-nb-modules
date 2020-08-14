package de.adito.aditoweb.nbm.nbide.nbaditointerface.database;

import org.jetbrains.annotations.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.Collection;
import java.util.function.Function;

/**
 * @author w.glanzer, 13.08.2020
 */
public interface IPossibleConnectionProvider
{

  /**
   * Collects and returns all currently possible database connections
   *
   * @return connections
   */
  @NotNull
  Collection<IPossibleDBConnection> getPossibleConnections();

  /**
   * Defines the database connection, that maybe possible to connect to
   */
  interface IPossibleDBConnection
  {

    /**
     * @return the URL where this connection points to
     */
    @NotNull
    String getURL();

    /**
     * @return a simple "hint" for the user to know, where this connection may come from
     */
    @Nullable
    String getSourceName();

    /**
     * Opens the connection and executes something on it
     *
     * @param pFunction Function that consumes the connection and executes something on it
     * @return the result of the function
     */
    <T, Ex extends Throwable> T withJDBCConnection(@NotNull IConnectionFunction<T, Ex> pFunction) throws IOException, Ex;

    /**
     * Function for the connection - exception aware
     */
    interface IConnectionFunction<T, Ex extends Throwable>
    {
      /**
       * Consumes the given connection and executes something on it
       *
       * @param pConnection Connection
       * @throws Ex Exception, if any
       */
      T apply(@NotNull Connection pConnection) throws Ex;
    }
  }
}
