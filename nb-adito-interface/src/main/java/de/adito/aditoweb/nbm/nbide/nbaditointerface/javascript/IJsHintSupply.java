package de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript;

import org.openide.filesystems.FileObject;

/**
 * @author d.poellath, 06.12.12
 */
public interface IJsHintSupply
  extends IAditoSupply
{
  public String getDescription(String pId);

  public String getDisplayName(String pId);

  public String findProcessWithMethod(String pMethodName);

  public boolean isMethodDeclaredInAditoProcess(FileObject pJavaScriptFileObject, String pCallName);

}
