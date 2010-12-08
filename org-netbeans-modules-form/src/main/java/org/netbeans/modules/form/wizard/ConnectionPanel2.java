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

package org.netbeans.modules.form.wizard;

import java.beans.*;
import java.util.*;
import javax.swing.event.*;

import org.openide.awt.Mnemonics;
import org.openide.util.NbBundle;
import org.openide.WizardDescriptor;

/**
 * The UI component of the ConnectionWizardPanel2.
 *
 * @author Tomas Pavek
 */

class ConnectionPanel2 extends javax.swing.JPanel {

    private ConnectionWizardPanel2 wizardPanel;

    private Object[] propertyListData;
    private Object[] methodListData;
    private MethodDescriptor[] methodDescriptors;
    private PropertyDescriptor[] propDescriptors;

    java.util.ResourceBundle bundle;

    /** Creates new form ConnectionPanel2 */
    ConnectionPanel2(ConnectionWizardPanel2 wizardPanel) {
        this.wizardPanel = wizardPanel;

        initComponents();

        bundle = NbBundle.getBundle(ConnectionPanel2.class);

        setName(bundle.getString("CTL_CW_Step2_Title")); // NOI18N

        javax.swing.ButtonGroup gr = new javax.swing.ButtonGroup();
        gr.add(propertyButton);
        gr.add(methodButton);
        gr.add(codeButton);

        targetComponentName.setText(wizardPanel.getTargetComponent().getName());

        actionList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        actionList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent evt) {
                if (!evt.getValueIsAdjusting())
                    ConnectionPanel2.this.wizardPanel.fireStateChanged();
            }
        });

        // localization code
        Mnemonics.setLocalizedText(targetNameLabel, bundle.getString("CTL_CW_TargetComponent")); // NOI18N
        targetComponentName.setToolTipText(
            bundle.getString("CTL_CW_TargetComponent_Hint")); // NOI18N
        Mnemonics.setLocalizedText(propertyButton, bundle.getString("CTL_CW_SetProperty")); // NOI18N
        propertyButton.setToolTipText(
            bundle.getString("CTL_CW_SetProperty_Hint")); // NOI18N
        Mnemonics.setLocalizedText(methodButton, bundle.getString("CTL_CW_MethodCall")); // NOI18N
        methodButton.setToolTipText(bundle.getString("CTL_CW_MethodCall_Hint")); // NOI18N
        Mnemonics.setLocalizedText(codeButton, bundle.getString("CTL_CW_XUserCode")); // NOI18N
        codeButton.setToolTipText(
            bundle.getString("CTL_CW_XUserCode_Hint")); // NOI18N

        targetComponentName.getAccessibleContext().setAccessibleDescription(
            bundle.getString("ACSD_CW_TargetComponent")); // NOI18N
        propertyButton.getAccessibleContext().setAccessibleDescription(
            bundle.getString("ACSD_CW_SetProperty")); // NOI18N
        methodButton.getAccessibleContext().setAccessibleDescription(
            bundle.getString("ACSD_CW_MethodCall")); // NOI18N
        codeButton.getAccessibleContext().setAccessibleDescription(
            bundle.getString("ACSD_CW_XUserCode")); // NOI18N
        actionList.getAccessibleContext().setAccessibleDescription(
            bundle.getString("ACSD_CW_ActionList")); // NOI18N
        getAccessibleContext().setAccessibleDescription(
            bundle.getString("ACSD_CW_ConnectionPanel2")); // NOI18N

        updateActionList();

        putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, new Integer(1)); // NOI18N
    }

    @Override
    public java.awt.Dimension getPreferredSize() {
        return new java.awt.Dimension(450, 300);
    }

    int getActionType() {
        if (methodButton.isSelected())
            return ConnectionWizardPanel2.METHOD_TYPE;
        else if (propertyButton.isSelected())
            return ConnectionWizardPanel2.PROPERTY_TYPE;
        else
            return ConnectionWizardPanel2.CODE_TYPE;
    }

    MethodDescriptor getSelectedMethod() {
        if (!methodButton.isSelected() || actionList.getSelectedIndex() == -1)
            return null;
        return methodDescriptors[actionList.getSelectedIndex()];
    }

    PropertyDescriptor getSelectedProperty() {
        if (!propertyButton.isSelected() || actionList.getSelectedIndex() == -1)
            return null;
        return propDescriptors[actionList.getSelectedIndex()];
    }

    private void updateActionList() {
        if (codeButton.isSelected()) {
            actionList.setListData(new String [] {
                bundle.getString("CTL_CW_UserCodeText1"), // NOI18N
                bundle.getString("CTL_CW_UserCodeText2") }); // NOI18N
            actionList.setEnabled(false);
            actionList.getAccessibleContext().setAccessibleName(codeButton.getText());
        } 
        else if (propertyButton.isSelected()) {
            // properties list
            actionList.setEnabled(true);
            if (propertyListData == null) {
                BeanInfo targetBeanInfo =
                    wizardPanel.getTargetComponent().getBeanInfo();
                PropertyDescriptor[] descs = targetBeanInfo.getPropertyDescriptors();

                // filter out read-only properties // [FUTURE: provide also indexed properties]
                List<PropertyDescriptor> list = new ArrayList<PropertyDescriptor>();
                for (int i = 0; i < descs.length; i++) {
                    if (descs[i].getWriteMethod() != null) {
                        list.add(descs[i]);
                    }
                }

                // sort the properties by name
                Collections.sort(list, new Comparator<PropertyDescriptor>() {
                    @Override
                    public int compare(PropertyDescriptor o1, PropertyDescriptor o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });

                propDescriptors = new PropertyDescriptor [list.size()];
                list.toArray(propDescriptors);

                propertyListData = new String [propDescriptors.length];
                for (int i = 0; i < propDescriptors.length; i++) {
                    propertyListData [i] = propDescriptors [i].getName();
                }
            }
            actionList.setListData(propertyListData);
            actionList.getAccessibleContext().setAccessibleName(propertyButton.getText());
        } 
        else {
            // methods list
            actionList.setEnabled(true);
            if (methodListData == null) {
                BeanInfo targetBeanInfo =
                    wizardPanel.getTargetComponent().getBeanInfo();
                methodDescriptors = targetBeanInfo.getMethodDescriptors();
                ArrayList<MethodDescriptor> list = new ArrayList<MethodDescriptor>();
                for (int i = 0; i < methodDescriptors.length; i++) {
                    list.add(methodDescriptors[i]);
                }

                // sort the methods by name
                Collections.sort(list, new Comparator<MethodDescriptor>() {
                    @Override
                    public int compare(MethodDescriptor o1, MethodDescriptor o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });

                // copy it back to the array as it is used later
                list.toArray(methodDescriptors);

                methodListData = new String [list.size()];
                int i = 0;
                for (Iterator it = list.iterator(); it.hasNext();) {
                    methodListData [i++] = getMethodName((MethodDescriptor)it.next());
                }
            }
            actionList.setListData(methodListData);
            actionList.getAccessibleContext().setAccessibleName(methodButton.getText());
        }
        actionList.revalidate();
        actionList.repaint();
    }

    private static String getMethodName(MethodDescriptor desc) {
        StringBuilder sb = new StringBuilder(desc.getName());
        Class[] params = desc.getMethod().getParameterTypes();
        if ((params == null) ||(params.length == 0)) {
            sb.append("()"); // NOI18N
        } else {
            for (int i = 0; i < params.length; i++) {
                if (i == 0) sb.append("("); // NOI18N
                else sb.append(", "); // NOI18N
                sb.append(org.openide.util.Utilities.getShortClassName(params[i]));
            }
            sb.append(")"); // NOI18N
        }

        return sb.toString();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        targerInfoPanel = new javax.swing.JPanel();
        targetNamePanel = new javax.swing.JPanel();
        targetNameLabel = new javax.swing.JLabel();
        targetComponentName = new javax.swing.JTextField();
        actionTypePanel = new javax.swing.JPanel();
        propertyButton = new javax.swing.JRadioButton();
        methodButton = new javax.swing.JRadioButton();
        codeButton = new javax.swing.JRadioButton();
        actionPanel = new javax.swing.JScrollPane();
        actionList = new javax.swing.JList();

        setLayout(new java.awt.BorderLayout(0, 2));

        targerInfoPanel.setLayout(new java.awt.BorderLayout(0, 6));

        targetNamePanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        targetNameLabel.setLabelFor(targetComponentName);
        targetNameLabel.setText("Target Component:");
        targetNameLabel.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(0, 0, 0, 6)));
        targetNamePanel.add(targetNameLabel);

        targetComponentName.setEditable(false);
        targetComponentName.setText("jTextField1");
        targetNamePanel.add(targetComponentName);

        targerInfoPanel.add(targetNamePanel, java.awt.BorderLayout.NORTH);

        actionTypePanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        propertyButton.setSelected(true);
        propertyButton.setText("Set Property");
        propertyButton.setMargin(new java.awt.Insets(2, 2, 2, 10));
        propertyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConnectionPanel2.this.actionTypeButtonPressed(evt);
            }
        });

        actionTypePanel.add(propertyButton);

        methodButton.setText("Method Call");
        methodButton.setMargin(new java.awt.Insets(2, 2, 2, 10));
        methodButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConnectionPanel2.this.actionTypeButtonPressed(evt);
            }
        });

        actionTypePanel.add(methodButton);

        codeButton.setText("User Code");
        codeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConnectionPanel2.this.actionTypeButtonPressed(evt);
            }
        });

        actionTypePanel.add(codeButton);

        targerInfoPanel.add(actionTypePanel, java.awt.BorderLayout.CENTER);

        add(targerInfoPanel, java.awt.BorderLayout.NORTH);

        actionPanel.setViewportView(actionList);

        add(actionPanel, java.awt.BorderLayout.CENTER);

    }//GEN-END:initComponents

    private void actionTypeButtonPressed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionTypeButtonPressed
        updateActionList();
        wizardPanel.fireStateChanged();
        if (evt.getSource() != codeButton)
            actionList.requestFocus();
    }//GEN-LAST:event_actionTypeButtonPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton codeButton;
    private javax.swing.JList actionList;
    private javax.swing.JRadioButton methodButton;
    private javax.swing.JScrollPane actionPanel;
    private javax.swing.JPanel targetNamePanel;
    private javax.swing.JRadioButton propertyButton;
    private javax.swing.JPanel targerInfoPanel;
    private javax.swing.JLabel targetNameLabel;
    private javax.swing.JTextField targetComponentName;
    private javax.swing.JPanel actionTypePanel;
    // End of variables declaration//GEN-END:variables
}
