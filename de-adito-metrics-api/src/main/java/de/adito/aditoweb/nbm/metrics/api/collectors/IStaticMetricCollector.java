package de.adito.aditoweb.nbm.metrics.api.collectors;

/**
 * A static metric collector represents an object which contains methods annotated by some metric annotations.
 * Those methods will be called automatically, so that e.g. interval based collectors can be implemented.
 * The method calling strategy depends on the class annotation, {@link StaticMetricCollector#type()}.
 *
 * @author w.glanzer, 14.07.2021
 */
public interface IStaticMetricCollector
{
}
