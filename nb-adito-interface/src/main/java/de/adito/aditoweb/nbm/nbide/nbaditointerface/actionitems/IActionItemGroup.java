package de.adito.aditoweb.nbm.nbide.nbaditointerface.actionitems;

import lombok.NonNull;

/**
 * @author w.glanzer, 27.04.2020
 */
public interface IActionItemGroup
{

  /**
   * @return human readable name
   */
  @NonNull
  String getName();

  /**
   * @return the severity of this group to describe, how critical a single group of actionitems is
   */
  @NonNull
  ESeverity getSeverity();

  enum ESeverity
  {
    ERROR,
    WARNING,
    NORMAL
  }

}
