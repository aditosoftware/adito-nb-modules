package org.netbeans.core.output2;

import java.beans.PropertyChangeListener;
import org.openide.windows.*;

/**
 * Listener-Erweiterung des InputOutput
 *
 * @author T. Feldmann, 03.05.13
 */
public interface InputOutputExt extends InputOutput
{
  /*
   Listener auf ein InputOutput-Fenster
   Gibt R�ckmeldung wenn Ausgabefenster geschlossen wird
   */
  abstract public void addPropertyChangeListener( PropertyChangeListener l );

  abstract public void removePropertyChangeListener( PropertyChangeListener l );

}
