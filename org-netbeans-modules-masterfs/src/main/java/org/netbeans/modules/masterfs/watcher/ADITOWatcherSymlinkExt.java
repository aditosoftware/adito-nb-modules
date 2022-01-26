package org.netbeans.modules.masterfs.watcher;

import org.jetbrains.annotations.*;
import org.netbeans.modules.masterfs.filebasedfs.fileobjects.FileObjectFactory;
import org.netbeans.modules.masterfs.providers.Notifier;
import org.openide.filesystems.*;
import org.openide.util.BaseUtilities;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.logging.Level;

/**
 * Implementes specialhandling for NTFS junction files
 * and "regular" symlink files under linux
 *
 * @author w.glanzer, 16.12.2021
 */
class ADITOWatcherSymlinkExt
{

  /**
   * Returns all references that have to be refreshed, after pChangedFile changed.
   * This method tries to handle symbolic links too.
   *
   * @param pChangedFile       File that changed
   * @param pFileObjectFactory Factory to retrieve FileObjects
   * @param pKeyRefProvider       All Refs, that are currently watched
   * @param pNotifier          Notifier
   * @return a set of FileObjects that have to be recalculcated / refreshed, because they somehow belong to pChangedFile
   */
  @NotNull
  public static <KEY> Set<FileObject> getAllReferences(@NotNull File pChangedFile, @NotNull FileObjectFactory pFileObjectFactory,
                                                       @NotNull Consumer<Consumer<Set<NotifierKeyRef>>> pKeyRefProvider,
                                                       @Nullable Notifier<KEY> pNotifier)
  {
    // NetBeans Original
    Set<FileObject> toRefresh = new HashSet<>();
    FileObject fo = pFileObjectFactory.getCachedOnly(pChangedFile);
    if (fo == null || fo.isData())
      fo = pFileObjectFactory.getCachedOnly(pChangedFile.getParentFile());
    final FileObject finalFo = fo;

    if (fo != null)
      pKeyRefProvider.accept(pRefs -> {
        NotifierKeyRef<?> kr = new NotifierKeyRef<>(finalFo, null, null, pNotifier);
        if (pRefs.contains(kr))
          toRefresh.add(finalFo);
      });

    // ADITO: Retrieve all symlinked files
    try
    {
      String pathToFire = fo == null ? pChangedFile.getAbsolutePath() : fo.getPath();
      AtomicReference<Set<NotifierKeyRef>> savedRefs = new AtomicReference<>();
      pKeyRefProvider.accept(pRefs -> savedRefs.set(new HashSet<>(pRefs)));
      for (NotifierKeyRef<?> watchedRef : savedRefs.get())
      {
        FileObject refFo = watchedRef.get();
        if (refFo != null && _isSymbolicLinkRecursive(refFo))
        {
          FileObject symlinkTarget = _readSymbolicLinkRecursive(refFo);
          if (Objects.equals(pathToFire, symlinkTarget.getPath()))
            toRefresh.add(refFo);
        }
      }
    }
    catch (Exception e)
    {
      // ignore
    }

    return toRefresh;
  }

  /**
   * Determines, if pFo is a symbolic link.
   * This method returns true, even if a parent is a symbolic link.
   *
   * @param pFo FileObject to check
   * @return true if pFo or any of its parents are symlinks
   */
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

  /**
   * Reads the target of the symlink which pFo references.
   * This method tries to resolve the whole symlink path of pFo, so
   * if some parents of pFo are symlinks, they get resolved too.
   *
   * @param pFo FileObject to check
   * @return the target
   */
  @NotNull
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

  /**
   * Determines, if pFo (and only pFo, no parent check!) is a symlink.
   * This has to be done a little weird, because of NTFS "Junction" links.
   * Those are symlinks too, but {@link FileObject#isSymbolicLink()} returns false, because
   * symlinks are handeld different in NTFS.
   *
   * @param pFo FileObject to check
   * @return true, if pFo (and only pFo!) is a kind of symlink
   */
  private static boolean _isSymbolicLink(@NotNull FileObject pFo) throws Exception
  {
    try
    {
      // Specialhandling: NTFS - only on Windows
      if (BaseUtilities.isWindows())
        if (ADITOLinkWindowsNatives.isJunctionLink(pFo.getPath()))
          return true;
    }
    catch (Exception e)
    {
      // fallback
    }

    return pFo.isSymbolicLink();
  }

  /**
   * Reads the target of the symlink which pFo references.
   * This has to be done a little weird, because of NTFS "Junction" links.
   * Those are symlinks too, but {@link FileObject#isSymbolicLink()} returns false, because
   * symlinks are handeld different in NTFS. So {@link FileObject#readSymbolicLink()} will throw an exception,
   * because NetBeans thinks, that they are no symbolic links...
   *
   * @param pFo FileObject to check
   * @return the target
   */
  private static FileObject _readSymbolicLink(@NotNull FileObject pFo) throws Exception
  {
    try
    {
      // Specialhandling: NTFS - only on Windows
      if (BaseUtilities.isWindows())
        if (ADITOLinkWindowsNatives.isJunctionLink(pFo.getPath()))
          return FileUtil.toFileObject(new File(ADITOLinkWindowsNatives.readJunctionLink(pFo.getPath())));
    }
    catch (Exception e)
    {
      // fallback
    }

    return pFo.readSymbolicLink();
  }


}
