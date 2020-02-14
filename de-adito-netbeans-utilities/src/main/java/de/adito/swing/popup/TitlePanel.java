package de.adito.swing.popup;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Dimension;

import static de.adito.swing.popup.PopupPanel.DRAG_BORDER_WIDTH;
import static javax.swing.SwingConstants.CENTER;

/**
 * A JLabel with the title of the popup
 *
 * @author a.arnold, 15.11.2018
 */
class TitlePanel extends JPanel
{
  TitlePanel(@NotNull MouseDragHandler pMouseDragHandler, @NotNull String pLabelName)
  {
    setLayout(new BorderLayout());
    if (!pLabelName.isEmpty())
    {
      JLabel titleLabel = new JLabel(pLabelName);
      titleLabel.setHorizontalAlignment(CENTER);
      titleLabel.setBorder(new EmptyBorder(0, 0, DRAG_BORDER_WIDTH, 0));
      add(titleLabel, BorderLayout.CENTER);
      setBackground(UIManager.getColor("adito.secondary.background.color"));
    }
    else
    {
      setPreferredSize(new Dimension(0, 0));
    }
    addMouseListener(pMouseDragHandler);
    addMouseMotionListener(pMouseDragHandler);
  }
}
