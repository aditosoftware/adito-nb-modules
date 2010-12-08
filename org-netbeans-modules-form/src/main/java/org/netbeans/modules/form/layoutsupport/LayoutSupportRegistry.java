/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */

package org.netbeans.modules.form.layoutsupport;

import java.awt.*;
import java.util.*;
import java.lang.ref.*;

import org.openide.loaders.*;
import org.openide.filesystems.*;
import org.openide.ErrorManager;

import org.netbeans.modules.form.FormModel;
import org.netbeans.modules.form.FormUtils;
import org.netbeans.modules.form.palette.PaletteItem;
import org.netbeans.modules.form.palette.PaletteUtils;

/**
 * Registry and factory class for LayoutSupportDelegate implementations.
 *
 * @author Tomas Pavek
 */

public class LayoutSupportRegistry {

    private static Map<String,String> containerToLayoutDelegate;
    private static Map<String,String> layoutToLayoutDelegate;

    private static boolean needPaletteRescan = true;

    public static final String DEFAULT_SUPPORT = "<default>"; // NOI18N

    private static FileChangeListener paletteListener;

    private static Map<FormModel,LayoutSupportRegistry> instanceMap;

    private Reference<FormModel> formModelRef;

    // -------

    private LayoutSupportRegistry(FormModel formModel) {
        this.formModelRef = new WeakReference<FormModel>(formModel);
    }

    public static LayoutSupportRegistry getRegistry(FormModel formModel) {
        LayoutSupportRegistry reg;
        if (instanceMap == null) {
            instanceMap = new WeakHashMap<FormModel,LayoutSupportRegistry>(); 
            reg = null;
        }
        else reg = instanceMap.get(formModel);

        if (reg == null) {
            reg = new LayoutSupportRegistry(formModel);
            instanceMap.put(formModel, reg);
        }

        return reg;
    }

    // --------------
    // get methods

    public Class getSupportClassForContainer(Class containerClass) {
        String className = getContainersMap().get(containerClass.getName());
        if (className == null) {
            className = findSuperClass(getContainersMap(), containerClass);
//            if (className == null && needPaletteRescan) {
//                className = scanPalette(containerClass.getName());
//                if (className == null) // try container superclass again
//                    className = findSuperClass(getContainersMap(),
//                                               containerClass);
//            }
        }

        return className != null ? loadClass(className) : null;
    }

    public String getSupportNameForContainer(String containerClassName) {
        String className = getContainersMap().get(containerClassName);
        if (className == null) {
            Class containerClass = loadClass(containerClassName);
            if (containerClass != null)
                className = findSuperClass(getContainersMap(), containerClass);
//            if (className == null && needPaletteRescan) {
//                className = scanPalette(containerClassName);
//                if (className == null) // try container superclass again
//                    className = findSuperClass(getContainersMap(),
//                                               containerClass);
//            }
        }

        return className;
    }

    public Class getSupportClassForLayout(Class layoutClass) {
        String className = getLayoutsMap().get(layoutClass.getName());
        if (className == null && needPaletteRescan)
            className = scanPalette(layoutClass.getName());
        if (className == null)
            className = findSuperClass(getLayoutsMap(), layoutClass);

        return className != null ? loadClass(className) : null;
    }

    public String getSupportNameForLayout(String layoutClassName) {
        String className = getLayoutsMap().get(layoutClassName);
        if (className == null && needPaletteRescan)
            className = scanPalette(layoutClassName);
        if (className == null) {
            Class layoutClass = loadClass(layoutClassName);
            if (layoutClass != null)
                className = findSuperClass(getLayoutsMap(), layoutClass);
        }

        return className;
    }

    // ------------
    // registering methods

    public static void registerSupportForContainer(
                           Class containerClass,
                           Class layoutDelegateClass)
    {
        getContainersMap().put(containerClass.getName(),
                               layoutDelegateClass.getName());
    }

    public static void registerSupportForContainer(
                           String containerClassName,
                           String layoutDelegateClassName)
    {
        getContainersMap().put(containerClassName, layoutDelegateClassName);
    }

    public static void registertSupportForLayout(
                           Class layoutClass,
                           Class layoutDelegateClass)
    {
        getLayoutsMap().put(layoutClass.getName(),
                            layoutDelegateClass.getName());
    }

    public static void registerSupportForLayout(
                           String layoutClassName,
                           String layoutDelegateClassName)
    {
        getLayoutsMap().put(layoutClassName, layoutDelegateClassName);
    }

