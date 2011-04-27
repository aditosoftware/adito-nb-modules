package org.netbeans.modules.form.adito;

import de.adito.aditoweb.core.util.debug.Debug;
import de.adito.aditoweb.designer.filetype.PropertiesCookie;
import de.adito.aditoweb.filesystem.datamodelfs.access.DataAccessHelper;
import de.adito.aditoweb.filesystem.datamodelfs.access.mechanics.array.IArrayAccess;
import de.adito.aditoweb.filesystem.datamodelfs.access.mechanics.field.IFieldAccess;
import de.adito.aditoweb.filesystem.datamodelfs.access.mechanics.model.*;
import de.adito.aditoweb.filesystem.datamodelfs.access.model.FieldConst;
import de.adito.aditoweb.filesystem.datamodelfs.access.verification.ResultOfVerification;
import org.jetbrains.annotations.*;
import org.netbeans.modules.form.RADComponent;
import org.netbeans.modules.form.adito.mapping.EModelComponentMapping;
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
    Debug.write("deleted", radComponent); // DEBUG: remove it!

    FileObject modelFile = modelDataObject.getPrimaryFile();

    try
    {
//      IArrayAccess<IModelAccess> arrayAccess = DataAccessHelper.createArrayAccess();
//      arrayAccess.add(DataAccessHelper.<IModelAccess>accessModel(modelFile));
//      deleted = arrayAccess.getFileObject().getFileObject(modelFile.getName());
//      Debug.write(deleted.getChildren()); // DEBUG: remove it!


//      FileSystem memoryFileSystem = FileUtil.createMemoryFileSystem();
//      FileObject folder = memoryFileSystem.getRoot().createFolder(modelFile.getNameExt());
//
//      FieldAccessUtil.setModelValue(memoryFileSystem.getRoot(), DataAccessHelper.<IModelAccess>accessModel(modelFile));
//      Debug.write(memoryFileSystem.getRoot().getChildren()); // DEBUG: remove it!
    }
    catch (Exception e)
    {
      throw new RuntimeException(e); // TODO: errorHandling
    }
    ArrayModelAccess arrayModelAccess = DataAccessHelper.accessModel(modelFile.getParent());
    ResultOfVerification removeResult = arrayModelAccess.remove(DataAccessHelper.<IModelAccess>accessModel(modelFile));
    if (removeResult.getException() != null)
      throw new RuntimeException(removeResult.getException()); // TODO: errorHandling
  }

  public void add()
  {
    Debug.write("added", radComponent); // DEBUG: remove it!

    //Debug.write("setParentRadComponent", radComponent); // DEBUG: remove it!
    if (modelDataObject == null)
    {
      RADComponent parentRadComponent = radComponent.getParentComponent();
      ARADComponentHandler parentRadHandler = parentRadComponent.getARADComponentHandler();
      IFieldAccess<IArrayAccess> childField = FieldConst.CHILDDATAMODELS.accessField(
          parentRadHandler.getModelDataObject().getPrimaryFile());

      EModelComponentMapping modelComponentMapping = EModelComponentMapping.get(radComponent);
      IModelAccess modelAccess = DataAccessHelper.createModelAccess(modelComponentMapping.getScheme());

      ResultOfVerification addResult = childField.getValue().add(modelAccess);
      if (addResult.getException() != null)
        throw new RuntimeException(addResult.getException()); // TODO: errorHandling
      else
      {
        FileObject addedFo = childField.getValue().getFileObject().getFileObject(modelAccess.getName());
        setModelDataObject(DataFolder.findFolder(addedFo));
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
    if (formDataBridge != null)
      formDataBridge.unregisterAll();
    radComponent = null;
    modelDataObject = null;
    formDataBridge = null;
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
