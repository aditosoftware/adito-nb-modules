package de.adito.aditoweb.nbm.nbide.nbaditointerface.metainfo;

import org.jetbrains.annotations.*;

/**
 * The source of meta information
 *
 * @author s.seemann, 29.06.2021
 */
public interface IMetaInfoSource
{
  /**
   * @return Name of the system, where the meta information are located
   */
  @NotNull
  String getSystemName();

  /**
   * @return ServerId of the system, where the meta information are located or null, if it cannot be found
   */
  @Nullable
  String getServerId();

  /**
   * @return the path to the server config
   */
  @NotNull
  String getServerConfigPath();
}