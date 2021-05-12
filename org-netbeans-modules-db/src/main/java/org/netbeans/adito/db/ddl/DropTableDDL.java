package org.netbeans.adito.db.ddl;

import org.netbeans.lib.ddl.impl.*;

/**
 * Creates a DDL-String for dropping a table
 *
 * @author s.seemann, 18.02.2021
 */
class DropTableDDL
{
  public String getDDL(Specification pSpec, String pSchema, String pTableName) throws Exception
  {
    AbstractCommand cmd = pSpec.createCommandDropTable(pTableName);
    cmd.setObjectOwner(pSchema);

    return cmd.getCommand();
  }
}
