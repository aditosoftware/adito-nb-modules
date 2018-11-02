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

package org.netbeans.modules.tasklist.impl;

import java.io.*;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.netbeans.modules.parsing.spi.indexing.Context;
import org.netbeans.modules.parsing.spi.indexing.CustomIndexer;
import org.netbeans.modules.parsing.spi.indexing.Indexable;
import org.netbeans.modules.parsing.spi.indexing.support.IndexDocument;
import org.netbeans.modules.parsing.spi.indexing.support.IndexingSupport;
import org.netbeans.modules.tasklist.filter.TaskFilter;
import org.netbeans.modules.tasklist.trampoline.Accessor;
import org.netbeans.spi.tasklist.FileTaskScanner;
import org.netbeans.spi.tasklist.Task;
import org.netbeans.spi.tasklist.TaskScanningScope;
import org.openide.awt.Actions;
import org.openide.filesystems.FileObject;
import org.openide.util.ContextAwareAction;

import javax.swing.*;

/**
 * Called from Indexing API framework. Simply asks all registered and active
 * FileTaskProviders to scan files provided by Indexing framework.
 *
 * @author S. Aubrecht
 */
public class TaskIndexer extends CustomIndexer {

    private final TaskList taskList;
    private final static Logger LOG = Logger.getLogger(TaskIndexer.class.getName());

    static final String KEY_SCANNER = "scanner"; //NOI18N
    static final String KEY_TASK = "task"; //NOI18N

    public TaskIndexer( TaskList taskList ) {
        this.taskList = taskList;
    }

    @Override
    protected void index(Iterable<? extends Indexable> files, Context context) {
        TaskManagerImpl tm = TaskManagerImpl.getInstance();
        if( !tm.isObserved() ) {
            tm.makeCacheDirty();
            return;
        }
        TaskFilter filter = tm.getFilter();
        if( null == filter )
            filter = TaskFilter.EMPTY;
        TaskScanningScope scope = tm.getScope();
        ArrayList<FileTaskScanner> scanners = null;
        try {
            boolean firstScan = true;
            boolean isInScope = false;
            boolean currentFileFound = false;
            IndexingSupport is = IndexingSupport.getInstance(context);
            for( Indexable idx : files ) {
                if (context.isCancelled()) {
                    LOG.log(Level.FINE, "Indexer cancelled"); //NOI18N
                    return;
                }

                // get fileObject
                FileObject root = context.getRoot();
                if( null == root ) {
                    LOG.log(Level.FINE, "Context root not available");
                    return;
                }
                FileObject fo = root.getFileObject(idx.getRelativePath());
                if( null == fo ) {
                    LOG.log(Level.FINE, "Cannot find file [%0] under root [%1]", new Object[] {idx.getRelativePath(), root});
                    continue;
                }

                /*
                 * if the currentEditorScope is active we want to scan only
                 * current editor file. If fo isn't the file we are looking for
                 * we will skip it and flag the cache as dirty
                 */
                if (tm.isCurrentEditorScope()) {
                    isInScope = scope.isInScope(fo);
                    if (isInScope) {
                        currentFileFound = true;
                    } else {
                        tm.makeCacheDirty();
                        continue;
                    }
                }

                //prepare file scanners
                if( null == scanners ) {
                    scanners = new ArrayList<FileTaskScanner>( 20 );
                    for( FileTaskScanner s : tm.getFileScanners() ) {
                        if( filter.isEnabled(s) ) {
                            s.notifyPrepare();
                            scanners.add(s);
                            LOG.fine("Using FileTaskScanner: " + s); //NOI18N
                        }
                    }
                }

                if (firstScan){
                    isInScope = scope.isInScope(fo);
                    firstScan = false;
                }
                is.removeDocuments(idx);
                IndexDocument doc = null;

                // scan and cache tasks
                for( FileTaskScanner scanner : scanners ) {
                    List<? extends Task> tasks = scanner.scan(fo);
                    if( null == tasks )
                        continue;
                    if( isInScope )
                        taskList.update(scanner, fo, new ArrayList<Task>(tasks), filter);
                    if( !tasks.isEmpty() ) {
                        if( null == doc ) {
                            doc = is.createDocument(idx);                            
                            doc.addPair(KEY_SCANNER, ScannerDescriptor.getType(scanner), true, true);                            
                        }
                        for( Task t : tasks ) {
                            doc.addPair(KEY_TASK, encode(t), false, true);
                        }
                    }
                }
                if (doc != null) {
                    is.addDocument(doc);
                }
                // current editor file has been found, no need for further scanning and caching
                if (currentFileFound) {
                    break;
                }
            }
        } catch( IOException ioE ) {
            LOG.log(Level.INFO, "Error while scanning file for tasks.", ioE);
        } finally {
            if( null != scanners ) {
                for( FileTaskScanner s : scanners ) {
                    s.notifyFinish();
                }
            }
        }
    }

