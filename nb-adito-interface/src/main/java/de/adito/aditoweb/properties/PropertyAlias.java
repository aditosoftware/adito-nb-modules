package de.adito.aditoweb.properties;

import org.jetbrains.annotations.NotNull;

/**
 * Describes a property in one of the data models for plugins
 *
 * @author m.kaspera, 30.05.2022
 */
public interface PropertyAlias
{

  /**
   * Resolve the string used for accessing the property in its propertyPitProvider
   *
   * @return the name for the property
   */
  @NotNull
  String getPropertyName();

}
