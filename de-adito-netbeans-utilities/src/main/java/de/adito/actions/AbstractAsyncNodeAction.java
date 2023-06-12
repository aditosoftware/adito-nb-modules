package de.adito.actions;

import lombok.NonNull;
import org.openide.nodes.Node;
import org.openide.util.RequestProcessor;
import org.openide.util.actions.NodeAction;

import javax.swing.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

/**
 * Node that will handle the "enable" method asynchronously
 *
 * @author w.glanzer, 26.10.2018
 */
public abstract class AbstractAsyncNodeAction extends NodeAction
{

  private final RequestProcessor processor = new RequestProcessor("AbstractAsyncNodeAction: " + getClass().getSimpleName(), 1, true, true);
  private final AtomicReference<Future<?>> lastFuture = new AtomicReference<>(null);
  private final AtomicBoolean lastFutureResult = new AtomicBoolean(false);

  public AbstractAsyncNodeAction()
  {
  }

  @Override
  protected final boolean enable(Node[] activatedNodes)
  {
    if (activatedNodes == null)
      return false;

    // Letzte anfrage canceln, wenn nötig
    Future<?> lastFutureRes = lastFuture.getAndSet(null);
    if (lastFutureRes != null && !lastFutureRes.isDone())
      lastFutureRes.cancel(true);

    Callable<Boolean> r = () -> {
      boolean isEnabled = enable0(activatedNodes);
      if (!Thread.currentThread().isInterrupted())
        SwingUtilities.invokeLater(() -> {
          setEnabled(isEnabled);
          lastFutureResult.set(isEnabled);
        });
      return isEnabled;
    };

    synchronized (lastFuture)
    {
      if (asynchronous())
        lastFuture.set(processor.submit(r));
      else
      {
        try
        {
          return r.call();
        }
        catch (Exception ignored)
        {
        }
      }
    }

    return lastFutureResult.get();
  }

  @Override
  protected boolean asynchronous()
  {
    return true;
  }

  /**
   * Liefert zurück, ob die Aktion aktiviert werden kann
   * Wird asynchron aufgerunden, wenn "asynchronous" auf TRUE gesetzt wird
   *
   * @see #asynchronous()
   * @return <tt>true</tt> wenn dies der Fall ist
   */
  protected abstract boolean enable0(@NonNull Node[] activatedNodes);

}
