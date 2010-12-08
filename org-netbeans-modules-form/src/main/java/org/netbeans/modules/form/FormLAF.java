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

package org.netbeans.modules.form;

import java.beans.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.*;
import org.jdesktop.layout.LayoutStyle;
import org.netbeans.modules.form.project.ClassPathUtils;
import org.openide.util.*;
import org.openide.ErrorManager;
import org.openide.filesystems.FileObject;

/**
 * Support for execution of tasks in another look and feel.
 *
 * @author Jan Stola, Tran Duc Trung
 */
public class FormLAF {
    private static final String SWING_NOXP = "swing.noxp"; // NOI18N
    /** Determines whether the FormLAF has been initialized (e.g. DelegatingDefaults has been installed). */
    private static boolean initialized = false;
    /** Determines whether we already are in LAF block. */
    private static boolean lafBlockEntered;
    /** DelegatingDefaults installed in UIManager.FormLAF */
    private static DelegatingDefaults delDefaults;
    /** User UIDefaults of the IDE. */
    private static Map<Object,Object> netbeansDefaults = new HashMap<Object,Object>();
    /** User UIDefaults of components */
    private static Map<Object,Object> userDefaults = new HashMap<Object,Object>();
    /** Maps LAF class to its theme. */
    private static Map<Class, MetalTheme> lafToTheme = new HashMap<Class, MetalTheme>();
    /** Determines whether the IDE LAF is subclass of MetalLookAndFeel. */
    private static boolean ideLafIsMetal;
    /** Determines whether the current LAF block corresponds to preview. */
    private static boolean preview;
    /** LAF class of preview. */
    private static Class previewLaf;

    /**
     * <code>FormLAF</code> has static methods only => no need for public constructor.
     */
    private FormLAF() {
    }
    
    public static PreviewInfo initPreviewLaf(Class lafClass, ClassLoader formClassLoader) {
        try {
            boolean previewLafIsMetal = MetalLookAndFeel.class.isAssignableFrom(lafClass);
            if (!ideLafIsMetal && previewLafIsMetal &&
                !MetalLookAndFeel.class.equals(lafClass) && (lafToTheme.get(MetalLookAndFeel.class) == null)) {
                lafToTheme.put(MetalLookAndFeel.class, MetalLookAndFeel.getCurrentTheme());
            }
            LookAndFeel previewLookAndFeel = (LookAndFeel)lafClass.newInstance();
            if (previewLafIsMetal) {
                MetalTheme theme = lafToTheme.get(lafClass);
                if (theme == null) {
                    lafToTheme.put(lafClass, MetalLookAndFeel.getCurrentTheme());
                } else {
                    MetalLookAndFeel.setCurrentTheme(theme);
                }
            }
            
            String noxp = null;
            boolean classic = isClassicWinLAF(lafClass.getName());
            if (classic) {
                noxp = System.getProperty(SWING_NOXP);
                System.setProperty(SWING_NOXP, "y"); // NOI18N
            }
            UIDefaults previewDefaults = null;
            try {
                previewLookAndFeel.initialize();
                previewDefaults = previewLookAndFeel.getDefaults();
            } finally {
                if (classic) {
                    if (noxp == null) {
                        System.getProperties().remove(SWING_NOXP);
                    } else {
                        System.setProperty(SWING_NOXP, noxp);
                    }
                }
            }

            PreviewInfo info = new PreviewInfo(previewLookAndFeel, previewDefaults);
            if (isNimbusLAF(lafClass) && !isNimbusLAF(UIManager.getLookAndFeel().getClass())) {
                Object control = null;
                try {
                    // Nimbus is based on "control" default. So, make sure that we use the correct one.
                    control = UIManager.getDefaults().remove("control"); // NOI18N
                    // Issue 130221 - Nimbus needs "defaultFont" on some places outside
                    // our control (e.g. outside our preview LAF blocks). Unfortunately,
                    // it is not possible to use Nimbus.Overrides for this purpose because
                    // we don't have access to all components that needs this key. For example,
                    // it is needed by tooltips that are created on demand.
                    // Modification of IDE defaults may be dangerous, but AFAIK there is no other
                    // look and feel or library using "defaultFont" key.
                    UIManager.getDefaults().put("defaultFont", previewDefaults.get("defaultFont")); // NOI18N
                    // The update of derived colors must be performed within preview block
                    FormLAF.setUsePreviewDefaults(formClassLoader, info);
                    for (PropertyChangeListener listener : UIManager.getPropertyChangeListeners()) {
                        if (listener.getClass().getName().contains("NimbusDefaults")) { // NOI18N
                            // Forces update of derived colors, see NimbusDefaults.DefaultsListener
                            // We must fire the event several times because some values are dependent
                            for (int i=0; i<5; i++) {
                                listener.propertyChange(new PropertyChangeEvent(UIManager.class, "lookAndFeel", null, null)); // NOI18N
                            }
                            // Remove listeners added by NimbusLookAndFeel.initialize()
                            UIManager.removePropertyChangeListener(listener);
                        }
                    }
                    for (PropertyChangeListener listener : UIManager.getDefaults().getPropertyChangeListeners()) {
                        if (listener.getClass().getName().contains("NimbusDefaults")) { // NOI18N
                            UIManager.getDefaults().removePropertyChangeListener(listener);
                        }
                    }
                } finally {
                    FormLAF.setUsePreviewDefaults(null, null);
                }
                // Return the old "control" default back
                if (control != null) {
                    UIManager.getDefaults().put("control", control); // NOI18N
                }
            }

            if (previewLafIsMetal && ideLafIsMetal) {
                LookAndFeel ideLaf = UIManager.getLookAndFeel();
                MetalTheme theme = lafToTheme.get(ideLaf.getClass());
                MetalLookAndFeel.setCurrentTheme(theme);
            }

            ClassLoader classLoader = lafClass.getClassLoader();
            if (classLoader != null) previewDefaults.put("ClassLoader", classLoader); // NOI18N

            // Force switch of the LayoutStyle
            if (previewDefaults.get("LayoutStyle.instance") == null) { // NOI18N
                previewDefaults.put("LayoutStyle.instance", // NOI18N
                    createLayoutStyle(previewLookAndFeel)); 
            }

            return info;
        } catch (Exception ex) {
            ErrorManager.getDefault().notify(ErrorManager.INFORMATIONAL, ex);
        } catch (LinkageError ex) {
            ErrorManager.getDefault().notify(ErrorManager.INFORMATIONAL, ex);
        }
        return null;
    }

