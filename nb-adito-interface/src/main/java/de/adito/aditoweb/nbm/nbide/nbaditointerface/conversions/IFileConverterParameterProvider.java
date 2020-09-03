package de.adito.aditoweb.nbm.nbide.nbaditointerface.conversions;

import org.jetbrains.annotations.*;

import java.io.File;
import java.util.Map;

/**
 * Provides (default) parameters for a specific file conversion
 *
 * @author w.glanzer, 03.09.2020
 */
public interface IFileConverterParameterProvider
{

  /**
   * Modifies the given Parameters, so that all default parameters are included
   *
   * @param pSourceLocation Location of the original file
   * @param pTargetLocation Location that the converted file should be placed at
   * @param pSourceType     File type of the file to convert
   * @param pTargetType     File type of the final file
   * @param pParams         Parameters for the conversion that were given in the original conversion call.
   *                        They have to be modified
   */
  void modifyParameters(@Nullable File pSourceLocation, @Nullable File pTargetLocation,
                        @NotNull String pSourceType, @NotNull String pTargetType,
                        @NotNull Map<String, Object> pParams);

}
