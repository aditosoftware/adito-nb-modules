package de.adito.notification.internal;

import com.google.common.annotations.VisibleForTesting;
import de.adito.notification.INotificationFacade;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.mockito.Mockito;

import java.lang.invoke.*;
import java.lang.reflect.*;
import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.*;

/**
 * Test Util class for testing {@link de.adito.notification.INotificationFacade} via reflection.
 * This class should only be used for testing purposes.
 *
 * @author r.hartinger, 21.02.2023
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@VisibleForTesting
@SuppressWarnings("unused") // called and used in some nb-plugins
public class NotificationFacadeTestUtil
{

  /**
   * Verifies that {@link INotificationFacade#error(Throwable, String, String)} will be called.
   *
   * @param pClass    the class of the {@link Throwable} that will be should to the method
   * @param pTitle    the title that should be passed to the method
   * @param pMessage  the message that should be passed to the method
   * @param pRunnable the runnable which should call a method that should call {@link INotificationFacade}
   * @throws NoSuchFieldException   Error while accessing a field via reflections that does not exist
   * @throws IllegalAccessException Error while accessing and setting a field via reflections
   */
  public static void verifyNotificationFacade(@NotNull Class<? extends Throwable> pClass, @NotNull String pTitle, @NotNull String pMessage, @NotNull Runnable pRunnable) throws NoSuchFieldException, IllegalAccessException
  {
    verifyNotificationFacade(pNotificationFacade -> Mockito.verify(pNotificationFacade).error(isA(pClass), eq(pTitle), eq(pMessage)),
                             pRunnable);
  }

  /**
   * Verifies that {@link INotificationFacade#error(Throwable, String)} will be called.
   *
   * @param pClass    the class of the {@link Throwable} that will be should to the method
   * @param pTitle    the title that should be passed to the method
   * @param pRunnable the runnable which should call a method that should call {@link INotificationFacade}
   * @throws NoSuchFieldException   Error while accessing a field via reflections that does not exist
   * @throws IllegalAccessException Error while accessing and setting a field via reflections
   */
  public static void verifyNotificationFacade(@NotNull Class<? extends Throwable> pClass, @NotNull String pTitle, @NotNull Runnable pRunnable) throws NoSuchFieldException, IllegalAccessException
  {
    verifyNotificationFacade(pNotificationFacade -> Mockito.verify(pNotificationFacade).error(isA(pClass), eq(pTitle)),
                             pRunnable);
  }

  /**
   * Verifies that {@link INotificationFacade#error(Throwable)} will be called.
   *
   * @param pClass    the class of the {@link Throwable} that will be should to the method
   * @param pRunnable the runnable which should call a method that should call {@link INotificationFacade}
   * @throws NoSuchFieldException   Error while accessing a field via reflections that does not exist
   * @throws IllegalAccessException Error while accessing and setting a field via reflections
   */
  public static void verifyNotificationFacade(@NotNull Class<? extends Throwable> pClass, @NotNull Runnable pRunnable) throws NoSuchFieldException, IllegalAccessException
  {
    verifyNotificationFacade(pNotificationFacade -> Mockito.verify(pNotificationFacade).error(isA(pClass)),
                             pRunnable);
  }

  /**
   * Verifies that {@link INotificationFacade} will never be called.
   *
   * @param pRunnable the runnable which should call a method that should not call {@link INotificationFacade}
   * @throws NoSuchFieldException   Error while accessing a field via reflections that does not exist
   * @throws IllegalAccessException Error while accessing and setting a field via reflections
   */
  public static void verifyNoInteractionsWithNotificationFacade(@NotNull Runnable pRunnable) throws NoSuchFieldException, IllegalAccessException
  {
    verifyNotificationFacade(pNotificationFacade -> {
      // no additional checks to do
    }, pRunnable);
  }

  /**
   * Verifies that {@link INotificationFacade#notify(String, String, boolean)} will be called.
   *
   * @param pTitle    the title that should be passed to the method
   * @param pMessage  the message that should be passed to the method
   * @param pRunnable the runnable which should call a method that should call {@link INotificationFacade}
   * @throws NoSuchFieldException   Error while accessing a field via reflections that does not exist
   * @throws IllegalAccessException Error while accessing and setting a field via reflections
   */
  public static void verifyNotificationFacadeNotify(@NotNull String pTitle, @NotNull String pMessage, @NotNull Runnable pRunnable) throws NoSuchFieldException, IllegalAccessException
  {
    verifyNotificationFacade(pNotificationFacade -> Mockito.verify(pNotificationFacade).notify(eq(pTitle), eq(pMessage), anyBoolean()),
                             pRunnable);
  }

  /**
   * Base method for verifying every call with {@link INotificationFacade#INSTANCE}.
   *
   * @param pVerifyCall the consumer with which you can do additional checks as verifying calls to different methods
   * @param pRunnable   the runnable which should call any method that could potentially call a method from {@link INotificationFacade}
   * @throws NoSuchFieldException   Error while accessing a field via reflections that does not exist
   * @throws IllegalAccessException Error while accessing and setting a field via reflections
   * @see <a href="https://stackoverflow.com/a/69362143">How to access a private final field via reflections</a>
   */
  private static void verifyNotificationFacade(@NotNull Consumer<INotificationFacade> pVerifyCall, @NotNull Runnable pRunnable) throws NoSuchFieldException, IllegalAccessException
  {
    // JAVA 12+ solution for setting the static final field INSTANCE within the class INotificationFacade
    Field field = INotificationFacade.class.getDeclaredField("INSTANCE");
    // removing the private flag
    field.setAccessible(true);

    // removing the final flag, so we can change the value
    MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(Field.class, MethodHandles.lookup());
    VarHandle modifiers = lookup.findVarHandle(Field.class, "modifiers", int.class);
    int mods = field.getModifiers();
    if (Modifier.isFinal(mods))
    {
      modifiers.set(field, mods & ~Modifier.FINAL);
    }

    // creating our dummy notification facade
    INotificationFacade notificationFacade = Mockito.spy(INotificationFacade.class);

    //  and set the value to the field
    field.set(null, notificationFacade);


    // execute the method call
    pRunnable.run();

    // do verifications
    pVerifyCall.accept(notificationFacade);
    Mockito.verifyNoMoreInteractions(notificationFacade);
  }

}
