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
//import org.netbeans.modules.form.FormJavaSource;

/**
 * Class representing code structure of one form. Also manages a pool
 * of variables for code expressions, and a undo/redo queue.
 *
 * @author Tomas Pavek
 */

public class CodeStructure {

    public static final CodeExpression[] EMPTY_PARAMS = new CodeExpression[0];

    private static UsingCodeObject globalUsingObject;

    private final Map<String,Variable> namesToVariables = new HashMap<String,Variable>(50);
    private final Map<Object/*?*/,Variable> expressionsToVariables = new HashMap<Object,Variable>(50);

    private final int defaultVariableType = -1;


    // --------
    // constructor

    public CodeStructure() {
    }

    // -------
    // expressions

    /** Creates a new expression based on a constructor. */
    public CodeExpression createExpression(Constructor ctor,
                                           CodeExpression[] params)
    {
        CodeExpressionOrigin origin =
                            new CodeSupport.ConstructorOrigin(ctor, params);
        return new DefaultCodeExpression(this, origin);
    }

  /** Creates a new expression from based on a value. */
    public CodeExpression createExpression(Class type,
                                           Object value)
    {
        return new DefaultCodeExpression(this, new CodeSupport.ValueOrigin(
                                                    type, value));
    }

    /** Creates a new expression of an arbitrary origin. /*/
    public CodeExpression createExpression(CodeExpressionOrigin origin) {
        return new DefaultCodeExpression(this, origin);
    }

    /** Creates an expression representing null value. */
    public CodeExpression createNullExpression(Class type) {
        return new DefaultCodeExpression(this, new CodeSupport.ValueOrigin(
                                                    type, null)); // NOI18N
    }

    /** Creates an expression with no origin. The origin must be set
     * explicitly before the expression is used. */
    public CodeExpression createDefaultExpression() {
        return new DefaultCodeExpression(this);
    }

    /** Prevents an expression from being removed automatically from structure
     * when no more used (by any UsingCodeObject). */
    public void registerExpression(CodeExpression expression) {
        if (globalUsingObject == null)
            globalUsingObject = new GlobalUsingObject();

        expression.addUsingObject(globalUsingObject,
                                  UsedCodeObject.USING,
                                  CodeStructure.class);
    }

    /** Filters out expressions whose origin uses given or equal meta object.
     * Passed expressions are returned in an array. */
    public static CodeExpression[] filterExpressions(Iterator it,
                                                     Object originMetaObject)
    {
        List<CodeExpression> list = new ArrayList<CodeExpression>();
        while (it.hasNext()) {
            CodeExpression exp = (CodeExpression) it.next();
            if (originMetaObject.equals(exp.getOrigin().getMetaObject()))
                list.add(exp);
        }
        return list.toArray(new CodeExpression[list.size()]);
    }

    // --------
    // statements

    /** Creates a new method statement. */
    public static CodeStatement createStatement(CodeExpression expression,
                                                Method m,
                                                CodeExpression[] params)
    {
        CodeStatement statement = new CodeSupport.MethodStatement(
                                                      expression, m, params);
        registerUsingCodeObject(statement);
        return statement;
    }

    /** Creates a new field statement. */
    public static CodeStatement createStatement(CodeExpression expression,
                                                Field f,
                                                CodeExpression assignExp)
    {
        CodeStatement statement = new CodeSupport.FieldStatement(
                                                    expression, f, assignExp);
        registerUsingCodeObject(statement);
        return statement;
    }

    /** Removes a statement from the structure completely. */
    public static void removeStatement(CodeStatement statement) {
        unregisterUsingCodeObject(statement);
    }

    /** Removes all statements provided by an Iterator. */
    public static void removeStatements(Iterator it) {
        List list = new ArrayList();
        while (it.hasNext())
            list.add(it.next());

        for (int i=0, n=list.size(); i < n; i++)
            unregisterUsingCodeObject((CodeStatement) list.get(i));
    }

    /** Filters out statements using given or equal meta object. Passed
     * statements are returned in an array. */
    public static CodeStatement[] filterStatements(Iterator it,
                                                   Object metaObject)
    {
        List<CodeStatement> list = new ArrayList<CodeStatement>();
        while (it.hasNext()) {
            CodeStatement statement = (CodeStatement) it.next();
            if (metaObject.equals(statement.getMetaObject()))
                list.add(statement);
        }
        return list.toArray(new CodeStatement[list.size()]);
    }

    // --------
    // statements code group

    /** Creates a default group of statements. */
    public CodeGroup createCodeGroup() {
        return new CodeSupport.DefaultCodeGroup();
    }

