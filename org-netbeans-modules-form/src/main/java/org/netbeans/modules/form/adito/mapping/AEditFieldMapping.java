package org.netbeans.modules.form.adito.mapping;

import org.netbeans.modules.form.adito.comps.AEditField;

import javax.swing.*;
import java.util.Map;

/**
 * @author J. Boesl, 21.03.11
 */
public class AEditFieldMapping extends ComponentInfo
{

  @Override
  public Class<? extends JComponent> getComponentClass()
  {
    return AEditField.class;
  }

  @Override
  public void createMappingForAditoLayout(Map<String, String> pMapping)
  {
    pMapping.put("name", "name");
    pMapping.put("comment", "comment");
    pMapping.put("fieldLinkTableName", "fieldLinkTableName");
    pMapping.put("fieldLinkFieldName", "fieldLinkFieldName");
    pMapping.put("dataType", "dataType");
    pMapping.put("precision", "precision");
    pMapping.put("outputFormat", "outputFormat");
    pMapping.put("inputFormat", "inputFormat");
    pMapping.put("representation", "representation");
    pMapping.put("ignoreWriteProtection", "ignoreWriteProtection");
    pMapping.put("searchLink", "searchLink");
    pMapping.put("searcheableModes", "searcheableModes");
    pMapping.put("readOnly", "readOnly");
    pMapping.put("obligationInput", "obligationInput");
    pMapping.put("keyboardSupport", "keyboardSupport");
    pMapping.put("calendarSupport", "calendarSupport");
    pMapping.put("clockSupport", "clockSupport");
    pMapping.put("timeIntervall", "timeIntervall");
    pMapping.put("searchInInterval", "searchInInterval");
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
    pMapping.put("enabled", "enabled");
    pMapping.put("tabOrder", "tabOrder");
    pMapping.put("selectAllOnFocus", "selectAllOnFocus");
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