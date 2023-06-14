package de.adito.aditoweb.nbm.metrics.api;

import lombok.NonNull;
import org.jetbrains.annotations.Nullable;
import org.openide.util.Lookup;

/**
 * This proxy factory creates a "shallow" wrapper objects around a given object, so that
 * all metric annotations can be evaluated correctly.
 *
 * @author w.glanzer, 09.07.2021
 */
public interface IMetricProxyFactory
{

  /**
   * Creates a "shallow" wrapper around a given object, so that all metric
   * annotations can be evaluated correctly.
   *
   * @param pObject Object to proxy
   * @return the proxied object that has to be used, if metrics should be evaluated
   */
  @Nullable
  static <T> T proxy(@Nullable T pObject)
  {
    if(pObject == null)
      return null;

    for (IMetricProxyFactory proxy : Lookup.getDefault().lookupAll(IMetricProxyFactory.class))
    {
      if(proxy.canCreateProxy(pObject))
        return proxy.createProxy(pObject);
    }

    // no plugin installed - just return object and do not proxy metrics
    return pObject;
  }

  /**
   * Specifies, if this factory can create a proxy object of the given parameter
   *
   * @param pObject Object to proxy
   * @return true, if it can be proxied by this proxy factory
   */
  boolean canCreateProxy(@NonNull Object pObject);

  /**
   * Creates a proxy around the given object
   *
   * @param pObject Object to proxy
   * @return the proxied object
   */
  @NonNull
  <T> T createProxy(@NonNull T pObject);

}
