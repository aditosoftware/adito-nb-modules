package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.dataobject;

import org.netbeans.api.actions.Openable;
import org.openide.awt.UndoRedo;
import org.openide.cookies.EditorCookie;
import org.openide.text.DataEditorSupport;

/**
 * @author J. Boesl, 27.08.12
 */
public interface IAditoFormDataObjectData
{

  DataEditorSupport getFormEditorSupport();

  EditorCookie.Observable getObservableEditorCookie();

  Openable getOpenable();

  UndoRedo.Manager getUndoRedoManager();

  void resetForm();

}
