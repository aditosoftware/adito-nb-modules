package de.adito.swing.popup;

import de.adito.swing.quicksearch.IExtendedQuickSearchCallback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openide.awt.QuickSearch;

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
  @Nullable
  private final IExtendedQuickSearchCallback quickSearchCallback;
  private final _WindowDisposer windowDisposer;
  private QuickSearch quickSearch = null;
  private final PopupPanel popupPanel;

  /**
   * @param parent     Window that should be registered as the parent
   * @param pTitle     Title the popup should have, pass an empty string for no header/title
   * @param pComponent Content that is displayed in the popup
   */
  public PopupWindow(@Nullable Window parent, @NotNull String pTitle, @NotNull JComponent pComponent)
  {
    this(parent, pTitle, pComponent, null);
  }

  /**
   * @param parent               Window that should be registered as the parent
   * @param pTitle               Title the popup should have, pass an empty string for no header/title
   * @param pComponent           Content that is displayed in the popup
   * @param pQuickSearchCallback Callback for the Quicksearch that should be attached to the Popup. Null if no popup is wanted
   */
  public PopupWindow(@Nullable Window parent, @NotNull String pTitle, @NotNull JComponent pComponent, @Nullable IExtendedQuickSearchCallback pQuickSearchCallback)
  {
    super(parent);
    quickSearchCallback = pQuickSearchCallback;
    windowDisposer = new _WindowDisposer();
    popupPanel = new PopupPanel(pComponent, pTitle, this);
    add(popupPanel);
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
    if(quickSearchCallback != null)
      quickSearch = QuickSearch.attach(getSearchAttachComponent(), BorderLayout.SOUTH, quickSearchCallback);
    pack();
    super.setVisible(b);
    SwingUtilities.invokeLater(() -> Toolkit.getDefaultToolkit().addAWTEventListener(windowDisposer, AWTEvent.WINDOW_EVENT_MASK | AWTEvent.KEY_EVENT_MASK));
  }

  public void disposeWindow()
  {
    Toolkit.getDefaultToolkit().removeAWTEventListener(windowDisposer);
    if(quickSearch != null) {
      quickSearch.detach();
    }
    dispose();
  }

  public JComponent getSearchAttachComponent() {
    return popupPanel.getSearchAttachComponent();
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
            && !(pEvent.getID() == WindowEvent.WINDOW_CLOSED && ((WindowEvent) pEvent).getWindow().getType() == Type.POPUP))
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
        if(quickSearchCallback != null && quickSearchCallback.isSearchActive()) {
          quickSearchCallback.quickSearchCanceled();
        } else
        {
          disposeWindow();
        }
      }
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

