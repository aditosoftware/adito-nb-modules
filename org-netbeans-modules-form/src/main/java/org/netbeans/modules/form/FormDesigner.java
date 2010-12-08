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
import java.util.*;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import javax.swing.*;
import javax.swing.border.*;
import java.beans.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdesktop.layout.Baseline;
import org.jdesktop.layout.LayoutStyle;

import org.netbeans.core.spi.multiview.*;
import org.netbeans.modules.form.menu.MenuEditLayer;
import org.netbeans.modules.form.palette.PaletteItem;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.actions.FileSystemAction;
import org.openide.util.actions.SystemAction;
import org.openide.windows.TopComponent;
import org.openide.nodes.Node;
import org.openide.util.*;
import org.openide.util.lookup.*;
import org.openide.awt.UndoRedo;
import org.openide.explorer.ExplorerUtils;
import org.openide.ErrorManager;
import org.openide.explorer.ExplorerManager;

import org.netbeans.modules.form.assistant.*;
import org.netbeans.modules.form.wizard.ConnectionWizard;
import org.netbeans.modules.form.layoutsupport.LayoutSupportManager;
import org.netbeans.modules.form.layoutdesign.*;
import org.netbeans.modules.form.layoutdesign.LayoutConstants.PaddingType;
import org.netbeans.modules.form.layoutdesign.support.SwingLayoutBuilder;
import org.netbeans.modules.form.palette.PaletteUtils;
import org.netbeans.modules.form.project.ClassPathUtils;
import org.openide.awt.StatusDisplayer;
import org.openide.filesystems.FileObject;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;


/**
 * This is a TopComponent subclass holding the form designer. It consist of two
 * layers - HandleLayer (responsible for interaction with user) and
 * ComponentLayer (presenting the components, not accessible to the user).
 *
 * FormDesigner
 *  +- AssistantView
 *  +- JScrollPane
 *      +- JLayeredPane
 *          +- HandleLayer
 *          +- ComponentLayer
 *
 * @author Tran Duc Trung, Tomas Pavek, Josef Kozak
 */

public class FormDesigner extends TopComponent implements MultiViewElement
{
    static final String PROP_DESIGNER_SIZE = "designerSize"; // NOI18N

    // UI components composition
    private JLayeredPane layeredPane;
    private ComponentLayer componentLayer;
    private HandleLayer handleLayer;
    private NonVisualTray nonVisualTray;
    private FormToolBar formToolBar;
    
    // in-place editing
    private InPlaceEditLayer textEditLayer;
    private FormProperty editedProperty;
    private InPlaceEditLayer.FinishListener finnishListener;
    
    private MenuEditLayer menuEditLayer;
            
    // metadata
    private FormModel formModel;
    private FormModelListener formModelListener;
    private RADVisualComponent topDesignComponent;

    private FormEditor formEditor;

    // layout visualization and interaction
    private List<RADComponent> selectedComponents = new ArrayList<RADComponent>();
    private List<RADComponent> selectedLayoutComponents = new ArrayList<RADComponent>();
    private VisualReplicator replicator;
    private LayoutDesigner layoutDesigner;
    private List<Action> designerActions;
    private List<Action> resizabilityActions;
    
    private JToggleButton[] resizabilityButtons;
            
    private int designerMode;
    public static final int MODE_SELECT = 0;
    public static final int MODE_CONNECT = 1;
    public static final int MODE_ADD = 2;
    
    private boolean initialized = false;

    private RADComponent connectionSource;
    private RADComponent connectionTarget;

    MultiViewElementCallback multiViewObserver;

    private ExplorerManager explorerManager;
    private FormProxyLookup lookup;
    private boolean settingLookup;

    private AssistantView assistantView;
    private PreferenceChangeListener settingsListener;

    /** The icons for FormDesigner */
    private static String iconURL =
        "org/netbeans/modules/form/resources/formDesigner.gif"; // NOI18N

    private boolean hasPropertyChangeListener = false;

    // ----------
    // constructors and setup

    FormDesigner(FormEditor formEditor) {
        setIcon(ImageUtilities.loadImage(iconURL));
        setLayout(new BorderLayout());

        FormLoaderSettings settings = FormLoaderSettings.getInstance();
        Color backgroundColor = settings.getFormDesignerBackgroundColor();
        Color borderColor = settings.getFormDesignerBorderColor();

        JPanel loadingPanel = new JPanel();
        loadingPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 12 + (settings.getAssistantShown() ? 40 : 0)));
        loadingPanel.setBackground(backgroundColor);
        JLabel loadingLbl = new JLabel(FormUtils.getBundleString("LBL_FormLoading")); // NOI18N
        loadingLbl.setOpaque(true);
        loadingLbl.setPreferredSize(new Dimension(410,310));
        loadingLbl.setHorizontalAlignment(SwingConstants.CENTER);
        loadingPanel.add(loadingLbl);
        loadingLbl.setBorder(new CompoundBorder(new LineBorder(borderColor, 5),
            new EmptyBorder(new Insets(6, 6, 6, 6))));
        add(loadingPanel, BorderLayout.CENTER);

        this.formEditor = formEditor;
        
        if (formEditor != null) { // Issue 67879
            explorerManager = new ExplorerManager();

            // add FormDataObject to lookup so it can be obtained from multiview TopComponent
            ActionMap map = ComponentInspector.getInstance().setupActionMap(getActionMap());
            final FormDataObject formDataObject = formEditor.getFormDataObject();
            lookup = new FormProxyLookup(new Lookup[] {
                ExplorerUtils.createLookup(explorerManager, map),
                PaletteUtils.getPaletteLookup(formDataObject.getPrimaryFile()),
                Lookup.EMPTY // placeholder for data node lookup used when no node selected in the form
            });
            associateLookup(lookup);

            formToolBar = new FormToolBar(this);
        }
        
        setMinimumSize(new Dimension(10, 10));
    }
    
    void initialize() {
        initialized = true;
        removeAll();

        formModel = formEditor.getFormModel();

        componentLayer = new ComponentLayer(formModel);
        handleLayer = new HandleLayer(this);
        nonVisualTray = FormEditor.isNonVisualTrayEnabled() ?
                        new NonVisualTray(formModel) : null;

        JPanel designPanel = new JPanel(new BorderLayout());
        designPanel.add(componentLayer, BorderLayout.CENTER);
        if (nonVisualTray != null) {
            designPanel.add(nonVisualTray, BorderLayout.SOUTH);
        }
        
        layeredPane = new JLayeredPane() {
            // hack: before each paint make sure the dragged components have
            // bounds set out of visible area (as they physically stay in their
            // container and the layout manager may lay them back if some
            // validation occurs)
            @Override
            protected void paintChildren(Graphics g) {
                handleLayer.maskDraggingComponents();
                super.paintChildren(g);
            }
        };
        layeredPane.setLayout(new OverlayLayout(layeredPane));
        layeredPane.add(designPanel, new Integer(1000));
        layeredPane.add(handleLayer, new Integer(1001));

        updateAssistant();
        settingsListener = new PreferenceChangeListener() {
            @Override
            public void preferenceChange(PreferenceChangeEvent evt) {
                if (FormLoaderSettings.PROP_ASSISTANT_SHOWN.equals(evt.getKey())) {
                    updateAssistant();
                }
            }

        };
        FormLoaderSettings.getPreferences().addPreferenceChangeListener(settingsListener);

        JScrollPane scrollPane = new JScrollPane(layeredPane);
        scrollPane.setBorder(null); // disable border, winsys will handle borders itself
        scrollPane.setViewportBorder(null); // disable also GTK L&F viewport border 
        scrollPane.getVerticalScrollBar().setUnitIncrement(5); // Issue 50054
        scrollPane.getHorizontalScrollBar().setUnitIncrement(5);
        add(scrollPane, BorderLayout.CENTER);

        explorerManager.setRootContext(formEditor.getFormRootNode());
        
        if(!hasPropertyChangeListener) {
            addPropertyChangeListener("activatedNodes", new PropertyChangeListener() { // NOI18N
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    try {
                        if (formEditor == null) {
                            // Lazy synchronization of already closed form - issue 129877
                            return;
                        }
                        FormDataObject fdo = formEditor.getFormDataObject();
                        if (!fdo.isValid()) {
                            return; // Issue 130637
                        }
                        Node dataNode = fdo.getNodeDelegate();
                        Node[] nodes = (Node[])evt.getNewValue();
                        List<Node> list = new ArrayList<Node>(nodes.length);
                        for (Node n : nodes) {
                            if (n != null && n != dataNode) {
                                list.add(n);
                            }
                        }
                        switchLookup(list.isEmpty()); // if no form node, select data node (of FormDataObject)
                        explorerManager.setSelectedNodes(list.toArray(new Node[list.size()]));
                    } catch (PropertyVetoException ex) {
                        Logger.getLogger(getClass().getName()).log(Level.INFO, ex.getMessage(), ex);
                    }
                }
            });            
            hasPropertyChangeListener = true;
        }

        if (formModelListener == null)
            formModelListener = new FormListener();
        formModel.addFormModelListener(formModelListener);

        replicator = new VisualReplicator(true, FormUtils.getViewConverters(), 
            FormEditor.getBindingSupport(formModel));

        resetTopDesignComponent(false);
        handleLayer.setViewOnly(formModel.isReadOnly());

        // Beans without layout model doesn't require layout designer
        if (formModel.getLayoutModel() != null) {
            layoutDesigner = new LayoutDesigner(formModel.getLayoutModel(),
                                            new LayoutMapper());
        }
        
        updateWholeDesigner();
        
        // not very nice hack - it's better FormEditorSupport has its
        // listener registered after FormDesigner
        formEditor.reinstallListener();

        if (formEditor.getFormDesigner() == null) {
            // 70940: Make sure some form designer is registered
            formEditor.setFormDesigner(this);
        }
        
        //force the menu edit layer to be created
        getMenuEditLayer();

        // vlv: print
        designPanel.putClientProperty("print.printable", Boolean.TRUE); // NOI18N

        // Issue 137741
        RADComponent topMetacomp = formModel.getTopRADComponent();
        if (topMetacomp == null) {
            ComponentInspector inspector = ComponentInspector.getInstance();
            inspector.focusForm(formEditor);
            try {
                inspector.setSelectedNodes(new Node[] {formEditor.getFormRootNode()}, formEditor);
            } catch (PropertyVetoException pvex) {}
        } else {
            setSelectedComponent(topMetacomp);
        }
    }

    void reset(FormEditor formEditor) {
        if (menuEditLayer != null) {
            menuEditLayer.hideMenuLayer();
            menuEditLayer = null;
        }
                
        if (initialized) {
            clearSelection();
            explorerManager.setRootContext(new AbstractNode(Children.LEAF));
        }
        initialized = false;

        if (formEditor == null && preLoadTask != null) {
            // designer closed before form loading started
            preLoadTask = null;
            StatusDisplayer.getDefault().setStatusText(""); // NOI18N
        }

        removeAll();
                
        componentLayer = null;
        handleLayer = null;
        nonVisualTray = null;        
        layeredPane = null;        
        if(textEditLayer!=null) {            
            if (textEditLayer.isVisible()){
                textEditLayer.finishEditing(false);                
            }
            textEditLayer.removeFinishListener(getFinnishListener());
            textEditLayer=null;               
        }
        
        if (formModel != null) {
            if (formModelListener != null) {
                formModel.removeFormModelListener(formModelListener);                
            }                
            if (settingsListener != null) {
                FormLoaderSettings.getPreferences().removePreferenceChangeListener(settingsListener);
            }
            topDesignComponent = null;
            formModel = null;
        }
        
        replicator = null;
        layoutDesigner = null;
        
        connectionSource = null;
        connectionTarget = null;        
        this.formEditor = formEditor;
    }

    private void switchLookup(boolean includeDataNodeLookup) {
        if (settingLookup) {
            return;
        }
        Lookup[] lookups = lookup.getSubLookups();
        int index = lookups.length - 1;
        boolean dataNodeLookup = (lookups[index] != Lookup.EMPTY);
        if (includeDataNodeLookup != dataNodeLookup) {
            lookups[index] = includeDataNodeLookup
                    ? formEditor.getFormDataObject().getNodeDelegate().getLookup()
                    : Lookup.EMPTY;
            try {
                settingLookup = true; // avoid re-entrant call
                lookup.setSubLookups(lookups);
            } finally {
                settingLookup = false;
            }
        }
    }

    private void updateAssistant() {
        if (FormLoaderSettings.getInstance().getAssistantShown()) {
            AssistantModel assistant = FormEditor.getAssistantModel(formModel);
            assistantView = new AssistantView(assistant);
            assistant.setContext("select"); // NOI18N
            add(assistantView, BorderLayout.NORTH);
        } else {
            if (assistantView != null) {
                remove(assistantView);
                assistantView = null;
            }
        }
        revalidate();
    }


    // ------
    // important getters

    public FormModel getFormModel() {
        return formModel;
    }

    public HandleLayer getHandleLayer() {
        return handleLayer;
    }

    ComponentLayer getComponentLayer() {
        return componentLayer;
    }
    
    NonVisualTray getNonVisualTray() {
        return nonVisualTray;
    }

    FormToolBar getFormToolBar() {
        return formToolBar;
    }

    public LayoutDesigner getLayoutDesigner() {
        return layoutDesigner;
    }
    
    public FormEditor getFormEditor() {
        return formEditor;
    }
    
    @Override
    public javax.swing.Action[] getActions() {
        Action[] actions = super.getActions();
        SystemAction fsAction = SystemAction.get(FileSystemAction.class);
        if (!Arrays.asList(actions).contains(fsAction)) {
            Action[] newActions = new Action[actions.length+1];
            System.arraycopy(actions, 0, newActions, 0, actions.length);
            newActions[actions.length] = fsAction;
            actions = newActions;
        }
        return actions;
    }

    // ------------
    // designer content

    public Object getComponent(RADComponent metacomp) {
        return replicator.getClonedComponent(metacomp.getId());
    }

    public Object getComponent(String componentId) {
        return replicator.getClonedComponent(componentId);
    }

    public RADComponent getMetaComponent(Object comp) {
        String id = replicator.getClonedComponentId(comp);
        return id != null ? formModel.getMetaComponent(id) : null;
    }
    
