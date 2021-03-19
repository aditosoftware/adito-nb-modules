package de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript.node;

import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * Describes a single version (installation) of nodejs and its environment
 *
 * @author w.glanzer, 05.03.2021
 */
public interface INodeJSEnvironment
{

  /**
   * @return Path to the nodejs installation
   */
  @NotNull
  File getPath();

  /**
   * Resolves the execbase to determine, on which executable a command should be run exactly
   *
   * @param pBase the base
   * @return the file
   */
  @NotNull
  File resolveExecBase(@NotNull INodeJSExecBase pBase);

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
