package org.netbeans.modules.javascript.hints.adito;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript.IJsUpgrade;
import org.jetbrains.annotations.*;
import org.mozilla.nb.javascript.Node;
import org.netbeans.api.progress.*;
import org.netbeans.api.project.Project;
import org.netbeans.editor.BaseDocument;
import org.netbeans.modules.csl.api.*;
import org.netbeans.modules.csl.core.*;
import org.netbeans.modules.csl.hints.GsfHintsFactory;
import org.netbeans.modules.csl.hints.infrastructure.GsfHintsManager;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.javascript.editing.JsParseResult;
import org.netbeans.modules.javascript.hints.adito.doc.DocumentModification;
import org.netbeans.modules.javascript.hints.infrastructure.JsHintsProvider;
import org.netbeans.modules.parsing.api.*;
import org.netbeans.modules.parsing.spi.*;
import org.netbeans.spi.editor.hints.*;
import org.openide.*;
import org.openide.filesystems.FileObject;
import org.openide.text.PositionBounds;
import org.openide.util.*;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * @author W.Glanzer, 01.10.2017
 */
class AditoHintUtility
{

  private static final ResourceBundle BUNDLE = NbBundle.getBundle(AditoHintUtility.class);

  /**
   * Implementiert alle HintFixes für eine Liste aus Sources
   *
   * @param pSource         Sources, deren Fixe implementiert werden sollen
   * @param pProgressHandle ProgressHandle das angeben kann, wie viel Sources schon angefasst wurden. Pro Source wird eine WorkUnit addiert.
   * @return Liste aus HintFixes die nicht implementiert werden konnten
   */
  public static List<HintFix> implementHintFixes(@NotNull List<Source> pSource, @NotNull Set<Class<? extends HintFix>> pHintFixesToSolve,
                                                 @Nullable ProgressHandle pProgressHandle, @Nullable Consumer<Exception> pExceptionConsumer)
  {
    return implementHintFixes(pSource, new Predicate<HintFix>()
    {
      private List<String> doneFixes = new ArrayList<>();

      @Override
      public boolean test(HintFix pFix)
      {
        if (doneFixes.contains(pFix.getDescription()))
          return false;

        doneFixes.add(pFix.getDescription());
        return pHintFixesToSolve.stream().anyMatch(fixFilter -> pFix.getClass().isAssignableFrom(fixFilter));
      }
    }, pProgressHandle, pExceptionConsumer);
  }

