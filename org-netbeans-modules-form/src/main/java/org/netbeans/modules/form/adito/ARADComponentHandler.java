package org.netbeans.modules.form.adito;

import com.google.common.base.Objects;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.model.IAditoModelDataProvider;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync.*;
import org.jetbrains.annotations.*;
import org.netbeans.modules.form.*;
import org.openide.filesystems.*;
import org.openide.loaders.*;
import org.openide.nodes.Node;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author J. Boesl, 17.02.11
 */
public class ARADComponentHandler
{

  @Nullable
  private RADComponent radComponent;
  @Nullable
  private FileObject modelFileObject;
  @Nullable
  private FormDataBridge formDataBridge;


  public void setRadComponent(@NotNull RADComponent pRadComponent)
  {
    radComponent = pRadComponent;
    tryInit();
  }

  public void added()
  {
    /**
     * Wenn modelFileObject und formDataBridge nicht 'null' sind, dann wurde die Komponente im Model hinzugefügt und
     * zum FormModel synchronisiert. Muss also nicht mehr weiter behandelt werden.
     */
    if (modelFileObject != null && formDataBridge != null)
      return;

    assert radComponent != null;
    RADComponent parentRadComponent = radComponent.getParentComponent();
    ARADComponentHandler parentRadHandler = parentRadComponent.getARADComponentHandler();

    IAditoModelDataProvider dataProvider = NbAditoInterface.lookup(IAditoModelDataProvider.class);
    FileObject createdOrRestored = dataProvider.createDataModel(parentRadHandler.getModelFileObject(),
                                                                radComponent.getBeanClass(),
                                                                radComponent.getName());
    setModelFileObject(createdOrRestored);
    assert modelFileObject != null;
    radComponent.setName(modelFileObject.getName());

    if (formDataBridge != null)
      formDataBridge.newComponentAdded();
  }

  public void nameIsAboutToChange(String pOldName, String pNewName) throws IllegalStateException
  {
    try
    {
      if (Objects.equal(pOldName, pNewName) || modelFileObject == null)
        return;
      if (radComponent == null)
        throw new IllegalStateException("oldName: " + pOldName + ", newName: " + pNewName);
      DataFolder.findFolder(modelFileObject).rename(pNewName);
    }
    catch (IOException e)
    {
      throw new IllegalArgumentException(e); // TODO: add details
    }
    catch (Exception e)
    {
      e.printStackTrace();  // TODO: notify exception
    }
  }

  public void deleted()
  {
    FileObject modelFo = getModelFileObject();
    _deinitialize(true);
    if (modelFo != null && modelFo.isValid())
    {
      IAditoModelDataProvider dataProvider = NbAditoInterface.lookup(IAditoModelDataProvider.class);
      dataProvider.removeDataModel(modelFo);
    }
  }

  public void childrenReordered()
  {
    if (!(radComponent instanceof ComponentContainer) || modelFileObject == null)
      return;
    ComponentContainer container = (ComponentContainer) radComponent;
    IAditoModelDataProvider modelDataProvider = NbAditoInterface.lookup(IAditoModelDataProvider.class);

    final Map<String, Integer> namePositionMap = new HashMap<>();
    RADComponent[] subBeans = container.getSubBeans();
    for (int i = 0; i < subBeans.length; i++)
      namePositionMap.put(subBeans[i].getName(), i);

    modelDataProvider.reorder(modelFileObject, new Comparator<String>()
    {
      @Override
      public int compare(String o1, String o2)
      {
        return namePositionMap.get(o1) - namePositionMap.get(o2);
      }
    });
  }

  public void addChild(RADComponent pToCopy)
  {
    try
    {
      IAditoModelDataProvider modelDataProvider = NbAditoInterface.lookup(IAditoModelDataProvider.class);
      FileObject defaultChildContainer = modelDataProvider.getDefaultChildContainer(modelFileObject);

      DataFolder target = DataFolder.findFolder(defaultChildContainer);
      DataObject source = DataObject.find(pToCopy.getARADComponentHandler().getModelFileObject());
      source.copy(target);
    }
    catch (IOException e)
    {
      e.printStackTrace();  // TODO: exceptionHandling
    }
  }

  public void move(RADComponent pTarget)
  {
    move(pTarget, null);
  }

  public void move(RADComponent pTarget, Node.Property[] pProperties)
  {
    IAditoModelDataProvider dataProvider = NbAditoInterface.lookup(IAditoModelDataProvider.class);
    FileObject defaultChildContainer = dataProvider.getDefaultChildContainer(
        pTarget.getARADComponentHandler().getModelFileObject());
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
    dataProvider.moveDataModel(modelFileObject, defaultChildContainer);
  }

  @NotNull
  public String getName(Class pBeanClass)
  {
    if (modelFileObject != null)
      return modelFileObject.getNameExt();
    FileObject configFile = FileUtil.getConfigFile("FormDesignerPalette/Adito/" + pBeanClass.getSimpleName()
        .toLowerCase() + ".palette_item");
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
  public FileObject getModelFileObject()
  {
    return modelFileObject;
  }

  public void setModelFileObject(@NotNull FileObject pModelFileObject)
  {
    modelFileObject = pModelFileObject;
    tryInit();
  }

  public void layoutPropertiesChanged(String pPropertyName)
  {
    if (formDataBridge != null)
      formDataBridge.layoutPropertiesChanged(pPropertyName);
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
    modelFileObject = null;
    formDataBridge = null;
  }

  private void tryInit()
  {
    if (radComponent != null && modelFileObject != null && formDataBridge == null)
    {
      try
      {
        IFormComponentInfoProvider compInfoProvider = NbAditoInterface.lookup(IFormComponentInfoProvider.class);
        IFormComponentInfo componentInfo = compInfoProvider.createComponentInfo(modelFileObject);
        IFormComponentPropertyMapping propertyMapping = compInfoProvider.getFormPropertyMapping(
            radComponent.getBeanClass());
        if (componentInfo != null && propertyMapping != null)
        {
          formDataBridge = new FormDataBridge(radComponent, componentInfo, propertyMapping);
          formDataBridge.registerListeners();
        }
      }
      catch (Exception e)
      {
        throw new RuntimeException("couldn't init. " + modelFileObject, e);
      }
    }
  }

}
