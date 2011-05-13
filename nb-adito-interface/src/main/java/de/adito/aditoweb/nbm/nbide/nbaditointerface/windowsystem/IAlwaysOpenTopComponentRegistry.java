package de.adito.aditoweb.nbm.nbide.nbaditointerface.windowsystem;

import org.openide.windows.TopComponent;

import java.util.Collection;

/**
 * Die Registrierung der Komponenten, die auch 'geöffnet' bleiben sollen, wenn die TopComponent geschlossen wird.
 * Im Detail: Für die RibbonTasks müssen TopComponents versteckt werden können. Verstecken geht dadurch, dass
 * Komponenten geschlossen werden, die Referenzen gespeichert werden und mittels dieser die Komponenten später wieder
 * geöffnet werden. Da einige interne Netbeans-APIs auf das Schliesen 'horchen' um dann bestimmte Resourcen frei zu
 * geben, werden für die hier registrierten Komponenten die close-Events verschluckt.
 *
 * @author J. Boesl, 11.11.10
 */
public interface IAlwaysOpenTopComponentRegistry
{

  /**
   * Registriert eine TopComponent. Für diese werden nun keine 'close'-Events mehr erzeugt.
   *
   * @param pTc die zu registrierende TopComonent.
   */
  public void registerTopComponent(TopComponent pTc);

  /**
   * Gibt eine TopComponent wieder frei. Wird die Komponente geschlossen bekommt dies die ganze Platform mit.
   *
   * @param pTc die zu registrierende TopComonent.
   */
  public void unregisterTopComponent(TopComponent pTc);

  /**
   * Gibt an, ob die zu Prüfende TopComponent hier schon eingetragen ist.
   *
   * @param pTc die zu Prüfende TopComponent.
   * @return ob's so ist.
   */
  public boolean contains(TopComponent pTc);

  /**
   * Liefert alle registrierten Komponenten.
   *
   * @return eine Collection mit den Registrierungen.
   */
  public Collection<TopComponent> getRegisteredTopComponents();

}
