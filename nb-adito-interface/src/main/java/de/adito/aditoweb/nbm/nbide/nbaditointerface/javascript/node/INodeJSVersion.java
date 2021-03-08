package de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript.node;

import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * Describes a single version (installation) of nodejs
 *
 * @author w.glanzer, 05.03.2021
 */
public interface INodeJSVersion
{

  /**
   * @return Path to the nodejs installation
   */
  @NotNull
  File getPath();

  /**
   * @return Version of nodejs
   */
  @NotNull
  String getVersion();

  /**
   * @return true, if this version is valid and can be used to execute commands
   */
  boolean isValid();

}
