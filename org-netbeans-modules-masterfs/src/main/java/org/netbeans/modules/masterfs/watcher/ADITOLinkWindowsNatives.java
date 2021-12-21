package org.netbeans.modules.masterfs.watcher;

import org.jetbrains.annotations.NotNull;
import org.openide.util.*;
import sun.misc.Unsafe;

import java.io.*;
import java.lang.reflect.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.*;

/**
 * @author w.glanzer, 17.12.2021
 */
class ADITOLinkWindowsNatives
{

  private static Unsafe _UNSAFE;
  private static Method _IS_REPARSE_POINT;
  private static Method _OPEN_FOR_READATTRIBUTE_ACCESS;
  private static Method _GET_NATIVE_BUFFER;
  private static Method _GET_NATIVE_BUFFER_ADDRESS;
  private static Method _GET_NATIVE_BUFFER_RELEASE;
  private static Method _DEVICEIO_CONTROL_GET_REPARSE_POINT;
  private static Method _CLOSE_HANDLE;
  private static Method _STRIP_PREFIX;

  static
  {
    try
    {
      Class<?> unsafe = Unsafe.class;
      Field theUnsafe = unsafe.getDeclaredField("theUnsafe");
      theUnsafe.setAccessible(true);
      _UNSAFE = (Unsafe) theUnsafe.get(null);

      _IS_REPARSE_POINT = Class.forName("sun.nio.fs.WindowsFileAttributes").getDeclaredMethod("isReparsePoint");
      _IS_REPARSE_POINT.setAccessible(true);

      _OPEN_FOR_READATTRIBUTE_ACCESS = Class.forName("sun.nio.fs.WindowsPath").getDeclaredMethod("openForReadAttributeAccess", boolean.class);
      _OPEN_FOR_READATTRIBUTE_ACCESS.setAccessible(true);

      _GET_NATIVE_BUFFER = Class.forName("sun.nio.fs.NativeBuffers").getDeclaredMethod("getNativeBuffer", int.class);
      _GET_NATIVE_BUFFER.setAccessible(true);

      Class<?> nativeBuffer = Class.forName("sun.nio.fs.NativeBuffer");
      _GET_NATIVE_BUFFER_ADDRESS = nativeBuffer.getDeclaredMethod("address");
      _GET_NATIVE_BUFFER_ADDRESS.setAccessible(true);
      _GET_NATIVE_BUFFER_RELEASE = nativeBuffer.getDeclaredMethod("release");
      _GET_NATIVE_BUFFER_RELEASE.setAccessible(true);

      Class<?> windowsNativeDispatcher = Class.forName("sun.nio.fs.WindowsNativeDispatcher");
      _DEVICEIO_CONTROL_GET_REPARSE_POINT = windowsNativeDispatcher.getDeclaredMethod("DeviceIoControlGetReparsePoint", long.class, long.class, int.class);
      _DEVICEIO_CONTROL_GET_REPARSE_POINT.setAccessible(true);
      _CLOSE_HANDLE = windowsNativeDispatcher.getDeclaredMethod("CloseHandle", long.class);
      _CLOSE_HANDLE.setAccessible(true);

      _STRIP_PREFIX = Class.forName("sun.nio.fs.WindowsLinkSupport").getDeclaredMethod("stripPrefix", String.class);
      _STRIP_PREFIX.setAccessible(true);
    }
    catch (Exception e)
    {
      // only log on Windows, because unix/mac won't have this class.
      if (BaseUtilities.isWindows())
        Logger.getLogger(ADITOWatcherSymlinkExt.class.getName()).log(Level.WARNING, "", e);
    }
  }

