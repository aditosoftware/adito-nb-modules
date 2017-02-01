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
import org.netbeans.modules.form.layoutsupport.LayoutSupportManager;
import org.openide.loaders.DataObject;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author J. Boesl, 19.01.11
 */
public class AditoPersistenceManager extends PersistenceManager
{

  private static final int LAYOUT_ABSOLUTE = 6;
  private static final int LAYOUT_UNKNOWN = -1;


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
  }


  private void _loadComponent(APersistenceManagerInfo pInfo, IPropertyPitProvider<?, ?, ?> pModelComp, RADComponent pComponent,
                              RADComponent pParentComponent) throws PersistenceException
  {

    // if the loaded component is a visual component in a visual container,
    // then load NB 3.1 layout constraints for it
    if (pComponent instanceof RADVisualComponent && pParentComponent instanceof RADVisualContainer)
      _loadConstraints(pModelComp, pComponent, (RADVisualContainer) pParentComponent);

    ComponentContainer container = // is this component a container?
        pComponent instanceof ComponentContainer ? (ComponentContainer) pComponent : null;
    if (container == null)
    { // this component is not a container
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

      if (convIndex >= 0)
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
      }

      if (!layoutInitialized)
        layoutSupport.setUnknownLayoutDelegate();

      if (layoutEx != null)
        layoutEx.printStackTrace(); // TODO: error-handling
    }
    else
    { // non-visual container (e.g. AWT menu)
      container.initSubComponents(childComponents);
    }

    for (RADComponent childComponent : container.getSubBeans())
      _copyValuesFromModelToComponent(childComponent);
  }

  private RADComponent _restoreComponent(APersistenceManagerInfo pInfo, IPropertyPitProvider<?, ?, ?> pChildModel,
                                         RADComponent pParentComponent) throws PersistenceException
  {
    AComponentInfo componentInfo = AComponentInfo.create(pChildModel, pInfo);
    if (componentInfo == null)
    {
      String modelPath = pChildModel == null ? null : new PropertyPath(pChildModel).asString();
      new RuntimeException("null for: " + modelPath).printStackTrace();
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
  {
    pComponent.getARADComponentHandler().applyValuesFromAditoModel();
  }

}
