package org.netbeans.adito.db;

import org.jetbrains.annotations.NotNull;
import org.netbeans.lib.ddl.impl.Specification;
import org.netbeans.modules.db.explorer.dlg.ColumnItem;

import java.util.List;

/**
 * Service that will provide the default structure of an ADITO DB table
 *
 * @author w.glanzer, 25.11.2019
 */
public interface IDefaultSystemStructureProvider
{

  /**
   * @return Returns a list of all default columns, that will be included in the create dialog of Netbeans
   */
  @NotNull
  List<ColumnItem> getDefaultColumnItems(@NotNull Specification pSpec);

}
