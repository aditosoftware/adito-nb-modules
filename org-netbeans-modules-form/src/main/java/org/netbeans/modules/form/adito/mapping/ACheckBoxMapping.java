package org.netbeans.modules.form.adito.mapping;

import javax.swing.*;
import java.util.Map;

/**
 * @author J. Boesl, 21.03.11
 */
public class ACheckBoxMapping extends ComponentInfo
{

  @Override
  public Class<? extends JComponent> getComponentClass()
  {
    return JCheckBox.class;
  }

  @Override
  public void createMappingForAditoLayout(Map<String, String> pMapping)
  {
    pMapping.put("name", "name");
    pMapping.put("comment", "comment");
    pMapping.put("fieldLinkTableName", "fieldLinkTableName");
    pMapping.put("fieldLinkFieldName", "fieldLinkFieldName");
    pMapping.put("dataType", "dataType");
    pMapping.put("valueForYES", "valueForYES");
    pMapping.put("valueForNO", "valueForNO");
    pMapping.put("ignoreWriteProtection", "ignoreWriteProtection");
    pMapping.put("searchLink", "searchLink");
    pMapping.put("searcheableModes", "searcheableModes");
    pMapping.put("readOnly", "readOnly");
    pMapping.put("obligationInput", "obligationInput");
    pMapping.put("x", "AALC_x");
    pMapping.put("y", "AALC_y");
    pMapping.put("width", "AALC_width");
    pMapping.put("height", "AALC_height");
    pMapping.put("dynamicHorizontal", "dynamicHorizontal");
    pMapping.put("dynamicVertical", "dynamicVertical");
    pMapping.put("bgColor", "bgColor");
    pMapping.put("invisible", "invisible");
    pMapping.put("enabled", "enabled");
    pMapping.put("label", "label");
    pMapping.put("labelActive", "labelActive");
    pMapping.put("labelFont", "labelFont");
    pMapping.put("labelColor", "labelColor");
    pMapping.put("labelOrientation", "labelOrientation");
    pMapping.put("labelDistance", "labelDistance");
    pMapping.put("labelAlignment", "labelAlignment");
    pMapping.put("labelAnchor", "labelAnchor");
    pMapping.put("tabOrder", "tabOrder");
    pMapping.put("toolTip", "toolTip");
    pMapping.put("cbPresetNew", "cbPresetNew");
    pMapping.put("cbPresetChange", "cbPresetChange");
  }
}
