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

import java.beans.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import org.openide.ErrorManager;

import org.netbeans.modules.form.editors.*;
import org.netbeans.modules.form.editors2.JTableSelectionModelEditor;
import org.netbeans.modules.form.fakepeer.FakePeerSupport;

/**
 * Implementation of properties for (meta)components (class RADComponent).
 * RADComponent is used to get the component instance and
 * PropertyDescriptor provides read and write methods to get and set
 * property values.
 *
 * @author Tomas Pavek
 */
public class RADProperty extends FormProperty {

    public static final String SYNTH_PREFIX = "$$$_"; // NOI18N
    public static final String SYNTH_PRE_CODE = SYNTH_PREFIX + PROP_PRE_CODE + "_"; // NOI18N
    public static final String SYNTH_POST_CODE = SYNTH_PREFIX + PROP_POST_CODE + "_"; // NOI18N

    private RADComponent component;
    private PropertyDescriptor desc;
    private Object defaultValue;

    public RADProperty(RADComponent metacomp, PropertyDescriptor propdesc) {
        super(new FormPropertyContext.Component(metacomp),
              propdesc.getName(),
              propdesc.getPropertyType(),
              propdesc.getDisplayName(),
              propdesc.getShortDescription());

        component = metacomp;
        desc = propdesc;

        if (desc.getWriteMethod() == null) {
            setAccessType(NO_WRITE);
        } else if (desc.getReadMethod() == null) {
            setAccessType(DETACHED_READ);
        } // assuming a bean property is at least readable or writeable

        defaultValue = BeanSupport.NO_VALUE;
        if (canReadFromTarget()) {
            try {
                defaultValue = getTargetValue();
            } catch (Exception ex) {}
        }
    }

    public RADComponent getRADComponent() {
        return component;
    }

    public PropertyDescriptor getPropertyDescriptor() {
        return desc;
    }

    @Override
    public Object getTargetValue() throws IllegalAccessException,
                                          InvocationTargetException {
        Method readMethod = desc.getReadMethod();
        if (readMethod == null) {
            throw new IllegalAccessException("Not a readable property: "+desc.getName()); // NOI18N
        }
        return readMethod.invoke(component.getBeanInstance(), new Object[0]);
    }

    @Override
    public void setTargetValue(Object value) throws IllegalAccessException,
                                                 IllegalArgumentException,
                                                 InvocationTargetException {
        Method writeMethod = desc.getWriteMethod();
        if (writeMethod == null) {
            throw new IllegalAccessException("Not a writeable property: "+desc.getName()); // NOI18N
        }

        Object beanInstance = component.getBeanInstance();

        // Ugly hack for Scrollbar - Scrollbar.setOrientation(...) method tries
        // to re-create the (native) peer, which we cannot allow. So we detach
        // the peer first before calling the method. This is the only place
        // where we can do it. It could be probably done for all AWT
        // components, but I don't know about any other which would need it.
        java.awt.peer.ComponentPeer scrollbarPeerHack =
            "setOrientation".equals(writeMethod.getName()) // NOI18N
                    && beanInstance instanceof java.awt.Scrollbar ?
            FakePeerSupport.detachFakePeer((java.awt.Component)beanInstance)
            : null;

        try {
            // invoke the setter method
            writeMethod.invoke(component.getBeanInstance(),
                               new Object[] { value });
        }
        catch (InvocationTargetException ex) {
            // annotate exception
            String message = FormUtils.getFormattedBundleString(
                "MSG_ERR_WRITING_TO_PROPERTY", // NOI18N
                new Object[] { getDisplayName() });

            Throwable tex = ex.getTargetException();
            if(tex instanceof IllegalArgumentException) {
                ErrorManager.getDefault().annotate(
                    tex, ErrorManager.WARNING, null,
                    message, null, null);
                // Issue 73627
                if ("contentType".equals(getName()) && (beanInstance instanceof javax.swing.JTextPane)) { // NOI18N
                    return;
                }
                throw (IllegalArgumentException) tex;
            } else if(tex instanceof IllegalAccessException) {
                ErrorManager.getDefault().annotate(
                    tex, ErrorManager.WARNING, null,
                    message, null, null);                
                throw (IllegalAccessException) tex;
            } else if(value==null && tex instanceof NullPointerException) {
                IllegalArgumentException iae = new IllegalArgumentException();
                ErrorManager.getDefault().annotate(
                    iae, ErrorManager.WARNING, null,
                    message, null, null);                
                throw iae;                
            }
            
            ErrorManager.getDefault().annotate(
                ex, ErrorManager.WARNING, null,
                message, null, null);

            throw ex;
        }

        if (scrollbarPeerHack != null) // restore the Scrollbar's fake peer
            FakePeerSupport.attachFakePeer((java.awt.Component)beanInstance,
                                           scrollbarPeerHack);
    }

