package org.netbeans.modules.javascript.editing.adito;

import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.modules.javascript.editing.JsClassPathProvider;
import org.openide.filesystems.FileObject;
import org.openide.loaders.*;

import java.awt.*;
import java.beans.BeanInfo;
import java.util.*;
import java.util.List;

/**
 * @author j.boesl, 20.07.16
 */
public class AditoLibraryQuery
{

  public List<Packet> find(FileObject pContextFileObject)
  {
    List<Packet> packets = new ArrayList<>();

    // Internal libs
    ClassPath classPath = ClassPath.getClassPath(pContextFileObject, JsClassPathProvider.BOOT_CP);
    for (FileObject fileObject : classPath.getRoots())
    {
      for (FileObject child : fileObject.getChildren())
      {
        String name = child.getNameExt();
        if (name.endsWith(".js"))
        {
          if (name.startsWith("stub_adito_"))
          {
            name = "System." + name.substring("stub_adito_".length(), name.length() - ".js".length());
            packets.add(new Packet(child, EPacketType.SYSTEM_ADITO, name, null));
          }
          else
          {
            name = child.getName();
            packets.add(new Packet(child, EPacketType.SYSTEM_CORE, name, null));
          }
        }
      }
    }

    // Project libs
    classPath = ClassPath.getClassPath(pContextFileObject, JsClassPathProvider.SOURCE_CP);
    for (FileObject fileObject : classPath.getRoots())
    {
      FileObject processesFolder = fileObject.getFileObject("process");
      if (processesFolder != null)
      {
        for (FileObject processFolder : processesFolder.getChildren())
        {
          if (processFolder.isFolder())
          {
            FileObject processFo = processFolder.getFileObject("process.js");
            if (processFo != null)
            {
              String name = processFolder.getName();
              packets.add(new Packet(processFo, EPacketType.LIBRARY, name, null)
              {
                @Override
                public Image getImage()
                {
                  try
                  {
                    FileObject processAodFo = processFolder.getFileObject(name + ".aod");
                    DataObject dataObject;
                    if (processAodFo == null)
                      dataObject = DataObject.find(processFo);
                    else
                      dataObject = DataObject.find(processAodFo);
                    return dataObject.getNodeDelegate().getIcon(BeanInfo.ICON_COLOR_16x16);
                  }
                  catch (DataObjectNotFoundException pE)
                  {
                    return super.getImage();
                  }
                }
              });
            }
          }
        }
      }
    }

    return packets;
  }

  /**
   * Library-Description
   */
  public static class Packet
  {
    private FileObject fileObject;
    private EPacketType type;
    private String name;
    private Image image;

    Packet(FileObject pFileObject, EPacketType pType, String pName, Image pImage)
    {
      fileObject = pFileObject;
      type = pType;
      name = pName;
      image = pImage;
    }

    public FileObject getFileObject()
    {
      return fileObject;
    }

    public EPacketType getType()
    {
      return type;
    }

    public String getName()
    {
      return name;
    }

    public Image getImage()
    {
      return image;
    }
  }

  /**
   * Packet-Type
   */
  public enum EPacketType
  {
    LIBRARY,
    SYSTEM_ADITO,
    SYSTEM_CORE
  }

}
