package de.adito.aditoweb.nbm.nbide.nbaditointerface.cache;

import com.google.common.hash.HashFunction;
import org.jetbrains.annotations.*;

import java.io.Serializable;

/**
 * A simple interface to a pure performance cache that persists data between JVM sessions, but whose data can also be cleaned up at any time.
 *
 * @author J. Boesl, 23.02.12
 */
public interface ICache
{
  /**
   * Checks if this key is contained in the cache
   *
   * @param pKey the key
   * @return true, if the key is contained
   */
  boolean has(Object pKey);

  /**
   * Returns the corresponding value.
   *
   * @param pKey the key
   * @return the value or null, if key is not contained
   */
  @Nullable
  Serializable get(Object pKey);

  /**
   * Puts an object into the cache.
   *
   * @param pKey   the key
   * @param pValue the value written to the cache. If a value already exists under the specified key, it will be overwritten.
   */
  void put(@NotNull Object pKey, @Nullable Serializable pValue);

  /**
   * Returns a function that can be used to only cache the hash of data. HashFunction is provided to ensure that when comparing from different caches,
   * the hash was created using the same function.
   *
   * @return default HashFunction.
   */
  @SuppressWarnings("UnstableApiUsage")
  @NotNull
  HashFunction getHashFunction();
}