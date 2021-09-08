package de.adito.nbm.translation.api;

/**
 * Determines, how the line ending handling ("\r\n", "\n") should be done
 *
 * @author W.Glanzer, 06.04.2017
 */
public enum ELineBreakMethod
{

  /**
   * Converts the linebreak to spaces before sending.
   * A backwards translation is not possible.
   */
  LINEBREAK_TO_SPACE("Linebreak to Space"),

  /**
   * Splits the given text on line breaks and sends it as separate rest requests.
   * Afterwards the response can be combined.
   */
  LINEBREAK_TO_SINGLE_REQUEST("Linebreak as Single Request"),

  /**
   * No special line break treatment
   */
  IGNORE("No Special Treatment");

  private final String displayName;

  ELineBreakMethod(String pDisplayName)
  {
    displayName = pDisplayName;
  }

  public String getDisplayName()
  {
    return displayName;
  }
}
