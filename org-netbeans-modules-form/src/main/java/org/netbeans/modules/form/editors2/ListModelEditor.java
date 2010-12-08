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

package org.netbeans.modules.form.editors2;

import java.awt.Component;
import javax.swing.*;
import org.netbeans.modules.form.NamedPropertyEditor;
import org.netbeans.modules.form.editors.StringArrayCustomEditor;
import org.netbeans.modules.form.editors.StringArrayEditor;
import org.openide.util.NbBundle;

/** A simple property editor for ListModel.
 *
 * @author Tomas Pavek
 */

public class ListModelEditor extends StringArrayEditor implements NamedPropertyEditor {

    private ListModel listModel = null;

    @Override
    public void setValue(Object val) {
        if (val instanceof ListModel) {
            listModel = (ListModel) val;
            super.setValue(getDataFromModel(listModel));
        }
        else if (val instanceof String[]) {
            listModel = getModelForData((String[])val);
            super.setValue(val);
        }
        else {
            listModel = getModelForData(new String[0]);
            super.setValue(null);
        }
    }

    @Override
    public Object getValue() {
        return listModel;
    }

    @Override
    public void setStringArray(String[] value) {
        listModel = getModelForData(value);
        super.setValue(value);
    }

    @Override
    public String[] getStringArray () {
        return (String[])super.getValue ();
    }

    @Override
    public String getJavaInitializationString() {
        if (getStrings(true).equals(""))
            return null;
        StringBuilder buf = new StringBuilder("new javax.swing.AbstractListModel() {\n"); // NOI18N
        buf.append("String[] strings = { "); // NOI18N
        buf.append(getStrings(true));
        buf.append(" };\n"); // NOI18N
        buf.append("public int getSize() { return strings.length; }\n"); // NOI18N
        buf.append("public Object getElementAt(int i) { return strings[i]; }\n"); // NOI18N
        buf.append("}"); // NOI18N

        return buf.toString();
    }

    static String[] getDataFromModel(ListModel model) {
        String[] data = new String[model.getSize()];
        for (int i=0; i < data.length; i++) {
            Object obj = model.getElementAt(i);
            data[i] = obj instanceof String ? (String) obj : ""; // NOI18N
        }
        return data;
    }

    static ListModel getModelForData(String[] data) {
        DefaultListModel model = new DefaultListModel();
        for (int i=0; i < data.length; i++)
            model.addElement(data[i]);
        return model;
    }

    /**
     * Returns human-readable name of this property editor.
     * 
     * @return human-readable name of this property editor.
     */
    @Override
    public String getDisplayName() {
        return NbBundle.getBundle(ListModelEditor.class).getString("CTL_ListModelEditor_DisplayName"); // NOI18N
    }
    
    @Override
    public Component getCustomEditor() {
        return new StringArrayCustomEditor(
                this,
                NbBundle.getMessage(
                    ListModelEditor.class,
                    "ListModelEditor.label.text")
                );  // NOI18N
    }
}
