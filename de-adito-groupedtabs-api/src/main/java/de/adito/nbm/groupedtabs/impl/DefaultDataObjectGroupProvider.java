package de.adito.nbm.groupedtabs.impl;

import de.adito.nbm.groupedtabs.api.IDataObjectGroupProvider;
import org.jetbrains.annotations.NotNull;
import org.netbeans.api.project.*;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.util.lookup.ServiceProvider;

import java.util.*;

/**
 * Default implementation of {@link IDataObjectGroupProvider}.
 * All files that are inside the folder of an entity belong to one group.
 * All other files belong to the default group.
 * DataObjects are sorted by their path.
 *
 * @author p.neub, 28.02.2023
 */
@ServiceProvider(service = IDataObjectGroupProvider.class)
public final class DefaultDataObjectGroupProvider implements IDataObjectGroupProvider
{
  @NotNull
  @Override
  public Optional<String> group(@NotNull DataObject pDataObject)
  {
    final FileObject file = pDataObject.getPrimaryFile();
    return Optional.ofNullable(file)
        .map(FileOwnerQuery::getOwner)
        .map(Project::getProjectDirectory)
        .map(FileObject::getPath)
        .map(pProjectDir -> {
          FileObject curr = file.getParent();
          while (true)
          {
            FileObject parent = curr.getParent();
            if (parent == null || pProjectDir.equals(parent.getPath()))
              return null;
            if ("entity".equals(parent.getName()))
              return curr.getName();
            curr = parent;
          }
        });
  }

  @Override
  public int compare(DataObject pFirst, DataObject pSecond)
  {
    return pFirst.getPrimaryFile().getPath()
        .compareToIgnoreCase(pSecond.getPrimaryFile().getPath());
  }
}
