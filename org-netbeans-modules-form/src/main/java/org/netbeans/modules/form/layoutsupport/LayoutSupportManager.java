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

package org.netbeans.modules.form.layoutsupport;

import java.awt.*;
import java.beans.*;
import java.util.*;

import org.openide.nodes.*;

import org.netbeans.modules.form.*;
import org.netbeans.modules.form.layoutsupport.delegates.NullLayoutSupport;
import org.netbeans.modules.form.fakepeer.FakePeerSupport;

/**
 * Main class of general layout support infrastructure. Connects form editor
 * metadata with specialized LayoutSupportDelegate implementations (layout
 * specific functionality is delegated to the right LayoutSupportDelegate).
 *
 * @author Tomas Pavek
 */

public final class LayoutSupportManager implements LayoutSupportContext {

    // possible component resizing directions (bit flag constants)
    public static final int RESIZE_UP = 1;
    public static final int RESIZE_DOWN = 2;
    public static final int RESIZE_LEFT = 4;
    public static final int RESIZE_RIGHT = 8;

    private LayoutSupportDelegate layoutDelegate;
    private boolean needInit;
    private boolean initializeFromInstance;

    private Node.PropertySet[] propertySets;

    private LayoutListener layoutListener;

    private final RADVisualContainer metaContainer;

    private Container primaryContainer; // bean instance from metaContainer
    private Container primaryContainerDelegate; // container delegate for it

  // ----------
    // initialization

    // initialization for a new container, layout delegate is set to null
    public LayoutSupportManager(RADVisualContainer container)
    {
        this.metaContainer = container;
    }

    /**
     * Creation and initialization of a layout delegate for a new container.
     * @param initialize whether the layout-delegate shall be installed.
     * @return false if suitable layout delegate is not found
     * @throw Exception if the container instance is not empty
     */
    public boolean prepareLayoutDelegate(boolean initialize) throws Exception
    {
        LayoutSupportDelegate delegate;
        LayoutManager lmInstance = null;

        FormModel formModel = metaContainer.getFormModel();
        LayoutSupportRegistry layoutRegistry =
            LayoutSupportRegistry.getRegistry(formModel);

        // first try to find a dedicated layout delegate (for the container)
        Class layoutDelegateClass = layoutRegistry.getSupportClassForContainer(
                                                  metaContainer.getBeanClass());

        if (layoutDelegateClass != null) {
            delegate = (LayoutSupportDelegate) layoutDelegateClass.newInstance();
            if (!delegate.checkEmptyContainer(getPrimaryContainer())) {
                RuntimeException ex = new IllegalArgumentException();
                org.openide.ErrorManager.getDefault().annotate(
                    ex, AbstractLayoutSupport.getBundle().getString(
                                        "MSG_ERR_NonEmptyContainer")); // NOI18N
                throw ex;
            }
        }
        else {
          // initialization from LayoutManager instance
          Container contDel = getPrimaryContainerDelegate();
          if (contDel.getComponentCount() == 0) {
              // we can still handle only empty containers ...
              lmInstance = contDel.getLayout();
              delegate = lmInstance != null ?
                  layoutRegistry.createSupportForLayout(lmInstance.getClass()) :
                  new NullLayoutSupport();
          }
          else {
              RuntimeException ex = new IllegalArgumentException();
              org.openide.ErrorManager.getDefault().annotate(
                  ex, AbstractLayoutSupport.getBundle().getString(
                                      "MSG_ERR_NonEmptyContainer")); // NOI18N
              throw ex;
          }
        }

        if (delegate == null)
            return false;

        if (initialize) {
            setLayoutDelegate(delegate);
        }
        else {
            layoutDelegate = delegate;
            needInit = true;
            initializeFromInstance = lmInstance != null;
        }

        return true;
    }

    public void initializeLayoutDelegate() throws Exception {
        if (layoutDelegate != null && needInit) {
            LayoutManager lmInstance = initializeFromInstance ?
                    getPrimaryContainerDelegate().getLayout() : null;
            layoutDelegate.initialize(this, lmInstance);
            fillLayout(null);
            getPropertySets(); // force properties and listeners creation
            needInit = false;
        }
    }

