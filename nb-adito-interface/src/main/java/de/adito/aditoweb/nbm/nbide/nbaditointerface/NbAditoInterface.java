package de.adito.aditoweb.nbm.nbide.nbaditointerface;

import org.openide.util.Lookup;

/**
 * @author J. Boesl, 11.05.11
 */
public class NbAditoInterface
{

  private static final INetbeansAditoInterface PROVIDER;

  static
  {
    INetbeansAditoInterface prov = Lookup.getDefault().lookup(INetbeansAditoInterface.class);
    PROVIDER = prov == null ? new _NetbeansAditoInterfaceProvider() : prov;
  }

  private NbAditoInterface()
  {
  }

  private static INetbeansAditoInterface getDefault()
  {
    return PROVIDER;
  }

  public static <T> T lookup(Class<? extends T> pClass)
  {
    T l = getDefault().lookup(pClass);
    if (l == null)
      throw new RuntimeException("could not lookup '" + pClass + "'.");
    return l;
  }

  private static class _NetbeansAditoInterfaceProvider implements INetbeansAditoInterface
  {
    @Override
    public <T> T lookup(Class<T> pClass)
    {
      return null;
    }
  }

}
