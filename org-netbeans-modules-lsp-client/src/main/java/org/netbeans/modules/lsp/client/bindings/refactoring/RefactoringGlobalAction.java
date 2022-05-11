package org.netbeans.modules.lsp.client.bindings.refactoring;

import org.openide.awt.Actions;
import org.openide.cookies.EditorCookie;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;
import org.openide.text.CloneableEditorSupport;
import org.openide.util.*;
import org.openide.util.actions.*;
import org.openide.util.lookup.*;
import org.openide.windows.TopComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;

/**
 * Copy of org.netbeans.modules.refactoring.spi.impl.RefactoringGlobalAction, because it is not public
 *
 * @author s.seemann, 09.05.2022
 */
public abstract class RefactoringGlobalAction extends NodeAction
{

  /**
   * Creates a new instance of RefactoringGlobalAction
   */
  public RefactoringGlobalAction(String name, Icon icon)
  {
    setName(name);
    setIcon(icon);
  }

  @Override
  public final String getName()
  {
    return (String) getValue(Action.NAME);
  }

  protected void setName(String name)
  {
    putValue(Action.NAME, name);
  }

  protected void setMnemonic(char m)
  {
    putValue(Action.MNEMONIC_KEY, new Integer(m));
  }

  private static String trim(String arg)
  {
    arg = arg.replace("&", ""); // NOI18N
    return arg.replace("...", ""); // NOI18N
  }

  @Override
  public org.openide.util.HelpCtx getHelpCtx()
  {
    return HelpCtx.DEFAULT_HELP;
  }

  protected Lookup getLookup(Node[] n)
  {
    InstanceContent ic = new InstanceContent();
    for (Node node : n)
      ic.add(node);
    if (n.length > 0)
    {
      EditorCookie tc = getTextComponent(n[0]);
      if (tc != null)
      {
        ic.add(tc);
      }
    }
    return new AbstractLookup(ic);
  }


  protected static EditorCookie getTextComponent(Node n)
  {
    DataObject dobj = n.getCookie(DataObject.class);
    if (dobj != null)
    {
      EditorCookie ec = dobj.getCookie(EditorCookie.class);
      if (ec != null)
      {
        TopComponent activetc = TopComponent.getRegistry().getActivated();
        if (activetc instanceof CloneableEditorSupport.Pane)
        {
          return ec;
        }
      }
    }
    return null;
  }

  public abstract void performAction(Lookup context);

  protected abstract boolean enable(Lookup context);

  protected abstract boolean applicable(Lookup context);

  @Override
  public final void performAction(final Node[] activatedNodes)
  {
    performAction(getLookup(activatedNodes));
  }

  @Override
  protected boolean enable(Node[] activatedNodes)
  {
    return enable(getLookup(activatedNodes));
  }


  @Override
  public Action createContextAwareInstance(Lookup actionContext)
  {
    return new ContextAction(actionContext);
  }

  public class ContextAction implements Action, Presenter.Menu, Presenter.Popup, Presenter.Toolbar
  {

    Lookup context;

    public ContextAction(Lookup context)
    {
      this.context = context;
    }

    @Override
    public Object getValue(String arg0)
    {
      if ("applicable".equals(arg0))
      { //NOI18N
        return RefactoringGlobalAction.this.applicable(context);
      }
      return RefactoringGlobalAction.this.getValue(arg0);
    }

    @Override
    public void putValue(String arg0, Object arg1)
    {
      RefactoringGlobalAction.this.putValue(arg0, arg1);
    }

    @Override
    public void setEnabled(boolean arg0)
    {
      RefactoringGlobalAction.this.setEnabled(arg0);
    }

    @Override
    public boolean isEnabled()
    {
      return enable(context);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener arg0)
    {
      RefactoringGlobalAction.this.addPropertyChangeListener(arg0);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener arg0)
    {
      RefactoringGlobalAction.this.removePropertyChangeListener(arg0);
    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
      RefactoringGlobalAction.this.performAction(context);
    }

    @Override
    public JMenuItem getMenuPresenter()
    {
      if (isMethodOverridden(RefactoringGlobalAction.this, "getMenuPresenter"))
      { // NOI18N

        return RefactoringGlobalAction.this.getMenuPresenter();
      }
      else
      {
        return new Actions.MenuItem(this, true);
      }
    }

    @Override
    public JMenuItem getPopupPresenter()
    {
      if (isMethodOverridden(RefactoringGlobalAction.this, "getPopupPresenter"))
      { // NOI18N

        return RefactoringGlobalAction.this.getPopupPresenter();
      }
      else
      {
        return new Actions.MenuItem(this, false);
      }
    }

    @Override
    public Component getToolbarPresenter()
    {
      if (isMethodOverridden(RefactoringGlobalAction.this, "getToolbarPresenter"))
      { // NOI18N

        return RefactoringGlobalAction.this.getToolbarPresenter();
      }
      else
      {
        final JButton button = new JButton();
        Actions.connect(button, this);
        return button;
      }
    }

    private boolean isMethodOverridden(NodeAction d, String name)
    {
      try
      {
        Method m = d.getClass().getMethod(name, new Class[0]);

        return m.getDeclaringClass() != CallableSystemAction.class;
      }
      catch (java.lang.NoSuchMethodException ex)
      {
        ex.printStackTrace();
        throw new IllegalStateException("Error searching for method " + name + " in " + d); // NOI18N
      }
    }
  }
}
