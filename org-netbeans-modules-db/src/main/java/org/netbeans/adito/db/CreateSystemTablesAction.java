package org.netbeans.adito.db;

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
import javax.swing.table.*;
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

                CreateTableDialog.showDialogAndCreate(spec, schema, columnItems, table.getName());
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
   * Panel mit der Tabelle die die Namen der Systemtabellen anzeigt.
   */
  private class TableSelectionPanel extends JPanel
  {
    private SelectionModel model;

    private TableSelectionPanel()
    {
      super(new BorderLayout());
      add(_createTable());
    }

    private JScrollPane _createTable()
    {
      model = new SelectionModel();
      JTable table = new JTable(model);
      table.setFillsViewportHeight(true);

      TableColumnModel tcm = table.getColumnModel();
      TableColumn tc = tcm.getColumn(0);

      int w = 26;
      tc.setPreferredWidth(w);
      tc.setMaxWidth(w);
      tc.setResizable(false);

      table.getTableHeader().setReorderingAllowed(false);

      return new JScrollPane(table);
    }

    /**
     * Liefert die Selektion zurück.
     *
     * @return die Konstanten der seklektierten Tabellen.
     */
    public List<String> getSelection()
    {
      return model.getSelection();
    }
  }


  private class SelectionModel extends AbstractTableModel
  {
    final int CREATE = 0;
    final int TABLENAME = 1;

    private String[] columnNames;
    private Class[] columnClasses;
    private List<Row> data;

    private SelectionModel()
    {
      columnNames = new String[]{" ", trans.tableName()};
      columnClasses = new Class[columnNames.length];
      columnClasses[CREATE] = Boolean.class;
      columnClasses[TABLENAME] = String.class;

      data = new ArrayList<>();
      List<String> systemTableNames = NbAditoInterface.lookup(IAditoDbInfo.class).getSystemTableNames();
      Collections.sort(systemTableNames);
      for (String value : systemTableNames)
        data.add(new Row(value));
    }

    @Override
    public String getColumnName(int column)
    {
      return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
      return columnClasses[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
      return columnIndex == CREATE;
    }

    @Override
    public int getRowCount()
    {
      return data.size();
    }

    @Override
    public int getColumnCount()
    {
      return columnNames.length;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
      Row row = data.get(rowIndex);
      switch (columnIndex)
      {
        case CREATE:
          row.create(aValue);
          fireTableDataChanged();
          break;
      }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
      Row row = data.get(rowIndex);
      switch (columnIndex)
      {
        case CREATE:
          return row.mustCreate();

        case TABLENAME:
          return row.getTableName();

      }
      return null;
    }

    public List<String> getSelection()
    {
      List<String> helper = new ArrayList<>();
      for (Row row : data)
      {
        if (row.mustCreate())
          helper.add(row.table);
      }
      return helper;
    }
  }

  private class Row
  {
    Object create = Boolean.FALSE;
    private final String table;

    public Row(String pTable)
    {
      table = pTable;
    }

    public boolean mustCreate()
    {
      return Boolean.TRUE.equals(create);
    }


    public void create(Object pCreate)
    {
      create = pCreate;
    }

    public String getTableName()
    {
      return table;
    }
  }


}
