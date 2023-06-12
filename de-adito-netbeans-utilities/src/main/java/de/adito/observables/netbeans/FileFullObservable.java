package de.adito.observables.netbeans;

import de.adito.util.reactive.AbstractListenerObservable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.NonNull;
import org.openide.filesystems.*;

import java.io.File;
import java.util.Optional;

/**
 * @author m.kaspera, 15.01.2020
 */
@Deprecated(forRemoval = true)
public class FileFullObservable extends AbstractListenerObservable<FileChangeListener, File, Optional<File>>
{

  private FileFullObservable(@NonNull File pListenableValue)
  {
    super(pListenableValue);
  }

  @NonNull
  public static Observable<Optional<File>> create(@NonNull File pFile)
  {
    return Observable.create(new FileFullObservable(pFile)).startWithItem(Optional.of(pFile))
        .observeOn(Schedulers.io())
        .subscribeOn(Schedulers.io());
  }

  @NonNull
  @Override
  protected FileChangeListener registerListener(@NonNull File pFile, @NonNull IFireable<Optional<File>> pFireable)
  {
    FileChangeListener fcl = new _FileCreatedListener(pFireable);
    FileUtil.addRecursiveListener(fcl, FileUtil.normalizeFile(pFile));
    return fcl;
  }

  @Override
  protected void removeListener(@NonNull File pFile, @NonNull FileChangeListener pFileChangeListener)
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

  private static class _FileCreatedListener extends FileChangeAdapter
  {
    private final IFireable<Optional<File>> fireable;

    _FileCreatedListener(IFireable<Optional<File>> pFireable)
    {
      fireable = pFireable;
    }

    @Override
    public void fileDataCreated(FileEvent fe)
    {
      fireable.fireValueChanged(Optional.of(FileUtil.toFile(fe.getFile())));
    }

    @Override
    public void fileChanged(FileEvent fe)
    {
      fireable.fireValueChanged(Optional.of(FileUtil.toFile(fe.getFile())));
    }

    @Override
    public void fileDeleted(FileEvent fe)
    {
      fireable.fireValueChanged(Optional.of(FileUtil.toFile(fe.getFile())));
    }

    @Override
    public void fileRenamed(FileRenameEvent fe)
    {
      fireable.fireValueChanged(Optional.of(FileUtil.toFile(fe.getFile())));
    }
  }
}