    // ------------
    // creation methods

    public LayoutSupportDelegate createSupportForContainer(Class containerClass)
        throws ClassNotFoundException,
               InstantiationException,
               IllegalAccessException
    {
        Class delegateClass = getSupportClassForContainer(containerClass);
        if (delegateClass == null)
            return null;

        return (LayoutSupportDelegate) delegateClass.newInstance();
    }

    public LayoutSupportDelegate createSupportForLayout(Class layoutClass)
        throws ClassNotFoundException,
               InstantiationException,
               IllegalAccessException
    {
        String delegateClassName = getSupportNameForLayout(layoutClass.getName());
        if (delegateClassName == null)
            return null;

        if (delegateClassName == DEFAULT_SUPPORT)
            return new DefaultLayoutSupport(layoutClass);

        return (LayoutSupportDelegate)
               loadClass(delegateClassName).newInstance();
    }

    public static LayoutSupportDelegate createSupportInstance(
                                            Class layoutDelegateClass)
        throws InstantiationException, IllegalAccessException
    {
        return (LayoutSupportDelegate) layoutDelegateClass.newInstance();
    }

    // -----------
    // private methods

    private String findSuperClass(Map map, Class subClass) {
        for (Iterator it=map.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry en = (Map.Entry) it.next();
            String className = (String) en.getKey();
            Class<?> keyClass = loadClass(className);
            if (keyClass != null && keyClass.isAssignableFrom(subClass))
                return (String) en.getValue();
        }
        return null;
    }

    private static String scanPalette(String wantedClassName) {
        FileObject paletteFolder = PaletteUtils.getPaletteFolder();

        // create palette content listener - only once
        boolean newPaletteListener = paletteListener == null;
        if (newPaletteListener) {
            paletteListener = new FileChangeAdapter() {
                @Override
                public void fileDataCreated(FileEvent fe) {
                    needPaletteRescan = true;
                }
                @Override
                public void fileFolderCreated(FileEvent fe) {
                    needPaletteRescan = true;
                    fe.getFile().addFileChangeListener(this);
                }
                @Override
                public void fileDeleted(FileEvent fe) {
                    fe.getFile().removeFileChangeListener(this);
                }
            };

            paletteFolder.addFileChangeListener(paletteListener);
        }

        String foundSupportClassName = null;

        FileObject[] paletteCategories = paletteFolder.getChildren();
        for (int i=0; i < paletteCategories.length; i++) {
            FileObject categoryFolder = paletteCategories[i];
            if (!categoryFolder.isFolder())
                continue;
           
            if (newPaletteListener)
                categoryFolder.addFileChangeListener(paletteListener);

            FileObject[] paletteItems = categoryFolder.getChildren();
            for (int j=0; j < paletteItems.length; j++) {
                DataObject itemDO = null;
                try {
                    itemDO = DataObject.find(paletteItems[j]);
                }
                catch (DataObjectNotFoundException ex) {
                    continue;
                }

                PaletteItem item = itemDO.getCookie(PaletteItem.class);
                if (item == null || !item.isLayout())
                    continue;

                Class itemClass = item.getComponentClass();
                if (itemClass == null)
                    continue; // cannot resolve class - ignore

                Class delegateClass = null;
                Class supportedClass = null;

                if (LayoutSupportDelegate.class.isAssignableFrom(itemClass)) {
                    // register LayoutSupportDelegate directly
                    delegateClass = itemClass;
                    try {
                        LayoutSupportDelegate delegate =
                            (LayoutSupportDelegate) delegateClass.newInstance();
                        supportedClass = delegate.getSupportedClass();
                    }
                    catch (Exception ex) {
                        org.openide.ErrorManager.getDefault().notify(
                            org.openide.ErrorManager.INFORMATIONAL, ex);
                        continue; // invalid - ignore
                    }
                }
                else if (LayoutManager.class.isAssignableFrom(itemClass)) {
                    // register default support for layout
                    supportedClass = itemClass;
                }

                if (supportedClass != null) {
                    Map<String,String> map;
                    if (Container.class.isAssignableFrom(supportedClass))
                        map = getContainersMap();
                    else if (LayoutManager.class.isAssignableFrom(supportedClass))
                        map = getLayoutsMap();
                    else continue; // invalid - ignore

                    String supportedClassName = supportedClass.getName();
                    if (map.get(supportedClassName) == null) {
                        String delegateClassName = delegateClass != null ?
                                                     delegateClass.getName():
                                                     DEFAULT_SUPPORT;

                        map.put(supportedClassName, delegateClassName);

                        if (supportedClassName.equals(wantedClassName))
                            foundSupportClassName = delegateClassName;
                    }
                }
            }
        }

        needPaletteRescan = false;
        return foundSupportClassName;
    }

