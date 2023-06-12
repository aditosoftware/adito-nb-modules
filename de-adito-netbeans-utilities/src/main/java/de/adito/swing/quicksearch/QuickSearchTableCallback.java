package de.adito.swing.quicksearch;

import lombok.NonNull;
import org.jetbrains.annotations.*;
import org.openide.awt.QuickSearch;

import javax.swing.*;
import java.util.*;
import java.util.stream.*;

/**
 * Implementation of the callback for a search, can cycle through results
 * If no matching result was found, the selection is cleared
 *
 * @author w.glanzer, 29.04.2020
 * @author m.kaspera, 06.02.2019
 */
public abstract class QuickSearchTableCallback implements QuickSearch.Callback
{

  private final JTable table;
  private final List<Integer> columnsToSearch = new ArrayList<>();
  private String searchString = null;
  private int searchResultIndex = 0;

  public QuickSearchTableCallback(@NonNull JTable pTable)
  {
    this(pTable, IntStream.range(0, pTable.getColumnCount())
        .boxed()
        .collect(Collectors.toList()));
  }

  public QuickSearchTableCallback(@NonNull JTable pTable, @NonNull List<Integer> pColumnsToSearch)
  {
    table = pTable;

    for (Integer columnToSearch : pColumnsToSearch)
    {
      if (table.getModel().getColumnCount() > columnToSearch)
        columnsToSearch.add(columnToSearch);
    }
  }

  @Override
  public String findMaxPrefix(String pPrefix)
  {
    return null;
  }

  @Override
  public void quickSearchCanceled()
  {
    searchString = null;
    searchResultIndex = 0;
  }

  @Override
  public void quickSearchConfirmed()
  {
    _goToFoundLine(_findNthOccurrence(searchString, searchResultIndex));
  }

  @Override
  public void quickSearchUpdate(String pSearchText)
  {
    searchString = pSearchText;
    searchResultIndex = 0;
    if (pSearchText != null)
      _goToFoundLine(_findNthOccurrence(searchString, searchResultIndex));
  }

  @Override
  public void showNextSelection(boolean pForward)
  {
    if (pForward)
      searchResultIndex++;
    else
      searchResultIndex--;
    _goToFoundLine(_findNthOccurrence(searchString, searchResultIndex));
  }

  /**
   * Returns the string representation of a single node
   *
   * @param pCellValue Value of the single cell
   * @return current string representation to search in or null if this element should be skipped
   */
  @Nullable
  protected abstract String toString(@NonNull Object pCellValue);

  /**
   * @param pSearchString the String to look for
   * @param pN            which occurrence should be found
   * @return the row of the n-th occurrence, or -1 if no occurrence was found
   */
  private int _findNthOccurrence(String pSearchString, int pN)
  {
    int lastOccurrence = -1;
    int foundOccurrences = 0;

    for (int rowIndex = 0; rowIndex < table.getModel().getRowCount(); rowIndex++)
    {
      for (Integer colToSearch : columnsToSearch)
      {
        if (_isOccurrence(pSearchString, rowIndex, colToSearch))
        {
          foundOccurrences++;
          lastOccurrence = rowIndex;
          // if n != -1 and n-th result exists (n < foundOccurrences because count found occurrences 1 for one hit, n for first result = 0)
          if (pN >= 0 && foundOccurrences > pN)
          {
            return rowIndex;
          }
          // only one result per row, else the user can press next twice and still be in the same row
          break;
        }
      }
    }
    // if n == -1 -> pressed back on first occurrence -> set index to last occurrence
    if (pN < 0)
      searchResultIndex = foundOccurrences - 1;
    // if result is bigger than biggest occurrence -> Index to 0 (first result) and look up row of first occurrence
    if (pN >= foundOccurrences && foundOccurrences != 0)
    {
      searchResultIndex = 0;
      return _findNthOccurrence(pSearchString, searchResultIndex);
    }
    // found nothing -> -1
    if (foundOccurrences == 0)
    {
      return -1;
    }
    // if n < 0 return the row of the last occurrence
    return lastOccurrence;
  }

  /**
   * @param pSearchString string that should be matched to the content of the cell
   * @param pRowIndex     row of the cell to check
   * @param pColIndex     column of the cell to check
   * @return true if the toString method of the object in the cell contains the searchString
   */
  private boolean _isOccurrence(String pSearchString, int pRowIndex, int pColIndex)
  {
    Object foundObj = table.getModel().getValueAt(pRowIndex, pColIndex);
    if (foundObj == null)
      return false;
    String stringRep = toString(foundObj);
    if (stringRep == null)
      return false;
    return Utility.containsIgnoreCase(stringRep, pSearchString);
  }

  /**
   * marks the foundRow-th row and moves the visible rectangle to that location
   *
   * @param pFoundRow the number of the line that should be selected/gone to
   */
  private void _goToFoundLine(int pFoundRow)
  {
    if (pFoundRow >= 0)
    {
      table.scrollRectToVisible(table.getCellRect(pFoundRow, 1, true));
      table.setRowSelectionInterval(pFoundRow, pFoundRow);
    }
    else
    {
      table.getSelectionModel().clearSelection();
    }
  }
}
