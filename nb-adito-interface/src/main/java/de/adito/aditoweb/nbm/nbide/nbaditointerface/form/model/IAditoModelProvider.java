package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.model;

import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;

/**
 * @author J. Boesl, 16.05.11
 */
public interface IAditoModelProvider
{

  static final String CHILDDATAMODELS = "childDataModels";
  static final String X = "x";
  static final String Y = "y";
  static final String WIDTH = "width";
  static final String HEIGHT = "height";


  Node getBaseNode(DataObject pFormDataObject);

  FileObject loadModel(FileObject pAodFile);

}
