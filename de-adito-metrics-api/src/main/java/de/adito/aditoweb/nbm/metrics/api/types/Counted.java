package de.adito.aditoweb.nbm.metrics.api.types;

import java.lang.annotation.*;

/**
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
