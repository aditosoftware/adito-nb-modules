package de.adito.nbm.project;

import de.adito.notification.INotificationFacade;
import lombok.NonNull;
import org.openide.windows.TopComponent;

import java.lang.reflect.*;
import java.util.*;

/**
 * Utils for the ProjectTab
 *
 * @author s.seemann, 11.05.2021
 */
public class ProjectTabUtil
{
  static String _PATH_PROJECT_TAB = "org.netbeans.modules.project.ui.ProjectTab";

  private ProjectTabUtil()
  {
    // no instance, only util
  }

  /**
   * Returns all expanded nodes of the project tree in the project tab.
   *
   * @return the expanded nodes or an empty list, if there is no project tab opened
   */
  @NonNull
  public static List<String[]> getExpandedNodes()
  {
    Optional<TopComponent> tabOpt = _findProjectTab();
    if (!tabOpt.isPresent())
      return List.of();

    try
    {
      TopComponent tab = tabOpt.get();
      Field btv = tab.getClass().getDeclaredField("btv");
      btv.setAccessible(true);
      Object treeView = btv.get(tab);
      Method getExpandedPaths = treeView.getClass().getDeclaredMethod("getExpandedPaths");
      getExpandedPaths.setAccessible(true);
      //noinspection unchecked
      return (List<String[]>) getExpandedPaths.invoke(treeView);
    }
    catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException pE)
    {
      // should not be possible, because of unittest
      INotificationFacade.INSTANCE.error(pE);
      return List.of();
    }
  }

  /**
   * Sets the expanded nodes of the project tree in the project tab. If no project tab is opened, nothing happens.
   */
  public static void setExpandedNodes(@NonNull List<String[]> pNodes)
  {
    Optional<TopComponent> tabOpt = _findProjectTab();
    if (!tabOpt.isPresent())
      return;

    try
    {
      TopComponent tab = tabOpt.get();
      Field btv = tab.getClass().getDeclaredField("btv");
      btv.setAccessible(true);
      Object treeView = btv.get(tab);
      Method expandNode = treeView.getClass().getDeclaredMethod("expandNodes", List.class);
      expandNode.setAccessible(true);
      expandNode.invoke(treeView, pNodes);
    }
    catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException pE)
    {
      // should not be possible, because of unittest
      INotificationFacade.INSTANCE.error(pE);
    }
  }

  /**
   * Returns the project tab, if it is openend.
   *
   * @return the project tab or an empty optional
   */
  @NonNull
  private static Optional<TopComponent> _findProjectTab()
  {
    return TopComponent.getRegistry().getOpened().stream()
        .filter(pTopComponent -> pTopComponent.getClass().getName().equals(_PATH_PROJECT_TAB))
        .findFirst();
  }
}
