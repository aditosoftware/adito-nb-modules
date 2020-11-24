package de.adito.swing;

import java.text.MessageFormat;

/**
 * Utility für das MultilineLabel.
 *
 * @author J. Boesl, 10.05.13
 */
public class MultilineLabelUtil
{

  private MultilineLabelUtil()
  {
  }

  /**
   * Liefert aus einem normalen String (ohne html) einen html-text bei dem der Text zentriert ausgerichtet ist.
   *
   * @param pString der String.
   * @return der String als html. \n wird durch &lt;br\> ersetzt. Text ist zentriert.
   */
  public static String getCenteredHtmlText(String pString)
  {
    return MessageFormat.format(
        "<html><STYLE type=\"text/css\">BODY '{text-align: center}'</STYLE><BODY>{0}</html>",
        pString.replace("\n", "<br/>"));
  }

}
