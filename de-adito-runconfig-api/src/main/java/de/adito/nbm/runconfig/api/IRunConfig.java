package de.adito.nbm.runconfig.api;

import io.reactivex.rxjava3.core.Observable;
import org.jetbrains.annotations.NotNull;
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
  @NotNull
  Observable<Optional<IRunConfigCategory>> category();

  /**
   * @return DisplayName of this RunConfig. Will be displayed at least in the ComboBox in Toolbar
   */
  @NotNull
  Observable<String> displayName();

  /**
   * @return Icon, or <tt>null</tt>. Default: Icon of the category
   */
  @NotNull
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
  void executeAsnyc(@NotNull ProgressHandle pProgressHandle) throws Exception;

}
