package de.adito.aditoweb.nbm.metrics.api.types;

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
}
