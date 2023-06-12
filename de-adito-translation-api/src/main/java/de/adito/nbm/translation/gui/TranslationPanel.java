package de.adito.nbm.translation.gui;

import de.adito.nbm.translation.api.*;
import de.adito.swing.TableLayoutUtil;
import info.clearthought.layout.TableLayout;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;
import org.openide.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

import static info.clearthought.layout.TableLayoutConstants.PREFERRED;

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

  protected TranslationPanel(@Nullable ETranslatorType[] pTypes, @Nullable Locale pTargetLocaleDefaultValue, boolean pUsePreviousSettings)
  {
    this(pTypes, pTargetLocaleDefaultValue, null, pUsePreviousSettings);
  }

  protected TranslationPanel(@Nullable ETranslatorType[] pTypes, @Nullable Locale pTargetLocaleDefaultValue,
                             @Nullable Locale pSourceLocaleDefaultValue, boolean pUsePreviousSettings)
  {
    double pref = PREFERRED;
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
    setBorder(new EmptyBorder(6, 6, 6, 6));
    TableLayoutUtil tlu = new TableLayoutUtil(this);
    tlu.add(1, 1, new JLabel(_TRANSLATORTYPE + ":"));
    translatorCombo = new JComboBox<>(pTypes == null ? ETranslatorType.values() : pTypes);
    readSetting("translation.tType", null)
        .filter(pType -> pUsePreviousSettings)
        .map(ETranslatorType::valueOf)
        .ifPresent(translatorCombo::setSelectedItem);
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
    if (pSourceLocaleDefaultValue != null)
      _selectLocaleInCombobox(fromLangCombo, pSourceLocaleDefaultValue);
    else
      readSetting("translation.from", null)
          .filter(pType -> pUsePreviousSettings)
          .map(Locale::forLanguageTag)
          .ifPresent(pLocale -> _selectLocaleInCombobox(fromLangCombo, pLocale));
    tlu.add(3, 3, fromLangCombo);

    tlu.add(1, 5, new JLabel(_TARGET_LANG + ":"));
    toLangCombo = new JComboBox<>(_getLocales(true));
    toLangCombo.setRenderer(comboRenderer);
    if (pTargetLocaleDefaultValue != null)
      _selectLocaleInCombobox(toLangCombo, pTargetLocaleDefaultValue);
    else
      readSetting("translation.to", null)
          .filter(pType -> pUsePreviousSettings)
          .map(Locale::forLanguageTag)
          .ifPresentOrElse(pLocale -> _selectLocaleInCombobox(toLangCombo, pLocale), () -> _selectLocaleInCombobox(toLangCombo, Locale.ENGLISH));
    tlu.add(3, 5, toLangCombo);

    tlu.add(1, 7, new JLabel(_LINEBREAK + ":"));
    linebreakCombo = new JComboBox<>(ELineBreakMethod.values());
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
    readSetting("translation.lbMethod", null)
        .filter(pType -> pUsePreviousSettings)
        .map(ELineBreakMethod::valueOf)
        .ifPresentOrElse(linebreakCombo::setSelectedItem, () -> linebreakCombo.setSelectedItem(ELineBreakMethod.LINEBREAK_TO_SINGLE_REQUEST));
    tlu.add(3, 7, linebreakCombo);

    JComponent additionalComponents = createAdditional(pUsePreviousSettings);
    JPanel additionalComponentContainer = new JPanel(new BorderLayout());
    if (additionalComponents != null)
      additionalComponentContainer.add(additionalComponents, BorderLayout.CENTER);
    tlu.add(1, 9, 3, 9, additionalComponentContainer);
  }

  /**
   * @return the result containing all set "properties"
   */
  @NonNull
  public TranslationDialog.TranslationResult getResult()
  {
    Locale fromLang = (Locale) fromLangCombo.getSelectedItem();
    if (fromLang == null || fromLang.equals(Locale.ROOT))
      fromLang = null;

    Locale toLang = (Locale) toLangCombo.getSelectedItem();
    ELineBreakMethod lbMethod = (ELineBreakMethod) linebreakCombo.getSelectedItem();
    ETranslatorType tType = (ETranslatorType) translatorCombo.getSelectedItem();

    // store
    storeSetting("translation.from", fromLang == null ? null : fromLang.toLanguageTag());
    storeSetting("translation.to", toLang == null ? null : toLang.toLanguageTag());
    storeSetting("translation.lbMethod", lbMethod == null ? null : lbMethod.name());
    storeSetting("translation.tType", tType == null ? null : tType.name());

    return new TranslationDialog.TranslationResult(fromLang, toLang, lbMethod, tType);
  }

  /**
   * Can be overridden, if components should be added to the panel
   *
   * @param pUsePreviousSettings true, if the latest used settings should be set by default
   * @return the component (panel for more) that should be added
   */
  @Nullable
  protected JComponent createAdditional(boolean pUsePreviousSettings)
  {
    return null;
  }


  protected void storeSetting(@NonNull String pKey, @Nullable String pValue)
  {
    Preferences pref = NbPreferences.forModule(TranslationPanel.class);
    if (pValue != null)
      pref.put(pKey, pValue);
    else
      pref.remove(pKey);
  }

  @NonNull
  protected Optional<String> readSetting(@NonNull String pKey, @Nullable String pDefault)
  {
    return Optional.ofNullable(NbPreferences.forModule(TranslationPanel.class).get(pKey, pDefault));
  }

  /**
   * Returns all available locales
   *
   * @param pExcludeEmpty true, if empty locales should be omitted
   * @return the locales
   */
  @NonNull
  private Locale[] _getLocales(boolean pExcludeEmpty)
  {
    return Arrays.stream(Locale.getAvailableLocales())
        .filter(pLocale -> !pExcludeEmpty || !Objects.equals(pLocale, Locale.ROOT))
        .collect(Collectors.toMap(Locale::getLanguage, pLocale -> pLocale, (pLocale, pLocale2) -> pLocale)) // Sprachduplikate vermeiden
        .values().stream()
        .sorted((o1, o2) -> String.CASE_INSENSITIVE_ORDER.compare(o1.getDisplayLanguage(), o2.getDisplayLanguage())) // Nach Alphabet sortieren
        .toArray(Locale[]::new);
  }

  /**
   * Selects the locale in the combobox. Only the values of {@link Locale#getLanguage()} are compared, because in the combobox can be any Locale
   * with {@link Locale#getLanguage()} of {@code pLocaleToSelect}. Example: ComboBox en_EN, {@code pLocaleToSelect} en_US: Then should be en_EN be selected.
   *
   * @param pCombo          combobox
   * @param pLocaleToSelect the locale to select
   * @see #_getLocales(boolean)
   */
  private void _selectLocaleInCombobox(@NonNull JComboBox<Locale> pCombo, @NonNull Locale pLocaleToSelect)
  {
    ComboBoxModel<Locale> model = pCombo.getModel();
    List<Locale> localesInModel = new ArrayList<>();

    for (int i = 0; i < model.getSize(); i++)
      localesInModel.add(model.getElementAt(i));

    localesInModel.stream()
        .filter(pLocale -> pLocale.getLanguage().equals(pLocaleToSelect.getLanguage()))
        .findFirst().ifPresent(model::setSelectedItem);
  }
}