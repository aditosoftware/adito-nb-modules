package de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript;

import com.google.common.collect.Multimap;
import org.jetbrains.annotations.NotNull;
import org.openide.util.Lookup;

/**
 * Contains anything about javascript parsing utilities
 *
 * @author w.glanzer, 30.05.2022
 */
public interface IJsParserUtility
{

  /**
   * @return the instance to use
   */
  @NotNull
  static IJsParserUtility getInstance()
  {
    return Lookup.getDefault().lookup(IJsParserUtility.class);
  }

  /**
   * Determines, if the given code snippet contains an import
   *
   * @param pLine Code to check
   * @return true, if the code snippet contains an import
   */
  boolean isImportLine(@NotNull String pLine);

  /**
   * Parses all imports from the given script
   *
   * @param pScript Script to parse imports from. "Main code" can be included.
   * @return all needed imports defined in the given script
   * @throws Exception if parsing failed
   */
  @NotNull
  Multimap<String, String> parseImports(@NotNull String pScript) throws Exception;

  /**
   * Manipulates the script imports, so that the given ones are contained.
   * Imports, that are not specified in the given map but are available in the script won't get deleted.
   * Duplicated imports are ignored.
   *
   * @param pScript  Script to append imports to
   * @param pImports Imports to append
   * @return the modified script
   * @throws Exception if pre-parsing failed
   */
  @NotNull
  String appendImports(@NotNull String pScript, @NotNull Multimap<String, String> pImports) throws Exception;

}
