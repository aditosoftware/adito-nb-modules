package org.netbeans.adito.db;

import com.jidesoft.swing.*;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.common.IAditoNetbeansTranslations;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.database.*;
import org.netbeans.lib.ddl.impl.Specification;
import org.netbeans.modules.db.explorer.DatabaseConnection;
import org.netbeans.modules.db.explorer.action.*;
import org.netbeans.modules.db.explorer.dlg.*;
import org.openide.*;
import org.openide.nodes.Node;
import org.openide.util.*;
import org.openide.util.actions.SystemAction;
import org.openide.windows.WindowManager;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.text.MessageFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Aktion die das Anlegen der Systemtabellen ermöglicht.
 *
 * @author Thomas Tasior 11.12.12, 09:41
 */
public class CreateSystemTablesAction extends BaseAction
{
  private IAditoNetbeansTranslations trans = NbAditoInterface.lookup(IAditoNetbeansTranslations.class);
  private String create = trans.create();
  private String config = trans.config();
  private String cancel = trans.cancel();

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

    Node node = nodeHolder.get();
    if (node != null)
    {
      Object[] buttons = {create, config, cancel};

      String title = trans.getCreateSystemTablesAction();
      TableSelectionPanel panel = new TableSelectionPanel();
      DialogDescriptor descriptor = new DialogDescriptor(panel, title, true, buttons, create,
                                                         DialogDescriptor.BOTTOM_ALIGN, null, null);

      Dialog dialog = DialogDisplayer.getDefault().createDialog(descriptor);
      descriptor.setButtonListener(new ButtonListener(dialog, panel, node));
      dialog.setLocationRelativeTo(WindowManager.getDefault().getMainWindow());
      dialog.setVisible(true);
    }
  }

  /**
   * Legt die vorher im Dialog selektierten Tabellen an
   *
   * @param pNode      enthält Informationen über die Datenbank die zum Anlegen benötigt werden.
   * @param pSelection die ausgewählten Tabellen.
   */
  private void perform(final Node pNode, final List<String> pSelection)
  {
    try
    {
      DatabaseConnection connection = pNode.getLookup().lookup(DatabaseConnection.class);
      String schema = findSchemaWorkingName(pNode.getLookup());

      IAditoDbInfo dbInfo = NbAditoInterface.lookup(IAditoDbInfo.class);

      int count = 0;
      for (; count < pSelection.size(); count++)
      {
        String systemTable = pSelection.get(count);
        IAditoDbTable table = dbInfo.getTable(connection.getDriver(), systemTable);
        AditoDbTableCreator.createTable(connection, schema, table);
      }
      SystemAction.get(RefreshAction.class).performAction(new Node[]{pNode});
      dbInfo.notify(MessageFormat.format(trans.tooltipTableWereCreated(), count));
    }
    catch (Exception e)
    {
      NbAditoInterface.lookup(IAditoDbInfo.class).notifyException(e);
    }
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
   * Hört auf die Buttonklicks im Dialog
   */
  private class ButtonListener implements ActionListener
  {
    private final Dialog dialog;
    private final TableSelectionPanel panel;
    private final Node node;

    public ButtonListener(Dialog pDialog, TableSelectionPanel pPanel, Node pNode)
    {
      dialog = pDialog;
      panel = pPanel;
      node = pNode;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
      dialog.dispose();
      final List<String> selection = panel.getSelection();
      String cmd = e.getActionCommand();
      if (cmd.equals(create) & (selection.size() > 0))//Nur anlegen
      {
        RequestProcessor.getDefault().post(new Runnable()
        {
          @Override
          public void run()
          {
            perform(node, selection);
          }
        });
      }
      else if (cmd.equals(config) & (selection.size() > 0))//Für jede Tabelle wird der Dialog zum Tabellen anlegen angezeigt
      {
        RequestProcessor.getDefault().post(new Runnable()
        {
          @Override
          public void run()
          {
            try
            {
              DatabaseConnection connection = node.getLookup().lookup(DatabaseConnection.class);
              String schema = findSchemaWorkingName(node.getLookup());
              Specification spec = connection.getConnector().getDatabaseSpecification();
              IAditoDbInfo dbInfo = NbAditoInterface.lookup(IAditoDbInfo.class);

              for (String systemTable : selection)
              {
                IAditoDbTable table = dbInfo.getTable(connection.getDriver(), systemTable);
                List<ColumnItem> columnItems = ColumnItemCreator.toColumnItems(table.getColumns(), spec);

                if (CreateTableDialog.showDialogAndCreate(spec, schema, columnItems, table.getName()))
                  AditoDbTableCreator.checkAndCreateSysDbVersionEntry(connection, schema, table.getName());
              }
            }
            catch (Exception e1)
            {
              NbAditoInterface.lookup(IAditoDbInfo.class).notifyException(e1);
            }
            SystemAction.get(RefreshAction.class).performAction(new Node[]{node});
          }
        });
      }
    }
  }

  /**
   * Panel mit dem Baum der die Namen der Systemtabellen anzeigt.
   */
  private class TableSelectionPanel extends JPanel
  {
    private final String ROOT = "ROOT";
    private CheckBoxTreeSelectionModel cbModel;

    private TableSelectionPanel()
    {
      super(new BorderLayout());
      add(_createTree());
      setPreferredSize(new Dimension(350, 860));
    }

    private JScrollPane _createTree()
    {
      final CheckBoxTree tree = new CheckBoxTree(buildTreeModel());

      DefaultTreeCellRenderer r = new DefaultTreeCellRenderer();
      r.setIconTextGap(5);
      //Ein Icon ohne Inhalt
      ImageIcon i = new ImageIcon("R0lGODdhAQABAIABANQAI////ywAAAAAAQABAAACAkQBADs=".getBytes());
      r.setIcon(i);
      r.setLeafIcon(i);
      r.setOpenIcon(i);
      r.setClosedIcon(i);
      tree.setCellRenderer(r);

      tree.setRootVisible(false);
      tree.setRowHeight(17);
      cbModel = tree.getCheckBoxTreeSelectionModel();
      _expand(tree);
      return new JScrollPane(tree);
    }

    /**
     * Expandiert den Baum
     */
    private void _expand(JTree pTree)
    {
      TreeNode root = (TreeNode) pTree.getModel().getRoot();
      if (root != null)
      {
        // Traverse tree from root
        expandPath(pTree, new TreePath(root));
      }
    }

    /**
     * Expandiert oder kollabiert den Pfad parent.
     * Der Methodenname entspricht nicht mehr dem Original im Javaalmanac.
     *
     * @param tree   Der Baum auf dem die Methode angewendet werden soll.
     * @param parent Der Pfad der manipuliert werden soll.
     */
    private void expandPath(JTree tree, TreePath parent)
    {
      // Traverse children
      TreeNode node = (TreeNode) parent.getLastPathComponent();
      if (node.getChildCount() >= 0)
      {
        for (Enumeration e = node.children(); e.hasMoreElements(); )
        {
          TreeNode n = (TreeNode) e.nextElement();
          TreePath path = parent.pathByAddingChild(n);
          expandPath(tree, path);
        }
      }
      tree.expandPath(parent);
    }

    protected TreeModel buildTreeModel()
    {
      DefaultMutableTreeNode root = new DefaultMutableTreeNode(ROOT);
      DefaultMutableTreeNode parent;

      Map<String, List<String>> map = NbAditoInterface.lookup(IAditoDbInfo.class).getSystemTableNamesGrouped();
      for (String group : map.keySet())
      {
        parent = new DefaultMutableTreeNode(group);
        root.add(parent);
        List<String> tables = map.get(group);
        for (String table : tables)
        {
          parent.add(new DefaultMutableTreeNode(table));
        }
      }
      return new DefaultTreeModel(root);
    }

    /**
     * Liefert die Selektion zurück.
     *
     * @return die Konstanten der seklektierten Tabellen.
     */
    public List<String> getSelection()
    {
      Set<String> groups = new HashSet<>();
      Collections.addAll(groups, IAditoDbInfo.OTHER,
                         IAditoDbInfo.CALENDAR,
                         IAditoDbInfo.FARM,
                         IAditoDbInfo.MAILREPOSIT,
                         IAditoDbInfo.WORKFLOW,
                         IAditoDbInfo.XMPP);
      List<String> tableNames = new ArrayList<>();
      TreePath[] paths = cbModel.getSelectionPaths();

      for (TreePath path : paths)
      {
        DefaultMutableTreeNode n = (DefaultMutableTreeNode) path.getLastPathComponent();
        String s = n.toString();
        if (s.equals(ROOT))
          _fetchGroups(n, tableNames);
        else if (groups.contains(s))
          _fetchChildren(n, tableNames);
        else
          tableNames.add(s);
      }

      return tableNames;
    }
  }

  private void _fetchGroups(DefaultMutableTreeNode pRoot, List<String> pTableNames)
  {
    for (int i = 0; i < pRoot.getChildCount(); i++)
    {
      _fetchChildren((DefaultMutableTreeNode) pRoot.getChildAt(i), pTableNames);
    }
  }

  private void _fetchChildren(DefaultMutableTreeNode pParent, List<String> pTableNames)
  {
    for (int i = 0; i < pParent.getChildCount(); i++)
    {
      pTableNames.add(pParent.getChildAt(i).toString());
    }
  }
}
