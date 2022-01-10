package de.adito.aditoweb.nbm.nbide.nbaditointerface.common;


import io.reactivex.rxjava3.disposables.Disposable;
import org.jetbrains.annotations.*;
import org.netbeans.api.project.Project;

import java.util.function.Consumer;

/**
 * Service for disposing objects
 *
 * @author s.seemann, 10.01.2022
 */
public interface IDisposerService
{

  /**
   * Wait until the submitted project has been disposed.
   *
   * @param pProject project whose dispose is to be waited for
   */
  void awaitProjectDispose(@NotNull Project pProject);

  /**
   * Disposes all disposables associated with the submitted project
   *
   * @param pProject the project which should be disposed
   */
  void disposeProject(@NotNull Project pProject);

  /**
   * Disposing an object asynchronously
   *
   * @param pObject the object which should be disposed
   */
  void disposeObjectAsync(@Nullable Object pObject);

  /**
   * Disposing an object asynchronously
   *
   * @param pObject    the object which should be disposed
   * @param pRecursive if the object should be disposed recursively (if it is a lookup, dispose all elements of the lookup)
   */
  void disposeObjectAsync(@Nullable Object pObject, boolean pRecursive);

  /**
   * Registers a new disposable on the given project
   *
   * @param pProject    the project
   * @param pDisposable the disposable
   */
  void register(@NotNull Project pProject, @NotNull Disposable pDisposable);

  /**
   * Registers a consumer, which is called, when the project is disposed
   *
   * @param pHandler consumer who receives the project that is currently to be dispatched
   */
  void doOnDispose(@NotNull Consumer<Project> pHandler);

  /**
   * Removes a dispose consumer, which was added with {@link #doOnDispose(Consumer)}.
   *
   * @param pHandler the handler
   */
  void deregisterOnDisposeHandler(@NotNull Consumer<Project> pHandler);
}
