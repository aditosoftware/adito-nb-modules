package org.netbeans.modules.form.adito;

import de.adito.aditoweb.designer.filetype.PropertiesCookie;
import de.adito.aditoweb.filesystem.datamodelfs.access.DataAccessHelper;
import de.adito.aditoweb.filesystem.datamodelfs.access.mechanics.*;
import de.adito.aditoweb.filesystem.datamodelfs.access.model.FieldConst;
import de.adito.aditoweb.filesystem.datamodelfs.access.verification.ResultOfVerification;
import de.adito.aditoweb.filesystem.datamodelfs.resolver.schema.EScheme;
import org.jetbrains.annotations.*;
import org.netbeans.modules.form.RADComponent;
import org.netbeans.modules.form.adito.mapping.EModelComponentMapping;
import org.openide.filesystems.*;
import org.openide.loaders.*;
import org.openide.nodes.*;
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
    FileObject modelFile = modelDataObject.getPrimaryFile();

    for (DataObject dataObject : DataObject.getRegistry().getModifiedSet())
    {
      if (FileUtil.isParentOf(modelFile, dataObject.getPrimaryFile()))
      {
        CloneableOpenSupport openSupport = dataObject.getLookup().lookup(CloneableOpenSupport.class);
        if (!openSupport.close())
          throw new RuntimeException("user canceled");
      }
    }


    try
    {
      IModelAccess modelAccess = DataAccessHelper.accessModel(modelDataObject.getPrimaryFile());

      IModelAccess copy = DataAccessHelper.createModelAccess(EScheme.resolveScheme(modelAccess), modelAccess.getName());
      IFieldAccess<Object> copyField = copy.getParentAccess().getFieldAccess(copy.getName());
      ResultOfVerification resultOfVerification = copyField.setValue(modelAccess);

      if (resultOfVerification.isError())
      {
        throw new RuntimeException(resultOfVerification.getException());
        //NotifyUtil.simpleError(resultOfVerification.getException());
        //return;
      }
      deleted = copy.getFileObject();

      ArrayModelAccess arrayModelAccess = DataAccessHelper.accessModel(modelFile.getParent());
      ResultOfVerification removeResult = arrayModelAccess.remove(modelFile.getNameExt());
      if (resultOfVerification.isError())
        throw new RuntimeException(removeResult.getException()); // TODO: errorHandling
      _deinitialize(true);
    }
    catch (Exception e)
    {
      throw new RuntimeException(e); // TODO: errorHandling
    }
  }

  public void add()
  {
    if (modelDataObject == null)
    {
      RADComponent parentRadComponent = radComponent.getParentComponent();
      ARADComponentHandler parentRadHandler = parentRadComponent.getARADComponentHandler();
      IFieldAccess<ArrayModelAccess> childField = FieldConst.CHILDDATAMODELS.accessField(
          parentRadHandler.getModelDataObject().getPrimaryFile());

      if (deleted != null)
      {
        IModelAccess modelAccess = DataAccessHelper.accessModel(deleted);
        ResultOfVerification addResult = childField.getValue().add(modelAccess);
        if (addResult.isError())
          throw new RuntimeException(addResult.getException()); // TODO: errorHandling
        else
        {
          FileObject addedFo = childField.getValue().getFileObject().getFileObject(modelAccess.getName());
          setModelDataObject(DataFolder.findFolder(addedFo));
          deleted = null;
        }
      }
      else
      {
        EModelComponentMapping modelComponentMapping = EModelComponentMapping.get(radComponent);
        IModelAccess modelAccess = DataAccessHelper.createModelAccess(modelComponentMapping.getScheme());

        ResultOfVerification addResult = childField.getValue().add(modelAccess);
        if (addResult.isError())
          throw new RuntimeException(addResult.getException()); // TODO: errorHandling
        else
        {
          FileObject addedFo = childField.getValue().getFileObject().getFileObject(modelAccess.getName());
          setModelDataObject(DataFolder.findFolder(addedFo));
        }
      }
    }
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

  public IFormDataInfo getFormDataInfo()
  {
    return formDataBridge.getFormDataInfo();
  }

  public void layoutPropertiesChanged()
  {
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
      formDataBridge = new FormDataBridge(radComponent, modelDataObject);
      formDataBridge.registerListeners();
    }
  }

  //  @Nullable
//  public DataFolder getModelDataObject()
//  {
//    return modelDataObject;
//  }
//
//  public void initRADComponent(@NotNull RADComponent pRADComponent) throws InvocationTargetException, IllegalAccessException
//  {
//    if (radComponent != null)
//      throw new RuntimeException("Can't init with: " + pRADComponent + ". Another component is already set: "
//                                     + radComponent + ".");
//    radComponent = pRADComponent;
//
//    _registerListeners();
//  }

  @NotNull
  public Node.PropertySet[] getPropertySets()
  {
    if (sheet == null)
      sheet = _createPropertySheet();
    if (sheet != null)
      return sheet.toArray();
    return new Node.PropertySet[0];
  }

  @Nullable
  private Sheet _createPropertySheet()
  {
    assert modelDataObject != null;

    PropertiesCookie propsCookie = modelDataObject.getCookie(PropertiesCookie.class);
    if (propsCookie == null)
      return null;
    Sheet propSheet = new Sheet();
    propsCookie.applyAditoPropertiesSync(propSheet);
    return propSheet;
  }

}
