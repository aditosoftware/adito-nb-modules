package de.adito.aditoweb.nbm.metrics.api.types;

import org.jetbrains.annotations.*;

import java.io.*;
import java.lang.annotation.*;
import java.util.Objects;

/**
 * Methods annotated with this annotation are metered a little bit different than ones with {@link Counted}.
 * The calls of methods annotated with this annotation get recorded as "events" and act like "distribution summaries", so that all
 * calls of this method get published to the metric endpoint.
 *
 * @author w.glanzer, 20.10.2021
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@MetricType
public @interface Sampled
{

  /**
   * @return unique name of this metric. Must not be empty and has to be unique!
   */
  String name();

  /**
   * @return factory that extends the given name dynamically based on the called method arguments
   */
  Class<? extends MetricType.IMetricNameFactory> nameFactory() default MetricType.DefaultMetricNameFactory.class;

  /**
   * @return this converter describes, how the argument values of the called method should be converted
   * to a sendable and human readable string
   */
  Class<? extends IArgumentConverter> argumentConverter() default DefaultArgumentConverter.class;

  /**
   * Describes, how the argument values of the called method should be converted
   * to a sendable and human readable string
   */
  interface IArgumentConverter
  {
    /**
     * Converts the given argument value to a readable string
     *
     * @param pArgumentValue value that should be converted
     * @return the converted string or NULL, if this argument should be skipped.
     */
    @Nullable
    String toString(@Nullable Object pArgumentValue);
  }

  class DefaultArgumentConverter implements IArgumentConverter
  {
    @Nullable
    @Override
    public String toString(@Nullable Object pArgumentValue)
    {
      if (pArgumentValue instanceof Throwable)
      {
        StringWriter writer = new StringWriter();
        ((Throwable) pArgumentValue).printStackTrace(new PrintWriter(writer));
        return writer.toString();
      }
      else
        return Objects.toString(pArgumentValue);
    }
  }

}