    // --------
    // origins

    /** Creates an expression origin from a constructor. */
    public static CodeExpressionOrigin createOrigin(Constructor ctor,
                                                    CodeExpression[] params)
    {
        return new CodeSupport.ConstructorOrigin(ctor, params);
    }

    /** Creates an expression origin from a method. */
    public static CodeExpressionOrigin createOrigin(CodeExpression parent,
                                                    Method m,
                                                    CodeExpression[] params)
    {
        return new CodeSupport.MethodOrigin(parent, m, params);
    }

  // -------
    // getting to expressions and statements dependent on given expression
    // (used as their parent or parameter)

    /** Returns an iterator of expressions that are defined by given
     * expression. These expressions use the given expression as the parent
     * of origin). */
    public static Iterator getDefinedExpressionsIterator(CodeExpression exp) {
        return exp.getUsingObjectsIterator(UsedCodeObject.DEFINED,
                                           CodeExpression.class);
    }

    /** Returns an iterator of statements that are defined by given
     * expression. These statements use the given expression as the parent. */
    public static Iterator getDefinedStatementsIterator(CodeExpression exp) {
        return exp.getUsingObjectsIterator(UsedCodeObject.DEFINED,
                                           CodeStatement.class);
    }
    // -------
    // managing references between code objects

    // Registers usage of expressions used by a statement.
    private static void registerUsingCodeObject(CodeStatement statement) {
        CodeExpression parent = statement.getParentExpression();
        if (parent != null)
            parent.addUsingObject(
                statement, UsedCodeObject.DEFINED, CodeStatement.class);

        CodeExpression[] params = statement.getStatementParameters();
        if (params != null)
          for (CodeExpression param : params)
            param.addUsingObject(
                statement, UsedCodeObject.USING, CodeStatement.class);
    }

    // Registers usage of expressions used by the origin of an expression.
    static void registerUsingCodeObject(CodeExpression expression) {
        CodeExpressionOrigin origin = expression.getOrigin();
        CodeExpression parent = origin.getParentExpression();

        if (parent != null)
            parent.addUsingObject(expression,
                                  UsedCodeObject.DEFINED,
                                  CodeExpression.class);

        CodeExpression[] params = origin.getCreationParameters();
        if (params != null)
          for (CodeExpression param : params)
            param.addUsingObject(expression,
                                 UsedCodeObject.USING,
                                 CodeExpression.class);
    }

    // Unregisters usage of all objects used by a using object.
    static void unregisterUsingCodeObject(UsingCodeObject usingObject) {
        Iterator it = usingObject.getUsedObjectsIterator();
        while (it.hasNext()) {
            UsedCodeObject usedObject = (UsedCodeObject) it.next();
            if (!usedObject.removeUsingObject(usingObject)) {
                // usedObject is no more used, so it should be removed
                if (usedObject instanceof UsingCodeObject)
                    unregisterUsingCodeObject((UsingCodeObject)usedObject);
            }
        }
    }

    // Unregisters usage of just one object used by a using object.
    static void unregisterObjectUsage(UsingCodeObject usingObject,
                                      UsedCodeObject usedObject)
    {
        if (!usedObject.removeUsingObject(usingObject)) {
            // usedObject is no more used, so it should be removed
            if (usedObject instanceof UsingCodeObject)
                unregisterUsingCodeObject((UsingCodeObject)usedObject);
        }
    }

    private static class GlobalUsingObject implements UsingCodeObject {
      @Override
        public void usedObjectRemoved() {
      }

      @Override
        public Iterator getUsedObjectsIterator() {
            return null;
        }
    }

    // -------
    // variables

  /** Renames variable of name oldName to newName. */
    public boolean renameVariable(String oldName, String newName) {
        Variable var = namesToVariables.get(oldName);
        if (var == null || newName == null
                || newName.equals(var.getName())
                || namesToVariables.get(newName) != null)
            return false;

        namesToVariables.remove(oldName);
        var.name = newName;
        namesToVariables.put(newName, var);

        return true;
    }

    /** Releases variable of given name. */
    void releaseVariable(String name) {
        namesToVariables.remove(name);
    }

  public CodeVariable createVariableForExpression(CodeExpression expression,
                                                    int type,
                                                    String name) {
        CodeVariable var = (expression == null) ? null : expression.getVariable();
        String typeParameters = (var == null) ? "" : var.getDeclaredTypeParameters(); // NOI18N
        return createVariableForExpression(expression, type, typeParameters, name);
    }

