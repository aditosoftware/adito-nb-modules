package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.model;

import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;

/**
 * Stellt Daten über Modelle zur Verfügung.
 *
 * @author J. Boesl, 16.05.11
 */
public interface IAditoModelDataProvider
{

  Node getBaseNode(DataObject pFormDataObject);

  FileObject loadModel(FileObject pAodFile);

  FileObject getChildDataModels(FileObject pFileObject);

}
