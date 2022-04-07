package de.adito.observables.netbeans;

import com.google.common.collect.ImmutableList;
import de.adito.util.reactive.AbstractListenerObservable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.jetbrains.annotations.NotNull;
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
  @NotNull
  public static <T> Observable<List<T>> create(Lookup pLookup, Class<T> pLookupClass)
  {
    Lookup.Result<T> result = pLookup.lookupResult(pLookupClass);
    return Observable.create(new LookupResultObservable<>(result))
        .startWithItem(_getValuesUnmodifiable(result))
        .observeOn(Schedulers.computation())
        .distinctUntilChanged();
  }

  private LookupResultObservable(@NotNull Lookup.Result<T> pResult)
  {
    super(pResult);
  }

  @NotNull
  @Override
  protected LookupListener registerListener(@NotNull Lookup.Result<T> pResult, @NotNull IFireable<List<T>> pFireable)
  {
    LookupListener ll = ev -> pFireable.fireValueChanged(_getValuesUnmodifiable(pResult));
    pResult.addLookupListener(ll);
    return ll;
  }

  @Override
  protected void removeListener(@NotNull Lookup.Result<T> pResult, @NotNull LookupListener pLookupListener)
  {
    pResult.removeLookupListener(pLookupListener);
  }

  @NotNull
  private static <T> List<T> _getValuesUnmodifiable(@NotNull Lookup.Result<T> pResult)
  {
    return ImmutableList.copyOf(pResult.allInstances());
  }

}
