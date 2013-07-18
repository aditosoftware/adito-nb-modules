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

package org.netbeans.core.windows.view.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.plaf.UIResource;
import org.netbeans.core.windows.actions.MaximizeWindowAction;
import org.openide.awt.CloseButtonFactory;
import org.openide.awt.TabbedPaneFactory;
import org.openide.util.Exceptions;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

/**
 * Copy of original CloseButtonTabbedPane from the NetBeans 3.4 winsys.  Old code never dies.
 * (moved from openide.awt module)
 *
 * @author Tran Duc Trung
 * @author S. Aubrecht
 * @since 2.52
 *
 */
final class CloseButtonTabbedPane extends JTabbedPane implements PropertyChangeListener {

    CloseButtonTabbedPane() {
            // close tab via middle button
            addMouseListener(new MouseAdapter() {
                int lastIdx = -1;

                @Override
                public void mousePressed(MouseEvent e) {
                    if (SwingUtilities.isMiddleMouseButton(e)) {
                        lastIdx = getUI().tabForCoordinate(CloseButtonTabbedPane.this, e.getX(), e.getY());
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (SwingUtilities.isMiddleMouseButton(e)) {
                        int idx = getUI().tabForCoordinate(CloseButtonTabbedPane.this, e.getX(), e.getY());
                        if (idx >= 0) {
                            Component comp = getComponentAt(idx);
                            if (idx == lastIdx && comp != null && !hideCloseButton(comp)) {
                                fireCloseRequest(comp);
                            }
                        }
                        lastIdx = -1;
                    }
                }

            @Override
            public void mouseClicked( MouseEvent e ) {
                if( e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton( e ) ) {
                    //toggle maximize
                    TopComponent tc = ( TopComponent ) SwingUtilities.getAncestorOfClass( TopComponent.class, CloseButtonTabbedPane.this );
                    if( null != tc ) {
                        MaximizeWindowAction mwa = new MaximizeWindowAction(tc);
                        if( mwa.isEnabled() )
                            mwa.actionPerformed(null);
                    }
                }
            }


            });
        //Bugfix #28263: Disable focus.
        setFocusable(false);
        setFocusCycleRoot(true);
        setFocusTraversalPolicy(new CBTPPolicy());
        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    private Component sel() {
        Component c = getSelectedComponent();
        return c == null ? this : c;
    }

    private class CBTPPolicy extends FocusTraversalPolicy {
        @Override
        public Component getComponentAfter(Container aContainer, Component aComponent) {
            return sel();
        }

        @Override
        public Component getComponentBefore(Container aContainer, Component aComponent) {
            return sel();
        }

        @Override
        public Component getFirstComponent(Container aContainer) {
            return sel();
        }

        @Override
        public Component getLastComponent(Container aContainer) {
            return sel();
        }

        @Override
        public Component getDefaultComponent(Container aContainer) {
            return sel();
        }
    }

    private int pressedCloseButtonIndex = -1;
    private int mouseOverCloseButtonIndex = -1;

    @Override
    public void insertTab(String title, Icon icon, Component component, String tip, int index) {
        super.insertTab(title, icon, component, tip, index);
            component.addPropertyChangeListener(TabbedPaneFactory.NO_CLOSE_BUTTON, this);
            if (!hideCloseButton(component)) {
                setTabComponentAt(index, new ButtonTab());
            }
        if (title != null) {
            setTitleAt(index, title);
        }
        validate();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        //#179323 - disable ctrl+page up/down actions if there's just one tab,
        //parent container, e.g. TopComponent tabs, may want to handle these keys itself
        ActionMap am = getActionMap();
        Action a = am.get("navigatePageUp");
        if( null != a && !(a instanceof MyNavigateAction) ) {
            am.put("navigatePageUp", new MyNavigateAction(a));
        }
        a = am.get("navigatePageDown");
        if( null != a && !(a instanceof MyNavigateAction) ) {
            am.put("navigatePageDown", new MyNavigateAction(a));
        }
    }


    @Override
    public void removeTabAt(int index) {
        Component c = getComponentAt(index);
        c.removePropertyChangeListener(TabbedPaneFactory.NO_CLOSE_BUTTON, this);
        super.removeTabAt(index);
    }

    private static final boolean HTML_TABS_BROKEN = htmlTabsBroken();
    private static boolean htmlTabsBroken() {
        String version = System.getProperty("java.version");
        for (int i = 14; i < 18; i++) {
            if (version.startsWith("1.6.0_" + i)) {
                return true;
            }
        }
        if( version.startsWith("1.6.0") && isAquaLaF() )
            return true;
        return false;
    }
    private final Pattern removeHtmlTags = HTML_TABS_BROKEN ? Pattern.compile("\\<.*?\\>") : null;

    @Override
    public void setTitleAt(int idx, String title) {
        if (title == null) {
            super.setTitleAt(idx, null);
            return;
        }
        // workaround for JDK bug (http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6670274)
        // NB issue #113388
        if (removeHtmlTags != null && title.startsWith("<html>")) {
            title = removeHtmlTags.matcher(title).replaceAll("");
            title = title.replace("&nbsp;", "");
        }

        super.setTitleAt(idx, title);
    }

    private Component findTabAt(int index) {
        int componentIndex = -1;
        for( Component c : getComponents() ) {
            if( c instanceof UIResource )
                continue;
            if( ++componentIndex == index )
                return c;
        }
        return null;
    }

    private boolean hideCloseButton(Component c) {
        if (c!=null && c instanceof JComponent) {
            Object prop = ((JComponent) c).getClientProperty(TabbedPaneFactory.NO_CLOSE_BUTTON);
            if (prop!=null && prop instanceof Boolean && (Boolean) prop) {
                return true;
            }
        }
        return false;
    }

    private Rectangle getCloseButtonBoundsAt(int i) {
        Component c = findTabAt(i);
        //if NO_CLOSE_BUTTON -> return null
        if (hideCloseButton(c)) {
            return null;
        }
        Rectangle b = getBoundsAt(i);
        if (b == null)
            return null;
        else {
            b = new Rectangle(b);
            fixGetBoundsAt(b);

            Dimension tabsz = getSize();
            if (b.x + b.width >= tabsz.width
                || b.y + b.height >= tabsz.height)
                return null;
             // bugfix #110654
             if (b.width == 0 || b.height == 0) {
                 return null;
             }
            if( (isWindowsVistaLaF() || isWindowsXPLaF() || isWindowsLaF()) && i == getSelectedIndex() ) {
                b.x -= 3;
                b.y -= 2;
            } else if( isWindowsXPLaF() || isWindowsLaF() || isAquaLaF() ) {
                b.x -= 2;
            } else if( isGTKLaF() && i == getSelectedIndex() ) {
                b.x -= 1;
                b.y -= 2;
            }
            if( i == getTabCount()-1 ) {
                if( isMetalLaF() )
                    b.x--;
                else if( isAquaLaF() ) 
                    b.x -= 3;
            }
            return new Rectangle(b.x + b.width - 13,
                                 b.y + b.height / 2 - 5,
                                 12,
                                 12);
        }
    }


    private boolean isWindowsVistaLaF() {
        String osName = System.getProperty ("os.name");
        return osName.indexOf("Vista") >= 0 
            || (osName.equals( "Windows NT (unknown)" ) && "6.0".equals( System.getProperty("os.version") ));
    }
    
    private boolean isWindowsXPLaF() {
        Boolean isXP = (Boolean)Toolkit.getDefaultToolkit().
                        getDesktopProperty("win.xpstyle.themeActive"); //NOI18N
        return isWindowsLaF() && (isXP == null ? false : isXP.booleanValue());
    }
    
    private boolean isWindowsLaF () {
        String lfID = UIManager.getLookAndFeel().getID();
        return lfID.endsWith("Windows"); //NOI18N
    }
    
    private static boolean isAquaLaF() {
        return "Aqua".equals( UIManager.getLookAndFeel().getID() );
    }
    
    private boolean isMetalLaF () {
        String lfID = UIManager.getLookAndFeel().getID();
        return "Metal".equals( lfID ); //NOI18N
    }

    private boolean isGTKLaF () {
        return "GTK".equals( UIManager.getLookAndFeel().getID() ); //NOI18N
    }
    
    private void setPressedCloseButtonIndex(int index) {
        if (pressedCloseButtonIndex == index)
            return;

        if (pressedCloseButtonIndex >= 0
        && pressedCloseButtonIndex < getTabCount()) {
            Rectangle r = getCloseButtonBoundsAt(pressedCloseButtonIndex);
            if (r != null) {
                repaint(r.x, r.y, r.width + 2, r.height + 2);
            }

            JComponent c = _getJComponentAt(pressedCloseButtonIndex);
            if( c != null )
                setToolTipTextAt(pressedCloseButtonIndex, c.getToolTipText());
        }

        pressedCloseButtonIndex = index;

        if (pressedCloseButtonIndex >= 0
        && pressedCloseButtonIndex < getTabCount()) {
            Rectangle r = getCloseButtonBoundsAt(pressedCloseButtonIndex);
            if (r != null) {
                repaint(r.x, r.y, r.width + 2, r.height + 2);
            }
            setMouseOverCloseButtonIndex(-1);
            setToolTipTextAt(pressedCloseButtonIndex, null);
        }
    }

    private void setMouseOverCloseButtonIndex(int index) {
        if (mouseOverCloseButtonIndex == index)
            return;

        if (mouseOverCloseButtonIndex >= 0
        && mouseOverCloseButtonIndex < getTabCount()) {
            Rectangle r = getCloseButtonBoundsAt(mouseOverCloseButtonIndex);
            if (r != null) {
                repaint(r.x, r.y, r.width + 2, r.height + 2);
            }
            JComponent c = _getJComponentAt(mouseOverCloseButtonIndex);
            if( c != null )
                setToolTipTextAt(mouseOverCloseButtonIndex, c.getToolTipText());
        }

        mouseOverCloseButtonIndex = index;

        if (mouseOverCloseButtonIndex >= 0
        && mouseOverCloseButtonIndex < getTabCount()) {
            Rectangle r = getCloseButtonBoundsAt(mouseOverCloseButtonIndex);
            if (r != null) {
                repaint(r.x, r.y, r.width + 2, r.height + 2);
            }
            setPressedCloseButtonIndex(-1);
            setToolTipTextAt(mouseOverCloseButtonIndex, null);
        }
    }

    private JComponent _getJComponentAt( int tabIndex ) {
        Component c = getComponentAt( tabIndex );
        return c instanceof JComponent ? (JComponent)c : null;
    }
    
    private void fireCloseRequest(Component c) {
        firePropertyChange(TabbedPaneFactory.PROP_CLOSE, null, c);
        if (getTabLayoutPolicy() == JTabbedPane.SCROLL_TAB_LAYOUT) {
            int idx = getSelectedIndex();
            if (idx > 0) {
                setSelectedIndex(0);
                setSelectedIndex(idx);
            }
        }
    }

    static void fixGetBoundsAt(Rectangle b) {
        if (b.y < 0)
            b.y = -b.y;
        if (b.x < 0)
            b.x = -b.x;
    }

    static int findTabForCoordinate(JTabbedPane tab, int x, int y) {
        for (int i = 0; i < tab.getTabCount(); i++) {
            Rectangle b = tab.getBoundsAt(i);
            if (b != null) {
                b = new Rectangle(b);
                fixGetBoundsAt(b);

                if (b.contains(x, y)) {
                    return i;
                }
            }
        }
        return -1;
    }
    

    @Override
    protected void processMouseEvent (MouseEvent me) {
        try {
            super.processMouseEvent (me);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            //Bug in BasicTabbedPaneUI$Handler:  The focusIndex field is not
            //updated when tabs are removed programmatically, so it will try to
            //repaint a tab that's not there
            Exceptions.attachLocalizedMessage(aioobe,
                                              "Suppressed AIOOBE bug in BasicTabbedPaneUI"); //NOI18N
            Logger.getAnonymousLogger().log(Level.WARNING, null, aioobe);
        }
    }

    @Override
    protected void fireStateChanged() {
        try {
            super.fireStateChanged();
        } catch( ArrayIndexOutOfBoundsException e ) {
            if( Utilities.isMac() ) {
                //#126651 - JTabbedPane is buggy on Mac OS
            } else {
                throw e;
            }
        }
    }

    @Override
    public Color getBackgroundAt(int index) {
        if( isWindowsLaF() && !isWindowsXPLaF() ) {
            // In Windows L&F selected and unselected tab may have same color
            // which make hard to distinguish which tab is selected (especially
            // in SCROLL_TAB_LAYOUT). In such case manage tab colors manually.
            Color selected = UIManager.getColor("controlHighlight");
            Color unselected = UIManager.getColor("control");
            if (selected.equals(unselected)) {
                //make unselected tabs darker
                unselected = new Color(Math.max(selected.getRed() - 12, 0),
                        Math.max(selected.getGreen() - 12, 0),
                        Math.max(selected.getBlue() - 12, 0));
            }
            return index == getSelectedIndex() ? selected : unselected;
        }
        return super.getBackgroundAt(index);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() instanceof Component) {
            assert evt.getPropertyName().equals(TabbedPaneFactory.NO_CLOSE_BUTTON);
            Component c = (Component) evt.getSource();
            int idx = indexOfComponent(c);
            boolean noCloseButton = (Boolean) evt.getNewValue();
            setTabComponentAt(idx, noCloseButton ? null : new ButtonTab());
        }
    }
    
    /**
     * Custom tab component for JTabbedPane
     */
    class ButtonTab extends JPanel {
        JLabel label;

        public ButtonTab() {
            super(new FlowLayout(FlowLayout.LEFT, 0, 0));
            setOpaque(false);
            label = new JLabel("") {
                
                private String lastText = null;
                
                @Override
                public String getText() {
                    String currentText = "";
                    int i = indexOfTabComponent(ButtonTab.this);
                    if (i >= 0)
                        currentText = getTitleAt(i);
                    
                    if (null != lastText && lastText.equals(currentText))
                        return lastText;
                    
                    lastText = currentText;
                    if (!super.getText().equals(currentText)) {
                        setText(currentText);
                    }
                    return currentText;
                }

                @Override
                public Icon getIcon() {
                    int i = indexOfTabComponent(ButtonTab.this);
                    Icon icon = i >= 0 ? getIconAt(i) : null;
                    if (super.getIcon() != icon) {
                        setIcon(icon);
                    }
                    return icon;
                }
            };
            add(label);
            JButton tabCloseButton = CloseButtonFactory.createCloseButton();
            tabCloseButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    int i = indexOfTabComponent(ButtonTab.this);
                    if (i != -1) {
                        fireCloseRequest(CloseButtonTabbedPane.this.getComponentAt(i));
                    }
                }
            });
            add(tabCloseButton);
        }
    }

    private class MyNavigateAction extends AbstractAction {

        private final Action orig;

        public MyNavigateAction( Action orig ) {
            this.orig = orig;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            orig.actionPerformed(e);
        }

        @Override
        public boolean isEnabled() {
            return getTabCount() > 1;
        }
    }
}
