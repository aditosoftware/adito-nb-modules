package de.adito.aditoweb.nbm.nbide.nbaditointerface.metrics;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;

/**
 * This MetricsFacade takes different types of metric objects.
 * They will be presented in different ways - maybe via JMX in JVisualVM / JProfiler or via Console
 *
 * @author w.glanzer, 24.02.2020
 */
public interface IMetricsFacade
{

  /**
   * Returns a (new) counter object
   *
   * @param pName name of the counter
   * @return the counter instance
   */
  @NotNull
  ICounter counter(@NotNull String pName);

  /**
   * Returns a (new) meter object
   *
   * @param pName name of the meter
   * @return the meter instance
   */
  @NotNull
  IMeter meter(@NotNull String pName);

  /**
   * Returns a (new) timer object
   *
   * @param pName name of the timer
   * @return the timer instance
   */
  @NotNull
  ITimer timer(@NotNull String pName);

  /**
   * A counter is just a gauge for an AtomicLong instance.
   * You can increment or decrement its value.
   * For example, we may want a more efficient way of measuring the pending job in a queue
   */
  interface ICounter
  {
    /**
     * Increment its value if positive, or decrements it if negative
     *
     * @param pValue Value
     */
    void count(long pValue);
  }

  /**
   * A meter measures the rate of events over time (e.g., “requests per second”).
   * In addition to the mean rate, meters also track 1-, 5-, and 15-minute moving averages.
   */
  interface IMeter
  {
    /**
     * Marks, that an event has occured
     */
    void mark();
  }

  /**
   * A timer measures both the rate that a particular piece of code
   * is called and the distribution of its duration.
   */
  interface ITimer
  {
    /**
     * Times and records the duration of event.
     *
     * @param pAction Action to track
     * @return the return value of pAction
     */
    <T> T time(@NotNull Callable<T> pAction);
  }

}
