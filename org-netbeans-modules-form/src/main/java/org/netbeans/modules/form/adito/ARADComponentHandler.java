package org.netbeans.modules.form.adito;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.model.IAditoModelDataProvider;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync.*;
import org.jetbrains.annotations.*;
import org.netbeans.modules.form.RADComponent;
import org.openide.filesystems.*;
import org.openide.loaders.*;
import org.openide.windows.CloneableOpenSupport;

import java.util.UUID;

/**
 * @author J. Boesl, 17.02.11
 */
public class ARADComponentHandler
{

  @Nullable
  private RADComponent radComponent;
  @Nullable
  private DataFolder modelDataObject;
  @Nullable
  private FormDataBridge formDataBridge;
  @Nullable
  private FileObject deleted;


  public void setRadComponent(@NotNull RADComponent pRadComponent)
  {
    radComponent = pRadComponent;
    tryInit();
  }

  public void add()
  {
    RADComponent parentRadComponent = radComponent.getParentComponent();
    ARADComponentHandler parentRadHandler = parentRadComponent.getARADComponentHandler();

    IAditoModelDataProvider dataProvider = NbAditoInterface.lookup(IAditoModelDataProvider.class);
    FileObject createdOrRestored = dataProvider.createOrRestoreDataModel(parentRadHandler.getModelDataObject(),
                                                                         radComponent.getBeanClass(),
                                                                         radComponent.getName(), deleted);
    setModelDataObject(DataFolder.findFolder(createdOrRestored));
    radComponent.setName(modelDataObject.getName());
    if (deleted != null)
      deleted = null;
  }

  public void delete()
  {
    FileObject modelFile = modelDataObject.getPrimaryFile();

    for (DataObject dataObject : DataObject.getRegistry().getModifiedSet())
    {
      if (FileUtil.isParentOf(modelFile, dataObject.getPrimaryFile()))
      {
        CloneableOpenSupport openSupport = dataObject.getLookup().lookup(CloneableOpenSupport.class);
        if (!openSupport.close())
          throw new RuntimeException("user canceled"); // TODO
      }
    }

    DataFolder modelDataObject = getModelDataObject();
    _deinitialize(true);
    IAditoModelDataProvider dataProvider = NbAditoInterface.lookup(IAditoModelDataProvider.class);
    deleted = dataProvider.removeDataModel(modelDataObject);
  }

  @NotNull
  public String getName(Class pBeanClass)
  {
    DataFolder mdo = getModelDataObject();
    if (mdo != null)
      return mdo.getName();
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
        return displayName.toString();
    }
    return UUID.randomUUID().toString();
  }

  @Nullable
  public DataFolder getModelDataObject()
  {
    return modelDataObject;
  }

  public void setModelDataObject(@NotNull DataFolder pModelDataObject)
  {
    modelDataObject = pModelDataObject;
    tryInit();
  }

  public void layoutPropertiesChanged()
  {
    if (formDataBridge != null)
      formDataBridge.layoutPropertiesChanged();
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
    modelDataObject = null;
    formDataBridge = null;
  }

  private void tryInit()
  {
    if (radComponent != null && modelDataObject != null && formDataBridge == null)
    {
      try
      {
        IFormComponentInfoProvider compInfoProvider = NbAditoInterface.lookup(IFormComponentInfoProvider.class);
        IFormComponentInfo componentInfo = compInfoProvider.createComponentInfo(modelDataObject);
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
        throw new RuntimeException("couldn't init. " + modelDataObject, e);
      }
    }
  }

}
