package org.netbeans.adito.db;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.common.*;
import org.netbeans.modules.db.explorer.DatabaseConnection;
import org.netbeans.modules.db.explorer.action.BaseAction;
import org.netbeans.modules.db.metadata.model.api.*;
import org.openide.nodes.Node;
import org.openide.util.*;

import java.util.concurrent.atomic.*;
import java.util.logging.*;

/**
 * Aktion die das Anlegen der Systemtabellen ermöglicht.
 *
 * @author Thomas Tasior 11.12.12, 09:41
 */
public class CreateSystemTablesAction extends BaseAction
{
  private final IAditoNetbeansTranslations trans = NbAditoInterface.lookup(IAditoNetbeansTranslations.class);
  private final RequestProcessor processor = new RequestProcessor();

  @Override
  protected void performAction(Node[] activatedNodes)
  {
    final AtomicReference<Node> nodeHolder = new AtomicReference<>(null);
    for (Node theNode : activatedNodes)
    {
      //Suche nach einem Node mit einer DatabaseConnection
      if (theNode.getLookup().lookup(DatabaseConnection.class) != null)
      {
        nodeHolder.set(theNode);
        break;
      }
    }

    RequestProcessor.getDefault().post(() -> {
      Node node = nodeHolder.get();
      if (node != null)
      {
        IAditoExternalWizardsProvider wizardProvider = NbAditoInterface.lookup(IAditoExternalWizardsProvider.class);
        Lookup lookup = node.getLookup();
        String schema = findSchemaWorkingName(lookup);
        wizardProvider.createSystemTableWizard(lookup, schema, pTable -> _tableExists(lookup, pTable));
      }
    });
  }

  @Override
  protected boolean enable(Node[] activatedNodes)
  {
    for (Node n : activatedNodes)
    {
      DatabaseConnection connection = n.getLookup().lookup(DatabaseConnection.class);
      if (connection != null)
      {
        return true;
      }
    }
    return false;
  }

  @Override
  public String getName()
  {
    return trans.getCreateSystemTablesAction();
  }

  @Override
  public HelpCtx getHelpCtx()
  {
    return HelpCtx.DEFAULT_HELP;
  }

  /**
   * @return Gibt zurück, ob eine Tabelle mit dem übergebenen Namen im DefaultSchema bereits existiert.
   * Wirft KEINE Exception, gibt <tt>false</tt> zurück wenn Fehler
   */
  private boolean _tableExists(Lookup pLookup, String pTableName)
  {
    try
    {
      return processor.submit(() -> {
        AtomicBoolean result = new AtomicBoolean(false);

        try
        {
          MetadataModel model = pLookup.lookup(DatabaseConnection.class).getMetadataModel();
          MetadataElementHandle handle = pLookup.lookup(MetadataElementHandle.class);
          model.runReadAction(pMetaData -> {
            Catalog catalog = pMetaData.getDefaultCatalog();
            if (catalog != null)
            {
              Schema schema = (Schema) handle.resolve(pMetaData);
              if (schema != null)
              {
                Table tableInfo = schema.getTable(pTableName);
                result.set(tableInfo != null);
              }
            }
          });
        }
        catch (Exception e)
        {
          Logger.getLogger(CreateSystemTablesAction.class.getName()).log(Level.WARNING, "", e);
        }

        return result.get();
      }).get();
    }
    catch(Exception e)
    {
      Logger.getLogger(CreateSystemTablesAction.class.getName()).log(Level.WARNING, "", e);
      return false;
    }
  }

}
