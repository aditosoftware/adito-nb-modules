package de.adito.nbm.blueprints.api;


import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Describes the strucutre and content of a single blueprint
 *
 * @author w.glanzer, 06.07.2020
 */
public interface IBlueprint
{

  /**
   * @return Returns all parameters for this blueprint
   */
  @NonNull
  List<IBlueprintParameter> getParameters();

  /**
   * @return Returns all presets for this blueprint
   */
  @NonNull
  List<IBlueprintPreset> getPresets();

  /**
   * @return a unique identifier
   */
  @NonNull
  String getID();

  /**
   * @return the displayName on GUI / dialog
   */
  @NonNull
  String getDisplayName();

  /**
   * @return the type of model (entity, neonView, etc.) as string or NULL, if it is available for all types of models
   */
  @Nullable
  String getType();

}
