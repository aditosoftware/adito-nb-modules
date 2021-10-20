package de.adito.aditoweb.nbm.metrics.api.types;

import org.jetbrains.annotations.*;

import java.lang.annotation.*;

/**
 * Annotation to mark all annotations that are metric annotations
 *
 * @author w.glanzer, 14.07.2021
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface MetricType
{

  /**
   * Name factory to create dynamic names based on method arguments
   */
  interface IMetricNameFactory
  {
    /**
     * Creates a name for a specific metric
     *
     * @param pBaseName        Base name, set in annotation
     * @param pMethodArguments Arguments given to the called, annotated method
     * @return the name
     */
    @Nullable
    String create(@Nullable String pBaseName, @NotNull Object[] pMethodArguments);
  }

  /**
   * NameFactory where input = output
   */
  class DefaultMetricNameFactory implements IMetricNameFactory
  {
    @Nullable
    @Override
    public String create(@Nullable String pBaseName, @NotNull Object[] pMethodArguments)
    {
      return pBaseName;
    }
  }

}
