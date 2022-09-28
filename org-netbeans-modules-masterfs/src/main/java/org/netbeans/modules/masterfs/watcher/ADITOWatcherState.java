package org.netbeans.modules.masterfs.watcher;

/**
 * @author p.neub, 28.09.2022
 */
public interface ADITOWatcherState
{
  /**
   * set the FileWatcher state to indicate whether FileWatcher is currently running or waiting
   *
   * @param pIsRunning indicates whether the FileWatcher is currently running or waiting
   */
  void setWatcherState(boolean pIsRunning);
}
