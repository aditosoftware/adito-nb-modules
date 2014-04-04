/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2010 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */
package org.netbeans.modules.db.dataview.meta;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.api.db.sql.support.SQLIdentifiers;
import org.netbeans.modules.db.dataview.util.DataViewUtils;

/**
 * Extracts database metadata information (table names and constraints, their
 * associated columns, etc.)
 *
 * @author Ahimanikya Satapathy
 */
public final class DBMetaDataFactory {

    public static final int DB2 = 0;
    public static final int ORACLE = 1;
    public static final int SQLSERVER = 2;
    public static final int JDBC = 3;
    public static final int PostgreSQL = 4;
    public static final int MYSQL = 5;
    public static final int DERBY = 6;
    public static final int SYBASE = 7;
    public static final int AXION = 8;
    public static final int POINTBASE = 9;
    private Connection dbconn;
    private int dbType = -1;
    private DatabaseMetaData dbmeta;

    public DBMetaDataFactory(Connection dbconn) throws SQLException {
        assert dbconn != null;
        this.dbconn = dbconn;
        dbmeta = dbconn.getMetaData();
        dbType = getDBType();
    }

    public boolean supportsLimit() {
        switch (dbType) {
            case MYSQL:
            case PostgreSQL:
            case AXION:
                return true;
            default:
                return false;
        }
    }

    public int getDBType() throws SQLException {
        if (dbType != -1) {
            return dbType;
        }
        // get the database type based on the product name converted to lowercase
        if (dbmeta.getURL() != null) {
            return getDBTypeFromURL(dbmeta.getURL());
        }
        return JDBC;
    }

    private static int getDBTypeFromURL(String url) {
        int dbtype;

        // get the database type based on the product name converted to lowercase
        url = url.toLowerCase();
        if (url.indexOf("sybase") > -1) { // NOI18N
            dbtype = SYBASE;
        } else if (url.indexOf("sqlserver") > -1) { // NOI18N
            dbtype = SQLSERVER;
        } else if (url.indexOf("db2") > -1) { // NOI18N
            dbtype = DB2;
        } else if (url.indexOf("orac") > -1) { // NOI18N
            dbtype = ORACLE;
        } else if (url.indexOf("axion") > -1) { // NOI18N
            dbtype = AXION;
        } else if (url.indexOf("derby") > -1) { // NOI18N
            dbtype = DERBY;
        } else if (url.indexOf("postgre") > -1) { // NOI18N
            dbtype = PostgreSQL;
        } else if (url.indexOf("mysql") > -1) { // NOI18N
            dbtype = MYSQL;
        } else if (url.contains("pointbase")) { // NOI18N
            dbtype = POINTBASE;
        } else {
            dbtype = JDBC;
        }

        return dbtype;
    }

    private DBPrimaryKey getPrimaryKeys(String tcatalog, String tschema, String tname) {
        ResultSet rs = null;
        try {
            rs = dbmeta.getPrimaryKeys(setToNullIfEmpty(tcatalog), setToNullIfEmpty(tschema), tname);
            return new DBPrimaryKey(rs);
        } catch (SQLException e) {
            return null;
        } finally {
            DataViewUtils.closeResources(rs);
        }
    }

    private Map<String, DBForeignKey> getForeignKeys(DBTable table) {
        Map<String, DBForeignKey> fkList = Collections.emptyMap();
        ResultSet rs = null;
        try {
            rs = dbmeta.getImportedKeys(setToNullIfEmpty(table.getCatalog()), setToNullIfEmpty(table.getSchema()), table.getName());
            fkList = DBForeignKey.createForeignKeyColumnMap(table, rs);
        } catch (SQLException e) {
            return null;
        } finally {
            DataViewUtils.closeResources(rs);
        }
        return fkList;
    }

