package de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript.node;

import org.jetbrains.annotations.*;

/**
 * @author w.glanzer, 19.03.2021
 */
class SimpleNodeJSExecBase implements INodeJSExecBase
{

  private final String basePath;
  private final boolean realtiveToProject;
  private final String winExt;
  private final String macExt;
  private final String linuxExt;

  public SimpleNodeJSExecBase(@NotNull String pBasePath, boolean pRealtiveToProject)
  {
    basePath = pBasePath;
    realtiveToProject = pRealtiveToProject;
    winExt = null;
    macExt = null;
    linuxExt = null;
  }

  public SimpleNodeJSExecBase(@NotNull String pBasePath, boolean pRealtiveToProject,
                              @Nullable String pWinExt, @Nullable String pMacExt, @Nullable String pLinuxExt)
  {
    basePath = pBasePath;
    realtiveToProject = pRealtiveToProject;
    winExt = pWinExt;
    macExt = pMacExt;
    linuxExt = pLinuxExt;
  }

  @NotNull
  @Override
  public String getBasePath()
  {
    return basePath;
  }

  @Override
  public boolean isRelativeToWorkingDir()
  {
    return realtiveToProject;
  }

  @Nullable
  @Override
  public String getWindowsExt()
  {
    return winExt;
  }

  @Nullable
  @Override
  public String getMacExt()
  {
    return macExt;
  }

  @Nullable
  @Override
  public String getLinuxExt()
  {
    return linuxExt;
  }

}
