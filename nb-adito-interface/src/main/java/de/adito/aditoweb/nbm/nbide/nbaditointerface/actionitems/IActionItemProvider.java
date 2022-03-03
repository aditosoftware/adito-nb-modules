package de.adito.aditoweb.nbm.nbide.nbaditointerface.actionitems;

import io.reactivex.rxjava3.core.Flowable;
import org.jetbrains.annotations.NotNull;
import org.openide.filesystems.FileObject;

import java.util.*;

/**
 * @author w.glanzer, 27.04.2020
 */
public interface IActionItemProvider
{

  /**
   * Creates a new flowable to get all items of the whole designer instance
   *
   * @return Flowable with a list of all current items
   */
  @NotNull
  Flowable<Set<IActionItem>> observeItems();

  /**
   * Creates a new flowable to get all items of a specific file.
   * Recursive, if the given fileobject is a folder
   *
   * @param pFo FileObject (file or folder)
   * @return Flowable with a list of all current items
   */
  @NotNull
  Flowable<Set<IActionItem>> observeItems(@NotNull FileObject pFo);

}
