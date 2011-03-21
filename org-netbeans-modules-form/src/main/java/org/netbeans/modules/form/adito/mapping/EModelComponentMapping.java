package org.netbeans.modules.form.adito.mapping;

import de.adito.aditoweb.filesystem.datamodelfs.access.model.EModelAccessType;
import org.netbeans.modules.form.adito.comps.*;

import javax.swing.*;

/**
 * @author J. Boesl, 17.02.11
 */
public enum EModelComponentMapping
{

  BUTTON(EModelAccessType.BUTTON, AButton.class),
  CHECKBOX(EModelAccessType.CHECKBOX, JCheckBox.class),
  COMBOBOX(EModelAccessType.COMBOBOX, AComboBox.class),
  EDITFIELD(EModelAccessType.EDITFIELD, AEditField.class),
  LABEL(EModelAccessType.LABEL, ALabel.class),
  LIST(EModelAccessType.LIST, JList.class),
  RADIOBUTTON(EModelAccessType.RADIOBUTTON, JRadioButton.class),
  REGISTER(EModelAccessType.REGISTER, JTabbedPane.class),
  TABLE(EModelAccessType.TABLE, JTable.class),
  TREE(EModelAccessType.TREE, JTree.class);


  private EModelAccessType modelAccessType;
  private Class<? extends JComponent> swingClass;


  EModelComponentMapping(EModelAccessType pType, Class<? extends JComponent> pCls)
  {
    modelAccessType = pType;
    swingClass = pCls;
  }

  public static EModelComponentMapping get(Class<? extends JComponent> pCls)
  {
    for (EModelComponentMapping eModelComponentMapping : values())
    {
      if (eModelComponentMapping.swingClass.equals(pCls))
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

  public Class<? extends JComponent> getSwingClass()
  {
    return swingClass;
  }
}
