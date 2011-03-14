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

import java.util.*;
import java.lang.reflect.*;

/**
 * @author Tomas Pavek
 */

class CodeSupport {

    private CodeSupport() {
    }

  // ----------
    // implementation classes of CodeStatement interface

    static final class MethodStatement extends AbstractCodeStatement {
        private final Method performMethod;
        private final CodeExpression[] parameters;

        public MethodStatement(CodeExpression exp,
                               Method m,
                               CodeExpression[] params)
        {
            super(exp);
            performMethod = m;
            parameters = params != null ? params : CodeStructure.EMPTY_PARAMS;
        }

        @Override
        public Object getMetaObject() {
            return performMethod;
        }

        @Override
        public CodeExpression[] getStatementParameters() {
            return parameters;
        }

    }

    static final class FieldStatement extends AbstractCodeStatement {
        private final Field assignField;
        private final CodeExpression[] parameters;

        public FieldStatement(CodeExpression exp,
                              Field f,
                              CodeExpression assignedExp)
        {
            super(exp);
            assignField = f;
            parameters = new CodeExpression[] { assignedExp };
        }

        @Override
        public Object getMetaObject() {
            return assignField;
        }

        @Override
        public CodeExpression[] getStatementParameters() {
            return parameters;
        }

    }

    static final class AssignVariableStatement extends AbstractCodeStatement {

      public AssignVariableStatement(CodeExpression exp) {
            super(exp);
      }

        @Override
        public Object getMetaObject() {
            return parentExpression;
        }

        @Override
        public CodeExpression[] getStatementParameters() {
            return parentExpression.getOrigin().getCreationParameters();
        }

    }

  // ------------
    // implementation classes of CodeExpressionOrigin interface

    static final class ConstructorOrigin implements CodeExpressionOrigin {
        private final Constructor constructor;
        private final CodeExpression[] parameters;

        public ConstructorOrigin(Constructor ctor, CodeExpression[] params) {
            constructor = ctor;
            parameters = params != null ? params : CodeStructure.EMPTY_PARAMS;
        }

        @Override
        public Class getType() {
            return constructor.getDeclaringClass();
        }

        @Override
        public CodeExpression getParentExpression() {
            return null;
        }

        @Override
        public Object getMetaObject() {
            return constructor;
        }

        @Override
        public Object getValue() {
            Object[] params = new Object[parameters.length];
            for (int i=0; i < params.length; i++) {
                CodeExpressionOrigin paramOrigin = parameters[i].getOrigin();
                Object value = paramOrigin.getValue();
                Class type = paramOrigin.getType();
                if (value == null && type.isPrimitive())
                    return null;
                params[i] = value;
            }

            try {
                return constructor.newInstance(params);
            }
            catch (Exception ex) {
                org.openide.ErrorManager.getDefault().notify(org.openide.ErrorManager.INFORMATIONAL, ex);
                return null;
            }
        }

        @Override
        public CodeExpression[] getCreationParameters() {
            return parameters;
        }

    }

    static final class MethodOrigin implements CodeExpressionOrigin {
        private final CodeExpression parentExpression;
        private final Method creationMethod;
        private final CodeExpression[] parameters;

        public MethodOrigin(CodeExpression parent,
                            Method m,
                            CodeExpression[] params)
        {
            parentExpression = parent;
            creationMethod = m;
            parameters = params != null ? params : CodeStructure.EMPTY_PARAMS;
        }

        @Override
        public Class getType() {
            return creationMethod.getReturnType();
        }

        @Override
        public CodeExpression getParentExpression() {
            return parentExpression;
        }

        @Override
        public Object getMetaObject() {
            return creationMethod;
        }

        @Override
        public Object getValue() {
            return null;
        }

        @Override
        public CodeExpression[] getCreationParameters() {
            return parameters;
        }

    }

  static final class ValueOrigin implements CodeExpressionOrigin {
        private final Class expressionType;
        private final Object expressionValue;

      public ValueOrigin(Class type, Object value) {
            expressionType = type;
            expressionValue = value;
      }

        @Override
        public Class getType() {
            return expressionType;
        }

        @Override
        public CodeExpression getParentExpression() {
            return null;
        }

        @Override
        public Object getMetaObject() {
            return null;
        }

        @Override
        public Object getValue() {
            return expressionValue;
        }

        @Override
        public CodeExpression[] getCreationParameters() {
            return CodeStructure.EMPTY_PARAMS;
        }

    }

    // --------
    // implementation of CodeGroup interface

    // temporary reduced implementation
    static final class DefaultCodeGroup implements CodeGroup {

        private final List<Object/*CodeStatement or CodeGroup*/> statements = new ArrayList<Object>();

        @Override
        public void addStatement(CodeStatement statement) {
            statements.add(statement);
        }

        @Override
        public void addStatement(int index, CodeStatement statement) {
            statements.add(index, statement);
        }

        @Override
        public void addGroup(CodeGroup group) {
            statements.add(group);
        }

      @Override
        public void remove(Object object) {
            statements.remove(object);
        }

      @Override
        public void removeAll() {
            statements.clear();
        }

        @Override
        public Iterator<CodeStatement> getStatementsIterator() {
            return new StatementsIterator();
        }

        class StatementsIterator implements Iterator<CodeStatement> {
            int index = 0;
            final int count = statements.size();
            Iterator<CodeStatement> subIter;

            @Override
            public boolean hasNext() {
                if (subIter != null) {
                    if (subIter.hasNext())
                        return true;
                    subIter = null;
                    index++;
                }

                while (index < count) {
                    Object item = statements.get(index);
                    if (item instanceof CodeGroup) {
                        subIter = ((CodeGroup)item).getStatementsIterator();
                        if (subIter.hasNext())
                            return true;
                        subIter = null;
                    }
                    else if (item instanceof CodeStatement)
                        return true;
                    index++;
                }

                return false;
            }

            @Override
            public CodeStatement next() {
                if (!hasNext())
                    throw new NoSuchElementException();

                return subIter != null ? subIter.next() :
                                         (CodeStatement)statements.get(index++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        }
    }
}
