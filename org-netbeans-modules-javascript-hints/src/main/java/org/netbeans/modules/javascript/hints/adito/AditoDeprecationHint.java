package org.netbeans.modules.javascript.hints.adito;

import org.mozilla.nb.javascript.*;
import org.netbeans.modules.csl.api.*;
import org.netbeans.modules.javascript.editing.*;
import org.netbeans.modules.javascript.editing.adito.AditoLibraryQuery;
import org.netbeans.modules.javascript.hints.infrastructure.JsRuleContext;
import org.netbeans.modules.parsing.spi.indexing.support.QuerySupport;
import org.openide.filesystems.FileObject;

import java.util.*;
import java.util.stream.*;

/**
 * @author j.boesl, 22.07.16
 */
public class AditoDeprecationHint extends AbstractAditoHint
{

  // #13983: Für Module die deprecated sind sollen immer Warnungen angezeigt werden, auch wenn sie nicht explizit
  // importiert werden.
  private List<String> MARK_AS_DEPRECATED_MODULES =
      Stream.of("a", "calendar", "date", "emails", "imc", "log", "MODULES", "SYSTEM", "telephony")
      .map(s -> AditoLibraryQuery.SYSTEM_LIBS + "." + s)
      .collect(Collectors.toList());


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
    jsIndex.setAutoImports(MARK_AS_DEPRECATED_MODULES);

    String callName = AstUtilities.getCallName(node, true);
    String altName = info.getSource().substring(node.getSourceStart(), node.getSourceEnd());
    if (altName.startsWith(callName))
    {
      Set<IndexedElement> elements = jsIndex.getElements(callName, null, QuerySupport.Kind.EXACT, info);
      if (!elements.isEmpty() && elements.iterator().next().isDeprecated())
      {
        String description = "'" + callName + "' is deprecated an should no longer be used.";
        Hint desc = new Hint(this, description, fileObject, AstUtilities.getNameRange(node), null, 5000);
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
}
