package de.adito.observables.netbeans;

import de.adito.util.reactive.AbstractListenerObservable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.NonNull;
import org.openide.nodes.*;

import java.beans.PropertyChangeEvent;
import java.util.*;

/**
 * Observable that listens on a specfic node and triggers, if something has changed
 *
 * @author w.glanzer, 02.12.2019
 */
public class NodeObservable extends AbstractListenerObservable<NodeListener, Node, Node>
{
  private List<String> properties;

  @NonNull
  public static Observable<Node> create(@NonNull Node pNode, @NonNull String... pProps)
  {
    return Observable.create(new NodeObservable(pNode, pProps))
        .startWithItem(pNode)
        .observeOn(Schedulers.computation())
        .subscribeOn(Schedulers.computation());
  }

  private NodeObservable(@NonNull Node pListenableValue, @NonNull String... pProperties)
  {
    super(pListenableValue);
    properties = Arrays.asList(pProperties);
  }

  @NonNull
  @Override
  protected NodeListener registerListener(@NonNull Node pListenableValue, @NonNull IFireable<Node> pFireable)
  {
    NodeListener listener = new NodeAdapter()
    {
      @Override
      public void propertyChange(PropertyChangeEvent ev)
      {
        if (properties.isEmpty() || properties.contains(ev.getPropertyName()))
          pFireable.fireValueChanged(pListenableValue);
        super.propertyChange(ev);
      }
    };
    pListenableValue.addNodeListener(listener);
    return listener;
  }

  @Override
  protected void removeListener(@NonNull Node pListenableValue, @NonNull NodeListener pListener)
  {
    pListenableValue.removeNodeListener(pListener);
  }
}