    public void setLayoutDelegate(LayoutSupportDelegate newDelegate) throws Exception
    {
        LayoutConstraints[] oldConstraints;
        LayoutSupportDelegate oldDelegate = layoutDelegate;

        if (layoutDelegate != null)
            oldConstraints = removeLayoutDelegate(true);
        else
            oldConstraints = null;

        layoutDelegate = newDelegate;
        propertySets = null;
        needInit = false;

        if (layoutDelegate != null) {
            try {
                layoutDelegate.initialize(this, null);
                fillLayout(oldConstraints);
                getPropertySets(); // force properties and listeners creation
            }
            catch (Exception ex) {
                removeLayoutDelegate(false);
                layoutDelegate = oldDelegate;
                if (layoutDelegate != null)
                    fillLayout(null);
                throw ex;
            }
        }
    }

    public LayoutSupportDelegate getLayoutDelegate() {
        return layoutDelegate;
    }

    public void setUnknownLayoutDelegate() {
        try {
            setLayoutDelegate(new UnknownLayoutSupport());
        }
        catch (Exception ex) { // nothing should happen, ignore
            ex.printStackTrace();
        }
    }

  public boolean isSpecialLayout() {
        // Every standard layout manager has its own layout delegate.
        // Hence, the DefaultLayoutSupport is used by special layout managers only.
        return !(layoutDelegate instanceof DefaultLayoutSupport);
    }

    // copy layout delegate from another container
    public void copyLayoutDelegateFrom(
                    LayoutSupportManager sourceLayoutSupport,
                    RADVisualComponent[] newMetaComps)
    {
        LayoutSupportDelegate sourceDelegate =
            sourceLayoutSupport.getLayoutDelegate();

        int componentCount = sourceDelegate.getComponentCount();

        Container cont = getPrimaryContainer();
        Container contDel = getPrimaryContainerDelegate();

        if (layoutDelegate != null)
            removeLayoutDelegate(false);

        Component[] primaryComps = new Component[componentCount];

        for (int i=0; i < componentCount; i++) {
            RADVisualComponent metacomp = newMetaComps[i];
            primaryComps[i] = (Component) metacomp.getBeanInstance();
            ensureFakePeerAttached(primaryComps[i]);
        }

        LayoutSupportDelegate newDelegate = sourceDelegate.cloneLayoutSupport(this, newMetaComps);

        newDelegate.setLayoutToContainer(cont, contDel);
        newDelegate.addComponentsToContainer(cont, contDel, primaryComps, 0);

        layoutDelegate = newDelegate;

        // Ensure correct propagation of copied properties (issue 50011, 72351)
        try {
            layoutDelegate.acceptContainerLayoutChange(null);
        } catch (PropertyVetoException pvex) {
            // should not happen
        }
    }

  public RADVisualContainer getMetaContainer() {
        return metaContainer;
    }

//    public boolean supportsArranging() {
//        return layoutDelegate instanceof LayoutSupportArranging;
//    }

    private LayoutConstraints[] removeLayoutDelegate(boolean extractConstraints)
    {
        int componentCount = layoutDelegate.getComponentCount();
        LayoutConstraints[] constraints = null;

        if (componentCount > 0) {
            RADVisualComponent[] metacomps = metaContainer.getSubComponents();
            if (metacomps.length == componentCount) { // robustness: might be called after failed layout initialization
                if (extractConstraints)
                    constraints = new LayoutConstraints[componentCount];

                for (int i=0; i < componentCount; i++) {
                    LayoutConstraints constr = layoutDelegate.getConstraints(i);
                    if (extractConstraints)
                        constraints[i] = constr;
                    if (constr != null)
                        metacomps[i].setLayoutConstraints(layoutDelegate.getClass(), constr);
                }
            }
        }

        layoutDelegate.removeAll();
        layoutDelegate.clearContainer(getPrimaryContainer(),
                                      getPrimaryContainerDelegate());
        layoutDelegate = null;

        return constraints;
    }