  /**
   * Implementiert alle HintFixes für eine Liste aus Sources
   *
   * @param pSource               Sources, deren Fixe implementiert werden sollen
   * @param pShouldResolveHintFix Predicate um auszusagen, welche HintFixes implementiert werden sollen
   * @param pProgressHandle       ProgressHandle das angeben kann, wie viel Sources schon angefasst wurden. Pro Source wird eine WorkUnit addiert.
   * @return Liste aus HintFixes die nicht implementiert werden konnten
   */
  public static List<HintFix> implementHintFixes(@NotNull List<Source> pSource, @NotNull Predicate<HintFix> pShouldResolveHintFix,
                                                 @Nullable ProgressHandle pProgressHandle, @Nullable Consumer<Exception> pExceptionConsumer)
  {
    List<HintFix> notImplementableFixes = new ArrayList<>();
    AtomicInteger sourceCounter = new AtomicInteger(0);
    Set<Class<? extends HintFix>> fixesToFixAfterThis = new HashSet<>();

    try
    {
      ParserManager.parse(pSource, new UserTask()
      {
        @Override
        public void run(ResultIterator resultIterator)
        {
          try
          {
            if (resultIterator == null)
              return;

            for (Embedding e : resultIterator.getEmbeddings())
              run(resultIterator.getResultIterator(e));

            if (pProgressHandle != null)
              pProgressHandle.progress(MessageFormat.format(BUNDLE.getString("LBL_HandleDetails"), sourceCounter.get(), pSource.size(), resultIterator.getSnapshot().getSource().getFileObject().getPath()));

            ArrayList<ErrorDescription> errors = new ArrayList<>();
            _collectHints(resultIterator, errors, resultIterator.getSnapshot());

            Comparator<PositionBounds> objectComparator = Comparator.comparingInt(pBound -> pBound.getBegin().getOffset());
            SortedMap<PositionBounds, List<HintFix>> fixesToImplementReverseOrder = new TreeMap<>(objectComparator.reversed());
            for (ErrorDescription description : errors)
            {
              for (Fix fix : description.getFixes().getFixes())
              {
                try
                {
                  HintFix hintFix = _getHintFix(fix);
                  if (hintFix != null && pShouldResolveHintFix.test(hintFix))
                    fixesToImplementReverseOrder.computeIfAbsent(description.getRange(), pRange -> new ArrayList<>()).add(hintFix);
                }
                catch (Exception e)
                {
                  if (pExceptionConsumer != null)
                    pExceptionConsumer.accept(e);
                }
              }
            }

            AtomicReference<IJsUpgrade.IDocumentModification<Node>> modificationRef = new AtomicReference<>();
            fixesToImplementReverseOrder.forEach((pBounds, pFixList) -> pFixList.forEach(pFix -> {
              try
              {
                if (modificationRef.get() == null)
                  modificationRef.set(DocumentModification.create(resultIterator.getSnapshot().getSource().getDocument(true), ((JsParseResult) resultIterator.getParserResult()).getRootNode()));

                boolean result = _implementHintFix(pFix, modificationRef.get(), fixesToFixAfterThis);
                if (!result)
                  notImplementableFixes.add(pFix);
              }
              catch (Exception e)
              {
                if (pExceptionConsumer != null)
                  pExceptionConsumer.accept(new RuntimeException(resultIterator.getSnapshot().getSource().getFileObject().getPath(), e));
              }
            }));

            if (pProgressHandle != null)
              pProgressHandle.progress(sourceCounter.getAndIncrement());
          }
          catch (Exception e)
          {
            if (pExceptionConsumer != null)
              pExceptionConsumer.accept(e);
          }
        }
      });

      // Alle Fixes, die noch gefixt werden müssen, hier abhandeln
      if (!fixesToFixAfterThis.isEmpty())
        notImplementableFixes.addAll(implementHintFixes(pSource, fixesToFixAfterThis, pProgressHandle, pExceptionConsumer));
    }
    catch (Exception e)
    {
      if (pExceptionConsumer != null)
        pExceptionConsumer.accept(e);
    }

    return notImplementableFixes;
  }

  private static boolean _implementHintFix(HintFix pFix, IJsUpgrade.IDocumentModification<Node> pDocumentModification, Set<Class<? extends HintFix>> pFixesToFixAfterThis) throws Exception
  {
    if (pFix == null)
      return true;

    boolean result = true;
    if (pFix instanceof IFixExtendedContext)
      result = ((IFixExtendedContext) pFix).implementAndReturn(pDocumentModification, pFixesToFixAfterThis);
    else
      pFix.implement();
    return result;
  }

  /**
   * @return Liefert den HintFix aus einem Fix
   */
  @Nullable
  private static HintFix _getHintFix(Fix pFix)
  {
    try
    {
      Field fixField = pFix.getClass().getDeclaredField("fix");
      fixField.setAccessible(true);

      return (HintFix) fixField.get(pFix);
    }
    catch (Exception ignored)
    {
      // Wenn kein "fix"-Feld vorhanden ist, dann handelt es sich nicht um einen gewöhnlichen HintFix und es kann nichts getan werden..
    }

    return null;
  }

