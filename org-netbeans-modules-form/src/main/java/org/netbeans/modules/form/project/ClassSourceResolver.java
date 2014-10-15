/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2011 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development and
 * Distribution License("CDDL") (collectively, the "License"). You may not use
 * this file except in compliance with the License. You can obtain a copy of
 * the License at http://www.netbeans.org/cddl-gplv2.html or
 * nbbuild/licenses/CDDL-GPL-2-CP. See the License for the specific language
 * governing permissions and limitations under the License. When distributing
 * the software, include this License Header Notice in each file and include
 * the License file at nbbuild/licenses/CDDL-GPL-2-CP. Oracle designates this
 * particular file as subject to the "Classpath" exception as provided by
 * Oracle in the GPL Version 2 section of the License file that accompanied
 * this code. If applicable, add the following below the License Header, with
 * the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license." If you do not indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to its
 * licensees as provided above. However, if you add GPL Version 2 code and
 * therefore, elected the GPL Version 2 license, then the option applies only
 * if the new code is made subject to such option by the copyright holder.
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2011 Sun Microsystems, Inc.
 */
package org.netbeans.modules.form.project;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.api.project.libraries.Library;
import org.netbeans.api.project.libraries.LibraryManager;
import org.netbeans.modules.form.project.ClassSource.Entry;
import org.netbeans.spi.project.libraries.LibraryFactory;
import org.netbeans.spi.project.libraries.LibraryImplementation;
import org.netbeans.spi.project.libraries.support.LibrariesSupport;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

/**
 * NetBeans {@code ClassSource.Resolver}.
 *
 * @author Jan Stola
 */
@ServiceProvider(service=ClassSource.Resolver.class)
public class ClassSourceResolver implements ClassSource.Resolver {
    private static final String TYPE_JAR = "jar"; // NOI18N
    private static final String TYPE_LIBRARY = "library"; // NOI18N
    private static final String TYPE_PROJECT = "project"; // NOI18N
    private static final String TYPE_NAMED_DEPENDENCY ="dependency"; // only for NBM projects // NOI18N

    @Override
    public Entry resolve(String type, String name) {
        if (type.equals(TYPE_JAR)) {
            return new JarEntry(new File(name));
        } else if (type.equals(TYPE_LIBRARY)) {
            Library lib;
            int hash = name.indexOf('#');
            if (hash != -1) {
                try {
                    lib = LibraryManager.forLocation(new URL(name.substring(0, hash))).getLibrary(name.substring(hash + 1));
                } catch (IllegalArgumentException x) {
                    Exceptions.printStackTrace(x);
                    return null;
                } catch (MalformedURLException x) {
                    Exceptions.printStackTrace(x);
                    return null;
                }
            } else {
                lib = LibraryManager.getDefault().getLibrary(name);
            }
            return lib != null ? new LibraryEntry(lib) : null;
        } else if (type.equals(TYPE_PROJECT)) {
            File file = new File(name);
            file = FileUtil.normalizeFile(file);
            if (file.isDirectory()) {
                FileObject fob = FileUtil.toFileObject(file);
                if (fob != null) {
                    try {
                        Project project = ProjectManager.getDefault().findProject(fob);
                        if (project != null) {
                            return new ProjectEntry(project);
                        }
                    } catch (IOException ioex) {
                        Exceptions.printStackTrace(ioex);
                        return null;
                    }
                }
            }
            // Backward compatibility for previously used ant artifacts. It resembles
            // AntArtifactQuery.findArtifactFromFile(file).getProject()
            while ((file != null) && !file.exists()) {
                // Find some existing parent directory (hopefully in the same
                // project) if the artifact is cleaned.
                file = file.getParentFile();
            }
            if (file != null) { // Issue 194202
                FileObject fob = FileUtil.toFileObject(file);
                if (fob != null) {
                    Project project = FileOwnerQuery.getOwner(fob);
                    if (project != null) {
                        return new ProjectEntry(project);
                    }
                }
            }
            return null;
        } else if (type.equals(TYPE_NAMED_DEPENDENCY)) {
            Library lib = null;
            LibraryImplementation libImpl = LibrariesSupport.createLibraryImplementation("j2se", new String[] {"classpath"}); // NOI18N
            libImpl.setName(name);
            try {
                libImpl.setContent("classpath", Collections.singletonList(
                        new URL("jar:nbinst://"+name+"/modules/"+name.replace('.', '-')+".jar!/"))); // NOI18N
                lib = LibraryFactory.createLibrary(libImpl);
            } catch (MalformedURLException ex) {}
            return lib != null ? new LibraryEntry(lib) : null;
        } else {
            return null;
        }
    }

    private static URL translateURL(URL u) {
        if (FileUtil.isArchiveFile(u)) {
            return FileUtil.getArchiveRoot(u);
        } else {
            return u;
        }
    }

