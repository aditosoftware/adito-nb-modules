package org.netbeans.modules.javascript.hints.adito;

import org.mozilla.nb.javascript.*;
import org.netbeans.modules.csl.api.*;
import org.netbeans.modules.javascript.editing.*;
import org.netbeans.modules.javascript.editing.adito.AditoLibraryQuery;
import org.netbeans.modules.javascript.hints.infrastructure.JsRuleContext;
import org.netbeans.modules.parsing.spi.indexing.support.QuerySupport;
import org.openide.filesystems.FileObject;

import java.util.*;

/**
 * @author d.poellath, 06.12.12
 */
public class AditoImportHint extends AbstractAditoHint
{
  public void run(JsRuleContext pContext, List<Hint> pResultHints)
  {
    Node node = pContext.node;

    JsParseResult info = AstUtilities.getParseResult(pContext.parserResult);
    FileObject fileObject = info.getSnapshot().getSource().getFileObject();
    if (fileObject == null)
      return;

    JsIndex jsIndex = JsIndex.get(QuerySupport.findRoots(fileObject, Collections.singleton(JsClassPathProvider.SOURCE_CP),
                                                         Collections.singleton(JsClassPathProvider.BOOT_CP), Collections.emptySet()));

    String callName = AstUtilities.getCallName(node, true);
    String altName = info.getSource().substring(node.getSourceStart(), node.getSourceEnd());

    if (!callName.equals(JsAnalyzer.ADITO_IMPORT) && altName.startsWith(callName))
    {
      Set<IndexedElement> elements = jsIndex.getElements(callName, null, QuerySupport.Kind.EXACT, info);
      if (elements == null || elements.isEmpty())
      {
        elements = jsIndex.getElements(callName, null, QuerySupport.Kind.EXACT, null);
        for (IndexedElement element : elements)
        {
          if (element.isPublic() && !element.isDeprecated())
          {
            boolean parentIsDeprecated = false;
            if (element.getIn() != null)
            {
              Set<IndexedElement> parentElement = jsIndex.getElements(element.getIn(), null, QuerySupport.Kind.EXACT, null);
              parentIsDeprecated = !parentElement.isEmpty() && parentElement.iterator().next().isDeprecated();
            }
            if (!parentIsDeprecated)
            {
              AditoLibraryQuery.Packet packet = new AditoLibraryQuery().getPacket(element.getFileObject());
              if (packet != null)
              {
                String importStatement = "import(\"" + packet.getName() + "\")";
                List<HintFix> fix = Collections.singletonList(new AditoImportHintFix(pContext, importStatement + ";\n"));

                String description = "Add '" + packet.getName() + "' to imports.";
                Hint desc = new Hint(this, description, fileObject, AstUtilities.getNameRange(node), fix, 1500);

                boolean hintAllreadyExists = false;
                for(Hint existHint: pResultHints)
                  if(existHint.getDescription().equals(description))
                    for(HintFix existHintFix : existHint.getFixes())
                      if(existHintFix instanceof AditoImportHintFix)
                      {
                        for(HintFix newFix : fix)
                        {
                          if(newFix instanceof AditoImportHintFix)
                          {
                            if(((AditoImportHintFix)existHintFix).getContext().node.getParentNode().equals(((AditoImportHintFix)newFix).getContext().node.getParentNode()))
                            {
                              ((AditoImportHintFix)desc.getFixes().get(0)).getContext().parserResult.getSnapshot().getText();
                              existHint.getRange().getEnd();
                              desc.getRange().getStart();

                              String substring = ((AditoImportHintFix)desc.getFixes().get(0)).getContext().parserResult.getSnapshot().getText().subSequence(existHint.getRange().getEnd(), desc.getRange().getStart()).toString();

                              if(!substring.contains("\n"))
                                hintAllreadyExists = true;
                            }
                          }
                        }
                      }

                if(!hintAllreadyExists)
                  pResultHints.add(desc);
              }
            }
          }
        }
      }
    }
  }

  @Override
  public HintSeverity getDefaultSeverity()
  {
    return HintSeverity.WARNING;
  }

  @Override
  public Set<Integer> getKinds()
  {
    return new HashSet<>(Arrays.asList(Token.CALL, Token.NEW));
  }

  @Override
  public String getDisplayName()
  {
    return "Auto import: the referenced method was found in a library.";
  }

  @Override
  public String getDescription()
  {
    return "Provides automatic importing for referenced method which weren't found in current context but in other " +
        "libraries in the current project.";
  }
}
