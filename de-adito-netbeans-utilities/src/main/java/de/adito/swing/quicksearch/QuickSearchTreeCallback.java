package de.adito.swing.quicksearch;

import org.jetbrains.annotations.*;
import org.openide.awt.QuickSearch;

import javax.swing.*;
import javax.swing.tree.*;
import java.util.Collections;

/**
 * Implementation of the Callback of QuickSearch for Trees
 *
 * @author w.glanzer, 29.04.2020
 * @author m.kaspera, 19.02.2019
 */
public abstract class QuickSearchTreeCallback implements QuickSearch.Callback
{

  private final JTree tree;
  private final boolean expandOnResultFound;
  private String searchString = null;
  private int searchResultIndex = 0;
  private TreePath lastFoundBuffer = null;

  protected QuickSearchTreeCallback()
  {
    this(false);
  }

  protected QuickSearchTreeCallback(boolean pExpandOnResultFound)
  {
    tree = null;
    expandOnResultFound = pExpandOnResultFound;
  }

  public QuickSearchTreeCallback(@NotNull JTree pTree)
  {
    this(pTree, false);
  }

  public QuickSearchTreeCallback(@NotNull JTree pTree, boolean pExpandOnResultFound)
  {
    tree = pTree;
    expandOnResultFound = pExpandOnResultFound;
  }

  @Override
  public String findMaxPrefix(String pPrefix)
  {
    return null;
  }

  @Override
  public void quickSearchCanceled()
  {
    searchResultIndex = 0;
    searchString = null;
  }

  @Override
  public void quickSearchConfirmed()
  {
    _SearchResult searchResult = _findNthOccurrence(searchString, searchResultIndex, null, getRootNode());
    goToFoundPath(searchResult.getValidPath(), expandOnResultFound);
  }

  @Override
  public void quickSearchUpdate(String pSearchText)
  {
    searchResultIndex = 0;
    searchString = pSearchText;
    _SearchResult searchResult = _findNthOccurrence(searchString, searchResultIndex, null, getRootNode());
    goToFoundPath(searchResult.getValidPath(), expandOnResultFound);
  }

  @Override
  public void showNextSelection(boolean pForward)
  {
    if (pForward)
    {
      searchResultIndex++;
    }
    else
      searchResultIndex--;
    _SearchResult searchResult = _findNthOccurrence(searchString, searchResultIndex, null, getRootNode());
    goToFoundPath(searchResult.getValidPath(), expandOnResultFound);
  }

  /**
   * Returns the string representation of a single node
   *
   * @param pNode Node
   * @return current string representation to search in or null if this node should be skipped
   */
  @Nullable
  protected abstract String toString(@NotNull TreeNode pNode);

  /**
   * @return the root node of the tree to search
   */
  @NotNull
  protected TreeNode getRootNode()
  {
    assert tree != null;
    return (TreeNode) tree.getModel().getRoot();
  }

  /**
   * marks the foundRow-th row and moves the visible rectangle to that location
   *
   * @param pFoundPath the path of the line that should be selected/gone to
   * @param pExpand    true, if not expanded paths should be expanded
   */
  protected void goToFoundPath(@Nullable TreePath pFoundPath,boolean pExpand)
  {
    assert tree != null;

    if (pFoundPath != null)
    {
      if(pExpand)
        tree.expandPath(pFoundPath);

      tree.scrollPathToVisible(pFoundPath);
      tree.getSelectionModel().setSelectionPath(pFoundPath);
    }
    else
    {
      tree.getSelectionModel().clearSelection();
    }
  }

  /**
   * Checks, if the given path is visible
   *
   * @param pPath path to check
   * @return true if visible
   */
  protected boolean isVisible(@NotNull TreePath pPath)
  {
    assert tree != null;
    return tree.isVisible(pPath);
  }

  /**
   * finds the n-th node that contains the searchString in the tree, only considers leafs for search hits
   *
   * @param pSearchString the String to look for
   * @param pN            the n in "find n-th occurrence"
   * @param pPath         TreePath that describes the path from root down to pNode, inclusive the root node and exclusive the pNode
   * @param pNode         current Node
   */
  private _SearchResult _findNthOccurrence(String pSearchString, int pN, TreePath pPath, TreeNode pNode)
  {
    // keeps track of how many occurrences have been found so far
    int foundOccurrences = 0;
    {
      for (TreeNode childNode : Collections.list(pNode.children()))
      {
        TreePath childPath = pPath == null ? new TreePath(pNode) : pPath.pathByAddingChild(pNode);
        if (expandOnResultFound || isVisible(childPath.pathByAddingChild(childNode)))
        {
          String stringRep = toString(childNode);
          if (Utility.containsIgnoreCase(stringRep, pSearchString))
          {
            foundOccurrences++;
            lastFoundBuffer = childPath.pathByAddingChild(childNode);
            if (pN == 0)
              return new _SearchResult(foundOccurrences, childPath.pathByAddingChild(childNode));
            else
              pN--;
          }
          if (!childNode.isLeaf())
          {
            _SearchResult searchResult = _findNthOccurrence(pSearchString, pN, childPath, childNode);
            foundOccurrences += searchResult.getFoundOccurrences();
            if ((pN == 0 || pN - foundOccurrences == -1) && searchResult.getValidPath() != null)
              return new _SearchResult(foundOccurrences, searchResult.getValidPath());
            pN -= searchResult.getFoundOccurrences();
          }
        }
      }
    }
    if (pPath == null)
    {
      if (pN >= 0 && foundOccurrences > 0)
      {
        searchResultIndex = 0;
        return _findNthOccurrence(pSearchString, searchResultIndex, null, pNode);
      }
      if (pN < 0)
      {
        searchResultIndex = foundOccurrences - 1;
        return new _SearchResult(foundOccurrences, lastFoundBuffer);
      }
      lastFoundBuffer = null;
    }
    return new _SearchResult(foundOccurrences, null);
  }

  /**
   * Simple class to hold a TreePath and an integer indicating the number of matches found
   */
  private static class _SearchResult
  {
    private final int foundOccurrences;
    private final TreePath validPath;

    _SearchResult(int pFoundOccurrences, TreePath pValidPath)
    {
      foundOccurrences = pFoundOccurrences;
      validPath = pValidPath;
    }

    TreePath getValidPath()
    {
      return validPath;
    }

    int getFoundOccurrences()
    {
      return foundOccurrences;
    }
  }

}
