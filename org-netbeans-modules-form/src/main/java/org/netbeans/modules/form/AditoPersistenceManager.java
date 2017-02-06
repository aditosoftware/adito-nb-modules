package org.netbeans.modules.form;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.model.IAditoModelDataProvider;
import de.adito.propertly.core.spi.IPropertyPitProvider;
import org.netbeans.modules.form.adito.AFormModelListener;
import org.openide.loaders.DataObject;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author J. Boesl, 19.01.11
 */
public class AditoPersistenceManager extends PersistenceManager
{

  @Override
  public boolean canLoadForm(DataObject formObject) throws PersistenceException
  {
    return true;
  }

  @Override
  public synchronized void loadForm(DataObject pFormObject, FormModel pFormModel, List<Throwable> pNonFatalErrors)
      throws PersistenceException
  {
    IAditoModelDataProvider aditoModelDataProvider = NbAditoInterface.lookup(IAditoModelDataProvider.class);
    IPropertyPitProvider<?, ?, ?> modelRoot = aditoModelDataProvider.loadModel(pFormObject.getPrimaryFile());
    try
    {
      Consumer<Exception> exceptionHandler = pNonFatalErrors::add;
      pFormModel.setFormBase(modelRoot, exceptionHandler);

      // Force creation of the default instance in the correct L&F context
      BeanSupport.getDefaultInstance(pFormModel.getTopRADComponent().getBeanClass());

      RADComponent topComp = pFormModel.getTopRADComponent();
      _copyValuesFromModelToComponent(topComp);

      List<RADComponent> list = new ArrayList<>();
      List<IPropertyPitProvider<?, ?, ?>> others = NbAditoInterface.lookup(IAditoModelDataProvider.class).getOthers(modelRoot);
      for (IPropertyPitProvider<?, ?, ?> other : others)
      {
        RADComponent othersRadComp = pFormModel.getComponentCreator().createComponent(other, exceptionHandler);
        list.add(othersRadComp);
        _copyValuesFromModelToComponent(othersRadComp);
      }
      RADComponent[] nonVisualComps = new RADComponent[list.size()];
      list.toArray(nonVisualComps);
      pFormModel.getModelContainer().initSubComponents(nonVisualComps);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    pFormModel.addFormModelListener(new AFormModelListener()
    {
      @Override
      protected void clearProperties(RADComponent pRADComponent)
      {
        pRADComponent.clearProperties();
      }
    });
  }

  @Override
  public void saveForm(DataObject pFormObject, FormModel pFormModel, List<Throwable> pNonfatalErrors)
      throws PersistenceException
  {
    // Save gibt es hier nicht. Das Model wird sowieso synchronisiert.
  }

  private static void _copyValuesFromModelToComponent(RADComponent pComponent)
  {
    pComponent.getARADComponentHandler().applyValuesFromAditoModel();
  }

}
