package org.netbeans.modules.lsp.client.bindings;

import org.jetbrains.annotations.NotNull;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.modules.lsp.client.*;
import org.openide.filesystems.FileObject;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Pseudo background task for progress handle
 *
 * If the language server is starting, it takes some time until hints are displayed. During this a progress handle should be displayed.
 * Unfortunately, there is no timestamp when the server will be ready. This task can be used to roughly estimate when the language server will be ready.
 *
 * @author s.seemann, 04.04.2022
 */
public class ProgressHandleTask implements LSPWorkingPool.BackgroundTask
{
  private static final Map<String, ProgressHandle> handles = new ConcurrentHashMap<>();

  public ProgressHandleTask(@NotNull FileObject file)
  {
    String ext = Objects.requireNonNullElse(file.getExt(), "").toLowerCase(Locale.ROOT);
    ProgressHandle handle = handles.get(ext);
    if(handle == null)
    {
      handle = ProgressHandle.createSystemHandle("", null);
      handle.start();
      handle.switchToIndeterminate();
      handle.setDisplayName("Initializing " + ext.toUpperCase() + " features");
      handles.put(ext, handle);
    }
  }

  @Override
  public void run(LSPBindings bindings, FileObject file)
  {
    cleanup(file);
  }

  public static void cleanup(FileObject file)
  {
    String ext = Objects.requireNonNullElse(file.getExt(), "").toLowerCase(Locale.ROOT);
    ProgressHandle handle = handles.get(ext);
    if (handle != null)
    {
      handle.finish();
      handle.close();
      handles.remove(ext);
    }
  }
}
