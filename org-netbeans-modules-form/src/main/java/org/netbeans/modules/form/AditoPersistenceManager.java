package org.netbeans.modules.form;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.anchor.IAnchorLayoutPropertyTypes;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.common.IAditoLayoutConstraints;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.register.IRegisterLayoutPropertyTypes;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.model.IAditoModelDataProvider;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync.*;
import de.adito.propertly.core.common.path.PropertyPath;
import de.adito.propertly.core.spi.IPropertyPitProvider;
import org.netbeans.modules.form.adito.*;
import org.netbeans.modules.form.adito.layout.*;
import org.netbeans.modules.form.adito.perstistencemanager.*;
import org.netbeans.modules.form.layoutdesign.LayoutModel;
import org.netbeans.modules.form.layoutsupport.LayoutSupportManager;
import org.openide.loaders.DataObject;
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
  public boolean canLoadForm(DataObject formObject) throws PersistenceException
  {
    return true;
  }

  @Override
  public synchronized void loadForm(DataObject formObject, FormModel formModel, List<Throwable> nonfatalErrors)
      throws PersistenceException
  {
    _loadForm(new APersistenceManagerInfo(formObject, formModel == null ? new FormModel() : formModel, nonfatalErrors));
  }

  @Override
  public void saveForm(DataObject pFormObject, FormModel pFormModel, List<Throwable> pNonfatalErrors)
      throws PersistenceException
  {
    // Save gibt es hier nicht. Das Model wird sowieso synchronisiert.
  }

  private void _loadForm(APersistenceManagerInfo pInfo) throws PersistenceException
  {
    FormModel formModel = pInfo.getFormModel();
    IPropertyPitProvider<?, ?, ?> modelRoot = pInfo.getModelRoot();
    try
    {
      AComponentInfo componentInfo = AComponentInfo.create(modelRoot, pInfo);
      if (componentInfo != null)
      {
        Class<?> formBaseClass = componentInfo.getComponentClass();
        formModel.setFormBaseClass(formBaseClass, DMHelper.getHandler(modelRoot));
        formModel.setName(componentInfo.getComponentName());
        // Force creation of the default instance in the correct L&F context
        BeanSupport.getDefaultInstance(formBaseClass);

        RADComponent topComp = formModel.getTopRADComponent();
        _loadComponent(pInfo, modelRoot, topComp, null);
        _copyValuesFromModelToComponent(topComp);
      }

      List<RADComponent> list = new ArrayList<>();
      List<IPropertyPitProvider<?, ?, ?>> others = NbAditoInterface.lookup(IAditoModelDataProvider.class).getOthers(modelRoot);
      for (IPropertyPitProvider<?, ?, ?> other : others)
      {
        RADComponent othersRadComp = _restoreComponent(pInfo, other, null);
        if (othersRadComp != null)
        {
          list.add(othersRadComp);
          _copyValuesFromModelToComponent(othersRadComp);
        }
      }
      RADComponent[] nonVisualComps = new RADComponent[list.size()];
      list.toArray(nonVisualComps);
      formModel.getModelContainer().initSubComponents(nonVisualComps);
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


  private void _loadComponent(APersistenceManagerInfo pInfo, IPropertyPitProvider<?, ?, ?> pModelComp, RADComponent pComponent,
                              RADComponent pParentComponent) throws PersistenceException
  {

    // if the loaded component is a visual component in a visual container,
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

    List<IPropertyPitProvider<?, ?, ?>> childModels = NbAditoInterface.lookup(IAditoModelDataProvider.class).getChildModels(pModelComp);
    List<RADComponent> list = new ArrayList<>();
    for (IPropertyPitProvider<?, ?, ?> childModel : childModels)
    {
      RADComponent newComp = _restoreComponent(pInfo, childModel, pComponent);
      if (newComp != null)
        list.add(newComp);
    }
    childComponents = new RADComponent[list.size()];
    list.toArray(childComponents);


    if (visualContainer != null)
    {
      Throwable layoutEx = null;
      boolean layoutInitialized = false;
      LayoutSupportManager layoutSupport = visualContainer.getLayoutSupport();

      if (convIndex == LAYOUT_NATURAL)
      {
        LayoutModel layoutModel = pInfo.getFormModel().getLayoutModel();
        Map<String, String> nameToIdMap = new HashMap<>();
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

    try
    {
      for (RADComponent childComponent : container.getSubBeans())
        _copyValuesFromModelToComponent(childComponent);
    }
    catch (IllegalAccessException | InvocationTargetException e)
    {
      e.printStackTrace();  // TODO: error-handling
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

  private RADComponent _restoreComponent(APersistenceManagerInfo pInfo, IPropertyPitProvider<?, ?, ?> pChildModel,
                                         RADComponent pParentComponent) throws PersistenceException
  {
    AComponentInfo componentInfo = AComponentInfo.create(pChildModel, pInfo);
    if (componentInfo == null)
    {
      new RuntimeException("null for: " + new PropertyPath(pChildModel.getPit().getOwnProperty()).asString()).printStackTrace();
      return null;
    }

    // create a new metacomponent
    RADComponent newComponent = null;
    switch (componentInfo.getModelPropProvider().getContainerType())
    {
      case NONE:
        if (FormUtils.isVisualizableClass(componentInfo.getComponentClass()))
          newComponent = new RADVisualComponent();
        else
          newComponent = new RADComponent();
        break;
      case VISUAL:
        newComponent = new RADVisualContainer();
        break;
      case NONVISUAL:
        if (FormUtils.isVisualizableClass(componentInfo.getComponentClass()))
          newComponent = new NonvisContainerRADVisualComponent();
        else
          newComponent = new NonvisContainerRADComponent();
        break;
      default:
        PersistenceException ex = new PersistenceException("Unknown component element: " + pChildModel.toString()); // NOI18N
        pInfo.getNonfatalErrors().add(ex);
        return null;
    }

    // initialize the metacomponent
    Throwable compEx = null;
    try
    {
      if (componentInfo.getComponentClass() == InvalidComponent.class)
        newComponent.setValid(false);

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


  private int _loadLayout(IPropertyPitProvider<?, ?, ?> pModelComp, LayoutSupportManager layoutSupport)
  {

    try
    {
      // TODO: lade layout abhängig von layout feld
      LayoutManager layout = _getPropertyInfo().createComponentInfo(pModelComp).createLayout();
      layoutSupport.getPrimaryContainer().setLayout(layout);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      // mach ma nix ...
    }
    return LAYOUT_ABSOLUTE;
  }

  private boolean _loadConstraints(IPropertyPitProvider<?, ?, ?> pModelComp, RADComponent pComponent, RADVisualContainer pParentComponent)
  {
    try
    {
      if (pComponent instanceof RADVisualComponent)
      {
        IFormComponentInfo formModelPropProvider = _getPropertyInfo().createComponentInfo(pModelComp);

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

  private static void _copyValuesFromModelToComponent(RADComponent pComponent)
      throws InvocationTargetException, IllegalAccessException
  {
    IPropertyPitProvider<?, ?, ?> model = pComponent.getARADComponentHandler().getModel();
    if (model == null)
      throw new IllegalStateException(pComponent.toString());

    IFormComponentInfoProvider compInfoProvider = NbAditoInterface.lookup(IFormComponentInfoProvider.class);
    IFormComponentInfo componentInfo = compInfoProvider.createComponentInfo(model);
    for (Map.Entry<String, Object> entry : componentInfo.getInitialValues().entrySet())
    {
      Node.Property radProperty = pComponent.getPropertyByName(entry.getKey());

      if (radProperty != null)
      {
        radProperty.setValue(entry.getValue());
      }

    }
  }

}
