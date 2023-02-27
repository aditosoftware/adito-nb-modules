package de.adito.notification;

import lombok.*;
import org.jetbrains.annotations.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.mockito.*;
import org.openide.awt.NotificationDisplayer.Priority;
import org.openide.util.Exceptions;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;

/**
 * Test class for {@link NotificationFacadeImpl}.
 *
 * @author r.hartinger, 21.02.2023
 */
class NotificationFacadeImplTest
{
  private static final String MY_TITLE = "My Title";
  private static final String MY_JUNIT_MESSAGE = "My Junit Message";


  /**
   * Tests the methods {@link NotificationFacadeImpl#notify(String, String, boolean)} and {@link NotificationFacadeImpl#notify(String, String, boolean, ActionListener)}.
   */
  @Nested
  class Notify
  {
    /**
     * Tests that the method without the listener parameter calls the other method.
     */
    @Test
    void shouldNotifyWithoutActionListener()
    {
      baseVerifyNotify(MY_TITLE, MY_JUNIT_MESSAGE, true, Priority.NORMAL, null, pNotificationFacade -> {
        pNotificationFacade.notify(MY_TITLE, MY_JUNIT_MESSAGE, true);

        Mockito.verify(pNotificationFacade).notify(MY_TITLE, MY_JUNIT_MESSAGE, true);
        Mockito.verify(pNotificationFacade).notify(MY_TITLE, MY_JUNIT_MESSAGE, true, null);
      });
    }

    /**
     * Tests that the method with the listener works.
     */
    @Test
    void shouldNotifyWithActionListener()
    {
      ActionListener actionListener = Mockito.spy(ActionListener.class);

      baseVerifyNotify(MY_TITLE, MY_JUNIT_MESSAGE, true, Priority.NORMAL, actionListener, pNotificationFacade -> {
        pNotificationFacade.notify(MY_TITLE, MY_JUNIT_MESSAGE, true, actionListener);

        Mockito.verify(pNotificationFacade).notify(MY_TITLE, MY_JUNIT_MESSAGE, true, actionListener);
      });
    }
  }

  /**
   * Tests the methods {@link NotificationFacadeImpl#error(Throwable)}, {@link NotificationFacadeImpl#error(Throwable, String)}) and {@link NotificationFacadeImpl#error(Throwable, String, String)}.
   */
  @Nested
  class Error
  {

    private static final String ILLEGAL_ARGUMENT_EXCEPTION = "IllegalArgumentException";
    private static final String EXCEPTION_AND_CAUSE = ILLEGAL_ARGUMENT_EXCEPTION + ": " + MY_JUNIT_MESSAGE;
    private final IllegalArgumentException exception = new IllegalArgumentException("wrong message", new UnsupportedOperationException(MY_JUNIT_MESSAGE));

    /**
     * Tests the method {@link NotificationFacadeImpl#getExceptionAndMessage(Throwable)} which is needed for some error methods.
     */
    @Test
    void shouldGetExceptionAndMessage()
    {
      NotificationFacadeImpl notificationFacade = new NotificationFacadeImpl();
      assertEquals(EXCEPTION_AND_CAUSE, notificationFacade.getExceptionAndMessage(exception));
    }

    /**
     * Tests that the method call {@link NotificationFacadeImpl#error(Throwable)} works.
     */
    @Test
    void shouldHandleThrowable()
    {
      baseError(ILLEGAL_ARGUMENT_EXCEPTION, MY_JUNIT_MESSAGE, pNotificationFacade -> {
        pNotificationFacade.error(exception);

        Mockito.verify(pNotificationFacade).error(any());
        Mockito.verify(pNotificationFacade).notifyError(any(), any(), any());
        Mockito.verify(pNotificationFacade, Mockito.times(2)).getRootMessage(any());
      });
    }

    /**
     * Tests that the method call {@link NotificationFacadeImpl#error(Throwable, String)} works.
     */
    @Test
    void shouldHandleThrowableString()
    {
      baseError(MY_TITLE, EXCEPTION_AND_CAUSE, pNotificationFacade -> {
        pNotificationFacade.error(exception, MY_TITLE);

        Mockito.verify(pNotificationFacade).error(any(), any());
        Mockito.verify(pNotificationFacade).notifyError(any(), any(), any());
        Mockito.verify(pNotificationFacade).getExceptionAndMessage(any());
        Mockito.verify(pNotificationFacade, Mockito.times(2)).getRootMessage(any());
      });
    }

    /**
     * Tests that the method call {@link NotificationFacadeImpl#error(Throwable, String, String)} works.
     */
    @Test
    void shouldHandleThrowableStringString()
    {
      String myAdditionalInformation = "My additional information";
      baseError(MY_TITLE, EXCEPTION_AND_CAUSE + "\n " + myAdditionalInformation, pNotificationFacade -> {
        pNotificationFacade.error(exception, MY_TITLE, myAdditionalInformation);

        Mockito.verify(pNotificationFacade).error(any(), any(), any());
        Mockito.verify(pNotificationFacade).notifyError(any(), any(), any());
        Mockito.verify(pNotificationFacade).getExceptionAndMessage(any());
        Mockito.verify(pNotificationFacade, Mockito.times(2)).getRootMessage(any());
      });
    }

