/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.netbeans.modules.db.explorer;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.util.NbBundle;

/**
 *
 * @author Martin Adamek, Andrei Badea
 */
public class ProgressPanel extends JPanel {

    // XXX copied from j2ee.core.utilities. No better way since the module
    // is in the Java cluster.

    private Dialog dialog;

    public ProgressPanel() {
        initComponents();
    }

    public void open(JComponent progressComponent) {
        holder.add(progressComponent, BorderLayout.CENTER);

        DialogDescriptor dd = new DialogDescriptor(
                this,
                NbBundle.getMessage (ProgressPanel.class, "MSG_PleaseWait"),
                true,
                new Object[0],
                DialogDescriptor.NO_OPTION,
                DialogDescriptor.DEFAULT_ALIGN,
                null,
                null,
                true);
        dialog = DialogDisplayer.getDefault().createDialog(dd);
        if (dialog instanceof JDialog) {
            JDialog jDialog = ((JDialog)dialog);
            jDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            JRootPane rootPane = jDialog.getRootPane();
            rootPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancel"); // NOI18N
            rootPane.getActionMap().put("cancel", new AbstractAction() { // NOI18N
                public void actionPerformed(ActionEvent event) {
                    if (cancelButton.isEnabled()) {
                        cancelButton.doClick();
                    }
                }
            });
        }
        dialog.setResizable(false);
        dialog.setVisible(true);
    }

    public void close() {
	if (dialog != null) {
	    dialog.setVisible(false);
	    dialog.dispose();
	}
    }

    public boolean isOpen() {
        return dialog != null && dialog.isVisible();
    }

    public void setText(String text) {
        info.setText(text);
    }

    public String getText() {
        return info.getText();
    }

    public void setCancelVisible(boolean cancelVisible) {
        cancelButton.setVisible(cancelVisible);
    }

    public boolean isCancelVisible() {
        return cancelButton.isVisible();
    }

    public void setCancelEnabled(boolean cancelEnabled) {
        cancelButton.setEnabled(cancelEnabled);
    }

    public boolean isCancelEnabled() {
        return cancelButton.isEnabled();
    }

    public void addCancelActionListener(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">                          
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        info = new javax.swing.JLabel();
        holder = new JPanel();
        cancelButton = new JButton();

        setLayout(new java.awt.GridBagLayout());

        info.setText(" ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 11, 11);
        add(info, gridBagConstraints);

        holder.setLayout(new BorderLayout());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 11, 11);
        add(holder, gridBagConstraints);

        cancelButton.setText(NbBundle.getMessage (ProgressPanel.class, "LBL_Cancel"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 11, 11);
        add(cancelButton, gridBagConstraints);

    }// </editor-fold>                        


    // Variables declaration - do not modify                     
    private JButton cancelButton;
    private JPanel holder;
    private javax.swing.JLabel info;
    // End of variables declaration                   

    public Dimension getPreferredSize() {
        Dimension orig = super.getPreferredSize();
        return new Dimension(500, orig.height);
    }

}
