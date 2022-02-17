package de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript.node;

import io.reactivex.rxjava3.core.Observable;
import org.jetbrains.annotations.NotNull;
import org.netbeans.api.project.Project;
import org.openide.loaders.DataObject;

import java.util.Optional;

/**
 * @author m.kaspera, 11.02.2022
 */
public interface IJSNodeNameProvider
{

  /**
   * Returns the provider for the given project
   *
   * @param pProject Project to search provider for
   * @return the provider
   */
  @NotNull
  static Optional<IJSNodeNameProvider> findInstance(@NotNull Project pProject)
  {
    return Optional.ofNullable(pProject.getLookup().lookup(IJSNodeNameProvider.class));
  }

  @NotNull
  Observable<Optional<String>> getDisplayName(@NotNull DataObject pDataObject);

}
