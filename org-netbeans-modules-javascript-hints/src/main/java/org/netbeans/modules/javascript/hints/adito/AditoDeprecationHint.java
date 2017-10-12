package org.netbeans.modules.javascript.hints.adito;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript.IJsUpgrade;
import org.jetbrains.annotations.*;
import org.mozilla.nb.javascript.*;
import org.netbeans.api.project.*;
import org.netbeans.modules.csl.api.*;
import org.netbeans.modules.javascript.editing.*;
import org.netbeans.modules.javascript.hints.infrastructure.JsRuleContext;
import org.netbeans.modules.parsing.api.Source;
import org.netbeans.modules.parsing.spi.indexing.support.QuerySupport;
import org.openide.filesystems.FileObject;
import org.openide.util.*;

import javax.swing.text.Document;
import java.util.*;
import java.util.stream.*;

/**
 * @author j.boesl, 22.07.16
 */
public class AditoDeprecationHint extends AbstractAditoHint
{

  @Override
  public void run(JsRuleContext pContext, List<Hint> pResultHints)
  {
    Node node = pContext.node;

    JsParseResult info = AstUtilities.getParseResult(pContext.parserResult);
    FileObject fileObject = info.getSnapshot().getSource().getFileObject();
    if (fileObject == null)
      return;

    String altName = info.getSource().substring(node.getSourceStart(), node.getSourceEnd());
    String callName = node.getType() == Token.CALL || node.getType() == Token.NEW ? AstUtilities.getCallName(node, true) : altName;
    if (altName.startsWith(callName) && callName.split("\\.").length >= 2)
    {
      JsIndex jsIndex = JsIndex.get(QuerySupport.findRoots(fileObject, Collections.singleton(JsClassPathProvider.SOURCE_CP),
                                                           Collections.singleton(JsClassPathProvider.BOOT_CP), Collections.emptySet()));
      jsIndex.setAutoImports(JsIndex.MARK_AS_DEPRECATED_MODULES);

      Set<IndexedElement> elements = jsIndex.getElements(callName, null, QuerySupport.Kind.EXACT, info);
      if (!elements.isEmpty() && elements.iterator().next().isDeprecated())
      {
        String description = "'" + callName + "' is deprecated an should no longer be used.";
        List<HintFix> fixes = new ArrayList<>();
        fixes.add(new _DeprecationFixSingle(fileObject, info.getSnapshot().getSource().getDocument(true), node, callName));

        // Noch kein Fix-All-Eintrag vorhanden? Dann einen hinzufügen
        if(_getFixes(pResultHints, _DeprecationFixAll.class).isEmpty())
        {
          Project project = FileOwnerQuery.getOwner(fileObject);
          fixes.add(new _DeprecationFixInFile(project, fileObject));
          fixes.add(new _DeprecationFixAll(project));
        }

        int priority = 10000 - _getFixes(pResultHints, _DeprecationFixSingle.class).size();
        Hint desc = new Hint(this, description, fileObject, AstUtilities.getNameRange(node), fixes, priority);
        pResultHints.add(desc);
      }
    }
  }

  @Override
  public HintSeverity getDefaultSeverity()
  {
    return HintSeverity.INFO;
  }

  @Override
  public Set<Integer> getKinds()
  {
    return new HashSet<>(Arrays.asList(Token.CALL, Token.NEW, Token.GETPROP));
  }

  @Override
  public String getDisplayName()
  {
    return NbBundle.getMessage(AditoDeprecationHint.class, "LBL_DeprecationHint_Name");
  }

  @Override
  public String getDescription()
  {
    return NbBundle.getMessage(AditoDeprecationHint.class, "LBL_DeprecationHint_Descr");
  }

  private List<HintFix> _getFixes(List<Hint> pResultHints, Class<? extends HintFix> pFilterFix)
  {
    return pResultHints.stream()
        .flatMap(pHint -> pHint.getFixes() == null ? Stream.empty() : pHint.getFixes().stream())
        .filter(pFix -> pFilterFix.isAssignableFrom(pFix.getClass()))
        .collect(Collectors.toList());
  }

  private static class _DeprecationFixSingle implements HintFix, AditoHintUtility.IFixAllFixable
  {
    private final FileObject fileObject;
    private final Document document;
    private final Node node;
    private final String accessorName;

    public _DeprecationFixSingle(FileObject pFileObject, Document pDocument, Node pNode, String pAccessorName)
    {
      fileObject = pFileObject;
      document = pDocument;
      node = pNode;
      accessorName = pAccessorName;
    }

    @Override
    public String getDescription()
    {
      return NbBundle.getMessage(_DeprecationFixSingle.class, "LBL_DeprecationFixSingle_Descr", accessorName);
    }

    @Override
    public void implement() throws Exception
    {
      implementAndReturn();
    }

    @Override
    public boolean implementAndReturn() throws Exception
    {
      boolean result = Lookup.getDefault().lookup(IJsUpgrade.class).upgrade(node, document, true);
      if(result)
        AditoHintUtility.implementHintFixes(Collections.singletonList(Source.create(fileObject)), pFix -> pFix instanceof AditoImportHintFix, null, null, null);
      return result;
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

    @Override
    public Object getID()
    {
      return node.getSourceStart() + " " + node.getSourceEnd() + " " + node.getType();
    }
  }

  private static class _DeprecationFixInFile extends AditoHintUtility.ImplementAllOfTypeFix
  {
    private final FileObject currentFO;

    public _DeprecationFixInFile(Project pProject, FileObject pCurrentFO)
    {
      super(pProject, false, true, _DeprecationFixSingle.class);
      currentFO = pCurrentFO;
    }

    @Override
    public String getDescription()
    {
      return NbBundle.getMessage(_DeprecationFixAll.class, "LBL_DeprecationFixInFile_Descr");
    }

    @Nullable
    @Override
    protected List<FileObject> getFileObjects(@NotNull Project pProject)
    {
      return Collections.singletonList(currentFO);
    }
  }

  private static class _DeprecationFixAll extends AditoHintUtility.ImplementAllOfTypeFix
  {
    public _DeprecationFixAll(Project pProject)
    {
      super(pProject, true, true, _DeprecationFixSingle.class);
    }

    @Override
    public String getDescription()
    {
      return NbBundle.getMessage(_DeprecationFixAll.class, "LBL_DeprecationFixAll_Descr");
    }
  }

}
