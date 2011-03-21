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

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.undo.*;

import org.openide.nodes.Node;

import org.netbeans.modules.form.layoutsupport.*;
import org.openide.ErrorManager;

/**
 * Describes single change in FormModel. Provides UndoableEdit capable to
 * undo/redo the change.
 *
 * @author Tran Duc Trung, Tomas Pavek
 */

public class FormModelEvent extends EventObject
{
    // possible types of changes
    public static final int FORM_LOADED = 1;
    public static final int FORM_TO_BE_SAVED = 2;
    public static final int FORM_TO_BE_CLOSED = 3;
    public static final int CONTAINER_LAYOUT_EXCHANGED = 4;
    public static final int CONTAINER_LAYOUT_CHANGED = 5;
    public static final int COMPONENT_LAYOUT_CHANGED = 6;
    public static final int COMPONENT_ADDED = 7;
    public static final int COMPONENT_REMOVED = 8;
    public static final int COMPONENTS_REORDERED = 9;
    public static final int COMPONENT_PROPERTY_CHANGED = 10;
//    public static final int BINDING_PROPERTY_CHANGED = 16;
    public static final int SYNTHETIC_PROPERTY_CHANGED = 11;
    public static final int EVENT_HANDLER_ADDED = 12;
    public static final int EVENT_HANDLER_RENAMED = 14;
    public static final int OTHER_CHANGE = 15;

    // data about the change
    private int changeType;

    private boolean createdDeleted;
    private RADComponent component;
    private ComponentContainer container;
    private LayoutConstraints constraints;
    private int componentIndex = -1;
    private int[] reordering;
    private String propertyName;
    private Object oldPropertyValue;
    private Object newPropertyValue;

    private UndoableEdit undoableEdit;

    // -----------

  // -----------

    FormModelEvent(FormModel source, int changeType) {
        super(source);
        this.changeType = changeType;
        informInterestedEvents(this);
    }

    void setProperty(String propName, Object oldValue, Object newValue) {
        propertyName = propName;
        oldPropertyValue = oldValue;
        newPropertyValue = newValue;
    }

  void setComponentAndContainer(RADComponent metacomp,
                                  ComponentContainer metacont)
    {
        component = metacomp;
        container = metacont != null ? metacont : deriveContainer(metacomp);
    }

    void setLayout(RADVisualContainer metacont,
                   LayoutSupportDelegate oldLayoutSupp,
                   LayoutSupportDelegate newLayoutSupp)
    {
        component = metacont;
        container = metacont;
        oldPropertyValue = oldLayoutSupp;
        newPropertyValue = newLayoutSupp;
    }

    void setReordering(int[] perm) {
        reordering = perm;
    }

    void setAddData(RADComponent metacomp,
                    ComponentContainer metacont,
                    boolean addedNew)
    {
        setComponentAndContainer(metacomp, metacont);
        createdDeleted = addedNew;

        if (component instanceof RADVisualComponent
            && container instanceof RADVisualContainer)
        {
            componentIndex = container.getIndexOf(component);
            if (componentIndex >= 0) {
                LayoutSupportManager laysup =
                    ((RADVisualContainer)container).getLayoutSupport();
                if (laysup != null)
                    constraints = laysup.getConstraints(componentIndex);
            }
        }
    }

    void setRemoveData(RADComponent metacomp,
                       ComponentContainer metacont,
                       int index,
                       boolean removedFromModel)
    {
        component = metacomp;
        container = metacont;
        componentIndex = index;
        createdDeleted = removedFromModel;

        if (metacomp instanceof RADVisualComponent
            && metacont instanceof RADVisualContainer)
        {
            LayoutSupportManager laysup =
                ((RADVisualContainer)metacont).getLayoutSupport();
            constraints = laysup == null ? null :
                laysup.getStoredConstraints((RADVisualComponent)metacomp);
        }
    }

    void setEvent(String oldHandlerName, String newHandlerName) {
        oldPropertyValue = oldHandlerName;
        propertyName = newHandlerName;
        newPropertyValue = newHandlerName;
    }

  private static ComponentContainer deriveContainer(RADComponent comp) {
        if (comp == null)
            return null;
        if (comp.getParentComponent() instanceof ComponentContainer)
            return (ComponentContainer) comp.getParentComponent();
        else if (comp.getParentComponent() == null)
            return comp.getFormModel().getModelContainer();
        return null;
    }

