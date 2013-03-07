package org.netbeans.modules.form.adito.actions;

import javax.swing.*;

/**
 * Object welches eine Action mit Position kapselt
 * Objecte können über die AditoSortedActionList anhand der Position sortiert werden
 *
 * @author T. Feldmann, 07.03.13
 */
public class AditoActionObject
{
  private Action action;
  private int positon = 99999; // Magic-Number, wenn keine Positon angegeben, dann ganz ans Ende

  public AditoActionObject(Action pAction)
  {
    action = pAction;
  }

  public AditoActionObject(Action pAction, int pPositon)
  {
    action = pAction;
    positon = pPositon;
  }

  public AditoActionObject(Action pAction, int pPositon, String pName)
  {
    action = pAction;
    positon = pPositon;
    action.putValue(Action.NAME, pName);
  }

  public Action getAction()
  {
    return action;
  }

  public void setAction(Action pAction)
  {
    action = pAction;
  }

  public int getPositon()
  {
    return positon;
  }

  public void setPositon(int pPositon)
  {
    positon = pPositon;
  }

  public String getName()
  {
    return action.getValue(Action.NAME).toString();
  }

  public void setName(String pName)
  {
    action.putValue(Action.NAME, pName);
  }
}
