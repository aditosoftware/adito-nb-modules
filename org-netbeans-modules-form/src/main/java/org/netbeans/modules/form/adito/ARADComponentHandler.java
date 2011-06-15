package org.netbeans.modules.form.adito;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.model.IAditoModelDataProvider;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync.*;
import org.jetbrains.annotations.*;
import org.netbeans.modules.form.RADComponent;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataFolder;
import org.openide.nodes.*;

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
  private Sheet sheet;
  @Nullable
  private FileObject deleted;

//  @Nullable
//  public RADComponent getRadComponent()
//  {
//    return radComponent;
//  }

  public void setRadComponent(@NotNull RADComponent pRadComponent)
  {
    radComponent = pRadComponent;
    tryInit();
  }

  public void delete()
  {
    //FileObject modelFile = modelDataObject.getPrimaryFile();
    //
    //for (DataObject dataObject : DataObject.getRegistry().getModifiedSet())
    //{
    //  if (FileUtil.isParentOf(modelFile, dataObject.getPrimaryFile()))
    //  {
    //    CloneableOpenSupport openSupport = dataObject.getLookup().lookup(CloneableOpenSupport.class);
    //    if (!openSupport.close())
    //      throw new RuntimeException("user canceled");
    //  }
    //}
    //
    //
    //try
    //{
    //  IModelAccess modelAccess = DataAccessHelper.accessModel(modelDataObject.getPrimaryFile());
    //
    //  IModelAccess copy = DataAccessHelper.createModelAccess(EScheme.resolveScheme(modelAccess), modelAccess.getName());
    //  IFieldAccess<Object> copyField = copy.getParentAccess().getFieldAccess(copy.getName());
    //  ResultOfVerification resultOfVerification = copyField.setValue(modelAccess);
    //
    //  if (resultOfVerification.isError())
    //  {
    //    throw new RuntimeException(resultOfVerification.getException());
    //    //NotifyUtil.simpleError(resultOfVerification.getException());
    //    //return;
    //  }
    //  deleted = copy.getFileObject();
    //
    //  ArrayModelAccess arrayModelAccess = DataAccessHelper.accessModel(modelFile.getParent());
    //  ResultOfVerification removeResult = arrayModelAccess.remove(modelFile.getNameExt());
    //  if (resultOfVerification.isError())
    //    throw new RuntimeException(removeResult.getException()); // TODO: errorHandling
    //  _deinitialize(true);
    //}
    //catch (Exception e)
    //{
    //  throw new RuntimeException(e); // TODO: errorHandling
    //}
  }

  public void add()
  {
    RADComponent parentRadComponent = radComponent.getParentComponent();
    ARADComponentHandler parentRadHandler = parentRadComponent.getARADComponentHandler();

    IAditoModelDataProvider dataProvider = NbAditoInterface.lookup(IAditoModelDataProvider.class);
    FileObject createdOrRestored = dataProvider.createOrRestoreDataModel(parentRadHandler.getModelDataObject(),
                                                                         radComponent.getBeanClass(),
                                                                         UUID.randomUUID().toString(), deleted);
    setModelDataObject(DataFolder.findFolder(createdOrRestored));
    if (deleted != null)
      deleted = null;
  }

  @NotNull
  public String getName()
  {
    DataFolder mdo = getModelDataObject();
    if (mdo != null)
      return mdo.getName();
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
    sheet = null;
  }

  private void tryInit()
  {
    if (radComponent != null && modelDataObject != null && formDataBridge == null)
    {
      try
      {
        IAditoPropertyProvider aditoModelPropProvider =
            getPropertyInfo().createModelPropProvider(modelDataObject.getPrimaryFile());
        IAditoComponentDetailProvider componentDetailProvider =
            getPropertyInfo().getComponentDetailProvider(radComponent.getBeanClass());
        if (aditoModelPropProvider != null && componentDetailProvider != null)
        {
          formDataBridge = new FormDataBridge(radComponent, aditoModelPropProvider, componentDetailProvider);
          formDataBridge.registerListeners();
        }
      }
      catch (Exception e)
      {
        System.out.println("couldn't init. " + modelDataObject); // TODO: sout
      }
    }
  }

  //@Nullable
  //public DataFolder getModelDataObject()
  //{
  //  return modelDataObject;
  //}
  //
  //public void initRADComponent(@NotNull RADComponent pRADComponent) throws InvocationTargetException, IllegalAccessException
  //{
  //  if (radComponent != null)
  //    throw new RuntimeException("Can't init with: " + pRADComponent + ". Another component is already set: "
  //                                   + radComponent + ".");
  //  radComponent = pRADComponent;
  //
  //  _registerListeners();
  //}

  @NotNull
  public Node.PropertySet[] getPropertySets()
  {
    tryInit();
    if (sheet == null && formDataBridge != null)
      sheet = formDataBridge.getAditoModelPropProvider().createSheet();
    if (sheet != null)
      return sheet.toArray();
    return new Node.PropertySet[0];
  }

  private IAditoComponentInfoProvider getPropertyInfo()
  {
    return NbAditoInterface.lookup(IAditoComponentInfoProvider.class);
  }

}
