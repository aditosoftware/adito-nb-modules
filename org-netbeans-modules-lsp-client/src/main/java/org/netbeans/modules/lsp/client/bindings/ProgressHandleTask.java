package org.netbeans.modules.lsp.client.bindings;

import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.modules.lsp.client.*;
import org.openide.filesystems.FileObject;

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
  private ProgressHandle handle;

  public ProgressHandleTask()
  {
    handle = ProgressHandle.createSystemHandle("", null);
    handle.start();
    handle.switchToIndeterminate();
    handle.setDisplayName("Initializing JS/TS features");
  }

  @Override
  public void run(LSPBindings bindings, FileObject file)
  {
    if (handle != null)
    {
      handle.finish();
      handle.close();
    }
    handle = null;
  }
}
