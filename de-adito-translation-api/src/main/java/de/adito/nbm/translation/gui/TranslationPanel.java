package de.adito.nbm.translation.gui;

import de.adito.nbm.translation.api.*;
import de.adito.swing.TableLayoutUtil;
import info.clearthought.layout.TableLayout;
import org.jetbrains.annotations.*;
import org.openide.util.NbBundle;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author w.glanzer, 08.09.2021
 */
public class TranslationPanel extends JPanel
{
  private static final String _TRANSLATORTYPE = NbBundle.getMessage(TranslationPanel.class, "TITLE_TranslatorType");
  private static final String _SOURCE_LANG = NbBundle.getMessage(TranslationPanel.class, "TITLE_SourceLang");
  private static final String _TARGET_LANG = NbBundle.getMessage(TranslationPanel.class, "TITLE_TargetLang");
  private static final String _LINEBREAK = NbBundle.getMessage(TranslationPanel.class, "TITLE_Linebreak");

  private final JComboBox<Locale> fromLangCombo;
  private final JComboBox<Locale> toLangCombo;
  private final JComboBox<ELineBreakMethod> linebreakCombo;
  private final JComboBox<ETranslatorType> translatorCombo;

  protected TranslationPanel(@Nullable ETranslatorType[] pTypes, @Nullable Locale pTargetLocaleDefaultValue)
  {
    double pref = TableLayout.PREFERRED;
    double gap = 3;

    double[] cols = {gap, pref, gap, pref, gap};
    double[] rows = {gap,
                     pref,
                     gap,
                     pref,
                     gap,
                     pref,
                     gap,
                     pref,
                     gap,
                     pref
    };

    setLayout(new TableLayout(cols, rows));
    TableLayoutUtil tlu = new TableLayoutUtil(this);
    tlu.add(1, 1, new JLabel(_TRANSLATORTYPE + ":"));
    translatorCombo = new JComboBox<>(pTypes == null ? ETranslatorType.values() : pTypes);
    tlu.add(3, 1, translatorCombo);

    ListCellRenderer<Object> comboRenderer = new DefaultListCellRenderer()
    {
      @Override
      public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
      {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        String displayLang = ((Locale) value).getDisplayLanguage();
        setText(displayLang != null && !displayLang.isEmpty() ? displayLang : " ");
        return this;
      }
    };

    tlu.add(1, 3, new JLabel(_SOURCE_LANG + ":"));
    fromLangCombo = new JComboBox<>(_getLocales(false));
    fromLangCombo.setRenderer(comboRenderer);
    tlu.add(3, 3, fromLangCombo);

    tlu.add(1, 5, new JLabel(_TARGET_LANG + ":"));
    toLangCombo = new JComboBox<>(_getLocales(true));
    toLangCombo.setRenderer(comboRenderer);
    if (pTargetLocaleDefaultValue != null)
      toLangCombo.setSelectedItem(pTargetLocaleDefaultValue);
    else
      toLangCombo.setSelectedItem(Locale.ENGLISH);
    tlu.add(3, 5, toLangCombo);

    tlu.add(1, 7, new JLabel(_LINEBREAK + ":"));
    linebreakCombo = new JComboBox<>(ELineBreakMethod.values());
    linebreakCombo.setSelectedItem(ELineBreakMethod.LINEBREAK_TO_SINGLE_REQUEST); //default
    linebreakCombo.setRenderer(new DefaultListCellRenderer()
    {
      @Override
      public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
      {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        setText(((ELineBreakMethod) value).getDisplayName());
        return this;
      }
    });
    tlu.add(3, 7, linebreakCombo);

    JComponent additionalComponents = createAdditional();
    JPanel additionalComponentContainer = new JPanel(new BorderLayout());
    additionalComponentContainer.setBorder(new EmptyBorder(4, 4, 4, 4));
    if (additionalComponents != null)
      additionalComponentContainer.add(additionalComponents, BorderLayout.CENTER);
    tlu.add(1, 9, 3, 9, additionalComponentContainer);
  }

  /**
   * @return the result containing all set "properties"
   */
  @NotNull
  public TranslationDialog.TranslationResult getResult()
  {
    Locale fromLang = (Locale) fromLangCombo.getSelectedItem();
    if (fromLang == null || fromLang.equals(Locale.ROOT))
      fromLang = null;

    Locale toLang = (Locale) toLangCombo.getSelectedItem();
    ELineBreakMethod lbMethod = (ELineBreakMethod) linebreakCombo.getSelectedItem();
    ETranslatorType tType = (ETranslatorType) translatorCombo.getSelectedItem();
    return new TranslationDialog.TranslationResult(fromLang, toLang, lbMethod, tType);
  }

  /**
   * Can be overridden, if components should be added to the panel
   *
   * @return the component (panel for more) that should be added
   */
  @Nullable
  protected JComponent createAdditional()
  {
    return null;
  }

  /**
   * Returns all available locales
   *
   * @param pExcludeEmpty true, if empty locales should be omitted
   * @return the locales
   */
  @NotNull
  private Locale[] _getLocales(boolean pExcludeEmpty)
  {
    return Arrays.stream(Locale.getAvailableLocales())
        .filter(pLocale -> !pExcludeEmpty || !Objects.equals(pLocale, Locale.ROOT))
        .collect(Collectors.toMap(Locale::getLanguage, pLocale -> pLocale, (pLocale, pLocale2) -> pLocale)) // Sprachduplikate vermeiden
        .values().stream()
        .sorted((o1, o2) -> String.CASE_INSENSITIVE_ORDER.compare(o1.getDisplayLanguage(), o2.getDisplayLanguage())) // Nach Alphabet sortieren
        .toArray(Locale[]::new);
  }

}