package de.adito.observables.netbeans;

import com.google.common.collect.ImmutableList;
import de.adito.util.reactive.AbstractListenerObservable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.NonNull;
import org.openide.util.*;

import java.util.*;


/**
 * Observable, das auf ein Lookup hört
 *
 * @author w.glanzer, 24.12.2018
 */
public class LookupResultObservable<T> extends AbstractListenerObservable<LookupListener, Lookup.Result<T>, List<T>>
{

  /**
   * Erstellt ein neues Observable das auf Änderungen in einem Lookup hört.
   *
   * @param pLookup      Lookup, auf das gehorcht wird
   * @param pLookupClass Klasse, die als LookupResult dient
   * @return ein Observable, das alle Instanzen als Liste enthält
   */
  @NonNull
  public static <T> Observable<List<T>> create(Lookup pLookup, Class<T> pLookupClass)
  {
    Lookup.Result<T> result = pLookup.lookupResult(pLookupClass);
    return Observable.create(new LookupResultObservable<>(result))
        .startWithItem(_getValuesUnmodifiable(result))
        .observeOn(Schedulers.computation())
        .subscribeOn(Schedulers.computation())
        .distinctUntilChanged();
  }

  private LookupResultObservable(@NonNull Lookup.Result<T> pResult)
  {
    super(pResult);
  }

  @NonNull
  @Override
  protected LookupListener registerListener(@NonNull Lookup.Result<T> pResult, @NonNull IFireable<List<T>> pFireable)
  {
    LookupListener ll = ev -> pFireable.fireValueChanged(_getValuesUnmodifiable(pResult));
    pResult.addLookupListener(ll);
    return ll;
  }

  @Override
  protected void removeListener(@NonNull Lookup.Result<T> pResult, @NonNull LookupListener pLookupListener)
  {
    pResult.removeLookupListener(pLookupListener);
  }

  @NonNull
  private static <T> List<T> _getValuesUnmodifiable(@NonNull Lookup.Result<T> pResult)
  {
    return ImmutableList.copyOf(pResult.allInstances());
  }

}
