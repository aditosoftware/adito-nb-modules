package org.netbeans.modules.form;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.*;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.model.IAditoModelDataProvider;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync.*;
import org.netbeans.modules.form.adito.*;
import org.netbeans.modules.form.adito.layout.*;
import org.netbeans.modules.form.adito.perstistencemanager.*;
import org.netbeans.modules.form.layoutdesign.LayoutModel;
import org.netbeans.modules.form.layoutsupport.LayoutSupportManager;
import org.openide.filesystems.FileObject;
import org.openide.nodes.Node;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;

/**
 * @author J. Boesl, 19.01.11
 */
public class AditoPersistenceManager extends PersistenceManager
{

  private static final int LAYOUT_ABSOLUTE = 6;
  private static final int LAYOUT_UNKNOWN = -1;
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
    _loadForm(new APersistenceManagerInfo(formObject, formModel == null ? new FormModel() : formModel, nonfatalErrors));
  }

  @Override
  public void saveForm(FormDataObject pFormObject, FormModel pFormModel, List<Throwable> pNonfatalErrors)
      throws PersistenceException
  {
    // Save gibts hier nicht. Das Model wird sowieso synchronisiert.
  }

  private void _loadForm(APersistenceManagerInfo pInfo) throws PersistenceException
  {
    FormModel formModel = pInfo.getFormModel();
    FileObject modelRoot = pInfo.getModelRoot();
    try
    {
      AComponentInfo componentInfo = AComponentInfo.create(modelRoot, pInfo);
      Class<?> formBaseClass = componentInfo.getComponentClass();
      formModel.setFormBaseClass(formBaseClass, DMHelper.getHandler(modelRoot));
      formModel.setName(componentInfo.getComponentName());
      // Force creation of the default instance in the correct L&F context
      BeanSupport.getDefaultInstance(formBaseClass);

      RADComponent topComp = formModel.getTopRADComponent();
      _loadComponent(pInfo, modelRoot, topComp, null);
      _copyValues(topComp);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    formModel.addFormModelListener(new AFormModelListener()
    {
      @Override
      protected void clearProperties(RADComponent pRADComponent)
      {
        pRADComponent.clearProperties();
      }
    });
//    FormEditor.updateProjectForNaturalLayout(formModel);
//    formModel.setFreeDesignDefaultLayout(true);
  }


  private void _loadComponent(APersistenceManagerInfo pInfo, FileObject pModelComp, RADComponent pComponent, RADComponent pParentComponent)
      throws PersistenceException
  {

    // if the loaded component is a visual component in a visual contianer,
    // then load NB 3.1 layout constraints for it
    if (pComponent instanceof RADVisualComponent && pParentComponent instanceof RADVisualContainer /*&&
        pModelComp.getFileObject("x") != null && pModelComp.getFileObject("y") != null &&
        pModelComp.getFileObject("width") != null && pModelComp.getFileObject("height") != null*/)
      _loadConstraints(pModelComp, pComponent, (RADVisualContainer) pParentComponent);

    ComponentContainer container = // is this component a container?
        pComponent instanceof ComponentContainer ? (ComponentContainer) pComponent : null;
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
      convIndex = _loadLayout(pModelComp, visualContainer.getLayoutSupport());
    }


    // load subcomponents
    RADComponent[] childComponents;

    FileObject childModels = NbAditoInterface.lookup(IAditoModelDataProvider.class).getChildDataModels(pModelComp);
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

      try
      {
        _copyChildValues(visualContainer);
      }
      catch (IllegalAccessException e)
      {
        e.printStackTrace();  // TODO: error-handling
      }
      catch (InvocationTargetException e)
      {
        e.printStackTrace();  // TODO: error-handling
      }

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

  private void _copyChildValues(RADVisualContainer pVisualContainer) throws InvocationTargetException, IllegalAccessException
  {
    for (RADComponent childComponent : pVisualContainer.getSubComponents())
      _copyValues(childComponent);
  }

  private void _copyValues(RADComponent pComponent) throws InvocationTargetException, IllegalAccessException
  {
    ARADComponentHandler aRADComponentHandler = pComponent.getARADComponentHandler();
    if (aRADComponentHandler != null)
    {
      for (Node.PropertySet set : aRADComponentHandler.getPropertySets())
      {
        for (Node.Property prop : set.getProperties())
        {
          Node.Property radProperty = pComponent.getPropertyByName(prop.getName());
          if (radProperty != null)
            radProperty.setValue(prop.getValue());
        }
      }
    }
  }

  private RADComponent _restoreComponent(APersistenceManagerInfo pInfo, FileObject pChildModel, RADComponent pParentComponent)
      throws PersistenceException
  {
    AComponentInfo componentInfo = AComponentInfo.create(pChildModel, pInfo);
    if (componentInfo == null)
      return null;

    // create a new metacomponent
    RADComponent newComponent;

    if (componentInfo.getModelPropProvider().isContainer())
      newComponent = new RADVisualContainer();
    else if (FormUtils.isVisualizableClass(componentInfo.getComponentClass()))
      newComponent = new RADVisualComponent();
    else
    {
      //newComponent = new RADComponent();
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
    Throwable compEx = null;
    try
    {
      if (componentInfo.getComponentClass() == InvalidComponent.class)
      {
        newComponent.setValid(false);
      }
      newComponent.initialize(pInfo.getFormModel());
      newComponent.setStoredName(componentInfo.getComponentName());
      newComponent.initInstance(componentInfo.getComponentClass(), DMHelper.getHandler(pChildModel));
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

    pInfo.getComponentsMap().put(componentInfo.getComponentName(), newComponent);

    // load the metacomponent (properties, events, layout, etc)
    _loadComponent(pInfo, pChildModel, newComponent, pParentComponent);

    return newComponent;
  }


  private int _loadLayout(FileObject pModelComp, LayoutSupportManager layoutSupport)
  {

    try
    {
      LayoutManager layout = _getPropertyInfo().createModelPropProvider(pModelComp).createLayout();
      layoutSupport.getPrimaryContainer().setLayout(layout);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      // mach ma nix ...
    }
    return LAYOUT_ABSOLUTE;
  }

  private boolean _loadConstraints(FileObject pModelComp, RADComponent pComponent, RADVisualContainer pParentComponent)
  {
    try
    {
      if (pComponent instanceof RADVisualComponent)
      {
        IFormComponentInfo formModelPropProvider = _getPropertyInfo().createModelPropProvider(pModelComp);

        //Class<?> layoutMgrCls = formModelPropProvider.getParentLayoutClass();
        //LayoutSupportRegistry registry = LayoutSupportRegistry.getRegistry(pComponent.getFormModel());
        //registry.createSupportForLayout(layoutMgrCls);

        Object realConstraints = formModelPropProvider.createConstraints();
        if (realConstraints instanceof IAditoLayoutConstraints)
        {
          Object typeInfo = ((IAditoLayoutConstraints) realConstraints).getTypeInfo();
          if (typeInfo instanceof IAnchorLayoutPropertyTypes)
            ((RADVisualComponent) pComponent).setLayoutConstraints(
                AditoLayoutSupport.class, new AditoComponentConstraints((IAditoLayoutConstraints) realConstraints));
          else if (typeInfo instanceof IRegisterLayoutPropertyTypes)
            ((RADVisualComponent) pComponent).setLayoutConstraints(
                AditoRegisterLayoutSupport.class, new AditoComponentConstraints((IAditoLayoutConstraints) realConstraints));
        }
      }

      return true;
    }
    catch (Exception ex)
    { // should not happen
      ex.printStackTrace();
    }
    return false;
  }

  private IFormComponentInfoProvider _getPropertyInfo()
  {
    return NbAditoInterface.lookup(IFormComponentInfoProvider.class);
  }

}
