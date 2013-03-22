package de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript;

import org.netbeans.modules.parsing.spi.Parser;
import org.openide.filesystems.FileObject;

import java.util.List;

/**
 * @author d.poellath, 06.12.12
 */
public interface IJsHintSupply
    extends IAditoSupply
{
  public String getDescription(String pId);

  public String getDisplayName(String pId);

  public List<Parser.Result> findProcessWithMethod(FileObject pFileObject);

  public String getAditoLibName(FileObject pFileObject);

  public boolean isMethodDeclaredInAditoProcess(String pParseSource, String pCallName);   //FileObject pJavaScriptFileObject

}
