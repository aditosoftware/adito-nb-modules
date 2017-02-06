package org.netbeans.modules.form;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.model.IAditoModelDataProvider;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync.IFormComponentInfoProvider;
import de.adito.propertly.core.spi.IPropertyPitProvider;
import org.jetbrains.annotations.*;
import org.netbeans.modules.form.adito.DMHelper;
import org.netbeans.modules.form.adito.components.AditoMetaComponentCreatorSupport;
import org.netbeans.modules.form.layoutsupport.LayoutSupportManager;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

/**
 * Creates RADComponents from IPropertyPitProviders.
 *
 * @author j.boesl, 06.02.17
 */
public class AditoMetaComponentCreator
{

  private FormModel formModel;

  public AditoMetaComponentCreator(FormModel pFormModel)
  {
    formModel = pFormModel;
  }

  @NotNull
  public RADComponent createComponent(@NotNull IPropertyPitProvider<?, ?, ?> pModel,
                                      @Nullable Consumer<Exception> pExceptionHandler)
  {
    Exception ex = null;
    RADComponent radComponent;
    if (pExceptionHandler == null)
    {
      List<Exception> exceptions = new ArrayList<>();
      radComponent = _createComponent(pModel, exceptions::add);
      if (!exceptions.isEmpty())
        ex = exceptions.get(0);
    }
    else
      radComponent = _createComponent(pModel, pExceptionHandler);

    if (ex != null || radComponent == null)
      throw new RuntimeException("Component '" + pModel.getPit().getOwnProperty().getName() + "' could not be created.", ex);

    return radComponent;
  }

  private RADComponent _createComponent(@NotNull IPropertyPitProvider<?, ?, ?> pModel,
                                        @NotNull Consumer<Exception> pExceptionHandler)
  {
    try
    {
      Class<?> componentClass = AditoMetaComponentCreatorSupport.getComponentClass(pModel);
      RADComponent component = AditoMetaComponentCreatorSupport.createComponent(componentClass);
      component.initialize(formModel);
      component.initInstance(componentClass, DMHelper.getHandler(pModel));
      component.setStoredName(pModel.getPit().getOwnProperty().getName());
      component.setInModel(true);

      finishContainerInitialization(component, pExceptionHandler);

      return component;
    }
    catch (Exception pE)
    {
      pExceptionHandler.accept(pE);
      return null;
    }
  }

  public void finishContainerInitialization(@NotNull RADComponent component, @NotNull Consumer<Exception> pExceptionHandler)
  {
    if (!(component instanceof ComponentContainer))
      return;

    IPropertyPitProvider<?, ?, ?> model = component.getARADComponentHandler().getModel();
    assert model != null;
    RADVisualContainer visualContainer = component instanceof RADVisualContainer ? (RADVisualContainer) component : null;
    if (visualContainer != null)
    {
      visualContainer.setOldLayoutSupport(true);
      IFormComponentInfoProvider info = NbAditoInterface.lookup(IFormComponentInfoProvider.class);
      LayoutManager layout = info.createComponentInfo(model).createLayout();
      visualContainer.getLayoutSupport().getPrimaryContainer().setLayout(layout);
    }

    // load subcomponents
    IAditoModelDataProvider aditoModelDataProvider = NbAditoInterface.lookup(IAditoModelDataProvider.class);
    List<IPropertyPitProvider<?, ?, ?>> childModels = aditoModelDataProvider.getChildModels(model);
    List<RADComponent> list = new ArrayList<>();
    for (IPropertyPitProvider<?, ?, ?> childModel : childModels)
    {
      RADComponent newComp = _createComponent(childModel, pExceptionHandler);
      if (newComp != null)
        list.add(newComp);
    }

    // init layout
    ((ComponentContainer) component).initSubComponents(list.toArray(new RADComponent[list.size()]));

    if (visualContainer != null)
    {
      LayoutSupportManager layoutSupport = visualContainer.getLayoutSupport();

      try
      {
        // initialize layout support from restored code
        layoutSupport.prepareLayoutDelegate(true);
      }
      catch (Exception ex)
      {
        layoutSupport.setUnknownLayoutDelegate();
        pExceptionHandler.accept(ex);
      }
    }

    // copy values
    for (RADComponent childComponent : ((ComponentContainer) component).getSubBeans())
      childComponent.getARADComponentHandler().applyValuesFromAditoModel();
  }

}
