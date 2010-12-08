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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;

/**
 * ComboBox with tree in popup.
 *
 * @author Jan Stola
 */
public class ComboBoxWithTree extends JComboBox {
    /** Window used as popup. */
    private Window popup;
    /** Tree in popup. */
    private JTree tree;
    /** Scroll pane enclosing the tree. */
    private JScrollPane scrollPane;
    /** Converter between tree path and its string representation. */
    private Converter converter;
    
    /**
     * Creates new <code>ComboBoxWithTree</code>.
     * 
     * @param treeModel tree model.
     * @param treeCellRenderer tree cell renderer.
     * @param converter converter between tree path and its string representation.
     */
    public ComboBoxWithTree(TreeModel treeModel, TreeCellRenderer treeCellRenderer, Converter converter) {
        this.converter = converter;
        initCombo();
        initTree(treeModel, treeCellRenderer);
    }

    /**
     * Initializes the combo. 
     */
    private void initCombo() {
        setEditable(true);
        addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                if (issue112997Hack) {
                    setPopupVisible(true);
                    return;
                }
                getPopup().setVisible(false);
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                if (issue112997Hack) {
                    issue112997Hack = false;
                    setPopupVisible(true);
                    return;
                }
                getPopup().setVisible(false);
            }

            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                updateTreeSelection();
                Dimension dim = getSize();
                Point p = getLocationOnScreen();
                Window w = getPopup();
                w.setLocation(p.x, p.y + dim.height);
                w.setSize(new Dimension(dim.width, scrollPane.getPreferredSize().height));
                w.setVisible(true);
            }
        });
        // Get rid of original popup
        setModel(new DefaultComboBoxModel(new Object[] {""})); // NOI18N
        setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (index == -1) { // Make sure that the combo has a correct preferred size
                    return super.getListCellRendererComponent(list, "null", index, isSelected, cellHasFocus); // NOI18N
                } else {
                    JLabel comp = new JLabel();
                    comp.setPreferredSize(new Dimension(0,-10000));
                    return comp;
                }
            }
        });
    }

    /**
     * Initializes the tree. 
     * 
     * @param treeModel tree model.
     * @param treeCellRenderer tree cell renderer.
     */
    private void initTree(TreeModel treeModel, TreeCellRenderer treeCellRenderer) {
        tree = new JTree();
        TreeSelectionModel selectionModel = new DefaultTreeSelectionModel();
        selectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setRootVisible(false);
        tree.setShowsRootHandles(true);
        tree.setSelectionModel(selectionModel);
        tree.setVisibleRowCount(10);
        tree.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!isPopupVisible()) {
                    setPopupVisible(true);
                }
                int code = e.getKeyCode();
                if ((code == KeyEvent.VK_ENTER) || (code == KeyEvent.VK_ESCAPE)) {
                    setPopupVisible(false);
                }
            }
        });
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isPopupVisible()) {
                    setPopupVisible(true);
                }
                if (e.getClickCount() > 1) {
                    setPopupVisible(false);
                }
            }
        });
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                String value = converter.pathToString(e.getPath());
                setSelectedItem(value);
            }
        });
        tree.setModel(treeModel);
        tree.setCellRenderer(treeCellRenderer);
    }

    /**
     * Returns the popup.
     * 
     * @return the popup.
     */
    private Window getPopup() {
        if (popup == null) {
            popup = new Window(SwingUtilities.getWindowAncestor(this));
            scrollPane = new JScrollPane(tree);
            // The scrollPane must be in JPopupMenu to ensure that
            // it is not closed when components within it obtain the focus
            scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            JPopupMenu menu = new JPopupMenu() {
                // Cannot use setVisible(true) on JDK 6 due to changes in isPopupMenu()
                @Override
                public boolean isVisible() {
                    return true;
                }
            };
            menu.setBorder(new EmptyBorder(0,0,0,0));
            menu.setLayout(new BorderLayout());
            menu.add(scrollPane);
            popup.add(menu);
        }
        return popup;
    }

    /**
     * Updates tree selection according to string in combo. 
     */
    private void updateTreeSelection() {
        final TreePath path = getSelectedTreePath();
        if (path == null) {
            tree.clearSelection();
        } else {
            tree.setSelectionPath(path);
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    tree.scrollPathToVisible(path);
                }
            });
        }
    }

    public TreePath getSelectedTreePath() {
        String value = getEditor().getItem().toString();
        TreePath path = converter.stringToPath(value);
        return path;
    }

    @Override
    public void addNotify() {
        super.addNotify();
        Container cont = getParent();
        while (!(cont instanceof Window) && (cont.getParent() != null)) {
            cont = cont.getParent();
        }
        if (cont instanceof Window) {
            ((Window)cont).addWindowListener(new WindowAdapter() {
                @Override
                public void windowDeactivated(WindowEvent e) {
                    if (isPopupVisible()) {
                        issue112997Hack = true;
                    }
                }
            });
        }
    }
    private boolean issue112997Hack = false;

    /**
     * Converter between tree path and its string representation.
     */
    public static interface Converter {
        /**
         * Converts tree path to string representation.
         * 
         * @param path path to convert.
         * @return string representation of tree path.
         */
        String pathToString(TreePath path);

        /**
         * Converts string representation to tree path.
         * 
         * @param value string to convert.
         * @return tree path that corresponds to the given string representation.
         */
        TreePath stringToPath(String value);
    }
    
}
