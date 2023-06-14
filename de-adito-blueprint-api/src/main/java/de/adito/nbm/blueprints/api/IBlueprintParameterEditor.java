package de.adito.nbm.blueprints.api;

import io.reactivex.rxjava3.disposables.Disposable;
import lombok.NonNull;
import org.jetbrains.annotations.*;

import javax.swing.*;
import java.util.function.Consumer;

/**
 * Describes an editor of a parameter of a blueprint
 *
 * @author w.glanzer, 06.07.2020
 * @see IBlueprintParameter
 */
public interface IBlueprintParameterEditor
{

  /**
   * Initializes this editor with a specific dialogModel.
   * Every disposable that should be disposed after closing the dialog, should be returned.
   *
   * @param pParameter   Parameter that should be shown
   * @param pDialogModel DialogModel to get data
   */
  @Nullable
  Disposable init(@NonNull IBlueprintParameter pParameter, @NonNull IBlueprintDialogModel pDialogModel);

  /**
   * Adds a "Listener" to this editor to retrieve the information, if a new value is available
   *
   * @param pNewValueConsumer Consumer for the new value
   */
  void attachValueChangeListener(@NonNull Consumer<String> pNewValueConsumer);

  /**
   * @return the component
   */
  @Nullable
  JComponent getComponent();

}
