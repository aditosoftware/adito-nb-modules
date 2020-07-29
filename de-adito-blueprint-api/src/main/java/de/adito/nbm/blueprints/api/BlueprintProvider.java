package de.adito.nbm.blueprints.api;

import de.adito.picoservice.*;

import java.lang.annotation.*;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Annotates an IBlueprintProvider
 *
 * @see IBlueprintProvider
 * @author w.glanzer, 28.07.2020
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@PicoService
public @interface BlueprintProvider
{

  /**
   * Contains all available BlueprintProviders
   */
  Collection<IBlueprintProvider> INSTANCES = IPicoRegistry.INSTANCE.find(IBlueprintProvider.class, BlueprintProvider.class).keySet().stream()
      .map(pBlueprintProvider -> {
        try
        {
          return pBlueprintProvider.getDeclaredConstructor().newInstance();
        }
        catch (Exception e)
        {
          throw new RuntimeException(e);
        }
      })
      .collect(Collectors.toList());

}