    private static String encode( Task t ) {
        StringBuffer res = new StringBuffer();
        URL url = Accessor.DEFAULT.getURL(t);
        if( null == url )
            res.append("-");
        else
            res.append(url.toExternalForm());
        res.append("\n");
        res.append( Accessor.DEFAULT.getLine(t) );

        // ADITO
        res.append("\n");
        Action[] actions = Accessor.DEFAULT.getActions(t);
        if(actions != null)
            res.append(_encodeActions(actions));

        res.append("\n");
        res.append( Accessor.DEFAULT.getGroup(t).getName() );
        res.append("\n");
        res.append( Accessor.DEFAULT.getDescription(t) );
        return res.toString();
    }

    public static Task decode( FileObject fo, String encodedTask ) {
        int delimIndex = encodedTask.indexOf("\n");
        String strUrl = encodedTask.substring(0, delimIndex);
        URL url = null;
        if( !"-".equals(strUrl) ) {
            try {
                url = new URL(strUrl);
            } catch( MalformedURLException ex ) {
                //ignore
            }
        }
        encodedTask = encodedTask.substring(delimIndex+1);
        delimIndex = encodedTask.indexOf("\n");

        int lineNumber = Integer.valueOf(encodedTask.substring(0, delimIndex));
        encodedTask = encodedTask.substring(delimIndex+1);
        delimIndex = encodedTask.indexOf("\n");

        //ADITO
        Action[] actionInstances = _decodeActions(encodedTask.substring(0, delimIndex), fo);
        encodedTask = encodedTask.substring(delimIndex+1);
        delimIndex = encodedTask.indexOf("\n");

        String groupName = encodedTask.substring(0, delimIndex);
        String description = encodedTask.substring(delimIndex+1);
        if( null != url )
            return Task.create(url, groupName, description);

        // A
        Task task = Task.create(fo, groupName, description, lineNumber);
        try
        {
            if(Stream.of(actionInstances).anyMatch(Objects::nonNull))
            {
                Field actionsField = task.getClass().getDeclaredField("actions");
                actionsField.setAccessible(true);
                actionsField.set(task, actionInstances);
            }
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }

        return task;
    }

    //A
    private static String _encodeActions(Action[] pActions)
    {
        StringBuilder res = new StringBuilder();
        for (Action action : pActions)
        {
            if (action != null)
            {
                res.append(action.getClass().getName().replaceAll("\\$", ".")).append(";");
                try
                {
                    for (Field field : action.getClass().getDeclaredFields())
                    {
                        field.setAccessible(true);
                        if (Serializable.class.isAssignableFrom(field.getType()))
                        {
                            String fieldName = field.getName();

                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            ObjectOutputStream oos = new ObjectOutputStream(baos);
                            oos.writeObject(field.get(action));
                            oos.close();
                            String fieldValue = Base64.getEncoder().encodeToString(baos.toByteArray());
                            res.append(fieldName).append(";").append(fieldValue).append(";");
                        }
                    }

                }
                catch (Exception e)
                {
                    LOG.log(Level.INFO, "Error while encoding actions.", e);
                }
                res.append(";");
            }
        }

        return res.toString();
    }

    // A
    private static Action[] _decodeActions(String pActions, FileObject pFileObject)
    {
        String[] actionClassesWithArguments = pActions.split(";;");
        Action[] actionInstances = new Action[actionClassesWithArguments.length];
        for (int i = 0; i < actionClassesWithArguments.length; i++)
        {
            String actionClassWithArgument = actionClassesWithArguments[i];
            String[] actionClassWithArgumentSplit = actionClassWithArgument.split(";");
            String actionClass = actionClassWithArgumentSplit[0];
            if (actionClass != null && !actionClass.trim().isEmpty())
            {
                Action action = Actions.forID("adito/scan", actionClass);
                if(action != null)
                {
                    if (action instanceof ContextAwareAction)
                        action = ((ContextAwareAction) action).createContextAwareInstance(pFileObject.getLookup());

                    try
                    {
                        for (int argI = 1; argI < actionClassWithArgumentSplit.length; argI = argI + 2)
                        {
                            try
                            {
                                String fieldName = actionClassWithArgumentSplit[argI];
                                Field field = action.getClass().getDeclaredField(fieldName);
                                field.setAccessible(true);

                                String fieldValueBase64 = actionClassWithArgumentSplit[argI + 1];
                                byte[] fieldValueBytes = Base64.getDecoder().decode(fieldValueBase64);
                                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(fieldValueBytes));
                                Object fieldValue = ois.readObject();
                                ois.close();

                                field.set(action, fieldValue);
                            }
                            catch (NoSuchFieldException ignored)
                            {
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        LOG.log(Level.INFO, "Error while decoding actions.", e);
                    }
                    actionInstances[i] = action;
                }
            }
        }

        return actionInstances;
    }
}
