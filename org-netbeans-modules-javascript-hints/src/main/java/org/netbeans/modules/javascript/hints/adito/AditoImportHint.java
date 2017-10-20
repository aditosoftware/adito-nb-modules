package org.netbeans.modules.javascript.hints.adito;

import org.mozilla.nb.javascript.*;
import org.netbeans.modules.csl.api.*;
import org.netbeans.modules.javascript.editing.*;
import org.netbeans.modules.javascript.editing.adito.AditoLibraryQuery;
import org.netbeans.modules.javascript.hints.infrastructure.JsRuleContext;
import org.netbeans.modules.parsing.spi.indexing.support.QuerySupport;
import org.openide.filesystems.FileObject;
import org.openide.util.NbBundle;

import java.util.*;

import static org.netbeans.modules.javascript.editing.adito.AditoLibraryQuery.*;

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

    String altName = info.getSource().substring(node.getSourceStart(), node.getSourceEnd());
    String callName = node.getType() == Token.CALL || node.getType() == Token.NEW ? AstUtilities.getCallName(node, true) : altName;

    if (!callName.equals(JsAnalyzer.ADITO_IMPORT) && altName.startsWith(callName))
    {
      JsIndex jsIndex = JsIndex.get(QuerySupport.findRoots(fileObject, Collections.singleton(JsClassPathProvider.SOURCE_CP),
                                                           Collections.singleton(JsClassPathProvider.BOOT_CP), Collections.emptySet()));

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
              Packet packet = new AditoLibraryQuery().getPacket(element.getFileObject(), 
                                                                EPacketType.SYSTEM_ADITO,
                                                                EPacketType.SYSTEM_CORE,
                                                                EPacketType.LIBRARY);
                                                                                  
              if (packet != null)
              {
                String importStatement = "import(\"" + packet.getName() + "\")";
                String description = "Add '" + packet.getName() + "' to imports.";
                List<HintFix> fix = Collections.singletonList(new AditoImportHintFix(pContext, importStatement + ";\n"));

                // Nur das Paket markieren, da eigentlich nur das importiert wird und nicht die Funktion
                OffsetRange packetRange = new OffsetRange(node.getSourceStart(), callName.contains(".") ? node.getSourceStart() + callName.indexOf(".") : node.getSourceEnd());
                Hint desc = new Hint(this, description, fileObject, packetRange, fix, 1500);

                if (!hintAlreadyExistsInLine(desc, pResultHints))
                  pResultHints.add(desc);
                else
                  pResultHints.add(new Hint(this, description, fileObject, packetRange, null, 1500));
              }
            }
          }
        }
      }
    }
  }


  private boolean hintAlreadyExistsInLine(Hint pNewHint, List<Hint> pOldHints)
  {
    for (Hint oldHint : pOldHints)
      if (oldHint.getDescription().equals(pNewHint.getDescription()) && oldHint.getFixes() != null)
        for (HintFix existHintFix : oldHint.getFixes())   //1
          if (existHintFix != null && existHintFix instanceof AditoImportHintFix)
            for (HintFix newFix : pNewHint.getFixes())  //1
              if (newFix instanceof AditoImportHintFix)
                if (((AditoImportHintFix) existHintFix).getContext().node.getParentNode().equals(((AditoImportHintFix) newFix).getContext().node.getParentNode()))
                {
                  String substring = ((AditoImportHintFix) existHintFix).getContext().parserResult.getSnapshot().getText().subSequence(oldHint.getRange().getEnd(), pNewHint.getRange().getStart()).toString();

                  if (!substring.contains("\n"))
                    return true;
                }

    return false;
  }

  @Override
  public HintSeverity getDefaultSeverity()
  {
    return HintSeverity.WARNING;
  }

  @Override
  public Set<Integer> getKinds()
  {
    return new HashSet<>(Arrays.asList(Token.CALL, Token.NEW, Token.GETPROP));
  }

  @Override
  public String getDisplayName()
  {
    return NbBundle.getMessage(AditoImportHint.class, "LBL_ImportHint_Name");
  }

  @Override
  public String getDescription()
  {
    return NbBundle.getMessage(AditoImportHint.class, "LBL_ImportHint_Descr");
  }
}
