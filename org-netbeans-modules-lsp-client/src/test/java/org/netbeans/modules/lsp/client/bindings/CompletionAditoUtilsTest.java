package org.netbeans.modules.lsp.client.bindings;

import org.junit.jupiter.api.Test;
import org.netbeans.modules.editor.NbEditorDocument;

import javax.swing.text.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for {@link CompletionAditoUtils}.
 * @author r.hartinger, 15.11.2022
 */
class CompletionAditoUtilsTest
{

  /**
   * Tests that the {@code @} is correctly detected and removed.
   * @throws BadLocationException Error while removing the special character
   */
  @Test
  void testRemoveSpecialCharacters() throws BadLocationException
  {
    NbEditorDocument document = new NbEditorDocument("text/javascript");
    document.insertString(0, "import { vars } from \"@ad\"", null);

    assertEquals(22, CompletionAditoUtils.removeSpecialCharacters(document, 25));
  }
}