package de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript;

import lombok.NonNull;
import org.openide.filesystems.FileObject;

import java.util.stream.Stream;

/**
 * Stellt für JavaScript Daten über Adito bereit.
 *
 * @author J. Boesl, 21.08.12
 */
public interface IJsDataSupply
{

  @NonNull
  Stream<String> getVariables(@NonNull FileObject pFileObject);

}
