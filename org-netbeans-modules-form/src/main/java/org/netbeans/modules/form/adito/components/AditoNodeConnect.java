package org.netbeans.modules.form.adito.components;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.model.*;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync.*;
import de.adito.propertly.core.spi.IPropertyPitProvider;
import org.jetbrains.annotations.Nullable;
import org.netbeans.modules.form.RADComponent;
import org.openide.nodes.*;
import org.openide.util.*;

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
    return _resolve(pComponent, new _NodeC<Image>()
    {
      @Override
      public Image resolveNode(@Nullable Node pNode)
      {
        return pNode == null ? null : pNode.getIcon(pType);
      }
    });
  }

  public static String getDisplayName(RADComponent pComponent)
  {
    return _resolve(pComponent, new _NodeC<String>()
    {
      @Override
      public String resolveNode(@Nullable Node pNode)
      {
        return pNode == null ? null : pNode.getDisplayName();
      }
    });
  }

  public static String getName(RADComponent pComponent)
  {
    return _resolve(pComponent, new _NodeC<String>()
    {
      @Override
      public String resolveNode(@Nullable Node pNode)
      {
        return pNode == null ? null : pNode.getName();
      }
    });
  }

  @Nullable
  public static Node.PropertySet[] getPropertySets(RADComponent pComponent)
  {
    return _resolve(pComponent, new _NodeC<Node.PropertySet[]>()
    {
      @Override
      public Node.PropertySet[] resolveNode(@Nullable Node pNode)
      {
        return pNode == null ? new Node.PropertySet[0] : pNode.getPropertySets();
      }
    });
  }

  public static void addWeakPropertyChangeListener(RADComponent pComponent, final PropertyChangeListener pListener)
  {
    _resolve(pComponent, new _NodeC<Void>()
    {
      @Override
      public Void resolveNode(@Nullable Node pNode)
      {
        Objects.requireNonNull(pNode);
        pNode.addPropertyChangeListener(WeakListeners.propertyChange(pListener, pNode));
        return null;
      }
    });
  }

  public static void addWeakNodeListener(RADComponent pComponent, final NodeListener pListener)
  {
    _resolve(pComponent, new _NodeC<Void>()
    {
      @Override
      public Void resolveNode(@Nullable Node pNode)
      {
        Objects.requireNonNull(pNode);
        pNode.addNodeListener(WeakListeners.create(NodeListener.class, pListener, pNode));
        return null;
      }
    });
  }

  public static List<Action> getActions(RADComponent pComponent, final boolean pContext)
  {
    Action[] actions = _resolve(pComponent, new _NodeC<Action[]>()
    {
      @Override
      public Action[] resolveNode(@Nullable Node pNode)
      {
        return pNode == null ? new Action[0] : pNode.getActions(pContext);
      }
    });
    if (actions == null)
      return Collections.emptyList();
    return Arrays.asList(actions);
  }

  @Nullable
  public static Lookup getLookup(RADComponent pComponent)
  {
    return _resolve(pComponent, new _DataObjectC<Lookup>()
    {
      @Override
      public Lookup resolveDataObjectLookup(@Nullable Lookup pDataObjectLookup)
      {
        return pDataObjectLookup;
      }
    });
  }

  public static Optional<Node> getNode(RADComponent pComponent)
  {
    return _resolve(pComponent, new _NodeC<Optional<Node>>()
    {
      @Override
      public Optional<Node> resolveNode(@Nullable Node pNode)
      {
        return Optional.ofNullable(pNode);
      }
    });
  }

  public static boolean shouldHaveNode(RADComponent pParent, RADComponent pComponent)
  {
    IAditoModelDataProvider modelDataProvider = NbAditoInterface.lookup(IAditoModelDataProvider.class);
    IFormComponentChildContainer childContainer = modelDataProvider.getChildContainer(pParent.getARADComponentHandler().getModel());
    return childContainer == null || childContainer.shouldHaveNode(pComponent.getARADComponentHandler().getModel());
  }

  private static <T> T _resolve(RADComponent pComp, _PropertyPitProviderC<T> pC)
  {
    return pC.resolvePPP(pComp.getARADComponentHandler().getModel());
  }


  /**
   * Ausführungsbeschreibung auf Nodes.
   */
  private abstract static class _NodeC<T> implements _PropertyPitProviderC<T>
  {
    @Override
    public final T resolvePPP(IPropertyPitProvider<?, ?, ?> pModel)
    {
      IFormComponentInfo ci = pModel == null ? null : NbAditoInterface.lookup(IFormComponentInfoProvider.class)
          .createComponentInfo(pModel);
      return resolveNode(ci == null ? null : ci.getNode());
    }

    public abstract T resolveNode(@Nullable Node pNode);
  }

  /**
   * Ausführungsbeschreibung auf DataObjects.
   */
  private abstract static class _DataObjectC<T> implements _PropertyPitProviderC<T>
  {
    @Override
    public final T resolvePPP(IPropertyPitProvider<?, ?, ?> pModel)
    {
      IFormComponentInfo ci = pModel == null ? null : NbAditoInterface.lookup(IFormComponentInfoProvider.class)
          .createComponentInfo(pModel);
      return resolveDataObjectLookup(ci == null ? null : ci.getDataObjectLookup());
    }

    public abstract T resolveDataObjectLookup(@Nullable Lookup pDataObjectLookup);
  }

  /**
   * Ausführungsbeschreibung auf PropertyPitProvider.
   */
  private interface _PropertyPitProviderC<T>
  {
    T resolvePPP(IPropertyPitProvider<?, ?, ?> pModel);
  }

}
