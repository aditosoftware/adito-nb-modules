package org.netbeans.adito.db;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.common.*;
import org.netbeans.modules.db.explorer.DatabaseConnection;
import org.netbeans.modules.db.explorer.action.BaseAction;
import org.openide.nodes.Node;
import org.openide.util.*;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Aktion die das Anlegen der Systemtabellen ermöglicht.
 *
 * @author Thomas Tasior 11.12.12, 09:41
 */
public class CreateSystemTablesAction extends BaseAction
{
  private final IAditoNetbeansTranslations trans = NbAditoInterface.lookup(IAditoNetbeansTranslations.class);

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
        NbAditoInterface.lookup(IAditoExternalWizardsProvider.class).createSystemTableWizard(node.getLookup(), findSchemaWorkingName(node.getLookup()));
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

}