    private void fillLayout(LayoutConstraints[] oldConstraints) {
        RADVisualComponent[] metacomps = metaContainer.getSubComponents();
        int componentCount = metacomps.length;

        Component[] designComps = new Component[componentCount];
        Component[] primaryComps = new Component[componentCount];
        LayoutConstraints[] newConstraints = new LayoutConstraints[componentCount];

        FormDesigner designer = FormEditor.getFormDesigner(metaContainer.getFormModel());

        for (int i=0; i < componentCount; i++) {
            RADVisualComponent metacomp = metacomps[i];

            primaryComps[i] = (Component) metacomp.getBeanInstance();
            ensureFakePeerAttached(primaryComps[i]);
            newConstraints[i] = metacomp.getLayoutConstraints(
                                             layoutDelegate.getClass());

            Component comp = designer != null ?
                            (Component) designer.getComponent(metacomp) : null;
            designComps[i] = comp != null ?
                             comp : (Component) metacomp.getBeanInstance();
        }

        layoutDelegate.convertConstraints(oldConstraints,
                                          newConstraints,
                                          designComps);

        if (componentCount > 0) {
            layoutDelegate.acceptNewComponents();
            layoutDelegate.addComponents(metacomps, newConstraints, 0);

            for (int i=0; i < componentCount; i++)
                metacomps[i].resetConstraintsProperties();
            }

        // setup primary container
        Container cont = getPrimaryContainer();
        Container contDel = getPrimaryContainerDelegate();
//        layoutDelegate.clearContainer(cont, contDel);
        layoutDelegate.setLayoutToContainer(cont, contDel);
        if (componentCount > 0)
            layoutDelegate.addComponentsToContainer(cont, contDel,
                                                    primaryComps, 0);
    }

    // ---------
    // public API delegated to LayoutSupportDelegate

    public boolean isDedicated() {
        return layoutDelegate.isDedicated();
    }

    public Class getSupportedClass() {
        return layoutDelegate.getSupportedClass();
    }

    // node presentation
    public boolean shouldHaveNode() {
        return layoutDelegate.shouldHaveNode();
    }

    public String getDisplayName() {
        return layoutDelegate.getDisplayName();
    }

    public Image getIcon(int type) {
        return layoutDelegate.getIcon(type);
    }

    // properties and customizer
    public Node.PropertySet[] getPropertySets() {
        if (propertySets == null) {
            if (layoutDelegate == null) return new Node.PropertySet[0]; // Issue 63916
            propertySets = layoutDelegate.getPropertySets();

          for (Node.PropertySet propertySet : propertySets)
          {
            Node.Property[] props = propertySet.getProperties();
            for (Node.Property prop1 : props)
              if (prop1 instanceof FormProperty)
              {
                FormProperty prop = (FormProperty) prop1;
                prop.addVetoableChangeListener(getLayoutListener());
                prop.addPropertyChangeListener(getLayoutListener());
              }
          }
        }
        return propertySets;
    }

    public Node.Property[] getAllProperties() {
        if (layoutDelegate instanceof AbstractLayoutSupport)
            return ((AbstractLayoutSupport)layoutDelegate).getAllProperties();

        java.util.List<Node.Property> allPropsList = new ArrayList<Node.Property>();
      for (Node.PropertySet propertySet : propertySets)
      {
        Node.Property[] props = propertySet.getProperties();
        allPropsList.addAll(Arrays.asList(props));
      }

        Node.Property[] allProperties = new Node.Property[allPropsList.size()];
        allPropsList.toArray(allProperties);
        return allProperties;
    }

    public Node.Property getLayoutProperty(String name) {
        if (layoutDelegate instanceof AbstractLayoutSupport)
            return ((AbstractLayoutSupport)layoutDelegate).getProperty(name);

        Node.Property[] properties = getAllProperties();
      for (Node.Property property : properties)
        if (name.equals(property.getName()))
          return property;

        return null;
    }

    public Class getCustomizerClass() {
        return layoutDelegate.getCustomizerClass();
    }

    public Component getSupportCustomizer() {
        return layoutDelegate.getSupportCustomizer();
    }

  // data validation
    public void acceptNewComponents()
    {
        layoutDelegate.acceptNewComponents();
    }

    // components adding/removing
    public void addComponents(RADVisualComponent[] components, LayoutConstraints[] constraints, int index)
    {
        Component[] comps = new Component[components.length];

        for (int i=0; i < components.length; i++) {
            comps[i] = (Component) components[i].getBeanInstance();
            ensureFakePeerAttached(comps[i]);
        }

        if (index < 0)
            index = layoutDelegate.getComponentCount();

        layoutDelegate.addComponents(components, constraints, index);

        for (RADVisualComponent component : components)
          component.resetConstraintsProperties();

        layoutDelegate.addComponentsToContainer(getPrimaryContainer(),
                                                getPrimaryContainerDelegate(),
                                                comps, index);
    }

