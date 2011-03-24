package org.netbeans.modules.form.adito.mapping;

import org.netbeans.modules.form.layoutsupport.LayoutSupportDelegate;

import javax.swing.*;
import java.util.List;

/**
 * @author J. Boesl, 21.03.11
 */
public interface IComponentInfo
{

  public final static String NO_MAPPING = "-nomapping";

  public Class<? extends JComponent> getComponentClass();

  public List<? extends Class<? extends LayoutSupportDelegate>> getSupportedLayouts();

  public String getFormPropertyName(Class<? extends LayoutSupportDelegate> pLayoutSupportClass,
                                    String pModelPropertyName);

  public String getModelPropertyName(Class<? extends LayoutSupportDelegate> pLayoutSupportClass,
                                     String pFormPropertyName);

}
