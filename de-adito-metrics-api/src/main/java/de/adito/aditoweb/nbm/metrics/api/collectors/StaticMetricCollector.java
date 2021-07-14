package de.adito.aditoweb.nbm.metrics.api.collectors;

import de.adito.picoservice.PicoService;

import java.lang.annotation.*;

/**
 * This annotation marks an {@link IStaticMetricCollector}
 *
 * @author w.glanzer, 14.07.2021
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@PicoService
public @interface StaticMetricCollector
{

  /**
   * @return unique name of the annotated collector
   */
  String name();

  /**
   * @return type of the collector
   */
  EStaticMetricCollectorType type();

}
