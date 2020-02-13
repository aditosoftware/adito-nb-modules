package de.adito.swing.popup;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author m.kaspera, 13.02.2020
 */
public class PopupMouseAdapter extends MouseAdapter
{

  private static final int MAX_HEIGHT = 600;
  private static final int WIDTH_BUFFER = 30;
  private static final int ADD_HEIGHT = 40;
  private final PopupWindow popupWindow;
  private final JComponent originComponent;
  private final JComponent popupContent;

  public PopupMouseAdapter(PopupWindow pPopupWindow, JComponent pOriginComponent, JComponent pPopupContent)
  {
    popupWindow = pPopupWindow;
    originComponent = pOriginComponent;
    popupContent = pPopupContent;
  }

  @Override
  public void mouseClicked(MouseEvent pE)
  {
    // set the size of the popup, maximum initial height of 600 px
    popupWindow.setPreferredSize(new Dimension(popupContent.getPreferredSize().width + WIDTH_BUFFER, Math.min(popupContent.getPreferredSize().height + ADD_HEIGHT, MAX_HEIGHT)));
    Point labelStart = originComponent.getLocationOnScreen();
    int x = Math.min(labelStart.x, labelStart.x + originComponent.getWidth() - (int) popupWindow.getPreferredSize().getWidth());
    int y = labelStart.y + originComponent.getHeight() - (int) popupWindow.getPreferredSize().getHeight();
    popupWindow.setVisible(true);
    popupWindow.setLocation(x, y);
  }
}
