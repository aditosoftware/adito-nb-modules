package de.adito.properties;

import de.adito.aditoweb.properties.PropertyAlias;
import lombok.NonNull;

/**
 * Interface that contains the names/aliases for properties of data models. One data model is encapsulated by an enum, and the enum contains
 * the (exposed) properties of that data model. The enums have to implement the PropertyAlias interface
 *
 * @author m.kaspera, 25.05.2022
 */
public interface PropertyNames
{

  /**
   * Contains names/aliases for the exposed properties of the SystemDataModel
   */
  enum SystemDataModel implements PropertyAlias
  {
    /**
     * IP address for client requests. If you want the server address
     * to be different from the designer when you start the client than in the preferences (<b>systemServerAddress</b>), it will be entered here.
     */
    SERVER_ADDRESS("serverAddress"),
    /**
     * Port that the client uses to connect to the server. If this property is not set, the port given in the instance configuration will be used.
     * This property is mandatory, if the server is behind a reverse proxy.
     */
    SERVER_PORT("serverPort"),
    /**
     * denotes the path for the server config file
     */
    SERVER_CONFIG_PATH("serverConfigPath"),
    /**
     * denotes the path for the tunnel config file
     */
    TUNNEL_CONFIG_PATH("tunnelConfigPath");

    final String value;

    SystemDataModel(String pValue)
    {
      value = pValue;
    }

    @NonNull
    public String getPropertyName()
    {
      return value;
    }
  }

}
