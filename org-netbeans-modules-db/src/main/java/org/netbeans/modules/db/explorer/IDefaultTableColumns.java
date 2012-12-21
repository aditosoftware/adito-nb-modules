package org.netbeans.modules.db.explorer;

import org.netbeans.modules.db.explorer.dlg.ColumnItem;

import java.util.List;

/**
 * @author Thomas Tasior 20.12.12, 15:02
 */
public interface IDefaultTableColumns
{
  /**
   * Liefert die Spalteninformationen für die Standardspalten aller
   * Adito Tabellen:<br><b>"ID", "DATE_EDIT", "DATE_NEW", "USER_EDIT", "USER_NEW".</br>
   */
  public List<ColumnItem> get();
}
