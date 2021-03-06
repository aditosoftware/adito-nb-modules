package de.adito.aditoweb.nbm.nbide.nbaditointerface.conversions;

import org.jetbrains.annotations.*;

import java.io.*;
import java.util.Map;

/**
 * Interface for service providers that can convert one file type to another
 *
 * @author m.kaspera, 08.04.2019
 */
public interface IFileConverter
{

  /**
   * Check if the Converter can accomplish the specified conversion
   *
   * @param pSourceType File type of the file to convert
   * @param pTargetType File type of the final file
   * @param pParams     Parameters for the conversion
   * @return true if the serviceProvider can accomplish the specified conversion, false otherwise
   */
  boolean canConvert(@NotNull String pSourceType, @NotNull String pTargetType, @NotNull Map<Object, Object> pParams);

  /**
   * Converts the source file to the specified type at the target location
   *
   * @param pSourceLocation Location of the original file
   * @param pTargetLocation Location that the converted file should be placed at
   * @param pSourceType     File type of the file to convert
   * @param pTargetType     File type of the final file
   * @param pParams         Parameters for the conversion
   * @return conversion result, or null
   */
  @Nullable
  default Object convert(@NotNull File pSourceLocation, @NotNull File pTargetLocation, @NotNull String pSourceType, @NotNull String pTargetType,
                         @NotNull Map<Object, Object> pParams) throws IOException
  {
    return convert(new FileReader(pSourceLocation), new FileWriter(pTargetLocation), pSourceType, pTargetType, pParams);
  }

  /**
   * Converts the source file to the specified type at the target location
   *
   * @param pSource     Reader for the input source
   * @param pTarget     Write for the output
   * @param pSourceType File type of the file to convert
   * @param pTargetType File type of the final file
   * @param pParams     Parameters for the conversion
   * @return conversion result, or null
   */
  @Nullable
  Object convert(@NotNull Reader pSource, @NotNull Writer pTarget, @NotNull String pSourceType, @NotNull String pTargetType,
                 @NotNull Map<Object, Object> pParams) throws IOException;

}
