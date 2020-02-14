package de.adito.swing.popup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * a implementation of the HeavyWightWindow to show all branches
 *
 * @author a.arnold, 12.11.2018
 */

public class PopupWindow extends JWindow
{
  private _WindowDisposer windowDisposer;

  public PopupWindow(Window parent, String pTitle, JComponent pComponent)
  {
    super(parent);
    windowDisposer = new _WindowDisposer();
    add(new PopupPanel(pComponent, pTitle, this));
    setType(Type.POPUP);
    try
    {
      setAlwaysOnTop(true);
    }
    catch (SecurityException se)
    {
      throw new RuntimeException();
    }
  }

  @Override
  public void setVisible(boolean b)
  {
    pack();
    super.setVisible(b);
    SwingUtilities.invokeLater(() -> Toolkit.getDefaultToolkit().addAWTEventListener(windowDisposer, AWTEvent.WINDOW_EVENT_MASK | AWTEvent.KEY_EVENT_MASK));
  }

  public void disposeWindow() {
    Toolkit.getDefaultToolkit().removeAWTEventListener(windowDisposer);
    dispose();
  }

  /**
   * AWTEventListener that disposes of the window if any mouse/keyboard/windowAction happens outside this component or any of its children
   */
  private class _WindowDisposer implements AWTEventListener
  {

    @Override
    public void eventDispatched(AWTEvent pEvent)
    {
      if (!_isSourceRecursive(pEvent, PopupWindow.this))
      {
        if (pEvent instanceof MouseEvent && (pEvent.getID() == MouseEvent.MOUSE_CLICKED || pEvent.getID() == MouseEvent.MOUSE_WHEEL))
        {
          disposeWindow();
        }
        if (pEvent instanceof WindowEvent && pEvent.getID() != WindowEvent.WINDOW_OPENED
            && !(pEvent.getID() == WindowEvent.WINDOW_CLOSED && pEvent.getSource() instanceof PopupWindow))
        {
          disposeWindow();
        }
        if (pEvent instanceof KeyEvent)
        {
          disposeWindow();
        }
      }
      else if (pEvent instanceof KeyEvent && ((KeyEvent) pEvent).getKeyCode() == KeyEvent.VK_ESCAPE)
      {
        disposeWindow();
      }
    }

    /**
     * removes the AWTEventListener and disposes of the ChunkPopupWindow
     */
    void disposeWindow()
    {
      Toolkit.getDefaultToolkit().removeAWTEventListener(this);
      dispose();
    }

    /**
     * Checks if the container or any of its components (or the components components or...)
     * is the source of the event.
     * This is a recursive function
     *
     * @param pAWTEvent  event to look for
     * @param pContainer Container to check
     * @return true if event originated in the Container or any of its children, false otherwise
     */
    private boolean _isSourceRecursive(AWTEvent pAWTEvent, Container pContainer)
    {
      for (Component component : pContainer.getComponents())
      {
        if (pAWTEvent.getSource() == component || (component instanceof Container && _isSourceRecursive(pAWTEvent, (Container) component)))
          return true;
      }
      return false;
    }
  }
}

