package org.netbeans.modules.form.adito.dataobject;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.dataobject.*;
import org.openide.loaders.DataObject;
import org.openide.util.lookup.ServiceProvider;

/**
 * @author J. Boesl, 26.09.12
 */
@ServiceProvider(service = IAditoFormDataObjectDataProvider.class)
public class AditoFormDataObjectDataProviderImpl implements IAditoFormDataObjectDataProvider
{

  @Override
  public IAditoFormDataObjectData get(DataObject pDataObject)
  {
    return new AditoFormDataObjectDataImpl(pDataObject);
  }
}
