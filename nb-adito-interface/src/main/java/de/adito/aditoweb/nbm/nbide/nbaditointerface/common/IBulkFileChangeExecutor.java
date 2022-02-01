package de.adito.aditoweb.nbm.nbide.nbaditointerface.common;

import org.jetbrains.annotations.NotNull;

/**
 * Executor for many bundled file changes, while the indexer should be disabled, for example.
 *
 * @author s.seemann, 31.01.2022
 */
public interface IBulkFileChangeExecutor<Ex extends Throwable>
{

  /**
   * Executes something without indexing
   *
   * @param pRunnable runnable, which should be executed
   */
  void execute(@NotNull IBulkFileChangeRunnable<Ex> pRunnable) throws Ex;


  /**
   * Runnable with Exception for BulkFileChanges
   *
   * @param <Ex>
   */
  interface IBulkFileChangeRunnable<Ex extends Throwable>
  {

    void run() throws Ex;
  }
}
