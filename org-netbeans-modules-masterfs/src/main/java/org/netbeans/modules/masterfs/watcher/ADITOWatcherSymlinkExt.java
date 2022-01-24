package org.netbeans.modules.masterfs.watcher;

import org.jetbrains.annotations.*;
import org.netbeans.modules.masterfs.filebasedfs.fileobjects.FileObjectFactory;
import org.netbeans.modules.masterfs.providers.Notifier;
import org.openide.filesystems.*;
import org.openide.util.BaseUtilities;

import java.io.File;
import java.util.*;
import java.util.function.*;

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
   * @param pChangedFile         File that changed
   * @param pFileObjectFactory   Factory to retrieve FileObjects
   * @param pWatchedRefs         All Refs, that are currently watched
   * @param pNotifier            Notifier
   * @param pExecuteSynchronized Wraps the passed runnable in a synchronized block
   * @return a set of FileObjects that have to be recalculcated / refreshed, because they somehow belong to pChangedFile
   */
  @NotNull
  public static <KEY> Set<FileObject> getAllReferences(@NotNull File pChangedFile, @NotNull FileObjectFactory pFileObjectFactory,
                                                       @NotNull Supplier<Map<FileObject, Set<NotifierKeyRef>>> pWatchedRefs, @Nullable Notifier<KEY> pNotifier,
                                                       @NotNull Consumer<Runnable> pExecuteSynchronized)
  {
    // NetBeans Original
    Set<FileObject> toRefresh = new HashSet<>();
    Map<FileObject, Set<NotifierKeyRef>> watchedRefs = new HashMap<>();
    final FileObject[] fo = new FileObject[1];
    fo[0] = pFileObjectFactory.getCachedOnly(pChangedFile);
    pExecuteSynchronized.accept(() -> watchedRefs.putAll(pWatchedRefs.get()));
    if (fo[0] == null || fo[0].isData())
      fo[0] = pFileObjectFactory.getCachedOnly(pChangedFile.getParentFile());

    if (fo[0] != null)
    {
      pExecuteSynchronized.accept(() -> {
        NotifierKeyRef<?> kr = new NotifierKeyRef<>(fo[0], null, null, pNotifier);
        // ADITO: Retrieve all symlinked files
        Optional.ofNullable(watchedRefs.get(kr.get()))
            .ifPresent(pSet -> pSet
                .forEach(pNotifierKeyRef -> {
                  toRefresh.add(pNotifierKeyRef.getSymlinkRealTargetLink());
                  if (Optional.ofNullable(pNotifierKeyRef.get()).map(pFo -> !pFo.equals(pNotifierKeyRef.getSymlinkRealTargetLink())).orElse(false))
                    toRefresh.add(pNotifierKeyRef.get());
                }));
      });
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
  static boolean isSymbolicLinkRecursive(@Nullable FileObject pFo) throws Exception
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
  static FileObject readSymbolicLinkRecursive(@NotNull FileObject pFo) throws Exception
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
