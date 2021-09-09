package de.adito.nbm.translation.gui;

import de.adito.nbm.translation.api.*;
import de.adito.nbm.translation.spi.ITranslatorAuthKeyProvider;
import de.adito.notification.INotificationFacade;
import org.jetbrains.annotations.*;
import org.openide.*;
import org.openide.util.*;

import java.util.*;

/**
 * @author w.glanzer, 08.09.2021
 */
public class TranslationDialog
{

  /**
   * Shows the dialog for translation and returns the result of it
   *
   * @param pDefaultLocale       default target locale
   * @param pUsePreviousSettings true, if the latest used settings should be set by default
   * @return the result
   */
  @Nullable
  public TranslationResult show(@Nullable Locale pDefaultLocale, boolean pUsePreviousSettings)
  {
    Set<ETranslatorType> availableTypes = new HashSet<>();
    for (ITranslatorAuthKeyProvider keyProvider : Lookup.getDefault().lookupAll(ITranslatorAuthKeyProvider.class))
      for (ETranslatorType type : ETranslatorType.values())
      {
        String providedKey = keyProvider.getAuthKey(type);
        if (providedKey != null && !providedKey.trim().isEmpty())
          availableTypes.add(type);
      }

    if (availableTypes.isEmpty())
    {
      INotificationFacade.INSTANCE.notify("Translation", "No API key found", true, null);
      return null;
    }

    return show(availableTypes.toArray(new ETranslatorType[0]), pDefaultLocale, pUsePreviousSettings);
  }

  /**
   * Shows the dialog for translation and returns the result of it
   *
   * @param pAvailableTypes      available translator types
   * @param pDefaultLocale       default target locale
   * @param pUsePreviousSettings true, if the latest used settings should be set by default
   * @return the result
   */
  @Nullable
  public TranslationResult show(@Nullable ETranslatorType[] pAvailableTypes, @Nullable Locale pDefaultLocale, boolean pUsePreviousSettings)
  {
    TranslationPanel panel = createContent(pAvailableTypes, pDefaultLocale, pUsePreviousSettings);
    DialogDescriptor dialogDescriptor = new DialogDescriptor(panel, NbBundle.getMessage(TranslationDialog.class, "TITLE_DLG_TRANSLATION"), true,
                                                             new Object[]{DialogDescriptor.OK_OPTION, DialogDescriptor.CANCEL_OPTION},
                                                             DialogDescriptor.OK_OPTION, DialogDescriptor.BOTTOM_ALIGN, null, null);
    Object result = DialogDisplayer.getDefault().notify(dialogDescriptor);
    if (result == DialogDescriptor.OK_OPTION)
      return panel.getResult();
    return null;
  }

  /**
   * Creates the content of the dialog
   *
   * @param pTypes               available translator types
   * @param pDefaultTarget       default target locale
   * @param pUsePreviousSettings true, if the latest used settings should be set by default
   * @return the content
   */
  @NotNull
  protected TranslationPanel createContent(@Nullable ETranslatorType[] pTypes, @Nullable Locale pDefaultTarget, boolean pUsePreviousSettings)
  {
    return new TranslationPanel(pTypes, pDefaultTarget, pUsePreviousSettings);
  }

  /**
   * Translation Result POJO
   */
  public static class TranslationResult
  {
    private final Locale from;
    private final Locale to;
    private final ELineBreakMethod lineBreakMethod;
    private final ETranslatorType translatorType;

    protected TranslationResult(Locale pFrom, Locale pTo, ELineBreakMethod pLineBreakMethod, ETranslatorType pTranslatorType)
    {
      from = pFrom;
      to = pTo;
      lineBreakMethod = pLineBreakMethod;
      translatorType = pTranslatorType;
    }

    public Locale getFrom()
    {
      return from;
    }

    public Locale getTo()
    {
      return to;
    }

    public ELineBreakMethod getLineBreakMethod()
    {
      return lineBreakMethod;
    }

    public ETranslatorType getTranslatorType()
    {
      return translatorType;
    }

    @Override
    public String toString()
    {
      return "_Result{" +
          "from=" + from +
          ", to=" + to +
          ", lineBreakMethod=" + lineBreakMethod +
          ", translatorType=" + translatorType +
          '}';
    }
  }
}
