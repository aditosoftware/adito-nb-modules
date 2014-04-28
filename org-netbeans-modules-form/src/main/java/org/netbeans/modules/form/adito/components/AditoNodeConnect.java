package org.netbeans.modules.form.adito.components;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.sync.*;
import org.jetbrains.annotations.Nullable;
import org.netbeans.modules.form.RADComponent;
import org.openide.filesystems.FileObject;
import org.openide.loaders.*;
import org.openide.nodes.*;
import org.openide.util.Lookup;

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
      public Image resolve(Node pNode)
      {
        return pNode.getIcon(pType);
      }
    });
  }

  public static String getDisplayName(RADComponent pComponent)
  {
    return _resolve(pComponent, new _NodeC<String>()
    {
      @Override
      public String resolve(Node pNode)
      {
        return pNode.getDisplayName();
      }
    });
  }

  public static String getName(RADComponent pComponent)
  {
    return _resolve(pComponent, new _NodeC<String>()
    {
      @Override
      public String resolve(Node pNode)
      {
        return pNode.getName();
      }
    });
  }

  @Nullable
  public static Sheet getSheet(RADComponent pComponent)
  {
    return _resolve(pComponent, new _FileObjectC<Sheet>()
    {
      @Override
      public Sheet resolve(FileObject pFileObject)
      {
        IFormComponentInfoProvider compInfoProvider = NbAditoInterface.lookup(IFormComponentInfoProvider.class);
        IFormComponentInfo componentInfo = compInfoProvider.createComponentInfo(pFileObject);
        return componentInfo.createSheet();
      }
    });
  }

  public static void addPropertyChangeListener(RADComponent pComponent, final PropertyChangeListener pListener)
  {
    _resolve(pComponent, new _NodeC<Void>()
    {
      @Override
      public Void resolve(Node pNode)
      {
        pNode.addPropertyChangeListener(pListener);
        return null;
      }
    });
  }

  public static void removePropertyChangeListener(RADComponent pComponent, final PropertyChangeListener pListener)
  {
    _resolve(pComponent, new _NodeC<Void>()
    {
      @Override
      public Void resolve(Node pNode)
      {
        pNode.removePropertyChangeListener(pListener);
        return null;
      }
    });
  }

  public static List<Action> getActions(RADComponent pComponent, final boolean pContext)
  {
    Action[] actions = _resolve(pComponent, new _NodeC<Action[]>()
    {
      @Override
      public Action[] resolve(Node pNode)
      {
        return pNode.getActions(pContext);
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
      public Lookup resolve(DataObject pDataObject)
      {
        return pDataObject.getLookup();
      }
    });
  }

  public static INodePrivileges getPriveleges(RADComponent pComponent)
  {
    return _resolve(pComponent, new _DataObjectC<INodePrivileges>()
    {
      @Override
      public INodePrivileges resolve(final DataObject pDataObject)
      {
        return new INodePrivileges()
        {
          @Override
          public boolean canDelete()
          {
            return pDataObject.isDeleteAllowed();
          }

          @Override
          public boolean canCopy()
          {
            return pDataObject.isCopyAllowed();
          }

          @Override
          public boolean canMove()
          {
            return pDataObject.isMoveAllowed();
          }

          @Override
          public boolean canRename()
          {
            return pDataObject.isRenameAllowed();
          }
        };
      }
    });
  }


  private static <T> T _resolve(RADComponent pComp, _FileObjectC<T> pC)
  {
    FileObject modelFileObject = pComp.getARADComponentHandler().getModelFileObject();
    if (modelFileObject == null)
      return null;
    return pC.resolve(modelFileObject);
  }


  /**
   * Ausführungsbeschreibung auf Nodes.
   */
  private abstract static class _NodeC<T> extends _DataObjectC<T>
  {
    @Override
    public T resolve(DataObject pDataObject)
    {
      return pDataObject != null ? resolve(pDataObject.getNodeDelegate()) : null;
    }

    public abstract T resolve(Node pNode);
  }

  /**
   * Ausführungsbeschreibung auf DataObjects.
   */
  private abstract static class _DataObjectC<T> implements _FileObjectC<T>
  {
    @Override
    public final T resolve(FileObject pFileObject)
    {
      try
      {
        DataFolder dataFolder = DataFolder.findFolder(pFileObject);
        return dataFolder != null ? resolve(dataFolder) : null;
      }
      catch (IllegalArgumentException e)
      {
        return null;
      }
    }

    public abstract T resolve(DataObject pDataObject);
  }

  /**
   * Ausführungsbeschreibung auf FileObjects.
   */
  private interface _FileObjectC<T>
  {
    T resolve(FileObject pFileObject);
  }

}
