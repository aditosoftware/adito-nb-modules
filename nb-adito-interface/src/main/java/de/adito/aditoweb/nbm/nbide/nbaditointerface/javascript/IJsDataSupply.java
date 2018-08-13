package de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript;

import org.jetbrains.annotations.NotNull;
import org.openide.filesystems.FileObject;

import java.util.stream.Stream;

/**
 * Stellt für JavaScript Daten über Adito bereit.
 *
 * @author J. Boesl, 21.08.12
 */
public interface IJsDataSupply
{

  @NotNull
  Stream<String> getVariables(@NotNull FileObject pFileObject);

}