    // -------

    public final FormModel getFormModel() {
        return (FormModel) getSource();
    }

    public final int getChangeType() {
        return changeType;
    }

    public final boolean isModifying() {
        return changeType != FORM_LOADED
               && changeType != FORM_TO_BE_SAVED
               && changeType != FORM_TO_BE_CLOSED
               && changeType != EVENT_HANDLER_ADDED;
    }

    public final boolean getCreatedDeleted() {
        return createdDeleted;
    }

    public final ComponentContainer getContainer() {
        return container;
    }

    public final RADComponent getComponent() {
        return component;
    }

    public final LayoutConstraints getComponentLayoutConstraints() {
        return constraints;
    }

  public final String getPropertyName() {
        return propertyName;
    }

  public final RADProperty getComponentProperty() {
        return component != null && propertyName != null ?
            component.getBeanProperty(propertyName) : null;
    }

    public final Object getOldPropertyValue() {
        return oldPropertyValue;
    }

    public final Object getNewPropertyValue() {
        return newPropertyValue;
    }

    public final LayoutSupportDelegate getOldLayoutSupport() {
        return (LayoutSupportDelegate) oldPropertyValue;
    }

    public final LayoutSupportDelegate getNewLayoutSupport() {
        return (LayoutSupportDelegate) newPropertyValue;
    }

    // ----------

    UndoableEdit getUndoableEdit() {
        if (undoableEdit == null)
            undoableEdit = new FormUndoableEdit();
        return undoableEdit;
    }

    // ----------
    // methods for events interested in additional events occured
    // (used for undo/redo processing of event handlers)

  private static void informInterestedEvents(FormModelEvent newEvent) {
    }

  // ----------

    private class FormUndoableEdit extends AbstractUndoableEdit {
        @Override
        public void undo() throws CannotUndoException {
            super.undo();

            // turn off undo/redo monitoring in FormModel while undoing!
            boolean undoRedoOn = getFormModel().isUndoRedoRecording();
            if (undoRedoOn)
                getFormModel().setUndoRedoRecording(false);

            switch(changeType) {
                case CONTAINER_LAYOUT_EXCHANGED:
                    FormModel.t("UNDO: container layout change"); // NOI18N
                    undoContainerLayoutExchange();
                    break;
                case CONTAINER_LAYOUT_CHANGED:
                    FormModel.t("UNDO: container layout property change"); // NOI18N
                    undoContainerLayoutChange();
                    break;
                case COMPONENT_LAYOUT_CHANGED:
                    FormModel.t("UNDO: component layout constraints change"); // NOI18N
                    undoComponentLayoutChange();
                    break;
                case COMPONENTS_REORDERED:
                    FormModel.t("UNDO: components reorder"); // NOI18N
                    undoComponentsReorder();
                    break;
                case COMPONENT_ADDED:
                    FormModel.t("UNDO: component addition"); // NOI18N
                    undoComponentAddition();
                    break;
                case COMPONENT_REMOVED:
                    FormModel.t("UNDO: component removal"); // NOI18N
                    undoComponentRemoval();
                    break;
                case COMPONENT_PROPERTY_CHANGED:
                    FormModel.t("UNDO: component property change"); // NOI18N
                    undoComponentPropertyChange();
                    break;
                case SYNTHETIC_PROPERTY_CHANGED:
                    FormModel.t("UNDO: synthetic property change"); // NOI18N
                    undoSyntheticPropertyChange();
                    break;

                default: FormModel.t("UNDO: "+changeType); // NOI18N
                         break;
            }

            if (undoRedoOn) // turn on undo/redo monitoring again
                getFormModel().setUndoRedoRecording(true);
        }

