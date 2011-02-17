package org.netbeans.modules.form.adito;

import de.adito.aditoweb.designer.filetype.PropertiesCookie;
import de.adito.aditoweb.filesystem.datamodelfs.access.DataAccessHelper;
import de.adito.aditoweb.filesystem.datamodelfs.access.mechanics.field.IFieldAccess;
import org.netbeans.modules.form.*;
import org.openide.filesystems.*;
import org.openide.loaders.DataFolder;
import org.openide.nodes.*;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author J. Boesl, 17.02.11
 */
public class ARADComponentHandler
{

  private RADComponent radComponent;
  private DataFolder modelDataObject;

  private FileChangeListener fileChangeListener;


  public ARADComponentHandler(DataFolder pModelDataObject)
  {
    modelDataObject = pModelDataObject;
  }

  public DataFolder getModelDataObject()
  {
    return modelDataObject;
  }

  public void initRADComponent(RADComponent pRADComponent) throws InvocationTargetException, IllegalAccessException
  {
    if (modelDataObject == null)
      return;

    if (radComponent != null)
      throw new RuntimeException("Can't init with: " + pRADComponent + ". Another component is already set: "
                                     + radComponent + ".");
    radComponent = pRADComponent;

    PropertiesCookie propsCookie = modelDataObject.getCookie(PropertiesCookie.class);
    if (propsCookie != null)
    {
      Sheet sheet = new Sheet();
      propsCookie.applyAditoPropertiesSync(sheet);
      Node.PropertySet[] sets = sheet.toArray();
      for (Node.PropertySet set : sets)
      {
        for (Node.Property prop : set.getProperties())
        {
          RADProperty beanProperty = radComponent.getBeanProperty(prop.getName());
          if (beanProperty != null)
            beanProperty.setValue(prop.getValue());
        }
      }
    }

    for (FileObject fo : modelDataObject.getPrimaryFile().getChildren())
    {
      final RADProperty beanProperty = radComponent.getBeanProperty(fo.getNameExt());
      if (beanProperty != null)
      {
        fo.addFileChangeListener(_getFileChangeListener());
      }
    }
  }

  public void createPropertySets(List<Node.PropertySet> propSets)
  {
    if (modelDataObject != null)
    {
//          return modelDataObject.getNodeDelegate().getPropertySets(); // klappt nicht weil asynchron!
      PropertiesCookie propsCookie = modelDataObject.getCookie(PropertiesCookie.class);
      if (propsCookie != null)
      {
        Sheet sheet = new Sheet();
        propsCookie.applyAditoPropertiesSync(sheet);
        Node.PropertySet[] sets = sheet.toArray();
//        for (Node.PropertySet set : sets)
//        {
//          Sheet.Set setCopy = new Sheet.Set();
//          setCopy.setName(set.getName());
//          setCopy.setDisplayName(set.getDisplayName());
//          setCopy.setShortDescription(set.getShortDescription());
//          if (set.getValue("tabName") != null)
//            setCopy.setValue("tabName", set.getValue("tabName"));
//          for (Node.Property property : set.getProperties())
//          {
//            Node.Property found = pRADComponent.getPropertyByName(property.getName());
//            if (found != null)
//            {
//              setCopy.put(AConnectionProperty.create(property, found));
//            }
//            else
//              setCopy.put(property);
//          }
//          propSets.add(setCopy);
//        }
        propSets.addAll(Arrays.asList(sets));
      }
    }
  }

  private FileChangeListener _getFileChangeListener()
  {
    if (fileChangeListener == null)
    {
      fileChangeListener = new FileChangeAdapter()
      {
        @Override
        public void fileChanged(FileEvent fe)
        {
          FileObject fo = fe.getFile();
          IFieldAccess<Object> fieldAccess = DataAccessHelper.accessField(fo);
          if (fieldAccess != null)
          {
            try
            {
              radComponent.getBeanProperty(fo.getNameExt()).setValue(fieldAccess.getValue());
            }
            catch (IllegalAccessException e)
            {
              e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            catch (InvocationTargetException e)
            {
              e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
          }
        }
      };
    }
    return fileChangeListener;
  }

}
