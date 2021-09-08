package de.adito.nbm.translation.spi;

import de.adito.nbm.translation.api.ETranslatorType;
import org.jetbrains.annotations.*;

/**
 * @author w.glanzer, 08.09.2021
 */
public interface ITranslatorAuthKeyProvider
{

  /**
   * Provides an Authentication Key for the given translator type
   *
   * @param pType Type of translator
   * @return key or NULL if nothing found
   */
  @Nullable
  String getAuthKey(@NotNull ETranslatorType pType);

}