    /**
     * Tests that the method {@link NotificationFacadeImpl#notifyError(Throwable, String, String)} is calling the notify method.
     */
    @Test
    void shouldHandleNotifyError()
    {
      baseError(MY_TITLE, MY_JUNIT_MESSAGE, pNotificationFacade -> {
        pNotificationFacade.notifyError(exception, MY_TITLE, MY_JUNIT_MESSAGE);

        Mockito.verify(pNotificationFacade).notifyError(any(), any(), any());
      });
    }

    /**
     * Base method for testing all error methods. It checks that
     * {@link NotificationFacadeImpl#_notify(String, String, boolean, Priority, ActionListener)} will be called.
     *
     * @param pTitle          the title that should be passed to the notify method
     * @param pMessage        the message that should be passed to the notify method
     * @param pFacadeConsumer the consumer for making the method call
     */
    private void baseError(String pTitle, String pMessage, @NotNull Consumer<NotificationFacadeImpl> pFacadeConsumer)
    {
      try (MockedStatic<Exceptions> exceptionsMockedStatic = Mockito.mockStatic(Exceptions.class))
      {
        baseVerifyNotify(pTitle, pMessage, false, Priority.HIGH, null, pFacadeConsumer);

        exceptionsMockedStatic.verify(() -> Exceptions.printStackTrace(any()));
      }
    }
  }

  /**
   * Tests  the method {@link NotificationFacadeImpl#getRootMessage(Throwable)}.
   */
  @Nested
  @TestInstance(TestInstance.Lifecycle.PER_CLASS)
  class GetRootMessage
  {
    /**
     * @return the arguments for {@link #shouldGetRootMessage(String, Throwable)}
     */
    @NotNull
    private Stream<Arguments> shouldGetRootMessage()
    {
      String exception = "exception";
      String notNeeded = "not needed";

      return Stream.of(

          // no cause, no root message
          Arguments.of(exception, new IOException()),

          // no cause, message given
          Arguments.of(MY_JUNIT_MESSAGE, new IOException(MY_JUNIT_MESSAGE)),

          // cause with no message
          Arguments.of(exception, new IOException(new UnsupportedOperationException())),

          // cause with message
          Arguments.of(MY_JUNIT_MESSAGE, new IOException(new UnsupportedOperationException(MY_JUNIT_MESSAGE))),

          // cause with no message. This shows that exception will be returned, even if the exception passed to the method has a message
          Arguments.of(exception, new IOException(notNeeded, new UnsupportedOperationException())),

          // cause with message, showing that message from passed exception is ignored
          Arguments.of(MY_JUNIT_MESSAGE, new IOException(notNeeded, new UnsupportedOperationException(MY_JUNIT_MESSAGE))),

          // exception with localized message and message, showing that localized will be used
          Arguments.of(MY_JUNIT_MESSAGE, new MyJunitException(MY_JUNIT_MESSAGE, notNeeded)),

          // exception with no localized message, showing that message will be used
          Arguments.of(MY_JUNIT_MESSAGE, new MyJunitException(null, MY_JUNIT_MESSAGE))

      );
    }

    /**
     * Tests the method call.
     *
     * @param pExpected  the expected message that should be returned by the method
     * @param pThrowable the throwable passed to the method
     */
    @ParameterizedTest
    @MethodSource
    void shouldGetRootMessage(@NotNull String pExpected, @NotNull Throwable pThrowable)
    {
      NotificationFacadeImpl notificationFacade = new NotificationFacadeImpl();

      assertEquals(pExpected, notificationFacade.getRootMessage(pThrowable));
    }

    /**
     * Dummy exception for testing the method call with different localized message and message.
     * <p>
     * Hint: the methods {@link Throwable#getLocalizedMessage()} and {@link Throwable#getMessage()} are overridden by lomboks {@link Getter} annotation.
     */
    @AllArgsConstructor
    private final class MyJunitException extends RuntimeException
    {
      @Nullable
      @Getter
      private final String localizedMessage;
      @Nullable
      @Getter
      private final String message;
    }
  }


  /**
   * Base method for testing all methods that call {@link NotificationFacadeImpl#_notify(String, String, boolean, Priority, ActionListener)}.
   *
   * @param pTitle          the title that should be passed to the notify method
   * @param pMessage        the message that should be passed to the notify method
   * @param pAutoDispose    the autoDispose  that should be passed to the notify method
   * @param pPriority       the priority that should be passed to the notify method
   * @param pListener       the listener that should be passed to the notify method
   * @param pFacadeConsumer the consumer for making the method call. It needs also make any verify calls except
   *                        {@link NotificationFacadeImpl#_notify(String, String, boolean, Priority, ActionListener)}
   */
  private void baseVerifyNotify(String pTitle, String pMessage, boolean pAutoDispose, @NotNull Priority pPriority,
                                @Nullable ActionListener pListener, @NotNull Consumer<NotificationFacadeImpl> pFacadeConsumer)
  {
    NotificationFacadeImpl notificationFacade = Mockito.spy(new NotificationFacadeImpl());

    Mockito.doNothing().when(notificationFacade)._notify(any(), any(), anyBoolean(), any(), any());


    pFacadeConsumer.accept(notificationFacade);

    Mockito.verify(notificationFacade)._notify(pTitle, pMessage, pAutoDispose, pPriority, pListener);
    Mockito.verifyNoMoreInteractions(notificationFacade);
  }

}