  /**
   * Determines, if the given path is a junction link
   *
   * @param pPath path to determine
   * @return true, if it is a junction link
   */
  public static boolean isJunctionLink(@NotNull String pPath) throws Exception
  {
    BasicFileAttributes attributes = Files.readAttributes(new File(pPath).toPath(), BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
    return (boolean) _IS_REPARSE_POINT.invoke(attributes);
  }

  /**
   * Reads the target of the given junction link
   *
   * @param pPath Path to read its target from
   * @return the target
   */
  @NotNull
  public static String readJunctionLink(@NotNull String pPath) throws Exception
  {
    assert _OPEN_FOR_READATTRIBUTE_ACCESS != null && _IS_REPARSE_POINT != null &&
        _GET_NATIVE_BUFFER != null && _GET_NATIVE_BUFFER_ADDRESS != null && _GET_NATIVE_BUFFER_RELEASE != null &&
        _DEVICEIO_CONTROL_GET_REPARSE_POINT != null && _STRIP_PREFIX != null;
    long pathHandle = (long) _OPEN_FOR_READATTRIBUTE_ACCESS.invoke(new File(pPath).toPath(), false);
    int bufferSize = 16 * 1024;
    Object nativeBuffer = _GET_NATIVE_BUFFER.invoke(null, bufferSize);

    try
    {
      // Read all Information into nativeBuffer
      _DEVICEIO_CONTROL_GET_REPARSE_POINT.invoke(null, pathHandle, _GET_NATIVE_BUFFER_ADDRESS.invoke(nativeBuffer), bufferSize);

      // The native buffer should now contain a struct _REPARSE_DATA_BUFFER
      // typedef struct _REPARSE_DATA_BUFFER {
      //     ULONG  ReparseTag;
      //     USHORT  ReparseDataLength;
      //     USHORT  Reserved;
      //     union {
      //         struct {
      //             USHORT  SubstituteNameOffset;
      //             USHORT  SubstituteNameLength;
      //             USHORT  PrintNameOffset;
      //             USHORT  PrintNameLength;
      //             WCHAR  PathBuffer[1];
      //         } SymbolicLinkReparseBuffer;
      //         struct {
      //             USHORT  SubstituteNameOffset;
      //             USHORT  SubstituteNameLength;
      //             USHORT  PrintNameOffset;
      //             USHORT  PrintNameLength;
      //             WCHAR  PathBuffer[1];
      //         } MountPointReparseBuffer;
      //         struct {
      //             UCHAR  DataBuffer[1];
      //         } GenericReparseBuffer;
      //     };
      // } REPARSE_DATA_BUFFER
      //

      int IO_REPARSE_TAG_MOUNTPOINT = 0xA0000003;
      short OFFSETOF_REPARSETAG = 0;
      short OFFSETOF_PATHOFFSET = 8;
      short OFFSETOF_PATHLENGTH = 10;
      short OFFSETOF_MOUNTPOINT_REPARSE_BUFFER = 16;

      long nativeBufferAddress = (long) _GET_NATIVE_BUFFER_ADDRESS.invoke(nativeBuffer);
      int tag = (int) _UNSAFE.getLong(nativeBufferAddress + OFFSETOF_REPARSETAG);
      if (tag != IO_REPARSE_TAG_MOUNTPOINT)
        throw new NotLinkException(pPath, null, "Reparse point is not a mount point -> no junction");

      short nameOffset = _UNSAFE.getShort(nativeBufferAddress + OFFSETOF_PATHOFFSET);
      short nameLengthInBytes = _UNSAFE.getShort(nativeBufferAddress + OFFSETOF_PATHLENGTH);
      if ((nameLengthInBytes % 2) != 0)
        throw new FileSystemException(pPath, null, "link corrupted");

      char[] link = new char[nameLengthInBytes / 2];
      _UNSAFE.copyMemory(null, nativeBufferAddress + OFFSETOF_MOUNTPOINT_REPARSE_BUFFER + nameOffset, link,
                         Unsafe.ARRAY_CHAR_BASE_OFFSET, nameLengthInBytes);

      String target = (String) _STRIP_PREFIX.invoke(null, new String(link));
      if (target.isEmpty())
        throw new IOException(pPath + " link target invalid");
      return target;
    }
    finally
    {
      try
      {
        _GET_NATIVE_BUFFER_RELEASE.invoke(nativeBuffer);
      }
      finally
      {
        _CLOSE_HANDLE.invoke(pathHandle);
      }
    }
  }

}
