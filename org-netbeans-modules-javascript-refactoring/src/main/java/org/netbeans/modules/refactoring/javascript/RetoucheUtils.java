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
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
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

package org.netbeans.modules.refactoring.javascript;

import java.awt.Color;
import java.io.CharConversionException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.swing.text.AttributeSet;
import javax.swing.text.StyleConstants;
import org.mozilla.nb.javascript.Node;
import org.netbeans.api.editor.mimelookup.MimeLookup;
import org.netbeans.api.editor.mimelookup.MimePath;
import org.netbeans.api.editor.settings.FontColorSettings;
import org.netbeans.editor.BaseDocument;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenId;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.Sources;
import org.netbeans.api.project.ui.OpenProjects;
import org.netbeans.modules.javascript.editing.AstUtilities;
import org.netbeans.modules.javascript.editing.JsClassPathProvider;
import org.netbeans.modules.javascript.editing.JsParseResult;
import org.netbeans.modules.javascript.editing.JsUtils;
import org.netbeans.modules.javascript.editing.lexer.JsTokenId;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.parsing.spi.indexing.support.QuerySupport;
import org.openide.filesystems.FileObject;
import org.openide.util.Lookup;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.text.CloneableEditorSupport;
import org.openide.util.Exceptions;
import org.openide.xml.XMLUtil;

/**
 * Various utilies related to Js refactoring; the generic ones are based
 * on the ones from the Java refactoring module.
 * 
 * @author Jan Becicka
 * @author Tor Norbye
 */
public class RetoucheUtils {
    private RetoucheUtils() {
    }

    public static boolean isJsFile(FileObject fo) {
        // XXX: parsingapi; this should somehow be available in Parsing API
        //return LanguageRegistry.getInstance().isRelevantFor(fo, JsTokenId.JAVASCRIPT_MIME_TYPE);

        // these are the mimetypes for which JsEmbeddingProvider is registered
        return JsUtils.isJsFile(fo)
                || fo.getMIMEType().equals("application/x-httpd-eruby") //NOI18N
                || fo.getMIMEType().equals("text/html")  //NOI18N
                || fo.getMIMEType().equals("text/x-jsp")  //NOI18N
                || fo.getMIMEType().equals("text/x-tag")  //NOI18N
                || fo.getMIMEType().equals("text/x-php5");  //NOI18N
    }

    public static BaseDocument getDocument(Parser.Result info) {
        BaseDocument doc = null;

        if (info != null) {
            doc = (BaseDocument) info.getSnapshot().getSource().getDocument(true);
        }

        return doc;
    }
    
    
    /** Compute the names (full and simple, e.g. Foo::Bar and Bar) for the given node, if any, and return as 
     * a String[2] = {name,simpleName} */
    public static String[] getNodeNames(Node node) {
        String name = null;
        String simpleName = null;
        int type = node.getType();
        if (type == org.mozilla.nb.javascript.Token.CALL || type == org.mozilla.nb.javascript.Token.NEW) {
            name = AstUtilities.getCallName(node, true);
            simpleName = AstUtilities.getCallName(node, false);
        } else if (node instanceof Node.StringNode) {
            name = node.getString();
        } else if (node.getType() == org.mozilla.nb.javascript.Token.FUNCTION) {
            name = AstUtilities.getFunctionFqn(node, null);
            if (name != null && name.indexOf('.') != -1) {
                name = name.substring(name.indexOf('.')+1);
            }
        } else {
            return new String[] { null, null};
        }
        // TODO - FUNCTION - also get full name!
        
        if (simpleName == null) {
            simpleName = name;
        }
        
        return new String[] { name, simpleName };
    }

