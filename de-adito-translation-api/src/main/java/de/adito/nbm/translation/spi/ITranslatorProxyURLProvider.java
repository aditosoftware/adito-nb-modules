package de.adito.nbm.translation.spi;

import de.adito.nbm.translation.api.ETranslatorType;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

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
  String getProxyURL(@NonNull ETranslatorType pType);

}
