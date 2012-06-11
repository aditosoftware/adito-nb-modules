package org.netbeans.modules.form.adito.components;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync.*;
import org.netbeans.modules.form.RADComponent;
import org.openide.loaders.*;
import org.openide.nodes.*;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.List;

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
      public Image resolve(DataFolder pDataObject)
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
      public String resolve(DataFolder pDataObject)
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
      public Sheet resolve(DataFolder pDataObject)
      {
        IFormComponentInfoProvider compInfoProvider = NbAditoInterface.lookup(IFormComponentInfoProvider.class);
        IFormComponentInfo componentInfo = compInfoProvider.createComponentInfo(pDataObject);
        return componentInfo.createSheet();
      }
    });
  }

  public static void addPropertyChangeListener(RADComponent pComponent, final PropertyChangeListener pListener)
  {
    _resolve(pComponent, new _C<Void>()
    {
      @Override
      public Void resolve(DataFolder pDataObject)
      {
        pDataObject.getNodeDelegate().addPropertyChangeListener(pListener);
        return null;
      }
    });
  }

  public static List<Action> getActions(RADComponent pComponent, final boolean pContext)
  {
    Action[] actions = _resolve(pComponent, new _C<Action[]>()
    {
      @Override
      public Action[] resolve(DataFolder pDataObject)
      {
        return pDataObject.getNodeDelegate().getActions(pContext);
      }
    });
    if (actions == null)
      return Collections.emptyList();
    return Arrays.asList(actions);
  }

  public static Lookup getLookup(RADComponent pComponent)
  {
    return _resolve(pComponent, new _C<Lookup>()
    {
      @Override
      public Lookup resolve(DataFolder pDataObject)
      {
        return Lookups.exclude(pDataObject.getLookup(), DataObject.class, Node.class);
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

  /**
   * Ausführungsinterface
   */
  private interface _C<T>
  {
    T resolve(DataFolder pDataObject);
  }

}
