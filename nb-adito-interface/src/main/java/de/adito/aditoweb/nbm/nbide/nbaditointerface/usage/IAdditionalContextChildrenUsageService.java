package de.adito.aditoweb.nbm.nbide.nbaditointerface.usage;

import org.jetbrains.annotations.NotNull;
import org.netbeans.api.project.Project;
import org.openide.util.lookup.ServiceProvider;

import java.io.File;
import java.util.stream.Stream;

/**
 * Service interface for finding additional usages of the context children.
 * This interface should be implemented as a {@link ServiceProvider}.
 *
 * @author r.hartinger, 15.12.2022
 */
@SuppressWarnings("unused")
public interface IAdditionalContextChildrenUsageService
{

  /**
   * Returns a stream of files of additional usages of a context children.
   * Context children can be views or entities.
   *
   * @param pProject       the project where the additional usages should be found
   * @param pNameOfProperty the name of the property of the children element, e.g. the name of a view or an entity
   * @return a stream of files that are related to the property
   */
  @NotNull
  Stream<File> findAdditionalUsages(@NotNull Project pProject, @NotNull String pNameOfProperty);

}
