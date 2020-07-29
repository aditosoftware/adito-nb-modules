package de.adito.nbm.blueprints.api;

import org.jetbrains.annotations.NotNull;

/**
 * Contains a single document, that a blueprint can create.
 * Those are not compiled and contain placeholders for parameter values.
 *
 * @author w.glanzer, 06.07.2020
 */
public interface IBlueprintPreset
{

  /**
   * @return the data as XML document
   */
  @NotNull
  String getXMLContent();

}