    private Class loadClass(String className) {
        try {
            return FormUtils.loadClass(className, formModelRef.get());
        }
        catch (Exception ex) {
            ErrorManager.getDefault().notify(ErrorManager.INFORMATIONAL, ex);
        }
        catch (LinkageError ex) {
            ErrorManager.getDefault().notify(ErrorManager.INFORMATIONAL, ex);
        }
        return null;
    }

    private static Map<String,String> getContainersMap() {
        if (containerToLayoutDelegate == null) {
            containerToLayoutDelegate = new HashMap<String,String>();
            // fill in default containers
            containerToLayoutDelegate.put(
                "javax.swing.JScrollPane", // NOI18N
                "org.netbeans.modules.form.layoutsupport.delegates.JScrollPaneSupport"); // NOI18N
            containerToLayoutDelegate.put(
                "java.awt.ScrollPane", // NOI18N
                "org.netbeans.modules.form.layoutsupport.delegates.ScrollPaneSupport"); // NOI18N
            containerToLayoutDelegate.put(
                "javax.swing.JSplitPane", // NOI18N
                "org.netbeans.modules.form.layoutsupport.delegates.JSplitPaneSupport"); // NOI18N
            containerToLayoutDelegate.put(
                "javax.swing.JTabbedPane", // NOI18N
                "org.netbeans.modules.form.layoutsupport.delegates.JTabbedPaneSupport"); // NOI18N
            containerToLayoutDelegate.put(
                "javax.swing.JLayeredPane", // NOI18N
                "org.netbeans.modules.form.layoutsupport.delegates.JLayeredPaneSupport"); // NOI18N
            containerToLayoutDelegate.put(
                "javax.swing.JToolBar", // NOI18N
                "org.netbeans.modules.form.layoutsupport.delegates.JToolBarSupport"); // NOI18N
            containerToLayoutDelegate.put(
                "javax.swing.JMenuBar", // NOI18N
                "org.netbeans.modules.form.layoutsupport.delegates.MenuFakeSupport"); // NOI18N
            containerToLayoutDelegate.put(
                "javax.swing.JMenu", // NOI18N
                "org.netbeans.modules.form.layoutsupport.delegates.MenuFakeSupport"); // NOI18N
            containerToLayoutDelegate.put(
                "javax.swing.JPopupMenu", // NOI18N
                "org.netbeans.modules.form.layoutsupport.delegates.MenuFakeSupport"); // NOI18N
        }
        return containerToLayoutDelegate;
    }

    private static Map<String,String> getLayoutsMap() {
        if (layoutToLayoutDelegate == null) {
            layoutToLayoutDelegate = new HashMap<String,String>();
            // fill in default layouts
            layoutToLayoutDelegate.put(
                "java.awt.BorderLayout", // NOI18N
                "org.netbeans.modules.form.layoutsupport.delegates.BorderLayoutSupport"); // NOI18N
            layoutToLayoutDelegate.put(
                "java.awt.FlowLayout", // NOI18N
                "org.netbeans.modules.form.layoutsupport.delegates.FlowLayoutSupport"); // NOI18N
            layoutToLayoutDelegate.put(
                "javax.swing.BoxLayout", // NOI18N
                "org.netbeans.modules.form.layoutsupport.delegates.BoxLayoutSupport"); // NOI18N
            layoutToLayoutDelegate.put(
                "java.awt.GridBagLayout", // NOI18N
                "org.netbeans.modules.form.layoutsupport.delegates.GridBagLayoutSupport"); // NOI18N
            layoutToLayoutDelegate.put(
                "java.awt.GridLayout", // NOI18N
                "org.netbeans.modules.form.layoutsupport.delegates.GridLayoutSupport"); // NOI18N
            layoutToLayoutDelegate.put(
                "java.awt.CardLayout", // NOI18N
                "org.netbeans.modules.form.layoutsupport.delegates.CardLayoutSupport"); // NOI18N
            layoutToLayoutDelegate.put(
                "org.netbeans.lib.awtextra.AbsoluteLayout", // NOI18N
                "org.netbeans.modules.form.layoutsupport.delegates.AbsoluteLayoutSupport"); // NOI18N
        }
        return layoutToLayoutDelegate;
    }
}
