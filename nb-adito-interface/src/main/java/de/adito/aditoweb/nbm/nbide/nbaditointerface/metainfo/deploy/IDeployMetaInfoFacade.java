package de.adito.aditoweb.nbm.nbide.nbaditointerface.metainfo.deploy;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.metainfo.*;
import org.jetbrains.annotations.*;
import org.netbeans.api.project.Project;

import java.io.File;
import java.util.List;

/**
 * Facade for deploy meta information
 *
 * @author s.seemann, 29.06.2021
 */
public interface IDeployMetaInfoFacade
{
  /**
   * @return all available sources
   */
  @NotNull
  List<IMetaInfoSource> getSources(@NotNull Project pProject);

  /**
   * Returns the corresponding source of a server config
   *
   * @param pPathServerConfig path to the server config
   * @return the source or null, if it cannot be found
   */
  @Nullable
  IMetaInfoSource getSource(@NotNull Project pProject, @NotNull String pPathServerConfig);

  /**
   * Returns the meta information of a source
   *
   * @param pSource the source
   * @return the meta information or null, if the source is not valid
   */
  @Nullable
  IMetaInfo getInfos(@NotNull IMetaInfoSource pSource);

  /**
   * Returns the meta information of a source
   *
   * @param pServerConfigFile server config file
   * @return the meta information or null, if the source is not valid
   */
  @Nullable
  IMetaInfo getInfos(@NotNull File pServerConfigFile);
}