//    public RADComponent getMetaComponent(String componentId) {
//        return formModel.getMetaComponent(componentId);
//    }

    public RADVisualComponent getTopDesignComponent() {
        return topDesignComponent;
    }

    boolean isTopRADComponent() {
        RADComponent topMetaComp = formModel.getTopRADComponent();
        return topMetaComp != null && topMetaComp == topDesignComponent;
    }
    
    public void setTopDesignComponent(RADVisualComponent component,
                                      boolean update) {
        
        highlightTopDesignComponentName(false);
        // TODO need to remove bindings of the current cloned view (or clone bound components as well)
        topDesignComponent = component;
        highlightTopDesignComponentName(!isTopRADComponent());        
        
        FormDataObject formDO = formEditor.getFormDataObject();
        if(formDO!=null) {
            formDO.getFormEditorSupport().updateMVTCDisplayName();            
        }        
        if (update) {
            setSelectedComponent(topDesignComponent);
            updateWholeDesigner();
        }
    }

    private void highlightTopDesignComponentName(boolean bl) {
        if(topDesignComponent!=null) {
            RADComponentNode node = topDesignComponent.getNodeReference();
            if(node!=null) {
                node.highlightDisplayName(bl);
            }
        }        
    }
    
    public void resetTopDesignComponent(boolean update) {
        RADComponent top = formModel.getTopRADComponent();
        setTopDesignComponent(top instanceof RADVisualComponent ? (RADVisualComponent)top : null,
                              update);
    }

    /** Tests whether top designed container is some parent of given component
     * (whether the component is in the tree under top designed container).
     * 
     * @param metacomp component.
     * @return <code>true</code> if the component is in designer,
     * returns <code>false</code> otherwise.
     */
    public boolean isInDesigner(RADVisualComponent metacomp) {
        Object comp = replicator.getClonedComponent(metacomp);
        return comp instanceof Component ? componentLayer.isAncestorOf((Component)comp) : false;
    }

    void updateWholeDesigner() {
        if (formModelListener != null)
            formModelListener.formChanged(null);
    }

    private void updateComponentLayer(final boolean fireChange) {
        if (formModel == null) { // the form can be closed just after opened, before this gets called (#70439)
            return;
        }
        if (getLayoutDesigner() == null) {
            return;
        }

        // Ensure that the components are laid out
        componentLayer.revalidate(); // Add componentLayer among components to validate
        RepaintManager.currentManager(componentLayer).validateInvalidComponents();

        LayoutModel layoutModel = formModel.getLayoutModel();
        if (getLayoutDesigner().updateCurrentState() && fireChange) {
            formModel.fireFormChanged(true); // hack: to regenerate code once again
        }

        layoutModel.endUndoableEdit();
        updateResizabilityActions();
        componentLayer.repaint();
    }

    // updates layout of a container in designer to match current model - used
    // by HandleLayer when canceling component dragging
    void updateContainerLayout(RADVisualContainer metacont) {
        replicator.updateContainerLayout(metacont);
        componentLayer.revalidate();
        componentLayer.repaint();
    }

    public static Container createFormView(final RADComponent metacomp, final FormLAF.PreviewInfo previewInfo)
        throws Exception
    {
        Container result = null;
        FormModel formModel = metacomp.getFormModel();
        FileObject formFile = FormEditor.getFormDataObject(formModel).getFormFile();
        final ClassLoader classLoader = ClassPathUtils.getProjectClassLoader(formFile);
        Locale defaultLocale = switchToDesignLocale(formModel);
        try {
            FormLAF.setUsePreviewDefaults(classLoader, previewInfo);
            result = (Container) FormLAF.executeWithLookAndFeel(formModel,
            new Mutex.ExceptionAction () {
                @Override
                public Object run() throws Exception {
                    VisualReplicator r = new VisualReplicator(false, FormUtils.getViewConverters(), null);
                    r.setTopMetaComponent(metacomp);
                    Object container = r.createClone();
                    if (container instanceof RootPaneContainer) {
                        JRootPane rootPane = ((RootPaneContainer)container).getRootPane();
                        JLayeredPane newPane = new JLayeredPane() {
                            @Override
                            public void paint(Graphics g) {
                                try {
                                    FormLAF.setUsePreviewDefaults(classLoader, previewInfo);
                                    super.paint(g);
                                } finally {
                                    FormLAF.setUsePreviewDefaults(null, null);
                                }
                            }
                        };
                        // Copy components from the original layered pane into our one
                        JLayeredPane oldPane = rootPane.getLayeredPane();
                        Component[] comps = oldPane.getComponents();
                        for (int i=0; i<comps.length; i++) {
                            newPane.add(comps[i], Integer.valueOf(oldPane.getLayer(comps[i])));
                        }
                        // Use our layered pane that knows about LAF switching
                        rootPane.setLayeredPane(newPane);
                        // Make the glass pane visible to force repaint of the whole layered pane
                        rootPane.getGlassPane().setVisible(true);
                        // Mark it as design preview
                        rootPane.putClientProperty("designPreview", Boolean.TRUE); // NOI18N
                    } // else AWT Frame - we don't care that the L&F of the Swing
                    // components may not look good - it is a strange use case
                    return container;
                }
            }
        
        );
        } finally {
            FormLAF.setUsePreviewDefaults(null, null);
            if (defaultLocale != null)
                Locale.setDefault(defaultLocale);
        }
        return result;
    }

    private static Locale switchToDesignLocale(FormModel formModel) {
        Locale defaultLocale = null;
        String locale = FormEditor.getResourceSupport(formModel).getDesignLocale();
        if (locale != null && !locale.equals("")) { // NOI18N
            defaultLocale = Locale.getDefault();

            String[] parts = locale.split("_"); // NOI18N
            int i = 0;
            if ("".equals(parts[i])) // NOI18N
                i++;
            String language = i < parts.length ? parts[i++] : null;
            String country = i < parts.length ? parts[i++] : ""; // NOI18N
            String variant = i < parts.length ? parts[i] : ""; // NOI18N
            if (language != null)
                Locale.setDefault(new Locale(language, country, variant));
        }
        return defaultLocale;
    }

    Component getTopDesignComponentView() {
        return topDesignComponent != null
                ? (Component) replicator.getClonedComponent(topDesignComponent)
                : null;
    }

    // NOTE: does not create a new Point instance
    Point pointFromComponentToHandleLayer(Point p, Component sourceComp) {
        Component commonParent = layeredPane;
        Component comp = sourceComp;
        while (comp != commonParent) {
            p.x += comp.getX();
            p.y += comp.getY();
            comp = comp.getParent();
        }
        comp = handleLayer;
        while (comp != commonParent) {
            p.x -= comp.getX();
            p.y -= comp.getY();
            comp = comp.getParent();
        }
        return p;
    }

    // NOTE: does not create a new Point instance
    Point pointFromHandleToComponentLayer(Point p, Component targetComp) {
        Component commonParent = layeredPane;
        Component comp = handleLayer;
        while (comp != commonParent) {
            p.x += comp.getX();
            p.y += comp.getY();
            comp = comp.getParent();
        }
        comp = targetComp;
        while (comp != commonParent) {
            p.x -= comp.getX();
            p.y -= comp.getY();
            comp = comp.getParent();
        }
        return p;
    }
    
    boolean isCoordinatesRoot(Component comp) {
        return (layeredPane == comp);
    }

    private Rectangle componentBoundsToTop(Component component) {
        if (component == null)
            return null;

        Component top = getTopDesignComponentView();

        int dx = 0;
        int dy = 0;

        if (component != top) {
            Component comp = component.getParent();
            while (comp != top) {
                if (comp == null) {
                    break;//return null;
                }
                dx += comp.getX();
                dy += comp.getY();
                comp = comp.getParent();
            }
        }
        else {
            dx = -top.getX();
            dy = -top.getY();
        }

        Rectangle bounds = component.getBounds();
        bounds.x += dx;
        bounds.y += dy;

        return bounds;
    }

    // -------
    // designer mode

    void setDesignerMode(int mode) {
        formToolBar.updateDesignerMode(mode);

        if (mode == designerMode || !initialized) {
            return;
        }

        if (mode == MODE_ADD) {
            PaletteItem pitem = PaletteUtils.getSelectedItem();
            if ((pitem != null) && PaletteItem.TYPE_CHOOSE_BEAN.equals(pitem.getExplicitComponentType())
                    && ComponentInspector.getInstance().getFocusedForm() == formEditor) {
                NotifyDescriptor.InputLine desc = new NotifyDescriptor.InputLine(
                    FormUtils.getBundleString("MSG_Choose_Bean"), // NOI18N
                    FormUtils.getBundleString("TITLE_Choose_Bean")); // NOI18N
                DialogDisplayer.getDefault().notify(desc);
                if (NotifyDescriptor.OK_OPTION.equals(desc.getValue())) {
                    pitem.setClassFromCurrentProject(desc.getInputText(),
                            formEditor.getFormDataObject().getPrimaryFile());
                } else {
                    toggleSelectionMode();
                    return;
                }
            }
        }

        designerMode = mode;

        resetConnection();
        if (mode == MODE_CONNECT)
            clearSelection();

        handleLayer.endDragging(null);
        AssistantModel aModel = FormEditor.getAssistantModel(formModel);
        switch (mode) {
            case MODE_CONNECT: aModel.setContext("connectSource"); break; // NOI18N
            case MODE_SELECT: aModel.setContext("select"); break; // NOI18N
        }
    }

    public int getDesignerMode() {
        return designerMode;
    }

    public void toggleSelectionMode() {
        setDesignerMode(MODE_SELECT);
        PaletteUtils.clearPaletteSelection();
    }

    void toggleConnectionMode() {
        setDesignerMode(MODE_CONNECT);
        PaletteUtils.clearPaletteSelection();
    }

    void toggleAddMode() {
        setDesignerMode(MODE_ADD);
        PaletteUtils.clearPaletteSelection();
    }

    // -------
    // designer size

    Dimension getDesignerSize() {
        return componentLayer.getDesignerSize();
    }

    void setDesignerSize(Dimension size, Dimension oldSize) {
        if (topDesignComponent instanceof RADVisualFormContainer) {
            ((RADVisualFormContainer)topDesignComponent).setDesignerSize(size);
        }
        else if (topDesignComponent != null) {
            if (topDesignComponent == formModel.getTopRADComponent()) {
                oldSize = (Dimension) topDesignComponent.getAuxValue(PROP_DESIGNER_SIZE);
                topDesignComponent.setAuxValue(PROP_DESIGNER_SIZE, size);
            }
            if (oldSize == null)
                oldSize = getDesignerSize();

            getFormModel().fireSyntheticPropertyChanged(topDesignComponent,
                    FormDesigner.PROP_DESIGNER_SIZE, oldSize, size);
        }
    }

    public void resetDesignerSize() {
        setDesignerSize(null, null);
    }

    void storeDesignerSize(Dimension size) { // without firing model change
        if (topDesignComponent instanceof RADVisualFormContainer)
            ((RADVisualFormContainer)topDesignComponent).setDesignerSizeImpl(size);
        else if (topDesignComponent == formModel.getTopRADComponent()) // root not a visual container
            topDesignComponent.setAuxValue(PROP_DESIGNER_SIZE, size);
    }

    private void setupDesignerSize() {
        Dimension size = null;
        RADVisualFormContainer formCont = topDesignComponent instanceof RADVisualFormContainer ?
                                          (RADVisualFormContainer) topDesignComponent : null;
        if (formCont == null
            || formCont.hasExplicitSize()
            || !RADVisualContainer.isFreeDesignContainer(topDesignComponent))
        {   // try to obtain stored designer size
            if (formCont != null)
                size = formCont.getDesignerSize();
            if (size == null)
                size = (Dimension) topDesignComponent.getAuxValue(PROP_DESIGNER_SIZE);
            if (size == null
                && (!formModel.isFreeDesignDefaultLayout()
                     || topDesignComponent == formModel.getTopRADComponent()))
            {   // use default size if no stored size is available and
                // old layout form or top design comp is root in the form (but not a container)
                size = new Dimension(400, 300);
            }
        }

        Dimension setSize = componentLayer.setDesignerSize(size); // null computes preferred size
        storeDesignerSize(setSize);
    }

    private void checkDesignerSize() {
        if ((formModel.isFreeDesignDefaultLayout()
                || RADVisualContainer.isFreeDesignContainer(topDesignComponent))
            && topDesignComponent instanceof RADVisualComponent
            && (!(topDesignComponent instanceof RADVisualFormContainer)
                || !((RADVisualFormContainer)topDesignComponent).hasExplicitSize()))
        {   // new layout container defining designer size
            // designer size not defined explicitly - check minimum size
            Component topComp = getTopDesignComponentView();
            Component topCont = null;
            if (topDesignComponent instanceof RADVisualContainer) {
                topCont = ((RADVisualContainer)topDesignComponent).getContainerDelegate(topComp);
            }
            if (topCont == null) {
                topCont = topComp;
            }
            // can't rely on minimum size of the container wrap - e.g. menu bar
            // returns wrong min height
            int wDiff = topComp.getWidth() - topCont.getWidth();
            int hDiff = topComp.getHeight() - topCont.getHeight();

            Dimension designerSize = new Dimension(getDesignerSize());
            designerSize.width -= wDiff;
            designerSize.height -= hDiff;
            Dimension minSize = topCont.getMinimumSize();
            boolean corrected = false;
            if (designerSize.width < minSize.width) {
                designerSize.width = minSize.width;
                corrected = true;
            }
            if (designerSize.height < minSize.height) {
                designerSize.height = minSize.height;
                corrected = true;
            }

            if (corrected) {
                designerSize.width += wDiff;
                designerSize.height += hDiff;

                // hack: we need the size correction in the undo/redo
                if (formModel.isCompoundEditInProgress()) {
                    FormModelEvent ev = new FormModelEvent(formModel, FormModelEvent.SYNTHETIC_PROPERTY_CHANGED);
                    ev.setComponentAndContainer(topDesignComponent, null);
                    ev.setProperty(PROP_DESIGNER_SIZE, getDesignerSize(), designerSize);
                    formModel.addUndoableEdit(ev.getUndoableEdit());
                }

                componentLayer.setDesignerSize(designerSize);
                storeDesignerSize(designerSize);
            }
        }
    }

    // ---------
    // components selection

    public java.util.List<RADComponent> getSelectedComponents() {
        return selectedComponents;
    }

    Node[] getSelectedComponentNodes() {
        List<Node> selectedNodes = new ArrayList<Node>(selectedComponents.size());
        for (RADComponent c : selectedComponents) {
            if (c.getNodeReference() != null) { // issue 126192 workaround
                selectedNodes.add(c.getNodeReference());
            }
        }
        return selectedNodes.toArray(new Node[selectedNodes.size()]);
    }
    
    java.util.List<RADComponent> getSelectedLayoutComponents() {
        return selectedLayoutComponents;
    }

    boolean isComponentSelected(RADComponent metacomp) {
        return selectedComponents.contains(metacomp);
    }

    public void setSelectedComponent(RADComponent metacomp) {
        clearSelectionImpl();
        addComponentToSelectionImpl(metacomp);
        repaintSelection();
        updateComponentInspector();
    }

    public void setSelectedComponents(RADComponent[] metacomps) {
        clearSelectionImpl();

        for (int i=0; i < metacomps.length; i++)
            addComponentToSelectionImpl(metacomps[i]);

        repaintSelection();
        updateComponentInspector();
    }

    void setSelectedNode(FormNode node) {
        if (node instanceof RADComponentNode)
            setSelectedComponent(((RADComponentNode)node).getRADComponent());
        else {
            clearSelectionImpl();
            repaintSelection();

            ComponentInspector ci = ComponentInspector.getInstance();
            if (ci.getFocusedForm() != formEditor)
                return;


            Node[] selectedNodes = new Node[] { node };
            try {
                ci.setSelectedNodes(selectedNodes, formEditor);
                // sets also the activated nodes (both for ComponentInspector
                // and FormDesigner)
            }
            catch (java.beans.PropertyVetoException ex) {
                Logger.getLogger(getClass().getName()).log(Level.INFO, ex.getMessage(), ex);
            }
        }
    }

    public void addComponentToSelection(RADComponent metacomp) {
        addComponentToSelectionImpl(metacomp);
        repaintSelection();
        updateComponentInspector();
    }

    void addComponentsToSelection(RADComponent[] metacomps) {
        for (int i=0; i < metacomps.length; i++)
            addComponentToSelectionImpl(metacomps[i]);

        repaintSelection();
        updateComponentInspector();
    }

    void removeComponentFromSelection(RADComponent metacomp) {
        removeComponentFromSelectionImpl(metacomp);
        repaintSelection();
        updateComponentInspector();
    }

    public void clearSelection() {
        clearSelectionImpl();
        repaintSelection();
        updateComponentInspector();
    }

    void addComponentToSelectionImpl(RADComponent metacomp) {
        if (metacomp != null) {
            selectedComponents.add(metacomp);
            RADVisualComponent layoutComponent = componentToLayoutComponent(metacomp);
            if (layoutComponent != null) {
                selectedLayoutComponents.add(layoutComponent);
                ensureComponentIsShown((RADVisualComponent)metacomp);
                selectionChanged();
            }
        }
    }
    
    RADVisualComponent componentToLayoutComponent(RADComponent metacomp) {
        if (metacomp instanceof RADVisualComponent) {
            RADVisualComponent visualComp = (RADVisualComponent) metacomp;
            if (!visualComp.isMenuComponent()) {
                RADVisualContainer metacont = visualComp.getParentContainer();
                if ((metacont != null) && JScrollPane.class.isAssignableFrom(metacont.getBeanInstance().getClass())
                     && isInDesigner(metacont))
                {   // substitute with scroll pane...
                    return metacont;
                }
                // otherwise just check if it is visible in the designer
                return isInDesigner(visualComp) ? visualComp : null;
            }
        }
        return null;
    }

    void removeComponentFromSelectionImpl(RADComponent metacomp) {
        selectedComponents.remove(metacomp);
        selectedLayoutComponents.remove(metacomp);
        selectionChanged();
    }

    void clearSelectionImpl() {
        selectedComponents.clear();
        selectedLayoutComponents.clear();
        selectionChanged();
    }

    void selectionChanged() {
        if (formModel == null) {
            // Some (redundant) postponed update => ignore
            // See, for example, issue 153953 - the formDesigner is reset
            // during refactoring. The selection is cleared, but the corresponding
            // event arrives after formModel is cleared and before it is
            // initialized again
            return;
        }
        updateDesignerActions();
        updateResizabilityActions();
        updateAssistantContext();
    }

    void repaintSelection() {
        if (handleLayer != null) { // Issue 174373
            handleLayer.repaint();
        }
    }

    private void updateDesignerActions() {
        Collection selectedIds = selectedLayoutComponentIds();
        boolean enabled = (layoutDesigner == null) ? false : layoutDesigner.canAlign(selectedIds);
        Iterator iter = getDesignerActions(true).iterator();
        while (iter.hasNext()) {
            Action action = (Action)iter.next();
            action.setEnabled(enabled);
        }
    }

    void setResizabilityButtons(JToggleButton[] buttons) {
        this.resizabilityButtons = buttons;
    }
    
    public JToggleButton[] getResizabilityButtons() {
        return resizabilityButtons;
    }

    public void updateResizabilityActions() {
        Collection componentIds = componentIds();
        Action[] actions = getResizabilityActions().toArray(new Action[2]);

        RADComponent top = getTopDesignComponent();
        if (top == null || componentIds.contains(top.getId())) {
            for (Action a : actions) {
                a.setEnabled(false);
            }
            return;
        }

        LayoutModel layoutModel = getFormModel().getLayoutModel();
        LayoutDesigner layoutDesigner = getLayoutDesigner();
        Iterator iter = componentIds.iterator();
        boolean resizable[] = new boolean[2];
        boolean nonResizable[] = new boolean[2];
        while (iter.hasNext()) {
            String id = (String)iter.next();
            LayoutComponent comp = layoutModel.getLayoutComponent(id);
            for (int i=0; i<2; i++) {
                if (layoutDesigner.isComponentResizing(comp,
                        (i == 0) ? LayoutConstants.HORIZONTAL : LayoutConstants.VERTICAL)) {
                    resizable[i] = true;
                } else {
                    nonResizable[i] = true;
                }
            }
        }
        for (int i=0; i<2; i++) {
            boolean match;
            boolean miss;
            match = resizable[i];
            miss = nonResizable[i];
            getResizabilityButtons()[i].setSelected(!miss && match);
            actions[i].setEnabled(match || miss);
//                getResizabilityButtons()[i].setPaintDisabledIcon(match && miss);
        }
    }

    private void updateAssistantContext() {
        String context = null;
        String additionalCtx = null;
        List<RADComponent> selComps = getSelectedComponents();
        int selCount = selComps.size();
        if (selCount > 0) {
            RADComponent metacomp = selComps.get(0);
            if (layoutDesigner != null && layoutDesigner.isUnplacedComponent(metacomp.getId())) {
                if (selCount > 1) {
                    List<String> ids = new ArrayList<String>(selCount);
                    for (RADComponent c : selComps) {
                        ids.add(c.getId());
                    }
                    if (layoutDesigner.getDraggableComponents(ids).size() == selCount) {
                        // all selected components are "unplaced" in the same container
                        context = "unplacedComponents1"; // NOI18N
                        additionalCtx = "unplacedComponents2"; // NOI18N
                    }
                } else {
                    context = "unplacedComponent1"; // NOI18N
                    additionalCtx = "unplacedComponent2"; // NOI18N
                }
            }
            if (selCount == 1 && context == null) {
                Object bean = metacomp.getBeanInstance();
                if (bean instanceof JTabbedPane) {
                    JTabbedPane pane = (JTabbedPane)bean;
                    int count = pane.getTabCount();
                    switch (count) {
                        case 0: context = "tabbedPaneEmpty"; break; // NOI18N
                        case 1: context = "tabbedPaneOne"; break; // NOI18N
                        default: context = "tabbedPane"; break; // NOI18N
                    }
                } else if (bean instanceof JRadioButton) {
                    Node.Property property = metacomp.getPropertyByName("buttonGroup"); // NOI18N
                    try {
                        if ((property != null) && (property.getValue() == null)) {
                            context = "buttonGroup"; // NOI18N
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(getClass().getName()).log(Level.INFO, ex.getMessage(), ex);
                    }        
                } else if ((bean instanceof JPanel) && (getTopDesignComponent() != metacomp) && (Math.random() < 0.2)) {
                    context = "designThisContainer"; // NOI18N
                } else if ((bean instanceof JComboBox) && (Math.random() < 0.4)) {
                    context = "comboBoxModel"; // NOI18N
                } else if ((bean instanceof JList) && (Math.random() < 0.4)) {
                    context = "listModel"; // NOI18N
                } else if ((bean instanceof JTable) && (Math.random() < 0.4)) {
                    context = "tableModel"; // NOI18N
                } else if (bean instanceof JScrollPane) {
                    JScrollPane scrollPane = (JScrollPane)bean;
                    if ((scrollPane.getViewport() != null)
                            && (scrollPane.getViewport().getView() == null)) {
                        context = "scrollPaneEmpty"; // NOI18N
                    } else if (Math.random() < 0.5) {
                        context = "scrollPane"; // NOI18N
                    }
                }
            }
        }
        if (context == null) {
            context = "select"; // NOI18N
        }
        FormEditor.getAssistantModel(formModel).setContext(context, additionalCtx);
    }

    /** Finds out what component follows after currently selected component
     * when TAB (forward true) or Shift+TAB (forward false) is pressed. 
     * @return the next or previous component for selection
     */
    RADComponent getNextVisualComponent(boolean forward) {
        RADComponent currentComp = null;
        int n = selectedComponents.size();
        if (n > 0) {
            if (n > 1)
                return null;
            RADComponent sel = selectedComponents.get(0);
            if (sel instanceof RADVisualComponent) {
                currentComp = sel;
            } else {
                return null;
            }
        }

        return getNextVisualComponent(currentComp, forward);
    }

    /** @return the next or prevoius component to component comp
     */
    RADComponent getNextVisualComponent(RADComponent comp, boolean forward) {
        if (comp == null)
            return topDesignComponent;
        if (getComponent(comp) == null)
            return null;

        RADVisualContainer cont;
        RADComponent[] subComps;

        if (forward) {
            // try the first sub-component
            subComps = getVisualSubComponents(comp);
            if (subComps.length > 0) {
                return subComps[0];
            }

            // try the next component (or the next of the parent then)
            if (comp == topDesignComponent)
                return topDesignComponent;
            cont = (RADVisualContainer)comp.getParentComponent();
            if (cont == null) {
                return null;
            }
            int i = cont.getIndexOf(comp);
            while (i >= 0) {
                subComps = cont.getSubComponents();
                if (i+1 < subComps.length)
                    return subComps[i+1];

                if (cont == topDesignComponent)
                    break;
                comp = cont; // one level up
                cont = (RADVisualContainer)comp.getParentComponent();
                if (cont == null)
                    return null; // should not happen
                i = cont.getIndexOf(comp);
            }

            return topDesignComponent;
        }
        else { // backward
            // take the previuos component
            if (comp != topDesignComponent) {
                cont = (RADVisualContainer)comp.getParentComponent();
                if (cont == null) {
                    return null;
                }
                int i = cont.getIndexOf(comp);
                if (i >= 0) { // should be always true
                    if (i == 0) return cont; // the opposite to the 1st forward step

                    subComps = cont.getSubComponents();
                    comp = subComps[i-1];
                }
                else comp = topDesignComponent;
            }

            // find the last subcomponent of it
            do {
                subComps = getVisualSubComponents(comp);
                if (subComps.length > 0) {
                    comp = subComps[subComps.length-1];
                    continue;
                } else {
                    break;
                }
            }
            while (true);
            return comp;
        }
    }

    private RADComponent[] getVisualSubComponents(RADComponent metacomp) {
        return metacomp instanceof RADVisualContainer ?
            ((RADVisualContainer)metacomp).getSubComponents() : new RADComponent[0];
        // TBD components set as properties
    }

    /**
     * Aligns selected components in the specified direction.
     *
     * @param closed determines if closed group should be created.
     * @param dimension dimension to align in.
     * @param alignment requested alignment.
     */
    void align(boolean closed, int dimension, int alignment) {
        // Check that the action is enabled
        Action action = null;
        Iterator iter = getDesignerActions(true).iterator();
        while (iter.hasNext()) {
            Action candidate = (Action)iter.next();
            if (candidate instanceof AlignAction) {
                AlignAction alignCandidate = (AlignAction)candidate;
                if ((alignCandidate.getAlignment() == alignment) && (alignCandidate.getDimension() == dimension)) {
                    action = alignCandidate;
                    break;
                }
            }
        }
        if ((action == null) || (!action.isEnabled())) {
            return;
        }
        Collection selectedIds = selectedLayoutComponentIds();
        RADComponent parent = commonParent(selectedIds);
        LayoutModel layoutModel = formModel.getLayoutModel();
        Object layoutUndoMark = layoutModel.getChangeMark();
        javax.swing.undo.UndoableEdit ue = layoutModel.getUndoableEdit();
        boolean autoUndo = true;
        try {
            getLayoutDesigner().align(selectedIds, closed, dimension, alignment);
            autoUndo = false;
        } finally {
            formModel.fireContainerLayoutChanged((RADVisualContainer)parent, null, null, null);
            if (!layoutUndoMark.equals(layoutModel.getChangeMark())) {
                formModel.addUndoableEdit(ue);
            }
            if (autoUndo) {
                formModel.forceUndoOfCompoundEdit();
            }
        }
    }
    
    /**
     * Returns designer actions (they will be displayed in toolbar).
     *
     * @param forToolbar determines whether the method should return
     * all designer actions or just the subset for the form toolbar.
     * @return <code>Collection</code> of <code>Action</code> objects.
     */
    public Collection<Action> getDesignerActions(boolean forToolbar) {
        if (designerActions == null) {
            designerActions = new LinkedList<Action>();
            // Grouping actions
            designerActions.add(new AlignAction(LayoutConstants.HORIZONTAL, LayoutConstants.LEADING, true));
            designerActions.add(new AlignAction(LayoutConstants.HORIZONTAL, LayoutConstants.TRAILING, true));
            designerActions.add(new AlignAction(LayoutConstants.HORIZONTAL, LayoutConstants.CENTER, true));
            designerActions.add(new AlignAction(LayoutConstants.VERTICAL, LayoutConstants.LEADING, true));
            designerActions.add(new AlignAction(LayoutConstants.VERTICAL, LayoutConstants.TRAILING, true));
            designerActions.add(new AlignAction(LayoutConstants.VERTICAL, LayoutConstants.CENTER, true));
            // Align actions
            designerActions.add(new AlignAction(LayoutConstants.HORIZONTAL, LayoutConstants.LEADING, false));
            designerActions.add(new AlignAction(LayoutConstants.HORIZONTAL, LayoutConstants.TRAILING, false));
            designerActions.add(new AlignAction(LayoutConstants.VERTICAL, LayoutConstants.LEADING, false));
            designerActions.add(new AlignAction(LayoutConstants.VERTICAL, LayoutConstants.TRAILING, false));
        }
        return forToolbar ? designerActions.subList(0, 6) : designerActions;
    }

    public Collection<Action> getResizabilityActions() {
        if (resizabilityActions == null) {
            resizabilityActions = new LinkedList<Action>();
            resizabilityActions.add(new ResizabilityAction(LayoutConstants.HORIZONTAL));
            resizabilityActions.add(new ResizabilityAction(LayoutConstants.VERTICAL));
        }
        return resizabilityActions;
    }
    
    /**
     * Returns collection of ids of the selected layout components.
     *
     * @return <code>Collection</code> of <code>String</code> objects.
     */
    Collection<String> selectedLayoutComponentIds() {
        Iterator metacomps = getSelectedLayoutComponents().iterator();
        Collection<String> selectedIds = new LinkedList<String>();
        while (metacomps.hasNext()) {
            RADComponent metacomp = (RADComponent)metacomps.next();
            selectedIds.add(metacomp.getId());
        }
        return selectedIds;
    }
    
    /**
     * Checks whether the given components are in the same containter.
     *
     * @param compIds <code>Collection</code> of component IDs.
     * @return common container parent or <code>null</code>
     * if the components are not from the same container.
     */
    private RADComponent commonParent(Collection compIds) {
        RADComponent parent = null;
        Iterator iter = compIds.iterator();
        FormModel formModel = getFormModel();
        while (iter.hasNext()) {
            String compId = (String)iter.next();
            RADComponent metacomp = formModel.getMetaComponent(compId);
            RADComponent metacont = metacomp.getParentComponent();
            if (parent == null) {
                parent = metacont;
            }
            if ((metacont == null) || (parent != metacont)) {
                return null;
            }
        }
        return parent;
    }

    // ---------
    // visibility update

    // synchronizes ComponentInspector with selection in FormDesigner
    // [there is a hardcoded relationship between these two views]
    void updateComponentInspector() {
        ComponentInspector ci = ComponentInspector.getInstance();
        if (ci.getFocusedForm() != formEditor)
            return;

        Node[] selectedNodes = getSelectedComponentNodes();
        try {
            setActivatedNodes(selectedNodes); // Issue 62356
            ci.setSelectedNodes(selectedNodes, formEditor);
            // sets also the activated nodes (both for ComponentInspector
            // and FormDesigner)
        }
        catch (java.beans.PropertyVetoException ex) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, ex.getMessage(), ex);
        }
    }

    void updateVisualSettings() {
        componentLayer.updateVisualSettings();
        if (nonVisualTray != null) {
            nonVisualTray.updateVisualSettings();
        }
        layeredPane.revalidate();
        layeredPane.repaint(); // repaints both HanleLayer and ComponentLayer
    }

    private void ensureComponentIsShown(RADVisualComponent metacomp) {
        Component comp = (Component) getComponent(metacomp);
        if (comp == null)
            return; // component is not in the visualized tree

//        if (comp == null) { // visual component doesn't exist yet
//            if (metacont != null)
//                metacont.getLayoutSupport().selectComponent(
//                               metacont.getIndexOf(metacomp));
//            return;
//        }

        if (comp.isShowing())
            return; // component is showing
        if (!isInDesigner(metacomp))
            return; // component is not in designer

        Component topComp = (Component) getComponent(topDesignComponent);
        if (topComp == null || !topComp.isShowing())
            return; // designer is not showing

        RADVisualContainer metacont = metacomp.getParentContainer();
        RADVisualComponent child = metacomp;

        while (metacont != null) {
            Container cont = (Container) getComponent(metacont);

            LayoutSupportManager laysup = metacont.getLayoutSupport();
            if (laysup != null) {
                Container contDelegate = metacont.getContainerDelegate(cont);
                laysup.selectComponent(child.getComponentIndex());
                laysup.arrangeContainer(cont, contDelegate);
            }

            if (metacont == topDesignComponent || cont.isShowing())
                break;

            child = metacont;
            metacont = metacont.getParentContainer();
        }
    }

    // --------------
    // bean connection

    void connectBean(RADComponent metacomp, boolean showDialog) {
        if (connectionSource == null) {
            connectionSource = metacomp;
            FormEditor.getAssistantModel(formModel).setContext("connectTarget"); // NOI18N
            handleLayer.repaint();
        }
        else {
            if (metacomp == connectionSource) {
                if (connectionTarget != null) {
                    resetConnection();
                    toggleSelectionMode();
                }
                return;
            }
            connectionTarget = metacomp;
            handleLayer.repaint();
            if (showDialog) {
                if (connectionTarget != null)  {
                    FormEditor.getAssistantModel(formModel).setContext("connectWizard"); // NOI18N
                    createConnection(connectionSource, connectionTarget);
                }
//                resetConnection();
                toggleSelectionMode();
            }
        }
    }

    public RADComponent getConnectionSource() {
        return connectionSource;
    }

    public RADComponent getConnectionTarget() {
        return connectionTarget;
    }

    public void resetConnection() {
        if (connectionSource != null || connectionTarget != null) {
            connectionSource = null;
            connectionTarget = null;
            handleLayer.repaint();
        }
    }

    private void createConnection(RADComponent source, RADComponent target) {
        ConnectionWizard cw = new ConnectionWizard(formModel, source,target);

        if (cw.show()) {
            final Event event = cw.getSelectedEvent();
            final String eventName = cw.getEventName();
            String bodyText = cw.getGeneratedCode();

            formModel.getFormEvents().attachEvent(event, eventName, bodyText);

            // hack: after all updates, switch to editor
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    formModel.getFormEvents().attachEvent(event, eventName, null);
                }
            });
        }
    }

    // -----------------
    // in-place editing

    public void startInPlaceEditing(RADComponent metacomp) {
        
        if (formModel.isReadOnly())
            return;
        if (textEditLayer != null && textEditLayer.isVisible())
            return;
        if (!isEditableInPlace(metacomp)) // check for sure
            return;

        Component comp = (Component) getComponent(metacomp);
        if (comp == null) { // component is not visible
            notifyCannotEditInPlace();
            return;
        }

        FormProperty property = null;
        if (JTabbedPane.class.isAssignableFrom(metacomp.getBeanClass())) {
            JTabbedPane tabbedPane = (JTabbedPane)comp;
            int index = tabbedPane.getSelectedIndex();
            RADVisualContainer metacont = (RADVisualContainer)metacomp;
            RADVisualComponent tabComp = metacont.getSubComponent(index);
            Node.Property[] props = tabComp.getConstraintsProperties();
            for (int i=0; i<props.length; i++) {
                if (props[i].getName().equals("TabConstraints.tabTitle")) { // NOI18N
                    if (props[i] instanceof FormProperty) {
                        property = (FormProperty)props[i];
                    } else {
                        return;
                    }
                }
            }
            if (property == null) return;
        } else {
            property = metacomp.getBeanProperty("text"); // NOI18N
            if (property == null)
                return; // should not happen
        }

        String editText = null;
        try {
            Object text = property.getRealValue();
            if (!(text instanceof String)) text = ""; // or return?
            editText = (String) text;
        }
        catch (Exception ex) { // should not happen
            Logger.getLogger(getClass().getName()).log(Level.INFO, ex.getMessage(), ex);
            return;
        }

        editedProperty = property;

        getInPlaceEditLayer();
        try {
            textEditLayer.setEditedComponent(comp, editText);
        }
        catch (IllegalArgumentException ex) {
            notifyCannotEditInPlace();
            return;
        }

        textEditLayer.setVisible(true);
        handleLayer.setVisible(false);
        textEditLayer.requestFocus();
    }

    private InPlaceEditLayer.FinishListener getFinnishListener() {
        if(finnishListener==null) {
           finnishListener =  new InPlaceEditLayer.FinishListener() {
                @Override
                public void editingFinished(boolean textChanged) {
                    finishInPlaceEditing(textEditLayer.isTextChanged());
                }
            };
        }
        return finnishListener;
    }
        
    
        
    private void finishInPlaceEditing(boolean applyChanges) {
        if (applyChanges) {
            try {       
        Object value = editedProperty.getValue();
        if(value instanceof String) {
            editedProperty.setValue(textEditLayer.getEditedText());         
        } else {    
            PropertyEditor prEd = editedProperty.findDefaultEditor();
            editedProperty.setValue(new FormProperty.ValueWithEditor(textEditLayer.getEditedText(), prEd));             
        }                        
        } catch (Exception ex) { // should not happen
                Logger.getLogger(getClass().getName()).log(Level.INFO, ex.getMessage(), ex);
            }
        }
    if (handleLayer != null) {
            textEditLayer.setVisible(false);
            handleLayer.setVisible(true);
            handleLayer.requestFocus();
        }
        editedProperty = null;
    }

    public boolean isEditableInPlace(RADComponent metacomp) {
        if (metacomp == null) {
            return false;
        }
        Object comp = getComponent(metacomp);
        if (!(comp instanceof Component)) {
            return false;
        }

        // don't allow in-place editing if there's some AWT parent (it may
        // cause problems with fake peers on some platforms)
        RADComponent parent = metacomp.getParentComponent();
        while (parent != null) {
            if (!JComponent.class.isAssignableFrom(parent.getBeanClass())
                && !RootPaneContainer.class.isAssignableFrom(
                                        parent.getBeanClass()))
                return false;
            parent = parent.getParentComponent();
        }

        Class beanClass = metacomp.getBeanClass();
        return InPlaceEditLayer.supportsEditingFor(beanClass, false)
            && (!JTabbedPane.class.isAssignableFrom(beanClass) || ((JTabbedPane)comp).getTabCount() != 0);
    }

    private void notifyCannotEditInPlace() {
        DialogDisplayer.getDefault().notify(
            new NotifyDescriptor.Message(
                FormUtils.getBundleString("MSG_ComponentNotShown"), // NOI18N
                NotifyDescriptor.WARNING_MESSAGE));
    }

    
    // -----------------
    // menu editing
    
    public void openMenu(RADComponent metacomp) {
        MenuEditLayer menuEditLayer = getMenuEditLayer();
        Component comp = (Component) getComponent(metacomp);
        menuEditLayer.setVisible(true);
        menuEditLayer.openAndShowMenu(metacomp,comp);
    }
    
    // --------
    // methods of TopComponent

    // only MultiViewDescriptor is stored, not MultiViewElement
    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_NEVER;
    }

    @Override
    public HelpCtx getHelpCtx() {
        return new HelpCtx("gui.formeditor"); // NOI18N
    }

    @Override
    public void componentActivated() {
        if (formModel == null)
            return;

        formEditor.setFormDesigner(this);
        ComponentInspector ci = ComponentInspector.getInstance();
        if (ci.getFocusedForm() != formEditor) {
            ci.focusForm(formEditor);
            if (getDesignerMode() == MODE_CONNECT)
                clearSelection();
            else
                updateComponentInspector();
        }

        ci.attachActions();
        if (textEditLayer == null || !textEditLayer.isVisible())
            handleLayer.requestFocus();               
    }

    @Override
    public void componentDeactivated() {
        if (formModel == null)
            return;
        
        if (textEditLayer != null && textEditLayer.isVisible())
            textEditLayer.finishEditing(false);

        ComponentInspector.getInstance().detachActions();
        resetConnection();
    }

    @Override
    public UndoRedo getUndoRedo() {
        UndoRedo ur = formModel != null ? formModel.getUndoRedoManager() : null;
        return ur != null ? ur : super.getUndoRedo();
    }
    
    @Override
    protected String preferredID() {
        return formEditor.getFormDataObject().getName();
    }

    // ------
    // multiview stuff

    @Override
    public JComponent getToolbarRepresentation() {
        return getFormToolBar();
    }

    @Override
    public JComponent getVisualRepresentation() {
        return this;
    }

    @Override
    public void setMultiViewCallback(MultiViewElementCallback callback) {
        multiViewObserver = callback;

        // add FormDesigner as a client property so it can be obtained
        // from multiview TopComponent (it is not sufficient to put
        // it into lookup - only content of the lookup of the active
        // element is accessible)
        callback.getTopComponent().putClientProperty("formDesigner", this); // NOI18N

        // needed for deserialization...
        if (formEditor != null) {
            // this is used (or misused?) to obtain the deserialized multiview
            // topcomponent and set it to FormEditorSupport
            FormDataObject formDO = formEditor.getFormDataObject();
            formDO.getFormEditorSupport().setTopComponent(callback.getTopComponent());
        }
    }

    @Override
    public void requestVisible() {
        if (multiViewObserver != null)
            multiViewObserver.requestVisible();
        else
            super.requestVisible();
    }

    @Override
    public void requestActive() {
        if (multiViewObserver != null)
            multiViewObserver.requestActive();
        else
            super.requestActive();
    }

    @Override
    public void componentClosed() {
        super.componentClosed();
        // Closed FormDesigner is not going to be reused.
        // Clear all references to prevent memory leaks - even if FormDesigner
        // is kept for some reason, make sure FormModel is not held from it.
        reset(null);
    }

    @Override
    public void componentShowing() {
        super.componentShowing();
        if (!formEditor.isFormLoaded()) {
            // Let the TC showing finish, just invoke a task out of EDT to find
            // out form's superclass, then continue form loading in EDT again.
            if (preLoadTask == null) {
                preLoadTask = new PreLoadTask(formEditor.getFormDataObject());
                FormUtils.getRequestProcessor().post(preLoadTask);

                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if (formEditor != null) {
                            StatusDisplayer.getDefault().setStatusText(
                                FormUtils.getFormattedBundleString(
                                    "FMT_PreparingForm", // NOI18N
                                    new Object[] { formEditor.getFormDataObject().getName() }));
                        }
                    }
                });
            }
        } else {
            finishComponentShowing();
        }
    }

    private PreLoadTask preLoadTask;

    private class PreLoadTask implements Runnable {
        private FormDataObject formDataObject;

        PreLoadTask(FormDataObject fdo) {
            formDataObject = fdo;
        }

        @Override
        public void run() {
            long ms = System.currentTimeMillis();
            final GandalfPersistenceManager persistenceManager = getPersistenceManager();
            final String superClassName = (persistenceManager != null) ? computeSuperClass() : null;
            Logger.getLogger(FormEditor.class.getName()).log(Level.FINER, "Opening form time 2: {0}ms", (System.currentTimeMillis()-ms)); // NOI18N

            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if (formEditor != null) {
                        try {
                            if (persistenceManager != null) {
                                // Persistence manager will load the form in the same
                                // EDT round (can't be used for other forms during that time).
                                persistenceManager.setPrefetchedSuperclassName(superClassName);
                                formEditor.setPersistenceManager(persistenceManager);
                            }
                            preLoadTask = null; // set back to null in EDT
                            finishComponentShowing();
                        } finally {
                            if (persistenceManager != null) { // cleanup just for sure
                                persistenceManager.setPrefetchedSuperclassName(null);
                            }
                        }
                    }
                }
            });
        }

        private GandalfPersistenceManager getPersistenceManager() {
            try {
                GandalfPersistenceManager gandalf = (GandalfPersistenceManager) PersistenceManager.getManagers().next();
                if (gandalf.canLoadForm(formDataObject)) {
                    return gandalf;
                }
            } catch (Exception ex) { // failure not interesting here
            }
            return null;
        }

        private String computeSuperClass() {
            try {
                return GandalfPersistenceManager.determineSuperClassName(formDataObject.getPrimaryFile());
            } catch (Exception ex) { // failure not interesting here
            }
            return null;
        }
    }

    private void finishComponentShowing() {
        long ms = System.currentTimeMillis();

        if (!formEditor.isFormLoaded()) {
            formEditor.loadFormDesigner();
            if (!formEditor.isFormLoaded()) { // there was a loading error
                removeAll();
                return;
            }
            // hack: after IDE start, if some form is opened but not active in
            // winsys, we need to select it in ComponentInspector
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if (formEditor != null && formEditor.isFormLoaded()
                            && ComponentInspector.exists()
                            && ComponentInspector.getInstance().getFocusedForm() == null) {
                        ComponentInspector.getInstance().focusForm(formEditor);
                    }
                }
            });
        }
        if (!initialized) {
            initialize();
        }
        FormEditorSupport.checkFormGroupVisibility();

        Logger.getLogger(FormEditor.class.getName()).log(Level.FINER, "Opening form time 3: {0}ms", (System.currentTimeMillis()-ms)); // NOI18N
    }

    @Override
    public void componentHidden() {
        super.componentHidden();
        FormEditorSupport.checkFormGroupVisibility();
    }

    @Override
    public void componentOpened() {
        super.componentOpened();
        if ((formEditor == null) && (multiViewObserver != null)) { // Issue 67879
            multiViewObserver.getTopComponent().close();
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    FormEditorSupport.checkFormGroupVisibility();
                }
            });
        }
    }

    @Override
    public CloseOperationState canCloseElement() {
        // if this is not the last cloned designer, closing is OK
        if (!FormEditorSupport.isLastView(multiViewObserver.getTopComponent()))
            return CloseOperationState.STATE_OK;

        // return a placeholder state - to be sure our CloseHandler is called
        return MultiViewFactory.createUnsafeCloseState(
            "ID_FORM_CLOSING", // dummy ID // NOI18N
            MultiViewFactory.NOOP_CLOSE_ACTION,
            MultiViewFactory.NOOP_CLOSE_ACTION);
    }

    public InPlaceEditLayer getInPlaceEditLayer() {
        if (textEditLayer == null) {
            textEditLayer = new InPlaceEditLayer();
            textEditLayer.setVisible(false);
            textEditLayer.addFinishListener(getFinnishListener());
            layeredPane.add(textEditLayer, new Integer(2001));
        }
        return textEditLayer;
    }
    
    MenuEditLayer getMenuEditLayer() {
        if(menuEditLayer == null) {
            menuEditLayer = new MenuEditLayer(this);
            menuEditLayer.setVisible(false);
            layeredPane.add(menuEditLayer, new Integer(2000));
        }
        return menuEditLayer;
    }
    // -----------
    // innerclasses

    private class LayoutMapper implements VisualMapper {

        // -------

//        public String getTopComponentId() {
//            return getTopDesignComponent().getId();
//        }

        @Override
        public Rectangle getComponentBounds(String componentId) {
            Component visual = getVisualComponent(componentId, true, false);
            Rectangle rect = null;
            if (visual != null) {
                rect = componentBoundsToTop(visual);
            }
            
            if (getLayoutDesigner().logTestCode()) {
                getLayoutDesigner().testCode.add("  compBounds.put(\"" + componentId + "\", new Rectangle(" +  //NOI18N
                                                            rect.x + ", " + rect.y + ", " + rect.width + ", " + rect.height + "));"); //NOI18N
            }
            
            return rect;
        }

        @Override
        public Rectangle getContainerInterior(String componentId) {
            Component visual = getVisualComponent(componentId, true, false);
            if (visual == null)
                return null;

            RADVisualContainer metacont = (RADVisualContainer)
                                          getMetaComponent(componentId);
            Container cont = metacont.getContainerDelegate(visual);

            Rectangle rect = componentBoundsToTop(cont);
            Insets insets = cont.getInsets();
            rect.x += insets.left;
            rect.y += insets.top;
            rect.width -= insets.left + insets.right;
            rect.height -= insets.top + insets.bottom;

            if (getLayoutDesigner().logTestCode()) {
                getLayoutDesigner().testCode.add("  contInterior.put(\"" + componentId + "\", new Rectangle(" +  //NOI18N
                                                        rect.x + ", " + rect.y + ", " + rect.width + ", " + rect.height + "));"); //NOI18N
        }
            
            return rect;
        }

        @Override
        public Dimension getComponentMinimumSize(String componentId) {
            Component visual = getVisualComponent(componentId, false, false);
            Dimension dim = null;
            if (visual != null) {
                dim = visual.getMinimumSize();
            }
            if (getLayoutDesigner().logTestCode()) {
                getLayoutDesigner().testCode.add("  compMinSize.put(\"" + componentId + "\", new Dimension(" +  //NOI18N
                                                            new Double(dim.getWidth()).intValue() + ", " + new Double(dim.getHeight()).intValue() + "));"); //NOI18N
            }            
            return dim;
        }

        @Override
        public Dimension getComponentPreferredSize(String componentId) {
            Component visual = getVisualComponent(componentId, false, false);
            Dimension dim = null;
            if (visual != null) {
                dim = visual.getPreferredSize();
            }
            if (getLayoutDesigner().logTestCode()) {
                getLayoutDesigner().testCode.add("  compPrefSize.put(\"" + componentId + "\", new Dimension(" +  //NOI18N
                                                            new Double(dim.getWidth()).intValue() + ", " + new Double(dim.getHeight()).intValue() + "));"); //NOI18N
            }
            return dim;
        }

        @Override
        public boolean hasExplicitPreferredSize(String componentId) {
            JComponent visual = (JComponent) getVisualComponent(componentId, false, true);
            boolean hasExplPrefSize = false;
            if (visual != null) {
                hasExplPrefSize = visual.isPreferredSizeSet();
            }
            if (getLayoutDesigner().logTestCode()) {
                getLayoutDesigner().testCode.add("  hasExplicitPrefSize.put(\"" + componentId + "\", new Boolean(" + hasExplPrefSize + "));"); //NOI18N
            }
            return hasExplPrefSize;
        }

        @Override
        public int getBaselinePosition(String componentId, int width, int height) {
            int baseLinePos = -1;            
            JComponent comp = (JComponent) getVisualComponent(componentId, true, true);
            // [hack - vertically resizable components cannot be baseline aligned]
            // [this should be either solved or filtered in LayoutDragger according to vertical resizability of the component]
            if (comp != null && (comp instanceof JScrollPane
                                 || comp.getClass().equals(JPanel.class)
                                 || comp instanceof JTabbedPane
                                 || (comp instanceof AbstractButton
                                     && ((AbstractButton)comp).getVerticalTextPosition() != SwingConstants.CENTER)
                                 || (comp instanceof JLabel
                                     && ((JLabel)comp).getVerticalTextPosition() != SwingConstants.CENTER))) {
//                    || comp instanceof JTextArea
//                    || comp instanceof JTree || comp instanceof JTable || comp instanceof JList
                baseLinePos = 0;
            }

            if (baseLinePos == -1) {
                if (comp != null) {
                     baseLinePos = Baseline.getBaseline(comp, width, height);
                } else {
                    baseLinePos = 0;
                }
            }

            if (getLayoutDesigner().logTestCode()) {
                String id = componentId + "-" + width + "-" + height; //NOI18N
                getLayoutDesigner().testCode.add("  baselinePosition.put(\"" + id + "\", new Integer(" + baseLinePos + "));"); //NOI18N
            }

            return baseLinePos;
        }

        @Override
        public int getPreferredPadding(String comp1Id,
                                       String comp2Id,
                                       int dimension,
                                       int comp2Alignment,
                                       PaddingType paddingType)
        {
            String id = null;
        if (getLayoutDesigner().logTestCode()) {
        id = comp1Id + "-" + comp2Id + "-" + dimension + "-" + comp2Alignment + "-" // NOI18N
                     + (paddingType != null ? paddingType.ordinal() : 0);
        }
            
            JComponent comp1 = (JComponent) getVisualComponent(comp1Id, true, true);
            JComponent comp2 = (JComponent) getVisualComponent(comp2Id, true, true);
            if (comp1 == null || comp2 == null) { // not JComponents...
                if (getLayoutDesigner().logTestCode()) {
                    getLayoutDesigner().testCode.add("  prefPadding.put(\"" + id +                  //NOI18N
                "\", new Integer(10)); // comp1Id-comp2Id-dimension-comp2Alignment-paddingType");       //NOI18N
                }
                return 10; // default distance between components (for non-JComponents)
            }

            assert dimension == HORIZONTAL || dimension == VERTICAL;
            assert comp2Alignment == LEADING || comp2Alignment == TRAILING;

            int type = paddingType == PaddingType.INDENT ? LayoutStyle.INDENT :
                (paddingType == PaddingType.RELATED ? LayoutStyle.RELATED : LayoutStyle.UNRELATED);
            int position = 0;
            if (dimension == HORIZONTAL) {
                if (paddingType == PaddingType.INDENT) {
                    position = comp2Alignment == LEADING ?
                               SwingConstants.WEST : SwingConstants.EAST;
                } else {
                    position = comp2Alignment == LEADING ?
                               SwingConstants.EAST : SwingConstants.WEST;
                }
            }
            else {
                position = comp2Alignment == LEADING ?
                           SwingConstants.SOUTH : SwingConstants.NORTH;
            }

            int prefPadding = paddingType != PaddingType.SEPARATE ?
                FormLAF.getDesignerLayoutStyle().getPreferredGap(comp1, comp2, type, position, null)
                : SwingLayoutBuilder.PADDING_SEPARATE_VALUE; // not in LayoutStyle

            if (getLayoutDesigner().logTestCode()) {
                getLayoutDesigner().testCode.add("  prefPadding.put(\"" + id + "\", new Integer(" + prefPadding +   //NOI18N
                ")); // comp1Id-comp2Id-dimension-comp2Alignment-paddingType");             //NOI18N
            }
            
            return prefPadding;
        }

        @Override
        public int getPreferredPaddingInParent(String parentId,
                                               String compId,
                                               int dimension,
                                               int compAlignment)
        {
            String id = null;
        if (getLayoutDesigner().logTestCode()) {
        id = parentId + "-" + compId + "-" + dimension + "-" + compAlignment; //NOI18N
        }
            
            JComponent comp = null;
            Container parent = (Container)getVisualComponent(parentId, true, false);
            if (parent != null) {
                RADVisualContainer metacont = (RADVisualContainer)
                                              getMetaComponent(parentId);
                parent = metacont.getContainerDelegate(parent);
                comp = (JComponent) getVisualComponent(compId, true, true);
            }
            if (comp == null) {
                if (getLayoutDesigner().logTestCode()) {
                    getLayoutDesigner().testCode.add("  prefPaddingInParent.put(\"" + id +      //NOI18N
                "\", new Integer(10)); // parentId-compId-dimension-compAlignment");    //NOI18N
                }
                return 10; // default distance from parent border (for non-JComponents)
            }
            
            assert dimension == HORIZONTAL || dimension == VERTICAL;
            assert compAlignment == LEADING || compAlignment == TRAILING;

            int alignment;

            if (dimension == HORIZONTAL) {
                if (compAlignment == LEADING) {
                    alignment = SwingConstants.WEST;
                }
                else {
                    alignment = SwingConstants.EAST;
                }
            }
            else {
// See issue 182286 for the reason why the following code is commented out
//                if (compAlignment == LEADING) {
//                    alignment = SwingConstants.NORTH;
//                }
//                else {
                    alignment = SwingConstants.SOUTH;
//                }
            }
            int prefPadding = FormLAF.getDesignerLayoutStyle().getContainerGap(comp, alignment, parent);

            if (getLayoutDesigner().logTestCode()) {
                getLayoutDesigner().testCode.add("  prefPaddingInParent.put(\"" + id + "\", new Integer(" +  //NOI18N
            prefPadding + ")); // parentId-compId-dimension-compAlignment");             //NOI18N
            }

            return prefPadding;
        }

        @Override
        public boolean[] getComponentResizability(String compId, boolean[] resizability) {
            resizability[0] = resizability[1] = true;
            // [real resizability spec TBD]
            return resizability;
        }

        @Override
        public void rebuildLayout(String contId) {
            replicator.updateContainerLayout((RADVisualContainer)getMetaComponent(contId));
            replicator.getLayoutBuilder(contId).doLayout();
        }

        @Override
        public void setComponentVisibility(String componentId, boolean visible) {
            Object comp = getComponent(componentId);
            if (comp instanceof Component) {
                ((Component)comp).setVisible(visible);
            }
        }

        // -------

        private RADComponent getMetaComponent(String compId) {
            RADComponent metacomp = formModel.getMetaComponent(compId);
            if (metacomp == null) {
                RADComponent precreated =
                    formModel.getComponentCreator().getPrecreatedMetaComponent();
                if (precreated != null && precreated.getId().equals(compId)) {
                    metacomp = precreated;
                }
            }
            return metacomp;
        }

        private Component getVisualComponent(String compId, boolean needVisible, boolean needJComponent) {
            Object comp = getComponent(compId);
            if (comp == null) {
                RADVisualComponent precreated =
                    formModel.getComponentCreator().getPrecreatedMetaComponent();
                if (precreated != null && precreated.getId().equals(compId)) {
                    comp = precreated.getBeanInstance();
                }
                if (comp == null && !needVisible) {
                    RADComponent metacomp = getMetaComponent(compId);
                    if (metacomp != null) {
                        comp = metacomp.getBeanInstance();
                    }
                }
            }
            Class<?> type = needJComponent ? JComponent.class : Component.class;
            return comp != null && type.isAssignableFrom(comp.getClass()) ?
                   (Component) comp : null;
        }

    }

    // --------

    private Collection<String> componentIds() {
        List<String> componentIds = new LinkedList<String>();
        List selectedComps = getSelectedLayoutComponents();
        LayoutModel layoutModel = getFormModel().getLayoutModel();
        Iterator iter = selectedComps.iterator();
        while (iter.hasNext()) {
            RADVisualComponent visualComp = (RADVisualComponent)iter.next();
            if ((visualComp.getParentContainer() != null)
                && (visualComp.getParentLayoutSupport() == null)
                && layoutModel.getLayoutComponent(visualComp.getId()) != null)
                componentIds.add(visualComp.getId());
        }
        return componentIds;
    }


    // Listener on FormModel - ensures updating of designer view.
    private class FormListener implements FormModelListener, Runnable {

        private FormModelEvent[] events;

        @Override
        public void formChanged(final FormModelEvent[] events) {
            if (!EventQueue.isDispatchThread()) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        processEvents(events);
                    }
                });
            } else {
                processEvents(events);
            }
        }

        private void processEvents(FormModelEvent[] events) {
            boolean lafBlock;
            if (events == null) {
                lafBlock = true;
            }
            else {
                lafBlock = false;
                boolean modifying = false;
                for (int i=0; i < events.length; i++) {
                    FormModelEvent ev = events[i];
                    if (ev.isModifying())
                        modifying = true;
                    if ((ev.getChangeType() == FormModelEvent.COMPONENT_ADDED)
                            || (ev.getChangeType() == FormModelEvent.COMPONENT_PROPERTY_CHANGED)
                            || (ev.getChangeType() == FormModelEvent.BINDING_PROPERTY_CHANGED)) {
                        lafBlock = true;
                        break;
                    }
                }
                if (!modifying)
                    return;

                assert EventQueue.isDispatchThread();
            }

            this.events = events;

            if (lafBlock) { // Look&Feel UI defaults remapping needed
                Locale defaultLocale = switchToDesignLocale(getFormModel());
                try {
                    FormLAF.executeWithLookAndFeel(formModel, this);
                }
                finally {
                    if (defaultLocale != null)
                        Locale.setDefault(defaultLocale);
                }
            }
            else run();
        }

        @Override
        public void run() {
            if (events == null) {
                Object originalVisualComp = (topDesignComponent == null) ? null : replicator.getClonedComponent(topDesignComponent);
                Dimension originalSize =  originalVisualComp instanceof Component ?
                    ((Component)originalVisualComp).getSize() : null;

                replicator.setTopMetaComponent(topDesignComponent);
                Component formClone = (Component) replicator.createClone();
                if (formClone != null) {
                    formClone.setVisible(true);
                    componentLayer.setTopDesignComponent(formClone);
                    if (originalSize != null) {
                        componentLayer.setDesignerSize(originalSize);
                        checkDesignerSize();
                    }
                    else setupDesignerSize();
                    if (getLayoutDesigner() != null)
                        getLayoutDesigner().externalSizeChangeHappened();
                    // Must be invoked later. ComponentLayer doesn't have a peer (yet)
                    // when the form is opened and validate does nothing on components
                    // without peer.
                    EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            updateComponentLayer(false);
                        }
                    });
                }
                return;
            }

            FormModelEvent[] events = this.events;
            this.events = null;

            int prevType = 0;
            ComponentContainer prevContainer = null;
            boolean updateDone = false;
            boolean deriveDesignerSize = false;

            for (int i=0; i < events.length; i++) {
                FormModelEvent ev = events[i];
                int type = ev.getChangeType();
                ComponentContainer metacont = ev.getContainer();

                if (type == FormModelEvent.CONTAINER_LAYOUT_EXCHANGED
                    || type == FormModelEvent.CONTAINER_LAYOUT_CHANGED
                    || type == FormModelEvent.COMPONENT_LAYOUT_CHANGED)
                {
                    if ((prevType != FormModelEvent.CONTAINER_LAYOUT_EXCHANGED
                         && prevType != FormModelEvent.CONTAINER_LAYOUT_CHANGED
                         && prevType != FormModelEvent.COMPONENT_LAYOUT_CHANGED)
                        || prevContainer != metacont)
                    {
                        replicator.updateContainerLayout((RADVisualContainer)
                                                         metacont);
                        updateDone = true;
                    }
                }
                else if (type == FormModelEvent.COMPONENT_ADDED) {
                    if ((metacont instanceof RADVisualContainer
                            || metacont instanceof RADMenuComponent)
                        && (prevType != FormModelEvent.COMPONENT_ADDED
                            || prevContainer != metacont))
                    {
                        replicator.updateAddedComponents(metacont);
                        // Note: replicator calls BindingDesignSupport to establish
                        // bindings for the the cloned instance (e.g. in remove undo)
                        updateDone = true;
                    }
                }
                else if (type == FormModelEvent.COMPONENT_REMOVED) {
                    RADComponent removed = ev.getComponent();

                    // if the top designed component (or some of its parents)
                    // was removed then whole designer view must be recreated
                    if (removed instanceof RADVisualComponent
                        && (removed == topDesignComponent
                            || removed.isParentComponent(topDesignComponent)))
                    {
                        resetTopDesignComponent(false);
                        updateWholeDesigner();
                        return;
                    }
                    else {
                        replicator.removeComponent(ev.getComponent(), ev.getContainer());
                        updateDone = true;
                    }
                    // Note: BindingDesignSupport takes care of removing bindings
                }
                else if (type == FormModelEvent.COMPONENTS_REORDERED) {
                    if (prevType != FormModelEvent.COMPONENTS_REORDERED
                        || prevContainer != metacont)
                    {
                        replicator.reorderComponents(metacont);
                        updateDone = true;
                    }
                }
                else if (type == FormModelEvent.COMPONENT_PROPERTY_CHANGED) {
                    RADProperty eventProperty = ev.getComponentProperty();
                    RADComponent eventComponent = ev.getComponent();
                    
                    replicator.updateComponentProperty(eventProperty);
                    updateConnectedProperties(eventProperty, eventComponent);
                    
                    updateDone = true;
                }
                else if (type == FormModelEvent.BINDING_PROPERTY_CHANGED) {
                    if (ev.getSubPropertyName() == null) {
                        replicator.updateBinding(ev.getNewBinding());
                    }
                    // Note: BindingDesignSupport takes care of removing the old binding
                    updateDone = true;
                }
                else if (type == FormModelEvent.SYNTHETIC_PROPERTY_CHANGED
                         && PROP_DESIGNER_SIZE.equals(ev.getPropertyName()))
                {
                    Dimension size = (Dimension) ev.getNewPropertyValue();
                    if (size != null) {
                        componentLayer.setDesignerSize(size);
                        deriveDesignerSize = false;
                        updateDone = true;
                    }
                    else { // null size to compute designer size based on content (from resetDesignerSize)
                        deriveDesignerSize = true;
                        updateDone = true;
                    }
                }

                prevType = type;
                prevContainer = metacont;
            }

            if (updateDone) {
                if (deriveDesignerSize) { // compute from preferred size
                    setupDesignerSize();
                }
                else { // check if not smaller than minimum size
                    checkDesignerSize();
                }
                LayoutDesigner layoutDesigner = getLayoutDesigner();
                if ((layoutDesigner != null) && formModel.isCompoundEditInProgress()) {
                    getLayoutDesigner().externalSizeChangeHappened();
                }
                updateComponentLayer(true);
            }
        }
        
        private void updateConnectedProperties(RADProperty eventProperty, RADComponent eventComponent){
            for (RADComponent component : formModel.getAllComponents()){
                RADProperty[] properties = component.getKnownBeanProperties();
                for(int i = 0; i < properties.length; i++){
                    try{
                        if (properties[i].isChanged()) {
                            Object value = properties[i].getValue();
                            if (value instanceof RADConnectionPropertyEditor.RADConnectionDesignValue) {
                                RADConnectionPropertyEditor.RADConnectionDesignValue propertyValue = 
                                    (RADConnectionPropertyEditor.RADConnectionDesignValue)value;

                                if (propertyValue.getRADComponent() != null
                                   && propertyValue.getProperty() != null
                                   && eventComponent.getName().equals(propertyValue.getRADComponent().getName())
                                   && eventProperty.getName().equals(propertyValue.getProperty().getName())) {

                                    replicator.updateComponentProperty(properties[i]);
                                }
                            }
                        }
                    } catch(Exception e){
                        ErrorManager.getDefault().notify(e);
                    }                                                        
                }
            }
                                
        }
    }

    /**
     * Action that aligns selected components in the specified direction.
     */
    private class AlignAction extends AbstractAction {
        // PENDING change to icons provided by Dusan
        private static final String ICON_BASE = "org/netbeans/modules/form/resources/align_"; // NOI18N
        /** Dimension to align in. */
        private int dimension;
        /** Requested alignment. */
        private int alignment;
        /** Group/Align action. */
        private boolean closed;
        
        /**
         * Creates action that aligns selected components in the specified direction.
         *
         * @param dimension dimension to align in.
         * @param alignment requested alignment.
         */
        AlignAction(int dimension, int alignment, boolean closed) {
            this.dimension = dimension;
            this.alignment = alignment;
            this.closed = closed;
            boolean horizontal = (dimension == LayoutConstants.HORIZONTAL);
            boolean leading = (alignment == LayoutConstants.LEADING);
            String code;
            if (alignment == LayoutConstants.CENTER) {
                code = (horizontal ? "ch" : "cv"); // NOI18N
            } else {
                code = (horizontal ? (leading ? "l" : "r") : (leading ? "u" : "d")); // NOI18N
            }
            String iconResource = ICON_BASE + code + ".png"; // NOI18N
            putValue(Action.SMALL_ICON, ImageUtilities.loadImageIcon(iconResource, false));
            putValue(Action.SHORT_DESCRIPTION, FormUtils.getBundleString("CTL_AlignAction_" + code)); // NOI18N
            setEnabled(false);
        }
        
        /**
         * Performs the alignment of selected components.
         *
         * @param e event that invoked the action.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            align(closed, dimension, alignment);
        }
        
        public int getDimension() {
            return dimension;
        }
        
        public int getAlignment() {
            return alignment;
        }
        
    }    
    /**
     * Action that aligns selected components in the specified direction.
     */
    private class ResizabilityAction extends AbstractAction {
        // PENDING change to icons provided by Dusan
        private static final String ICON_BASE = "org/netbeans/modules/form/resources/resize_"; // NOI18N
        /** Dimension of resizability. */
        private int dimension;
        
        /**
         * Creates action that changes the resizability of the component.
         *
         * @param dimension dimension of the resizability
         */
        ResizabilityAction(int dimension) {
            this.dimension = dimension;
            String code = (dimension == LayoutConstants.HORIZONTAL) ? "h" : "v"; // NOI18N
            String iconResource = ICON_BASE + code + ".png"; // NOI18N
            putValue(Action.SMALL_ICON, ImageUtilities.loadImageIcon(iconResource, false));
            putValue(Action.SHORT_DESCRIPTION, FormUtils.getBundleString("CTL_ResizeButton_" + code)); // NOI18N
            setEnabled(false);
        }
        
        /**
         * Performs the resizability change of selected components.
         *
         * @param e event that invoked the action.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            FormModel formModel = getFormModel();
            LayoutModel layoutModel = formModel.getLayoutModel();
            Object layoutUndoMark = layoutModel.getChangeMark();
            javax.swing.undo.UndoableEdit ue = layoutModel.getUndoableEdit();
            boolean autoUndo = true;
            LayoutDesigner layoutDesigner = getLayoutDesigner();
            Collection componentIds = componentIds();
            Set<RADVisualContainer> containers = new HashSet<RADVisualContainer>();
            try {
                Iterator iter = componentIds.iterator();
                while (iter.hasNext()) {
                    String compId = (String)iter.next();
                    LayoutComponent layoutComp = layoutModel.getLayoutComponent(compId);
                    boolean resizing = (getResizabilityButtons()[dimension]).isSelected();
                    if (layoutDesigner.isComponentResizing(layoutComp, dimension) != resizing) {
                        layoutDesigner.setComponentResizing(layoutComp, dimension, resizing);
                        RADVisualComponent comp = (RADVisualComponent)formModel.getMetaComponent(compId);
                        containers.add(comp.getParentContainer());
                    }
                }
                autoUndo = false;
            } finally {
                Iterator<RADVisualContainer> iter = containers.iterator();
                while (iter.hasNext()) {
                    formModel.fireContainerLayoutChanged(iter.next(), null, null, null);
                }
                if (!layoutUndoMark.equals(layoutModel.getChangeMark())) {
                    formModel.addUndoableEdit(ue);
                }
                if (autoUndo) {
                    formModel.forceUndoOfCompoundEdit();
                }
            }
        }
    }
    
    static class FormProxyLookup extends ProxyLookup {

        FormProxyLookup(Lookup[] lookups) {
            super(lookups);
        }
        
        Lookup[] getSubLookups() {
            return getLookups();
        }
        
        void setSubLookups(Lookup[] lookups) {
            setLookups(lookups);
        }
        
    }
    
}
