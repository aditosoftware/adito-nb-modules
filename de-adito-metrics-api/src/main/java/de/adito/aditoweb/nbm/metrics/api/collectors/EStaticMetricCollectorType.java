package de.adito.aditoweb.nbm.metrics.api.collectors;

/**
 * Type that represents the strategy, how methods in the metric collectors will be called
 *
 * @author w.glanzer, 14.07.2021
 */
public enum EStaticMetricCollectorType
{

  /**
   * All metric methods will be called frequently in a fixed interval.
   * The specific interval depends on the implementation, but may be e.g. 30sec or less.
   */
  INTERVAL,

  /**
   * All metric methods will be called only once during application startup only
   */
  START

}
