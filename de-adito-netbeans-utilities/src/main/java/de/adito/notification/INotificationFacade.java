package de.adito.notification;

import de.adito.aditoweb.nbm.metrics.api.IMetricProxyFactory;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.awt.event.ActionListener;

/**
 * Facade to display Notifications as a balloon
 *
 * @author s.seemann, 19.03.2021
 */
public interface INotificationFacade
{
  INotificationFacade INSTANCE = IMetricProxyFactory.proxy(new NotificationFacadeImpl());

  /**
   * Shows a simple balloon to display a pMessage to the user.
   *
   * @param pTitle       Title
   * @param pMessage     Message
   * @param pAutoDispose {@code true} if the balloon should dispose automatically after a couple of seconds
   * @param pListener    Action to invoke when user click details text or {@code null}
   */
  void notify(@Nullable String pTitle, @Nullable String pMessage, boolean pAutoDispose, @Nullable ActionListener pListener);

  /**
   * Shows a simple balloon to display a pMessage to the user. This balloon will have no listener.
   *
   * @param pTitle       Title
   * @param pMessage     Message
   * @param pAutoDispose {@code true} if the balloon should dispose automatically after a couple of seconds
   */
  void notify(@Nullable String pTitle, @Nullable String pMessage, boolean pAutoDispose);

  /**
   * Displays an error as a balloon
   *
   * @param pThrowable Exception
   */
  void error(@NonNull Throwable pThrowable);

  /**
   * Displays an error as a balloon
   *
   * @param pThrowable the Exception
   * @param pTitle     the title of the box
   */
  void error(@NonNull Throwable pThrowable, @NonNull String pTitle);

  /**
   * Displays an error as a balloon
   *
   * @param pThrowable             the Exception
   * @param pTitle                 the title of the box
   * @param pAdditionalInformation additional information that should be displayed
   */
  void error(@NonNull Throwable pThrowable, @NonNull String pTitle, @NonNull String pAdditionalInformation);

  /**
   * Shows SQL in a Dialog
   *
   * @param pSqlText the SQL-Text, which should be displayed
   */
  void showSql(@NonNull String pSqlText);
}
