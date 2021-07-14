package de.adito.aditoweb.nbm.metrics.api.types;

import java.lang.annotation.*;

/**
 * A histogram measures the distribution of values in a stream of data: e.g.,
 * the number of results returned by a search or the currently allocated amount of RAM.
 *
 * The annotated method has to return this number (int, long, short, ..).
 * All floating numbers (float, double) may be converted to long, depending on implementation.
 *
 * @author w.glanzer, 08.07.2021
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@MetricType
public @interface Histogram
{

  /**
   * @return unique name of this metric. Must not be empty and has to be unique!
   */
  String name();

}