  /**
   * Kopiert aus dem GsfHintsManager
   *
   * @see GsfHintsManager#collectHints(org.netbeans.modules.parsing.api.ResultIterator, java.util.List[], org.netbeans.modules.parsing.api.Snapshot)
   */
  private static void _collectHints(ResultIterator controller, List<ErrorDescription> allHints, Snapshot tls)
  {
    String mimeType = controller.getSnapshot().getMimeType();
    Language language = LanguageRegistry.getInstance().getLanguageByMimeType(mimeType);
    if (language == null)
    {
      return;
    }
    GsfHintsManager hintsManager = language.getHintsManager();

    ParserResult parserResult = null;
    try
    {
      Parser.Result pr = controller.getParserResult();
      if (pr instanceof ParserResult)
      {
        parserResult = (ParserResult) pr;
      }
    }
    catch (ParseException ignored)
    {
    }

    if (parserResult == null)
    {
      return;
    }

    RuleContext context = new JsHintsProvider().createRuleContext();
    context.manager = language.getHintsManager();
    context.parserResult = parserResult;
    context.caretOffset = -1;
    context.selectionStart = -1;
    context.selectionEnd = -1;
    context.doc = (BaseDocument) parserResult.getSnapshot().getSource().getDocument(true);
    if (context.doc == null)
      return;

    List<ErrorDescription>[] hints = new List[3];
    getHints(hintsManager, context, hints, tls);
    for (int i = 0; i < 3; i++)
    {
      if (hints[i] != null)
        allHints.addAll(hints[i]);
    }

    for (Embedding e : controller.getEmbeddings())
    {
      _collectHints(controller.getResultIterator(e), allHints, tls);
    }
  }

  /**
   * Kopiert aus dem GsfHintsManager
   *
   * @see GsfHintsManager#getHints(org.netbeans.modules.csl.hints.infrastructure.GsfHintsManager, org.netbeans.modules.csl.api.RuleContext, java.util.List[], org.netbeans.modules.parsing.api.Snapshot)
   */
  private static void getHints(GsfHintsManager hintsManager, RuleContext context, List<ErrorDescription>[] ret, Snapshot tls)
  {
    if (hintsManager != null && context != null)
    {
      int caretPos = context.caretOffset;
      HintsProvider provider = new JsHintsProvider();

      // Force a refresh
      // HACK ALERT!
      List<Hint> descriptions = new ArrayList<>();
      if (caretPos == -1)
      {
        provider.computeHints(hintsManager, context, descriptions);
        List<ErrorDescription> result = ret[0] == null ? new ArrayList<>(descriptions.size()) : ret[0];
        for (int i = 0; i < descriptions.size(); i++)
        {
          Hint desc = descriptions.get(i);
          ErrorDescription errorDesc = hintsManager.createDescription(desc, context, true, i == descriptions.size() - 1);
          result.add(errorDesc);
        }

        ret[0] = result;
      }
      else
      {
        provider.computeSuggestions(hintsManager, context, descriptions, caretPos);
        List<ErrorDescription> result = ret[1] == null ? new ArrayList<>(descriptions.size()) : ret[1];
        for (int i = 0; i < descriptions.size(); i++)
        {
          Hint desc = descriptions.get(i);
          ErrorDescription errorDesc = hintsManager.createDescription(desc, context, true, i == descriptions.size() - 1);
          result.add(errorDesc);
        }

        ret[1] = result;
      }
    }
    try
    {
      ret[2] = GsfHintsFactory.getErrors(context.parserResult.getSnapshot(), context.parserResult, tls);
    }
    catch (ParseException ex)
    {
      Exceptions.printStackTrace(ex);
    }
  }

  /**
   * HintFix um über ein gesammtes Projekt bestimmte Hints zu fixen
   */
  public abstract static class ImplementAllOfTypeFix implements HintFix
  {
    private final Project project;
    private final boolean showConfirmDialog;
    private final boolean displayFailedFixes;
    private final Class<? extends HintFix>[] fixes;

    @SafeVarargs
    public ImplementAllOfTypeFix(Project pProject, boolean pShowConfirmDialog, boolean pDisplayFailedFixes, Class<? extends HintFix>... pFixes)
    {
      project = pProject;
      showConfirmDialog = pShowConfirmDialog;
      displayFailedFixes = pDisplayFailedFixes;
      fixes = pFixes;
    }

    @Override
    public void implement()
    {
      long time = System.currentTimeMillis();
      implementOfType(fixes);
      System.out.println(System.currentTimeMillis() - time);
    }

    @Override
    public boolean isSafe()
    {
      return false;
    }

    @Override
    public boolean isInteractive()
    {
      return false;
    }

