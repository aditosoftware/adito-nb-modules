package de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript.node;

import org.jetbrains.annotations.NotNull;
import org.openide.util.Lookup;

import java.io.IOException;

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
   * Executes the given command on the nodejs environment and waits until it finished
   *
   * @param pEnv     Environment to execute on. Has to be valid.
   * @param pBase    Base to execute commands on
   * @param pParams  Command to execute, without the base prefix
   * @param pTimeout Timeout in ms, -1 if no timeout
   * @return the output of the executed nodejs command
   * @throws IOException          if an error occured
   * @throws InterruptedException if the timeout killed the process
   */
  @NotNull
  String executeSync(@NotNull INodeJSEnvironment pEnv, @NotNull INodeJSExecBase pBase, long pTimeout, @NotNull String... pParams)
      throws IOException, InterruptedException;

}
