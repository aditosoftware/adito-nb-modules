package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync;

import org.jetbrains.annotations.*;
import org.openide.filesystems.FileObject;

/**
 * @author J. Boesl, 16.05.11
 */
public interface IFormComponentPropertyMapping
{

  @NotNull
  EContainerType getContainerType();

  /**
   * @return das FileObject aus der layer.xml für den Paletteneintrag.
   */
  @Nullable
  FileObject getPaletteConfigItem();

  Class<?> getComponentClass();

}
