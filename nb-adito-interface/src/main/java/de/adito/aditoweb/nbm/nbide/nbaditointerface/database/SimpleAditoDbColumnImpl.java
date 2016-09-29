package de.adito.aditoweb.nbm.nbide.nbaditointerface.database;

/**
 * @author j.boesl, 26.09.16
 */
public class SimpleAditoDbColumnImpl implements IAditoDbColumn
{
  private String name;
  private boolean nullable;
  private boolean primaryKey;
  private int scale;
  private int size;
  private int type;
  private boolean unique;
  private boolean index;
  private String defVal;

  public SimpleAditoDbColumnImpl(String pName, boolean pNullable, boolean pPrimaryKey, int pScale, int pSize, int pType,
                                 boolean pUnique, boolean pIndex, String pDefVal)
  {
    name = pName;
    nullable = pNullable;
    primaryKey = pPrimaryKey;
    scale = pScale;
    size = pSize;
    type = pType;
    unique = pUnique;
    index = pIndex;
    defVal = pDefVal;
  }

  @Override
  public String getName()
  {
    return name;
  }

  @Override
  public boolean isNullable()
  {
    return nullable;
  }

  @Override
  public boolean isPrimaryKey()
  {
    return primaryKey;
  }

  @Override
  public int getScale()
  {
    return scale;
  }

  @Override
  public int getSize()
  {
    return size;
  }

  @Override
  public int getType()
  {
    return type;
  }

  @Override
  public boolean isUnique()
  {
    return unique;
  }

  @Override
  public boolean isIndex()
  {
    return index;
  }

  @Override
  public String getDefVal()
  {
    return defVal;
  }
}
