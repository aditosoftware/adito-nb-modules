package org.netbeans.modules.form.adito;

import com.google.common.base.Objects;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.model.*;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync.*;
import de.adito.propertly.core.spi.IPropertyPitProvider;
import org.jetbrains.annotations.*;
import org.netbeans.modules.form.*;
import org.openide.filesystems.FileObject;
import org.openide.loaders.*;
import org.openide.nodes.Node;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author J. Boesl, 17.02.11
 */
public class ARADComponentHandler
{

  @Nullable
  private RADComponent radComponent;
  @Nullable
  private IPropertyPitProvider<?, ?, ?> model;
  @Nullable
  private FormDataBridge formDataBridge;


  public void setRadComponent(@NotNull RADComponent pRadComponent)
  {
    radComponent = pRadComponent;
    _tryInit();
  }

  public void added()
  {
    /**
     * Wenn model und formDataBridge nicht 'null' sind, dann wurde die Komponente im Model hinzugefügt und
     * zum FormModel synchronisiert. Muss also nicht mehr weiter behandelt werden.
     */
    if (_isBridgeValid())
      return;

    assert radComponent != null;
    RADComponent parentRadComponent = radComponent.getParentComponent();
    ARADComponentHandler parentRadHandler = parentRadComponent.getARADComponentHandler();

    IAditoModelDataProvider dataProvider = NbAditoInterface.lookup(IAditoModelDataProvider.class);
    IPropertyPitProvider<?, ?, ?> createdOrRestored;
    try
    {
      createdOrRestored = dataProvider.createDataModel(parentRadHandler.getModel(),
                                                       radComponent.getBeanClass(),
                                                       radComponent.getName());
    }
    catch (Exception e)
    {
      throw new EventCouldNotBeProcessedException(e);
    }

    setModel(createdOrRestored);
    assert model != null;
    radComponent.setName(model.getPit().getOwnProperty().getName());

    if (_isBridgeValid())
    {
      assert formDataBridge != null; // wird von _isBridgeValid() sicher gestellt.
      formDataBridge.newComponentAdded();
    }
  }

  public void nameIsAboutToChange(String pOldName, String pNewName) throws IllegalStateException
  {
    if (Objects.equal(pOldName, pNewName) || model == null)
      return;
    if (radComponent == null)
      throw new IllegalStateException("oldName: " + pOldName + ", newName: " + pNewName);

    // TODO: propertly
    //DataFolder.findFolder(model).rename(pNewName);
    if (!model.getPit().getOwnProperty().getName().equals(pNewName))
      model.getPit().getOwnProperty().rename(pNewName);
  }

  public void deleted()
  {
    IPropertyPitProvider<?, ?, ?> curModel = getModel();
    _deinitialize(true);
    if (curModel != null && curModel.getPit().getOwnProperty().isValid())
    {
      IAditoModelDataProvider dataProvider = NbAditoInterface.lookup(IAditoModelDataProvider.class);
      dataProvider.removeDataModel(curModel);
    }
  }

  public void childrenReordered()
  {
    if (!(radComponent instanceof ComponentContainer) || model == null)
      return;
    ComponentContainer container = (ComponentContainer) radComponent;

    final Map<String, Integer> namePositionMap = new HashMap<>();
    RADComponent[] subBeans = container.getSubBeans();
    for (int i = 0; i < subBeans.length; i++)
      namePositionMap.put(subBeans[i].getName(), i);

    IAditoModelDataProvider modelDataProvider = NbAditoInterface.lookup(IAditoModelDataProvider.class);
    boolean hasToReorder = modelDataProvider.reorder(model, (o1, o2) -> namePositionMap.get(o1) - namePositionMap.get(o2));

    if (_isBridgeValid() && hasToReorder)
    {
      assert formDataBridge != null; // wird von _isBridgeValid() sicher gestellt.
      formDataBridge.syncChildren();
    }
  }

