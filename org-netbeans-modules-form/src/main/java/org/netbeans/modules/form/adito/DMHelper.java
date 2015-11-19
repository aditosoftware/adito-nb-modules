package org.netbeans.modules.form.adito;

import de.adito.propertly.core.spi.IPropertyPitProvider;
import org.netbeans.modules.form.*;

import java.util.*;
import java.util.stream.Collectors;

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

  public static ARADComponentHandler getHandler(IPropertyPitProvider<?, ?, ?> pModel)
  {
    ARADComponentHandler aradComponentHandler = new ARADComponentHandler();
    aradComponentHandler.setModel(pModel);
    return aradComponentHandler;
  }

  static String getFreeName(FormModel pFormModel, String pName)
  {
    Set<String> names = pFormModel.getAllComponents().stream()
        .map(RADComponent::getName).collect(Collectors.toSet());
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
