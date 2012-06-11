package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync;

import org.jetbrains.annotations.*;

/**
 * @author J. Boesl, 16.05.11
 */
public interface IFormComponentPropertyMapping
{

  @NotNull
  EContainerType getContainerType();

  Class<?> getComponentClass();

  String getAditoPropName(String pRadPropName);

  String getRadPropName(String pAditoPropName);

}