        @Override
        public void redo() throws CannotRedoException {
            super.redo();

            // turn off undo/redo monitoring in FormModel while redoing!
            boolean undoRedoOn = getFormModel().isUndoRedoRecording();
            if (undoRedoOn)
                getFormModel().setUndoRedoRecording(false);

            switch(changeType) {
                case CONTAINER_LAYOUT_EXCHANGED:
                    FormModel.t("REDO: container layout change"); // NOI18N
                    redoContainerLayoutExchange();
                    break;
                case CONTAINER_LAYOUT_CHANGED:
                    FormModel.t("REDO: container layout property change"); // NOI18N
                    redoContainerLayoutChange();
                    break;
                case COMPONENT_LAYOUT_CHANGED:
                    FormModel.t("REDO: component layout constraints change"); // NOI18N
                    redoComponentLayoutChange();
                    break;
                case COMPONENTS_REORDERED:
                    FormModel.t("REDO: components reorder"); // NOI18N
                    redoComponentsReorder();
                    break;
                case COMPONENT_ADDED:
                    FormModel.t("REDO: component addition"); // NOI18N
                    redoComponentAddition();
                    break;
                case COMPONENT_REMOVED:
                    FormModel.t("REDO: component removal"); // NOI18N
                    redoComponentRemoval();
                    break;
                case COMPONENT_PROPERTY_CHANGED:
                    FormModel.t("REDO: component property change"); // NOI18N
                    redoComponentPropertyChange();
                    break;
                case SYNTHETIC_PROPERTY_CHANGED:
                    FormModel.t("REDO: synthetic property change"); // NOI18N
                    redoSyntheticPropertyChange();
                    break;

                default: FormModel.t("REDO: "+changeType); // NOI18N
                         break;
            }

            if (undoRedoOn) // turn on undo/redo monitoring again
                getFormModel().setUndoRedoRecording(true);
        }

        @Override
        public String getUndoPresentationName() {
            return ""; // NOI18N
        }
        @Override
        public String getRedoPresentationName() {
            return ""; // NOI18N
        }

        // -------------

        private void undoContainerLayoutExchange() {
            try {
                LayoutSupportDelegate layoutDelegate = getOldLayoutSupport();
                if (layoutDelegate != null) {
                    getFormModel().setContainerLayoutImpl(
                            (RADVisualContainer)getContainer(), layoutDelegate);
                }
                else {
                    getFormModel().setNaturalContainerLayoutImpl(
                        (RADVisualContainer)getContainer());
                }
            }
            catch (Exception ex) {
                ErrorManager.getDefault().notify(ErrorManager.INFORMATIONAL, ex);
            }
        }

        private void redoContainerLayoutExchange() {
            try {
                LayoutSupportDelegate layoutDelegate = getNewLayoutSupport();
                if (layoutDelegate != null) {
                    getFormModel().setContainerLayoutImpl(
                            (RADVisualContainer)getContainer(), layoutDelegate);
                }
                else {
                    getFormModel().setNaturalContainerLayoutImpl(
                            (RADVisualContainer)getContainer());
                }
            }
            catch (Exception ex) {
                ErrorManager.getDefault().notify(ErrorManager.INFORMATIONAL, ex);
            }
        }

        private void undoContainerLayoutChange() {
            RADVisualContainer metacont = (RADVisualContainer) getComponent();
            LayoutSupportManager laysup = metacont.getLayoutSupport();
            if (laysup != null) {
                String propName = getPropertyName();
                if (propName != null) {
                    Node.Property prop = laysup.getLayoutProperty(propName);
                    if (prop != null) {
                        try {
                            prop.setValue(getOldPropertyValue());
                        }
                        catch (Exception ex) { // should not happen
                            Logger.getLogger(getClass().getName()).log(Level.INFO, ex.getMessage(), ex);
                        }
                    }
                }
            }
            else {
                getFormModel().fireContainerLayoutChanged(metacont, null, null, null);
            }
        }

        private void redoContainerLayoutChange() {
            RADVisualContainer metacont = (RADVisualContainer) getComponent();
            LayoutSupportManager laysup = metacont.getLayoutSupport();
            if (laysup != null) {
                String propName = getPropertyName();
                if (propName != null) {
                    Node.Property prop = laysup.getLayoutProperty(propName);
                    if (prop != null) {
                        try {
                            prop.setValue(getNewPropertyValue());
                        }
                        catch (Exception ex) { // should not happen
                            Logger.getLogger(getClass().getName()).log(Level.INFO, ex.getMessage(), ex);
                        }
                    }
                }
            }
            else {
                getFormModel().fireContainerLayoutChanged(metacont, null, null, null);
            }
        }

        private void undoComponentLayoutChange() {
            if (getComponent() instanceof RADVisualComponent) {
                ((RADVisualComponent)getComponent()).getConstraintsProperties();
                Node.Property prop =
                    getComponent().getPropertyByName(getPropertyName());
                if (prop != null)
                    try {
                        prop.setValue(getOldPropertyValue());
                    }
                    catch (Exception ex) { // should not happen
                        Logger.getLogger(getClass().getName()).log(Level.INFO, ex.getMessage(), ex);
                    }
            }
        }

