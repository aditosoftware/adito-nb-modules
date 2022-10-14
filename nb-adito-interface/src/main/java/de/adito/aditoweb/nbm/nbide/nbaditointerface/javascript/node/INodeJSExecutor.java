package de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript.node;

import org.jetbrains.annotations.*;
import org.netbeans.api.project.Project;

import java.io.*;
import java.util.Optional;
import java.util.concurrent.*;

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
   * @param pTimeout Timeout in ms, -1 if no timeout
   * @param pParams  Command to execute, without the base prefix
   * @return the output of the executed nodejs command
   * @throws IOException          if an error occurred
   * @throws InterruptedException if the timeout killed the process
   * @throws TimeoutException     if the timeout killed the process
   */
  @NotNull
  String executeSync(@NotNull INodeJSEnvironment pEnv, @NotNull INodeJSExecBase pBase, long pTimeout, @NotNull String... pParams)
      throws IOException, InterruptedException, TimeoutException;

  /**
   * Executes the given command on the nodejs environment and waits until it finished
   *
   * @param pEnv           Environment to execute on. Has to be valid.
   * @param pBase          Base to execute commands on
   * @param pTimeout       Timeout in ms, -1 if no timeout
   * @param pIncludeStdErr Whether to include STDERR in the output
   * @param pParams        Command to execute, without the base prefix
   * @return the output of the executed nodejs command
   * @throws IOException          if an error occurred
   * @throws InterruptedException if the timeout killed the process
   * @throws TimeoutException     if the timeout killed the process
   */
  @NotNull
  String executeSync(@NotNull INodeJSEnvironment pEnv, @NotNull INodeJSExecBase pBase, long pTimeout, boolean pIncludeStdErr, @NotNull String... pParams)
      throws IOException, InterruptedException, TimeoutException;

  /**
   * Executes the given command on the nodejs environment and returns the process after it was started.
   *
   * @param pEnv    Environment to execute on. Has to be valid.
   * @param pBase   Base to execute commands on
   * @param pParams Command to execute, without the base prefix
   * @return the process handle
   * @throws IOException if an error occurred
   */
  @NotNull
  Process execute(@NotNull INodeJSEnvironment pEnv, @NotNull INodeJSExecBase pBase, @NotNull String... pParams) throws IOException;

  /**
   * Executes the given command on the nodejs environment and returns a future containing the exitcode after it was started.
   *
   * @param pEnv        Environment to execute on. Has to be valid.
   * @param pBase       Base to execute commands on
   * @param pDefaultOut Default-Output
   * @param pErrorOut   Error-Output, NULL will use the given Default-Output
   * @param pDefaultIn  Default-Input, NULL ignores the input and does not delegate something to the spawned process
   * @param pParams     Command to execute, without the base prefix
   * @return the future containing the exitcode
   * @throws IOException if an error occurred
   */
  @NotNull
  CompletableFuture<Integer> executeAsync(@NotNull INodeJSEnvironment pEnv, @NotNull INodeJSExecBase pBase,
                                          @NotNull OutputStream pDefaultOut, @Nullable OutputStream pErrorOut, @Nullable InputStream pDefaultIn,
                                          @NotNull String... pParams) throws IOException;

}
