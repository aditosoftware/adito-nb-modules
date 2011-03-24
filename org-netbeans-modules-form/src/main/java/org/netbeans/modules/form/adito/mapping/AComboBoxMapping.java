package org.netbeans.modules.form.adito.mapping;

import org.netbeans.modules.form.adito.comps.AComboBox;

import javax.swing.*;
import java.util.Map;

/**
 * @author J. Boesl, 21.03.11
 */
public class AComboBoxMapping extends ComponentInfo
{

  @Override
  public Class<? extends JComponent> getComponentClass()
  {
    return AComboBox.class;
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
    pMapping.put("font", "font");
    pMapping.put("fontColor", "fontColor");
    pMapping.put("bgColor", "bgColor");
    pMapping.put("invisible", "invisible");
    pMapping.put("enabled", "enabled");
    pMapping.put("idUsed", "idUsed");
    pMapping.put("hasRowBgColor", "hasRowBgColor");
    pMapping.put("tabOrder", "tabOrder");
    pMapping.put("editing", "editing");
    pMapping.put("listEntries", "listEntries");
    pMapping.put("maximumRowCount", "maximumRowCount");
    pMapping.put("emptyElement", "emptyElement");
    pMapping.put("label", "label");
    pMapping.put("labelActive", "labelActive");
    pMapping.put("labelFont", "labelFont");
    pMapping.put("labelColor", "labelColor");
    pMapping.put("labelOrientation", "labelOrientation");
    pMapping.put("labelDistance", "labelDistance");
    pMapping.put("labelAlignment", "labelAlignment");
    pMapping.put("labelAnchor", "labelAnchor");
    pMapping.put("toolTip", "toolTip");
    pMapping.put("inputFormat", "inputFormat");
    pMapping.put("outputFormat", "outputFormat");
  }
}