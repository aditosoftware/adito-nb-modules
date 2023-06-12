package de.adito.nbm.blueprints.api;

import lombok.NonNull;

import java.util.Set;

/**
 * A BlueprintProvider provides a set of blueprints from different sources.
 *
 * @author w.glanzer, 06.07.2020
 */
public interface IBlueprintProvider
{

  /**
   * Returns a set of all available blueprint names, that can be created
   *
   * @return all names as a set
   */
  @NonNull
  Set<String> getSourceNames();

  /**
   * Creates a new, empty blueprint
   *
   * @param pSourceName Name of the blueprint
   * @return new instance
   * @throws BlueprintNotAvailableException this exception gets thrown, if a blueprint with pSourceName as name could not be found.
   */
  @NonNull
  IBlueprint read(@NonNull String pSourceName) throws BlueprintNotAvailableException;

}
