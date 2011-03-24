package org.netbeans.modules.form.adito.mapping;

import com.google.common.collect.*;
import org.netbeans.modules.form.adito.layout.AditoLayoutSupport;
import org.netbeans.modules.form.layoutsupport.LayoutSupportDelegate;

import java.util.*;

/**
 * @author J. Boesl, 18.03.11
 */
public abstract class ComponentInfo implements IComponentInfo
{

  private final static List<? extends Class<? extends LayoutSupportDelegate>> supportedLayout;
  private final static Map<Class<? extends IComponentInfo>,
      Map<Class<? extends LayoutSupportDelegate>, BiMap<String, String>>> mappings;

  static
  {
    supportedLayout = Collections.singletonList(_getAditoLayoutSupportClass());
    mappings = new HashMap<Class<? extends IComponentInfo>,
        Map<Class<? extends LayoutSupportDelegate>, BiMap<String, String>>>();
  }


  private Map<Class<? extends LayoutSupportDelegate>, BiMap<String, String>> layoutMaps;

  protected ComponentInfo()
  {
    layoutMaps = mappings.get(getClass());
    if (layoutMaps == null)
    {
      layoutMaps = new HashMap<Class<? extends LayoutSupportDelegate>, BiMap<String, String>>();
      mappings.put(getClass(), layoutMaps);
    }
  }


  @Override
  public List<? extends Class<? extends LayoutSupportDelegate>> getSupportedLayouts()
  {
    return supportedLayout;
  }

  @Override
  public String getFormPropertyName(Class<? extends LayoutSupportDelegate> pLayoutSupportClass,
                                    String pModelPropertyName)
  {
    BiMap<String, String> propertyMapping = _getMappingForLayout(pLayoutSupportClass);
    return propertyMapping.get(pModelPropertyName);
  }

  @Override
  public String getModelPropertyName(Class<? extends LayoutSupportDelegate> pLayoutSupportClass, String pFormPropertyName)
  {
    BiMap<String, String> propertyMapping = _getMappingForLayout(pLayoutSupportClass);
    return propertyMapping.inverse().get(pFormPropertyName);
  }

  private BiMap<String, String> _getMappingForLayout(Class<? extends LayoutSupportDelegate> pLayoutSupportClass)
  {
    if (pLayoutSupportClass == null)
      pLayoutSupportClass = AditoLayoutSupport.class;

    BiMap<String, String> propertyMapping = layoutMaps.get(pLayoutSupportClass);
    if (propertyMapping == null)
    {
      if (!getSupportedLayouts().contains(pLayoutSupportClass))
        throw new IllegalArgumentException("'" + pLayoutSupportClass + "' is not supported.");
      propertyMapping = createMappingForLayout(pLayoutSupportClass);
      layoutMaps.put(pLayoutSupportClass, propertyMapping);
    }
    return propertyMapping;
  }

  protected BiMap<String, String> createMappingForLayout(Class<? extends LayoutSupportDelegate> pLayoutSupportClass)
  {
    BiMap<String, String> mapping = HashBiMap.create();
    if (AditoLayoutSupport.class.equals(pLayoutSupportClass))
      createMappingForAditoLayout(mapping);
    return mapping;
  }

  public abstract void createMappingForAditoLayout(Map<String, String> pMapping);


  private static Class<? extends LayoutSupportDelegate> _getAditoLayoutSupportClass()
  {
    return AditoLayoutSupport.class;
  }

}
