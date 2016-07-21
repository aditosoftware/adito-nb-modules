package org.netbeans.modules.javascript.editing.adito;

import org.jetbrains.annotations.Nullable;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.modules.javascript.editing.JsClassPathProvider;
import org.openide.filesystems.*;
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

  public static final String SYSTEM_LIBS = "system";
  public static final Set<EPacketType> IMPORT_TYPES = new HashSet<>(Arrays.asList(EPacketType.SYSTEM_ADITO, EPacketType.LIBRARY));


  public List<Packet> find(FileObject pContextFileObject)
  {
    return find(pContextFileObject, null);
  }

  public List<Packet> find(FileObject pContextFileObject, Set<EPacketType> pRequestedTypes)
  {
    if (pRequestedTypes == null || pRequestedTypes.isEmpty())
      pRequestedTypes = new HashSet<>(Arrays.asList(EPacketType.values()));

    List<Packet> packets = new ArrayList<>();

    // Internal libs
    ClassPath classPath = ClassPath.getClassPath(pContextFileObject, JsClassPathProvider.BOOT_CP);
    for (FileObject fileObject : classPath.getRoots())
      for (FileObject child : fileObject.getChildren())
      {
        Packet packet = _getPacket(child, pRequestedTypes);
        if (packet != null)
          packets.add(packet);
      }

    if (pRequestedTypes.contains(EPacketType.LIBRARY))
    {
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
              Packet packet = _getPacket(processFo, pRequestedTypes);
              if (packet != null)
                packets.add(packet);
            }
          }
        }
      }
    }

    return packets;
  }

  @Nullable
  public Packet getPacket(FileObject pFileObject)
  {
    return _getPacket(pFileObject, new HashSet<>(Arrays.asList(EPacketType.values())));
  }

  @Nullable
  private Packet _getPacket(FileObject pFileObject, Set<EPacketType> pRequestedTypes)
  {
    if (pFileObject != null)
    {
      String name = pFileObject.getNameExt();
      if (FileUtil.getArchiveFile(pFileObject) != null && name.matches("stub_.+_.+\\.js"))
      {
        if (name.startsWith("stub_adito_"))
        {
          if (pRequestedTypes.contains(EPacketType.SYSTEM_ADITO))
          {
            name = SYSTEM_LIBS + "." + name.substring("stub_adito_".length(), name.length() - ".js".length());
            return new Packet(pFileObject, EPacketType.SYSTEM_ADITO, name, null);
          }
        }
        else
        {
          if (pRequestedTypes.contains(EPacketType.SYSTEM_CORE))
          {
            name = pFileObject.getName();
            return new Packet(pFileObject, EPacketType.SYSTEM_CORE, name, null);
          }
        }
      }
      else if (pRequestedTypes.contains(EPacketType.LIBRARY))
      {
        FileObject processFolder = pFileObject.getParent();
        if (processFolder != null)
        {
          name = processFolder.getNameExt();
          FileObject grandParent = processFolder.getParent();
          if (grandParent != null && grandParent.getNameExt().equals("process"))
          {
            return new Packet(pFileObject, EPacketType.LIBRARY, name, null)
            {
              @Override
              public Image getImage()
              {
                try
                {
                  FileObject processAodFo = processFolder.getFileObject(getName() + ".aod");
                  DataObject dataObject;
                  if (processAodFo == null)
                    dataObject = DataObject.find(pFileObject);
                  else
                    dataObject = DataObject.find(processAodFo);
                  return dataObject.getNodeDelegate().getIcon(BeanInfo.ICON_COLOR_16x16);
                }
                catch (DataObjectNotFoundException pE)
                {
                  return super.getImage();
                }
              }
            };
          }
        }
      }
    }
    return null;
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
