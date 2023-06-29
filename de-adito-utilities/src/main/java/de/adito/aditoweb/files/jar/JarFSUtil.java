package de.adito.aditoweb.files.jar;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;
import java.util.stream.Collectors;
import java.util.zip.*;

/**
 * @author m.kaspera, 16.03.2022
 */
public class JarFSUtil
{

  private static final Logger LOGGER = Logger.getLogger(JarFSUtil.class.getName());

  public static List<String> listFilesFromNormalFolder(@NonNull File pFolder, @NonNull FileFilter pFileFilter)
  {
    File[] children = pFolder.listFiles(pFileFilter);
    if (children == null)
      throw new IllegalStateException("Failed to load children from folder " + pFolder);
    //noinspection UnstableApiUsage
    return Arrays.stream(children)
        .map(pFile -> Files.getNameWithoutExtension(pFile.getName()))
        .collect(Collectors.toList());
  }

  /**
   * Liefert alle Blueprints aus einem normalen Ordner
   *
   * @param pFolder Ordner auf der Festplatte
   * @return Map
   */
  @NonNull
  public static Map<String, InputStream> loadBlueprintsFromNormalFolder(@NonNull File pFolder)
  {
    File[] children = pFolder.listFiles((dir, name) -> name.endsWith(".xml")); // rekursiv
    if (children == null)
      throw new IllegalStateException("Failed to load children from folder " + pFolder);

    // Hier können Kinder direkt aus dem Ordner ausgelesen werden
    // Beispielsweise ist das im UnitTest der Fall
    //noinspection UnstableApiUsage
    return Arrays.stream(children)
        .collect(Collectors.toMap(pFile -> Files.getNameWithoutExtension(pFile.getName()),
                                  pFile -> {
                                    try
                                    {
                                      return new FileInputStream(pFile);
                                    }
                                    catch (FileNotFoundException e)
                                    {
                                      throw new RuntimeException(e);
                                    }
                                  }));
  }

  public static List<String> listFilesFromInnerJar(@NonNull URL pJarFile, @NonNull FileFilter pFileFilter)
  {
    // Daten können NICHT direkt aus dem Verzeichnis gelesen werden.
    // Beispielsweise wenn die XML-Dateien innerhalb einer JAR liegen
    String[] split = pJarFile.toExternalForm().split("!");
    if (split.length != 2)
      return listFilesFromNormalFolder(new File(pJarFile.getPath()), pFileFilter);
    return loadFromInnerJar(split, new FileListExtractor(), pFileFilter);
  }


  /**
   * Liefert alle Blueprints aus einer jar Datei
   *
   * @param pJarFile URL zur Jar
   * @return Map
   */
  @NonNull
  @Deprecated
  public static Map<String, InputStream> loadBlueprintsFromInnerJar(@NonNull URL pJarFile)
  {
    return loadBlueprintsFromInnerJar(pJarFile, null);
  }

  /**
   * Liefert alle Blueprints aus einer jar Datei
   *
   * @param pJarFile URL zur Jar
   * @param pResolver Resolver für die Blueprint-Datei-Pfade
   * @return Map
   */
  @NonNull
  public static Map<String, InputStream> loadBlueprintsFromInnerJar(@NonNull URL pJarFile, @Nullable BlueprintResolver pResolver)
  {
    // Daten können NICHT direkt aus dem Verzeichnis gelesen werden.
    // Beispielsweise wenn die XML-Dateien innerhalb einer JAR liegen
    String[] split = pJarFile.toExternalForm().split("!");
    if (split.length != 2)
      return loadBlueprintsFromNormalFolder(new File(pJarFile.getPath()));
    return loadFromInnerJar(split, new FileStreamExtractor(pResolver), pathname -> true);
  }


  /**
   * Liefert alle Blueprints aus einer jar Datei
   *
   * @param pSplitPath aufgesplittete URL des Pfades der Jar
   * @return Map
   */
  @NonNull
  public static <T> T loadFromInnerJar(String @NonNull [] pSplitPath, @NonNull ZipExtractor<T> pZipExtractor, @NonNull FileFilter pFileFilter)
  {
    String pathToJar = pSplitPath[0];
    if (pathToJar.startsWith("jar:file:"))
      pathToJar = pathToJar.substring(9);

    // Decode bspw. %20 zu " "
    pathToJar = URLDecoder.decode(pathToJar, Charsets.UTF_8);

    String pathInJar = pSplitPath[1];
    if (pathInJar.startsWith("/"))
      pathInJar = pathInJar.substring(1);

    try (ZipInputStream zis = new ZipInputStream(new FileInputStream(pathToJar)))
    {
      return pZipExtractor.extractEntries(zis, pathInJar, pFileFilter);
    }
    catch (Exception e)
    {
      throw new RuntimeException("Failed to load blueprints from system folder", e);
    }
  }

  private interface ZipExtractor<T>
  {
    T extractEntries(@NonNull ZipInputStream pZis, @NonNull String pPathInJar, @NonNull FileFilter pFileFilter) throws IOException;
  }

  private static class FileListExtractor implements ZipExtractor<List<String>>
  {

    @Override
    public List<String> extractEntries(@NonNull ZipInputStream pZis, @NonNull String pPathInJar, @NonNull FileFilter pFileFilter) throws IOException
    {
      List<String> result = new ArrayList<>();
      for (ZipEntry entry = pZis.getNextEntry(); entry != null; entry = pZis.getNextEntry())
        if (!entry.isDirectory() && entry.getName().startsWith(pPathInJar) && pFileFilter.accept(new File(entry.getName())))
        {
          //noinspection UnstableApiUsage
          result.add(Files.getNameWithoutExtension(entry.getName()));
        }
      return result;
    }
  }

  private static class FileStreamExtractor implements ZipExtractor<Map<String, InputStream>>
  {
    private final BlueprintResolver extractor;

    public FileStreamExtractor(@Nullable BlueprintResolver pExtractor)
    {
      extractor = pExtractor;
    }

    @Override
    public Map<String, InputStream> extractEntries(@NonNull ZipInputStream pZis, @NonNull String pPathInJar, @NonNull FileFilter pFileFilter) throws IOException
    {
      Map<String, InputStream> result = new HashMap<>();
      for (ZipEntry entry = pZis.getNextEntry(); entry != null; entry = pZis.getNextEntry())
        if (!entry.isDirectory() && entry.getName().startsWith(pPathInJar) && pFileFilter.accept(new File(entry.getName())))
        {
          try
          {
            if (extractor != null)
            {
              //noinspection UnstableApiUsage
              result.put(Files.getNameWithoutExtension(entry.getName()), extractor.loadBlueprint(entry.getName()));
            }
            else
            {
              //noinspection UnstableApiUsage
              result.put(Files.getNameWithoutExtension(entry.getName()), getClass().getResourceAsStream("/" + entry.getName()));
            }
          }
          catch (Exception e)
          {
            LOGGER.log(Level.SEVERE, String.format("Failed to load blueprint %s", entry.getName()), e);
          }
        }
      return result;
    }
  }

  public interface BlueprintResolver
  {
    /**
     * Loads a blueprint
     *
     * @param pPath path to the blueprint file
     * @return the corresponding input stream
     */
    @Nullable
    InputStream loadBlueprint(@NonNull String pPath);
  }
}
