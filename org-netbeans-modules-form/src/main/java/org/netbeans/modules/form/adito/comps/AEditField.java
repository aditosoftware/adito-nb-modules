package org.netbeans.modules.form.adito.comps;

import javax.swing.*;
import java.awt.*;

/**
 * @author J. Boesl, 14.02.11
 */
public class AEditField extends JTextField
{

  private boolean vis = true;


  public Color getFontColor()
  {
    return getForeground();
  }

  public void setFontColor(Color pFontColor)
  {
    setForeground(pFontColor);
  }

  public Color getBgColor()
  {
    return getBackground();
  }

  public void setBgColor(Color pBgColor)
  {
    setBackground(pBgColor);
  }

  public boolean isInvisible()
  {
    return vis;
  }

  public void setInvisible(boolean pInvisible)
  {
    vis = pInvisible;
    repaint();
  }

  @Override
  public void paint(Graphics g)
  {
    super.paint(g);
    if (!vis)
    {
      ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                        RenderingHints.VALUE_ANTIALIAS_ON);
      g.setColor(Color.RED);
      g.drawLine(1, 1, getWidth() - 1, getHeight() - 1);
      g.drawLine(1, getHeight() - 1, getWidth() - 1, 1);
    }
  }
}
