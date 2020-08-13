package de.adito.aditoweb.nbm.nbide.nbaditointerface.database;

import org.jetbrains.annotations.*;
import org.netbeans.api.db.explorer.DatabaseConnection;

import java.util.Collection;

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
     * @return opens the connection to this possible connection. May fail if something is wrong with it.
     */
    @NotNull
    DatabaseConnection openConnection() throws Exception;

  }
}
