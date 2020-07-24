package de.adito.nbm.runconfig.spi;

import de.adito.nbm.runconfig.api.IRunConfig;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Provider, um eine andere Combobox-Repräsentation erstellen zu können
 *
 * @author w.glanzer, 24.12.2018
 */
public interface IActiveConfigComponentProvider
{

  String DISPLAY_NAME_SEPARATOR = "###";

  /**
   * @return Erstellt eine neue ComboBox, die RunConfigs aufnehmen und anzeigen kann.
   */
  @NotNull
  JComboBox<IRunConfig> createRunConfigCombobox();

}
