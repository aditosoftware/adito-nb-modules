package de.adito.swing.popup;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * MouseListener that sets the selection style to any list elements or labels it covers (if the mouse is hovering over said element)
 *
 * @author m.kaspera, 19.02.2020
 */
public class HoverMouseListener extends MouseAdapter
{
  Color hoverColor;
  Color labelSelectedForeground = new JList().getSelectionForeground();
  Color labelForeground = new JLabel().getForeground();

  public HoverMouseListener()
  {
    hoverColor = UIManager.getColor("List.selectionBackground");
  }

  @Override
  public void mouseExited(MouseEvent pE)
  {
    Object source = pE.getSource();
    if (source instanceof JList)
    {
      JList<?> list = (JList<?>) source;
      list.clearSelection();
    }
    if (source instanceof JLabel)
    {
      JLabel label = (JLabel) source;
      label.setOpaque(false);
      label.setForeground(labelForeground);
      label.repaint();
    }
  }

  @Override
  public void mouseMoved(MouseEvent pE)
  {
    Object source = pE.getSource();
    if (source instanceof JList)
    {
      JList<?> list = (JList<?>) source;
      list.clearSelection();
      int i = list.locationToIndex(pE.getPoint());
      list.setSelectedIndex(i);
    }
    if (source instanceof JLabel)
    {
      JLabel label = (JLabel) source;
      label.setOpaque(true);
      label.setBackground(hoverColor);
      label.setForeground(labelSelectedForeground);
      label.repaint();
    }
  }
}
