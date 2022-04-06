package org.netbeans.modules.lsp.client.bindings;

import com.google.gson.*;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript.IJsDataSupply;
import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.Position;
import org.jetbrains.annotations.NotNull;
import org.netbeans.api.editor.document.*;
import org.netbeans.modules.lsp.client.Utils;
import org.openide.filesystems.FileObject;

import javax.swing.text.*;
import java.util.*;
import java.util.logging.*;

/**
 * Utils for completion with ADITO items
 *
 * @author s.seemann, 03.04.2022
 */
class CompletionAditoUtils
{
  private CompletionAditoUtils()
  {
    // Only static
  }

  /**
   * @param pDoc         current document
   * @param pCaretOffset caret offset
   * @param pFo          the file object
   * @param pItems       the list of items, where the custom items should be added
   * @return true, if potentially something has been added
   */
  public static boolean addAditoCompletionItems(@NotNull Document pDoc, int pCaretOffset, @NotNull FileObject pFo, @NotNull List<CompletionItem> pItems)
      throws BadLocationException
  {
    // Completions of ADITO are only within strings => Check, if current CaretPosition is within string
    boolean inString = true;
    String prefix = "";
    Position position = Utils.createPosition(pDoc, pCaretOffset);
    try
    {
      int lineStartFromIndex = LineDocumentUtils.getLineStartFromIndex((LineDocument) pDoc, position.getLine());
      String line = pDoc.getText(lineStartFromIndex, pCaretOffset - lineStartFromIndex);
      int idx = line.lastIndexOf('"');
      if (idx >= 0)
        prefix = line.substring(idx + 1);

      inString = (line.length() - line.replace("\"", "").length() & 1) == 1;
    }
    catch (Exception pE)
    {
      // use default
      Logger.getLogger(CompletionAditoUtils.class.getName()).log(Level.WARNING, "", pE);
    }

    try
    {
      if (inString)
      {
        // load ADITO variables
        IJsDataSupply supply = NbAditoInterface.lookup(IJsDataSupply.class);
        String finalPrefix = prefix;
        supply.getVariables(pFo)
            .filter(pS -> {
              int idx = finalPrefix.lastIndexOf('.');
              if (idx == -1)
                return true;
              return pS.startsWith(finalPrefix.substring(0, idx + 1));
            })
            .map(pS -> {
              int beginIdx = finalPrefix.lastIndexOf('.') + 1;

              int endIdx = pS.indexOf('.', beginIdx);
              if (endIdx == -1)
                return pS.substring(beginIdx);

              return pS.substring(beginIdx, endIdx);
            })
            .distinct()

            // Create "ADITO Completion Items" and add to items of lsp
            .forEach(pSuggestion -> {
              CompletionItem item = new CompletionItem(pSuggestion);
              item.setKind(CompletionItemKind.Constant);
              item.setInsertTextFormat(InsertTextFormat.PlainText);
              item.setCommitCharacters(List.of("."));

              // LSP server needs this information
              JsonObject obj = new JsonObject();
              obj.addProperty("file", pFo.getPath());
              obj.addProperty("line", position.getLine() + 1); // not 0 based
              obj.addProperty("offset", position.getCharacter() + 1); // not 0 based
              JsonArray arr = new JsonArray();
              arr.add(pSuggestion);
              obj.add("entryNames", arr);
              item.setData(obj);

              pItems.add(item);
            });
      }
    }
    catch (Exception pE)
    {
      // work on, no custom completions
      Logger.getLogger(CompletionAditoUtils.class.getName()).log(Level.WARNING, "", pE);
    }
    return inString;
  }

  /**
   * @param pBeginIdx    the begin index of the insert
   * @param pToAdd       string which should be added
   * @param pCaretOffset the caret offset
   * @return the print suffix
   */
  public static String getPrintSuffix(int pBeginIdx, @NotNull String pToAdd, int pCaretOffset)
  {
    String toInsert = pToAdd.substring(0, pCaretOffset - pBeginIdx);
    Optional<Integer> opt = new StringBuilder(toInsert).reverse().toString().chars()
        .filter(pInt -> !Character.isLetterOrDigit(pInt))
        .boxed()
        .findFirst();

    if (opt.isPresent())
      pBeginIdx -= (toInsert.lastIndexOf(opt.get())) + 1;

    return pToAdd.substring(pCaretOffset - pBeginIdx);
  }
}
