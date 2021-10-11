package de.adito.swing;

import org.jetbrains.annotations.*;
import org.openide.util.NbBundle;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.List;
import java.util.function.*;

/**
 * Component to select a path
 *
 * @author w.glanzer
 */
public class PathSelectionPanel extends JPanel
{
  private final JTextComponent path;
  private final String fileChooserTitle;
  private final int fileChooserType;
  private final FileFilter filter;
  private JComboBox<String> entries;

  public PathSelectionPanel(@NotNull String pFileChooserTitle, int pFileChooserType, JButton... pAdditionalButtons)
  {
    this(pFileChooserTitle, pFileChooserType, (FileFilter) null, pAdditionalButtons);
  }

  public PathSelectionPanel(@NotNull String pFileChooserTitle, int pFileChooserType, @Nullable FileFilter pFilter, JButton... pAdditionalButtons)
  {
    super(new BorderLayout(5, 0));
    fileChooserTitle = pFileChooserTitle;
    fileChooserType = pFileChooserType;
    filter = pFilter;
    path = new JTextField();
    _init(path, pAdditionalButtons);
  }

  public PathSelectionPanel(@NotNull String pFileChooserTitle, int pFileChooserType, @NotNull List<String> pEntries, JButton... pAdditionalButtons)
  {
    this(pFileChooserTitle, pFileChooserType, pEntries, null, pAdditionalButtons);
  }

  public PathSelectionPanel(@NotNull String pFileChooserTitle, int pFileChooserType, @NotNull List<String> pEntries, @Nullable FileFilter pFilter,
                            JButton... pAdditionalButtons)
  {
    super(new BorderLayout(5, 0));
    fileChooserTitle = pFileChooserTitle;
    fileChooserType = pFileChooserType;
    filter = pFilter;
    entries = new JComboBox<>(new DefaultComboBoxModel<>());
    entries.setEditable(true);
    path = (JTextComponent) entries.getEditor().getEditorComponent();
    _init(entries, pAdditionalButtons);
    setEntries(pEntries);
  }

  public void setEntries(@NotNull List<String> pEntries)
  {
    if (entries == null)
      throw new IllegalArgumentException("You are setting entries, but this is not allowed in a non-combobox editor");
    entries.setModel(new DefaultComboBoxModel<>(pEntries.toArray(new String[0])));
  }

  public void setValue(@NotNull String pValue)
  {
    if (entries != null)
      entries.setSelectedItem(pValue);
    else
      path.setText(pValue);
  }

  @NotNull
  public String getValue()
  {
    if (entries != null && entries.getSelectedItem() != null)
      return (String) entries.getSelectedItem();
    return path.getText();
  }

  public void addDocumentListener(@NotNull DocumentListener pListener)
  {
    path.getDocument().addDocumentListener(pListener);
  }

  private void _init(JComponent pComp, JButton... pAdditionalButtons)
  {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
    panel.add(_createBrowseButton(this, this::getValue, this::setValue));
    for (JButton btn : pAdditionalButtons)
      panel.add(btn);
    add(pComp, BorderLayout.CENTER);
    add(panel, BorderLayout.EAST);
  }

  /**
   * @return creates the browse button to search for a valid nodejs installation
   */
  @NbBundle.Messages({
      "LBL_Browse=Browse...",
  })
  @NotNull
  private JButton _createBrowseButton(@NotNull JComponent pParent, @NotNull Supplier<String> pDefaultPath, @NotNull Consumer<String> pOnFileSelected)
  {
    JButton btn = new JButton(Bundle.LBL_Browse());
    btn.addActionListener(e -> {
      JFileChooser chooser = new JFileChooser(pDefaultPath.get());
      chooser.setFileHidingEnabled(false);
      chooser.setDialogTitle(fileChooserTitle);
      chooser.setFileSelectionMode(fileChooserType);
      chooser.setFileFilter(filter);
      int result = chooser.showOpenDialog(pParent);
      if (result == JFileChooser.APPROVE_OPTION && chooser.getSelectedFile() != null)
        pOnFileSelected.accept(chooser.getSelectedFile().getAbsolutePath());
    });
    return btn;
  }
}
