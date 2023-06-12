package de.adito.nbm.runconfig.api;

import io.reactivex.rxjava3.core.Observable;
import lombok.NonNull;

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
  @NonNull
  String getName();

  /**
   * @return Title of this Category
   */
  @NonNull
  Observable<String> title();

  /**
   * @return Icon, or <tt>null</tt>
   */
  @NonNull
  default Observable<Optional<Image>> icon()
  {
    return Observable.just(Optional.empty());
  }

}
