package de.adito.aditoweb.nbm.metrics.api.types;

import java.lang.annotation.*;

/**
 * All method invocations of methods annotated with @Counted will be counted, so that
 * at the end of the day you get the amount of method calls during the whole application session.
 *
 * The counter never gets decreased, only increased on method call.
 *
 * The return type of the annotated method won't be used and does not effect the metric collection.
 *
 * @author w.glanzer, 08.07.2021
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@MetricType
public @interface Counted
{

  /**
   * @return unique name of this metric. Must not be empty and has to be unique!
   */
  String name();

}
