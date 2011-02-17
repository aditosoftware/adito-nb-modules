//package org.netbeans.modules.form.adito;
//
//import de.adito.aditoweb.filesystem.datamodelfs.access.model.EModelAccessType;
//import de.adito.aditoweb.swingcommon.misc.access.*;
//
//import javax.swing.*;
//
///**
// * @author J. Boesl, 17.02.11
// */
//public class ModelMapping extends AccessCollection<ModelMapping.Entry>
//{
//
//  public static enum TYPE
//  {
//    MODEL,
//    SWING
//  }
//
//  private final static AccessDefinition def = new AccessDefinition()
//  {
//    @Override
//    protected void init()
//    {
//      define(TYPE.MODEL, true);
//      define(TYPE.SWING, true);
//    }
//  };
//
//
//  private static ModelMapping instance;
//
//
//  private ModelMapping()
//  {
//    super(def);
//    add(new Entry(EModelAccessType.BUTTON, AButton.class));
//    add(new Entry(EModelAccessType.CHECKBOX, JCheckBox.class));
//    add(new Entry(EModelAccessType.COMBOBOX, JComboBox.class));
//    add(new Entry(EModelAccessType.EDITFIELD, AEditField.class));
//    add(new Entry(EModelAccessType.LABEL, JLabel.class));
//    add(new Entry(EModelAccessType.LIST, JList.class));
//    add(new Entry(EModelAccessType.RADIOBUTTON, JRadioButton.class));
//    add(new Entry(EModelAccessType.REGISTER, JTabbedPane.class));
//    add(new Entry(EModelAccessType.TABLE, JTable.class));
//    add(new Entry(EModelAccessType.TREE, JTree.class));
//  }
//
//  public Entry getModelEntry(EModelAccessType pModelAccessType)
//  {
//    return getAccessUnique(TYPE.MODEL, pModelAccessType);
//  }
//
//  public <T extends Class<? extends JComponent>> Entry getSwingEntry(T pSwingClass)
//  {
//    return getAccessUnique(TYPE.SWING, pSwingClass);
//  }
//
//
//  public static ModelMapping getInstance()
//  {
//    if (instance == null)
//      instance = new ModelMapping();
//    return instance;
//  }
//
//  public static class Entry extends AccessObject
//  {
//    public Entry(EModelAccessType pModelType, Class<? extends JComponent> pSwingClass)
//    {
//      super(def);
//      put(TYPE.MODEL, pModelType);
//      put(TYPE.SWING, pSwingClass);
//    }
//
//    public <T extends Class<? extends JComponent>> T getSwing()
//    {
//      return (T) get(TYPE.SWING);
//    }
//
//    public <T extends Enum<?>> EModelAccessType getModel()
//    {
//      return (EModelAccessType) get(TYPE.MODEL);
//    }
//  }
//
//}
