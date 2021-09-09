package de.adito.nbm.translation.spi;

import de.adito.nbm.translation.api.ETranslatorType;
import org.jetbrains.annotations.*;

/**
 * @author w.glanzer, 09.09.2021
 */
public interface ITranslatorProxyURLProvider
{

  /**
   * Provides an proxy url for the given translator type
   *
   * @param pType Type of translator
   * @return proxy url or NULL if nothing found
   */
  @Nullable
  String getProxyURL(@NotNull ETranslatorType pType);

}
