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

package org.netbeans.modules.form.palette;

import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import java.beans.*;
import java.util.*;

import org.openide.WizardDescriptor;
import org.openide.ErrorManager;
import org.openide.filesystems.*;
import org.netbeans.api.project.ant.*;
import org.netbeans.api.project.*;
import org.netbeans.modules.form.project.ClassSource;
import org.netbeans.spi.project.ui.support.ProjectChooser;
import org.openide.util.ChangeSupport;

/**
 * The first panel in the wizard for adding new components to the palette from
 * a project. In this panel the user chooses a project (as project folder
 * using the project chooser UI).
 *
 * @author Tomas Pavek
 */

class ChooseProjectWizardPanel implements WizardDescriptor.Panel<AddToPaletteWizard> {

    private JFileChooser projectChooser;
    private static String lastDirectoryUsed;

    private final ChangeSupport cs = new ChangeSupport(this);

    // ----------
    // WizardDescriptor.Panel implementation

    @Override
    public java.awt.Component getComponent() {
        if (projectChooser == null) { // create the UI component for the wizard step
            projectChooser = ProjectChooser.projectChooser();
            projectChooser.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

            // wizard API: set the caption and index of this panel
            projectChooser.setName(PaletteUtils.getBundleString("CTL_SelectProject_Caption")); // NOI18N
            projectChooser.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, // NOI18N
                                             new Integer(0));

            if (lastDirectoryUsed != null)
                projectChooser.setCurrentDirectory(new File(lastDirectoryUsed));
            projectChooser.setControlButtonsAreShown(false);

            projectChooser.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent ev) {
                    String propName = ev.getPropertyName();
                    if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(propName)
                         || JFileChooser.DIRECTORY_CHANGED_PROPERTY.equals(propName))
                        cs.fireChange();
                }
            });
        }

        return projectChooser;
    }

    @Override
    public org.openide.util.HelpCtx getHelp() {
        // PENDING
        return new org.openide.util.HelpCtx("beans.adding"); // NOI18N
    }

    @Override
    public boolean isValid() {
        if (projectChooser != null) {
            File file = projectChooser.getSelectedFile();
            if (file != null) {
                FileObject projectDir = FileUtil.toFileObject(FileUtil.normalizeFile(file));
                if (projectDir != null) {
                    try {
                        Project project = ProjectManager.getDefault()
                                                   .findProject(projectDir);
                        if (project != null) { // it is a project directory
                            lastDirectoryUsed = projectChooser.getCurrentDirectory()
                                                               .getAbsolutePath();
                            return true;
                        }
                    }
                    catch (IOException ex) {} // ignore
                }
            }
        }
        return false;
    }

    @Override
    public void readSettings(AddToPaletteWizard settings) {
    }

    @Override
    public void storeSettings(AddToPaletteWizard settings) {
        if (projectChooser == null)
            return;

        File file = projectChooser.getSelectedFile();
        if (file == null) {
            return;
        }
        FileObject projectDir = FileUtil.toFileObject(FileUtil.normalizeFile(file));
        if (projectDir == null) {
            return;
        }

        Project project = null;
        try {
            project = ProjectManager.getDefault().findProject(projectDir);
        }
        catch (IOException ex) {
            ErrorManager.getDefault().notify(ErrorManager.INFORMATIONAL, ex);
        }
        if (project == null)
            return;

        List<ClassSource.ProjectEntry> entries = new ArrayList<ClassSource.ProjectEntry>();
        for (AntArtifact aa : AntArtifactQuery.findArtifactsByType(project, /* XXX JavaProjectConstants.ARTIFACT_TYPE_JAR */ "jar")) { // NOI18N
            entries.add(new ClassSource.ProjectEntry(aa));
        }
        settings.setJARFiles(entries);
    }

    @Override
    public void addChangeListener(ChangeListener listener) {
        cs.addChangeListener(listener);
    }

    @Override
    public void removeChangeListener(ChangeListener listener) {
        cs.removeChangeListener(listener);
    }

}
