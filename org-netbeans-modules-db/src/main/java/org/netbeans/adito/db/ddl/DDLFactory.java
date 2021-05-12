package org.netbeans.adito.db.ddl;

import org.netbeans.lib.ddl.impl.Specification;

/**
 * Factory for creating DDL's
 *
 * @author s.seemann, 18.02.2021
 */
@SuppressWarnings("unused")
public class DDLFactory
{
  public static String getDropTableDDL(Specification pSpec, String pSchema, String pTableName) throws Exception
  {
    return new DropTableDDL().getDDL(pSpec, pSchema, pTableName);
  }

  public static String getModifyTableColumnDDL(Specification pSpec, String pSchema, String pTableName, String pColumnName, int pType, int pSize, int pDecSize,
                                               String pDefaultValue, boolean pNullAllowed) throws Exception
  {
    return new ModifyTableColumnDDL().getDDL(pSpec, pSchema, pTableName, pColumnName, pType, pSize, pDecSize, pDefaultValue, pNullAllowed);
  }

  public static String getAddTableColumnDDL(Specification pSpec, String pSchema, String pTableName, String pColumnName, int pType, int pSize,
                                            int pDecSize) throws Exception
  {
    return new AddTableColumnDDL().getDDL(pSpec, pSchema, pTableName, pColumnName, pType, pSize, pDecSize);
  }
}