    public synchronized Collection<DBTable> generateDBTables(ResultSet rs, String sql, boolean isSelect) throws SQLException {
        Map<String, DBTable> tables = new LinkedHashMap<String, DBTable>();
        String noTableName = "UNKNOWN"; // NOI18N

        // get table column information
        ResultSetMetaData rsMeta = rs.getMetaData();
        for (int i = 1; i <= rsMeta.getColumnCount(); i++) {
            // #153219 - workaround 
            String tableName = rsMeta.getTableName(i);
            if (tableName == null) {
                tableName = noTableName;
            }
            String schemaName = rsMeta.getSchemaName(i);
            // although Javadoc admit of returning null, SQLite returns null
            if (schemaName == null) {
                schemaName = "";
            }
            String catalogName = rsMeta.getCatalogName(i);
            // although Javadoc admit of returning null, SQLite returns null
            if (catalogName == null) {
                catalogName = "";
            }
            if (schemaName.trim().length() == 0 && catalogName.equals(tableName)) {
                // a workaround for SQLite
                // suppose the catalog shouldn't be same if schema is not supported
                catalogName = ""; // NOI18N
            }
            String key = catalogName + schemaName + tableName;
            if (key.equals("")) {
                key = noTableName;
            }
            DBTable table = tables.get(key);
            if (table == null) {
                table = new DBTable(tableName, schemaName, catalogName);
                tables.put(key, table);
            }

            int sqlTypeCode = rsMeta.getColumnType(i);
            String sqlTypeStr = rsMeta.getColumnTypeName(i);
            if (sqlTypeCode == java.sql.Types.OTHER && dbType == ORACLE) {
                if (sqlTypeStr.startsWith("TIMESTAMP")) { // NOI18N
                    sqlTypeCode = java.sql.Types.TIMESTAMP;
                } else if (sqlTypeStr.startsWith("FLOAT")) { // NOI18N
                    sqlTypeCode = java.sql.Types.FLOAT;
                } else if (sqlTypeStr.startsWith("REAL")) { // NOI18N
                    sqlTypeCode = java.sql.Types.REAL;
                } else if (sqlTypeStr.startsWith("BLOB")) { // NOI18N
                    sqlTypeCode = java.sql.Types.BLOB;
                } else if (sqlTypeStr.startsWith("CLOB")) { // NOI18N
                    sqlTypeCode = java.sql.Types.CLOB;
                }
            }

            String colName = rsMeta.getColumnName(i);
            int position = i;
            int scale = rsMeta.getScale(i);
            int precision;
            try {
                precision = rsMeta.getPrecision(i);
            } catch (NumberFormatException nfe) {
                // Oracle classes12.jar driver throws NumberFormatException while getting precision
                // let's ignore it and set Integer.MAX_VALUE as fallback and log it
                precision = Integer.MAX_VALUE;
                Logger.getLogger(DBMetaDataFactory.class.getName()).log(Level.FINE,
                        "Oracle classes12.jar driver throws NumberFormatException while getting precision, use Integer.MAX_VALUE as fallback.", // NOI18N
                        nfe);
            }

            boolean isNullable = (rsMeta.isNullable(i) == ResultSetMetaData.columnNullable);
            String displayName = rsMeta.getColumnLabel(i);
            int displaySize = rsMeta.getColumnDisplaySize(i);
            boolean autoIncrement = rsMeta.isAutoIncrement(i);

            //Oracle DATE type needs to be retrieved as full date and time
            if (sqlTypeCode == java.sql.Types.DATE && dbType == ORACLE) {
                sqlTypeCode = java.sql.Types.TIMESTAMP;
                displaySize = 22;
            }

            //Handle MySQL BIT(n) where n > 1
            if (sqlTypeCode == java.sql.Types.VARBINARY && dbType == MYSQL) {
                if (sqlTypeStr.startsWith("BIT")) { // NOI18N
                    sqlTypeCode = java.sql.Types.BIT;
                }
            }

            // The SQL Server timestamp type is a JDBC BINARY type with the fixed length of 8 bytes.
            // A Transact-SQL timestamp != an ANSI SQL-92 timestamp.
            // If its a SQL style timestamp you are after use a datetime data type.
            // A T-SQL timestamp are just auto generated binary numbers guarenteed to
            // be unique in the context of a database and are typically used for
            // versioning, not storing dates.

            if (sqlTypeCode == java.sql.Types.BINARY && dbType == SQLSERVER && precision == 8) {
                autoIncrement = true;
            }

            // create a table column and add it to the vector
            //String typeName = typeInfo.containsKey(sqlTypeCode) ? typeInfo.get(sqlTypeCode) : DataViewUtils.getStdSqlType(sqlTypeCode);
            DBColumn col = new DBColumn(table, colName, sqlTypeCode, sqlTypeStr, scale, precision, isNullable, autoIncrement);
            col.setOrdinalPosition(position);
            col.setDisplayName(displayName);
            col.setDisplaySize(displaySize);
            table.addColumn(col);
            table.setQuoter(SQLIdentifiers.createQuoter(dbmeta));
        }

        // Oracle does not return table name for resultsetmetadata.getTableName()
        DBTable table = tables.get(noTableName);
        if (tables.size() == 1 && table != null && isSelect) {
            adjustTableMetadata(sql, tables.get(noTableName));
            for (DBColumn col : table.getColumns().values()) {
                col.setEditable(!table.getName().equals("") && !col.isGenerated());
            }
        }

        DBModel dbModel = new DBModel();
        dbModel.setDBType(getDBType());
        for (DBTable tbl : tables.values()) {
            if (DataViewUtils.isNullString(tbl.getName())) {
                continue;
            }
            checkPrimaryKeys(tbl);
            checkForeignKeys(tbl);
            dbModel.addTable(tbl);
            if (getDBType() != POINTBASE) {  //#173798 - dbmeta.getColumns invalidates rs ResultsSet
                populateDefaults(tbl);
            }
        }

        return tables.values();
    }

