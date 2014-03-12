package org.netbeans.adito.db;

import org.netbeans.modules.db.metadata.model.MetadataUtilities;
import org.netbeans.modules.db.metadata.model.api.*;
import org.netbeans.modules.db.metadata.model.jdbc.*;
import org.netbeans.modules.db.metadata.model.spi.CatalogImplementation;

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


  public JDBCSynonymSchema(CatalogImplementation catalog, String name, boolean _default, boolean synthetic) {
    super((JDBCCatalog)catalog, name, _default, synthetic);
  }

  /**
   * Gibt die vorhandenen Synonyme zurück
   *
   * @return die Synonyme
   */
  public final Collection<Table> getSynonyms() {
    return initSynonyms().values();
  }

  public final Table getSynonym(String name) {
    return MetadataUtilities.find(name, initSynonyms());
  }

  public void refresh() {
    synonyms = null;
  }

  @Override
  public String toString() {
    return "JDBCSynonymSchema[name='" + name + "',default=" + _default + ",synthetic=" + synthetic + "]"; // NOI18N
  }

  protected JDBCTable createJDBCTable(String name) {
    return new JDBCTable(this, name);
  }

  /**
   * Holt die Synonyme aus der Datenbank und erstellt passende Tabellen
   */
  protected void createSynonyms() {
    LOGGER.log(Level.FINE, "Initializing synonyms in {0}", this);
    Map<String, Table> newSynonyms = new LinkedHashMap<String, Table>();
    try {
      ResultSet rs = MetadataUtilities.getTables(jdbcCatalog.getJDBCMetadata().getDmd(),
                                                 jdbcCatalog.getName(), name, "%", new String[]{"SYNONYM"}); // NOI18N
      try {
        while (rs.next()) {
          String synonymName = MetadataUtilities.trimmed(rs.getString("TABLE_NAME")); // NOI18N
          Table table = createJDBCTable(synonymName).getTable();
          newSynonyms.put(synonymName, table);
          LOGGER.log(Level.FINE, "Created synonym {0}", table);
        }
      } finally {
        if (rs != null) {
          rs.close();
        }
      }
    } catch (SQLException e) {
      throw new MetadataException(e);
    }
    synonyms = Collections.unmodifiableMap(newSynonyms);
  }

  private Map<String, Table> initSynonyms() {
    if (synonyms != null) {
      return synonyms;
    }
    createSynonyms();
    return synonyms;
  }

}
