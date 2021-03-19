package de.adito.notification;

import org.jetbrains.annotations.*;

import java.awt.event.ActionListener;

/**
 * Facade to display Notifications as a balloon
 *
 * @author s.seemann, 19.03.2021
 */
public interface INotificationFacade
{
  INotificationFacade INSTANCE = new NotificationFacadeImpl();

  /**
   * Shows a simple balloon to display a pMessage to the user.
   *
   * @param pTitle       Title
   * @param pMessage     Message
   * @param pAutoDispose <tt>true</tt> if the balloon should dispose automatically after a couple of seconds
   */
  void notify(@Nullable String pTitle, @Nullable String pMessage, boolean pAutoDispose, @Nullable ActionListener pListener);

  /**
   * Displays an error as a balloon
   *
   * @param pThrowable Exception
   */
  void error(@NotNull Throwable pThrowable);

  /**
   * Shows SQL in a Dialog
   *
   * @param pSqlText the SQL-Text, which should be displayed
   */
  void showSql(@NotNull String pSqlText);
}
