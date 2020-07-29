package de.adito.nbm.blueprints.api;

import org.jetbrains.annotations.*;

import java.util.Map;

/**
 * Describes a single parameter of a blueprint
 *
 * @author w.glanzer, 06.07.2020
 */
public interface IBlueprintParameter
{

  /**
   * @return DisplayName of this parameter, that gets visible on gui
   */
  @NotNull
  String getName();

  /**
   * @return unique ID of this parameter, to reference it in other parameters / models
   */
  @NotNull
  String getID();

  /**
   * @return default value, or NULL if no default value is set
   */
  @Nullable
  String getDefaultValue();

  /**
   * @return determines, if this parameter is visible on gui
   */
  boolean isVisible();

  /**
   * @return determines, if this parameter is has to be set before creating an instance of this blueprint
   */
  boolean isMandatory();

  /**
   * @return Attributes map
   */
  @NotNull
  Map<String, String> getAttributes();

  /**
   * @return Visual representation, or NULL if no representation is available.
   */
  @Nullable
  Class<? extends IBlueprintParameterEditor> getVisualComponentClass();

}
