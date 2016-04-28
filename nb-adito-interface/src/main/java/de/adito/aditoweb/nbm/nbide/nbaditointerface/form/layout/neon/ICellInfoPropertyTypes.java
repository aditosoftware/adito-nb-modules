package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.neon;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.common.IAditoLayoutPropertyType;

/**
 *  Definition der Eigenschaften eines CellDataModels.
 */
public interface ICellInfoPropertyTypes
{
  IAditoLayoutPropertyType<Integer> startX();
  IAditoLayoutPropertyType<Integer> startY();
  IAditoLayoutPropertyType<Integer> endX();
  IAditoLayoutPropertyType<Integer> endY();
  IAditoLayoutPropertyType<String> justify();
}
