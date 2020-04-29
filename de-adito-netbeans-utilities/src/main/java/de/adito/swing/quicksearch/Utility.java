package de.adito.swing.quicksearch;

import org.jetbrains.annotations.Nullable;

/**
 * @author w.glanzer, 29.04.2020
 */
class Utility
{
  /**
   * <p>Checks if String contains a search String irrespective of case,
   * handling <code>null</code>. Case-insensitivity is defined as by
   * {@link String#equalsIgnoreCase(String)}.
   *
   * <p>A <code>null</code> String will return <code>false</code>.</p>
   *
   * <pre>
   * StringUtils.contains(null, *) = false
   * StringUtils.contains(*, null) = false
   * StringUtils.contains("", "") = true
   * StringUtils.contains("abc", "") = true
   * StringUtils.contains("abc", "a") = true
   * StringUtils.contains("abc", "z") = false
   * StringUtils.contains("abc", "A") = true
   * StringUtils.contains("abc", "Z") = false
   * </pre>
   *
   * @param str  the String to check, may be null
   * @param searchStr  the String to find, may be null
   * @return true if the String contains the search String irrespective of
   * case or false if not or <code>null</code> string input
   */
  public static boolean containsIgnoreCase(@Nullable String str, @Nullable String searchStr) {
    if (str == null || searchStr == null) {
      return false;
    }
    int len = searchStr.length();
    int max = str.length() - len;
    for (int i = 0; i <= max; i++) {
      if (str.regionMatches(true, i, searchStr, 0, len)) {
        return true;
      }
    }
    return false;
  }
}
