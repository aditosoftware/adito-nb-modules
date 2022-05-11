package org.netbeans.modules.lsp.client.bindings.refactoring;

import org.openide.awt.*;
import org.openide.modules.Modules;
import org.openide.util.*;

import java.lang.reflect.*;
import java.util.List;
import java.util.logging.*;

/**
 * Find-Usages for JS and TS files
 *
 * @author s.seemann, 09.05.2022
 */
@ActionID(id = "org.netbeans.modules.lsp.client.bindings.refactoring.WhereUsedAction", category = "Refactoring")
@ActionRegistration(displayName = "#LBL_WhereUsedAction")
@ActionReferences({
    @ActionReference(path = "Editors/text/javascript/Popup", name = "WhereUsedAction", separatorBefore = 5990, position = 6000, separatorAfter = 6010),
    @ActionReference(path = "Editors/text/typescript/Popup", name = "WhereUsedAction", separatorBefore = 5990, position = 6000, separatorAfter = 6010)
})
public class WhereUsedAction extends RefactoringGlobalAction
{
  private static final Logger LOGGER = Logger.getLogger(WhereUsedAction.class.getName());
  private static final Method doFindUsages;
  private static final Method canFindUsages;

  static
  {
    try
    {
      // unfortunately only with reflection, because package is not public
      String className = String.join(".", List.of("org", "netbeans", "modules", "refactoring", "api", "impl",
                                                  "ActionsImplementationFactory"));
      Class<?> actionFactory = Modules.getDefault().findCodeNameBase("org.netbeans.modules.refactoring.api").getClassLoader().loadClass(className);
      doFindUsages = actionFactory.getDeclaredMethod("doFindUsages", Lookup.class);
      canFindUsages = actionFactory.getDeclaredMethod("canFindUsages", Lookup.class);
    }
    catch (Throwable t)
    {
      LOGGER.log(Level.SEVERE, t, t::getMessage);
      throw new RuntimeException(t);
    }
  }

  public WhereUsedAction()
  {
    super(NbBundle.getMessage(WhereUsedAction.class, "LBL_WhereUsedAction"), null);
    putValue("noIconInMenu", Boolean.TRUE);
  }

  @Override
  public void performAction(Lookup context)
  {
    try
    {
      doFindUsages.invoke(null, context);
    }
    catch (IllegalAccessException pE)
    {
      LOGGER.log(Level.SEVERE, pE, pE::getMessage);
      throw new RuntimeException(pE);
    }
    catch (InvocationTargetException pE)
    {
      LOGGER.log(Level.SEVERE, pE.getCause(), () -> pE.getCause().getMessage());
      throw new RuntimeException(pE.getCause());
    }
  }

  @Override
  protected boolean asynchronous()
  {
    return true;
  }

  @Override
  protected boolean enable(Lookup context)
  {
    try
    {
      return (boolean) canFindUsages.invoke(null, context);
    }
    catch (IllegalAccessException pE)
    {
      LOGGER.log(Level.SEVERE, pE, pE::getMessage);
      throw new RuntimeException(pE);
    }
    catch (InvocationTargetException pE)
    {
      LOGGER.log(Level.SEVERE, pE.getCause(), () -> pE.getCause().getMessage());
      throw new RuntimeException(pE.getCause());
    }
  }

  @Override
  protected boolean applicable(Lookup context)
  {
    return enable(context);
  }
}





