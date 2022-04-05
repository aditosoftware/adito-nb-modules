package de.adito.observables.netbeans;

import de.adito.util.reactive.*;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.jetbrains.annotations.NotNull;
import org.openide.filesystems.*;

import java.io.File;
import java.util.Optional;

/**
 * @author w.glanzer, 05.08.2020
 */
public class FileObservable extends AbstractListenerObservable<FileChangeListener, File, Optional<File>>
{

  private FileObservable(@NotNull File pListenableValue)
  {
    super(pListenableValue);
  }

  /**
   * Creates a new observable that contains the file object, if the given file/folder exists.
   * Returns an empty optional, if it does not.
   *
   * @param pFile File to check
   * @return Observable
   */
  @NotNull
  @Deprecated
  public static Observable<Optional<File>> create(@NotNull File pFile)
  {
    return createForDirectory(pFile);
  }

  /**
   * Creates a new observable that contains the file object, if the given folder exists.
   * Returns an empty optional, if it does not.
   *
   * @param pFile File to check
   * @return Observable
   */
  @NotNull
  public static Observable<Optional<File>> createForDirectory(@NotNull File pFile)
  {
    return Observables.create(new FileObservable(pFile), () -> _getValue(pFile))
        .observeOn(Schedulers.io())
        .subscribeOn(Schedulers.io());
  }

  /**
   * Creates a new observable that contains the file object, if the given file exists.
   * Returns an empty optional, if it does not.
   *
   * @param pFile File to check
   * @return Observable
   */
  @NotNull
  public static Observable<Optional<File>> createForPlainFile(@NotNull File pFile)
  {
    return createForDirectory(pFile.getParentFile())

        // determine, if File is available in parent and distinct it afterwards, so we will only be triggered initially and on file creation / deletion
        .map(pParent -> _getValue(pFile))
        .distinctUntilChanged()

        // handle file changes, if pMyFileOpt is available in parent
        .switchMap(pMyFileOpt -> pMyFileOpt
            .map(pMyFile -> Observables.create(new _NonRecursiveFileObservable(pMyFile), () -> _getValue(pMyFile)))
            .orElseGet(() -> Observable.just(Optional.empty())))

        // distinct, if file has not really changed
        .map(pFileOpt -> pFileOpt
            .map(File::lastModified)
            .orElse(-1L))
        .distinctUntilChanged()
        .map(pL -> pL == -1 ? Optional.empty() : _getValue(pFile));
  }

  @NotNull
  @Override
  protected FileChangeListener registerListener(@NotNull File pFile, @NotNull IFireable<Optional<File>> pFireable)
  {
    FileChangeListener fcl = new _FileListenerImpl(pFireable, pFile);
    FileUtil.addRecursiveListener(fcl, FileUtil.normalizeFile(pFile));
    return fcl;
  }

  @Override
  protected void removeListener(@NotNull File pFile, @NotNull FileChangeListener pFileChangeListener)
  {
    try
    {
      FileUtil.removeRecursiveListener(pFileChangeListener, FileUtil.normalizeFile(pFile));
    }
    catch(IllegalArgumentException iae)
    {
      // ignore
    }
  }

  @NotNull
  private static Optional<File> _getValue(@NotNull File pFile)
  {
    if(pFile.exists())
      return Optional.of(pFile);
    return Optional.empty();
  }

  /**
   * FileObservable, but not recursive
   */
  private static class _NonRecursiveFileObservable extends AbstractListenerObservable<FileChangeListener, File, Optional<File>>
  {
    public _NonRecursiveFileObservable(@NotNull File pListenableValue)
    {
      super(pListenableValue);
    }

    @NotNull
    @Override
    protected FileChangeListener registerListener(@NotNull File pFile, @NotNull IFireable<Optional<File>> pFireable)
    {
      FileChangeListener fcl = new _FileListenerImpl(pFireable, pFile);
      FileUtil.addFileChangeListener(fcl, FileUtil.normalizeFile(pFile));
      return fcl;
    }

    @Override
    protected void removeListener(@NotNull File pFile, @NotNull FileChangeListener pFileChangeListener)
    {
      try
      {
        FileUtil.removeFileChangeListener(pFileChangeListener, FileUtil.normalizeFile(pFile));
      }
      catch(IllegalArgumentException iae)
      {
        // ignore
      }
    }
  }

  private static class _FileListenerImpl implements FileChangeListener
  {
    private final IFireable<Optional<File>> fireable;
    private final File file;

    _FileListenerImpl(@NotNull IFireable<Optional<File>> pFireable, @NotNull File pFile)
    {
      fireable = pFireable;
      file = pFile;
    }

    @Override
    public void fileFolderCreated(FileEvent fe)
    {
      fireable.fireValueChanged(_getValue(file));
    }

    @Override
    public void fileDataCreated(FileEvent fe)
    {
      fireable.fireValueChanged(_getValue(file));
    }

    @Override
    public void fileChanged(FileEvent fe)
    {
      fireable.fireValueChanged(_getValue(file));
    }

    @Override
    public void fileDeleted(FileEvent fe)
    {
      fireable.fireValueChanged(_getValue(file));
    }

    @Override
    public void fileRenamed(FileRenameEvent fe)
    {
      fireable.fireValueChanged(_getValue(file));
    }

    @Override
    public void fileAttributeChanged(FileAttributeEvent fe)
    {
      fireable.fireValueChanged(_getValue(file));
    }
  }
}
