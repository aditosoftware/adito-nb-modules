package org.netbeans.modules.masterfs.watcher;

import org.jetbrains.annotations.*;
import org.netbeans.modules.masterfs.filebasedfs.fileobjects.FileObjectFactory;
import org.netbeans.modules.masterfs.providers.Notifier;
import org.openide.filesystems.*;
import org.openide.util.*;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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
   * @param pKeyRefProvider    All Refs, that are currently watched
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
    _SymlinkCache cache = new _SymlinkCache();

    try
    {
      String pathToFire = fo == null ? pChangedFile.getAbsolutePath() : fo.getPath();
      AtomicReference<Set<NotifierKeyRef>> savedRefs = new AtomicReference<>();
      pKeyRefProvider.accept(pRefs -> savedRefs.set(new HashSet<>(pRefs)));
      for (NotifierKeyRef<?> watchedRef : savedRefs.get())
      {
        FileObject refFo = watchedRef.get();
        if (refFo != null)
        {
          FileObject symlinkTarget = _readSymbolicLinkTraverseParents(refFo, cache);
          if (symlinkTarget != null && Objects.equals(pathToFire, symlinkTarget.getPath()))
            toRefresh.add(refFo);
        }
      }
    }
    catch (Throwable e)
    {
      Watcher.LOG.log(Level.WARNING, "", e);
    }
    finally
    {
      cache.invalidate();
    }

    return toRefresh;
  }

  /**
   * Reads the target of the symlink which pFo references.
   * This method tries to resolve the whole symlink path of pFo, so
   * if a parent of pFo is a symlink, it gets resolved too.
   *
   * @param pFo FileObject to check
   * @return the target
   */
  @Nullable
  private static FileObject _readSymbolicLinkTraverseParents(@NotNull FileObject pFo, @NotNull ISymlinkCache pCache)
  {
    FileObject fo = pFo;
    while (fo != null)
    {
      String relativeInLink = FileUtil.getRelativePath(fo, pFo);
      FileObject realFo = pCache.readSymbolicLink(fo);
      if(realFo != null)
        return realFo.getFileObject(relativeInLink);

      fo = fo.getParent();
    }

    return null;
  }

  interface ISymlinkCache
  {
    /**
     * Reads the target of the symlink which pFo references.
     * This has to be done a little weird, because of NTFS "Junction" links.
     * Those are symlinks too, but {@link FileObject#isSymbolicLink()} returns false, because
     * symlinks are handled different in NTFS. So {@link FileObject#readSymbolicLink()} will throw an exception,
     * because NetBeans thinks, that they are no symbolic links...
     *
     * @param pFo FileObject to check
     * @return the target
     */
    @Nullable
    FileObject readSymbolicLink(@NotNull FileObject pFo);

    /**
     * Invalidates the cache and all of its entries
     */
    void invalidate();
  }

  /**
   * ISymlinkCache-Impl
   */
  private static class _SymlinkCache implements ISymlinkCache
  {
    private final Map<FileObject, Optional<FileObject>> internalCache = new ConcurrentHashMap<>(10000);

    @Nullable
    @Override
    public FileObject readSymbolicLink(@NotNull FileObject pFo)
    {
      return internalCache.computeIfAbsent(pFo, pPair -> {
        try
        {
          // Specialhandling: NTFS - only on Windows
          if (BaseUtilities.isWindows())
            return Optional.ofNullable(FileUtil.toFileObject(new File(ADITOLinkWindowsNatives.readJunctionLink(pFo.getPath()))));
        }
        catch (Throwable e)
        {
          // fallback - catch everything because the native code can throw "errors" too.
        }

        try
        {
          return Optional.ofNullable(pFo.readSymbolicLink());
        }
        catch (Throwable e)
        {
          // no symbolic link detected
          return Optional.empty();
        }
      }).orElse(null);
    }

    @Override
    public void invalidate()
    {
      internalCache.clear();
    }
  }


}
