package de.adito.nbm.project;

import org.junit.jupiter.api.Test;

import java.lang.reflect.*;
import java.util.List;

import static de.adito.nbm.project.ProjectTabUtil._PATH_PROJECT_TAB;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for {@link ProjectTabUtil}
 */
class ProjectTabUtilTest
{

  /**
   * Tests, if the methods called per reflection a available
   */
  @Test
  void test_getExpandedNodeReflection()
  {
    try
    {
      Class<?> aClass = Class.forName(_PATH_PROJECT_TAB);
      Field btv = aClass.getDeclaredField("btv");
      btv.setAccessible(true);
      Method getExpandedPaths = btv.getType().getDeclaredMethod("getExpandedPaths");
      getExpandedPaths.setAccessible(true);
    }
    catch (NoSuchFieldException | NoSuchMethodException | ClassNotFoundException pE)
    {
      fail(pE);
    }
  }

  /**
   * Tests, if the methods called per reflection a available
   */
  @Test
  void test_setExpandedNodeReflection()
  {
    try
    {
      Class<?> aClass = Class.forName(_PATH_PROJECT_TAB);
      Field btv = aClass.getDeclaredField("btv");
      btv.setAccessible(true);
      Method getExpandedPaths = btv.getType().getDeclaredMethod("expandNodes", List.class);
      getExpandedPaths.setAccessible(true);
    }
    catch (NoSuchFieldException | NoSuchMethodException | ClassNotFoundException pE)
    {
      fail(pE);
    }
  }
}