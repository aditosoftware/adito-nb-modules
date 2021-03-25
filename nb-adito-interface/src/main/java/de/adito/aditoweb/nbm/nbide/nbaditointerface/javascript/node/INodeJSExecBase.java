package de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript.node;

import org.jetbrains.annotations.*;

/**
 * Determines on which executable the command within the executor should be executed on
 *
 * @author w.glanzer, 19.03.2021
 */
public interface INodeJSExecBase
{

  /**
   * Use this base, if you want to execute something within the ".bin" folder in ".node_modules"
   *
   * @param pBinaryName Name of the Binary
   * @return the base
   */
  @NotNull
  static INodeJSExecBase binary(@NotNull String pBinaryName)
  {
    return new SimpleNodeJSExecBase("node_modules/.bin/" + pBinaryName, true);
  }

  /**
   * Use this base, if you want to execute something on a module
   *
   * @param pModuleName Name of the module to execute something on
   * @param pInnerPath  Path to the executable inside the module
   * @return the base
   */
  @NotNull
  static INodeJSExecBase module(@NotNull String pModuleName, @Nullable String pInnerPath)
  {
    return new SimpleNodeJSExecBase("node_modules/" + pModuleName + (pInnerPath != null ? "/" + pInnerPath : ""), true);
  }

  /**
   * Use this base, if you want to execute something on the current package manager (like npm, yarn, ...)
   *
   * @return the base
   */
  @NotNull
  static INodeJSExecBase packageManager()
  {
    return new SimpleNodeJSExecBase("npm", false);
  }

  /**
   * Use this base, if you want to execute something on the current node executable
   *
   * @return the base
   */
  @NotNull
  static INodeJSExecBase node()
  {
    return new SimpleNodeJSExecBase("node", false);
  }

  /**
   * @return the base path to use in the nodejs execution command
   */
  @NotNull
  String getBasePath();

  /**
   * @return true, if the command is realtive to working directory (mainly the project)
   */
  boolean isRelativeToWorkingDir();

}
