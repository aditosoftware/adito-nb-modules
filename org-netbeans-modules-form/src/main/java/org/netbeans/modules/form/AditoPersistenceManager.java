package org.netbeans.modules.form;

import de.adito.aditoweb.filesystem.common.AfsUrlUtil;
import de.adito.aditoweb.filesystem.datamodelfs.access.mechanics.field.IFieldAccess;
import de.adito.aditoweb.filesystem.datamodelfs.access.model.*;
import de.adito.aditoweb.swingcommon.layout.aditolayout.*;
import org.netbeans.modules.form.adito.*;
import org.netbeans.modules.form.codestructure.*;
import org.netbeans.modules.form.layoutdesign.*;
import org.netbeans.modules.form.layoutdesign.support.SwingLayoutBuilder;
import org.netbeans.modules.form.layoutsupport.LayoutSupportManager;
import org.openide.ErrorManager;
import org.openide.filesystems.*;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;

/**
 * @author J. Boesl, 19.01.11
 */
public class AditoPersistenceManager extends PersistenceManager
{

  // --------
  // NB 3.1 compatibility - layout persistence conversion tables

  private static final int LAYOUT_BORDER = 0;
  private static final int LAYOUT_FLOW = 1;
  private static final int LAYOUT_BOX = 2;
  private static final int LAYOUT_GRIDBAG = 3;
  private static final int LAYOUT_GRID = 4;
  private static final int LAYOUT_CARD = 5;
  private static final int LAYOUT_ABSOLUTE = 6;
  private static final int LAYOUT_NULL = 7;
  private static final int LAYOUT_JSCROLL = 8;
  private static final int LAYOUT_SCROLL = 9;
  private static final int LAYOUT_JSPLIT = 10;
  private static final int LAYOUT_JTAB = 11;
  private static final int LAYOUT_JLAYER = 12;
  private static final int LAYOUT_TOOLBAR = 13;

  private static final int LAYOUT_UNKNOWN = -1;
  private static final int LAYOUT_FROM_CODE = -2;
  private static final int LAYOUT_NATURAL = -3;


  @Override
  public boolean canLoadForm(FormDataObject formObject) throws PersistenceException
  {
    return true;
  }

  @Override
  public synchronized void loadForm(FormDataObject formObject, FormModel formModel, List<Throwable> nonfatalErrors)
      throws PersistenceException
  {
    _loadForm(new _Info(formObject, formModel, nonfatalErrors));
  }

