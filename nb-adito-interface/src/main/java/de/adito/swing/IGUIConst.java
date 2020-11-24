package de.adito.swing;

import java.awt.*;

/**
 * Offers basic constants that can be used in GUIs to offer a consistent look of the GUI
 */
public interface IGUIConst
{
  /**
   * Sets the inner gap of object buttons to 0
   */
  Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);

  /**
   * Gap for components in a GUI (8 Pixel).
   */
  int GAP = 8;

  /**
   * Represents an unknown value - for example in a list
   */
  String UNKNOWN = "<?>";

  Cursor WAIT_CURSOR = new Cursor(Cursor.WAIT_CURSOR);

  Font SQL_EDITOR_FONT = new Font("monospaced", Font.PLAIN, 14);

  /**
   * This client property marks components that should be focused. Requires a FocusManager or FocusUtil to also know of this property
   */
  String CLIENT_PROPERTY_PREFERRED_FOCUS = "FocusUtil#clientPropertyPreferredFocus";
}

