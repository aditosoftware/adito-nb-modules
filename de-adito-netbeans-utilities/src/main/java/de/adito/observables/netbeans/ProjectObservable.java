package de.adito.observables.netbeans;

import de.adito.util.reactive.*;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.jetbrains.annotations.NotNull;
import org.netbeans.api.project.*;

import java.beans.PropertyChangeListener;

/**
 * Observable, welches auf ein spezifisches Projekt und seinen PropertyChangeListener hört
 *
 * @author w.glanzer, 29.12.2018
 */
public class ProjectObservable extends AbstractListenerObservable<PropertyChangeListener, ProjectInformation, ProjectInformation>
{

  /**
   * Erstellt ein Observable, welches auf ein spezifisches Projekt und seinen PropertyChangeListener hört
   *
   * @param pProject Projekt
   * @return das Observable mit dem Projekt
   */
  @NotNull
  public static Observable<ProjectInformation> createInfos(@NotNull Project pProject)
  {
    return LookupResultObservable.create(pProject.getLookup(), ProjectInformation.class)
        .switchMap(pInfos -> {
          if(pInfos.isEmpty())
            return Observable.empty();
          return Observables.create(new ProjectObservable(pInfos.get(0)), () -> pInfos.get(0));
        })
        .observeOn(Schedulers.computation())
        .subscribeOn(Schedulers.computation());
  }

  private ProjectObservable(@NotNull ProjectInformation pProjectInformation)
  {
    super(pProjectInformation);
  }

  @NotNull
  @Override
  protected PropertyChangeListener registerListener(@NotNull ProjectInformation pProjectInformation, @NotNull IFireable<ProjectInformation> pFireable)
  {
    PropertyChangeListener pcl = (e) -> pFireable.fireValueChanged(pProjectInformation);
    pProjectInformation.addPropertyChangeListener(pcl);
    return pcl;
  }

  @Override
  protected void removeListener(@NotNull ProjectInformation pProjectInformation, @NotNull PropertyChangeListener pPropertyChangeListener)
  {
    pProjectInformation.removePropertyChangeListener(pPropertyChangeListener);
  }
}
