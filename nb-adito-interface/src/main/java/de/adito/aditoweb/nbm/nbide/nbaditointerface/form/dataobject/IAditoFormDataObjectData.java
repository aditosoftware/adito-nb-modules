package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.dataobject;

import org.netbeans.api.actions.Openable;
import org.openide.awt.UndoRedo;
import org.openide.text.DataEditorSupport;

/**
 * @author J. Boesl, 27.08.12
 */
public interface IAditoFormDataObjectData
{

  DataEditorSupport getFormEditorSupport();

  Openable getOpenable();

  UndoRedo.Manager getUndoRedoManager();

  void resetForm();

}
