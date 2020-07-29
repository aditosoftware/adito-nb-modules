package de.adito.nbm.blueprints.api;

import io.reactivex.rxjava3.core.Observable;
import org.jetbrains.annotations.NotNull;
import org.netbeans.api.project.Project;

import java.util.Optional;

/**
 * @author w.glanzer, 08.07.2020
 */
public interface IBlueprintDialogModel
{

  /**
   * Returns an observable that contains the project for this blueprint
   *
   * @return Observable with project
   */
  @NotNull
  Observable<Optional<Project>> observeProject();

  /**
   * Returns an observable containing the value of a single attribut of a parameter.
   * Resolves other references if necessary.
   *
   * @param pID   ID of the parameter that should be observed
   * @param pName Name of the attribute
   * @return Observable containing the string value
   */
  @NotNull
  Observable<Optional<String>> observeAttributeValue(@NotNull String pID, @NotNull String pName);

}
