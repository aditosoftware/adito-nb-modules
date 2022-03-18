package de.adito.aditoweb.nbm.vaadinicons;

import de.adito.aditoweb.files.jar.JarFSUtil;
import de.adito.aditoweb.nbm.vaadinicons.download.*;
import de.adito.swing.icon.IconAttributes;
import org.jetbrains.annotations.*;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author m.kaspera, 14.03.2022
 */
public class VaadinIconLoader
{

  /**
   *
   * @param pIconName Name of the desired icon
   * @param pIconAttributes IconAttributes that give color/rotation/size of the desired image
   * @return BufferedImage of the converted svg file
   * @throws IOException if the file cannot be read
   */
  @Nullable
  public static BufferedImage loadSvg(@NotNull String pIconName, @NotNull IconAttributes pIconAttributes) throws IOException
  {
    return SVGToIconConverter.loadSvg(SVGFromNPMDownloader.SVG_DEST_FOLDER_NAME + "/" + pIconName + ".svg", pIconAttributes);
  }

  /**
   *
   * @return List with the names of all possible icons this loader can load
   */
  @NotNull
  public static List<String> getIconNames()
  {
    URL svgFolderUrl = VaadinIconLoader.class.getResource(SVGFromNPMDownloader.SVG_DEST_FOLDER_NAME);
    if(svgFolderUrl != null)
    return JarFSUtil.listFilesFromInnerJar(svgFolderUrl, pathname -> pathname.getName().endsWith(".svg")).stream()
        .map(Paths::get)
        .map(Path::getFileName)
        .map(Path::toString)
        .collect(Collectors.toList());
    return List.of();
  }
}
