package org.netbeans.modules.form.adito.mapping;

import javax.swing.*;
import java.util.Map;

/**
 * @author J. Boesl, 21.03.11
 */
public class ARegisterMapping extends ComponentInfo
{

  @Override
  public Class<? extends JComponent> getComponentClass()
  {
    return JTabbedPane.class;
  }

  @Override
  public void createMappingForAditoLayout(Map<String, String> pMapping)
  {
    pMapping.put("name", "name");
    pMapping.put("comment", "comment");
    pMapping.put("PLATZHALTER", "PLATZHALTER");
    pMapping.put("x", "AALC_x");
    pMapping.put("y", "AALC_y");
    pMapping.put("width", "AALC_width");
    pMapping.put("height", "AALC_height");
    pMapping.put("dynamicHorizontal", "dynamicHorizontal");
    pMapping.put("dynamicVertical", "dynamicVertical");
    pMapping.put("font", "font");
    pMapping.put("fontColor", "fontColor");
    pMapping.put("invisible", "invisible");
    pMapping.put("tabOrder", "tabOrder");
    pMapping.put("toolTip", "toolTip");
    pMapping.put("label", "label");
    pMapping.put("labelColor", "labelColor");
    pMapping.put("labelFont", "labelFont");
    pMapping.put("labelOrientation", "labelOrientation");
    pMapping.put("labelDistance", "labelDistance");
    pMapping.put("labelAlignment", "labelAlignment");
    pMapping.put("bgColor", "bgColor");
    pMapping.put("bgColorProcess", "bgColorProcess");
    pMapping.put("childDataModels", "childDataModels");
  }
}
