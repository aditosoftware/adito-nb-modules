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

import javax.swing.Action;
//import org.netbeans.api.java.loaders.JavaDataSupport;
import de.adito.aditoweb.designer.filetype.DataModelDataNode;
import org.openide.filesystems.FileObject;
import org.openide.loaders.*;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;

import org.openide.util.actions.SystemAction;

/** The DataNode for Forms.
 *
 * @author Ian Formanek
 */
public class FormDataNode extends FilterNode {
    /** generated Serialized Version UID */
    //  static final long serialVersionUID = 1795549004166402392L;

    /** Icon base for form data objects. */
    private static final String FORM_ICON_BASE = "org/netbeans/modules/form/resources/form.gif"; // NOI18N

    /** Constructs a new FormDataObject for specified primary file
     * 
     * @param fdo form data object
     */
    public FormDataNode(FormDataObject fdo) {
//        this(JavaDataSupport.createJavaNode(fdo.getPrimaryFile())); // TODO: stripped
      this(createNode(fdo.getPrimaryFile()));
    }
    
    private FormDataNode(Node orig) {
        super(orig);
        ((AbstractNode) orig).setIconBaseWithExtension(FORM_ICON_BASE);
    }
    
    @Override
    public Action getPreferredAction() {
        // issue 56351
        return new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                FormEditorSupport supp = getCookie(FormEditorSupport.class);
                supp.openFormEditor(false);
            }
        };
    }
    
    @Override
    public Action[] getActions(boolean context) {
        Action[] javaActions = super.getActions(context);
        Action[] formActions = new Action[javaActions.length+2];
        formActions[0] = SystemAction.get(org.openide.actions.OpenAction.class);
        formActions[1] = SystemAction.get(org.openide.actions.EditAction.class);
        formActions[2] = null;
        // Skipping the first (e.g. Open) action
        System.arraycopy(javaActions, 1, formActions, 3, javaActions.length-1);
        return formActions;
    }

  public static Node createNode(FileObject pBaseDataobject)
  {
    try
    {
      DataObject bdo = DataObject.find(pBaseDataobject);
      return new DataModelDataNode(bdo);
    }
    catch (DataObjectNotFoundException ex)
    {
      return new AbstractNode(Children.LEAF);
    }
  }

}