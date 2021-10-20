package de.adito.aditoweb.nbm.metrics.api.types;

import java.lang.annotation.*;

/**
 * A gauge is the simplest metric type. It just returns a value, so that the exporter will export the given value.
 *
 * The method annotated with @Gauge has to return the desired value, NULL is valid.
 *
 * @author w.glanzer, 14.07.2021
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@MetricType
public @interface Gauge
{

  /**
   * @return unique name of this metric. Must not be empty and has to be unique!
   */
  String name();

  /**
   * @return factory that extends the given name dynamically based on the called method arguments
   */
  Class<? extends MetricType.IMetricNameFactory> nameFactory() default MetricType.DefaultMetricNameFactory.class;

}