  @Override
  public void saveForm(FormDataObject pFormObject, FormModel pFormModel, List<Throwable> pNonfatalErrors)
      throws PersistenceException
  {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  private void _loadForm(_Info pInfo) throws PersistenceException
  {
    FormModel formModel = pInfo.getFormModel();
    try
    {
      Class<JPanel> formBaseClass = JPanel.class;
      formModel.setFormBaseClass(formBaseClass, DMHelper.getHandler(pInfo.getModelRoot()));
      // Force creation of the default instance in the correct L&F context
      BeanSupport.getDefaultInstance(formBaseClass);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    formModel.setName(pInfo.getFormObject().getPrimaryFile().getName());

    RADComponent topComp = formModel.getTopRADComponent();
    if (topComp != null) // load the main form component
      _loadComponent(pInfo, pInfo.getModelRoot(), topComp, null);

//    FormEditor.updateProjectForNaturalLayout(formModel);
//    formModel.setFreeDesignDefaultLayout(true);
  }


  private void _loadComponent(_Info pInfo, FileObject pModelComp, RADComponent pComponent, RADComponent pParentComponent)
      throws PersistenceException
  {

    // if the loaded component is a visual component in a visual contianer,
    // then load NB 3.1 layout constraints for it
    if (pComponent instanceof RADVisualComponent && pParentComponent instanceof RADVisualContainer)
    {
      CodeExpression compExp = pComponent.getCodeExpression();
      LayoutSupportManager layoutSupport = ((RADVisualContainer) pParentComponent).getLayoutSupport();

      if (pModelComp.getFileObject("x") != null && pModelComp.getFileObject("y") != null)
        _loadConstraints(pModelComp, compExp, layoutSupport);
    }

    ComponentContainer container = // is this component a container?
        pComponent instanceof ComponentContainer ?
            (ComponentContainer) pComponent : null;
    if (container == null)
    { // this component is not a container
//      if (pParentComponent == null) // this is a root component - load resource properties
//        ResourceSupport.loadInjectedResources(pComponent);
      return;
    }


    // we continue in loading container
    RADVisualContainer visualContainer = // is it a visual container?
        pComponent instanceof RADVisualContainer ?
            (RADVisualContainer) pComponent : null;

    int convIndex = LAYOUT_UNKNOWN;
    if (visualContainer != null)
    {
      visualContainer.setOldLayoutSupport(true);
      convIndex = _loadLayout(visualContainer.getLayoutSupport(), pModelComp);
    }


    // load subcomponents
    RADComponent[] childComponents;
    FileObject childModels = pModelComp.getFileObject(FieldConst.CHILDDATAMODELS.getName());
    if (childModels != null)
    {
      List<RADComponent> list = new ArrayList<RADComponent>();
      for (FileObject childModel : childModels.getChildren())
      {
        RADComponent newComp = _restoreComponent(pInfo, childModel, pComponent);
        if (newComp != null)
          list.add(newComp);
      }
      childComponents = new RADComponent[list.size()];
      list.toArray(childComponents);
    }
    else
      childComponents = new RADComponent[0];


    if (visualContainer != null)
    {
      Throwable layoutEx = null;
      boolean layoutInitialized = false;
      LayoutSupportManager layoutSupport = visualContainer.getLayoutSupport();

      if (convIndex == LAYOUT_NATURAL)
      {
        LayoutModel layoutModel = pInfo.getFormModel().getLayoutModel();
        Map<String, String> nameToIdMap = new HashMap<String, String>();
        for (RADComponent comp : childComponents)
          nameToIdMap.put(comp.getName(), comp.getId());
        try
        {
          layoutModel.loadContainerLayout(visualContainer.getId(), pModelComp /*layoutNode.getChildNodes()*/, nameToIdMap); // TODO
          visualContainer.setOldLayoutSupport(false);
          layoutSupport = null;
          layoutInitialized = true;
          //newLayout = Boolean.TRUE;  // TODO?
        }
        catch (Exception ex)
        {
          // error occurred - treat this container as with unknown layout
          layoutModel.changeContainerToComponent(visualContainer.getId());
          layoutEx = ex;
        }
        visualContainer.initSubComponents(childComponents);
      }
      else if (convIndex >= 0)
      {
        // initialize layout support from restored code
        try
        {
          layoutInitialized = layoutSupport.prepareLayoutDelegate(true, true);
        }
        catch (Exception ex)
        {
          layoutEx = ex;
        }
        catch (LinkageError ex)
        {
          layoutEx = ex;
        }
        // subcomponents are set after reading from code [for some reason...]
        visualContainer.initSubComponents(childComponents);
        if (layoutInitialized)
        { // successfully initialized - build the primary container
          try
          { // some weird problems might occur - see issue 67890
            layoutSupport.updatePrimaryContainer();
          }
          // can't do anything reasonable on failure, just log stack trace
          catch (Exception ex)
          {
            ErrorManager.getDefault().notify(ErrorManager.INFORMATIONAL, ex);
          }
          catch (Error ex)
          {
            ErrorManager.getDefault().notify(ErrorManager.INFORMATIONAL, ex);
          }
        }
      }
      else
      { // Issue 63394, 68753: Bean form that is container
        // Issue 70369: Container saved with unknown layout
        // Swing menus go here as well
        // default init - not reading from code - need the subcomponents set
        visualContainer.initSubComponents(childComponents);
        try
        {
          // (default init also builds the primary container)
          layoutInitialized = layoutSupport.prepareLayoutDelegate(false, true);
          if (!layoutInitialized)
          { // not known to the old support (e.g. has GroupLayout)
            // (but we are sure the container instance is empty)
            java.awt.Container cont = layoutSupport.getPrimaryContainerDelegate();
            if (SwingLayoutBuilder.isRelevantContainer(cont))
            {
              // acknowledged by SwingLayoutBuilder - this is new layout
              visualContainer.setOldLayoutSupport(false);
              pInfo.getFormModel().getLayoutModel().addRootComponent(
                  new LayoutComponent(visualContainer.getId(), true));
              layoutSupport = null;
              //newLayout = Boolean.TRUE; // TODO?
            }
            else
            {
              layoutSupport.setUnknownLayoutDelegate(false);
              System.err.println("[WARNING] Unknown layout " // TODO ?
                                     + " (" + pComponent.getBeanClass().getName() + ")"); // NOI18N
            }
            layoutInitialized = true;
          }
        }
        catch (Exception ex)
        {
          layoutEx = ex;
        }
      }

      if (!layoutInitialized)
        layoutSupport.setUnknownLayoutDelegate(false);

      /*if (layoutSupport != null && newLayout == null)   // TODO ?
      {
        newLayout = Boolean.FALSE;
      */

      if (layoutEx != null)
        layoutEx.printStackTrace(); // TODO: error-handling
    }
    else
    { // non-visual container (e.g. AWT menu)
      container.initSubComponents(childComponents);
    }


//    // hack for properties that can't be set until the component is added
//    // to the parent container
//    for (RADComponent childcomp : childComponents)
//    {
//      List postProps;
//      if (parentDependentProperties != null
//          && (postProps = parentDependentProperties.get(childcomp)) != null)
//      {
//        for (Iterator it = postProps.iterator(); it.hasNext();)
//        {
//          RADProperty prop = (RADProperty) it.next();
//          Object propValue = it.next();
//          try
//          {
//            prop.setValue(propValue);
//          }
//          catch (Exception ex)
//          { // ignore
//            String msg = createLoadingErrorMessage(
//                FormUtils.getBundleString("MSG_ERR_CannotSetLoadedValue"), // NOI18N
//                node);
//            annotateException(ex, msg);
//            nonfatalErrors.add(ex);
//          }
//        }
//      }
//      // here it is also safe to load resource properties into sub-component
//      ResourceSupport.loadInjectedResources(childcomp);
//    }
//
//    // hack for properties that can't be set until all subcomponents
//    // are added to the container
//    List postProps;
//    if (childrenDependentProperties != null
//        && (postProps = childrenDependentProperties.get(component)) != null)
//    {
//      for (Iterator it = postProps.iterator(); it.hasNext();)
//      {
//        RADProperty prop = (RADProperty) it.next();
//        Object propValue = it.next();
//        try
//        {
//          prop.setValue(propValue);
//        }
//        catch (Exception ex)
//        { // ignore
//          String msg = createLoadingErrorMessage(
//              FormUtils.getBundleString("MSG_ERR_CannotSetLoadedValue"), // NOI18N
//              node);
//          annotateException(ex, msg);
//          nonfatalErrors.add(ex);
//        }
//      }
//    }

//    if (pParentComponent == null) // this is a root component
//      ResourceSupport.loadInjectedResources(pComponent);
  }

  // recognizes, creates, initializes and loads a meta component

  private RADComponent _restoreComponent(_Info pInfo, FileObject pChildModel, RADComponent pParentComponent) throws PersistenceException
  {
    EModelAccessType compType;
    String compName;
    String className;
    try
    {
      Integer type = FieldConst.TYPE.accessField(pChildModel).getValue();
      compName = FieldConst.NAME.accessField(pChildModel).getValue();
      compType = EModelAccessType.get(type);
      EModelComponentMapping eModelCompMapping = EModelComponentMapping.get(compType);
      if (eModelCompMapping == null)
        return null;
      className = eModelCompMapping.getSwingClass().getName();
    }
    catch (Exception e)
    {
      return null; // kein Fehler, aber auch keine Komponente.
    }

    // first load the component class
    Class<?> compClass = null;
    Throwable compEx = null;
    try
    {
      compClass = FormUtils.loadSystemClass(className);
      // Force creation of the default instance in the correct L&F context
      BeanSupport.getDefaultInstance(compClass);
    }
    catch (Exception ex)
    {
      compClass = InvalidComponent.class;
      compEx = ex;
    }
    catch (LinkageError ex)
    {
      compClass = InvalidComponent.class;
      compEx = ex;
    }
    if (compEx != null)
    { // loading the component class failed
      pInfo.getNonfatalErrors().add(compEx);
    }

    compEx = null;
    // create a new metacomponent
    RADComponent newComponent;

    switch (compType)
    {
      case BUTTON:
      case CHECKBOX:
      case COMBOBOX:
      case EDITFIELD:
      case LABEL:
      case LIST:
      case RADIOBUTTON:
      case REGISTER:
      case TABLE:
      case TREE:
        if (FormUtils.isVisualizableClass(compClass))
          newComponent = new RADVisualComponent();
        else
          newComponent = new RADComponent();
        break;
      default:
        PersistenceException ex = new PersistenceException("Unknown component element: " + pChildModel.toString()); // NOI18N
        pInfo.getNonfatalErrors().add(ex);
        return null;
    }

//    if (XML_COMPONENT.equals(nodeName))
//    {
//      if (compClass == InvalidComponent.class)
//      {
//        if (parentComponent instanceof RADVisualContainer)
//        {
//          newComponent = new RADVisualComponent();
//        }
//        else
//        {
//          newComponent = new RADComponent();
//        }
//      }
//      else
//      {
//        if (FormUtils.isVisualizableClass(compClass))
//        {
//          newComponent = new RADVisualComponent();
//        }
//        else
//        {
//          newComponent = new RADComponent();
//        }
//      }
//    }
//    else if (XML_MENU_COMPONENT.equals(nodeName))
//    {
//      if (RADVisualComponent.getMenuType(compClass) != null)
//      {
//        newComponent = new RADVisualComponent();
//      }
//      else
//      {
//        newComponent = new RADMenuItemComponent();
//      }
//    }
//    else if (XML_MENU_CONTAINER.equals(nodeName))
//    {
//      if (RADVisualComponent.getMenuType(compClass) != null)
//      {
//        newComponent = new RADVisualContainer();
//      }
//      else
//      {
//        newComponent = new RADMenuComponent();
//      }
//    }
//    else if (XML_CONTAINER.equals(nodeName))
//    {
//      if (compClass == InvalidComponent.class)
//      {
//        newComponent = new RADVisualContainer();
//      }
//      else
//      {
//        if (java.awt.Container.class.isAssignableFrom(compClass))
//          newComponent = new RADVisualContainer();
//        else newComponent = new RADContainer();
//      }
//    }
//    else
//    {
//      PersistenceException ex = new PersistenceException(
//          "Unknown component element"); // NOI18N
//      annotateException(ex,
//                        ErrorManager.ERROR,
//                        FormUtils.getFormattedBundleString("FMT_ERR_UnknownElement", // NOI18N
//                                                           new Object[]{nodeName})
//      );
//      nonfatalErrors.add(ex);
//      return null;
//    }

    // initialize the metacomponent
    try
    {
      if (compClass == InvalidComponent.class)
      {
        newComponent.setValid(false);
        newComponent.setMissingClassName(className);
      }
      newComponent.initialize(pInfo.getFormModel());
      newComponent.setStoredName(compName);
      newComponent.initInstance(compClass, DMHelper.getHandler(pChildModel));
      newComponent.setInModel(true);
    }
    catch (Exception ex)
    {
      compEx = ex;
    }
    catch (LinkageError ex)
    {
      compEx = ex;
    }
    if (compEx != null)
    { // creating component instance failed
      pInfo.getNonfatalErrors().add(compEx);
      return null;
    }

    pInfo.getComponentsMap().put(compName, newComponent);

    // load the metacomponent (properties, events, layout, etc)
    _loadComponent(pInfo, pChildModel, newComponent, pParentComponent);

    return newComponent;
  }


  private int _loadLayout(LayoutSupportManager layoutSupport, FileObject pModelFo)
  {
    try
    {
      CodeExpression layoutExp = layoutSupport.getCodeStructure().createExpression(
          AditoAnchorLayout.class.getConstructor(new Class[0]),
          CodeStructure.EMPTY_PARAMS);
      CodeStructure.createStatement(
          layoutSupport.getContainerDelegateCodeExpression(),
          _getSetLayoutMethod(),
          new CodeExpression[]{layoutExp});
    }
    catch (Exception e)
    {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
    return LAYOUT_ABSOLUTE;
  }

  private boolean _loadConstraints(FileObject pModelComp, CodeExpression pCompExp, LayoutSupportManager pLayoutSupport)
  {
//        int convIndex = -1;
//        String layout31ConstraintName = node != null ?
//                   getAttribute(node, ATTR_CONSTRAINT_VALUE) : null;
//        if (layout31ConstraintName != null)
//            for (int i=0; i < layout31ConstraintsNames.length; i++)
//                if (layout31ConstraintName.equals(layout31ConstraintsNames[i])) {
//                    convIndex = i;
//                    break;
//                }
//
//        // skip constraints saved by NB 3.1 which are not for the current layout
//        if (convIndex < 0
//                || (layoutConvIndex >= 0 && convIndex != layoutConvIndex))
//            return false;
//
//        org.w3c.dom.Node constrNode = null;
//        org.w3c.dom.NamedNodeMap constrAttr = null;
//
//        if (/*convIndex >= 0 &&*/reasonable31Constraints[convIndex]) {
//            org.w3c.dom.NodeList children = node.getChildNodes();
//            if (children != null)
//                for (int i=0, n=children.getLength(); i < n; i++) {
//                    org.w3c.dom.Node cNode = children.item(i);
//                    if (cNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
//                        constrNode = cNode;
//                        constrAttr = cNode.getAttributes();
//                        break;
//                    }
//                }
//        }
//
//        if (constrNode == null)
//            return false;

    try
    { // obligatory try/catch block for finding methods and constructors

      CodeStructure codeStructure = pLayoutSupport.getCodeStructure();
      CodeExpression contCodeExp = pLayoutSupport.getContainerCodeExpression();
      CodeExpression contDelCodeExp = pLayoutSupport.getContainerDelegateCodeExpression();

//      if (convIndex == LAYOUT_BORDER)
//      {
//        if (!"BorderConstraints".equals(constrNode.getNodeName())) // NOI18N
//          return false; // should not happen
//
//        node = constrAttr.getNamedItem("direction"); // NOI18N
//        if (node != null)
//        {
//          String strValue = node.getNodeValue();
//          // create add method statement
//          CodeStructure.createStatement(
//              contDelCodeExp,
//              _getAddWithConstrMethod(),
//              new CodeExpression[]{pCompExp,
//                                   codeStructure.createExpression(
//                                       String.class,
//                                       strValue
//                                   )});
//        }
//      }
//
//      else if (convIndex == LAYOUT_GRIDBAG)
//      {
//        if (!"GridBagConstraints".equals(constrNode.getNodeName())) // NOI18N
//          return false; // should not happen
//
//        // create GridBagConstraints constructor expression
//        if (gridBagConstrConstructor == null)
//          gridBagConstrConstructor =
//              java.awt.GridBagConstraints.class.getConstructor(
//                  new Class[0]);
//
//        CodeExpression constrExp = codeStructure.createExpression(
//            gridBagConstrConstructor, CodeStructure.EMPTY_PARAMS);
//
//        // create statements for GridBagConstraints fields
//        String[] gbcAttrs = new String[]{
//            "gridX", "gridY", "gridWidth", "gridHeight", // NOI18N
//            "fill", "ipadX", "ipadY", // NOI18N
//            "anchor", "weightX", "weightY"}; // NOI18N
//        String[] gbcFields = new String[]{
//            "gridx", "gridy", "gridwidth", "gridheight", // NOI18N
//            "fill", "ipadx", "ipady", // NOI18N
//            "anchor", "weightx", "weighty"}; // NOI18N
//
//        for (int i = 0; i < gbcAttrs.length; i++)
//        {
//          node = constrAttr.getNamedItem(gbcAttrs[i]);
//          if (node != null)
//          {
//            Class valueType;
//            Object value;
//            String strValue = node.getNodeValue();
//            if (i < 8)
//            { // treat as int
//              valueType = Integer.TYPE;
//              value = Integer.valueOf(strValue);
//            }
//            else
//            { // treat as double
//              valueType = Double.TYPE;
//              value = Double.valueOf(strValue);
//            }
//
//            CodeStructure.createStatement(
//                constrExp,
//                java.awt.GridBagConstraints.class.getField(gbcFields[i]),
//                codeStructure.createExpression(valueType, value));
//          }
//        }
//
//        // Insets
//        CodeExpression[] insetsParams = new CodeExpression[4];
//        String[] insetsAttrs = new String[]{
//            "insetsTop", "insetsLeft", "insetsBottom", "insetsRight"}; // NOI18N
//
//        for (int i = 0; i < insetsAttrs.length; i++)
//        {
//          node = constrAttr.getNamedItem(insetsAttrs[i]);
//          String strValue = node != null ? node.getNodeValue() : "0"; // NOI18N
//          insetsParams[i] = codeStructure.createExpression(
//              Integer.TYPE,
//              Integer.valueOf(strValue)
//          );
//        }
//
//        if (insetsConstructor == null)
//          insetsConstructor = java.awt.Insets.class.getConstructor(
//              new Class[]{Integer.TYPE, Integer.TYPE,
//                          Integer.TYPE, Integer.TYPE});
//
//        CodeStructure.createStatement(
//            constrExp,
//            java.awt.GridBagConstraints.class.getField("insets"), // NOI18N
//            codeStructure.createExpression(insetsConstructor,
//                                           insetsParams));
//
//        // create add method statement
//        CodeStructure.createStatement(
//            contDelCodeExp,
//            _getAddWithConstrMethod(),
//            new CodeExpression[]{pCompExp, constrExp});
//      }
//
//      else if (convIndex == LAYOUT_JTAB)
//      {
//        if (!"JTabbedPaneConstraints".equals(constrNode.getNodeName())) // NOI18N
//          return false; // should not happen
//
//        Object tabName = null;
//        PropertyEditor tabNamePropEd = null;
//        Object toolTip = null;
//        PropertyEditor toolTipPropEd = null;
//        Object icon = null;
//        PropertyEditor iconPropEd = null;
//
//        org.w3c.dom.Node[] propNodes = findSubNodes(constrNode, XML_PROPERTY);
//        if (propNodes != null)
//          for (org.w3c.dom.Node propNode : propNodes)
//          {
//            Object editorOrValue = getPropertyEditorOrValue(propNode);
//            if (editorOrValue == NO_VALUE)
//              continue;
//
//            PropertyEditor prEd = null;
//            Object value = null;
//            if (editorOrValue instanceof PropertyEditor)
//              prEd = (PropertyEditor) editorOrValue;
//            else
//              value = editorOrValue;
//
//            String name = getAttribute(propNode, ATTR_PROPERTY_NAME);
//            if ("tabTitle".equals(name))
//            { // NOI18N
//              tabName = value;
//              tabNamePropEd = prEd;
//            }
//            else if ("tabToolTip".equals(name))
//            { // NOI18N
//              toolTip = value;
//              toolTipPropEd = prEd;
//            }
//            else if ("tabIcon".equals(name))
//            { // NOI18N
//              icon = value;
//              iconPropEd = prEd;
//            }
//          }
//
//        if (tabName == null
//            && (node = constrAttr.getNamedItem("tabName")) != null) // NOI18N
//          tabName = node.getNodeValue();
//        if (toolTip == null
//            && (node = constrAttr.getNamedItem("toolTip")) != null) // NOI18N
//          toolTip = node.getNodeValue();
//
//        if (toolTip != null || toolTipPropEd != null)
//        {
//          if (addTabMethod1 == null)
//            addTabMethod1 = javax.swing.JTabbedPane.class.getMethod(
//                "addTab", // NOI18N
//                new Class[]{String.class,
//                            javax.swing.Icon.class,
//                            java.awt.Component.class,
//                            String.class});
//          CodeStructure.createStatement(
//              contCodeExp,
//              addTabMethod1,
//              new CodeExpression[]{
//                  createExpressionForProperty(
//                      codeStructure, String.class, tabName, tabNamePropEd),
//                  createExpressionForProperty(
//                      codeStructure, javax.swing.Icon.class, icon, iconPropEd),
//                  pCompExp,
//                  createExpressionForProperty(
//                      codeStructure, String.class, toolTip, toolTipPropEd)});
//        }
//        else if (icon != null || iconPropEd != null)
//        {
//          if (addTabMethod2 == null)
//            addTabMethod2 = javax.swing.JTabbedPane.class.getMethod(
//                "addTab", // NOI18N
//                new Class[]{String.class,
//                            javax.swing.Icon.class,
//                            java.awt.Component.class});
//          CodeStructure.createStatement(
//              contCodeExp,
//              addTabMethod2,
//              new CodeExpression[]{
//                  createExpressionForProperty(
//                      codeStructure, String.class, tabName, tabNamePropEd),
//                  createExpressionForProperty(
//                      codeStructure, javax.swing.Icon.class, icon, iconPropEd),
//                  pCompExp});
//        }
//        else
//        {
//          if (addTabMethod3 == null)
//            addTabMethod3 = javax.swing.JTabbedPane.class.getMethod(
//                "addTab", // NOI18N
//                new Class[]{String.class,
//                            java.awt.Component.class});
//          CodeStructure.createStatement(
//              contCodeExp,
//              addTabMethod3,
//              new CodeExpression[]{
//                  createExpressionForProperty(
//                      codeStructure, String.class, tabName, tabNamePropEd),
//                  pCompExp});
//        }
//      }
//
//      else if (convIndex == LAYOUT_JSPLIT)
//      {
//        if (!"JSplitPaneConstraints".equals(constrNode.getNodeName())) // NOI18N
//          return false;
//
//        node = constrAttr.getNamedItem("position"); // NOI18N
//        if (node != null)
//        {
//          String position = node.getNodeValue();
//          Method addMethod;
//
//          if ("top".equals(position))
//          { // NOI18N
//            if (setTopComponentMethod == null)
//              setTopComponentMethod =
//                  javax.swing.JSplitPane.class.getMethod(
//                      "setTopComponent", // NOI18N
//                      new Class[]{java.awt.Component.class});
//            addMethod = setTopComponentMethod;
//          }
//          else if ("bottom".equals(position))
//          { // NOI18N
//            if (setBottomComponentMethod == null)
//              setBottomComponentMethod =
//                  javax.swing.JSplitPane.class.getMethod(
//                      "setBottomComponent", // NOI18N
//                      new Class[]{java.awt.Component.class});
//            addMethod = setBottomComponentMethod;
//          }
//          else if ("left".equals(position))
//          { // NOI18N
//            if (setLeftComponentMethod == null)
//              setLeftComponentMethod =
//                  javax.swing.JSplitPane.class.getMethod(
//                      "setLeftComponent", // NOI18N
//                      new Class[]{java.awt.Component.class});
//            addMethod = setLeftComponentMethod;
//          }
//          else if ("right".equals(position))
//          { // NOI18N
//            if (setRightComponentMethod == null)
//              setRightComponentMethod =
//                  javax.swing.JSplitPane.class.getMethod(
//                      "setRightComponent", // NOI18N
//                      new Class[]{java.awt.Component.class});
//            addMethod = setRightComponentMethod;
//          }
//          else return false;
//
//          CodeStructure.createStatement(contCodeExp,
//                                        addMethod,
//                                        new CodeExpression[]{pCompExp});
//        }
//      }
//
//      else if (convIndex == LAYOUT_CARD)
//      {
//        if (!"CardConstraints".equals(constrNode.getNodeName())) // NOI18N
//          return false;
//
//        node = constrAttr.getNamedItem("cardName"); // NOI18N
//        if (node != null)
//        {
//          String strValue = node.getNodeValue();
//          // create add method statement
//          CodeStructure.createStatement(
//              contDelCodeExp,
//              _getAddWithConstrMethod(),
//              new CodeExpression[]{pCompExp,
//                                   codeStructure.createExpression(
//                                       String.class,
//                                       strValue
//                                   )});
//        }
//      }
//
//      else if (convIndex == LAYOUT_JLAYER)
//      {
//        if (!"JLayeredPaneConstraints".equals(constrNode.getNodeName())) // NOI18N
//          return false;
//
//        CodeExpression[] boundsParams = new CodeExpression[4];
//        String[] boundsAttrs = new String[]{"x", "y", "width", "height"}; // NOI18N
//
//        for (int i = 0; i < boundsAttrs.length; i++)
//        {
//          node = constrAttr.getNamedItem(boundsAttrs[i]);
//          String strValue = node != null ?
//              node.getNodeValue() :
//              (i < 2 ? "0" : "-1"); // NOI18N
//          boundsParams[i] = codeStructure.createExpression(
//              Integer.TYPE,
//              Integer.valueOf(strValue)
//          );
//        }
//
//        if (setBoundsMethod == null)
//          setBoundsMethod = java.awt.Component.class.getMethod(
//              "setBounds", // NOI18N
//              new Class[]{Integer.TYPE, Integer.TYPE,
//                          Integer.TYPE, Integer.TYPE});
//        CodeStructure.createStatement(
//            pCompExp, setBoundsMethod, boundsParams);
//
//        node = constrAttr.getNamedItem("layer"); // NOI18N
//        if (node != null)
//        {
//          String strValue = node.getNodeValue();
//          // create add method statement
//          CodeStructure.createStatement(
//              contDelCodeExp,
//              _getAddWithConstrMethod(),
//              new CodeExpression[]{pCompExp,
//                                   codeStructure.createExpression(
//                                       Integer.TYPE,
//                                       Integer.valueOf(strValue)
//                                   )});
//        }
//      }
//
//      else

      IFieldAccess<Integer> fieldX = FieldConst.X.accessField(pModelComp);
      IFieldAccess<Integer> fieldY = FieldConst.Y.accessField(pModelComp);
      IFieldAccess<Integer> fieldWidth = FieldConst.WIDTH.accessField(pModelComp);
      IFieldAccess<Integer> fieldHeight = FieldConst.HEIGHT.accessField(pModelComp);
      AALComponentConstraints alComponentConstraints = new AALComponentConstraints(new Rectangle(
          fieldX.getValue(), fieldY.getValue(), fieldWidth.getValue(), fieldHeight.getValue()));

//      if (nullLayout)
//      {
//        if (setBoundsMethod == null)
//          setBoundsMethod = java.awt.Component.class.getMethod(
//              "setBounds", // NOI18N
//              new Class[]{Integer.TYPE, Integer.TYPE,
//                          Integer.TYPE, Integer.TYPE});
//        CodeStructure.createStatement(
//            pCompExp, setBoundsMethod, boundsParams);
//
//        // create add method statement
//        CodeStructure.createStatement(contDelCodeExp,
//                                      getSimpleAddMethod(),
//                                      new CodeExpression[]{pCompExp});
//      }
//      else

      // create add method statement
      CodeStructure.createStatement(
          contDelCodeExp,
          _getAddWithConstrMethod(),
          new CodeExpression[]{
              pCompExp, codeStructure.createExpression(AALComponentConstraints.class, alComponentConstraints)
          });

      return true;

    }
    catch (Exception ex)
    { // should not happen
      ex.printStackTrace();
    }
//    catch (NoSuchFieldException ex)
//    { // should not happen
//      ex.printStackTrace();
//    }
    return false;
  }


  private static Method _getSetLayoutMethod()
  {
    Method setLayoutMethod = null;
    try
    {
      setLayoutMethod = java.awt.Container.class.getMethod(
          "setLayout", // NOI18N
          new Class[]{java.awt.LayoutManager.class});
    }
    catch (NoSuchMethodException ex)
    { // should not happen
      ex.printStackTrace();
    }
    return setLayoutMethod;
  }

  private static Method _getAddWithConstrMethod()
  {
    Method addWithConstrMethod = null;
    try
    {
      addWithConstrMethod = java.awt.Container.class.getMethod(
          "add", // NOI18N
          new Class[]{java.awt.Component.class,
                      Object.class});
    }
    catch (NoSuchMethodException ex)
    { // should not happen
      ex.printStackTrace();
    }
    return addWithConstrMethod;
  }


  /**
   * Klasse mit Daten über den aktuellen Vorgang.
   */
  private class _Info
  {
    private FormDataObject formObject;
    private FormModel formModel;
    private List<Throwable> nonfatalErrors;
    private FileObject modelRoot;

    private Map<String, RADComponent> loadedComponents;


    private _Info(FormDataObject pFormObject, FormModel pFormModel, List<Throwable> pNonfatalErrors)
    {
      formObject = pFormObject;
      formModel = pFormModel;
      if (formModel == null)
        formModel = new FormModel();
      nonfatalErrors = pNonfatalErrors;

      try
      {
        FileObject aodFile = getFormObject().getPrimaryFile();
        modelRoot = URLMapper.findFileObject(AfsUrlUtil.createAdmFsUrl(aodFile.getURL()));
      }
      catch (Exception e)
      {
        throw new RuntimeException(e); // TODO: runtimeEx
      }
    }

    public FormDataObject getFormObject()
    {
      return formObject;
    }

    public FormModel getFormModel()
    {
      return formModel;
    }

    public List<Throwable> getNonfatalErrors()
    {
      return nonfatalErrors;
    }

    public FileObject getModelRoot()
    {
      return modelRoot;
    }

    public Map<String, RADComponent> getComponentsMap()
    {
      if (loadedComponents == null)
        loadedComponents = new HashMap<String, RADComponent>(50);
      return loadedComponents;
    }
  }

}
