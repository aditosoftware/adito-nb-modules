package de.adito.aditoweb.nbm.vaadinicons;

import de.adito.aditoweb.nbm.vaadinicons.download.SVGFromNPMDownloader;
import de.adito.swing.icon.IconAttributes;
import lombok.NonNull;
import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.transcoder.*;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.util.SVGConstants;
import org.jetbrains.annotations.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author m.kaspera, 11.03.2022
 */
class SVGToIconConverter
{

  private static final TranscodingHints transcoderHints = getTranscodingHints();

  /**
   * Given an inputstream, reads that inputstream and replaces the placeholders with the actual values from the IconAttributes
   *
   * @param pSvgFileStream  InputStream of the svg file
   * @param pIconAttributes IconAttributes that give color/rotation/size of the desired image
   * @return Stream with the final svg contents
   * @throws IOException if an error occurrs while reading the svg file
   */
  @NonNull
  static InputStream applyIconAttributesToStream(@NonNull InputStream pSvgFileStream, @NonNull IconAttributes pIconAttributes) throws IOException
  {
    String contents = new String(pSvgFileStream.readAllBytes());
    contents = contents.replace(SVGFromNPMDownloader.COLOR_PLACEHOLDER, String.format("%06X", 0xFFFFFF & pIconAttributes.getColor().getRGB()));
    contents = contents.replace(SVGFromNPMDownloader.WIDTH_PLACEHOLDER, String.format("%.0f", pIconAttributes.getSize()));
    contents = contents.replace(SVGFromNPMDownloader.HEIGHT_PLACEHOLDER, String.format("%.0f", pIconAttributes.getSize()));
    return new ByteArrayInputStream(contents.getBytes(StandardCharsets.UTF_8));
  }

  /**
   * Load an svg file with the given iconAttributes
   *
   * @param pSvgFile        name of the svg file that should be loaded
   * @param pIconAttributes IconAttributes that give color/rotation/size of the desired image
   * @return BufferedImage with the contents of the svg or null if no such image for the name can be found
   * @throws IOException if the svg file cannot be read
   */
  @Nullable
  public static BufferedImage loadSvg(@NonNull String pSvgFile, @NonNull IconAttributes pIconAttributes) throws IOException
  {
    SVGImageTranscoder t = new SVGImageTranscoder();
    InputStream svgFileStream = SVGToIconConverter.class.getResourceAsStream(pSvgFile);
    if (svgFileStream != null)
    {
      try
      {

        TranscoderInput input = new TranscoderInput(applyIconAttributesToStream(svgFileStream, pIconAttributes));

        t.setTranscodingHints(transcoderHints);
        t.transcode(input, null);
      }
      catch (TranscoderException pE)
      {
        throw new IOException("Couldn't convert " + pSvgFile, pE);
      }
    }
    else
    {
      return null;
    }

    if (pIconAttributes.getRotationInDegrees() == 0)
    {
      return t.getCreatedImage();
    }
    else
    {
      return rotateImage(t.getCreatedImage(), pIconAttributes);
    }
  }

  @NonNull
  private static TranscodingHints getTranscodingHints()
  {
    TranscodingHints transcoderHints = new TranscodingHints();
    transcoderHints.put(ImageTranscoder.KEY_XML_PARSER_VALIDATING, Boolean.FALSE);
    transcoderHints.put(ImageTranscoder.KEY_DOM_IMPLEMENTATION,
                        SVGDOMImplementation.getDOMImplementation());
    transcoderHints.put(ImageTranscoder.KEY_DOCUMENT_ELEMENT_NAMESPACE_URI,
                        SVGConstants.SVG_NAMESPACE_URI);
    transcoderHints.put(ImageTranscoder.KEY_DOCUMENT_ELEMENT, "svg");
    return transcoderHints;
  }

  /**
   * rotate the given image
   *
   * @param pBufferedImage  source image
   * @param pIconAttributes iconAttributes that give the rotation in degrees
   * @return rotated image
   */
  @NonNull
  private static BufferedImage rotateImage(@NonNull BufferedImage pBufferedImage, @NonNull IconAttributes pIconAttributes)
  {
    int w = pBufferedImage.getWidth();
    int h = pBufferedImage.getHeight();

    BufferedImage rotated = new BufferedImage(w, h, pBufferedImage.getType());
    Graphics2D graphic = rotated.createGraphics();
    graphic.rotate(Math.toRadians(pIconAttributes.getRotationInDegrees()), w / 2f, h / 2f);
    graphic.drawImage(pBufferedImage, null, 0, 0);
    graphic.dispose();
    return rotated;
  }

  private static class SVGImageTranscoder extends ImageTranscoder
  {

    private BufferedImage createdImage;

    @Override
    public BufferedImage createImage(int w, int h)
    {
      return new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    }

    @Override
    public void writeImage(BufferedImage image, TranscoderOutput out)
    {
      createdImage = image;
    }

    public BufferedImage getCreatedImage()
    {
      return createdImage;
    }
  }
}
