package org.netbeans.modules.db.explorer.dlg;

import org.netbeans.lib.ddl.impl.Specification;

/**
 * Konvertiert einen übergebeneen Namen (meist Tabelle oder Spalte) in groß oder klein.
 * Je nachdem wie es in der Date dbspec.plist im SchlüsselWort "Case" für das jeweilige DBMS hinterlegt ist.
 * @author c.stadler 11.01.2017
 */
public class CaseConverter
{
  public static final String CASE_STRING = "Case";
  public static final String LOWER_STRING = "Lower";

  public static String convertCase(String pObjectName, Specification pSpec)
  {
    String _case = (String)pSpec.getProperties().get(CASE_STRING);

    if(_case != null && _case.equalsIgnoreCase(LOWER_STRING))
      return pObjectName.toLowerCase();

    return pObjectName.toUpperCase();
  }
}
