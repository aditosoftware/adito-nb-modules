package org.netbeans.modules.masterfs.watcher;

import org.jetbrains.annotations.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.mockito.*;
import org.openide.filesystems.*;
import org.openide.util.Lookup;

import java.io.File;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

/**
 * @author m.kaspera, 10.05.2022
 */
class ADITOWatcherSymlinkExtTest
{

  /**
   * @return the arguments for {@link #isIncludeSymlinksResolvedProperly(String, boolean, IADITOWatcherSymlinkProvider)}.
   */
  @NotNull
  public static Stream<Arguments> provideProperties()
  {
    IADITOWatcherSymlinkProvider noSymlinks = Mockito.mock(IADITOWatcherSymlinkProvider.class);
    Mockito.doReturn(false).when(noSymlinks).isIncludeSymlinks(any());

    IADITOWatcherSymlinkProvider withSymlinks = Mockito.mock(IADITOWatcherSymlinkProvider.class);
    Mockito.doReturn(true).when(withSymlinks).isIncludeSymlinks(any());

    return Stream.of(
        // No result from Lookup > System.getProperty will be used for query
        Arguments.of(null, false, null),
        Arguments.of("false", false, null),
        Arguments.of("FALSE", false, null),
        Arguments.of("TRUE", true, null),
        Arguments.of("true", true, null),
        Arguments.of("", true, null),
        Arguments.of(" ", true, null),
        Arguments.of("test", true, null),

        // Lookup has a result, which returns false > System.getProperty is still used
        Arguments.of(null, false, noSymlinks),
        Arguments.of("false", false, noSymlinks),
        Arguments.of("FALSE", false, noSymlinks),
        Arguments.of("TRUE", true, noSymlinks),
        Arguments.of("true", true, noSymlinks),
        Arguments.of("", true, noSymlinks),
        Arguments.of(" ", true, noSymlinks),
        Arguments.of("test", true, noSymlinks),

        // Lookup has a result, which returns true > No call of System.getProperty, result will be always true
        Arguments.of(null, true, withSymlinks),
        Arguments.of("false", true, withSymlinks),
        Arguments.of("FALSE", true, withSymlinks),
        Arguments.of("TRUE", true, withSymlinks),
        Arguments.of("true", true, withSymlinks),
        Arguments.of("", true, withSymlinks),
        Arguments.of(" ", true, withSymlinks),
        Arguments.of("test", true, withSymlinks)
    );
  }

  /**
   * Tests the call of {@link ADITOWatcherSymlinkExt#isIsIncludeSymlinks(FileObject)}.
   *
   * @param pInput                       the value that should be set into {@code System.getProperty(ADITOWatcherSymlinkExt.IS_INCLUDE_SYMLINKS_PROPERTY)}.
   *                                     If {@code null} is passed, the property will be cleared.
   * @param pExpected                    the expected value that the method should return
   * @param pAditoWatcherSymlinkProvider the {@link IADITOWatcherSymlinkProvider} which should be returned by {@code Lookup.getDefault().lookup(IADITOWatcherSymlinkProvider.class)}
   */
  @ParameterizedTest
  @MethodSource("provideProperties")
  void isIncludeSymlinksResolvedProperly(@Nullable String pInput, boolean pExpected, @Nullable IADITOWatcherSymlinkProvider pAditoWatcherSymlinkProvider)
  {
    // creates a dummy file object for the method call
    FileObject fileObject = FileUtil.toFileObject(new File("").getAbsoluteFile());

    // sets or clears the property
    if (pInput == null)
      System.clearProperty(ADITOWatcherSymlinkExt.IS_INCLUDE_SYMLINKS_PROPERTY);
    else
      System.setProperty(ADITOWatcherSymlinkExt.IS_INCLUDE_SYMLINKS_PROPERTY, pInput);

    try (MockedStatic<Lookup> lookupMockedStatic = Mockito.mockStatic(Lookup.class))
    {
      Lookup lookup = Mockito.mock(Lookup.class);
      Mockito.doReturn(pAditoWatcherSymlinkProvider).when(lookup).lookup(IADITOWatcherSymlinkProvider.class);

      lookupMockedStatic.when(Lookup::getDefault).thenReturn(lookup);

      assertEquals(pExpected, ADITOWatcherSymlinkExt.isIsIncludeSymlinks(fileObject));
    }
  }

}