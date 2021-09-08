package de.adito.nbm.translation.api;

import de.adito.picoservice.IPicoRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Factory that creates TranslatorServices to translate strings into another language
 *
 * @author W.Glanzer, 04.04.2017
 * @see TranslationService
 */
public class TranslationServiceFactory
{

  private static final Map<ETranslatorType, ITranslationService> _SERVICES = _initServices();

  /**
   * Returns the translation service of a given type
   *
   * @param pType Type/"Hoster" of the translator
   * @return the translator
   * @throws TranslationException If the translator could not be found
   */
  @NotNull
  public static ITranslationService getTranslationService(@NotNull ETranslatorType pType) throws TranslationException
  {
    ITranslationService service = _SERVICES.get(pType);
    if (service == null)
      throw new TranslationException("implementing service for type " + pType + " not found");
    return service;
  }

  /**
   * Initializes the services (creates new instances of it)
   *
   * @return a map containg the ETranslatorType as key and the corresponding Service as value
   */
  private static Map<ETranslatorType, ITranslationService> _initServices()
  {
    return IPicoRegistry.INSTANCE.find(ITranslationService.class, TranslationService.class).entrySet().stream()
        .map(pEntry -> {
          try
          {
            ITranslationService serviceInstance = pEntry.getKey().newInstance();
            return new AbstractMap.SimpleImmutableEntry<>(pEntry.getValue().type(), serviceInstance);
          }
          catch (Exception e)
          {
            Logger.getLogger(TranslationServiceFactory.class.getName()).warning("Failed to initiate " + pEntry.getValue().type().name() +
                                                                                    " translation service " + pEntry.getKey().getCanonicalName());
            return null;
          }
        })
        .filter(Objects::nonNull)
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

}
