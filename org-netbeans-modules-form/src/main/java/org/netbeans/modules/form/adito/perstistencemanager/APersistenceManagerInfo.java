package org.netbeans.modules.form.adito.perstistencemanager;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.model.IAditoModelDataProvider;
import de.adito.propertly.core.spi.IPropertyPitProvider;
import org.netbeans.modules.form.*;
import org.openide.loaders.DataObject;

import java.util.*;

/**
 * Klasse mit Daten über den aktuellen Vorgang.
 */
public class APersistenceManagerInfo
{
  private DataObject formObject;
  private FormModel formModel;
  private List<Throwable> nonfatalErrors;
  private IPropertyPitProvider<?, ?, ?> modelRoot;

  private Map<String, RADComponent> loadedComponents;


  public APersistenceManagerInfo(DataObject pFormObject, FormModel pFormModel, List<Throwable> pNonfatalErrors)
  {
    formObject = pFormObject;
    formModel = pFormModel;
    nonfatalErrors = pNonfatalErrors;
  }

  public DataObject getFormObject()
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

  public IPropertyPitProvider<?, ?, ?> getModelRoot()
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
