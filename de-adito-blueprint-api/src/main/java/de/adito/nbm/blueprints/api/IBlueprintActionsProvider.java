package de.adito.nbm.blueprints.api;

import lombok.NonNull;

import javax.swing.*;

/**
 * Provider for Blueprint-Actions with specific types
 *
 * @author s.seemann, 11.12.2020
 */
public interface IBlueprintActionsProvider
{

  /**
   * Returns an instance of the "create"-ActionGroup
   *
   * @param pTypes the name of the types of the blueprints, which should be added to the ActionGroup
   * @return the ActionGroup
   */
  Action createModelActionGroup(@NonNull String... pTypes);
}
