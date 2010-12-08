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

import java.util.*;
import java.beans.EventSetDescriptor;
import javax.swing.tree.*;
import javax.swing.event.*;

import org.openide.awt.Mnemonics;
import org.openide.util.NbBundle;
import org.netbeans.modules.form.*;
import org.openide.WizardDescriptor;


/**
 * The UI component of the ConnectionWizardPanel1.
 *
 * @author Tomas Pavek
 */

class ConnectionPanel1 extends javax.swing.JPanel {

    private ConnectionWizardPanel1 wizardPanel;

    /** Creates new form ConnectionPanel1 */
    ConnectionPanel1(ConnectionWizardPanel1 wizardPanel) {
        this.wizardPanel = wizardPanel;

        initComponents();

        RADComponent source = wizardPanel.getSourceComponent();

        java.util.ResourceBundle bundle = NbBundle.getBundle(ConnectionPanel1.class);

        setName(bundle.getString("CTL_CW_Step1_Title")); // NOI18N
        sourceComponentName.setText(source.getName());

        eventNameCombo.setEnabled(wizardPanel.getSelectedEvent() != null);

        eventNameCombo.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                ConnectionPanel1.this.wizardPanel.fireStateChanged();
            }
        });

        eventNameCombo.getEditor().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                ConnectionPanel1.this.wizardPanel.fireStateChanged();
            }
        });

         // populate event tree
        final Vector<EventSetNode> eventNodes = new Vector<EventSetNode>();
        TreeNode rootNode = new TreeNode() {
            @Override
            public TreeNode getChildAt(int childIndex) {
                return(TreeNode) eventNodes.elementAt(childIndex);
            }
            @Override
            public int getChildCount() {
                return eventNodes.size();
            }
            @Override
            public TreeNode getParent() {
                return null;
            }
            @Override
            public int getIndex(TreeNode node) {
                return eventNodes.indexOf(node);
            }
            @Override
            public boolean getAllowsChildren() {
                return true;
            }
            @Override
            public boolean isLeaf() {
                return false;
            }
            @Override
            public Enumeration children() {
                return eventNodes.elements();
            }
        };

        EventSetDescriptor lastEventSetDesc = null;
        EventSetNode eventSetNode = null;
        List<EventNode> eventSetEvents = null;

        Event[] events = source.getAllEvents();
        for (int i=0; i < events.length; i++) {
            Event event = events[i];
            EventSetDescriptor eventSetDesc = event.getEventSetDescriptor();

            if (eventSetDesc != lastEventSetDesc) {
                eventSetEvents = new ArrayList<EventNode>();
                eventSetNode = new EventSetNode(eventSetDesc.getName(),
                                                eventSetEvents);
                eventNodes.add(eventSetNode);
                lastEventSetDesc = eventSetDesc;
            }

            eventSetEvents.add(new EventNode(eventSetNode, event));
        }

        DefaultTreeSelectionModel treeSelectionModel = new DefaultTreeSelectionModel();
        treeSelectionModel.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent evt) {
                TreePath[] paths = eventSelectTree.getSelectionPaths();
                if ((paths != null) &&(paths.length == 1)) {
                    TreeNode node =(TreeNode) paths[0].getLastPathComponent();
                    if ((node != null) &&(node instanceof EventNode)) {
                        setSelectedEvent(((EventNode)node).getEvent());
                        return;
                    }
                }
                setSelectedEvent(null);
            }
        });

        treeSelectionModel.setSelectionMode(DefaultTreeSelectionModel.SINGLE_TREE_SELECTION);
        eventSelectTree.setModel(new DefaultTreeModel(rootNode));
        eventSelectTree.setSelectionModel(treeSelectionModel);

        // localization code
        Mnemonics.setLocalizedText(sourceNameLabel, bundle.getString("CTL_CW_SourceComponent")); // NOI18N
        sourceComponentName.setToolTipText(
            bundle.getString("CTL_CW_SourceComponent_Hint")); // NOI18N
        Mnemonics.setLocalizedText(eventSelectLabel, bundle.getString("CTL_CW_Event")); // NOI18N
        eventSelectTree.setToolTipText(bundle.getString("CTL_CW_Event_Hint"));
        sourcePanel.setToolTipText(bundle.getString("CTL_CW_Event_Hint"));
        eventHandlerPanel.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(
                    new javax.swing.border.EtchedBorder(),
                    bundle.getString("CTL_CW_EventHandlerMethod")), // NOI18N
                new javax.swing.border.EmptyBorder(new java.awt.Insets(5, 5, 5, 5))));
        Mnemonics.setLocalizedText(eventNameLabel, bundle.getString("CTL_CW_MethodName")); // NOI18N
        
        eventSelectTree.getAccessibleContext().setAccessibleDescription(
            bundle.getString("ACSD_CW_EventTree")); // NOI18N
        eventNameCombo.getAccessibleContext().setAccessibleDescription(
            bundle.getString("ACSD_CW_MethodName")); // NOI18N
        sourceComponentName.getAccessibleContext().setAccessibleDescription(
            bundle.getString("ACSD_CW_SourceComponent")); // NOI18N
        getAccessibleContext().setAccessibleDescription(
            bundle.getString("ACSD_CW_ConnectionPanel1")); // NOI18N
        putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, new Integer(0)); // NOI18N
    }

    @Override
    public java.awt.Dimension getPreferredSize() {
        return new java.awt.Dimension(450, 300);
    }

    String getEventName() {
        return (String) eventNameCombo.getEditor().getItem();
    }

    private void setSelectedEvent(Event event) {
        eventNameCombo.removeAllItems();

        if (event != null) {
            eventNameCombo.setEnabled(true);

            FormEvents formEvents = wizardPanel.getSourceComponent()
                                       .getFormModel().getFormEvents();
            String defaultName = formEvents.findFreeHandlerName(
                                     event, wizardPanel.getSourceComponent());

            eventNameCombo.addItem(defaultName);

            if (event.hasEventHandlers()) {
                String[] handlers = event.getEventHandlers();
                for (int i=0; i < handlers.length; i++)
                    eventNameCombo.addItem(handlers[i]);
            }

            eventNameCombo.setSelectedIndex(0);
        }
        else eventNameCombo.setEnabled(false);

        wizardPanel.setSelectedEvent(event);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        sourcePanel = new javax.swing.JPanel();
        sourceInfoPanel = new javax.swing.JPanel();
        sourceNamePanel = new javax.swing.JPanel();
        sourceNameLabel = new javax.swing.JLabel();
        sourceComponentName = new javax.swing.JTextField();
        eventSelectLabelPanel = new javax.swing.JPanel();
        eventSelectLabel = new javax.swing.JLabel();
        eventSelectScroll = new javax.swing.JScrollPane();
        eventSelectTree = new javax.swing.JTree();
        eventHandlerPanel = new javax.swing.JPanel();
        eventNameLabel = new javax.swing.JLabel();
        eventNameCombo = new javax.swing.JComboBox();

        setLayout(new java.awt.BorderLayout(0, 11));

        sourcePanel.setLayout(new java.awt.BorderLayout());

        sourceInfoPanel.setLayout(new java.awt.GridLayout(2, 1));

        sourceNamePanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        sourceNameLabel.setLabelFor(sourceComponentName);
        sourceNameLabel.setText("Source Component:");
        sourceNameLabel.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(0, 0, 0, 6)));
        sourceNamePanel.add(sourceNameLabel);

        sourceComponentName.setEditable(false);
        sourceComponentName.setText("jTextField1");
        sourceNamePanel.add(sourceComponentName);

        sourceInfoPanel.add(sourceNamePanel);

        eventSelectLabelPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 3));

        eventSelectLabel.setLabelFor(eventSelectTree);
        eventSelectLabel.setText("Events");
        eventSelectLabelPanel.add(eventSelectLabel);

        sourceInfoPanel.add(eventSelectLabelPanel);

        sourcePanel.add(sourceInfoPanel, java.awt.BorderLayout.NORTH);

        eventSelectScroll.setMaximumSize(new java.awt.Dimension(32767, 100));
        eventSelectTree.setRootVisible(false);
        eventSelectTree.setShowsRootHandles(true);
        eventSelectScroll.setViewportView(eventSelectTree);

        sourcePanel.add(eventSelectScroll, java.awt.BorderLayout.CENTER);

        add(sourcePanel, java.awt.BorderLayout.CENTER);

        eventHandlerPanel.setLayout(new java.awt.BorderLayout(8, 0));

        eventHandlerPanel.setBorder(new javax.swing.border.TitledBorder("Event Handler Method"));
        eventNameLabel.setLabelFor(eventNameCombo);
        eventNameLabel.setText("Method Name:");
        eventHandlerPanel.add(eventNameLabel, java.awt.BorderLayout.WEST);

        eventNameCombo.setEditable(true);
        eventHandlerPanel.add(eventNameCombo, java.awt.BorderLayout.CENTER);

        add(eventHandlerPanel, java.awt.BorderLayout.SOUTH);

    }//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree eventSelectTree;
    private javax.swing.JLabel eventNameLabel;
    private javax.swing.JPanel sourcePanel;
    private javax.swing.JScrollPane eventSelectScroll;
    private javax.swing.JLabel eventSelectLabel;
    private javax.swing.JPanel sourceNamePanel;
    private javax.swing.JPanel sourceInfoPanel;
    private javax.swing.JPanel eventHandlerPanel;
    private javax.swing.JLabel sourceNameLabel;
    private javax.swing.JTextField sourceComponentName;
    private javax.swing.JComboBox eventNameCombo;
    private javax.swing.JPanel eventSelectLabelPanel;
    // End of variables declaration//GEN-END:variables

    // --------
    // Innerclasses

    static class EventSetNode implements TreeNode {
        private String eventSetName;
        private List<EventNode> subNodes;

        public EventSetNode(String eventSetName, List<EventNode> subNodes) {
            this.eventSetName = eventSetName;
            this.subNodes = subNodes;
        }

        @Override
        public TreeNode getChildAt(int childIndex) {
            return subNodes.get(childIndex);
        }
        @Override
        public int getChildCount() {
            return subNodes.size();
        }
        @Override
        public TreeNode getParent() {
            return null;
        }
        @Override
        public int getIndex(TreeNode node) {
            return subNodes.indexOf(node);
        }
        @Override
        public boolean getAllowsChildren() {
            return true;
        }
        @Override
        public boolean isLeaf() {
            return false;
        }
        @Override
        public Enumeration children() {
            return Collections.enumeration(subNodes);
        }
        @Override
        public String toString() {
            return eventSetName;
        }
    }

    static class EventNode implements TreeNode {
        private TreeNode parent;
        private Event event;
        public EventNode(TreeNode parent, Event event) {
            this.parent = parent;
            this.event = event;
        }
        @Override
        public TreeNode getChildAt(int childIndex) {
            return null;
        }
        @Override
        public int getChildCount() {
            return 0;
        }
        @Override
        public TreeNode getParent() {
            return parent;
        }
        @Override
        public int getIndex(TreeNode node) {
            return -1;
        }
        @Override
        public boolean getAllowsChildren() {
            return false;
        }
        @Override
        public boolean isLeaf() {
            return true;
        }
        @Override
        public Enumeration children() {
            return null;
        }
        @Override
        public String toString() {
            if (!event.hasEventHandlers())
                return event.getName();
            String[] handlers = event.getEventHandlers();
            if (handlers.length == 1)
                return event.getName() + " ["+ handlers[0] +"]"; // NOI18N
            return event.getName() + " [...]"; // NOI18N
        }
        Event getEvent() {
            return event;
        }
    }
}