    protected void implementOfType(Class<? extends HintFix>[] pTypesToFix)
    {
      if (project == null)
        return;

      ProgressHandle handle = ProgressHandleFactory.createSystemHandle(BUNDLE.getString("LBL_FixingHints"));
      List<Class<? extends HintFix>> fixesList = Arrays.asList(pTypesToFix);
      _ProgressRunnable runnable = new _ProgressRunnable(project, handle, pFix -> fixesList.contains(pFix.getClass()), () -> getFileObjects(project), showConfirmDialog, displayFailedFixes);
      BaseProgressUtils.showProgressDialogAndRun(runnable, handle, true);
    }

    /**
     * @return <tt>null</tt> = alle FileObjects des Projektes
     */
    @Nullable
    protected List<FileObject> getFileObjects(@NotNull Project pProject)
    {
      return null;
    }

    private static class _ProgressRunnable implements Runnable
    {
      private final Project project;
      private final ProgressHandle handle;
      private final Predicate<HintFix> shouldResolveHintFix;
      private final Supplier<List<FileObject>> fileObjectGetter;
      private final boolean showConfirmDialog;
      private final boolean displayFailedFixes;

      public _ProgressRunnable(Project pProject, ProgressHandle pHandle, @NotNull Predicate<HintFix> pShouldResolveHintFix, @NotNull Supplier<List<FileObject>> pFileObjectGetter,
                               boolean pShowConfirmDialog, boolean pDisplayFailedFixes)
      {
        project = pProject;
        handle = pHandle;
        shouldResolveHintFix = pShouldResolveHintFix;
        fileObjectGetter = pFileObjectGetter;
        showConfirmDialog = pShowConfirmDialog;
        displayFailedFixes = pDisplayFailedFixes;
      }

      @Override
      public void run()
      {
        handle.start();
        handle.switchToIndeterminate();
        List<FileObject> fileObjects = fileObjectGetter.get();
        List<Source> sources = (fileObjects != null ? fileObjects.stream() : _searchFileObject(project.getProjectDirectory(), pFo -> pFo.getMIMEType().equals("text/javascript")).stream())
            .map(Source::create)
            .collect(Collectors.toList());
        int initSize = sources.size();

        // Sicherheitsabfrage, wenn gewünscht
        if (showConfirmDialog)
        {
          String lbl_confirmFullFix = MessageFormat.format(BUNDLE.getString("LBL_ConfirmFullFix"), initSize, project.getProjectDirectory().getName());
          NotifyDescriptor.Confirmation confDescr = new DialogDescriptor.Confirmation(lbl_confirmFullFix, NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(confDescr) != NotifyDescriptor.YES_OPTION)
            return;
        }

        handle.switchToDeterminate(initSize);
        List<HintFix> fixesFailed = implementHintFixes(sources, shouldResolveHintFix, handle, Throwable::printStackTrace); //todo

        // Fixes die gefailed sind auswerten
        if (fixesFailed != null && !fixesFailed.isEmpty())
          _displayFailedFixes(fixesFailed);
      }

      private List<FileObject> _searchFileObject(FileObject pRoot, Predicate<FileObject> pPredicate)
      {
        ArrayList<FileObject> result = new ArrayList<>();
        for (FileObject child : pRoot.getChildren())
        {
          if (pPredicate.test(child))
            result.add(child);
          result.addAll(_searchFileObject(child, pPredicate));
        }
        return result;
      }

      private void _displayFailedFixes(List<HintFix> pFailedFixes)
      {
        if (!displayFailedFixes)
          return;

        NotifyDescriptor confirmation = new NotifyDescriptor.Message(NbBundle.getMessage(_ProgressRunnable.class, "LBL_FailedFixes", pFailedFixes.size()));
        DialogDisplayer.getDefault().notify(confirmation);
      }
    }
  }

  /**
   * Gibt an, dass ein HintFix von einer Fix-All-Action gefixt werden kann
   */
  public interface IFixExtendedContext
  {
    default boolean implementAndReturn(@NotNull IJsUpgrade.IDocumentModification<Node> pDocumentModification, Set<Class<? extends HintFix>> pFixesToFixAfterImplementation) throws Exception
    {
      return true;
    }
  }

}
