package de.adito.aditoweb.nbm.nbide.nbaditointerface.model;

import org.jetbrains.annotations.*;

import java.util.List;

/**
 * Facade for model information
 *
 * @author s.seemann, 20.07.2021
 */
public interface IModelFacade
{
  /**
   * @return all available major model types
   */
  @NotNull
  List<Class<?>> getMajorModelTypes();

  /**
   * Returns the name of the model group for a specific model type
   *
   * @param pType the type of the model
   * @return the name of the model group or null, if it cannot be found
   */
  @Nullable
  String getModelGroupNameForType(@NotNull Class<?> pType);
}
