package de.adito.observables.netbeans;

import de.adito.util.reactive.*;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.NonNull;
import org.openide.filesystems.*;

/**
 * Observable, that listens on fileChanged/fileDeleted/fileRenamed events
 * 
 * @author w.glanzer, 14.11.2018
 */
public class FileObjectObservable extends AbstractListenerObservable<FileChangeListener, FileObject, FileObject>
{

  @NonNull
  public static Observable<FileObject> create(@NonNull FileObject pFileObject)
  {
    return Observables.create(new FileObjectObservable(pFileObject), () -> pFileObject)
        .observeOn(Schedulers.io())
        .subscribeOn(Schedulers.io());
  }  
  
  private FileObjectObservable(@NonNull FileObject pListenableValue)
  {
    super(pListenableValue);
  }

  @NonNull
  @Override
  protected FileChangeListener registerListener(@NonNull FileObject pFileObject, @NonNull IFireable<FileObject> pFireable)
  {
    FileChangeListener fcl = new _FileChangeListener(pFileObject, pFireable);
    pFileObject.addFileChangeListener(fcl);
    return fcl;
  }

  @Override
  protected void removeListener(@NonNull FileObject pFileObject, @NonNull FileChangeListener pFileChangeListener)
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
    public void fileFolderCreated(FileEvent fe)
    {
      fireable.fireValueChanged(fileObject);
    }

    @Override
    public void fileDataCreated(FileEvent fe)
    {
      fireable.fireValueChanged(fileObject);
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
