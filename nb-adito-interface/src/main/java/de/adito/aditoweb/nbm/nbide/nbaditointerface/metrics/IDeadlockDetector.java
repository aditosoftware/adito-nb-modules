package de.adito.aditoweb.nbm.nbide.nbaditointerface.metrics;

/**
 * A deadlockDetector should detect deadlocks in the application threads. As such, the detection of the deadlocks should run in its own separate
 * thread so that there is no possibility of the deadlock affecting the deadlockDetecor itself
 *
 * @author m.kaspera, 15.12.2021
 */
public interface IDeadlockDetector extends IRunnableDetector
{

  /**
   * starts the deadlockDetector, this should start its own Thread or Executor in which the deadlocks are detected and then immediately return
   */
  void start();

}
