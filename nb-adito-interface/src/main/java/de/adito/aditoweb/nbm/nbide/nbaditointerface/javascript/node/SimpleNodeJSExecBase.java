package de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript.node;

import org.jetbrains.annotations.*;

/**
 * @author w.glanzer, 19.03.2021
 */
class SimpleNodeJSExecBase implements INodeJSExecBase
{

  private final String basePath;
  private final boolean realtiveToProject;

  public SimpleNodeJSExecBase(@NotNull String pBasePath, boolean pRealtiveToProject)
  {
    basePath = pBasePath;
    realtiveToProject = pRealtiveToProject;
  }

  @NotNull
  @Override
  public String getBasePath()
  {
    return basePath;
  }

  @Override
  public boolean isRelativeToProject()
  {
    return realtiveToProject;
  }
}
