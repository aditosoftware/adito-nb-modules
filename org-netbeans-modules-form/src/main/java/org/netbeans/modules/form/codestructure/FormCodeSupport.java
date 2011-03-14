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

package org.netbeans.modules.form.codestructure;

import java.beans.PropertyEditor;
import org.openide.nodes.Node;
import org.netbeans.modules.form.*;

/**
 * @author Tomas Pavek
 */

public class FormCodeSupport {

    public static CodeExpressionOrigin createOrigin(Node.Property property) {
        if (property instanceof FormProperty)
            return new FormPropertyValueOrigin((FormProperty)property);
        else
            return new PropertyValueOrigin(property);
    }

  public static CodeExpressionOrigin createOrigin(RADComponent component) {
        return new RADComponentOrigin(component);
    }

    public static void readPropertyExpression(CodeExpression expression,
                                              Node.Property property,
                                              boolean allowChangeFiring)
    {
        FormProperty fProperty = property instanceof FormProperty ?
                                 (FormProperty) property : null;

        if (fProperty != null) {
            if (!allowChangeFiring) {
                if (fProperty.isChangeFiring())
                    fProperty.setChangeFiring(false);
                else
                    allowChangeFiring = true; // just not to set firing back
            }

            Object metaOrigin = expression.getOrigin().getMetaObject();
            if (metaOrigin instanceof PropertyEditor)
                fProperty.setCurrentEditor((PropertyEditor)metaOrigin);
        }

        try {
            property.setValue(expression.getOrigin().getValue());
        }
        catch (Exception ex) { // ignore
            org.openide.ErrorManager.getDefault().notify(org.openide.ErrorManager.INFORMATIONAL, ex);
        }
        expression.setOrigin(createOrigin(property));

        if (fProperty != null && !allowChangeFiring)
            fProperty.setChangeFiring(true);
    }

    public static void readPropertyStatement(CodeStatement statement,
                                             Node.Property property,
                                             boolean allowChangeFiring)
    {
        // expecting statement with one expression parameter
        CodeExpression[] params = statement.getStatementParameters();
        if (params.length != 1)
            throw new IllegalArgumentException();

        readPropertyExpression(params[0], property, allowChangeFiring);
    }

    // --------

    static final class PropertyValueOrigin implements CodeExpressionOrigin {
        private final Node.Property property;

        public PropertyValueOrigin(Node.Property property) {
            this.property = property;
        }

        @Override
        public Class getType() {
            return property.getValueType();
        }

        @Override
        public CodeExpression getParentExpression() {
            return null;
        }

        @Override
        public Object getValue() {
            try {
                return property.getValue();
            }
            catch (Exception ex) {} // should no happen

            return null;
        }

        @Override
        public Object getMetaObject() {
            return property;
        }

      @Override
        public CodeExpression[] getCreationParameters() {
            return CodeStructure.EMPTY_PARAMS;
        }
    }

    static final class FormPropertyValueOrigin implements CodeExpressionOrigin {
        private final FormProperty property;

        public FormPropertyValueOrigin(FormProperty property) {
            this.property = property;
        }

        @Override
        public Class getType() {
            return property.getValueType();
        }

        @Override
        public CodeExpression getParentExpression() {
            return null;
        }

        @Override
        public Object getValue() {
            try {
                return property.getRealValue();
                // [or getValue() ??]
            }
            catch (Exception ex) {} // should no happen

            return null;
        }

        @Override
        public Object getMetaObject() {
            return property;
        }

      @Override
        public CodeExpression[] getCreationParameters() {
            return CodeStructure.EMPTY_PARAMS;
        }
    }

  static final class RADComponentOrigin implements CodeExpressionOrigin {
        private final RADComponent component;

        public RADComponentOrigin(RADComponent component) {
            this.component = component;
        }

        @Override
        public Class getType() {
            return component.getBeanClass();
        }

        @Override
        public CodeExpression getParentExpression() {
            return null;
        }

        @Override
        public Object getMetaObject() {
            return component;
        }

        @Override
        public Object getValue() {
            return component.getBeanInstance();
        }

        @Override
        public CodeExpression[] getCreationParameters() {
            return CodeStructure.EMPTY_PARAMS;
        }

    }
}
