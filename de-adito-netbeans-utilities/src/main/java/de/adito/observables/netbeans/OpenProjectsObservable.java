package de.adito.observables.netbeans;

import com.google.common.collect.ImmutableList;
import de.adito.util.reactive.AbstractListenerObservable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.NonNull;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ui.OpenProjects;

import java.beans.PropertyChangeListener;
import java.util.*;

/**
 * Observable, welches auf die OpenProjects hört und diese als Liste ausgibt wenn sich diese verändert.
 *
 * @author w.glanzer, 24.12.2018
 */
public class OpenProjectsObservable extends AbstractListenerObservable<PropertyChangeListener, OpenProjects, List<Project>>
{

  private static Observable<List<Project>> _INSTANCE;

  /**
   * Erstellt ein Observable, welches auf die geöffneten Projekte hört und diese als Liste zurückgibt.
   * Wird ein Projekt geöffnet oder geschlossen, feuert es eine neue Liste
   *
   * @return das Observable mit einer unmodifizierbaren Liste der Projekte
   */
  @NonNull
  public static synchronized Observable<List<Project>> create()
  {
    if (_INSTANCE == null)
      _INSTANCE = Observable.create(new OpenProjectsObservable())
          .startWithItem(ImmutableList.copyOf(OpenProjects.getDefault().getOpenProjects()))
          .observeOn(Schedulers.computation())
          .subscribeOn(Schedulers.computation())
          .replay(1)
          .autoConnect();
    return _INSTANCE;
  }

  private OpenProjectsObservable()
  {
    super(OpenProjects.getDefault());
  }

  @NonNull
  @Override
  protected PropertyChangeListener registerListener(@NonNull OpenProjects pOpenProjects, @NonNull IFireable<List<Project>> pFireable)
  {
    PropertyChangeListener pcl = evt -> {
      if (Objects.equals(evt.getPropertyName(), OpenProjects.PROPERTY_OPEN_PROJECTS))
        pFireable.fireValueChanged(ImmutableList.copyOf(pOpenProjects.getOpenProjects()));
    };
    pOpenProjects.addPropertyChangeListener(pcl);
    return pcl;
  }

  @Override
  protected void removeListener(@NonNull OpenProjects pOpenProjects, @NonNull PropertyChangeListener pPropertyChangeListener)
  {
    pOpenProjects.removePropertyChangeListener(pPropertyChangeListener);
  }

}