        private void redoComponentLayoutChange() {
            if (getComponent() instanceof RADVisualComponent) {
                ((RADVisualComponent)getComponent()).getConstraintsProperties();
                Node.Property prop =
                    getComponent().getPropertyByName(getPropertyName());
                if (prop != null)
                    try {
                        prop.setValue(getNewPropertyValue());
                    }
                    catch (Exception ex) { // should not happen
                        Logger.getLogger(getClass().getName()).log(Level.INFO, ex.getMessage(), ex);
                    }
            }
        }

        private void undoComponentAddition() {
            removeComponent();
        }

        private void undoComponentRemoval() {
            addComponent();
        }

        private void redoComponentAddition() {
            addComponent();
        }

        private void redoComponentRemoval() {
            removeComponent();
        }

        private void addComponent() {
            RADComponent component = getComponent();
            ComponentContainer container = getContainer();
            RADComponent[] currentSubComps = container.getSubBeans();
            RADComponent[] undoneSubComps =
                new RADComponent[currentSubComps.length+1];

            if (componentIndex < 0)
                componentIndex = currentSubComps.length;

            for (int i=0,j=0; j < undoneSubComps.length; i++,j++) {
                if (i == componentIndex) {
                    undoneSubComps[j] = component;
                    if (i == currentSubComps.length)
                        break;
                    j++;
                }
                undoneSubComps[j] = currentSubComps[i];
            }

            if (getCreatedDeleted() || !component.isInModel()) {
                FormModel.setInModelRecursively(component, true);
            }

            container.initSubComponents(undoneSubComps);

            if (component instanceof RADVisualComponent) {
                if (container instanceof RADVisualContainer) {
                    LayoutSupportManager layoutSupport =
                        ((RADVisualContainer)container).getLayoutSupport();
                    if (layoutSupport != null)
                        layoutSupport.addComponents(
                            new RADVisualComponent[] { (RADVisualComponent)component },
                            new LayoutConstraints[] { getComponentLayoutConstraints() },
                            componentIndex);
                } else {
                    ((RADVisualComponent)component).resetConstraintsProperties();
                }
            }

            getFormModel().fireComponentAdded(component, getCreatedDeleted());
        }

        private void removeComponent() {
            getFormModel().removeComponentImpl(getComponent(), getCreatedDeleted());
        }

        private void undoComponentsReorder() {
            if (getContainer() != null && reordering != null) {
                int[] revPerm = new int[reordering.length];
                for (int i=0; i < reordering.length; i++)
                    revPerm[reordering[i]] = i;

                getContainer().reorderSubComponents(revPerm);
                getFormModel().fireComponentsReordered(getContainer(), revPerm);
            }
        }

        private void redoComponentsReorder() {
            if (getContainer() != null && reordering != null) {
                getContainer().reorderSubComponents(reordering);
                getFormModel().fireComponentsReordered(getContainer(),
                                                       reordering);
            }
        }

        private void undoComponentPropertyChange() {
            Node.Property prop =
                getComponent().getPropertyByName(getPropertyName());
            if (prop != null)
                try {
                    prop.setValue(getOldPropertyValue());
                }
                catch (Exception ex) { // should not happen
                    Logger.getLogger(getClass().getName()).log(Level.INFO, ex.getMessage(), ex);
                }
        }

        private void redoComponentPropertyChange() {
            Node.Property prop =
                getComponent().getPropertyByName(getPropertyName());
            if (prop != null)
                try {
                    prop.setValue(getNewPropertyValue());
                }
                catch (Exception ex) { // should not happen
                    Logger.getLogger(getClass().getName()).log(Level.INFO, ex.getMessage(), ex);
                }
        }

      // TODO: stripped
//        private void undoBindingPropertyChange() {
//            String subPropName = getSubPropertyName();
//            BindingProperty prop = getComponent().getBindingProperty(getPropertyName());
//            if (subPropName == null) {
//                if (prop != null) {
//                    try {
//                        prop.setValue(getOldBinding());
//                    } catch (Exception ex) { // should not happen
//                        Logger.getLogger(getClass().getName()).log(Level.INFO, ex.getMessage(), ex);
//                    }
//                }
//            } else {
//                FormProperty subProp = prop.getSubProperty(subPropName);
//                try {
//                    subProp.setValue(getOldPropertyValue());
//                } catch (Exception ex) {
//                    Logger.getLogger(getClass().getName()).log(Level.INFO, ex.getMessage(), ex);
//                }
//            }
//        }

