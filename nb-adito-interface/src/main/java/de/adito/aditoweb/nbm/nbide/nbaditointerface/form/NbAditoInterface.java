package de.adito.aditoweb.nbm.nbide.nbaditointerface.form;

import org.openide.util.Lookup;

/**
 * @author J. Boesl, 11.05.11
 */
public class NbAditoInterface
{

  private static INetbeansAditoInterface provider;

  private NbAditoInterface()
  {
  }

  private static INetbeansAditoInterface getDefault()
  {
    if (provider == null)
    {
      provider = Lookup.getDefault().lookup(INetbeansAditoInterface.class);
      if (provider == null)
        provider = new _NetbeansAditoInterfaceProvider();
    }
    return provider;
  }

  public static <T> T lookup(Class<? extends T> pClass)
  {
    return getDefault().lookup(pClass);
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
