package de.adito.nbm.translation.api;

import org.jetbrains.annotations.*;

import java.util.Locale;

/**
 * Contains the result of a translation request.
 *
 * @author W.Glanzer, 05.04.2017
 */
public interface ITranslationResult
{

  /**
   * Returns the original texts that where sent to the translator service
   *
   * @return Text as String
   */
  @NotNull
  String[] getSourceTexts();

  /**
   * Returns the translated texts that where retrieved from the translator service
   *
   * @return Text as String
   */
  @NotNull
  String[] getTranslatedTexts();

  /**
   * The locale of the original text
   *
   * @return the locale or NULL, if it should be determined automatically
   */
  @Nullable
  Locale getSourceLocale();

  /**
   * The locale of the translated text
   *
   * @return the locale
   */
  @NotNull
  Locale getTargetLocale();

}
