package org.netbeans.modules.masterfs.watcher;

import org.jetbrains.annotations.*;
import org.netbeans.api.queries.VisibilityQuery;
import org.openide.filesystems.*;
import org.openide.util.*;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;
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

  private static final ISymlinkCache _SYMLINK_CACHE = new _SymlinkCache();
  private static final ILastModifiedCache _LASTMODIFIED_CACHE = new _LastModifiedCache();
  private static final ExecutorService _SYMLINK_CALCULATION_POOL = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), r -> {
    Thread t = Executors.defaultThreadFactory().newThread(r);
    t.setName("tSymlinkCalculationThread");
    return t;
  });

  /**
   * Returns all references that have to be refreshed, after pChangedFile changed.
   * This method tries to handle symbolic links too.
   *
   * @param pFileObject        File that changed
   * @param pKeyRefProvider    All Refs, that are currently watched
   * @return a set of FileObjects that have to be recalculcated / refreshed, because they somehow belong to pChangedFile
   */
  @NotNull
  public static Set<FileObject> getAllReferences(@NotNull FileObject pFileObject, @NotNull Consumer<Consumer<Set<NotifierKeyRef>>> pKeyRefProvider)
  {
    // a bit hacky, but should work in our current situation
    // we just want to exclude our "dist" directory from this calculation,
    // because we knew that there is nothing referenced.
    // If there is any problem with this, it can be removed - but think of our "dist" directory!
    if(!VisibilityQuery.getDefault().isVisible(pFileObject))
      return Set.of();

    // Invalidate the lastModified of the given fileobject, because we knew, pFileObject was changed now
    _LASTMODIFIED_CACHE.invalidate(pFileObject);

    try
    {
      // Check symlinks
      Set<FileObject> toRefresh = new HashSet<>();
      String pathToFire = pFileObject.getPath();
      AtomicReference<Set<NotifierKeyRef>> savedRefs = new AtomicReference<>();
      pKeyRefProvider.accept(pRefs -> savedRefs.set(new HashSet<>(pRefs)));
      CompletableFuture.allOf(savedRefs.get().stream()
                                  .map(pRef -> CompletableFuture.runAsync(() -> {
                                    FileObject refFo = pRef.get();
                                    if (refFo != null)
                                    {
                                      FileObject symlinkTarget = _SYMLINK_CACHE.readSymbolicLink(_LASTMODIFIED_CACHE, refFo);
                                      if (symlinkTarget != null && Objects.equals(pathToFire, symlinkTarget.getPath()))
                                        toRefresh.add(refFo);
                                    }
                                  }, _SYMLINK_CALCULATION_POOL))
                                  .toArray(CompletableFuture[]::new)).get();
      return toRefresh;
    }
    catch (Throwable e)
    {
      Watcher.LOG.log(Level.WARNING, "", e);
    }

    return Set.of();
  }

  /**
   * Reads the target of the symlink which pFo references.
   * This has to be done a little weird, because of NTFS "Junction" links.
   * Those are symlinks too, but {@link FileObject#isSymbolicLink()} returns false, because
   * symlinks are handled different in NTFS. So {@link FileObject#readSymbolicLink()} will throw an exception,
   * because NetBeans thinks, that they are no symbolic links...
   *
   * @param pFileObject FileObject to check
   * @return the target
   */
  @Nullable
  public static FileObject readSymbolicLink(@NotNull FileObject pFileObject)
  {
    return _SYMLINK_CACHE.readSymbolicLink(new _LastModifiedCache(), pFileObject);
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
    FileObject readSymbolicLink(@NotNull ILastModifiedCache pLastModifiedCache, @NotNull FileObject pFo);
  }

  interface ILastModifiedCache
  {
    /**
     * Determines the date, when pFo was last modified
     *
     * @param pFo FileObject to check
     * @return the lastModified timestamp
     */
    @NotNull
    Date lastModified(@NotNull FileObject pFo);

    /**
     * Invalidates the lastModified entry of the given FileObject
     *
     * @param pFo FileObject to invalidate
     */
    void invalidate(@NotNull FileObject pFo);
  }

  /**
   * ISymlinkCache-Impl
   */
  private static class _SymlinkCache implements ISymlinkCache
  {
    // key: symlink; value: Pair (symlink-target, lastmodified symlink)
    private final Map<FileObject, Pair<Optional<FileObject>, Date>> internalCache = new ConcurrentHashMap<>(20000);

    @Nullable
    @Override
    public FileObject readSymbolicLink(@NotNull ILastModifiedCache pLastModifiedCache, @NotNull FileObject pFo)
    {
      FileObject fo = pFo;
      while (fo != null)
      {
        Date foLastModified = pLastModifiedCache.lastModified(fo);
        Pair<Optional<FileObject>, Date> cachedData = internalCache.get(fo);

        // was calculated before and nothing has changed since calculation
        if (cachedData != null && Objects.equals(cachedData.second(), foLastModified))
          return cachedData.first().orElse(null); //todo refresh if null? refresh to check if null now?

        String relativeInLink = FileUtil.getRelativePath(fo, pFo);
        FileObject realFo = _readSymbolicLink(fo);

        // put back in cache
        internalCache.put(fo, Pair.of(Optional.ofNullable(realFo), foLastModified));
        if (realFo != null)
          return realFo.getFileObject(relativeInLink);

        fo = fo.getParent();
      }

      return null;
    }

    @Nullable
    private FileObject _readSymbolicLink(@NotNull FileObject pFo)
    {
      try
      {
        // Specialhandling: NTFS - only on Windows
        if (BaseUtilities.isWindows())
          return FileUtil.toFileObject(new File(ADITOLinkWindowsNatives.readJunctionLink(pFo.getPath())));
      }
      catch (Throwable e)
      {
        // fallback - catch everything because the native code can throw "errors" too.
      }

      try
      {
        return pFo.readSymbolicLink();
      }
      catch (Throwable e)
      {
        // no symbolic link detected
        return null;
      }
    }
  }

  /**
   * ILastModifiedCache-Impl
   */
  private static class _LastModifiedCache implements ILastModifiedCache
  {
    private final Map<FileObject, Date> internalCache = new ConcurrentHashMap<>(20000);

    @NotNull
    @Override
    public Date lastModified(@NotNull FileObject pFo)
    {
      Date lastModified = internalCache.get(pFo);
      if(lastModified == null)
      {
        lastModified = pFo.lastModified();
        internalCache.put(pFo, lastModified);
      }

      return lastModified;
    }

    @Override
    public void invalidate(@NotNull FileObject pFo)
    {
      internalCache.remove(pFo);
    }
  }


}
