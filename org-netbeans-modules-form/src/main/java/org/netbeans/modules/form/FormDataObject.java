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

import java.io.IOException;
import java.util.List;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.model.*;
import org.openide.cookies.EditCookie;
import org.openide.cookies.OpenCookie;
import org.openide.filesystems.FileObject;
import org.openide.loaders.*;
import org.openide.nodes.Node;
import org.openide.nodes.Node.Cookie;
import org.openide.util.Lookup;

/** The DataObject for forms.
 *
 * @author Ian Formanek, Petr Hamernik
 */
public class FormDataObject extends MultiDataObject {
    /** generated Serialized Version UID */
    static final long serialVersionUID = -975322003627854168L;

    //--------------------------------------------------------------------
    // Private variables

//    /** If true, a postInit method is called after reparsing - used after createFromTemplate */
//    transient private boolean templateInit;
//    /** If true, the form is marked as modified after regeneration - used if created from template */
//    transient private boolean modifiedInit;
//    /** A flag to prevent multiple registration of ComponentRefListener */
//    transient private boolean componentRefRegistered;

    transient private FormEditorSupport formEditor;

    transient private OpenEdit openEdit;

    //--------------------------------------------------------------------
    // Constructors

    public FormDataObject(FileObject pFo, FormDataLoader pLoader)
        throws DataObjectExistsException
    {
        super(pFo, pLoader);
        getCookieSet().assign( SaveAsCapable.class, new SaveAsCapable() {
            @Override
            public void saveAs(FileObject folder, String fileName) throws IOException {
                getFormEditorSupport().saveAs( folder, fileName );
            }
        });
      List<Cookie> cookies = NbAditoInterface.lookup(IAditoModelDataProvider.class).getCookies(this);
      for (Cookie cookie : cookies)
        getCookieSet().add(cookie);
    }

    //--------------------------------------------------------------------
    // Other methods

    @Override
    public <T extends Cookie> T getCookie(Class<T> type) {
        T retValue;

        if (OpenCookie.class.equals(type) || EditCookie.class.equals(type)) {
            if (openEdit == null)
                openEdit = new OpenEdit();
            retValue = type.cast(openEdit);
        } else if (type.isAssignableFrom(FormEditorSupport.class)) {
            retValue = (T) getFormEditorSupport();
        } else {
            retValue = super.getCookie(type);
        }
        return retValue;
    }

    @Override
    public Lookup getLookup() {
        return isValid() ? getNodeDelegate().getLookup() : Lookup.EMPTY;
    }

    private class OpenEdit implements OpenCookie, EditCookie {
        @Override
        public void open() {
            // open form editor with form designer selected
            getFormEditorSupport().openFormEditor(true);
        }
        @Override
        public void edit() {
            // open form editor with java editor selected (form not loaded)
            getFormEditorSupport().open();
        }
    }

    public FileObject getFormFile() {
        return getPrimaryEntry().getFile();
    }

    public boolean isReadOnly() {
        return !getPrimaryFile().canWrite();
    }

    public boolean formFileReadOnly() {
        return isReadOnly();
    }

    public synchronized FormEditorSupport getFormEditorSupport() {
        if (formEditor == null) {
            formEditor = new FormEditorSupport(this, getCookieSet());
        }
        return formEditor;
    }

    // PENDING remove when form_new_layout is merged to trunk
    public FormEditorSupport getFormEditor() {
        return getFormEditorSupport();
    }
    // END of PENDING

    /** Provides node that should represent this data object. When a node for
     * representation in a parent is requested by a call to getNode(parent) it
     * is the exact copy of this node with only parent changed. This
     * implementation creates instance <CODE>DataNode</CODE>.  <P> This method
     * is called only once.
     *
     * @return the node representation for this data object
     * @see FormDataNode
     */
    @Override
    protected Node createNodeDelegate() {
      return new FormDataNode(this);
    }

    //--------------------------------------------------------------------
    // Serialization

    private void readObject(java.io.ObjectInputStream is)
        throws java.io.IOException, ClassNotFoundException {
        is.defaultReadObject();
    }

    @Override
    protected DataObject handleCopyRename(DataFolder df, String name, String ext) throws IOException {
        FileObject fo = getPrimaryEntry().copyRename (df.getPrimaryFile (), name, ext);
        return DataObject.find( fo );
    }

}