    private void populateDefaults(DBTable table) {
        ResultSet rs = null;
        try {
            rs = dbmeta.getColumns(setToNullIfEmpty(table.getCatalog()), setToNullIfEmpty(table.getSchema()), table.getName(), "%");
            while (rs.next()) {
                String defaultValue = rs.getString("COLUMN_DEF"); // NOI18N
                DBColumn col = table.getColumn(rs.getString("COLUMN_NAME")); // NOI18N

                if (col != null && defaultValue != null && defaultValue.trim().length() != 0) {
                    col.setDefaultValue(defaultValue.trim());
                }
            }
        } catch (SQLException e) {
        } finally {
            DataViewUtils.closeResources(rs);
        }
    }

    private void adjustTableMetadata(String sql, DBTable table) {
        String tableName = "";
        if (sql.toUpperCase().contains("FROM")) { // NOI18N
            // User may type "FROM" in either lower, upper or mixed case
            String[] splitByFrom = sql.toUpperCase().split("FROM"); // NOI18N
            String fromsql = sql.substring(sql.length() - splitByFrom[1].length());
            if (fromsql.toUpperCase().contains("WHERE")) { // NOI18N
                splitByFrom = fromsql.toUpperCase().split("WHERE"); // NOI18N
                fromsql = fromsql.substring(0, splitByFrom[0].length());
            } else if (fromsql.toUpperCase().contains("ORDER BY")) { // NOI18N
                splitByFrom = fromsql.toUpperCase().split("ORDER BY"); // NOI18N
                fromsql = fromsql.substring(0, splitByFrom[0].length());
            }
            if (!sql.toUpperCase().contains("JOIN")) { // NOI18N
                StringTokenizer t = new StringTokenizer(fromsql, ","); // NOI18N

                if (t.hasMoreTokens()) {
                    tableName = t.nextToken().trim();
                }

                if (t.hasMoreTokens()) {
                    tableName = "";
                }
            }
        }
        String[] splitByDot = tableName.split("\\."); // NOI18N
        if (splitByDot.length == 3) {
            table.setCatalogName(unQuoteIfNeeded(splitByDot[0]));
            table.setSchemaName(unQuoteIfNeeded(splitByDot[1]));
            table.setName(unQuoteIfNeeded(splitByDot[2]));
        } else if (splitByDot.length == 2) {
            table.setSchemaName(unQuoteIfNeeded(splitByDot[0]));
            table.setName(unQuoteIfNeeded(splitByDot[1]));
        } else if (splitByDot.length == 1) {
            table.setName(unQuoteIfNeeded(splitByDot[0]));
        }
    }

    private String unQuoteIfNeeded(String id) {
        String quoteStr = "\""; // NOI18N
        try {
            quoteStr = dbmeta.getIdentifierQuoteString().trim();
        } catch (SQLException e) {
        }
        return id.replaceAll(quoteStr, "");
    }

    private void checkPrimaryKeys(DBTable newTable) {
        DBPrimaryKey keys = getPrimaryKeys(newTable.getCatalog(), newTable.getSchema(), newTable.getName());
        if (keys != null && keys.getColumnCount() != 0) {
            newTable.setPrimaryKey(keys);

            // now loop through all the columns flagging the primary keys
            List<DBColumn> columns = newTable.getColumnList();
            if (columns != null) {
                for (int i = 0; i < columns.size(); i++) {
                    DBColumn col = columns.get(i);
                    if (keys.contains(col.getName())) {
                        col.setPrimaryKey(true);
                    }
                }
            }
        }
    }

    private void checkForeignKeys(DBTable newTable) {
        // get the foreing keys
        Map<String, DBForeignKey> foreignKeys = getForeignKeys(newTable);
        if (foreignKeys != null && !foreignKeys.isEmpty()) {
            newTable.setForeignKeyMap(foreignKeys);

            // create a hash set of the keys
            Set<String> foreignKeysSet = new HashSet<String>();
            Iterator<DBForeignKey> it = foreignKeys.values().iterator();
            while (it.hasNext()) {
                DBForeignKey key = it.next();
                if (key != null) {
                    foreignKeysSet.addAll(key.getColumnNames());
                }
            }

            // now loop through all the columns flagging the foreign keys
            List<DBColumn> columns = newTable.getColumnList();
            if (columns != null) {
                for (int i = 0; i < columns.size(); i++) {
                    DBColumn col = columns.get(i);
                    if (foreignKeysSet.contains(col.getName())) {
                        col.setForeignKey(true);
                    }
                }
            }
        }
    }

    private String setToNullIfEmpty(String source) {
        if (source != null && source.equals("")) {
            source = null;
        }
        return source;
    }
}
