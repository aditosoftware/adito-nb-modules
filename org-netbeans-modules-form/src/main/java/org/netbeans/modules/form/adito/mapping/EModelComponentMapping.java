package org.netbeans.modules.form.adito.mapping;

import de.adito.aditoweb.filesystem.datamodelfs.resolver.schema.ESchemes;
import org.netbeans.modules.form.RADComponent;

import javax.swing.*;

/**
 * @author J. Boesl, 17.02.11
 */
public enum EModelComponentMapping
{

  BUTTON(ESchemes.BUTTON, new AButtonMapping()),
  CHECKBOX(ESchemes.CHECKBOX, new ACheckBoxMapping()),
  COMBOBOX(ESchemes.COMBOBOX, new AComboBoxMapping()),
  EDITFIELD(ESchemes.EDITFIELD, new AEditFieldMapping()),
  LABEL(ESchemes.LABEL, new ALabelMapping()),
  LIST(ESchemes.LIST, new AListMapping()),
  RADIOBUTTON(ESchemes.RADIOBUTTON, new ARadioButtonMapping()),
  REGISTER(ESchemes.REGISTER, new ARegisterMapping()),
  TABLE(ESchemes.TABLE, new ATableMapping()),
  TREE(ESchemes.TREE, new ATreeMapping());


  private ESchemes scheme;
  private IComponentInfo componentInfo;


  EModelComponentMapping(ESchemes pScheme, IComponentInfo pInfo)
  {
    scheme = pScheme;
    componentInfo = pInfo;
  }

  public static EModelComponentMapping get(RADComponent pRADComponent)
  {
    Class<?> beanClass = pRADComponent.getBeanClass();
    if (JComponent.class.isAssignableFrom(beanClass))
      //noinspection unchecked
      return get((Class<JComponent>) beanClass);
    return null;
  }

  public static EModelComponentMapping get(Class<? extends JComponent> pCls)
  {
    for (EModelComponentMapping eModelComponentMapping : values())
    {
      if (eModelComponentMapping.componentInfo.getComponentClass().equals(pCls))
        return eModelComponentMapping;
    }
    return null;
  }

  public static EModelComponentMapping get(ESchemes pScheme)
  {
    for (EModelComponentMapping eModelComponentMapping : values())
    {
      if (eModelComponentMapping.scheme.equals(pScheme))
        return eModelComponentMapping;
    }
    return null;
  }

  public ESchemes getScheme()
  {
    return scheme;
  }

  public IComponentInfo getComponentInfo()
  {
    return componentInfo;
  }

  public Class<? extends JComponent> getSwingClass()
  {
    return componentInfo.getComponentClass();
  }
}
