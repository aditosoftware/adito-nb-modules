package de.adito.nbm.blueprints.api;

import de.adito.picoservice.*;

import java.lang.annotation.*;

/**
 * Annotates an IBlueprintPreset
 *
 * @see IBlueprintPreset
 * @author s.seemann, 14.12.2020
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@PicoService
public @interface BlueprintPresets
{

  EContentType type();

  int position() default Integer.MAX_VALUE;
}
