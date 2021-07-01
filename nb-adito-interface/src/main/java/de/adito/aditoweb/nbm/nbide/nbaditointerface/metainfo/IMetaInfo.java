package de.adito.aditoweb.nbm.nbide.nbaditointerface.metainfo;

import org.jetbrains.annotations.*;

import java.util.Map;

/**
 * Meta information about something, e. g. about the last deploy
 *
 * @author s.seemann, 29.06.2021
 */
public interface IMetaInfo
{
  /**
   * @return all meta information
   */
  @NotNull
  Map<String, String> getAll();

  /**
   * @param pKey the key of the meta information
   * @return the meta information or null, if it cannot be found
   */
  @Nullable
  String getValue(@NotNull String pKey);
}
