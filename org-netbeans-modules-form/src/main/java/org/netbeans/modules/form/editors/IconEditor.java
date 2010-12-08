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

package org.netbeans.modules.form.editors;

import java.awt.Component;
import java.awt.Image;
import java.beans.*;
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

import org.openide.explorer.propertysheet.editors.XMLPropertyEditor;
import org.openide.filesystems.FileObject;
import org.openide.ErrorManager;
import org.openide.filesystems.FileUtil;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.modules.form.FormModel;
import org.netbeans.modules.form.FormAwareEditor;
import org.netbeans.modules.form.FormDesignValue;
import org.netbeans.modules.form.FormDesignValueAdapter;
import org.netbeans.modules.form.FormEditor;
import org.netbeans.modules.form.FormProperty;

/**
 * PropertyEditor for Icons. Depends on existing DataObject for images.
 * Images must be represented by some DataObject which returns itself
 * as cookie, and has image file as a primary file. File extensions
 * for images is specified in isImage method.
 *
 * @author Jan Jancura, Jan Stola, Tomas Pavek
 */
public class IconEditor extends PropertyEditorSupport
                        implements XMLPropertyEditor, FormAwareEditor
{
    /** Type constant for icons from URL. */
    public static final int TYPE_URL = 1;
    /** Type constant for icons from file. */
    public static final int TYPE_FILE = 2;
    /** Type constant for icons from classpath. */
    public static final int TYPE_CLASSPATH = 3;

    /**
     * Names of subfolders (relative to the form file) where we try to look for
     * images by default. If none exists, the folder of the form file is used.
     */
    private static final String[] DEFAULT_DIRS = { "resources", "resource", "images" }; // NOI18N

    /**
     * Resource name of the current package (uses / as separator but does not
     * contain the initial /). Short file names are resolved against this
     * package, and also the package content is offered in getTags().
     */
    private String currentPackage;
    private String[] currentFiles;

    //private FormModel formModel;
    private FileObject sourceFile;
    private boolean externalIconsAllowed = true;

    @Override
    public void setValue(Object value) {
        if (sameValue(value, getValue()))
            return;

        NbImageIcon nbIcon;
        if (value instanceof NbImageIcon) {
            nbIcon = (NbImageIcon) value;
            if (nbIcon.getType() == TYPE_CLASSPATH) {
                setCurrentPackage(getResourcePackage(nbIcon.getName()));
            }
        }
        else {
            nbIcon = null;
            if (value == null && currentPackage == null)
                setCurrentPackage(getDefaultResourcePackage());
        }
        super.setValue(value);

        currentFiles = null; // hack - reset sometimes to read new folder content
    }

    private static boolean sameValue(Object val1, Object val2) {
        if (val1 == null && val2 == null)
            return true;
        if (val1 instanceof NbImageIcon && val2 instanceof NbImageIcon)
            return sameIcon((NbImageIcon)val1, (NbImageIcon)val2);
        return false;
    }

    private static boolean sameIcon(NbImageIcon nbIcon1, NbImageIcon nbIcon2) {
        return nbIcon1.getType() == nbIcon2.getType()
               && nbIcon1.getName().equals(nbIcon2.getName());
    }

    @Override
    public String getAsText() {
        Object val = getValue();
        if (val instanceof NbImageIcon) {
            NbImageIcon nbIcon = (NbImageIcon) val;
            if (nbIcon.getType() == TYPE_CLASSPATH) {
                String resName = nbIcon.getName();
                if (currentPackage != null && resName.startsWith(currentPackage))
                    return resName.substring(currentPackage.length() + 1);
                else
                    return resName;
            }
            else return nbIcon.getName();
        }
        return ""; // NOI18N
    }

    @Override
    public void setAsText(String string) throws IllegalArgumentException {
        setValue(createIconFromText(string));
    }

    @Override
    public String getJavaInitializationString() {
        if (getValue() instanceof NbImageIcon) {
            NbImageIcon ii = (NbImageIcon)getValue();
            switch (ii.type) {
                case TYPE_URL: return
                "new javax.swing.JLabel() {\n" + // NOI18N
                "  public javax.swing.Icon getIcon() {\n" + // NOI18N
                "    try {\n" + // NOI18N
                "      return new javax.swing.ImageIcon(\n" + // NOI18N
                "        new java.net.URL(\"" + convert(ii.name) + "\")\n" + // NOI18N
                "      );\n" + // NOI18N
                "    } catch (java.net.MalformedURLException e) {\n" + // NOI18N
                "    }\n" + // NOI18N
                "    return null;\n" + // NOI18N
                "  }\n" + // NOI18N
                "}.getIcon()"; // NOI18N
                case TYPE_FILE: return
                    "*/\n\\1NOI18N*/\n\\0" // NOI18N
                    + "new javax.swing.ImageIcon(\"" + convert(ii.name) + "\")"; // NOI18N
                case TYPE_CLASSPATH: return
                    "*/\n\\1NOI18N*/\n\\0" // NOI18N
                    + "new javax.swing.ImageIcon(getClass().getResource(\"/" + ii.name + "\"))"; // NOI18N
                // */\n\\1 is a special code mark for line comment
                // */\n\\0 is a special code mark to indicate that a real code follows
            }
        }
        return "null"; // NOI18N
    }

    /**
     * Duplicates backslashes in the input string.
     * @param s string to duplicate backslashes in
     * @return string with duplicated backslashes
     */
    private static String convert(String s) {
        StringTokenizer st = new StringTokenizer(s, "\\"); // NOI18N
        StringBuilder sb = new StringBuilder();
        if (st.hasMoreElements()) {
            sb.append(st.nextElement());
            while (st.hasMoreElements())
                sb.append("\\\\").append(st.nextElement()); // NOI18N
        }
        return sb.toString();
    }

    @Override
    public String[] getTags() {
        if (currentFiles == null)
            currentFiles = getAvailableFileNames();
        return currentFiles;
    }
    
    @Override
    public boolean supportsCustomEditor() {
        return true;
    }
    
    @Override
    public Component getCustomEditor() {
        CustomIconEditor customEditor = new CustomIconEditor(this);
        Object value = getValue();
        if (!(value instanceof NbImageIcon)) { // Issue 101318
            value = null;
        }
        customEditor.setValue((NbImageIcon)value);
        currentFiles = null; // hack - reset sometimes to read new folder content
        return customEditor;
    }

    // FormAwareEditor implementation
    @Override
    public void setContext(FormModel model, FormProperty prop) {
        if (model != null) { // might be null when loaded as constraints property of JTabbedPane's tab
            this.sourceFile = FormEditor.getFormDataObject(model).getPrimaryFile();
        }
        if (prop != null)
            prop.setValue("canEditAsText", true); // NOI18N
        if (currentPackage == null && sourceFile != null) {
            setCurrentPackage(getDefaultResourcePackage());
        }
    }

    // FormAwareEditor implementation
    @Override
    public void updateFormVersionLevel() {
    }

    // -----

    FileObject getSourceFile() {
        return sourceFile;
    }
    
    public void setSourceFile(FileObject sourceFile) {
        this.sourceFile = sourceFile;
    }
    
    public void setExternalIconsAllowed(boolean externalIconsAllowed) {
        this.externalIconsAllowed = externalIconsAllowed;
    }
    public boolean isExternalIconsAllowed() {
        return this.externalIconsAllowed;
    }

    /**
     * Returns the "current folder" which is used to resolve short file names,
     * its content is offered via getTags(), and it is also selected in the
     * custom editor.
     * @return the current folder used to pick image files from preferentially
     */
    public FileObject getCurrentFolder() {
        if (currentPackage != null) {
            FileObject srcFile = getSourceFile();
            FileObject folder = ClassPath.getClassPath(srcFile, ClassPath.SOURCE)
                    .findResource(currentPackage);
            if (folder == null)
                folder = ClassPath.getClassPath(srcFile, ClassPath.EXECUTE)
                    .findResource(currentPackage);
            return folder;
        }
        return null;
    }

    /**
     * Sets the "current folder" which is used to resolve short file names, its
     * content is offered via getTags(), and it is also selected in the custom
     * editor. The folder is kept as a resource name of the corresponding
     * package. It should be on the classpath of the source file's project.
     * It also gets set when a classpath-based icon is set (if null, it is
     * initially set to getDefaultResourceFolder).
     * @param folder the current preferred folder for images
     */
    public void setCurrentFolder(FileObject folder) {
        if (folder != null) {
            FileObject srcFile = getSourceFile();
            FileObject root = ClassPath.getClassPath(srcFile, ClassPath.SOURCE)
                    .findOwnerRoot(folder);
            if (root == null)
                root = ClassPath.getClassPath(srcFile, ClassPath.EXECUTE)
                    .findOwnerRoot(folder);
            if (root != null)
                setCurrentPackage(FileUtil.getRelativePath(root, folder));
        }
        else setCurrentPackage(null);
    }

    private void setCurrentPackage(String pkg) {
        currentPackage = pkg;
        currentFiles = null;
    }

    /**
     * @param resName resource name of an image file
     * @return String representing the package of the resource
     */
    private String getResourcePackage(String resName) {
        int i = resName.lastIndexOf('/');
        if (i < 0) // default package? don't even try
            return null;

        return resName.substring(0, i);
    }

    FileObject getDefaultResourceFolder() {
        FileObject srcFile = getSourceFile();
        for (String dir : DEFAULT_DIRS) {
            FileObject folder = srcFile.getParent().getFileObject(dir);
            if (folder != null)
                return folder;
        }
        return srcFile.getParent();
    }

    private String getDefaultResourcePackage() {
        FileObject folder = getDefaultResourceFolder();
        ClassPath cp = ClassPath.getClassPath(folder, ClassPath.SOURCE);
        FileObject root = cp.findOwnerRoot(folder);
        return FileUtil.getRelativePath(root, folder);
    }

    /**
     * @return names of files (without path) available in current folder
     */
    private String[] getAvailableFileNames() {
        FileObject folder = getCurrentFolder();
        if (folder != null) {
            List<String> list = new LinkedList<String>();
            for (FileObject fo : folder.getChildren()) {
                if (isImageFile(fo))
                    list.add(fo.getNameExt());
            }
            String[] fileNames = new String[list.size()];
            list.toArray(fileNames);
            Arrays.sort(fileNames);
            return fileNames;
        }
        return null;
    }

    static boolean isImageFile(FileObject fo) {
        return fo.isFolder() ? false : isImageFileName(fo.getNameExt());
    }

    static boolean isImageFileName(String name) {
        name = name.toLowerCase();
        return name.endsWith(".gif") || name.endsWith(".jpg") || name.endsWith(".png") // NOI18N
               || name.endsWith(".jpeg") || name.endsWith(".jpe"); // NOI18N
    }

    private NbImageIcon createIconFromText(String txt) {
        if (txt == null || "".equals(txt.trim())) // NOI18N
            return null;

        if (!txt.contains("/") && !txt.contains("\\") && !txt.contains(":")) { // NOI18N
             // just a file name within current folder 
            String pkg = currentPackage != null ? currentPackage : getDefaultResourcePackage();
            txt = pkg + "/" + txt; // NOI18N
        }

        NbImageIcon nbIcon = iconFromResourceName(txt);
        if (nbIcon != null)
            return nbIcon;

        nbIcon = iconFromURL(txt, true);
        if (nbIcon != null)
            return nbIcon;

        return iconFromFileName(txt);
    }

    private NbImageIcon iconFromResourceName(String resName) {
        return iconFromResourceName(resName, getSourceFile());
    }

    private static NbImageIcon iconFromResourceName(String resName, FileObject srcFile) {
        ClassPath cp = ClassPath.getClassPath(srcFile, ClassPath.SOURCE);
        FileObject fo = cp.findResource(resName);
        if (fo == null) {
            cp = ClassPath.getClassPath(srcFile, ClassPath.EXECUTE);
            fo = cp.findResource(resName);
        }
        if (fo != null) {
            try {
                try {
                    Image image = ImageIO.read(fo.getURL());
                    if (image != null) { // issue 157546
                        return new NbImageIcon(TYPE_CLASSPATH, resName, new ImageIcon(image));
                    }
                } catch (IllegalArgumentException iaex) { // Issue 178906
                    Logger.getLogger(IconEditor.class.getName()).log(Level.INFO, null, iaex);
                    return new NbImageIcon(TYPE_CLASSPATH, resName, new ImageIcon(fo.getURL()));
                }
            } catch (IOException ex) { // should not happen
                Logger.getLogger(IconEditor.class.getName()).log(Level.WARNING, null, ex);
            }
        }
        return null;
    }

    private NbImageIcon iconFromURL(String urlString, boolean forceURL) {
        try { // try as URL
            URL url = new URL(urlString);
            if (!forceURL) { // prefer definition as file
                try {
                    File f = new File(url.toURI());
                    if (f.exists()) { // it is a local file
                        String fileName = f.getAbsolutePath();
                        try {
                            Image image = ImageIO.read(new File(fileName));
                            if (image != null) {
                                return new NbImageIcon(TYPE_FILE, fileName, new ImageIcon(image));
                            } else {
                                return null; // not a valid image file
                            }
                        } catch (IOException ex) { // should not happen
                            Logger.getLogger(IconEditor.class.getName()).log(Level.WARNING, null, ex);
                        }
                    }
                }
                catch (URISyntaxException ex) {}
                catch (IllegalArgumentException ex) {}
            }

            if (url != null) { // treat as url
                Icon icon = null;
                try {
                    try {
                        Image image = ImageIO.read(url);
                        if (image != null) {
                            icon = new ImageIcon(image);
                        }
                    } catch (IllegalArgumentException iaex) { // Issue 178906
                        Logger.getLogger(IconEditor.class.getName()).log(Level.INFO, null, iaex);
                        icon = new ImageIcon(url);
                    }
                } catch (IOException ex) {}
                // for URL-based icon create NbImageIcon even if no icon can be loaded from the URL
                return new NbImageIcon(TYPE_URL, urlString, icon);
            }
        }
        catch (MalformedURLException ex) {}

        return null;
    }

    private NbImageIcon iconFromFileName(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            try {
                try {
                    Image image = ImageIO.read(file);
                    if (image != null) {
                        return new NbImageIcon(TYPE_FILE, fileName, new ImageIcon(image));
                    }
                } catch (IllegalArgumentException iaex) { // Issue 178906
                    Logger.getLogger(IconEditor.class.getName()).log(Level.INFO, null, iaex);
                    return new NbImageIcon(TYPE_FILE, fileName, new ImageIcon(fileName));
                }
            } catch (IOException ex) {
                Logger.getLogger(IconEditor.class.getName()).log(Level.INFO, null, ex);
            }
        }
        return null;
    }

    /**
     * A wrapper class for an icon value. It is public to be accessible by the
     * resource-aware IconEditor in editors2 package.
     */
    public static class NbImageIcon extends FormDesignValueAdapter {
        /** Source type of the icon (TYPE_CLASSPATH, TYPE_FILE, TYPE_URL). */
        private int type;
        /** Name of the icon (can be resource name, file name, url string - according to type). */
        private String name;
        /** The icon itself. */
        private Icon icon;
        
        public NbImageIcon(int type, String name, Icon icon) {
            this.type = type;
            if ((type == TYPE_CLASSPATH) && name.startsWith("/")) // NOI18N
                name = name.substring(1);
            this.name = name;
            this.icon = icon;
        }

        public int getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public Icon getIcon() {
            return icon;
        }

        // FormDesignValue implementation
        @Override
        public Object getDesignValue() {
            return icon;
        }

        // FormDesignValue implementation
        @Override
        public String getDescription() {
            return name;
        }
        
        // FormDesignValue implementation
        @Override
        public FormDesignValue copy(FormProperty formProperty) {
            if (type == TYPE_CLASSPATH) {
                // Issue 142337 - can copy into another project
                // => try to reload the icon from this project
                FormModel targetModel = formProperty.getPropertyContext().getFormModel();
                if (targetModel != null) {
                    FileObject sourceFile = FormEditor.getFormDataObject(targetModel).getPrimaryFile();
                    return iconFromResourceName(name, sourceFile);
                }
            }
            return new IconEditor.NbImageIcon(type, name, icon);
        }
    }

    // -----
    // XMLPropertyEditor

    /** Root of the XML representation of the icon. */
    public static final String XML_IMAGE = "Image"; // NOI18N
    /** Attribute holding icon type. */
    public static final String ATTR_TYPE = "iconType"; // NOI18N
    /** Attribute holding icon name. */
    public static final String ATTR_NAME = "name"; // NOI18N

    @Override
    public void readFromXML(org.w3c.dom.Node element) throws java.io.IOException {
        if (!XML_IMAGE.equals(element.getNodeName())) {
            throw new java.io.IOException();
        }
        org.w3c.dom.NamedNodeMap attributes = element.getAttributes();
        try {
            int type = Integer.parseInt(attributes.getNamedItem(ATTR_TYPE).getNodeValue());
            String name = attributes.getNamedItem(ATTR_NAME).getNodeValue();
            switch (type) {
                case 0:
                    setValue(null);
                    break;
                case TYPE_URL:
                    setValue(iconFromURL(name, false));
                    break;
                case TYPE_FILE:
                    setValue(iconFromFileName(name));
                    break;
                case TYPE_CLASSPATH:
                    if (name.startsWith("/")) {// NOI18N
                        name = name.substring(1);
                    }
                    NbImageIcon iconValue = iconFromResourceName(name);
                    if (iconValue == null && name.contains("/")) { // NOI18N
                        // likely the image file cannot be found, but we should keep the value
                        iconValue = new NbImageIcon(TYPE_CLASSPATH, name, null);
                    }
                    setValue(iconValue);
                    break;
            }
        } catch (NullPointerException e) {
            java.io.IOException ioe = new java.io.IOException();
            ErrorManager.getDefault().annotate(ioe, e);
            throw ioe;
        }
    }

    @Override
    public org.w3c.dom.Node storeToXML(org.w3c.dom.Document doc) {
        org.w3c.dom.Element el = doc.createElement(XML_IMAGE);
        Object value = getValue();
        if (value instanceof NbImageIcon) {
            NbImageIcon ii = (NbImageIcon) value;
            String name = ii.getName();
            if (ii.getType() == TYPE_CLASSPATH && !name.startsWith("/")) // NOI18N
                name = "/" + name; // NOI18N
            el.setAttribute(ATTR_TYPE, Integer.toString(ii.type));
            el.setAttribute(ATTR_NAME, name);
        } else {
            el.setAttribute(ATTR_TYPE, "0"); // NOI18N
            el.setAttribute(ATTR_NAME, "null"); // NOI18N
        }
        return el;
    }
    
}
