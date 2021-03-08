package de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript.node;

import org.jetbrains.annotations.NotNull;
import org.openide.util.Lookup;

import java.io.*;

/**
 * Executor to execute commands on nodejs packages
 *
 * @author w.glanzer, 08.03.2021
 */
public interface INodeJSExecutor
{

  /**
   * Retrieves an instance of the executor
   *
   * @return the instance
   */
  @NotNull
  static INodeJSExecutor getInstance()
  {
    return Lookup.getDefault().lookup(INodeJSExecutor.class);
  }

  /**
   * Executes the given command on the nodejs version and waits until it finished
   *
   * @param pVersion Version to execute on. Has to be valid.
   * @param pCommand Command to execute, without "nodejs" prefix
   * @param pTimeout Timeout in ms, -1 if no timeout
   * @return the output of the executed nodejs command
   * @throws IOException if an error occured
   * @throws InterruptedException if the timeout killed the process
   */
  @NotNull
  String executeSync(@NotNull INodeJSVersion pVersion, @NotNull String pCommand, long pTimeout) throws IOException, InterruptedException;

}
