package de.adito.aditoweb.nbm.nbide.nbaditointerface.form;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.IAditoLayoutInfo;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.model.IAditoModelProvider;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync.IAditoPropertyInfo;
import org.openide.util.Lookup;

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
    public IAditoLayoutInfo getAditoLayoutInfo()
    {
      return null;
    }

    @Override
    public IAditoPropertyInfo getAditoPropertyInfo()
    {
      return null;
    }

    @Override
    public IAditoModelProvider getAditoModelProvider()
    {
      return null;
    }
  }

}
