package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.dataobject;

import org.openide.awt.UndoRedo;
import org.openide.cookies.OpenCookie;
import org.openide.text.DataEditorSupport;

/**
 * @author J. Boesl, 27.08.12
 */
public interface IAditoFormDataObjectData
{

  DataEditorSupport getFormEditorSupport();

  OpenCookie getOpenCookie();

  UndoRedo.Manager getUndoRedoManager();

  void resetForm();

}
