package de.adito.notification;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Strings;
import de.adito.aditoweb.nbm.metrics.api.types.Sampled;
import lombok.NonNull;
import org.jetbrains.annotations.*;
import org.openide.awt.*;
import org.openide.modules.Modules;
import org.openide.util.Exceptions;
import org.openide.windows.WindowManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.*;

/**
 * @author s.seemann, 19.03.2021
 */
class NotificationFacadeImpl implements INotificationFacade
{
  private static final int _HEIGHT_SQL_DIALOG = 750;
  private static final int _WIDTH_SQL_DIALOG = 1500;

  @Override
  public void showSql(@NonNull String pSqlText)
  {
    try
    {
      Class<?> aClass = Modules.getDefault()
          .findCodeNameBase("org.netbeans.modules.db.dataview")
          .getClassLoader()
          .loadClass("org.netbeans.modules.db.dataview.output.ShowSQLDialog");
      Constructor<?> constructor = aClass.getConstructor();
      constructor.setAccessible(true);
      JDialog dialog = (JDialog) constructor.newInstance();

      // set the SQL as Text
      Method setText = aClass.getDeclaredMethod("setText", String.class);
      setText.setAccessible(true);
      setText.invoke(dialog, pSqlText);

      SwingUtilities.invokeLater(() -> {
        dialog.setSize(new Dimension(_WIDTH_SQL_DIALOG, _HEIGHT_SQL_DIALOG));
        dialog.setLocationRelativeTo(WindowManager.getDefault().getMainWindow());
        dialog.setVisible(true);
      });
    }
    catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException pE)
    {
      error(pE);
    }
  }

  @Override
  public void notify(String pTitle, String pMessage, boolean pAutoDispose, @Nullable ActionListener pListener)
  {
    _notify(pTitle, pMessage, pAutoDispose, NotificationDisplayer.Priority.NORMAL, pListener);
  }

  @Override
  public void notify(@Nullable String pTitle, @Nullable String pMessage, boolean pAutoDispose)
  {
    notify(pTitle, pMessage, pAutoDispose, null);
  }

  @Override
  @Sampled(name = "logging.external.error")
  public void error(@NonNull Throwable pThrowable)
  {
    notifyError(pThrowable, pThrowable.getClass().getSimpleName(), getRootMessage(pThrowable));
  }

  @Override
  @Sampled(name = "logging.external.error")
  public void error(@NonNull Throwable pThrowable, @NonNull String pTitle)
  {
    notifyError(pThrowable, pTitle, getExceptionAndMessage(pThrowable));
  }

  @Override
  @Sampled(name = "logging.external.error")
  public void error(@NonNull Throwable pThrowable, @NonNull String pTitle, @NonNull String pAdditionalInformation)
  {
    String exceptionMessage = getExceptionAndMessage(pThrowable);
    notifyError(pThrowable, pTitle, exceptionMessage + "\n " + pAdditionalInformation);
  }

  /**
   * Notify about an error. It also prints the stack trace.
   *
   * @param pThrowable the {@link Throwable} that should be notified about
   * @param pTitle     the title of the message
   * @param pMessage   the message
   */
  @VisibleForTesting
  void notifyError(@NonNull Throwable pThrowable, @NonNull String pTitle, @NonNull String pMessage)
  {
    _notify(pTitle, pMessage, false, NotificationDisplayer.Priority.HIGH, null);
    Exceptions.printStackTrace(pThrowable);
  }


  @VisibleForTesting
  void _notify(@Nullable String pTitle, @Nullable String pMessage, boolean pAutoDispose, @NonNull NotificationDisplayer.Priority pPriority,
               @Nullable ActionListener pActionListener)
  {
    Icon icon = pPriority.getIcon();
    Notification n = NotificationDisplayer.getDefault().notify(Strings.nullToEmpty(pTitle), icon,
                                                               Strings.nullToEmpty(pMessage), pActionListener);
    if (pAutoDispose)
    {
      Timer timer = new Timer(6500, e -> n.clear());
      timer.setRepeats(false);
      timer.start();
    }
  }

  /**
   * Creates a message for notifying the user about the name and root message of an exception.
   *
   * @param pThrowable the throwable of which the message should be created
   * @return the message containing class name of the throwable and root message
   */
  @NonNull
  @VisibleForTesting
  String getExceptionAndMessage(@NonNull Throwable pThrowable)
  {
    return pThrowable.getClass().getSimpleName() + ": " + getRootMessage(pThrowable);
  }

  /**
   * Gets the root message of a throwable.
   * <p>
   * If the root message is {@code null}, the message of the throwable is returned, or {@code exception} if both are {@code null}.
   *
   * @param pThrowable the {@link Throwable} of which the root message should be extracted
   * @return the root message
   */
  @NonNull
  @VisibleForTesting
  String getRootMessage(@NonNull Throwable pThrowable)
  {
    Throwable cause = pThrowable.getCause();
    if (cause != null)
      return getRootMessage(cause);
    String msg = pThrowable.getLocalizedMessage();
    if (msg == null)
      msg = pThrowable.getMessage();
    if (msg == null)
      msg = "exception";
    return msg;
  }

}
