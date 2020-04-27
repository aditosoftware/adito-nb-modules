package de.adito.aditoweb.nbm.nbide.nbaditointerface.actionitems;

import org.jetbrains.annotations.Nullable;
import org.openide.filesystems.FileObject;

import java.net.URL;

/**
 * An ActionItem describes a single error/warning in a specific file
 *
 * @author w.glanzer, 27.04.2020
 */
public interface IActionItem
{
  /**
   * @return Returns the underlying fileobject, or null
   */
  @Nullable
  FileObject getFileObject();

  /**
   * @return URL of the underyling file
   */
  @Nullable
  URL getURL();

  /**
   * @return Description to describe the
   */
  @Nullable
  String getDescription();

  /**
   * @return the line, or -1
   */
  int getLine();

  /**
   * @return group of this item
   */
  @Nullable
  IActionItemGroup getGroup();
}
