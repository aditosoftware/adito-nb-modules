package de.adito.aditoweb.nbm.vaadinicons.download;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author m.kaspera, 11.03.2022
 */
class SVGFromNPMDownloaderTest
{

  @Nested
  class getSvgName {

    @Test
    void isCorrectNameExtracted()
    {
      String svgLine = "<g id=\"vaadin:absolute-position\"><path d=\"M0 0v16h16v-16h-16zM15 15h-14v-6h3v1l3-2-3-2v1h-3v-6h6v3h-1l2 3 2-3h-1v-3h6v14z\"></path></g>";
      assertEquals("absolute_position.svg", SVGFromNPMDownloader.getSvgName(svgLine));
    }

    @Test
    void canHandleNonsense()
    {
      String randomNonsense = "some random nonsense. Test";
      assertNull(SVGFromNPMDownloader.getSvgName(randomNonsense));
    }
  }

  @Nested
  class addColorPlaceholders
  {

    @Test
    void isPlaceholderInserted()
    {
      String testString = "<path d=\"M0 0h11v3h-11v-3z\"></path><path d=\"M0 4h15v3h-15v-3z\"></path><path d=\"M0 8h13v3h-13v-3z\"></path>";
      assertEquals("<path fill=\"#$$$COLOR$$$\" d=\"M0 0h11v3h-11v-3z\"></path>\n" +
                       "<path fill=\"#$$$COLOR$$$\" d=\"M0 4h15v3h-15v-3z\"></path>\n" +
                       "<path fill=\"#$$$COLOR$$$\" d=\"M0 8h13v3h-13v-3z\"></path>", SVGFromNPMDownloader.addPlaceholders(testString));
    }

    @Test
    void canHandleComment()
    {
      String testString = "<path d=\"M0 0h11v3h-11v-3z\"></path><!-- comment --><path d=\"M0 4h15v3h-15v-3z\"></path><path d=\"M0 8h13v3h-13v-3z\"></path>";
      assertEquals("<path fill=\"#$$$COLOR$$$\" d=\"M0 0h11v3h-11v-3z\"></path><!-- comment -->\n" +
                       "<path fill=\"#$$$COLOR$$$\" d=\"M0 4h15v3h-15v-3z\"></path>\n" +
                       "<path fill=\"#$$$COLOR$$$\" d=\"M0 8h13v3h-13v-3z\"></path>", SVGFromNPMDownloader.addPlaceholders(testString));
    }

    @Test
    void canHandleEmptyString()
    {
      assertEquals("", SVGFromNPMDownloader.addPlaceholders(""));
    }

    @Test
    void canHandleNonPathString()
    {
      String randomNonsense = "some random nonsense. Test";
      assertEquals(randomNonsense, SVGFromNPMDownloader.addPlaceholders(randomNonsense));
    }
  }

  @Nested
  class getModuleVersionNumber
  {
    @Test
    void canFindVersionInAnnotations()
    {
      assertNotNull(SVGFromNPMDownloader.getModuleVersionNumber());
    }
  }
}
