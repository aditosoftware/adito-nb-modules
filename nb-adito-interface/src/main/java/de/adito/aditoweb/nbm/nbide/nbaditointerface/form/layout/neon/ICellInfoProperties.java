package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.neon;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.common.IAditoLayoutPropertyType;
import de.adito.propertly.core.common.PD;
import de.adito.propertly.core.spi.IPropertyDescription;

/**
 * todo Javadoc
 * @author Thomas Tasior 24.02.2016, 14:17
 */
public interface ICellInfoProperties
{


  IAditoLayoutPropertyType<Integer> startColumn();
  IAditoLayoutPropertyType<Integer> startRow();
  IAditoLayoutPropertyType<Integer> endColumn();
  IAditoLayoutPropertyType<Integer> endRow();
  //IAditoLayoutPropertyType<EJustify> justify(); todo In welches Modul??


}
