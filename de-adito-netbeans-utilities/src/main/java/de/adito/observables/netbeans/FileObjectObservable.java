package de.adito.observables.netbeans;

import de.adito.util.reactive.*;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.jetbrains.annotations.NotNull;
import org.openide.filesystems.*;

/**
 * Observable, that listens on fileChanged/fileDeleted/fileRenamed events
 * 
 * @author w.glanzer, 14.11.2018
 */
public class FileObjectObservable extends AbstractListenerObservable<FileChangeListener, FileObject, FileObject>
{

  @NotNull
  public static Observable<FileObject> create(@NotNull FileObject pFileObject)
  {
    return Observables.create(new FileObjectObservable(pFileObject), () -> pFileObject)
        .observeOn(Schedulers.io())
        .subscribeOn(Schedulers.io());
  }  
  
  private FileObjectObservable(@NotNull FileObject pListenableValue)
  {
    super(pListenableValue);
  }

  @NotNull
  @Override
  protected FileChangeListener registerListener(@NotNull FileObject pFileObject, @NotNull IFireable<FileObject> pFireable)
  {
    FileChangeListener fcl = new _FileChangeListener(pFileObject, pFireable);
    pFileObject.addFileChangeListener(fcl);
    return fcl;
  }

  @Override
  protected void removeListener(@NotNull FileObject pFileObject, @NotNull FileChangeListener pFileChangeListener)
  {
    pFileObject.removeFileChangeListener(pFileChangeListener);
  }

  private static class _FileChangeListener extends FileChangeAdapter
  {
    private final FileObject fileObject;
    private final IFireable<FileObject> fireable;

    _FileChangeListener(FileObject pFileObject, IFireable<FileObject> pFireable)
    {
      fileObject = pFileObject;
      fireable = pFireable;
    }

    @Override
    public void fileChanged(FileEvent fe)
    {
      fireable.fireValueChanged(fileObject);
    }

    @Override
    public void fileDeleted(FileEvent fe)
    {
      fireable.fireValueChanged(fileObject);
    }

    @Override
    public void fileRenamed(FileRenameEvent fe)
    {
      fireable.fireValueChanged(fileObject);
    }
  }
}
