package de.adito.swing;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * KeyAdapter that forwards all KeyEvents to the given Component (useful for e.g. the QuickSearch mechanism of netbeans)
 *
 * @author m.kaspera, 14.02.2020
 */
public class KeyForwardAdapter extends KeyAdapter
{

  private JComponent receiver;

  public KeyForwardAdapter(JComponent pReceiver)
  {
    receiver = pReceiver;
  }

  @Override
  public void keyTyped(KeyEvent pEvent)
  {
    receiver.dispatchEvent(pEvent);
  }

}