      // TODO: stripped
//        private void redoBindingPropertyChange() {
//            String subPropName = getSubPropertyName();
//            BindingProperty prop = getComponent().getBindingProperty(getPropertyName());
//            if (subPropName == null) {
//                if (prop != null) {
//                    try {
//                        prop.setValue(getNewBinding());
//                    } catch (Exception ex) { // should not happen
//                        Logger.getLogger(getClass().getName()).log(Level.INFO, ex.getMessage(), ex);
//                    }
//                }
//            } else {
//                FormProperty subProp = prop.getSubProperty(subPropName);
//                try {
//                    subProp.setValue(getNewPropertyValue());
//                } catch (Exception ex) {
//                    Logger.getLogger(getClass().getName()).log(Level.INFO, ex.getMessage(), ex);
//                }
//            }
//        }

        private void undoSyntheticPropertyChange() {
            String propName = getPropertyName();
            if (propName.startsWith(RADProperty.SYNTH_PREFIX)) {
                // special case - pre/post init code of a property
                if (propName.startsWith(RADProperty.SYNTH_PRE_CODE)) {
                    FormProperty prop = (FormProperty)
                      getComponent().getPropertyByName(
                        propName.substring(RADProperty.SYNTH_PRE_CODE.length()));
                    prop.setPreCode((String)getOldPropertyValue());
                }
                else if (propName.startsWith(RADProperty.SYNTH_POST_CODE)) {
                    FormProperty prop = (FormProperty)
                      getComponent().getPropertyByName(
                        propName.substring(RADProperty.SYNTH_POST_CODE.length()));
                    prop.setPostCode((String)getOldPropertyValue());
                }
            } else { 
                Node.Property[] props;
                if (getComponent() == null) { // form synthetic property
                    FormEditor formEditor = FormEditor.getFormEditor(getFormModel());
                    FormRootNode rootNode = (FormRootNode)formEditor.getFormRootNode();
                    props = rootNode.getAllProperties();
                } else { // component synthetic property
                    props = getComponent().getSyntheticProperties();
                }
              for (Node.Property prop : props)
              {
                if (prop.getName().equals(propName))
                {
                  try
                  {
                    prop.setValue(getOldPropertyValue());
                  }
                  catch (Exception ex)
                  { // should not happen
                    Logger.getLogger(getClass().getName()).log(Level.INFO, ex.getMessage(), ex);
                  }
                  break;
                }
              }
            }
        }

        private void redoSyntheticPropertyChange() {
            String propName = getPropertyName();
            if (propName.startsWith(RADProperty.SYNTH_PREFIX)) {
                // special case - pre/post init code of a property
                if (propName.startsWith(RADProperty.SYNTH_PRE_CODE)) {
                    FormProperty prop = (FormProperty)
                      getComponent().getPropertyByName(
                        propName.substring(RADProperty.SYNTH_PRE_CODE.length()));
                    prop.setPreCode((String)getNewPropertyValue());
                }
                else if (propName.startsWith(RADProperty.SYNTH_POST_CODE)) {
                    FormProperty prop = (FormProperty)
                      getComponent().getPropertyByName(
                        propName.substring(RADProperty.SYNTH_POST_CODE.length()));
                    prop.setPostCode((String)getNewPropertyValue());
                }
            } else {
                Node.Property[] props;
                if (getComponent() == null) { // form synthetic property
                    FormEditor formEditor = FormEditor.getFormEditor(getFormModel());
                    FormRootNode rootNode = (FormRootNode)formEditor.getFormRootNode();
                    props = rootNode.getAllProperties();
                } else { // component synthetic property
                    props = getComponent().getSyntheticProperties();
                }
              for (Node.Property prop : props)
              {
                if (prop.getName().equals(propName))
                {
                  try
                  {
                    prop.setValue(getNewPropertyValue());
                  }
                  catch (Exception ex)
                  { // should not happen
                    Logger.getLogger(getClass().getName()).log(Level.INFO, ex.getMessage(), ex);
                  }
                  break;
                }
              }
            }
        }
    }
}
