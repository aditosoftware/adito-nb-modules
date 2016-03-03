package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.model;

import org.jetbrains.annotations.Nullable;

/**
 * @author T. Feldmann, 22.02.13
 */
public enum EModelFormType
{

  FRAME("swing"),
  NEON("neon"),
  UNDEFINED("!!UNDEFINED!!"),//Als Marker für die Stellen, wo kein Fileobjekt zur Verfügung steht.
  REPORT,
  APPLICATION,
  OTHER;

  private final String categoryName;

  EModelFormType(String pCategoryName)
  {
    categoryName = pCategoryName;
  }

  EModelFormType()
  {
    this(null);
  }

  @Nullable
  public String getPaletteCategoryName()
  {
    return categoryName;
  }

}
