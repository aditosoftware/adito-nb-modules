package de.adito.nbm.runconfig.api;

import io.reactivex.Observable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Optional;

/**
 * Category for an IRunConfig
 *
 * @see IRunConfig
 * @author w.glanzer, 24.12.2018
 */
public interface IRunConfigCategory
{

  /**
   * @return Unique identifier for this category
   */
  @NotNull
  String getName();

  /**
   * @return Title of this Category
   */
  @NotNull
  Observable<String> title();

  /**
   * @return Icon, or <tt>null</tt>
   */
  @NotNull
  default Observable<Optional<Image>> icon()
  {
    return Observable.just(Optional.empty());
  }

}