    public void removeComponent(RADVisualComponent metacomp, int index) {
        // first store constraints in the meta component
        LayoutConstraints constr = layoutDelegate.getConstraints(index);
        if (constr != null)
            metacomp.setLayoutConstraints(layoutDelegate.getClass(), constr);

        // remove the component from layout
        layoutDelegate.removeComponent(index);

        // remove the component instance from the primary container instance
        if (!layoutDelegate.removeComponentFromContainer(
                                getPrimaryContainer(),
                                getPrimaryContainerDelegate(),
                                (Component)metacomp.getBeanInstance()))
        {   // layout delegate does not support removing individual components,
            // so we clear the container and add the remaining components again
            layoutDelegate.clearContainer(getPrimaryContainer(),
                                          getPrimaryContainerDelegate());

            RADVisualComponent[] metacomps = metaContainer.getSubComponents();
            if (metacomps.length > 1) {
                // we rely on that metacomp was not removed from the model yet
                Component[] comps = new Component[metacomps.length-1];
                for (int i=0; i < metacomps.length; i++) {
                    if (i != index) {
                        Component comp = (Component) metacomps[i].getBeanInstance();
                        ensureFakePeerAttached(comp);
                        comps[i < index ? i : i-1] = comp;
                    }
                }
                layoutDelegate.addComponentsToContainer(
                                   getPrimaryContainer(),
                                   getPrimaryContainerDelegate(),
                                   comps,
                                   0);
            }
        }
    }

    public void removeAll() {
        // first store constraints in meta components
        RADVisualComponent[] components = metaContainer.getSubComponents();
        for (int i=0; i < components.length; i++) {
            LayoutConstraints constr =
                layoutDelegate.getConstraints(i);
            if (constr != null)
                components[i].setLayoutConstraints(layoutDelegate.getClass(),
                                                   constr);
        }

        // remove components from layout
        layoutDelegate.removeAll();

        // clear the primary container instance
        layoutDelegate.clearContainer(getPrimaryContainer(),
                                      getPrimaryContainerDelegate());
    }

  // managing constraints
    public LayoutConstraints getConstraints(int index) {
        return layoutDelegate.getConstraints(index);
    }

    public LayoutConstraints getConstraints(RADVisualComponent metacomp) {
        if (layoutDelegate == null)
            return null;

        int index = metaContainer.getIndexOf(metacomp);
        return index >= 0 && index < layoutDelegate.getComponentCount() ?
               layoutDelegate.getConstraints(index) : null;
    }

    public static LayoutConstraints storeConstraints(
                                        RADVisualComponent metacomp)
    {
        RADVisualContainer parent = metacomp.getParentContainer();
        if (parent == null)
            return null;

        LayoutSupportManager layoutSupport = parent.getLayoutSupport();
        if (layoutSupport == null)
            return null;
        LayoutConstraints constr = layoutSupport.getConstraints(metacomp);
        if (constr != null)
            metacomp.setLayoutConstraints(
                         layoutSupport.getLayoutDelegate().getClass(),
                         constr);
        return constr;
    }

    public LayoutConstraints getStoredConstraints(RADVisualComponent metacomp) {
        return metacomp.getLayoutConstraints(layoutDelegate.getClass());
    }

    // managing live components
    public void setLayoutToContainer(Container container,
                                     Container containerDelegate)
    {
        layoutDelegate.setLayoutToContainer(container, containerDelegate);
    }

    public void addComponentsToContainer(Container container,
                                         Container containerDelegate,
                                         Component[] components,
                                         int index)
    {
        layoutDelegate.addComponentsToContainer(container, containerDelegate,
                                                components, index);
    }

    public boolean removeComponentFromContainer(Container container,
                                                Container containerDelegate,
                                                Component component)
    {
        return !layoutDelegate.removeComponentFromContainer(
                            container, containerDelegate, component);
    }

    public boolean clearContainer(Container container,
                                  Container containerDelegate)
    {
        return layoutDelegate.clearContainer(container, containerDelegate);
    }

