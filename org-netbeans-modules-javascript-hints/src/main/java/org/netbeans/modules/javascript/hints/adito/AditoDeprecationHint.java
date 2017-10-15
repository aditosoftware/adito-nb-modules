package org.netbeans.modules.javascript.hints.adito;

import com.google.common.base.Objects;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript.IJsUpgrade;
import org.jetbrains.annotations.*;
import org.mozilla.nb.javascript.Node;
import org.mozilla.nb.javascript.*;
import org.netbeans.api.project.*;
import org.netbeans.modules.csl.api.*;
import org.netbeans.modules.javascript.editing.*;
import org.netbeans.modules.javascript.hints.adito.doc.DocumentModification;
import org.netbeans.modules.javascript.hints.infrastructure.JsRuleContext;
import org.netbeans.modules.parsing.api.Source;
import org.netbeans.modules.parsing.spi.indexing.support.QuerySupport;
import org.openide.filesystems.FileObject;
import org.openide.util.*;

import javax.swing.text.*;
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
        Document document = info.getSnapshot().getSource().getDocument(true);
        fixes.add(new _DeprecationFixSingle(fileObject, document, node, callName));

        // Fixes für diese Zeile
        List<HintFix> hintFixes = _getFixes(pResultHints, _DeprecationFixAll.class, document, node);

        // Noch kein Fix-All-Eintrag vorhanden? Dann einen hinzufügen
        if(hintFixes.isEmpty())
        {
          Project project = FileOwnerQuery.getOwner(fileObject);
          fixes.add(new _DeprecationFixInFile(project, fileObject));
          fixes.add(new _DeprecationFixAll(project));
        }

        int priority = 100000 - hintFixes.size();
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

  private List<HintFix> _getFixes(List<Hint> pResultHints, Class<? extends HintFix> pFilterFix, Document pDocument, Node pNode)
  {
    return pResultHints.stream()
        .filter(pHint -> _getLineIndex(pDocument, pHint.getRange().getStart()) == _getLineIndex(pDocument, pNode.getSourceStart()))
        .flatMap(pHint -> pHint.getFixes() == null ? Stream.empty() : pHint.getFixes().stream())
        .filter(pFix -> pFilterFix.isAssignableFrom(pFix.getClass()))
        .collect(Collectors.toList());
  }

  private int _getLineIndex(Document pDocument, int pPosition)
  {
    try
    {
      String text = pDocument.getText(0, pPosition);
      return text.split("\n").length;
    }
    catch (BadLocationException pE)
    {
      return -1;
    }
  }

  protected static class _DeprecationFixSingle implements HintFix, AditoHintUtility.IFixExtendedContext
  {
    public static final String _GENERATE_TODOS_KEY = "_FixSingle-GenerateToDos";

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
      Set<Class<? extends HintFix>> fixes = new HashSet<>();
      HashMap<Object, Object> map = new HashMap<>();
      map.put(_GENERATE_TODOS_KEY, true); //Todos hier immer erzeugen, is besser so
      implementAndReturn(DocumentModification.create(document, node), fixes, map);
      if(fixes.size() > 0)
        AditoHintUtility.implementHintFixes(Collections.singletonList(Source.create(fileObject)), fixes, null, null, null);
    }

    @Override
    public boolean implementAndReturn(@NotNull IJsUpgrade.IDocumentModification<Node> pDocumentModification, Set<Class<? extends HintFix>> pFixesAfterClass, @NotNull Map<Object, Object> pSessionObjects) throws Exception
    {
      boolean generateTodos = Boolean.TRUE.equals(pSessionObjects.get(_GENERATE_TODOS_KEY));
      boolean result = Lookup.getDefault().lookup(IJsUpgrade.class).upgrade(node, pDocumentModification, generateTodos, generateTodos);
      if(result)
        pFixesAfterClass.add(AditoImportHintFix.class);
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
    private final FileObject projectDirectory;

    public _DeprecationFixAll(Project pProject)
    {
      super(pProject, true, true, _DeprecationFixSingle.class);
      projectDirectory = pProject.getProjectDirectory();
    }

    @Override
    public String getDescription()
    {
      return NbBundle.getMessage(_DeprecationFixAll.class, "LBL_DeprecationFixAll_Descr");
    }

    @Override
    public boolean equals(Object pO)
    {
      if (this == pO) return true;
      if (pO == null || getClass() != pO.getClass()) return false;
      _DeprecationFixAll that = (_DeprecationFixAll) pO;
      return Objects.equal(projectDirectory, that.projectDirectory);
    }

    @Override
    public int hashCode()
    {
      return Objects.hashCode(projectDirectory);
    }
  }

}
