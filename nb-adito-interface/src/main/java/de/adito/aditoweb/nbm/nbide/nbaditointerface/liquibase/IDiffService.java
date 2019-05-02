package de.adito.aditoweb.nbm.nbide.nbaditointerface.liquibase;

import org.jetbrains.annotations.Nullable;

/**
 * Shows the difference between the locale database 
 * and the liquibase manipulated remote database.
 * @author t.tasior, 18.04.2019
 */
public interface IDiffService
{
  /**
   * Passes an object for performing the diff action.
   * @param pObject
   */
  void set(Object pObject);

  /**
   * Presents a dialog with diff informations.
   */
  void perform();

  /**
   * Returns a message shown in Netbean's balloon 
   * notification center.
   * @return a String or null.
   */
  @Nullable
  String getMessage();
}