    @Override
    protected Object getRealValue(Object value) {
        Object realValue = super.getRealValue(value);

        if (realValue == FormDesignValue.IGNORED_VALUE) {
            Object instance = component.getBeanInstance();
            String propName = desc.getName();
            if (instance instanceof java.awt.Component 
                && "text".equals(propName)) { // NOI18N
                realValue = ((FormDesignValue)value).getDescription();
            } else if (supportsDefaultValue()
                    && !((instance instanceof javax.swing.JEditorPane) && "page".equals(propName))) { // Issue 123303 // NOI18N
                // Issue 87647
                return getDefaultValue();
            }
        }

        return realValue;
    }

    @Override
    public boolean supportsDefaultValue() {
        return defaultValue != BeanSupport.NO_VALUE;
    }

    @Override
    public Object getDefaultValue() {
        Object specialDefaultValue = FormUtils.getSpecialDefaultPropertyValue(
                component.getBeanInstance(), getName());
        return specialDefaultValue != BeanSupport.NO_VALUE
                ? specialDefaultValue : defaultValue;
    }

    @Override
    public boolean canWrite() {
         return component.isReadOnly() ? false : super.canWrite();
    }

    @Override
    public PropertyEditor getExpliciteEditor() {
        PropertyEditor prEd = null;

        PropertyDescriptor descriptor = getPropertyDescriptor();
        if (descriptor.getPropertyType() == Integer.TYPE
            && ("mnemonic".equals(descriptor.getName()) // NOI18N
                || "displayedMnemonic".equals(descriptor.getName()))) { // NOI18N
                prEd = new MnemonicEditor();
        } else if (descriptor.getPropertyType().isArray()) {
            String typeName = descriptor.getPropertyType().getSimpleName();
            
            if (typeName.equals("boolean[]") || typeName.equals("byte[]")       // NOI18N
               || typeName.equals("short[]") || typeName.equals("int[]")        // NOI18N
               || typeName.equals("long[]") || typeName.equals("float[]")       // NOI18N
               || typeName.equals("double[]") || typeName.equals("char[]")) {   // NOI18N
               prEd = new PrimitiveTypeArrayEditor();
            }
        } else {
            if ("editor".equals(descriptor.getName()) && (javax.swing.JSpinner.class.isAssignableFrom(component.getBeanClass()))) { // NOI18N
                prEd = new SpinnerEditorEditor();
            } else if ("formatterFactory".equals(descriptor.getName()) && (javax.swing.JFormattedTextField.class.isAssignableFrom(component.getBeanClass()))) { // NOI18N
                prEd = new AbstractFormatterFactoryEditor();
            } else if ("selectionModel".equals(descriptor.getName()) && (javax.swing.JTable.class.equals(component.getBeanClass()))) { // NOI18N
                prEd = new JTableSelectionModelEditor();
            } else {
                prEd = createEnumEditor(descriptor);
            }
        }

        if (prEd == null) {
            try {
                prEd = desc.createPropertyEditor(component.getBeanInstance());
            }
            catch (Exception ex) {
                org.openide.ErrorManager.getDefault().notify(org.openide.ErrorManager.INFORMATIONAL, ex);
            }
        }

        if ((prEd == null) && (descriptor.getPropertyType().isEnum())) {
            prEd = createDefaultEnumEditor(descriptor.getPropertyType());
        }

        return prEd;
    }

