package de.adito.nbm.translation.gui;

import de.adito.nbm.translation.api.*;
import org.jetbrains.annotations.*;
import org.openide.*;
import org.openide.util.NbBundle;

import java.util.Locale;

/**
 * @author w.glanzer, 08.09.2021
 */
public class TranslationDialog
{

  /**
   * Shows the dialog for translation and returns the result of it
   *
   * @param pAvailableTypes available translator types
   * @param pDefaultLocale  default target locale
   * @return the result
   */
  @Nullable
  public TranslationResult show(@Nullable ETranslatorType[] pAvailableTypes, Locale pDefaultLocale)
  {
    TranslationPanel panel = createContent(pAvailableTypes, pDefaultLocale);
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
   * @param pTypes         available translator types
   * @param pDefaultTarget default target locale
   * @return the content
   */
  @NotNull
  protected TranslationPanel createContent(@Nullable ETranslatorType[] pTypes, @Nullable Locale pDefaultTarget)
  {
    return new TranslationPanel(pTypes, pDefaultTarget);
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