    // drag and drop support
    public LayoutConstraints getNewConstraints(Container container,
                                               Container containerDelegate,
                                               Component component,
                                               int index,
                                               Point posInCont,
                                               Point posInComp)
    {
        
        LayoutConstraints constraints =  layoutDelegate.getNewConstraints(container, containerDelegate,
                                                component, index,
                                                posInCont, posInComp);
        String context = null;
        Object[] params = null;
        if (layoutDelegate instanceof AbstractLayoutSupport) {
            AbstractLayoutSupport support = (AbstractLayoutSupport)layoutDelegate;
            context = support.getAssistantContext();
            params = support.getAssistantParams();
        }
        context = (context == null) ? "generalPosition" : context; // NOI18N
        FormEditor.getAssistantModel(metaContainer.getFormModel()).setContext(context, params);
        return constraints;
    }

    public int getNewIndex(Container container,
                           Container containerDelegate,
                           Component component,
                           int index,
                           Point posInCont,
                           Point posInComp)
    {
        return layoutDelegate.getNewIndex(container, containerDelegate,
                                          component, index,
                                          posInCont, posInComp);
    }

    public boolean paintDragFeedback(Container container, 
                                     Container containerDelegate,
                                     Component component,
                                     LayoutConstraints newConstraints,
                                     int newIndex,
                                     Graphics g)
    {
        return layoutDelegate.paintDragFeedback(container, containerDelegate,
                                                component,
                                                newConstraints, newIndex,
                                                g);
    }

    // resizing support
    public int getResizableDirections(Container container,
                                      Container containerDelegate,
                                      Component component,
                                      int index)
    {
        return layoutDelegate.getResizableDirections(container,
                                                     containerDelegate,
                                                     component, index);
    }

    public LayoutConstraints getResizedConstraints(Container container,
                                                   Container containerDelegate,
                                                   Component component,
                                                   int index,
                                                   Rectangle originalBounds,
                                                   Insets sizeChanges,
                                                   Point posInCont)
    {
        return layoutDelegate.getResizedConstraints(container,
                                                    containerDelegate,
                                                    component, index,
                                                    originalBounds,
                                                    sizeChanges,
                                                    posInCont);
    }

    // arranging support
    public void processMouseClick(Point p,
                                  Container cont,
                                  Container contDelegate)
    {
        layoutDelegate.processMouseClick(p, cont, contDelegate);
    }

    // arranging support
    public void selectComponent(int index) {
        layoutDelegate.selectComponent(index);
    }

    // arranging support
    public void arrangeContainer(Container container,
                                 Container containerDelegate)
    {
        layoutDelegate.arrangeContainer(container, containerDelegate);
    }

    // -----------
    // API for layout delegates (LayoutSupportContext implementation)

  // return container instance of meta container
    @Override
    public Container getPrimaryContainer() {
        return (Container) metaContainer.getBeanInstance();
    }

    // return container delegate of container instance of meta container
    @Override
    public Container getPrimaryContainerDelegate() {
        Container defCont = (Container) metaContainer.getBeanInstance();
        if (primaryContainerDelegate == null || primaryContainer != defCont) {
            primaryContainer = defCont;
            primaryContainerDelegate =
                metaContainer.getContainerDelegate(defCont);
        }
        return primaryContainerDelegate;
    }

    // return component instance of meta component
    @Override
    public Component getPrimaryComponent(int index) {
        return (Component) metaContainer.getSubComponent(index).getBeanInstance();
    }

    @Override
    public void updatePrimaryContainer() {
        Container cont = getPrimaryContainer();
        Container contDel = getPrimaryContainerDelegate();

        layoutDelegate.clearContainer(cont, contDel);
        layoutDelegate.setLayoutToContainer(cont, contDel);

        RADVisualComponent[] components = metaContainer.getSubComponents();
        if (components.length > 0) {
            Component[] comps = new Component[components.length];
            for (int i=0; i < components.length; i++) {
                comps[i] = (Component) components[i].getBeanInstance();
                ensureFakePeerAttached(comps[i]);
            }

            layoutDelegate.addComponentsToContainer(cont, contDel, comps, 0);
        }
    }

