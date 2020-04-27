package de.adito.aditoweb.nbm.nbide.nbaditointerface.actionitems;

import org.jetbrains.annotations.NotNull;

/**
 * @author w.glanzer, 27.04.2020
 */
public interface IActionItemGroup
{

  /**
   * @return human readable name
   */
  @NotNull
  String getName();

  /**
   * @return the severity of this group to describe, how critical a single group of actionitems is
   */
  @NotNull
  ESeverity getSeverity();

  enum ESeverity
  {
    ERROR,
    WARNING,
    NORMAL
  }

}
