package de.adito.nbm.translation.api;

import de.adito.picoservice.PicoService;

import java.lang.annotation.*;

/**
 * @author W.Glanzer, 04.04.2017
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@PicoService
public @interface TranslationService
{

  ETranslatorType type();

}
