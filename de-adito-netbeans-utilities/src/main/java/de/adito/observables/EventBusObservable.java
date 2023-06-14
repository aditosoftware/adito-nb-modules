package de.adito.observables;

import com.google.common.eventbus.*;
import de.adito.util.reactive.AbstractListenerObservable;
import io.reactivex.rxjava3.core.Observable;
import lombok.NonNull;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.function.Predicate;

/**
 * Observable that listens on the guava eventbus
 *
 * @see EventBus
 */
@SuppressWarnings("UnstableApiUsage")
public class EventBusObservable extends AbstractListenerObservable<Object, EventBus, Object>
{
  private final Predicate<Object> firePredicate;

  /**
   * Creates a new observable that triggers, if a specific event happened.
   * WARNING: This observable does *not* fire an initial value!
   *
   * @param pBus   Bus to listen onm
   * @param pEvent Event to listen on (multiple events possible)
   * @return Observable containing the event that happened
   */
  @NonNull
  @SafeVarargs
  public static <T> Observable<T> createForFixedEvent(@NonNull EventBus pBus, @NonNull T... pEvent)
  {
    List<T> events = Arrays.asList(pEvent);

    //noinspection unchecked,SuspiciousMethodCalls
    return Observable.create(new EventBusObservable(pBus, events::contains))
        .map(pObj -> (T) pObj);
  }

  public EventBusObservable(@NonNull EventBus pListenableValue, @NonNull Predicate<Object> pFirePredicate)
  {
    super(pListenableValue);
    firePredicate = pFirePredicate;
  }

  @NonNull
  @Override
  protected Object registerListener(@NonNull EventBus pListenableValue, @NonNull IFireable<Object> pFireable)
  {
    //noinspection unused
    Object listener = new Object()
    {
      @Subscribe
      public void fireEvent(@Nullable Object pObject)
      {
        if (pObject != null && firePredicate.test(pObject))
          pFireable.fireValueChanged(pObject);
      }
    };
    pListenableValue.register(listener);
    return listener;
  }

  @Override
  protected void removeListener(@NonNull EventBus pListenableValue, @NonNull Object pEventBusObservable)
  {
    pListenableValue.unregister(pEventBusObservable);
  }
}