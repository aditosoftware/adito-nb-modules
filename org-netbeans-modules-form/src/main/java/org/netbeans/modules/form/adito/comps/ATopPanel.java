package org.netbeans.modules.form.adito.comps;

import javax.swing.*;

/**
 * @author J. Boesl, 07.03.11
 */
public class ATopPanel extends JPanel
{

  public void setX(int pX)
  {
    setLocation(pX, getY());
  }

  public void setY(int pY)
  {
    setLocation(getX(), pY);
  }

  public void setWidth(int pWidth)
  {
    setSize(pWidth, getHeight());
  }

  public void setHeight(int pHeight)
  {
    setSize(getWidth(), pHeight);
  }

}