    static PropertyEditor createDefaultEnumEditor(Class enumClass) {
        try {
            Method method = enumClass.getMethod("values"); // NOI18N
            Enum[] values = (Enum[]) method.invoke(null);
            List<Object> list = new ArrayList<Object>(3*values.length);
            for (Enum value : values) {
                list.add(value.toString());
                list.add(value);
                list.add(enumClass.getName().replace('$', '.') + '.' + value.name());
            }
            // null value is always valid
            list.add(org.openide.util.NbBundle.
                    getBundle(RADProperty.class).
                    getString("CTL_NullText") // NOI18N
                    );
            list.add(null);
            list.add("null"); // NOI18N

            return new EnumEditor(list.toArray());
        } catch (Exception ex) {
            ErrorManager.getDefault().notify(ErrorManager.INFORMATIONAL, ex);
        }
        return null;
    }

    protected PropertyEditor createEnumEditor(PropertyDescriptor descriptor) {
        Object[] enumerationValues;

        if (!"debugGraphicsOptions".equals(descriptor.getName()) // NOI18N
            || !javax.swing.JComponent.class.isAssignableFrom(
                                              component.getBeanClass()))
        {   // get the enumeration values by standard means
            enumerationValues = (Object[])
                                descriptor.getValue("enumerationValues"); // NOI18N
        }
        else { // hack: debugGraphicsOptions is problematic because its
               // default value (0) does not correspond to any of the
               // enumerated constants (NONE_OPTION is -1)
            enumerationValues = new Object[] {
                "NONE_OPTION", new Integer(-1), "DebugGraphics.NONE_OPTION", // NOI18N
                "NO_CHANGES", new Integer(0), "0", // NOI18N
                "LOG_OPTION", new Integer(1), "DebugGraphics.LOG_OPTION", // NOI18N
                "FLASH_OPTION", new Integer(2), "DebugGraphics.FLASH_OPTION", // NOI18N
                "BUFFERED_OPTION", new Integer(4), "DebugGraphics.BUFFERED_OPTION" }; // NOI18N
        }

        if (enumerationValues == null
            && "defaultCloseOperation".equals(descriptor.getName()) // NOI18N
            && (javax.swing.JDialog.class.isAssignableFrom(
                                           component.getBeanClass())
                || javax.swing.JInternalFrame.class.isAssignableFrom(
                                           component.getBeanClass())))
        {   // hack: enumeration definition is missing in standard Swing
            // for JDialog and JInternalFrame defaultCloseOperation property
            enumerationValues = new Object[] {
                "DISPOSE_ON_CLOSE", new Integer(2), // NOI18N
                        "WindowConstants.DISPOSE_ON_CLOSE", // NOI18N
                "DO_NOTHING_ON_CLOSE", new Integer(0), // NOI18N
                        "WindowConstants.DO_NOTHING_ON_CLOSE", // NOI18N
                "HIDE_ON_CLOSE", new Integer(1), // NOI18N
                         "WindowConstants.HIDE_ON_CLOSE" }; // NOI18N
        }

        return enumerationValues != null ?
                 new EnumEditor(enumerationValues) : null;
    }

    @Override
    protected Method getWriteMethod() {	    
	return desc.getWriteMethod();	    
    }
    
    @Override
    public void setPreCode(String value) {
        if ((preCode == null && value != null)
                || (preCode != null && !preCode.equals(value))) {
            Object old = preCode;
            preCode = value;
            if (isChangeFiring() && component.getFormModel() != null)
                component.getFormModel().fireSyntheticPropertyChanged(
                    component, SYNTH_PRE_CODE + getName(), old, value);
        }
    }

    @Override
    public void setPostCode(String value) {
        if ((postCode == null && value != null)
                || (postCode != null && !postCode.equals(value))) {
            Object old = postCode;
            postCode = value;
            if (isChangeFiring() && component.getFormModel() != null)
                component.getFormModel().fireSyntheticPropertyChanged(
                    component, SYNTH_POST_CODE + getName(), old, value);
        }
    }

    // Descriptor for fake-properties (not real, design-time only) that
    // need to pretend they are of certain type although without both
    // getter and setter. Used e.g. by ButtonGroupProperty.
    static class FakePropertyDescriptor extends PropertyDescriptor {
        Class propType;

        FakePropertyDescriptor(String name, Class type) throws IntrospectionException {
            super(name,null,null);
            propType = type;
        }

        @Override
        public Class getPropertyType() {
            return propType;
        }
    }
}
