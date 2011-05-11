package de.adito.aditoweb.nbm.nbide.nbaditointerface;

import org.openide.util.Lookup;

import java.awt.*;

/**
 * @author J. Boesl, 11.05.11
 */
public abstract class NetbeansAditoInterfaceProvider
{

  private static INetbeansAditoInterface provider;


  public static INetbeansAditoInterface getDefault()
  {
    if (provider == null)
    {
      provider = Lookup.getDefault().lookup(INetbeansAditoInterface.class);
      if (provider == null)
        provider = new _NetbeansAditoInterfaceProvider();
    }
    return provider;
  }


  private static class _NetbeansAditoInterfaceProvider implements INetbeansAditoInterface
  {
    @Override
    public IAditoAnchorLayoutComponentConstaints createAditoAnchoLayoutComponentConstraints(Rectangle pBounds, boolean pAnchorLeft, boolean pAnchorBottom, boolean pAnchorRight, boolean pAnchorTop, boolean pIsBordered)
    {
      return null;
    }

    @Override
    public Class getAditoAnchoLayoutClass()
    {
      return null;
    }
  }

}
