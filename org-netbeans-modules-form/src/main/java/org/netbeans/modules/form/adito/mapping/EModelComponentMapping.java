package org.netbeans.modules.form.adito.mapping;

import de.adito.aditoweb.filesystem.datamodelfs.access.model.EModelAccessType;

import javax.swing.*;

/**
 * @author J. Boesl, 17.02.11
 */
public enum EModelComponentMapping
{

  BUTTON(EModelAccessType.BUTTON, new AButtonMapping()),
  CHECKBOX(EModelAccessType.CHECKBOX, new ACheckBoxMapping()),
  COMBOBOX(EModelAccessType.COMBOBOX, new AComboBoxMapping()),
  EDITFIELD(EModelAccessType.EDITFIELD, new AEditFieldMapping()),
  LABEL(EModelAccessType.LABEL, new ALabelMapping()),
  LIST(EModelAccessType.LIST, new AListMapping()),
  RADIOBUTTON(EModelAccessType.RADIOBUTTON, new ARadioButtonMapping()),
  REGISTER(EModelAccessType.REGISTER, new ARegisterMapping()),
  TABLE(EModelAccessType.TABLE, new ATableMapping()),
  TREE(EModelAccessType.TREE, new ATreeMapping());


  private EModelAccessType modelAccessType;
  private IComponentInfo componentInfo;


  EModelComponentMapping(EModelAccessType pType, IComponentInfo pInfo)
  {
    modelAccessType = pType;
    componentInfo = pInfo;
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

  public static EModelComponentMapping get(EModelAccessType pType)
  {
    for (EModelComponentMapping eModelComponentMapping : values())
    {
      if (eModelComponentMapping.modelAccessType.equals(pType))
        return eModelComponentMapping;
    }
    return null;
  }

  public EModelAccessType getModelAccessType()
  {
    return modelAccessType;
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