    @Override
    public void containerLayoutChanged(PropertyChangeEvent ev)
        throws PropertyVetoException
    {
        if (ev != null && ev.getPropertyName() != null) {
            layoutDelegate.acceptContainerLayoutChange(getEventWithValues(ev));

            FormModel formModel = metaContainer.getFormModel();
            formModel.fireContainerLayoutChanged(metaContainer,
                                                 ev.getPropertyName(),
                                                 ev.getOldValue(),
                                                 ev.getNewValue());
        }
        else propertySets = null;

        LayoutNode node = metaContainer.getLayoutNodeReference();
        if (node != null) {
            // propagate the change to node
            if (ev != null && ev.getPropertyName() != null)
                node.fireLayoutPropertiesChange();
            else
                node.fireLayoutPropertySetsChange();
        }
    }

    @Override
    public void componentLayoutChanged(int index, PropertyChangeEvent ev)
        throws PropertyVetoException
    {
        RADVisualComponent metacomp = metaContainer.getSubComponent(index);

        if (ev != null && ev.getPropertyName() != null) {
            layoutDelegate.acceptComponentLayoutChange(index,
                                                       getEventWithValues(ev));

            FormModel formModel = metaContainer.getFormModel();
            formModel.fireComponentLayoutChanged(metacomp,
                                                 ev.getPropertyName(),
                                                 ev.getOldValue(),
                                                 ev.getNewValue());

            if (metacomp.getNodeReference() != null) // propagate the change to node
                metacomp.getNodeReference().firePropertyChangeHelper(
//                                                     null, null, null);
                    ev.getPropertyName(),
                    ev.getOldValue(),
                    ev.getNewValue());
        }
        else {
            if (metacomp.getNodeReference() != null) // propagate the change to node
                metacomp.getNodeReference().fireComponentPropertySetsChange();
            metacomp.resetConstraintsProperties();
        }
    }

    private static PropertyChangeEvent getEventWithValues(PropertyChangeEvent ev) {
        Object oldVal = ev.getOldValue();
        Object newVal = ev.getNewValue();
        if (oldVal instanceof FormProperty.ValueWithEditor)
            ev = new PropertyChangeEvent(
                         ev.getSource(),
                         ev.getPropertyName(),
                         ((FormProperty.ValueWithEditor)oldVal).getValue(),
                         ((FormProperty.ValueWithEditor)newVal).getValue());
        return ev;
    }

    // ---------

    private LayoutListener getLayoutListener() {
        if (layoutListener == null)
            layoutListener = new LayoutListener();
        return layoutListener;
    }

    private class LayoutListener implements VetoableChangeListener,
                                            PropertyChangeListener
    {
        @Override
        public void vetoableChange(PropertyChangeEvent ev)
            throws PropertyVetoException
        {
            Object source = ev.getSource();
            String eventName = ev.getPropertyName();
            if (source instanceof FormProperty
                && (FormProperty.PROP_VALUE.equals(eventName)
                    || FormProperty.PROP_VALUE_AND_EDITOR.equals(eventName)))
            {
                ev = new PropertyChangeEvent(layoutDelegate,
                                             ((FormProperty)source).getName(),
                                             ev.getOldValue(),
                                             ev.getNewValue());

                containerLayoutChanged(ev);
            }
        }

        @Override
        public void propertyChange(PropertyChangeEvent ev) {
            Object source = ev.getSource();
            if (source instanceof FormProperty
                && FormProperty.CURRENT_EDITOR.equals(ev.getPropertyName()))
            {
                ev = new PropertyChangeEvent(layoutDelegate,
                                             null, null, null);
                try {
                    containerLayoutChanged(ev);
                }
                catch (PropertyVetoException ex) {} // should not happen
            }
        }
    }

    private static void ensureFakePeerAttached(Component comp) {
        // This method is called for components to be added to a container.
        // It might happen that the component is still in another container
        // (by error) and then when removed from this container before adding
        // to the new one, the peer would be null-ed. Trying to prevent this by
        // removing the component before attaching the fake peer. (For bug 115431.)
        if (comp != null && comp.getParent() != null) {
            comp.getParent().remove(comp);
        }
        FakePeerSupport.attachFakePeer(comp);
        if (comp instanceof Container)
            FakePeerSupport.attachFakePeerRecursively((Container)comp);
    }
}