    /** Creates a new variable and attaches given expression to it. If the
     * requested name is already in use, then a free name is found. If null
     * is provided as the name, then expression's short class name is used. */
    public CodeVariable createVariableForExpression(CodeExpression expression,
                                                    int type,
                                                    String typeParameters,
                                                    String name)
    {
        if (expression == null)
            throw new IllegalArgumentException();

        if (getVariable(expression) != null)
            return null; // variable already exists, cannot create new one

        if (type < 0)
            throw new IllegalArgumentException();

        if (expressionsToVariables.get(expression) != null)
            removeExpressionFromVariable(expression);

	name = getFreeVariableName(name, expression.getOrigin().getType());

        Variable var = new Variable(type,
                                    typeParameters,
                                    name);
        CodeStatement statement = createVariableAssignment(expression);
        var.addCodeExpression(expression, statement);

        namesToVariables.put(name, var);
        expressionsToVariables.put(expression, var);

        return var;
    }

    private String getFreeVariableName(String name, Class type) {
        if (name == null || namesToVariables.get(name) != null) {
            // variable name not provided or already being used
            int n = 0;
            String baseName;
            if (name != null) { // already used name provided
                // try to find number suffix
                int i = name.length();
                int exp = 1;
                while (--i >= 0) {
                    char c = name.charAt(i);
                    if (c >= '0' && c <= '9') {
                        n += (c - '0') * exp;
                        exp *= 10;
                    }
                    else break;
                }

                baseName = i >= 0 ? name.substring(0, i+1) : name;
            }
            else { // derive default name from class type, add "1" as suffix
                String typeName = type.getName();
                int i = typeName.lastIndexOf('$'); // NOI18N
                if (i < 0) {
                    i = typeName.lastIndexOf('+'); // NOI18N
                    if (i < 0)
                        i = typeName.lastIndexOf('.'); // NOI18N
                }
                baseName = Character.toLowerCase(typeName.charAt(i+1))
                           + typeName.substring(i+2);
            }

//	    javaSource.refresh();
            do { // find a free name
                name = baseName + (++n);
            }
	    while ( namesToVariables.get(name) != null); // || javaSource.containsField(name, false) ); // TODO: stripped
        }
	return name;
    }

    public String getExternalVariableName(Class type) {
      return getFreeVariableName(null, type);
    }

  /** Releases an expression from using a variable. */
    public void removeExpressionFromVariable(CodeExpression expression) {
        if (expression == null)
            return;

        Variable var = expressionsToVariables.remove(expression);
        if (var == null)
            return;

        var.removeCodeExpression(expression);

        if (var.expressionsMap.isEmpty()
                && (var.getType() & CodeVariable.EXPLICIT_RELEASE) == 0)
            // release unused variable
            releaseVariable(var.getName());
    }

  /** Returns variable of an expression. */
    public CodeVariable getVariable(CodeExpression expression) {
        return expressionsToVariables.get(expression);
    }

  // ---------

  int getDefaultVariableType() {
        return defaultVariableType > -1 ?
               defaultVariableType : CodeVariable.FIELD | CodeVariable.PRIVATE;
    }

    // ---------

    private CodeStatement createVariableAssignment(CodeExpression expression)
    {

      // important: assignment statement does not register usage of code
        // expressions (assigned expression, parameters) - so it does not hold
        // the expressions in the structure

        return new CodeSupport.AssignVariableStatement(expression);
    }

    // --------
    // undo/redo processing

  // --------
    // inner classes

    final class Variable implements CodeVariable {
        private final int type;
        private final String declaredTypeParameters;
        private String name;
        private Map<CodeExpression,CodeStatement> expressionsMap;

        Variable(int type, String declaredTypeParameters, String name) {
            if ((type & FINAL) != 0)
                type &= ~EXPLICIT_DECLARATION;
            this.type = type;
            this.declaredTypeParameters = declaredTypeParameters;
            this.name = name;
        }

        @Override
        public int getType() {
            return (type & DEFAULT_TYPE) != DEFAULT_TYPE ?
                   type : getDefaultVariableType();
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getDeclaredTypeParameters() {
            return declaredTypeParameters;
        }

        @Override
        public CodeStatement getAssignment(CodeExpression expression) {
            return expressionsMap != null ? expressionsMap.get(expression) : null;
        }

        // -------

        void addCodeExpression(CodeExpression expression,
                               CodeStatement statement)
        {
            if (expressionsMap == null)
                expressionsMap = new HashMap<CodeExpression,CodeStatement>();
            expressionsMap.put(expression, statement);
        }

        void removeCodeExpression(CodeExpression expression) {
            if (expressionsMap != null)
                expressionsMap.remove(expression);
        }
    }

    // --------

  // ---------------

}
