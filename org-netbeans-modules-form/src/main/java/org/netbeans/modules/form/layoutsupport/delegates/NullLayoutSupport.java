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

package org.netbeans.modules.form.layoutsupport.delegates;

import java.awt.*;
import java.beans.*;
import java.util.*;
import java.lang.reflect.Method;

import org.openide.util.ImageUtilities;

import org.netbeans.modules.form.layoutsupport.*;

/**
 * Support for null layout manager.
 *
 * @author Tomas Pavek
 */

public class NullLayoutSupport extends AbsoluteLayoutSupport {

    private static Method setBoundsMethod;

    /** The icon for NullLayout. */
    private static String iconURL = "org/netbeans/modules/form/layoutsupport/resources/NullLayout.gif"; // NOI18N
    /** The icon for NullLayout. */
    private static String icon32URL = "org/netbeans/modules/form/layoutsupport/resources/NullLayout32.gif"; // NOI18N    

    /** Gets the supported layout manager class - this one is rather special,
     * it's null.
     * @return the class supported by this delegate
     */
    @Override
    public Class getSupportedClass() {
        return null;
    }

    /** Provides an icon to be used for the layout node in Component
     * Inspector. Only 16x16 color icon is required.
     * @param type is one of BeanInfo constants: ICON_COLOR_16x16,
     *        ICON_COLOR_32x32, ICON_MONO_16x16, ICON_MONO_32x32
     * @return icon to be displayed for node in Component Inspector
     */
    @Override
    public Image getIcon(int type) {
        switch (type) {
            case BeanInfo.ICON_COLOR_16x16:
            case BeanInfo.ICON_MONO_16x16:
                return ImageUtilities.loadImage(iconURL);
            default:
                return ImageUtilities.loadImage(icon32URL);
        }
    }

    /** Sets up the layout (without adding components) on a real container,
     * according to the internal metadata representation.
     * @param container instance of a real container to be set
     * @param containerDelegate effective container delegate of the container
     *        (e.g. like content pane of JFrame)
     */
    @Override
    public void setLayoutToContainer(Container container,
                                     Container containerDelegate)
    {
        containerDelegate.setLayout(null);
    }

    /** Adds real components to given container (according to layout
     * constraints stored for the components).
     * @param container instance of a real container to be added to
     * @param containerDelegate effective container delegate of the container
     *        (e.g. like content pane of JFrame)
     * @param components components to be added
     * @param index position at which to add the components to container
     */
    @Override
    public void addComponentsToContainer(Container container,
                                         Container containerDelegate,
                                         Component[] components,
                                         int index)
    {
        for (int i=0; i < components.length; i++) {
            LayoutConstraints constr = getConstraints(i + index);
            if (constr instanceof AbsoluteLayoutConstraints) {
                AbsoluteLayoutConstraints alc = (AbsoluteLayoutConstraints)constr;
                Component comp = components[i];
                Rectangle bounds = alc.getBounds();
                if (bounds.width == -1 || bounds.height == -1) {
                    Dimension pref = !(comp instanceof javax.swing.JComponent)
                                            && alc.refComponent != null ?
                        alc.refComponent.getPreferredSize() :
                        comp.getPreferredSize();

                    if (bounds.width == -1)
                        bounds.width = pref.width;
                    if (bounds.height == -1)
                        bounds.height = pref.height;
                }
                containerDelegate.add(comp, i + index);
                comp.setBounds(bounds);
            }
        }
    }

    // ---------

    private static Method getSetBoundsMethod() {
        if (setBoundsMethod == null) {
            try {
                setBoundsMethod = Component.class.getMethod(
                                    "setBounds", // NOI18N
                                    new Class[] { Integer.TYPE, Integer.TYPE,
                                                  Integer.TYPE, Integer.TYPE });
            }
            catch (NoSuchMethodException ex) { // should not happen
                ex.printStackTrace();
            }
        }
        return setBoundsMethod;
    }
}
