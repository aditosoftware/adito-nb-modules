package de.adito.observables.netbeans;

import de.adito.util.reactive.AbstractListenerObservable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.jetbrains.annotations.NotNull;

import java.util.prefs.*;


/**
 * Observable, that listens on preferences
 *
 * @author w.glanzer, 26.03.2021
 */
public class PreferencesObservable extends AbstractListenerObservable<PreferenceChangeListener, Preferences, Preferences>
{

  @NotNull
  public static Observable<Preferences> create(@NotNull Preferences pPreferences)
  {
    return Observable.create(new PreferencesObservable(pPreferences))
        .startWithItem(pPreferences)
        .observeOn(Schedulers.computation())
        .subscribeOn(Schedulers.computation());
  }

  private PreferencesObservable(@NotNull Preferences pBase)
  {
    super(pBase);
  }

  @NotNull
  @Override
  protected PreferenceChangeListener registerListener(@NotNull Preferences pBase, @NotNull IFireable<Preferences> pFireable)
  {
    PreferenceChangeListener pcl = ev -> pFireable.fireValueChanged(pBase);
    pBase.addPreferenceChangeListener(pcl);
    return pcl;
  }

  @Override
  protected void removeListener(@NotNull Preferences pBase, @NotNull PreferenceChangeListener pPreferencesChangeListener)
  {
    pBase.removePreferenceChangeListener(pPreferencesChangeListener);
  }

}
