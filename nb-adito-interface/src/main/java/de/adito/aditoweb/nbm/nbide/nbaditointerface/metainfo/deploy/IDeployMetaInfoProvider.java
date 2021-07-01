package de.adito.aditoweb.nbm.nbide.nbaditointerface.metainfo.deploy;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Provides meta information about the deploy
 *
 * @author s.seemann, 29.06.2021
 */
public interface IDeployMetaInfoProvider
{
  /**
   * Name of the provider, which is used as prefix for the meta info keys
   *
   * @return the name
   */
  @NotNull
  String getName();

  /**
   * @return the meta information as key-value-pairs
   */
  @NotNull
  Map<String, String> getMetaInfo();
}
