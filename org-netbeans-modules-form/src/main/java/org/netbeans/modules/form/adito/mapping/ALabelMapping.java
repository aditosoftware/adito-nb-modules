package org.netbeans.modules.form.adito.mapping;

import javax.swing.*;
import java.util.Map;

/**
 * @author J. Boesl, 21.03.11
 */
public class ALabelMapping extends ComponentInfo
{

  @Override
  public Class<? extends JComponent> getComponentClass()
  {
    return null; // ALabel.class;
  }

  @Override
  public void createMappingForAditoLayout(Map<String, String> pMapping)
  {
    pMapping.put("name", "name");
    pMapping.put("comment", "comment");
    pMapping.put("fieldLinkTableName", "fieldLinkTableName");
    pMapping.put("fieldLinkFieldName", "fieldLinkFieldName");
    pMapping.put("dataType", "dataType");
    pMapping.put("outputFormat", "outputFormat");
    pMapping.put("alignment", "alignment");
    pMapping.put("x", "AALC_x");
    pMapping.put("y", "AALC_y");
    pMapping.put("width", "AALC_width");
    pMapping.put("height", "AALC_height");
    pMapping.put("dynamicHorizontal", "dynamicHorizontal");
    pMapping.put("dynamicVertical", "dynamicVertical");
    pMapping.put("font", "font");
    pMapping.put("fontColor", "fontColor");
    pMapping.put("bgColor", "bgColor");
    pMapping.put("invisible", "invisible");
    pMapping.put("label", "label");
    pMapping.put("labelActive", "labelActive");
    pMapping.put("labelFont", "labelFont");
    pMapping.put("labelColor", "labelColor");
    pMapping.put("labelOrientation", "labelOrientation");
    pMapping.put("labelDistance", "labelDistance");
    pMapping.put("labelAlignment", "labelAlignment");
    pMapping.put("labelAnchor", "labelAnchor");
    pMapping.put("toolTip", "toolTip");
  }
}