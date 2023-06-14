package de.adito.observables.netbeans;

import de.adito.util.reactive.AbstractListenerObservable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.NonNull;

import java.util.prefs.*;


/**
 * Observable, that listens on preferences
 *
 * @author w.glanzer, 26.03.2021
 */
public class PreferencesObservable extends AbstractListenerObservable<PreferenceChangeListener, Preferences, Preferences>
{

  @NonNull
  public static Observable<Preferences> create(@NonNull Preferences pPreferences)
  {
    return Observable.create(new PreferencesObservable(pPreferences))
        .startWithItem(pPreferences)
        .observeOn(Schedulers.computation())
        .subscribeOn(Schedulers.computation());
  }

  private PreferencesObservable(@NonNull Preferences pBase)
  {
    super(pBase);
  }

  @NonNull
  @Override
  protected PreferenceChangeListener registerListener(@NonNull Preferences pBase, @NonNull IFireable<Preferences> pFireable)
  {
    PreferenceChangeListener pcl = ev -> pFireable.fireValueChanged(pBase);
    pBase.addPreferenceChangeListener(pcl);
    return pcl;
  }

  @Override
  protected void removeListener(@NonNull Preferences pBase, @NonNull PreferenceChangeListener pPreferencesChangeListener)
  {
    pBase.removePreferenceChangeListener(pPreferencesChangeListener);
  }

}
