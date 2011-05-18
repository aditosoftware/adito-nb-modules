package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync;

import org.openide.filesystems.FileObject;

/**
 * @author J. Boesl, 16.05.11
 */
public interface IAditoPropertyInfo
{

  IAditoComponentDetailProvider getMapper(FileObject pModelFo);

  IAditoComponentDetailProvider getMapper(Class<?> pBeanClass);

  IAditoPropertyProvider createAditoModelPropProvider(FileObject pModelFo);


}
