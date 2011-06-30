package org.netbeans.modules.form.adito.components;

import org.netbeans.modules.form.RADComponent;
import org.openide.loaders.*;
import org.openide.nodes.*;

import java.awt.*;

/**
 * @author J. Boesl, 30.06.11
 */
public final class AditoNodeConnect
{

  private AditoNodeConnect()
  {
  }

  public static Image getIcon(RADComponent pComponent, final int pType)
  {
    return _resolve(pComponent, new _C<Image>()
    {
      @Override
      public Image resolve(DataObject pDataObject)
      {
        return pDataObject.getNodeDelegate().getIcon(pType);
      }
    });
  }

  public static String getDisplayName(RADComponent pComponent)
  {
    return _resolve(pComponent, new _C<String>()
    {
      @Override
      public String resolve(DataObject pDataObject)
      {
        return pDataObject.getNodeDelegate().getDisplayName();
      }
    });
  }

  public static Sheet getSheet(RADComponent pComponent)
  {
    return _resolve(pComponent, new _C<Sheet>()
    {
      @Override
      public Sheet resolve(DataObject pDataObject)
      {
        return null;
        //return pDataObject.getNodeDelegate().getPropertySets();
      }
    });
  }

  private static <T> T _resolve(RADComponent pComp, _C<T> pC)
  {
    DataFolder modelDataObject = pComp.getARADComponentHandler().getModelDataObject();
    if (modelDataObject == null)
      return null;
    return pC.resolve(modelDataObject);
  }

  private interface _C<T>
  {
    T resolve(DataObject pDataObject);
  }

}