    private static boolean isClassicWinLAF(String className) {
        return "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel".equals(className); // NOI18N
    }

    private static boolean isNimbusLAF(Class lafClass) {
        return lafClass.getName().endsWith("NimbusLookAndFeel"); // NOI18N
    }

    private static void invalidateXPStyle() {
        try {
            Class xpStyle = Class.forName("com.sun.java.swing.plaf.windows.XPStyle"); // NOI18N
            java.lang.reflect.Method method = xpStyle.getDeclaredMethod("invalidateStyle", (Class[])null); // NOI18N
            method.setAccessible(true);
            method.invoke(null);
        } catch (Exception ex) {
            Logger.getLogger(FormLAF.class.getName()).log(Level.INFO, ex.getMessage(), ex);
        }
    }

    private static void initialize() throws Exception {
        initialized = true;
        UIDefaults defaults = UIManager.getDefaults(); // Force initialization

        // Resolve lazy values
        Set<Object> keySet = new HashSet<Object>(defaults.keySet());
        // We cannot iterate directly over defaults.keySet() because
        // defaults.get(key) can modify the map (which leads
        // to ConcurrentModificationException).
        for (Object key : keySet) {
            defaults.get(key);
        }

        LookAndFeel laf = UIManager.getLookAndFeel();
        ideLafIsMetal = laf instanceof MetalLookAndFeel;
        if (ideLafIsMetal) {
            lafToTheme.put(laf.getClass(), MetalLookAndFeel.getCurrentTheme());
        }
        LookAndFeel original = laf;
        try {
            original = laf.getClass().newInstance();
        } catch (Exception ex) {
            Logger.getLogger(FormLAF.class.getName()).log(Level.INFO, ex.getMessage(), ex);
        }

        java.lang.reflect.Method method = UIManager.class.getDeclaredMethod("getLAFState", new Class[0]); // NOI18N
        method.setAccessible(true);
        Object lafState = method.invoke(null, new Object[0]);
        method = lafState.getClass().getDeclaredMethod("setLookAndFeelDefaults", new Class[] {UIDefaults.class}); // NOI18N
        method.setAccessible(true);

        UIDefaults ide = UIManager.getLookAndFeelDefaults();
        assert !(ide instanceof DelegatingDefaults);

        delDefaults = new DelegatingDefaults(null, original.getDefaults(), ide);
        method.invoke(lafState, new Object[] {delDefaults});

        // See UIDefaults.getUIClass() method - it stores className-class pairs
        // in its map. When project classpath is updated new versions
        // of classes are loaded which results in ClassCastException if
        // such new classes are casted to the ones obtained from the map.
        // Hence, we remove such mappings to avoid problems.
        UIManager.getDefaults().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (delDefaults.isDelegating() || delDefaults.isPreviewing()) {
                    Object newValue = evt.getNewValue();
                    if (newValue instanceof Class) {
                        Class<?> clazz = (Class<?>)newValue;
                        if (ComponentUI.class.isAssignableFrom(clazz)) {
                            UIManager.getDefaults().put(evt.getPropertyName(), null);
                        }
                    }
                }
            }
        });
    }

    static Object executeWithLookAndFeel(final FormModel formModel, final Mutex.ExceptionAction act)
        throws Exception
    {
        try {
            return Mutex.EVENT.readAccess(new Mutex.ExceptionAction<Object>() {
                @Override
                public Object run() throws Exception {
                    // FIXME(-ttran) needs to hold a lock on UIDefaults to
                    // prevent other threads from creating Swing components
                    // in the mean time
                    synchronized (Introspector.class) {
                    synchronized (UIManager.getDefaults()) {
                        boolean restoreAfter = true;
                        try {
                            if (lafBlockEntered)
                                restoreAfter = false;
                            else {
                                lafBlockEntered = true;
                                useDesignerLookAndFeel(formModel);
                                restoreAfter = true;
                            }
                            return act.run();
                        }
                        finally {
                            if (restoreAfter) {
                                useIDELookAndFeel();
                                lafBlockEntered = false;
                            }
                        }
                    }
                    }
                }
            });
        }
        catch (MutexException ex) {
            throw ex.getException();
        }
    }

    public static void executeWithLookAndFeel(final FormModel formModel, final Runnable run) {
        Mutex.EVENT.readAccess(new Mutex.Action<Object>() {
            @Override
            public Object run() {
                // FIXME(-ttran) needs to hold a lock on UIDefaults to
                // prevent other threads from creating Swing components
                // in the mean time
                synchronized (Introspector.class) {
                synchronized (UIManager.getDefaults()) {
                    boolean restoreAfter = true;
                    try {
                        if (lafBlockEntered)
                            restoreAfter = false;
                        else {
                            lafBlockEntered = true;
                            useDesignerLookAndFeel(formModel);
                            restoreAfter = true;
                        }
                        run.run();
                    }
                    finally {
                        if (restoreAfter) {
                            useIDELookAndFeel();
                            lafBlockEntered = false;
                        }
                    }
                }
                }
                return null;
            }
        });
    }

    private static void useDesignerLookAndFeel(FormModel formModel) {
        if (!initialized) {
            try {
                initialize();
            } catch (Exception ex) {
                Logger.getLogger(FormLAF.class.getName()).log(Level.INFO, ex.getMessage(), ex);
            }
        }
        UIDefaults defaults = UIManager.getDefaults();
        netbeansDefaults.clear();
        copyMultiUIDefaults(defaults, netbeansDefaults);
        netbeansDefaults.keySet().removeAll(userDefaults.keySet());
        defaults.keySet().removeAll(netbeansDefaults.keySet());

        if (!preview) {
            setUseDesignerDefaults(formModel);
        } else if (MetalLookAndFeel.class.isAssignableFrom(previewLaf)) {
            MetalLookAndFeel.setCurrentTheme(lafToTheme.get(previewLaf));
        }
    }

    private static void useIDELookAndFeel() {
        userDefaults.clear();
        copyMultiUIDefaults(UIManager.getDefaults(), userDefaults);

        if (!preview) {
            setUseDesignerDefaults(null);
        } else if (ideLafIsMetal) {
            if (!isNimbusLAF(previewLaf)) { // Issue 134846
                MetalLookAndFeel.setCurrentTheme(lafToTheme.get(UIManager.getLookAndFeel().getClass()));
            }
        }

        UIManager.getDefaults().putAll(netbeansDefaults);
    }

    private static void copyMultiUIDefaults(UIDefaults what, Map where) {
        // We cannot invoke what.entrySet() because it was overriden
        // in MultiUIDefaults in JDK 6 Update 10
        try {
            java.lang.reflect.Method method = Hashtable.class.getDeclaredMethod("getIterator", new Class[] {int.class}); // NOI18N
            method.setAccessible(true);
            Object i = method.invoke(what, new Object[] {2/*Hashtable.ENTRIES*/});
            if (i instanceof Iterator) {
                Iterator iter = (Iterator)i;
                while (iter.hasNext()) {
                    Object item = iter.next();
                    if (item instanceof Map.Entry) {
                        Map.Entry entry = (Map.Entry)item;
                        where.put(entry.getKey(), entry.getValue());
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(FormLAF.class.getName()).log(Level.INFO, ex.getMessage(), ex);
        }
    }

    /**
     * HACK - creates a LayoutStyle that corresponds to the given LAF.
     * LayoutStyle is created according to UIManager.getLookAndFeel()
     * which is not affected by our LAF switch => we have to create
     * the new LayoutStyle manually.
     */
    private static LayoutStyle createLayoutStyle(LookAndFeel laf) {
        String lafID = laf.getID();
        boolean useCoreLayoutStyle = false;
        try {
            Class.forName("javax.swing.LayoutStyle"); // NOI18N
            useCoreLayoutStyle = true;
        } catch (ClassNotFoundException cnfex) {}
        String layoutStyleClass;
        if (useCoreLayoutStyle) {
            if ("Aqua" == lafID) { // NOI18N
                try {
                    laf.getClass().getDeclaredMethod("getLayoutStyle", new Class[0]); // NOI18N
                    layoutStyleClass = "Swing"; // NOI18N
                } catch (NoSuchMethodException nsfex) {
                    // getLayoutStyle() not overriden => use our own (issue 52)
                    layoutStyleClass = "Aqua";
                }
            } else {
                layoutStyleClass = "Swing"; // NOI18N
            }
        } else if ("Metal" == lafID) { // NOI18N
            layoutStyleClass = "Metal"; // NOI18N
        }
        else if ("Windows" == lafID) { // NOI18N
            layoutStyleClass = "Windows"; // NOI18N
        }
        else if ("GTK" == lafID) { // NOI18N
            layoutStyleClass = "Gnome"; // NOI18N
        }
        else if ("Aqua" == lafID) { // NOI18N
            layoutStyleClass = "Aqua"; // NOI18N
        } else {
            layoutStyleClass = ""; // NOI18N
        }
        layoutStyleClass = "org.jdesktop.layout." + layoutStyleClass + "LayoutStyle"; // NOI18N
        LayoutStyle layoutStyle = null;
        try {
            Class clazz = Class.forName(layoutStyleClass);
            java.lang.reflect.Constructor constr = clazz.getDeclaredConstructor(new Class[0]);
            constr.setAccessible(true);
            layoutStyle = (LayoutStyle)constr.newInstance((Object[])null);
        } catch (Exception ex) {
            Logger.getLogger(FormLAF.class.getName()).log(Level.INFO, ex.getMessage(), ex);
        }
        return layoutStyle;
    }

    static LayoutStyle getDesignerLayoutStyle() {
        return LayoutStyle.getSharedInstance();
    }

    /**
     * Maps class loader (project class loader of the form) to set of defaults
     * added when this class loader was in use.
     */
    private static Map<ClassLoader, Map<Object,Object>> classLoaderToDefaults = new WeakHashMap<ClassLoader, Map<Object,Object>>();
    /** Last content of UIDefaults - to be able to find newly added defaults. */
    private static UIDefaults lastDefaults = new UIDefaults();
    /** Defaults that correspond to project class loader in use. */
    private static Map<Object,Object> classLoaderDefaults;

    static void setUseDesignerDefaults(FormModel formModel) {
        ClassLoader classLoader = null;
        UIDefaults defaults = UIManager.getDefaults();
        if (formModel == null) {
            // Determine new user defaults add add them to classLoaderDefaults
            UIDefaults newDefaults = new UIDefaults();
            copyMultiUIDefaults(UIManager.getDefaults(), newDefaults);
            newDefaults.keySet().removeAll(lastDefaults.keySet());
            classLoaderDefaults.putAll(newDefaults);
            defaults.putAll(lastDefaults);
        } else {
            FileObject formFile = FormEditor.getFormDataObject(formModel).getFormFile();
            classLoader = ClassPathUtils.getProjectClassLoader(formFile);
            classLoaderDefaults = classLoaderToDefaults.get(classLoader);
            if (classLoaderDefaults == null) {
                classLoaderDefaults = new HashMap<Object,Object>();
                classLoaderToDefaults.put(classLoader, classLoaderDefaults);
            }
            lastDefaults.clear();
            copyMultiUIDefaults(defaults, lastDefaults);
            defaults.putAll(classLoaderDefaults);
        }
        delDefaults.setDelegating(classLoader);
    }

    static String oldNoXP;
    static Object origLAF;
    public static void setUsePreviewDefaults(ClassLoader classLoader, PreviewInfo info) {
        boolean classic = (info == null)
            ? ((previewLaf == null) ? false : isClassicWinLAF(previewLaf.getName()))
            : isClassicWinLAF(info.lafClass.getName());
        preview = (classLoader != null);
        previewLaf = (info == null) ? null : info.lafClass;
        if (preview) {
            if (classic) {
                oldNoXP = System.getProperty(SWING_NOXP);
                System.setProperty(SWING_NOXP, "y"); // NOI18N
                invalidateXPStyle();
            }
            classLoaderDefaults = classLoaderToDefaults.get(classLoader);
            if (classLoaderDefaults == null) {
                classLoaderDefaults = new HashMap<Object,Object>();
                classLoaderToDefaults.put(classLoader, classLoaderDefaults);
            }
            Map<Object,Object> added = new HashMap<Object,Object>(classLoaderDefaults);
            added.keySet().removeAll(info.defaults.keySet());
            info.defaults.putAll(added);
            delDefaults.setPreviewDefaults(info.defaults);
            // AbstractRegionPainter in Nimus L&F uses UIManager.getLookAndFeel()
            // We cannot use setLookAndFeel() because it would cause update of everything
            if (isNimbusLAF(previewLaf)) {
                origLAF = changeLAFStatesLAF(info.laf);
            }
        } else {
            if (classic) {
                if (oldNoXP == null) {
                    System.getProperties().remove(SWING_NOXP);
                } else {
                    System.setProperty(SWING_NOXP, oldNoXP);
                }
                invalidateXPStyle();
            }
            oldNoXP = null;
            // Restore original (IDEs) UIManager.lookAndFeel
            if (origLAF != null) {
                changeLAFStatesLAF(origLAF);
                origLAF = null;
            }
        }
        delDefaults.setPreviewing(classLoader);
    }

    /**
     * Changes UIManager.lookAndFeel.
     * 
     * @param laf look and feel to set in UIManager.
     * @return previous look and feel stored in UIManager.lookAndFeel.
     */
    private static Object changeLAFStatesLAF(Object laf) {
        Object value = null;
        try {
            java.lang.reflect.Method method = UIManager.class.getDeclaredMethod("getLAFState", new Class[0]); // NOI18N
            method.setAccessible(true);
            Object lafState = method.invoke(null, new Object[0]);
            Field field = lafState.getClass().getDeclaredField("lookAndFeel"); // NOI18N
            field.setAccessible(true);
            value = field.get(lafState);
            field.set(lafState, laf);
        } catch (Exception ex) {
            Logger.getLogger(FormLAF.class.getName()).log(Level.INFO, ex.getMessage(), ex);
        }
        return value;
    }

    public static boolean getUsePreviewDefaults() {
        return preview && !delDefaults.isDelegating();
    }
    
    public static boolean inLAFBlock() {
        return preview || delDefaults.isDelegating();
    }

    /**
     * Class that encapsulates information needed during preview.
     */
    public static class PreviewInfo {
        PreviewInfo(LookAndFeel laf, UIDefaults defaults) {
            this.laf = laf;
            this.lafClass = laf.getClass();
            this.defaults = defaults;
        }
        Class lafClass;
        UIDefaults defaults;
        LookAndFeel laf;
    }

    /**
     * Implementation of UIDefaults that delegates requests between two
     * UIDefaults based on some rule.
     */
    static class DelegatingDefaults extends UIDefaults {
        /** UIDefaults used for preview. */
        private UIDefaults preview;
        /** The designer UIDefaults. */
        private UIDefaults original;
        /** IDE UIDefaults. */
        private UIDefaults ide;
        /** If true, then the designer map is used. */
        private boolean delegating;
        /** If true, then the preview map is used. */
        private boolean previewing;     
        /** If true, then new UI components may install their defaults. */
        
        DelegatingDefaults(UIDefaults preview, UIDefaults original, UIDefaults ide) {
            this.preview = preview;
            this.original = original;
            this.ide = ide;
        }

        public void setPreviewDefaults(UIDefaults preview) {
            this.preview = preview;
        }

        /**
         * Maps class loader (project class loader of the form) to set of LAF defaults
         * added when this class loader was in use.
         */
        private Map<ClassLoader, UIDefaults> classLoaderToLAFDefaults = new WeakHashMap<ClassLoader,UIDefaults>();
        /** LAF defaults that correspond to project class loader in use. */
        private UIDefaults classLoaderLAFDefaults;

        public void setDelegating(ClassLoader classLoader){
            classLoaderLAFDefaults = classLoaderToLAFDefaults.get(classLoader);
            if (classLoaderLAFDefaults == null) {
                classLoaderLAFDefaults = new UIDefaults();
                classLoaderToLAFDefaults.put(classLoader, classLoaderLAFDefaults);
            }
            this.delegating = (classLoader != null);
        }

        public boolean isDelegating() {
            return delegating;
        }

        // Preview may not work if project classpath has been changed
        // while the form was opened: new UIDefaults are created
        // when the classpath is changed - this allows correct creation
        // of new components; unfortunately both new and old components
        // are _cloned_ when preview is done
        // There are also other use-cases that may fail e.g.
        // attempt to connect (via some property) old and new
        // component. But all these use-cases should work after form reload.
        public void setPreviewing(ClassLoader classLoader){
            this.previewing = (classLoader != null);
            if (previewing) {
                classLoaderLAFDefaults = classLoaderToLAFDefaults.get(classLoader);
                if (classLoaderLAFDefaults == null) {
                    classLoaderLAFDefaults = new UIDefaults();
                    classLoaderToLAFDefaults.put(classLoader, classLoaderLAFDefaults);
                }
            }
        }

        public boolean isPreviewing() {
            return previewing;
        }

        // Delegated methods

        private UIDefaults getCurrentDefaults() {
            return delegating ? original : (previewing ? preview : ide);
        }

        @Override
        public Object get(Object key) {
            Object value;
            if (delegating) {
                value = classLoaderLAFDefaults.get(key);
                if (value == null) {
                    value = original.get(key);
                }
            } else if (previewing) {
                value = classLoaderLAFDefaults.get(key);
                if (value == null) {
                    value = preview.get(key);
                }
            } else {
                value = ide.get(key);
            }
            return value;
        }

        @Override
        public Set<Object> keySet() {
            Set<Object> set;
            if (delegating) {
                set = new HashSet<Object>(classLoaderLAFDefaults.keySet());
                set.addAll(original.keySet());
            } else if (previewing) {
                set = new HashSet<Object>(classLoaderLAFDefaults.keySet());
                set.addAll(preview.keySet());
            } else {
                set = ide.keySet();
            }
            return set;
        }

        @Override
        public Set<Map.Entry<Object,Object>> entrySet() {
            Set<Map.Entry<Object,Object>> set;
            if (delegating) {
                set = new HashSet<Map.Entry<Object,Object>>(classLoaderLAFDefaults.entrySet());
                set.addAll(original.entrySet());
            } else if (previewing) {
                set = new HashSet<Map.Entry<Object,Object>>(classLoaderLAFDefaults.entrySet());
                set.addAll(preview.entrySet());
            } else {
                set = ide.entrySet();
            }
            return set;
        }

        @Override
        public Object put(Object key, Object value) {
            Object retVal;
            if (delegating || previewing) {
                retVal = classLoaderLAFDefaults.put(key, value);
            } else {
                retVal = ide.put(key, value);
            }
            return retVal;
        }

        @Override
        public void putDefaults(Object[] keyValueList) {
            getCurrentDefaults().putDefaults(keyValueList);
        }

        @Override
        public Object get(Object key, Locale l) {
            return getCurrentDefaults().get(key, l);
        }

        @Override
        public synchronized void addResourceBundle(String bundleName) {
            getCurrentDefaults().addResourceBundle(bundleName);
        }

        @Override
        public synchronized void removeResourceBundle(String bundleName) {
            getCurrentDefaults().removeResourceBundle(bundleName);
        }

        @Override
        public void setDefaultLocale(Locale l) {
            getCurrentDefaults().setDefaultLocale(l);
        }

        @Override
        public Locale getDefaultLocale() {
            return getCurrentDefaults().getDefaultLocale();
        }

        @Override
        public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
            getCurrentDefaults().addPropertyChangeListener(listener);
        }

        @Override
        public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
            getCurrentDefaults().removePropertyChangeListener(listener);
        }

        @Override
        public synchronized PropertyChangeListener[] getPropertyChangeListeners() {
            return getCurrentDefaults().getPropertyChangeListeners();
        }

        @Override
        protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, "FormLAF.firePropertyChange called, but not implemented."); // NOI18N
        }

        @Override
        public synchronized Enumeration<Object> keys() {
            return getCurrentDefaults().keys();
        }

    }

}
