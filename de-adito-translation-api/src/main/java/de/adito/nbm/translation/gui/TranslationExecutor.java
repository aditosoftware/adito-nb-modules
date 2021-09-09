package de.adito.nbm.translation.gui;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import de.adito.nbm.translation.api.*;
import de.adito.nbm.translation.spi.*;
import org.jetbrains.annotations.*;
import org.openide.util.Lookup;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

/**
 * @author w.glanzer, 09.09.2021
 */
public class TranslationExecutor
{

  private static TranslationExecutor _INSTANCE;
  private final ExecutorService executorService = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder()
                                                                                        .setDaemon(true)
                                                                                        .setNameFormat("tTranslationExecutor")
                                                                                        .build());

  /**
   * @return singleton instance
   */
  @NotNull
  public static TranslationExecutor getInstance()
  {
    if (_INSTANCE == null)
      _INSTANCE = new TranslationExecutor();
    return _INSTANCE;
  }

  private TranslationExecutor()
  {
  }

  /**
   * Schedules a new translation request.
   * The proxy url and the auth key will be gathered automatically.
   *
   * @param pResult  Result from the previous shown translation dialog
   * @param pContent the content to translate
   * @return map containing the original text as key and the translated text as value
   */
  @NotNull
  public CompletableFuture<Map<String, String>> scheduleTranslation(@NotNull TranslationDialog.TranslationResult pResult, @NotNull List<String> pContent)
  {
    return scheduleTranslation(pResult.getFrom(), pResult.getTo(), pResult.getTranslatorType(), pResult.getLineBreakMethod(), pContent);
  }

  /**
   * Schedules a new translation request.
   *
   * @param pResult  Result from the previous shown translation dialog
   * @param pContent the content to translate
   * @return map containing the original text as key and the translated text as value
   */
  @NotNull
  public CompletableFuture<Map<String, String>> scheduleTranslation(@NotNull TranslationDialog.TranslationResult pResult, @Nullable String pAuthCode,
                                                                    @Nullable String pProxyUrl, @NotNull List<String> pContent)
  {
    return scheduleTranslation(pResult.getFrom(), pResult.getTo(), pResult.getTranslatorType(), pAuthCode, pResult.getLineBreakMethod(),
                               pProxyUrl, pContent);
  }

  /**
   * Schedules a new translation request.
   * The proxy url and the auth key will be gathered automatically.
   *
   * @param pFrom            Source Language
   * @param pTo              Target Language
   * @param pType            Type of the translator to use
   * @param pLineBreakMethod Determines the handling of line breaks / line endings
   * @param pContent         the content to translate
   * @return map containing the original text as key and the translated text as value
   */
  @NotNull
  public CompletableFuture<Map<String, String>> scheduleTranslation(@NotNull Locale pFrom, @NotNull Locale pTo, @NotNull ETranslatorType pType,
                                                                    @NotNull ELineBreakMethod pLineBreakMethod, @NotNull List<String> pContent)
  {
    return CompletableFuture.supplyAsync(() -> {
      // must not be null
      String authCode = Lookup.getDefault().lookupAll(ITranslatorAuthKeyProvider.class).stream()
          .map(pProvider -> pProvider.getAuthKey(pType))
          .filter(Objects::nonNull)
          .findFirst()
          .orElseThrow();

      // can be null
      String proxyURL = Lookup.getDefault().lookupAll(ITranslatorProxyURLProvider.class).stream()
          .map(pProvider -> pProvider.getProxyURL(pType))
          .filter(Objects::nonNull)
          .findFirst()
          .orElse(null);

      return _doTranslation(pFrom, pTo, pType, authCode, pLineBreakMethod, proxyURL, pContent);
    }, executorService);
  }

  /**
   * Schedules a new translation request.
   *
   * @param pFrom            Source Language
   * @param pTo              Target Language
   * @param pType            Type of the translator to use
   * @param pAuthCode        API Key for the endpoint, if necessary - NULL otherwise
   * @param pProxyUrl        Proxy to use, NULL if no proxy should be used
   * @param pLineBreakMethod Determines the handling of line breaks / line endings
   * @param pContent         the content to translate
   * @return map containing the original text as key and the translated text as value
   */
  @NotNull
  public CompletableFuture<Map<String, String>> scheduleTranslation(@NotNull Locale pFrom, @NotNull Locale pTo, @NotNull ETranslatorType pType,
                                                                    @Nullable String pAuthCode, @NotNull ELineBreakMethod pLineBreakMethod,
                                                                    @Nullable String pProxyUrl, @NotNull List<String> pContent)
  {
    return CompletableFuture.supplyAsync(() -> _doTranslation(pFrom, pTo, pType, pAuthCode, pLineBreakMethod, pProxyUrl, pContent), executorService);
  }

  /**
   * Translates the given content - blocking.
   *
   * @param pFrom            Source Language
   * @param pTo              Target Language
   * @param pType            Type of the translator to use
   * @param pAuthCode        API Key for the endpoint, if necessary - NULL otherwise
   * @param pProxyUrl        Proxy to use, NULL if no proxy should be used
   * @param pLineBreakMethod Determines the handling of line breaks / line endings
   * @param pContent         the content to translate
   * @return map containing the original text as key and the translated text as value
   */
  @NotNull
  private Map<String, String> _doTranslation(@NotNull Locale pFrom, @NotNull Locale pTo, @NotNull ETranslatorType pType, @Nullable String pAuthCode,
                                             @NotNull ELineBreakMethod pLineBreakMethod, @Nullable String pProxyUrl, @NotNull List<String> pContent)
  {
    String[] translateRequest = pContent.toArray(new String[0]);
    ITranslationService translationService = TranslationServiceFactory.getTranslationService(pType);
    ITranslationResult translationResult = translationService.translate(pFrom, pTo, pAuthCode, pProxyUrl, pLineBreakMethod, translateRequest);
    String[] translateResult = translationResult.getTranslatedTexts();
    return IntStream.range(0, translateRequest.length).boxed().collect(Collectors.toMap(i -> translateRequest[i], i -> translateResult[i]));
  }

}
