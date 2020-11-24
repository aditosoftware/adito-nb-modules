package de.adito.nbm.runconfig.api;

import org.jetbrains.annotations.Nullable;
import org.openide.nodes.Node;

/**
 * @author m.kaspera, 26.10.2020
 */
public interface INodeSystemInfoProvider
{

  @Nullable
  ISystemInfo getSystemInfoFromNodes(Node[] pActivatedNodes);

}
