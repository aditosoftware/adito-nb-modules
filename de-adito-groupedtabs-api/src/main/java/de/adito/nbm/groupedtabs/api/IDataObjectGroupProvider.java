package de.adito.nbm.groupedtabs.api;

import lombok.NonNull;
import org.openide.loaders.DataObject;
import org.openide.util.Lookup;

import java.util.*;

/**
 * Provide an implementation of this interface in the global lookup to alter the grouping behavior of DataObject tabs.
 * Implementations also have to implement a Comparator for DataObjects, to sort DataObjects inside groups.
 *
 * @author p.neub, 27.02.2023
 */
public interface IDataObjectGroupProvider extends Comparator<DataObject>
{
  /**
   * Returns an instance of the {@link IDataObjectGroupProvider}.
   * The default implementation is {@link de.adito.nbm.groupedtabs.impl.DefaultDataObjectGroupProvider}
   *
   * @return an instance of {@link IDataObjectGroupProvider}
   */
  @NonNull
  static IDataObjectGroupProvider getDefault()
  {
    return Objects.requireNonNull(Lookup.getDefault().lookup(IDataObjectGroupProvider.class));
  }

  /**
   * Name of the group that the specified DataObject is part of.
   * Return {@link Optional#empty()} if this DataObject does not belong to any specific group.
   *
   * @param pDataObject the DataObject
   * @return the group of the DataObject
   */
  @NonNull
  Optional<String> group(@NonNull DataObject pDataObject);
}
