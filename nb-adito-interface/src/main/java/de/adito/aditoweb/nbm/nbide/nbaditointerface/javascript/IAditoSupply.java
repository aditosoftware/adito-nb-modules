package de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript;

import org.openide.filesystems.FileObject;

/**
 * @author d.poellath, 06.12.12
 */
public interface IAditoSupply
{
  <T> T getAditoProperty(FileObject pProcessFileObject, String pProperty, Class<T> pType);

  <T> void setAditoProperty(FileObject pProcessFileObject, String pPropName, T pStrings);
}
