package org.netbeans.modules.form.adito.mapping;

import de.adito.aditoweb.filesystem.datamodelfs.resolver.schema.EScheme;
import org.netbeans.modules.form.RADComponent;

import javax.swing.*;

/**
 * @author J. Boesl, 17.02.11
 */
public enum EModelComponentMapping
{

  BUTTON(EScheme.BUTTON, new AButtonMapping()),
  CHECKBOX(EScheme.CHECKBOX, new ACheckBoxMapping()),
  COMBOBOX(EScheme.COMBOBOX, new AComboBoxMapping()),
  EDITFIELD(EScheme.EDITFIELD, new AEditFieldMapping()),
  LABEL(EScheme.LABEL, new ALabelMapping()),
  LIST(EScheme.LIST, new AListMapping()),
  RADIOBUTTON(EScheme.RADIOBUTTON, new ARadioButtonMapping()),
  REGISTER(EScheme.REGISTER, new ARegisterMapping()),
  TABLE(EScheme.TABLE, new ATableMapping()),
  TREE(EScheme.TREE, new ATreeMapping());


  private EScheme scheme;
  private IComponentInfo componentInfo;


  EModelComponentMapping(EScheme pScheme, IComponentInfo pInfo)
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
      if (eModelComponentMapping.getComponentInfo().getComponentClass().equals(pCls))
        return eModelComponentMapping;
    }
    return null;
  }

  public static EModelComponentMapping get(EScheme pScheme)
  {
    for (EModelComponentMapping eModelComponentMapping : values())
    {
      if (eModelComponentMapping.scheme.equals(pScheme))
        return eModelComponentMapping;
    }
    return null;
  }

  public EScheme getScheme()
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
