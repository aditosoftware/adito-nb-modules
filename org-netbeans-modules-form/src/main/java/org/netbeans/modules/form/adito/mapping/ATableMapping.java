package org.netbeans.modules.form.adito.mapping;

import javax.swing.*;
import java.util.Map;

/**
 * @author J. Boesl, 21.03.11
 */
public class ATableMapping extends ComponentInfo
{

  @Override
  public Class<? extends JComponent> getComponentClass()
  {
    return JTable.class;
  }

  @Override
  public void createMappingForAditoLayout(Map<String, String> pMapping)
  {
    pMapping.put("name", "name");
    pMapping.put("comment", "comment");
    pMapping.put("ignoreWriteProtection", "ignoreWriteProtection");
    pMapping.put("fixedColumns", "fixedColumns");
    pMapping.put("idDataType", "idDataType");
    pMapping.put("returnTypeOfCommand", "returnTypeOfCommand");
    pMapping.put("singleSelection", "singleSelection");
    pMapping.put("x", "AALC_x");
    pMapping.put("y", "AALC_y");
    pMapping.put("width", "AALC_width");
    pMapping.put("height", "AALC_height");
    pMapping.put("dynamicHorizontal", "dynamicHorizontal");
    pMapping.put("dynamicVertical", "dynamicVertical");
    pMapping.put("font", "font");
    pMapping.put("fontColor", "fontColor");
    pMapping.put("headerFont", "headerFont");
    pMapping.put("headerFontColor", "headerFontColor");
    pMapping.put("bgColor", "bgColor");
    pMapping.put("invisible", "invisible");
    pMapping.put("enabled", "enabled");
    pMapping.put("rowID", "rowID");
    pMapping.put("rowEditable", "rowEditable");
    pMapping.put("rowFontColor", "rowFontColor");
    pMapping.put("rowBgColor", "rowBgColor");
    pMapping.put("tabOrder", "tabOrder");
    pMapping.put("displayDetails", "displayDetails");
    pMapping.put("tableViewPopup", "tableViewPopup");
    pMapping.put("reorderColumns", "reorderColumns");
    pMapping.put("label", "label");
    pMapping.put("labelActive", "labelActive");
    pMapping.put("labelFont", "labelFont");
    pMapping.put("labelColor", "labelColor");
    pMapping.put("labelOrientation", "labelOrientation");
    pMapping.put("labelDistance", "labelDistance");
    pMapping.put("labelAlignment", "labelAlignment");
    pMapping.put("labelAnchor", "labelAnchor");
    pMapping.put("toolTip", "toolTip");
    pMapping.put("showGrid", "showGrid");
    pMapping.put("gridColor", "gridColor");
    pMapping.put("headerBgColor", "headerBgColor");
    pMapping.put("columnMetaData", "columnMetaData");
    pMapping.put("jumpToProcess", "jumpToProcess");
    pMapping.put("scrollMode", "scrollMode");
  }
}
