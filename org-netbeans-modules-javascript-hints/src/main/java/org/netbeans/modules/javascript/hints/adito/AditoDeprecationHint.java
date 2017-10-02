package org.netbeans.modules.javascript.hints.adito;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript.IJsUpgrade;
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

    JsIndex jsIndex = JsIndex.get(QuerySupport.findRoots(fileObject, Collections.singleton(JsClassPathProvider.SOURCE_CP),
                                                         Collections.singleton(JsClassPathProvider.BOOT_CP), Collections.emptySet()));
    jsIndex.setAutoImports(JsIndex.MARK_AS_DEPRECATED_MODULES);

    String callName = AstUtilities.getCallName(node, true);
    String altName = info.getSource().substring(node.getSourceStart(), node.getSourceEnd());
    if (altName.startsWith(callName))
    {
      Set<IndexedElement> elements = jsIndex.getElements(callName, null, QuerySupport.Kind.EXACT, info);
      if (!elements.isEmpty() && elements.iterator().next().isDeprecated())
      {
        String description = "'" + callName + "' is deprecated an should no longer be used.";
        Hint desc = new Hint(this, description, fileObject, AstUtilities.getNameRange(node), Arrays.asList(new _DeprecationFixSingle(fileObject, info.getSnapshot().getSource().getDocument(false), node),
                                                                                                           new _DeprecationFixAll(FileOwnerQuery.getOwner(fileObject))), 5000);
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
    return new HashSet<>(Arrays.asList(Token.CALL, Token.NEW));
  }

  @Override
  public String getDisplayName()
  {
    return "Deprecated reference detected";
  }

  @Override
  public String getDescription()
  {
    return "The referenced method is deprecated and should no longer be used. Deprecated references can be subject of " +
        "future removals.";
  }

  private static class _DeprecationFixSingle implements HintFix, AditoHintUtility.IFixAllFixable
  {
    private final FileObject fileObject;
    private final Document document;
    private final Node node;

    public _DeprecationFixSingle(FileObject pFileObject, Document pDocument, Node pNode)
    {
      fileObject = pFileObject;
      document = pDocument;
      node = pNode;
    }

    @Override
    public String getDescription()
    {
      return "Fix deprecation warning";
    }

    @Override
    public void implement() throws Exception
    {
      Lookup.getDefault().lookup(IJsUpgrade.class).upgrade(node, document, true);
      AditoHintUtility.implementHintFixes(Collections.singletonList(Source.create(fileObject)),pFix -> pFix instanceof AditoImportHintFix, null, null, null);
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

  private static class _DeprecationFixAll extends AditoHintUtility.ImplementAllOfTypeFix
  {
    public _DeprecationFixAll(Project pProject)
    {
      super(pProject, _DeprecationFixSingle.class);
    }

    @Override
    public String getDescription()
    {
      return "Fix all deprecation warnings";
    }
  }

}
