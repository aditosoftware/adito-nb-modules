package de.adito.aditoweb.nbm.nbide.nbaditointerface.nodes;

import org.jetbrains.annotations.*;
import org.openide.nodes.Node;

import java.util.Map;

/**
 * This support is able to modify nodes
 *
 * @author w.glanzer, 03.08.2020
 */
public interface INodeModificationSupport
{

  /**
   * Determines, if the given node can be modified by this support
   *
   * @param pNode Node to be modified
   * @return true, if it can be modified
   */
  boolean canModify(@NotNull Node pNode);

  /**
   * Modifies the given node or creates a new node if necessary
   *
   * @param pNode       Node to be modified
   * @param pAttributes Hints for modification
   * @return the modified / new node or NULL, if this node should be destroyed
   */
  @Nullable
  Node modify(@NotNull Node pNode, @NotNull Map<Object, Object> pAttributes);

}
