package de.adito.notification;

import com.google.common.base.Strings;
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
  public void showSql(@NotNull String pSqlText)
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
  public void error(@NotNull Throwable pThrowable)
  {
    _notify(pThrowable.getClass().getSimpleName(), _getRootMessage(pThrowable), false, NotificationDisplayer.Priority.HIGH, null);
    Exceptions.printStackTrace(pThrowable);
  }

  private void _notify(String pTitle, String pMessage, boolean pAutoDispose, @NotNull NotificationDisplayer.Priority pPriority,
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

  private String _getRootMessage(Throwable pThrowable)
  {
    Throwable cause = pThrowable.getCause();
    if (cause != null)
      return _getRootMessage(cause);
    String msg = pThrowable.getLocalizedMessage();
    if (msg == null)
      msg = pThrowable.getMessage();
    if (msg == null)
      msg = "exception";
    return msg;
  }

}
