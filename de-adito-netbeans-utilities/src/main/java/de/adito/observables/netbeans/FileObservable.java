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
  public static Observable<Optional<File>> create(@NotNull File pFile)
  {
    return Observables.create(new FileObservable(pFile), () -> _getValue(pFile))
        .observeOn(Schedulers.io())
        .subscribeOn(Schedulers.io());
  }

  @NotNull
  @Override
  protected FileChangeListener registerListener(@NotNull File pFile, @NotNull IFireable<Optional<File>> pFireable)
  {
    FileChangeListener fcl = new _FileListenerImpl(pFireable, pFile);
    FileUtil.addRecursiveListener(fcl, pFile);
    return fcl;
  }

  @Override
  protected void removeListener(@NotNull File pFile, @NotNull FileChangeListener pFileChangeListener)
  {
    FileUtil.removeRecursiveListener(pFileChangeListener, pFile);
  }

  @NotNull
  private static Optional<File> _getValue(@NotNull File pFile)
  {
    if(pFile.exists())
      return Optional.of(pFile);
    return Optional.empty();
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
