/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.modules.lsp.client;

import java.util.*;
import java.util.logging.*;

import org.netbeans.modules.lsp.client.bindings.*;
import org.openide.filesystems.FileObject;
import org.openide.util.RequestProcessor;

/**
 *
 * @author lahvac 
 * @author ranSprd
 * 
 */
public class LSPWorkingPool {
    private static final Logger LOG = Logger.getLogger(LSPWorkingPool.class.getName());
    private static final RequestProcessor WORKER = new RequestProcessor(LanguageClientImpl.class.getName(), 1, false, true);
    public static final RequestProcessor  ASYNC = new RequestProcessor(LanguageClientImpl.class.getName()+"-ASYNC", 1, false, false);
    
    private static final int DELAY = 500;

    private static final Map<FileObject, Map<BackgroundTask, RequestProcessor.Task>> backgroundTasks = new WeakHashMap<>();

    @SuppressWarnings("AccessingNonPublicFieldOfAnotherObject")
    public static synchronized void addBackgroundTask(FileObject file, BackgroundTask task) {
        RequestProcessor.Task req = WORKER.create(() -> {
            LSPBindings bindings;
            try
            {
                bindings = LSPBindingFactory.getBindingForFile(file);

                if (bindings == null)
                {
                    LOG.info(() -> "[LSP]: binding not found for file " + file);
                    // if bindings are not present, cancel progress handle
                    ProgressHandleTask.cleanup(file);
                    return;
                }
            }
            catch (Throwable t)
            {
                // if an error occurs, cancel progress handle
                ProgressHandleTask.cleanup(file);
                throw t;
            }

            task.run(bindings, file);
        });

        backgroundTasks.computeIfAbsent(file, f -> new LinkedHashMap<>()).put(task, req);
        scheduleBackgroundTask(req);
    }
    
    
    public static synchronized void removeBackgroundTask(FileObject file, BackgroundTask task) {
        RequestProcessor.Task req = backgroundTasksMapFor(file).remove(task);

        if (req != null) {
            req.cancel();
        }
    }

    /**
     * Removes and cancels all existing background tasks
     *
     * @param pFile the file, which is closed and which tasks should be removed
     */
    public static synchronized void removeBackgroundTasks(FileObject pFile) {
        try {
            Map<BackgroundTask, RequestProcessor.Task> removed = backgroundTasks.remove(pFile);
            if (removed != null)
                removed.forEach((key, value) -> value.cancel());
        }
        catch (Throwable t) {
            // failsafe, only log the error
            Logger.getLogger(LSPWorkingPool.class.getName()).log(Level.WARNING, t, t::getMessage);
        }
    }

    public static void runOnBackground(Runnable r) {
        WORKER.post(r);
    }

    private static void scheduleBackgroundTask(RequestProcessor.Task req) {
        WORKER.post(req, DELAY);
    }

    public static synchronized void rescheduleBackgroundTask(FileObject file, BackgroundTask task) {
        RequestProcessor.Task req = backgroundTasksMapFor(file).get(task);

        if (req != null) {
            WORKER.post(req, DELAY);
        }
    }

    public static synchronized void scheduleBackgroundTasks(FileObject file) {
        backgroundTasksMapFor(file).values().stream().forEach(LSPWorkingPool::scheduleBackgroundTask);
    }

    private static Map<BackgroundTask, RequestProcessor.Task> backgroundTasksMapFor(FileObject file) {
        return backgroundTasks.computeIfAbsent(file, f -> new IdentityHashMap<>());
    }
    
    
    
    public interface BackgroundTask {
        public void run(LSPBindings bindings, FileObject file);
    }
    
}
