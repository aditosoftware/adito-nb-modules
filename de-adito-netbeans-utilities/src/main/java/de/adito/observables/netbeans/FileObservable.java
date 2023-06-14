package de.adito.observables.netbeans;

import de.adito.util.reactive.*;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.NonNull;
import org.openide.filesystems.*;

import java.io.File;
import java.util.Optional;

/**
 * @author w.glanzer, 05.08.2020
 */
public class FileObservable extends AbstractListenerObservable<FileChangeListener, File, Optional<File>>
{

  private final boolean recurseInto;

  private FileObservable(@NonNull File pListenableValue, boolean pRecurseInto)
  {
    super(pListenableValue);
    recurseInto = pRecurseInto;
  }

  /**
   * Creates a new observable that contains the file object, if the given file/folder exists.
   * Returns an empty optional, if it does not.
   *
   * @param pFile File to check
   * @return Observable
   */
  @NonNull
  @Deprecated
  public static Observable<Optional<File>> create(@NonNull File pFile)
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
  @NonNull
  public static Observable<Optional<File>> createForDirectory(@NonNull File pFile)
  {
    return createForDirectory(pFile, true);
  }

  /**
   * Creates a new observable that contains the file object, if the given folder exists.
   * Returns an empty optional, if it does not.
   * If pFile represents a directory and pRecursiveInto is true, all recursive files events will be fired
   *
   * @param pFile          File to check
   * @param pRecursiveInto true if the observable should trigger if ANY file inside pFile will trigger this observable
   * @return Observable
   */
  @NonNull
  public static Observable<Optional<File>> createForDirectory(@NonNull File pFile, boolean pRecursiveInto)
  {
    return Observables.create(new FileObservable(pFile, pRecursiveInto), () -> _getValue(pFile))
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
  @NonNull
  public static Observable<Optional<File>> createForPlainFile(@NonNull File pFile)
  {
    File parentFile = pFile.getParentFile();
    Observable<Optional<File>> parentObservable;
    if(parentFile != null)
      // determine, if File is available in parent and distinct it afterwards, so we will only be triggered initially and on file creation / deletion
      parentObservable = createForDirectory(parentFile, false)
          .map(pParent -> _getValue(pFile))
          .distinctUntilChanged();
    else
      // we do not own a parent -> just fire our value
      parentObservable = Observable.just(_getValue(pFile));

    return parentObservable
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

  @NonNull
  @Override
  protected FileChangeListener registerListener(@NonNull File pFile, @NonNull IFireable<Optional<File>> pFireable)
  {
    FileChangeListener fcl = new _FileListenerImpl(pFireable, pFile);
    pFile = FileUtil.normalizeFile(pFile);
    if (recurseInto)
      FileUtil.addRecursiveListener(fcl, pFile);
    else
      FileUtil.addFileChangeListener(fcl, pFile);
    return fcl;
  }

  @Override
  protected void removeListener(@NonNull File pFile, @NonNull FileChangeListener pFileChangeListener)
  {
    try
    {
      FileUtil.removeRecursiveListener(pFileChangeListener, FileUtil.normalizeFile(pFile));
    }
    catch (IllegalArgumentException iae)
    {
      // ignore
    }
  }

  @NonNull
  private static Optional<File> _getValue(@NonNull File pFile)
  {
    if (pFile.exists())
      return Optional.of(pFile);
    return Optional.empty();
  }

  /**
   * FileObservable, but not recursive
   */
  private static class _NonRecursiveFileObservable extends AbstractListenerObservable<FileChangeListener, File, Optional<File>>
  {
    public _NonRecursiveFileObservable(@NonNull File pListenableValue)
    {
      super(pListenableValue);
    }

    @NonNull
    @Override
    protected FileChangeListener registerListener(@NonNull File pFile, @NonNull IFireable<Optional<File>> pFireable)
    {
      FileChangeListener fcl = new _FileListenerImpl(pFireable, pFile);
      FileUtil.addFileChangeListener(fcl, FileUtil.normalizeFile(pFile));
      return fcl;
    }

    @Override
    protected void removeListener(@NonNull File pFile, @NonNull FileChangeListener pFileChangeListener)
    {
      try
      {
        FileUtil.removeFileChangeListener(pFileChangeListener, FileUtil.normalizeFile(pFile));
      }
      catch (IllegalArgumentException iae)
      {
        // ignore
      }
    }
  }

  private static class _FileListenerImpl implements FileChangeListener
  {
    private final IFireable<Optional<File>> fireable;
    private final File file;

    _FileListenerImpl(@NonNull IFireable<Optional<File>> pFireable, @NonNull File pFile)
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