    public static CloneableEditorSupport findCloneableEditorSupport(JsParseResult info) {
        DataObject dob = null;
        try {
            dob = DataObject.find(info.getSnapshot().getSource().getFileObject());
        } catch (DataObjectNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
        return RetoucheUtils.findCloneableEditorSupport(dob);
    }

    public static CloneableEditorSupport findCloneableEditorSupport(DataObject dob) {
        Object obj = dob.getCookie(org.openide.cookies.OpenCookie.class);
        if (obj instanceof CloneableEditorSupport) {
            return (CloneableEditorSupport)obj;
        }
        obj = dob.getCookie(org.openide.cookies.EditorCookie.class);
        if (obj instanceof CloneableEditorSupport) {
            return (CloneableEditorSupport)obj;
        }
        return null;
    }

    public static String htmlize(String input) {
        try {
            return XMLUtil.toElementContent(input);
        } catch (CharConversionException cce) {
            Exceptions.printStackTrace(cce);
            return input;
        }
    }

//    /** Return the most distant method in the hierarchy that is overriding the given method, or null */
//    public static IndexedMethod getOverridingMethod(JsElementCtx element, CompilationInfo info) {
//        JsIndex index = JsIndex.get(info.getIndex());
//        String fqn = AstUtilities.getFqnName(element.getPath());
//
//        return index.getOverridingMethod(fqn, element.getName());
//    }

    public static String getHtml(String text) {
        StringBuffer buf = new StringBuffer();
        // TODO - check whether we need Js highlighting or rhtml highlighting
        TokenHierarchy tokenH = TokenHierarchy.create(text, JsTokenId.language());
        Lookup lookup = MimeLookup.getLookup(MimePath.get(JsTokenId.JAVASCRIPT_MIME_TYPE));
        FontColorSettings settings = lookup.lookup(FontColorSettings.class);
        @SuppressWarnings("unchecked")
        TokenSequence<? extends TokenId> tok = tokenH.tokenSequence();
        while (tok.moveNext()) {
            Token<? extends TokenId> token = tok.token();
            String category = token.id().name();
            AttributeSet set = settings.getTokenFontColors(category);
            if (set == null) {
                category = token.id().primaryCategory();
                if (category == null) {
                    category = "whitespace"; //NOI18N
                }
                set = settings.getTokenFontColors(category);                
            }
            String tokenText = htmlize(token.text().toString());
            buf.append(color(tokenText, set));
        }
        return buf.toString();
    }

    private static String color(String string, AttributeSet set) {
        if (set==null) {
            return string;
        }
        if (string.trim().length() == 0) {
            return string.replace(" ", "&nbsp;").replace("\n", "<br>"); //NOI18N
        } 
        StringBuffer buf = new StringBuffer(string);
        if (StyleConstants.isBold(set)) {
            buf.insert(0,"<b>"); //NOI18N
            buf.append("</b>"); //NOI18N
        }
        if (StyleConstants.isItalic(set)) {
            buf.insert(0,"<i>"); //NOI18N
            buf.append("</i>"); //NOI18N
        }
        if (StyleConstants.isStrikeThrough(set)) {
            buf.insert(0,"<s>"); // NOI18N
            buf.append("</s>"); // NOI18N
        }
        buf.insert(0,"<font color=" + getHTMLColor(StyleConstants.getForeground(set)) + ">"); //NOI18N
        buf.append("</font>"); //NOI18N
        return buf.toString();
    }
    
    private static String getHTMLColor(Color c) {
        String colorR = "0" + Integer.toHexString(c.getRed()); //NOI18N
        colorR = colorR.substring(colorR.length() - 2); 
        String colorG = "0" + Integer.toHexString(c.getGreen()); //NOI18N
        colorG = colorG.substring(colorG.length() - 2);
        String colorB = "0" + Integer.toHexString(c.getBlue()); //NOI18N
        colorB = colorB.substring(colorB.length() - 2);
        String html_color = "#" + colorR + colorG + colorB; //NOI18N
        return html_color;
    }

    public static boolean isFileInOpenProject(FileObject file) {
        assert file != null;
        Project p = FileOwnerQuery.getOwner(file);
        return OpenProjects.getDefault().isProjectOpen(p);
    }
    
    public static boolean isOnSourceClasspath(FileObject fo) {
        Project p = FileOwnerQuery.getOwner(fo);
        if (p==null) {
            return false;
        }
        Project[] opened = OpenProjects.getDefault().getOpenProjects();
        for (int i = 0; i<opened.length; i++) {
            if (p.equals(opened[i]) || opened[i].equals(p)) {
                //SourceGroup[] gr = ProjectUtils.getSources(p).getSourceGroups(JsProject.SOURCES_TYPE_Js);
                SourceGroup[] gr = ProjectUtils.getSources(p).getSourceGroups(Sources.TYPE_GENERIC);
                for (int j = 0; j < gr.length; j++) {
                    if (fo==gr[j].getRootFolder()) {
                        return true;
                    }
                    if (FileUtil.isParentOf(gr[j].getRootFolder(), fo)) {
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    public static boolean isRefactorable(FileObject file) {
        return isJsFile(file) && isFileInOpenProject(file) && isOnSourceClasspath(file);
    }

// XXX: parsingapi
//    public static boolean isClasspathRoot(FileObject fo) {
//        ClassPath cp = ClassPath.getClassPath(fo, ClassPath.SOURCE);
//        if (cp != null) {
//            FileObject f = cp.findOwnerRoot(fo);
//            if (f != null) {
//                return fo.equals(f);
//            }
//        }
//
//        return false;
//    }

    public static String getPackageName(FileObject folder) {
        assert folder.isFolder() : "argument must be folder"; //NOI18N
        Project p = FileOwnerQuery.getOwner(folder);
        if (p != null) {
            Sources s = ProjectUtils.getSources(p);
            for(SourceGroup g : s.getSourceGroups(Sources.TYPE_GENERIC)) {
                String relativePath = FileUtil.getRelativePath(g.getRootFolder(), folder);
                if (relativePath != null) {
                    return relativePath.replace('/', '.'); //NOI18N
                }
            }
        }
        return ""; //NOI18N
    }

// XXX: parsingapi
//    public static FileObject getClassPathRoot(URL url) throws IOException {
//        FileObject result = URLMapper.findFileObject(url);
//        File f = FileUtil.normalizeFile(new File(url.getPath()));
//        while (result==null) {
//            result = FileUtil.toFileObject(f);
//            f = f.getParentFile();
//        }
//        return ClassPath.getClassPath(result, ClassPath.SOURCE).findOwnerRoot(result);
//    }
//
//    public static ClasspathInfo getClasspathInfoFor(FileObject ... files) {
//        assert files.length >0;
//        Set<URL> dependentRoots = new HashSet<URL>();
//        for (FileObject fo: files) {
//            Project p = null;
//            if (fo!=null) {
//                p = FileOwnerQuery.getOwner(fo);
//            }
//            if (p!=null) {
//                ClassPath classPath = ClassPath.getClassPath(fo, ClassPath.SOURCE);
//                if (classPath == null) {
//                    return null;
//                }
//                FileObject ownerRoot = classPath.findOwnerRoot(fo);
//                if (ownerRoot != null) {
//                    URL sourceRoot = URLMapper.findURL(ownerRoot, URLMapper.INTERNAL);
//                    dependentRoots.addAll(SourceUtils.getDependentRoots(sourceRoot));
//                    //for (SourceGroup root:ProjectUtils.getSources(p).getSourceGroups(JsProject.SOURCES_TYPE_Js)) {
//                    for (SourceGroup root:ProjectUtils.getSources(p).getSourceGroups(Sources.TYPE_GENERIC)) {
//                        dependentRoots.add(URLMapper.findURL(root.getRootFolder(), URLMapper.INTERNAL));
//                    }
//                } else {
//                    dependentRoots.add(URLMapper.findURL(fo.getParent(), URLMapper.INTERNAL));
//                }
//            } else {
//                for(ClassPath cp: GlobalPathRegistry.getDefault().getPaths(ClassPath.SOURCE)) {
//                    for (FileObject root:cp.getRoots()) {
//                        dependentRoots.add(URLMapper.findURL(root, URLMapper.INTERNAL));
//                    }
//                }
//            }
//        }
//
//        ClassPath rcp = ClassPathSupport.createClassPath(dependentRoots.toArray(new URL[dependentRoots.size()]));
//        ClassPath nullPath = ClassPathSupport.createClassPath(new FileObject[0]);
//        ClassPath boot = files[0]!=null?ClassPath.getClassPath(files[0], ClassPath.BOOT):nullPath;
//        ClassPath compile = files[0]!=null?ClassPath.getClassPath(files[0], ClassPath.COMPILE):nullPath;
//
//        if (boot == null || compile == null) { // 146499
//            return null;
//        }
//
//        ClasspathInfo cpInfo = ClasspathInfo.create(boot, compile, rcp);
//        return cpInfo;
//    }
//
//    public static ClasspathInfo getClasspathInfoFor(JsElementCtx ctx) {
//        return getClasspathInfoFor(ctx.getFileObject());
//    }
//
    public static Set<FileObject> getJsFilesInProject(FileObject fileInProject) {
        return getJsFilesInProject(fileInProject, false);
    }

    public static Set<FileObject> getJsFilesInProject(FileObject fileInProject, boolean excludeReadOnlySourceRoots) {
        Set<FileObject> files = new HashSet<FileObject>(100);
        Collection<FileObject> sourceRoots = QuerySupport.findRoots(fileInProject,
                null,
                Collections.singleton(JsClassPathProvider.BOOT_CP),
                Collections.<String>emptySet());
        for (FileObject root : sourceRoots) {
            if(excludeReadOnlySourceRoots && !root.canWrite()) {
                continue; //skip read only source roots
            }
            String name = root.getName();
            // Skip non-refactorable parts in renaming
            if (name.equals("vendor") || name.equals("script")) { // NOI18N
                continue;
            }
            addJsFiles(files, root);
        }

        return files;
    }

    private static void addJsFiles(Set<FileObject> files, FileObject f) {
        if (f.isFolder()) {
            for (FileObject child : f.getChildren()) {
                addJsFiles(files, child);
            }
        } else if (isJsFile(f)) {
            files.add(f);
        }
    }
}
