package org.netbeans.modules.form.adito;

import javax.swing.*;

/**
 * @author J. Boesl, 27.06.11
 */
public final class AditoFormUtils
{

  private AditoFormUtils()
  {
  }

  public static void invokeLater(final Runnable pRunnable, final int pCount)
  {
    if (pCount < 1)
    {
      pRunnable.run();
    }
    else
    {
      SwingUtilities.invokeLater(new Runnable()
      {
        public void run()
        {
          invokeLater(pRunnable, pCount - 1);
        }
      });
    }
  }

}
