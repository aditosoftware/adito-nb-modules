package org.netbeans.adito.db;

import org.netbeans.modules.db.metadata.model.MetadataUtilities;
import org.netbeans.modules.db.metadata.model.api.*;
import org.netbeans.modules.db.metadata.model.jdbc.*;
import org.netbeans.modules.db.metadata.model.spi.CatalogImplementation;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

/**
 * Angepasstes Schema welches Oracle-Synonyme unterstützt
 *
 * @author T. Feldmann, 10.03.14
 */
public class JDBCSynonymSchema extends JDBCSchema
{

  private static final Logger LOGGER = Logger.getLogger(JDBCSchema.class.getName());
  protected Map<String, Table> synonyms;


  public JDBCSynonymSchema(CatalogImplementation catalog, String name, boolean _default, boolean synthetic)
  {
    super((JDBCCatalog) catalog, name, _default, synthetic);
  }

  /**
   * Gibt die vorhandenen Synonyme zurück
   *
   * @return die Synonyme
   */
  public final Collection<Table> getSynonyms()
  {
    return initSynonyms().values();
  }

  public final Table getSynonym(String name)
  {
    return MetadataUtilities.find(name, initSynonyms());
  }

  public void refresh()
  {
    synonyms = null;
  }

  @Override
  public String toString()
  {
    return "JDBCSynonymSchema[name='" + name + "',default=" + _default + ",synthetic=" + synthetic + "]"; // NOI18N
  }

  protected JDBCTable createJDBCTable(String name)
  {
    return new JDBCTable(this, name, false);
  }

  /**
   * Holt die Synonyme aus der Datenbank und erstellt passende Tabellen
   */
  protected void createSynonyms()
  {
    LOGGER.log(Level.FINE, "Initializing synonyms in {0}", this);
    Map<String, Table> newSynonyms = new LinkedHashMap<String, Table>();
    try
    {
      ResultSet rs;
      boolean isOracleConnection = Objects.equals(jdbcCatalog.getJDBCMetadata().getDmd().getClass().getName(), "oracle.jdbc.driver.OracleDatabaseMetaData");
      if (isOracleConnection)
        rs = _getTablesHACKED(jdbcCatalog.getJDBCMetadata().getDmd(), name); // NOI18N
      else
        rs = MetadataUtilities.getTables(jdbcCatalog.getJDBCMetadata().getDmd(), jdbcCatalog.getName(), name, "%", new String[]{"SYNONYM"});

      try
      {
        while (rs.next())
        {
          String synonymName = MetadataUtilities.trimmed(rs.getString("TABLE_NAME")); // NOI18N
          Table table = createJDBCTable(synonymName).getTable();
          newSynonyms.put(synonymName, table);
          LOGGER.log(Level.FINE, "Created synonym {0}", table);
        }
      }
      finally
      {
        if (rs != null)
          rs.close();
      }
    }
    catch (SQLException e)
    {
      throw new MetadataException(e);
    }
    synonyms = Collections.unmodifiableMap(newSynonyms);
  }

  /**
   * Dirty-Hack, damit das ResultSet aus "getTableData" wieder geschlossen wird und nicht offen bleibt
   */
  @Deprecated
  private ResultSet _getTablesHACKED(DatabaseMetaData pDMD, String var2) throws SQLException
  {
    String var6 = "       c.comments AS remarks\n";
    String var7 = "       NULL AS remarks\n";
    String var8 = "  FROM all_objects o, all_tab_comments c\n";
    String var9 = "  FROM all_objects o\n";
    String var10 = "  WHERE o.owner LIKE :1 ESCAPE \'/\'\n    AND o.object_name LIKE :2 ESCAPE \'/\'\n";
    String var11 = "    AND o.owner = c.owner (+)\n    AND o.object_name = c.table_name (+)\n";
    String var13 = "    AND o.object_type IN (\'xxx\', \'SYNONYM\')\n";
    String var14 = "    AND o.object_type IN (\'xxx\')\n";
    String var25 = "  ORDER BY table_type, table_schem, table_name\n";
    String var26 = "SELECT NULL AS table_cat,\n       s.owner AS table_schem,\n       s.synonym_name AS table_name,\n       \'SYNONYM\' AS table_table_type,\n";
    String var27 = "       c.comments AS remarks\n";
    String var28 = "       NULL AS remarks\n";
    String var29 = "  FROM all_synonyms s, all_objects o, all_tab_comments c\n";
    String var20 = "  FROM all_synonyms s, all_objects o\n";
    String var30 = "  WHERE s.owner LIKE :3 ESCAPE \'/\'\n    AND s.synonym_name LIKE :4 ESCAPE \'/\'\n    AND s.table_owner = o.owner\n    AND s.table_name = o.object_name\n    AND o.object_type IN (\'TABLE\', \'VIEW\')\n";
    String var22 = "SELECT NULL AS table_cat,\n       o.owner AS table_schem,\n       o.object_name AS table_name,\n       o.object_type AS table_type,\n";

    Connection connection = pDMD.getConnection();

    boolean remarksReporting;
    boolean restrictGetTables;

    try
    {
      Method remarksReportingMeth = connection.getClass().getMethod("getRemarksReporting");
      Method restrictGetTablesMeth = connection.getClass().getMethod("getRestrictGetTables");
      remarksReportingMeth.setAccessible(true);
      restrictGetTablesMeth.setAccessible(true);
      remarksReporting = (boolean) remarksReportingMeth.invoke(connection);
      restrictGetTables = (boolean) restrictGetTablesMeth.invoke(connection);
      remarksReportingMeth.setAccessible(false);
      restrictGetTablesMeth.setAccessible(false);
    }
    catch (Exception e)
    {
      //Sollte nicht auftreten
      throw new RuntimeException("Methods of \'OracleConnection\' could not be found!", e);
    }

    ResultSet var16 = pDMD.getTableTypes();
    while (var16.next())
      var16.getString(1);
    var16.close();

    if (remarksReporting)
      var22 = var22 + var6 + var8;
    else
      var22 = var22 + var7 + var9;

    var22 = var22 + var10;
    if (remarksReporting)
      var22 = var22 + var14;
    else
      var22 = var22 + var13;

    if (remarksReporting)
      var22 = var22 + var11;

    if (restrictGetTables)
    {
      var22 = var22 + "UNION\n" + var26;
      if (remarksReporting)
        var22 = var22 + var27 + var29;
      else
        var22 = var22 + var28 + var20;

      var22 = var22 + var30;
      if (remarksReporting)
        var22 = var22 + var11;
    }

    var22 = var22 + var25;
    PreparedStatement var23 = connection.prepareStatement(var22);
    var23.setString(1, var2 == null ? "%" : var2);
    var23.setString(2, "%");
    if (restrictGetTables)
    {
      var23.setString(3, var2 == null ? "%" : var2);
      var23.setString(4, "%");
    }

    var23.closeOnCompletion();
    return var23.executeQuery();
  }

  private Map<String, Table> initSynonyms()
  {
    if (synonyms != null)
    {
      return synonyms;
    }
    createSynonyms();
    return synonyms;
  }

}
