package org.netbeans.modules.lsp.client.bindings;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.netbeans.modules.editor.NbEditorDocument;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for {@link TextDocumentSyncServerCapabilityHandler.KeyListener}
 *
 * @author s.seemann, 20.07.2022
 */
class TextDocumentSyncServerCapabilityHandlerTest
{
  private _JEditorPane component;

  @BeforeEach
  void setUp()
  {
    component = new _JEditorPane();
    component.setDocument(new NbEditorDocument("text/javascript"));
    component.setText("This is my text.\r\nThis is line number two");
    TextDocumentSyncServerCapabilityHandler.KeyListener listener = new TextDocumentSyncServerCapabilityHandler.KeyListener();
    component.addKeyListener(listener);
  }

  @ParameterizedTest
  @MethodSource("_data")
  void shouldSurroundSelectedText(char pFirstChar, char pSecondChar)
  {
    component.setCaretPosition(7);
    component.setSelectionStart(5);
    component.setSelectionEnd(7);


    _typeChar(pFirstChar);
    assertEquals("This " + pFirstChar + "is" + pSecondChar + " my text.\r\nThis is line number two", component.getText());
    assertEquals(6, component.getSelectionStart());
    assertEquals(8, component.getSelectionEnd());
    assertEquals(8, component.getCaretPosition());
  }

  @Test
  void shouldOverrideSelectedText()
  {
    component.setCaretPosition(7);
    component.setSelectionStart(5);
    component.setSelectionEnd(7);

    _typeChar('a');
    assertEquals("This a my text.\r\nThis is line number two", component.getText());
    assertEquals(6, component.getSelectionStart());
    assertEquals(6, component.getSelectionEnd());
    assertEquals(6, component.getCaretPosition());
  }

  @ParameterizedTest
  @MethodSource("_data")
  void shouldInsertSecondCharacter(char pFirstChar, char pSecondChar)
  {
    component.setCaretPosition(7);

    _typeChar(pFirstChar);
    assertEquals("This is" + pFirstChar + pSecondChar + " my text.\r\nThis is line number two", component.getText());
    assertEquals(8, component.getCaretPosition());
  }

  @Test
  void shouldInsertNormalCharacter()
  {
    component.setCaretPosition(7);

    _typeChar('a');
    assertEquals("This isa my text.\r\nThis is line number two", component.getText());
    assertEquals(8, component.getCaretPosition());
  }

  private void _typeChar(char pChar)
  {
    SwingUtilities.invokeLater(() -> {
      try
      {
        KeyEvent event = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UNDEFINED, pChar);
        component.processKeyEvent(event);
        // invoke this Action "manually". This simulates Netbeans
        new DefaultEditorKit.DefaultKeyTypedAction().actionPerformed(new ActionEvent(component,
                                                                                     ActionEvent.ACTION_PERFORMED,
                                                                                     Character.toString(event.getKeyChar()),
                                                                                     event.getWhen(),
                                                                                     event.getModifiersEx()));
      }
      catch (Throwable pE)
      {
        throw new RuntimeException(pE);
      }
    });
    _awaitEDT();
  }

  private void _awaitEDT()
  {
    AtomicBoolean wait = new AtomicBoolean(false);
    SwingUtilities.invokeLater(() -> wait.set(true));

    //noinspection ConditionalBreakInInfiniteLoop
    while (true)
    {
      if (wait.get())
        break;

      try
      {
        //noinspection BusyWait
        Thread.sleep(100);
      }
      catch (InterruptedException pE)
      {
        throw new RuntimeException(pE);
      }
    }
  }

  private static Stream<Arguments> _data()
  {
    return Stream.of(
        Arguments.of('(', ')'),
        Arguments.of('[', ']'),
        Arguments.of('{', '}'),
        Arguments.of('\'', '\''),
        Arguments.of('"', '"'));
  }

  private static class _JEditorPane extends JEditorPane
  {
    @Override
    public void processKeyEvent(KeyEvent e)
    {
      super.processKeyEvent(e);
    }
  }
}