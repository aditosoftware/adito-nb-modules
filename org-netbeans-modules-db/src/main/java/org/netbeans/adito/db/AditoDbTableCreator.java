package org.netbeans.adito.db;


import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.database.*;
import org.jetbrains.annotations.*;
import org.netbeans.lib.ddl.impl.Specification;
import org.netbeans.modules.db.explorer.*;
import org.netbeans.modules.db.explorer.dlg.*;

import java.util.List;

/**
 * Zum Erstellen von Tabellen in einer Datenbank.
 *
 * @author J. Boesl, 11.02.13
 */
public class AditoDbTableCreator
{
  private AditoDbTableCreator()
  {
  }

  /**
   * Erstellt eine Tabelle in einer Datenbank.
   *
   * @param pDatabaseConnection die Verbindung zu einer Datenbank.
   * @param pSchema             das Schema bei dem die Tabelle erstellt werden soll.
   * @param pTableToCreate      die Beschreibung der Tabelle die erstellt werden soll.
   * @throws DdlExecutionException wenn ein Fehler beim Ausführen des Statements auftritt.
   */
  public static void createTable(@NotNull org.netbeans.api.db.explorer.DatabaseConnection pDatabaseConnection,
                                 @Nullable String pSchema, @NotNull IAditoDbTable pTableToCreate) throws DdlExecutionException
  {
    DatabaseConnection internalDbConnection = null;
    for (DatabaseConnection dbCon : ConnectionList.getDefault().getConnections())
    {
      if (pDatabaseConnection.equals(dbCon.getDatabaseConnection()))
      {
        internalDbConnection = dbCon;
        break;
      }
    }
    if (internalDbConnection == null)
      throw new IllegalStateException("unknown connection: " + pDatabaseConnection.toString());

    createTable(internalDbConnection, pSchema, pTableToCreate);
  }

  /**
   * Erstellt eine Tabelle in einer Datenbank.
   *
   * @param pDatabaseConnection die Verbindung zu einer Datenbank.
   * @param pSchema             das Schema bei dem die Tabelle erstellt werden soll.
   * @param pTableToCreate      die Beschreibung der Tabelle die erstellt werden soll.
   * @throws DdlExecutionException wenn ein Fehler beim Ausführen des Statements auftritt.
   */
  public static void createTable(@NotNull DatabaseConnection pDatabaseConnection, @Nullable String pSchema,
                                 @NotNull IAditoDbTable pTableToCreate) throws DdlExecutionException
  {
    final Specification spec = pDatabaseConnection.getConnector().getDatabaseSpecification();
    CreateTableDDL ddl = new CreateTableDDL(spec, pSchema, pTableToCreate.getName());

    try
    {
      List<ColumnItem> primaries = null;
      List<IAditoDbColumn> primaryC = pTableToCreate.getPrimaryKeyColumns();
      if (primaryC.size() > 1)
        primaries = ColumnItemCreator.toColumnItems(primaryC, spec);

      ddl.execute(ColumnItemCreator.toColumnItems(pTableToCreate.getColumns(), spec), primaries);
    }
    catch (Exception e)
    {
      throw new DdlExecutionException(e);
    }
    checkAndCreateSysDbVersionEntry(pDatabaseConnection, pSchema, pTableToCreate.getName());
  }

  /**
   * Erstellt optional für eine Tabelle, beschrieben durch ihren Namen, Standardeinträge.
   *
   * @param pDatabaseConnection benötigt, um etwas in die Datenbank zu schreiben.
   * @param pSchema             ein optionales Schema, in dem sich die Tabelle befindet.
   * @param pTableName          der Name der Tabelle, die evtl. mit Standardeinträge angereichert wird.
   */
  public static void checkAndCreateSysDbVersionEntry(@NotNull DatabaseConnection pDatabaseConnection,
                                                     @Nullable String pSchema, @NotNull String pTableName)
  {
    IAditoDbTableCreator tableCreator = NbAditoInterface.lookup(IAditoDbTableCreator.class);
    tableCreator.createDefaultEntries(pDatabaseConnection.getJDBCConnection(), pSchema, pTableName);
  }

}