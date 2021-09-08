package de.adito.nbm.translation.api;

import org.jetbrains.annotations.*;

import java.util.Locale;

/**
 * A TranslationService describes a service, that translates text with the help of an external tool.
 * Will be created once, has to be threadSafe!
 *
 * @author W.Glanzer, 05.04.2017
 */
public interface ITranslationService
{

  /**
   * Translates the given strings into another language
   *
   * @param pFrom               Source Language
   * @param pTo                 Target Language
   * @param pAuthCode           API Key for the endpoint, if necessary - NULL otherwise
   * @param pLineBreakMethod    Determines the handling of line breaks / line endings
   * @param pStringsToTranslate Strings to translate
   */
  @NotNull
  default ITranslationResult translate(@Nullable Locale pFrom, @NotNull Locale pTo, @Nullable String pAuthCode,
                                       @NotNull ELineBreakMethod pLineBreakMethod, @NotNull String... pStringsToTranslate)
      throws TranslationException
  {
    return translate(pFrom, pTo, pAuthCode, null, pLineBreakMethod, pStringsToTranslate);
  }

  /**
   * Translates the given strings into another language
   *
   * @param pFrom               Source Language
   * @param pTo                 Target Language
   * @param pAuthCode           API Key for the endpoint, if necessary - NULL otherwise
   * @param pProxyUrl           Proxy to use, NULL if no proxy should be used
   * @param pLineBreakMethod    Determines the handling of line breaks / line endings
   * @param pStringsToTranslate Strings to translate
   */
  @NotNull
  ITranslationResult translate(@Nullable Locale pFrom, @NotNull Locale pTo, @Nullable String pAuthCode, @Nullable String pProxyUrl,
                               @NotNull ELineBreakMethod pLineBreakMethod, @NotNull String... pStringsToTranslate)
      throws TranslationException;

}
