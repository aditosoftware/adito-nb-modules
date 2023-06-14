package de.adito.swing.quicksearch;

import lombok.NonNull;
import org.jetbrains.annotations.*;
import org.netbeans.swing.outline.*;

import javax.swing.tree.*;
import java.awt.*;

/**
 * Implementation of the Callback of QuickSearch for Outline TreeTables
 *
 * @author w.glanzer, 29.04.2020
 */
public abstract class QuickSearchOutlineCallback extends QuickSearchTreeCallback
{

  private final Outline treeTable;

  public QuickSearchOutlineCallback(@NonNull Outline pTreeTable)
  {
    super();
    treeTable = pTreeTable;
  }

  public QuickSearchOutlineCallback(@NonNull Outline pTreeTable, boolean pExpandOnResultFound)
  {
    super(pExpandOnResultFound);
    treeTable = pTreeTable;
  }

  @NonNull
  @Override
  protected TreeNode getRootNode()
  {
    return (TreeNode) ((OutlineModel) treeTable.getModel()).getRoot();
  }

  @Override
  protected void goToFoundPath(@Nullable TreePath pFoundPath, boolean pExpand)
  {
    if (pFoundPath != null)
    {
      if(pExpand)
        treeTable.expandPath(pFoundPath);

      Rectangle bounds = treeTable.getPathBounds(pFoundPath);
      if(bounds != null)
      {
        int row = treeTable.rowAtPoint(bounds.getLocation());
        treeTable.scrollRectToVisible(bounds);
        treeTable.getSelectionModel().setSelectionInterval(row, row);
      }
    }
    else
    {
      treeTable.getSelectionModel().clearSelection();
    }
  }

  @Override
  protected boolean isVisible(@NonNull TreePath pPath)
  {
    return treeTable.isVisible(pPath);
  }

}
