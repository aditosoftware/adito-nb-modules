package org.netbeans.modules.masterfs.watcher;

import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author m.kaspera, 10.05.2022
 */
class ADITOWatcherSymlinkExtTest
{

  public static Stream<Arguments> provideProperties()
  {
    return Stream.of(Arguments.of(null, false),
                     Arguments.of("false", false),
                     Arguments.of("FALSE", false),
                     Arguments.of("TRUE", true),
                     Arguments.of("true", true),
                     Arguments.of("", true),
                     Arguments.of(" ", true),
                     Arguments.of("test", true));
  }

  @ParameterizedTest
  @MethodSource("provideProperties")
  void isIncludeSymlinksResolvedProperly(@Nullable String pInuput, boolean pExpected)
  {
    if (pInuput == null)
      System.clearProperty(ADITOWatcherSymlinkExt.IS_INCLUDE_SYMLINKS_PROPERTY);
    else
      System.setProperty(ADITOWatcherSymlinkExt.IS_INCLUDE_SYMLINKS_PROPERTY, pInuput);
    assertEquals(pExpected, ADITOWatcherSymlinkExt.isIsIncludeSymlinks());
  }

}