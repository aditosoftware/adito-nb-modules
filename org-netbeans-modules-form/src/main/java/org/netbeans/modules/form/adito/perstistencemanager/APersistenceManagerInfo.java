package org.netbeans.modules.form.adito.perstistencemanager;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.model.IAditoModelDataProvider;
import org.netbeans.modules.form.*;
import org.openide.filesystems.FileObject;

import java.util.*;

/**
 * Klasse mit Daten über den aktuellen Vorgang.
 */
public class APersistenceManagerInfo
{
  private FormDataObject formObject;
  private FormModel formModel;
  private List<Throwable> nonfatalErrors;
  private FileObject modelRoot;

  private Map<String, RADComponent> loadedComponents;


  public APersistenceManagerInfo(FormDataObject pFormObject, FormModel pFormModel, List<Throwable> pNonfatalErrors)
  {
    formObject = pFormObject;
    formModel = pFormModel;
    nonfatalErrors = pNonfatalErrors;
  }

  public FormDataObject getFormObject()
  {
    return formObject;
  }

  public FormModel getFormModel()
  {
    return formModel;
  }

  public List<Throwable> getNonfatalErrors()
  {
    return nonfatalErrors;
  }

  public FileObject getModelRoot()
  {
    if (modelRoot == null)
    {
      IAditoModelDataProvider aditoModelDataProvider = NbAditoInterface.lookup(IAditoModelDataProvider.class);
      modelRoot = aditoModelDataProvider.loadModel(formObject.getPrimaryFile());
    }
    return modelRoot;
  }

  public Map<String, RADComponent> getComponentsMap()
  {
    if (loadedComponents == null)
      loadedComponents = new HashMap<String, RADComponent>(50);
    return loadedComponents;
  }
}
