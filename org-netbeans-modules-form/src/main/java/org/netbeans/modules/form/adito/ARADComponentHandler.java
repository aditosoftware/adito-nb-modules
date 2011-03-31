package org.netbeans.modules.form.adito;

import de.adito.aditoweb.core.util.debug.Debug;
import de.adito.aditoweb.designer.filetype.PropertiesCookie;
import de.adito.aditoweb.filesystem.datamodelfs.access.DataAccessHelper;
import de.adito.aditoweb.filesystem.datamodelfs.access.mechanics.array.IArrayAccess;
import de.adito.aditoweb.filesystem.datamodelfs.access.mechanics.field.IFieldAccess;
import de.adito.aditoweb.filesystem.datamodelfs.access.mechanics.model.IModelAccess;
import de.adito.aditoweb.filesystem.datamodelfs.access.model.FieldConst;
import de.adito.aditoweb.filesystem.datamodelfs.access.verification.ResultOfVerification;
import org.jetbrains.annotations.*;
import org.netbeans.modules.form.RADComponent;
import org.netbeans.modules.form.adito.mapping.EModelComponentMapping;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataFolder;
import org.openide.nodes.*;

import java.io.IOException;
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
    try
    {
      modelDataObject.delete();
    }
    catch (IOException e)
    {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
  }

  public void add()
  {
    Debug.write("added", radComponent); // DEBUG: remove it!

    //Debug.write("setParentRadComponent", radComponent); // DEBUG: remove it!
    RADComponent parentRadComponent = radComponent.getParentComponent();
    if (modelDataObject == null)
    {
      ARADComponentHandler parentRadHandler = parentRadComponent.getARADComponentHandler();
      IFieldAccess<IArrayAccess> childField = FieldConst.CHILDDATAMODELS.accessField(
          parentRadHandler.getModelDataObject().getPrimaryFile());

      EModelComponentMapping modelComponentMapping = EModelComponentMapping.get(radComponent);
      IModelAccess modelAccess = DataAccessHelper.createModelAccess(modelComponentMapping.getModelAccessType());

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
