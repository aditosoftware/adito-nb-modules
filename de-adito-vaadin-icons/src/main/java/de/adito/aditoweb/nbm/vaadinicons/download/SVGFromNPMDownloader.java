package de.adito.aditoweb.nbm.vaadinicons.download;

import org.apache.commons.compress.archivers.tar.*;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.*;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;
import java.util.logging.*;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

/**
 * Extracts the version of the npm module that the vaadin Icon class uses for retrieving the SVG files, downloads those files and then writes
 * them to disk with the following modifications:
 * the color attributes in the path is replaced by COLOR_PLACEHOLDER
 * the height attribute of the svg is replaced by HEIGHT_PLACEHOLDER
 * the width attribute of the svg is replaced by WIDTH_PLACEHOLDER
 *
 * these modifications can then be used to modifiy the SVG according to given IconAttributes
 *
 * @author m.kaspera, 09.03.2022
 */
public class SVGFromNPMDownloader
{

  public static final String COLOR_PLACEHOLDER = "$$$COLOR$$$";
  public static final String HEIGHT_PLACEHOLDER = "$$$HEIGHT$$$";
  public static final String WIDTH_PLACEHOLDER = "$$$WIDTH$$$";

  public static final String GROUP_ID = "@vaadin";
  public static final String ARTIFACT_ID = "icons";

  public static final String SVG_DEST_FOLDER_NAME = "svg_templates";

  private static final String ICON_CLASS_FULL_NAME = "com.vaadin.flow.component.icon.Icon";
  private static final String NPM_PACKAGE_CONTAINER_CLASS_FULL_NAME = "com.vaadin.flow.component.dependency.NpmPackage$Container";
  private static final String NPM_PACKAGE_CLASS_FULL_NAME = "com.vaadin.flow.component.dependency.NpmPackage";
  private static final String SVG_ICONS_FILE = "package/vaadin-iconset.js";
  private static final String SVG_CONTENT_START_MARKER = "<svg><defs>";
  private static final String SVG_CONTENT_END_MARKER = "</defs></svg>";
  private static final String SVG_PRE_NAME_CONTENT = "<g id=\"vaadin:";
  private static final String SVG_PATH_START_MARKER = "<path";
  private static final String SVG_PATHS_START_MARKER = "\">" + SVG_PATH_START_MARKER;
  private static final String SVG_PATHS_END_MARKER = "</g>";

  private static final String SVG_DEST_FOLDER_PATH = "target/classes/de/adito/aditoweb/nbm/vaadinicons/" + SVG_DEST_FOLDER_NAME;

  private static final String SVG_CONTENT_PREFIX = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
      "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n" +
      "<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"" + WIDTH_PLACEHOLDER +
      "\" height=\"" + HEIGHT_PLACEHOLDER + "\" viewBox=\"0 0 16 16\">\n";
  private static final String SVG_CONTENT_SUFFIX = "\n</svg>";

  private static final Logger LOGGER = Logger.getLogger(SVGFromNPMDownloader.class.getName());

  public static void main(String[] args)
  {
    String version = getModuleVersionNumber();
    if (version != null)
    {
      String downloadUrl = getNPMDownloadUrl(version);
      downloadSVGs(downloadUrl);
    }
  }

  /**
   * Download the npm module, extract the contained SVG files and write them to disk
   *
   * @param downloadUrl URL used to download the NPM module
   */
  private static void downloadSVGs(@NotNull String downloadUrl)
  {
    try (TarArchiveInputStream in = new TarArchiveInputStream(new GZIPInputStream(new BufferedInputStream(new URL(downloadUrl).openStream()))))
    {
      TarArchiveEntry entry;
      while ((entry = in.getNextTarEntry()) != null)
      {
        if (entry.isDirectory() || !entry.getName().equals(SVG_ICONS_FILE))
        {
          continue;
        }
        String iconsetString = IOUtils.toString(in);
        int startIndex = Math.min(iconsetString.length(), iconsetString.indexOf(SVG_CONTENT_START_MARKER) + SVG_CONTENT_START_MARKER.length());
        int endIndex = Math.min(iconsetString.length(), iconsetString.indexOf(SVG_CONTENT_END_MARKER));
        String svgs = iconsetString.substring(Math.max(0, startIndex), Math.max(0, endIndex));
        String[] svgStrings = svgs.replace("\r", "").split("\n");
        Arrays.stream(svgStrings)
            .filter(pS -> !pS.isBlank())
            .forEach(SVGFromNPMDownloader::writeSvgFile);
      }
    }
    catch (IOException pE)
    {
      LOGGER.log(Level.WARNING, "Error while downloading the npm module with url " + downloadUrl, pE);
    }
  }

  /**
   * Generate the download url for a npm artifact, based on the group/artifactId and the version
   *
   * @param version version of the npm artifact
   * @return String of the adress of the npm artifact that can be used to download the artifact
   */
  @NotNull
  private static String getNPMDownloadUrl(@NotNull String version)
  {
    return "https://registry.npmjs.org/" + GROUP_ID + "/" + ARTIFACT_ID + "/-/" + ARTIFACT_ID + "-" + version + ".tgz";
  }

