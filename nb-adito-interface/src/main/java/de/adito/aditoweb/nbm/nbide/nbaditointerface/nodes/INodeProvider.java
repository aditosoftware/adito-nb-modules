package de.adito.aditoweb.nbm.nbide.nbaditointerface.nodes;

import org.jetbrains.annotations.*;
import org.openide.filesystems.FileObject;
import org.openide.nodes.Node;

/**
 * Provider for nodes
 *
 * @author s.seemann, 20.07.2021
 */
public interface INodeProvider
{
  /**
   * Returns the node of a linked file object
   *
   * @param pLinkedFo the file object
   * @return the node or null, if none could be found
   */
  @Nullable
  Node findNodeFromLinkedFo(@NotNull FileObject pLinkedFo);
}
