package org.netbeans.modules.lsp.client.bindings;

import com.google.gson.*;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.NbAditoInterface;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript.IJsDataSupply;
import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.Position;
import org.jetbrains.annotations.*;
import org.netbeans.api.editor.document.*;
import org.netbeans.editor.*;
import org.netbeans.editor.Utilities;
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
  private static final List<Character> EXCLUSION_CHARACTERS = List.of('$', '#', '_', '@');

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
      String line = getLine(pDoc, pCaretOffset, position);
      if (line == null)
        return false;
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
   * Removes specific characters if there is one before the offset
   *
   * @param pDoc    the document
   * @param pOffset current offset in the document
   * @return the new offset
   */
  public static int removeSpecialCharacters(@NotNull Document pDoc, int pOffset) throws BadLocationException
  {
    if (!(pDoc instanceof BaseDocument))
      return pOffset;

    BaseDocument doc = (BaseDocument) pDoc;

    int[] block = Utilities.getIdentifierBlock(doc, pOffset);
    if (block == null)
      block = new int[]{pOffset, pOffset};
    try
    {
      while (true)
      {
        if (block[0] > 0 && EXCLUSION_CHARACTERS.contains(doc.getChars(block[0] - 1, 1)[0]))
        {
          block[0] -= 1;
          int[] tmp = Utilities.getIdentifierBlock(doc, block[0]);
          if (tmp != null)
          {
            block[0] = tmp[0];
          }
        }
        else
          break;
      }
    }
    catch (Throwable t)
    {
      // ignore
    }
    pDoc.remove(block[0], pOffset - block[0]);

    // return the new offset
    return block[0];
  }

  @Nullable
  private static String getLine(@NotNull Document pDoc, int pCaretOffset, @NotNull Position pPosition)
  {
    try
    {
      int lineStartFromIndex = LineDocumentUtils.getLineStartFromIndex((LineDocument) pDoc, pPosition.getLine());
      return pDoc.getText(lineStartFromIndex, pCaretOffset - lineStartFromIndex);
    }
    catch (Exception pE)
    {
      Logger.getLogger(CompletionAditoUtils.class.getName()).log(Level.WARNING, "", pE);
      return null;
    }
  }
}
