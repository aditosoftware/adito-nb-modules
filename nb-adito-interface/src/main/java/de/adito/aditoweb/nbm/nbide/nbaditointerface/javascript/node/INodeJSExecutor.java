package de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript.node;

import org.jetbrains.annotations.NotNull;
import org.netbeans.api.project.Project;

import java.io.IOException;
import java.util.Optional;

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
  static Optional<INodeJSExecutor> findInstance(@NotNull Project pProject)
  {
    return Optional.ofNullable(pProject.getLookup().lookup(INodeJSExecutor.class));
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

  /**
   * Executes the given command on the nodejs environment and returns the process after it was started.
   * Does wait until completion.
   *
   * @param pEnv     Environment to execute on. Has to be valid.
   * @param pBase    Base to execute commands on
   * @param pParams  Command to execute, without the base prefix
   * @param pTimeout Timeout in ms, -1 if no timeout
   * @return the process handle
   * @throws IOException if an error occured
   */
  @NotNull
  Process execute(@NotNull INodeJSEnvironment pEnv, @NotNull INodeJSExecBase pBase, long pTimeout, @NotNull String... pParams) throws IOException;

}
