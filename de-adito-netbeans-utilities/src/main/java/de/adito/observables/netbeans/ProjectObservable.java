package de.adito.observables.netbeans;

import de.adito.util.reactive.*;
import io.reactivex.rxjava3.core.Observable;
import lombok.NonNull;
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
  @NonNull
  public static Observable<ProjectInformation> createInfos(@NonNull Project pProject)
  {
    return createInfos(pProject, true);
  }

  /**
   * Erstellt ein Observable, welches auf ein spezifisches Projekt und seinen PropertyChangeListener hört
   *
   * @param pProject             Projekt
   * @param pUsePossibleDelegate true, wenn das Delegate des Projektes, wenn möglich (wenn implementiert), verwendet werden soll.
   *                             Regulär ist der Parameter IMMER TRUE, außer man implementiert das Delegate.
   * @return das Observable mit dem Projekt
   */
  @NonNull
  public static Observable<ProjectInformation> createInfos(@NonNull Project pProject, boolean pUsePossibleDelegate)
  {
    if (pUsePossibleDelegate && pProject instanceof IProvider)
      return ((IProvider) pProject).create();

    return LookupResultObservable.create(pProject.getLookup(), ProjectInformation.class)
        .switchMap(pInfos -> {
          if (pInfos.isEmpty())
            return Observable.empty();
          return Observables.create(new ProjectObservable(pInfos.get(0)), () -> pInfos.get(0));
        });
  }

  private ProjectObservable(@NonNull ProjectInformation pProjectInformation)
  {
    super(pProjectInformation);
  }

  @NonNull
  @Override
  protected PropertyChangeListener registerListener(@NonNull ProjectInformation pProjectInformation, @NonNull IFireable<ProjectInformation> pFireable)
  {
    PropertyChangeListener pcl = (e) -> pFireable.fireValueChanged(pProjectInformation);
    pProjectInformation.addPropertyChangeListener(pcl);
    return pcl;
  }

  @Override
  protected void removeListener(@NonNull ProjectInformation pProjectInformation, @NonNull PropertyChangeListener pPropertyChangeListener)
  {
    pProjectInformation.removePropertyChangeListener(pPropertyChangeListener);
  }

  public interface IProvider
  {
    @NonNull
    Observable<ProjectInformation> create();
  }
}
