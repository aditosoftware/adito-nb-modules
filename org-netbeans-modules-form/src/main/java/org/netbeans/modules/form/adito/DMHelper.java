package org.netbeans.modules.form.adito;

import org.netbeans.modules.form.*;
import org.openide.filesystems.FileObject;

import java.util.*;

/**
 * @author J. Boesl, 10.02.11
 */
public class DMHelper
{

  private DMHelper()
  {
  }

  public static ARADComponentHandler getHandler()
  {
    return new ARADComponentHandler();
  }

  public static ARADComponentHandler getHandler(FileObject pFo)
  {
    ARADComponentHandler aradComponentHandler = new ARADComponentHandler();
    aradComponentHandler.setModelFileObject(pFo);
    return aradComponentHandler;
  }

  static String getFreeName(FormModel pFormModel, String pName)
  {
    Set<String> names = new HashSet<>();
    for (RADComponent radComponent : pFormModel.getAllComponents())
    {
      boolean isNewName = names.add(radComponent.getName());
      if (!isNewName)
        throw new IllegalStateException("A component with name '" + radComponent.getName() + "' exists more often " +
                                            "than once. This is illegal.");
    }
    return _getFreeName(names, pName, 1);
  }

  private static String _getFreeName(Set<String> pNames, String pName, int pIndex)
  {
    int index = pIndex;
    while (index < Integer.MAX_VALUE)
    {
      String name = pName + (index > 1 ? index : "");
      if (!(pNames.contains(name)))
        return name;
      index++;
    }
    throw new RuntimeException("You mustn't have Integer.MAX_VALUES components you know?!");
  }

}
