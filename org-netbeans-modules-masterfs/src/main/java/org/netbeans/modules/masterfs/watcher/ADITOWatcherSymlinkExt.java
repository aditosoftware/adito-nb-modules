package org.netbeans.modules.masterfs.watcher;

import org.jetbrains.annotations.*;
import org.netbeans.modules.masterfs.filebasedfs.fileobjects.FileObjectFactory;
import org.netbeans.modules.masterfs.providers.Notifier;
import org.openide.filesystems.*;

import java.io.File;
import java.util.*;

/**
 * @author w.glanzer, 16.12.2021
 */
class ADITOWatcherSymlinkExt
{

  public static <KEY> Set<FileObject> getAllReferences(@NotNull File pChangedFile, @NotNull FileObjectFactory pFileObjectFactory,
                                                       @NotNull Set<NotifierKeyRef> pWatchedRefs, @NotNull Notifier<KEY> pNotifier)
  {
    Set<FileObject> toRefresh = new HashSet<>();
    FileObject fo = pFileObjectFactory.getCachedOnly(pChangedFile);
    if (fo == null || fo.isData())
      fo = pFileObjectFactory.getCachedOnly(pChangedFile.getParentFile());

    if (fo != null)
    {
      NotifierKeyRef<?> kr = new NotifierKeyRef<>(fo, null, null, pNotifier);
      if (pWatchedRefs.contains(kr))
        toRefresh.add(fo);

      //todo the "fo" does not have to be cached explicitly - there may be only symlinks without fo
      try
      {
        for (NotifierKeyRef watchedRef : pWatchedRefs)
        {
          FileObject refFo = watchedRef.get();
          if (refFo != null && _isSymbolicLinkRecursive(refFo)) //only check links
          {
            FileObject symlink = _readSymbolicLinkRecursive(refFo);
            if (Objects.equals(fo, symlink))
              toRefresh.add(refFo);
          }
        }
      }
      catch (Exception e)
      {
        e.printStackTrace(); //todo
      }
    }

    return toRefresh;
  }

  private static FileObject _readSymbolicLinkRecursive(@NotNull FileObject pFo) throws Exception
  {
    FileObject fo = pFo;
    while (fo != null)
    {
      if (_isSymbolicLink(fo))
      {
        String relativeInLink = FileUtil.getRelativePath(fo, pFo);
        FileObject realFo = _readSymbolicLink(fo);
        return realFo.getFileObject(relativeInLink);
      }

      fo = fo.getParent();
    }

    throw new NullPointerException(pFo.toString());
  }

  private static boolean _isSymbolicLinkRecursive(@Nullable FileObject pFo) throws Exception
  {
    FileObject fo = pFo;
    while (fo != null)
    {
      if (_isSymbolicLink(fo))
        return true;
      fo = fo.getParent();
    }
    return false;
  }

  private static boolean _isSymbolicLink(@NotNull FileObject pFo) throws Exception
  {
    return pFo.isSymbolicLink();
  }

  private static FileObject _readSymbolicLink(@NotNull FileObject pFo) throws Exception
  {
    return pFo.readSymbolicLink();
  }

}
