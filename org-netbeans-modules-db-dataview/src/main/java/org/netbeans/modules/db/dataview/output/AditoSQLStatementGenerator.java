package org.netbeans.modules.db.dataview.output;

import org.netbeans.modules.db.dataview.meta.*;

/**
 * Wrapper-Klasse f�r den SQLStatementGenerator von Netbeans.
 * Diese Klasse soll m�glichst wenig ver�ndert werden, die sie aus der Bibliothek �bernommen wurde.
 *
 * @author a.mayr, 01.03.2018.
 */
public class AditoSQLStatementGenerator extends SQLStatementGenerator
{

  private static final int MAX_PRECISION = 16383;
  private static final String NAME_MARIADB_JDBC = "mariadb-jdbc";
  private static final String NAME_MARIADB = "MariaDB";

  private String driverName;

  public AditoSQLStatementGenerator(String pDriverName)
  {
    driverName = pDriverName;
  }

  /**
   * Sonderbehandlung f�r MySQL Datenbanken.
   * F�r den Fall dass eine MySQL Datenbank mit dem MariaDB Treiber verwendet wird,
   * muss die L�nge von VARCHAR Spalten beschr�nkt werden.
   */
  protected int adjustPrecisionForMySql(DBTable pTable, DBColumn pColumn, int pPrecision)
  {
    int precision = pPrecision;

    // Bei MariaDB ist precision die Anzahl der ben�tigten Bytes
    if(pTable.getParentObject() != null && pTable.getParentObject().getDBType() == DBMetaDataFactory.MYSQL)
    {
      // multi-byte charsets brauchen 4 bytes, deswegen beschr�nken auf 65535 / 4 = 16383
      precision = _getPrecision(pColumn);
    }
    return precision;
  }

  private int _getPrecision(DBColumn col)
  {
    int precision = col.getDisplaySize();
    return _isMariaDBDriver() ? Math.min(precision, MAX_PRECISION) : precision;
  }

  private boolean _isMariaDBDriver()
  {
    return driverName.toLowerCase().contains(NAME_MARIADB.toLowerCase());
  }

}
