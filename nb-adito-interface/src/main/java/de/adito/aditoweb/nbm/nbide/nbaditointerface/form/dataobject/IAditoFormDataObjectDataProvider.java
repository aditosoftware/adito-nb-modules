package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.dataobject;

import org.openide.loaders.DataObject;

/**
 * @author J. Boesl, 26.09.12
 */
public interface IAditoFormDataObjectDataProvider
{

  IAditoFormDataObjectData get(DataObject pDataObject);

}