    /** Entry based on a single JAR file. */
    public static final class JarEntry extends Entry {
        private final File jar;
        public JarEntry(File jar) {
            assert jar != null;
            this.jar = jar;
        }
        public File getJar() {
            return jar;
        }
        @Override
        public List<URL> getClasspath() {
            try {
                return Collections.singletonList(translateURL(jar.toURI().toURL()));
            } catch (MalformedURLException x) {
                assert false : x;
                return Collections.emptyList();
            }
        }
        @Override
        public Boolean addToProjectClassPath(FileObject projectArtifact, String classPathType) throws IOException, UnsupportedOperationException {
            URL u = jar.toURI().toURL();
            FileObject jarFile = FileUtil.toFileObject(jar);
            if (jarFile == null) {
                return Boolean.FALSE; // Issue 147451
            }
            if (FileUtil.isArchiveFile(jarFile)) {
                u = FileUtil.getArchiveRoot(u);
            }
            // A
            return false;
            //return Boolean.valueOf(ProjectClassPathModifier.addRoots(new URL[] {u}, projectArtifact, classPathType));
        }
        @Override
        public String getDisplayName() {
            return NbBundle.getMessage(ClassSource.class, "FMT_JarSource", jar.getAbsolutePath());
        }
        @Override
        public String getPicklingType() {
            return TYPE_JAR;
        }
        @Override
        public String getPicklingName() {
            return jar.getAbsolutePath();
        }
    }

    /** Entry based on a (global or project) library. */
    public static final class LibraryEntry extends Entry {
        private final Library lib;
        public LibraryEntry(Library lib) {
            assert lib != null;
            this.lib = lib;
        }
        public Library getLibrary() {
            return lib;
        }
        @Override
        public List<URL> getClasspath() {
            // No need to translate to jar protocol; Library.getContent should have done this already.
            return lib.getContent("classpath"); // NOI18N
        }
        @Override
        public Boolean addToProjectClassPath(FileObject projectArtifact, String classPathType) throws IOException, UnsupportedOperationException {
            // A
            return false;
            //return  Boolean.valueOf(ProjectClassPathModifier.addLibraries(new Library[] {lib}, projectArtifact, classPathType));
        }
        @Override
        public String getDisplayName() {
            return NbBundle.getMessage(ClassSource.class, "FMT_LibrarySource", lib.getDisplayName());
        }
        @Override
        public String getPicklingType() {
            return TYPE_LIBRARY;
        }
        @Override
        public String getPicklingName() {
            // For backward compatibility with old *.palette_item files, treat bare names as global libraries.
            // Project libraries are given as e.g. "file:/some/where/libs/index.properties#mylib"
            LibraryManager mgr = lib.getManager();
            if (mgr == LibraryManager.getDefault() || mgr == null) {
                return lib.getName();
            } else {
                return mgr.getLocation() + "#" + lib.getName(); // NOI18N
            }
        }
    }

    public static final class ProjectEntry extends Entry {
        private final Project project;

        public ProjectEntry(Project project) {
            assert project != null;
            this.project = project;
        }

        @Override
        public List<URL> getClasspath() {
            // A
            return Collections.emptyList();
            /*Sources sources = ProjectUtils.getSources(project);
            SourceGroup[] sgs = sources.getSourceGroups(JavaProjectConstants.SOURCES_TYPE_JAVA);
            List<URL> list = new ArrayList<URL>();
            for (SourceGroup sg : sgs) {
                try {
                    ClassPath cp = ClassPath.getClassPath(sg.getRootFolder(), ClassPath.SOURCE);
                    if (cp != null) {
                        for (FileObject fob : cp.getRoots()) {
                            URL[] urls = UnitTestForSourceQuery.findSources(fob);
                            if (urls.length == 0) {
                                BinaryForSourceQuery.Result result = BinaryForSourceQuery.findBinaryRoots(fob.getURL());
                                list.addAll(Arrays.asList(result.getRoots()));
                            }
                        }
                    }
                } catch (FileStateInvalidException fsiex) {
                    FormUtils.LOGGER.log(Level.INFO, fsiex.getMessage(), fsiex);
                }
            }
            return list;*/
        }
        @Override
        public Boolean addToProjectClassPath(FileObject projectArtifact, String classPathType) throws IOException, UnsupportedOperationException {
            // A
            /*if (project != FileOwnerQuery.getOwner(projectArtifact)) {
                return Boolean.valueOf(ProjectClassPathModifier.addProjects(new Project[] {project}, projectArtifact, classPathType));
            }*/
            return Boolean.FALSE;
        }
        @Override
        public String getDisplayName() {
            return NbBundle.getMessage(ClassSource.class, "FMT_ProjectSource", // NOI18N
                    FileUtil.getFileDisplayName(project.getProjectDirectory()));
        }
        @Override
        public String getPicklingType() {
            return TYPE_PROJECT;
        }
        @Override
        public String getPicklingName() {
            return FileUtil.toFile(project.getProjectDirectory()).getAbsolutePath();
        }
    }
    
}