  /**
   * Extract the version number used for getting the icons by the Icon class of Vaadin
   *
   * @return version number of the vaadin icon npm package
   */
  @Nullable
  static String getModuleVersionNumber()
  {
    try
    {
      Class<?> iconClass = Class.forName(ICON_CLASS_FULL_NAME);
      Annotation[] annotations = iconClass.getDeclaredAnnotations();
      Optional<Annotation> npmContainer = Arrays.stream(annotations)
          .filter(pAnnotation -> NPM_PACKAGE_CONTAINER_CLASS_FULL_NAME.equals(pAnnotation.annotationType().getName()))
          .findFirst();
      if (npmContainer.isPresent())
      {
        Object[] npmModules = (Object[]) Class.forName(NPM_PACKAGE_CONTAINER_CLASS_FULL_NAME).getDeclaredMethod("value").invoke(npmContainer.get());
        for (Object npmModule : npmModules)
        {
          Class<?> npmPackage = Class.forName(NPM_PACKAGE_CLASS_FULL_NAME);
          String moduleName = (String) npmPackage.getDeclaredMethod("value").invoke(npmModule);
          if ((GROUP_ID + "/" + ARTIFACT_ID).equals(moduleName))
          {
            return (String) npmPackage.getDeclaredMethod("version").invoke(npmModule);
          }
        }
      }
    }
    catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | NoSuchMethodException pE)
    {
      LOGGER.log(Level.SEVERE, "Error while parsing the module version number for npm module " + GROUP_ID + "-" + ARTIFACT_ID + " from the vaadin Icon class", pE);
    }
    return null;
  }

  /**
   * Parse the name of the SVG from a line of the JS Code. One line should contain exactly one SVG
   *
   * @param pSvgLine line describing a SVG
   * @return the name used for the SVG
   */
  @Nullable
  static String getSvgName(@NotNull String pSvgLine)
  {
    int startIndex = Math.min(pSvgLine.length(), pSvgLine.indexOf(SVG_PRE_NAME_CONTENT) + SVG_PRE_NAME_CONTENT.length());
    int endIndex = Math.min(pSvgLine.length(), pSvgLine.indexOf("\">", startIndex));
    if (startIndex > 0 && endIndex > 0)
    {
      String nonAdjustedFilename = pSvgLine.substring(startIndex, endIndex) + ".svg";
      // before returning the file name replace the - with _ in order to match the file names to the enum names later on
      return nonAdjustedFilename.replace("-", "_");
    }
    return null;
  }

  /**
   * Extract the Paths that make up the contents of an SVG from a line of the JS Code in the NPM module. One line should contain exactly one SVG
   *
   * @param pSvgLine line describing a SVG
   * @return paths that describe the SVG
   */
  @NotNull
  private static String getSvgPaths(@NotNull String pSvgLine)
  {

    int startIndex = Math.min(pSvgLine.length(), pSvgLine.indexOf(SVG_PATHS_START_MARKER) + "\">".length());
    int endIndex = Math.min(pSvgLine.length(), pSvgLine.indexOf(SVG_PATHS_END_MARKER));
    return pSvgLine.substring(Math.max(0, startIndex), Math.max(0, endIndex));
  }

  /**
   * Combine the SVG Paths with the meta info necessary to make a well-formed .svg file
   *
   * @param pSvgPaths paths that describe the SVG
   * @return contents of a .svg file containing the path info given as string
   */
  @NotNull
  private static String getSvgFileContents(@NotNull String pSvgPaths)
  {
    return SVG_CONTENT_PREFIX + pSvgPaths + SVG_CONTENT_SUFFIX;
  }

  /**
   * Insert the color and size placeholders that are used later on to modify the color and size of the SVG according to IconAttributes
   *
   * @param pSvgPaths Paths that describe the look of the SVG
   * @return String that includes the paths and the placeholders
   */
  static String addPlaceholders(@NotNull String pSvgPaths)
  {
    if (!pSvgPaths.contains(SVG_PATH_START_MARKER))
      return pSvgPaths;
    return Arrays.stream(pSvgPaths.split(SVG_PATH_START_MARKER))
        .filter(pS -> !pS.isBlank())
        .map(pS -> SVG_PATH_START_MARKER + " fill=\"#" + SVGFromNPMDownloader.COLOR_PLACEHOLDER + "\"" + pS)
        .collect(Collectors.joining("\n"));
  }

  /**
   * Write the given contents of the svg file to disk
   *
   * @param pSvgLine contents of the svg file that should be written to disk
   */
  private static void writeSvgFile(@NotNull String pSvgLine)
  {
    String fileName = getSvgName(pSvgLine);
    File folder = new File(SVG_DEST_FOLDER_PATH);
    if (!folder.exists())
      folder.mkdirs();
    if (fileName != null)
    {
      File svgFile = new File(folder, fileName);
      try (FileOutputStream fileOutputStream = new FileOutputStream(svgFile, true))
      {
        IOUtils.write(getSvgFileContents(addPlaceholders(getSvgPaths(pSvgLine))), fileOutputStream);
      }
      catch (IOException pE)
      {
        LOGGER.log(Level.SEVERE, "Error while writing an SVG file to disk", pE);
      }
    }
  }

}
