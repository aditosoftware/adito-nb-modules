package org.netbeans.modules.lsp.client.bindings;

import lombok.NonNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.netbeans.modules.editor.NbEditorDocument;

import javax.swing.text.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for {@link CompletionAditoUtils}.
 * @author r.hartinger, 15.11.2022
 */
class CompletionAditoUtilsTest
{

  /**
   * @return Arguments for the parameterized test method {@link CompletionAditoUtilsTest#testRemoveInvalidSuffixes(String, String)}
   */
  @NonNull
  private static Stream<Arguments> testRemoveInvalidSuffixes()
  {
    return Stream.of(
        Arguments.of("import { Test } from \"dummy\"", "import { Test$1 } from \"dummy\""),
        Arguments.of("import { T, Test } from \"dummy\"", "import { T, Test$1 } from \"dummy\""),
        Arguments.of("import { T, Test, T2 } from \"dummy\"", "import { T, Test$1, T2 } from \"dummy\""),
        Arguments.of("import { Test } from \"dummy\"", "import { Test } from \"dummy\""),
        Arguments.of("import { T$1est } from \"dummy\"", "import { T$1est } from \"dummy\""),
        Arguments.of("import { T, T$1est } from \"dummy\"", "import { T, T$1est } from \"dummy\"")
    );
  }

  /**
   * Tests, if invalid suffixes get removed
   *
   * @param pExpected Already replaced string, that is the expected result of {@link CompletionAditoUtils#removeInvalidSuffixes(String)}
   * @param pToTest   String to test the replacement
   */
  @MethodSource
  @ParameterizedTest
  void testRemoveInvalidSuffixes(@NonNull String pExpected, @NonNull String pToTest)
  {
    assertEquals(pExpected, CompletionAditoUtils.removeInvalidSuffixes(pToTest));
  }

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