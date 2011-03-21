package org.netbeans.modules.form;

import de.adito.aditoweb.filesystem.common.AfsUrlUtil;
import de.adito.aditoweb.filesystem.datamodelfs.access.mechanics.field.IFieldAccess;
import de.adito.aditoweb.filesystem.datamodelfs.access.model.*;
import de.adito.aditoweb.swingcommon.layout.aditolayout.*;
import org.netbeans.modules.form.adito.*;
import org.netbeans.modules.form.adito.layout.*;
import org.netbeans.modules.form.adito.mapping.EModelComponentMapping;
import org.netbeans.modules.form.layoutdesign.LayoutModel;
import org.netbeans.modules.form.layoutsupport.LayoutSupportManager;
import org.openide.filesystems.*;

import javax.swing.*;
import java.awt.*;
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
      if (pModelComp.getFileObject("x") != null && pModelComp.getFileObject("y") != null)
        _loadConstraints(pModelComp, pComponent, (RADVisualContainer) pParentComponent);
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
          visualContainer.initSubComponents(childComponents);
          layoutInitialized = layoutSupport.prepareLayoutDelegate(true);
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
//        visualContainer.initSubComponents(childComponents);
//        if (layoutInitialized)
//        { // successfully initialized - build the primary container
//          try
//          { // some weird problems might occur - see issue 67890
//            layoutSupport.updatePrimaryContainer();
//          }
//          // can't do anything reasonable on failure, just log stack trace
//          catch (Exception ex)
//          {
//            ErrorManager.getDefault().notify(ErrorManager.INFORMATIONAL, ex);
//          }
//          catch (Error ex)
//          {
//            ErrorManager.getDefault().notify(ErrorManager.INFORMATIONAL, ex);
//          }
//        }
      }
//      else
//      { // Issue 63394, 68753: Bean form that is container
//        // Issue 70369: Container saved with unknown layout
//        // Swing menus go here as well
//        // default init - not reading from code - need the subcomponents set
//        visualContainer.initSubComponents(childComponents);
//        try
//        {
//          // (default init also builds the primary container)
//          layoutInitialized = layoutSupport.prepareLayoutDelegate(false, true);
//          if (!layoutInitialized)
//          { // not known to the old support (e.g. has GroupLayout)
//            // (but we are sure the container instance is empty)
//            java.awt.Container cont = layoutSupport.getPrimaryContainerDelegate();
//            if (SwingLayoutBuilder.isRelevantContainer(cont))
//            {
//              // acknowledged by SwingLayoutBuilder - this is new layout
//              visualContainer.setOldLayoutSupport(false);
//              pInfo.getFormModel().getLayoutModel().addRootComponent(
//                  new LayoutComponent(visualContainer.getId(), true));
//              layoutSupport = null;
//              //newLayout = Boolean.TRUE; // TODO?
//            }
//            else
//            {
//              layoutSupport.setUnknownLayoutDelegate(false);
//              System.err.println("[WARNING] Unknown layout " // TODO ?
//                                     + " (" + pComponent.getBeanClass().getName() + ")"); // NOI18N
//            }
//            layoutInitialized = true;
//          }
//        }
//        catch (Exception ex)
//        {
//          layoutEx = ex;
//        }
//      }

      if (!layoutInitialized)
        layoutSupport.setUnknownLayoutDelegate();

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

  private RADComponent _restoreComponent(_Info pInfo, FileObject pChildModel, RADComponent pParentComponent)
      throws PersistenceException
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
      layoutSupport.getPrimaryContainer().setLayout(new AditoAnchorLayout());
//      LayoutSupportDelegate delegate = new AditoLayoutSupport();
//      layoutSupport.setLayoutDelegate(delegate);
    }
    catch (Exception e)
    {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
    return LAYOUT_ABSOLUTE;
  }

  private boolean _loadConstraints(FileObject pModelComp, RADComponent pComponent, RADVisualContainer pParentComponent)
  {
    try
    { // obligatory try/catch block for finding methods and constructors

//      CodeExpression codeExpression = pComponent.getCodeExpression();
//      LayoutSupportManager layoutSupport = pParentComponent.getLayoutSupport();

//      CodeStructure codeStructure = layoutSupport.getCodeStructure();
//      CodeExpression contCodeExp = layoutSupport.getContainerCodeExpression();
//      CodeExpression contDelCodeExp = layoutSupport.getContainerDelegateCodeExpression();

      IFieldAccess<Integer> fieldX = FieldConst.X.accessField(pModelComp);
      IFieldAccess<Integer> fieldY = FieldConst.Y.accessField(pModelComp);
      IFieldAccess<Integer> fieldWidth = FieldConst.WIDTH.accessField(pModelComp);
      IFieldAccess<Integer> fieldHeight = FieldConst.HEIGHT.accessField(pModelComp);
      AALComponentConstraints alComponentConstraints = new AALComponentConstraints(new Rectangle(
          fieldX.getValue(), fieldY.getValue(), fieldWidth.getValue(), fieldHeight.getValue()));

      if (pComponent instanceof RADVisualComponent)
      {
        AditoComponentConstraints constraints = new AditoComponentConstraints(alComponentConstraints.getBounds());
        ((RADVisualComponent) pComponent).setLayoutConstraints(AditoLayoutSupport.class, constraints);
      }

//      // create add method statement
//      CodeStructure.createStatement(
//          contDelCodeExp,
//          _getAddWithConstrMethod(),
//          new CodeExpression[]{
//              codeExpression, codeStructure.createExpression(AALComponentConstraints.class, alComponentConstraints)
//          });

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
