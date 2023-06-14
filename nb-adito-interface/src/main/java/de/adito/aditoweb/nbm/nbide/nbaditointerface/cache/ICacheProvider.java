package de.adito.aditoweb.nbm.nbide.nbaditointerface.cache;

import lombok.NonNull;

/**
 * Simple CacheProvider which is contained in the global lookup
 *
 * @author J. Boesl, 21.02.12
 */
public interface ICacheProvider
{
  /**
   * Returns a cache, this cache is saved on the disk
   *
   * @param pClass for identifying the cache
   * @param pName  unique and constant name of the cache. The name must not be changed.
   * @return the cache
   */
  @NonNull
  ICache get(@NonNull Class<?> pClass, @NonNull String pName);

  /**
   * Returns a cache, this cache is saved on the disk
   *
   * @param pClass       for identifying the cache
   * @param pName        unique and constant name of the cache. The name must not be changed.
   * @param pMaxHeapSize maximum size of the cache in the heap (kB)
   * @param pMaxDiskSize maximum size of the cache on the disk (kB)
   * @return the cache
   */
  @NonNull
  ICache get(@NonNull Class<?> pClass, @NonNull String pName, long pMaxHeapSize, long pMaxDiskSize);
}