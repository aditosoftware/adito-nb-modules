package de.adito.swing.quicksearch;

import org.openide.awt.QuickSearch;

/**
 * @author m.kaspera, 17.02.2020
 */
public interface IExtendedQuickSearchCallback extends QuickSearch.Callback
{

  /**
   * Checks if e.g. quickSearchUpdate has been called without quickSearchCancelled or quickSearchConfirmed
   *
   * @return true if a search is currently active, false otherwise
   */
  boolean isSearchActive();

}
