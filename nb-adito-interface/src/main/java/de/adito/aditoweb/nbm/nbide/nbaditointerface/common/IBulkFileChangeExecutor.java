package de.adito.aditoweb.nbm.nbide.nbaditointerface.common;

import lombok.NonNull;

/**
 * Executor for many bundled file changes, while the indexer should be disabled, for example.
 *
 * @author s.seemann, 31.01.2022
 */
public interface IBulkFileChangeExecutor
{

  /**
   * Executes something without indexing
   *
   * @param pRunnable runnable, which should be executed
   */
  <Ex extends Throwable> void execute(@NonNull IBulkFileChangeRunnable<Ex> pRunnable) throws Ex;


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