  public IPropertyPitProvider<?, ?, ?> addChild(RADComponent pToCopy)
  {
    try
    {
      IAditoModelDataProvider modelDataProvider = NbAditoInterface.lookup(IAditoModelDataProvider.class);
      IFormComponentChildContainer defaultChildContainer = modelDataProvider.getChildContainer(model);
      if (defaultChildContainer == null)
      {
        assert model != null; // wenn defaultChildContainer nicht 'null' ist kann das auch nicht 'null' sein.
        IPropertyPitProvider<?, ?, ?> rootModel = this.model;
        while (rootModel.getPit().getParent() != null && defaultChildContainer == null)
        {
          rootModel = rootModel.getPit().getParent();
          defaultChildContainer = modelDataProvider.getChildContainer(rootModel);
        }
      }
      if (defaultChildContainer != null)
        return defaultChildContainer.copy(pToCopy.getARADComponentHandler().getModel());
      else
        throw new RuntimeException(); // TODO: exceptionHandling
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);  // TODO: exceptionHandling
    }
  }

  public void applyValuesFromAditoModel()
  {
    if (_isBridgeValid())
    {
      assert formDataBridge != null;
      formDataBridge.copyValuesFromAdito();
    }
  }

  /**
   * Fügt mehrere Komponenten dem Besitzer dieses Handlers hinzu.
   *
   * @param pComponents Liste mit Komponenten die einem Container hinzugefügt werden sollen.
   */
  public void addChildren(final List<RADComponent> pComponents)
  {
    List<IPropertyPitProvider<?, ?, ?>> ppps = pComponents.stream().map(this::addChild).collect(Collectors.toList());
    IAditoModelDataProvider dataProvider = NbAditoInterface.lookup(IAditoModelDataProvider.class);
    dataProvider.calcDropLocation(ppps);
  }

  public boolean canMove(RADComponent pTarget)
  {
    IAditoModelDataProvider dataProvider = NbAditoInterface.lookup(IAditoModelDataProvider.class);
    IFormComponentChildContainer childContainer = dataProvider.getChildContainer(
        pTarget.getARADComponentHandler().getModel());
    return childContainer != null && childContainer.canMove(model);
  }

  public boolean canAdd(Class pCls)
  {
    IAditoModelDataProvider dataProvider = NbAditoInterface.lookup(IAditoModelDataProvider.class);
    return dataProvider.canAdd(getModel(), pCls);
  }

  public void move(RADComponent pTarget, Node.Property[] pProperties)
  {
    IAditoModelDataProvider dataProvider = NbAditoInterface.lookup(IAditoModelDataProvider.class);
    IFormComponentChildContainer childContainer =
        dataProvider.getChildContainer(pTarget.getARADComponentHandler().getModel());
    if (pProperties != null)
    {
      for (Node.Property property : pProperties)
        try
        {
          formDataBridge.alignAditoToFormProp(property);
        }
        catch (InvocationTargetException e)
        {
          // skip
        }
    }
    if (childContainer != null)
      childContainer.moveDataModel(model);
  }

  /**
   * Fügt Komponenten einem Frame hinzu aus dem sie vorher ausgeschnitten wurden.
   *
   * @param pComponents Komponenten die dem selben Frame wieder hinzugefügt werden.
   */
  public void reInsert(final List<RADComponent> pComponents)
  {
    IAditoModelDataProvider dataProvider = NbAditoInterface.lookup(IAditoModelDataProvider.class);
    IFormComponentChildContainer childContainer = dataProvider.getChildContainer(getModel());
    if (childContainer != null)
    {
      List<String> names = new ArrayList<>();
      List<IPropertyPitProvider<?, ?, ?>> ppps = new ArrayList<>();
      for (RADComponent c : pComponents)
      {
        ARADComponentHandler componentHandler = c.getARADComponentHandler();
        names.add(componentHandler.getModel().getPit().getOwnProperty().getName());
        ppps.add(0, componentHandler.getModel());
      }

      dataProvider.calcDropLocation(ppps);

      for (RADComponent c : pComponents)
        c.getARADComponentHandler().move(radComponent, null);

      ppps.clear();
      for (IPropertyPitProvider<?, ?, ?> ppp : childContainer.getAllChildren())
        if (names.contains(ppp.getPit().getOwnProperty().getName()))
          ppps.add(ppp);
      dataProvider.toFront(getModel(), ppps);
    }
  }

  @NotNull
  public String getName(Class pBeanClass)
  {
    if (model != null)
      return model.getPit().getOwnProperty().getName();

    IFormComponentInfoProvider compInfoProvider = NbAditoInterface.lookup(IFormComponentInfoProvider.class);
    IFormComponentPropertyMapping propertyMapping = compInfoProvider.getFormPropertyMapping(pBeanClass);
    FileObject configFile = propertyMapping.getPaletteConfigItem();
    if (configFile != null)
    {
      DataObject dataObject;
      try
      {
        dataObject = DataObject.find(configFile);
      }
      catch (DataObjectNotFoundException e)
      {
        dataObject = null;
      }
      Object displayName = dataObject == null ? null : dataObject.getNodeDelegate().getDisplayName();
      if (displayName != null)
      {
        assert radComponent != null;
        return DMHelper.getFreeName(radComponent.getFormModel(), displayName.toString());
      }
    }
    return UUID.randomUUID().toString();
  }

  @Nullable
  public IPropertyPitProvider<?, ?, ?> getModel()
  {
    return model;
  }

  public void setModel(@NotNull IPropertyPitProvider<?, ?, ?> pModel)
  {
    model = pModel;
    _tryInit();
  }

  public void layoutPropertiesChanged(String pPropertyName)
  {
    if (_isBridgeValid())
    {
      assert formDataBridge != null;
      formDataBridge.layoutPropertiesChanged(pPropertyName);
    }
  }

  public void deinitialize()
  {
    _deinitialize(false);
  }

  private void _deinitialize(boolean pPartly)
  {
    if (formDataBridge != null)
      formDataBridge.unregisterAll();
    if (!pPartly)
      radComponent = null;
    model = null;
    formDataBridge = null;
  }

  private boolean _isBridgeValid()
  {
    return formDataBridge != null && model != null && model.getPit().isValid();
  }

  private void _tryInit()
  {
    if (radComponent != null && model != null && formDataBridge == null)
    {
      try
      {
        IFormComponentInfoProvider compInfoProvider = NbAditoInterface.lookup(IFormComponentInfoProvider.class);
        IFormComponentInfo componentInfo = compInfoProvider.createComponentInfo(model);
        IFormComponentPropertyMapping propertyMapping = compInfoProvider.getFormPropertyMapping(
            radComponent.getBeanClass());
        if (componentInfo != null && propertyMapping != null)
        {
          formDataBridge = new FormDataBridge(radComponent, componentInfo);
          formDataBridge.registerListeners();
        }
      }
      catch (Exception e)
      {
        throw new RuntimeException("couldn't init. " + model, e);
      }
    }
  }

}
