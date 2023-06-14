package de.adito.nbm.runconfig.api;

import io.reactivex.rxjava3.core.Observable;
import lombok.NonNull;
import org.netbeans.api.progress.ProgressHandle;

import java.awt.*;
import java.util.Optional;

/**
 * A RunConfig (Run Configuration) is an execution description, how something can be run by user.
 * It can contain some special preferences or just execute something.
 *
 * @author w.glanzer, 24.12.2018
 */
public interface IRunConfig {

  /**
   * @return Category to display in, or <tt>null</tt>
   */
  @NonNull
  Observable<Optional<IRunConfigCategory>> category();

  /**
   * @return DisplayName of this RunConfig. Will be displayed at least in the ComboBox in Toolbar
   */
  @NonNull
  Observable<String> displayName();

  /**
   * @return Icon, or <tt>null</tt>. Default: Icon of the category
   */
  @NonNull
  default Observable<Optional<Image>> icon() {
    return category()
        .defaultIfEmpty(Optional.empty())
        .switchMap(pCat -> pCat
            .map(IRunConfigCategory::icon)
            .orElseGet(() -> Observable.just(Optional.empty())));
  }

  /**
   * Executes this RunConfig in an other thread.
   * Not the EDT
   *
   * @param pProgressHandle Handle to display the progress
   * @throws Exception Exception -> Execution failed
   */
  void executeAsnyc(@NonNull ProgressHandle pProgressHandle) throws Exception;

}
