package de.adito.swing.icon;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.beans.BeanInfo;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * Contains information about how an image/icon should be rendered, e.g. size
 *
 * <b>IMPORTANT:</b> When passing the size to the constructor be aware that the passed argument has to be a float, an int is seen as a type instead of size.
 * Using the builder object instead  is recommended
 *
 * @author m.kaspera, 16.07.2020
 */
public class IconAttributes implements BiConsumer<Graphics2D, Font>
{

  private static final Color _LF_ICON_COLOR = isDarkTheme() ? Color.WHITE : Color.GRAY;

  private float size;
  private Color color;
  private int rotationInDegrees;

  /**
   * @param pSize size of the picture in pixels
   */
  public IconAttributes(float pSize)
  {
    this(pSize, _LF_ICON_COLOR, 0);
  }

  /**
   * @param pType type of the image, e.g. BeanInfo.ICON_COLOR_16x16
   */
  public IconAttributes(int pType)
  {
    this(_typeToSize(pType), _LF_ICON_COLOR, 0);
  }

  /**
   * @param pSize              size of the picture in pixels
   * @param pRotationInDegrees rotation of the image in degrees, rotation happens around the center of the image
   */
  public IconAttributes(float pSize, int pRotationInDegrees)
  {
    this(pSize, _LF_ICON_COLOR, pRotationInDegrees);
  }

  /**
   * @param pType              type of the image, e.g. BeanInfo.ICON_COLOR_16x16
   * @param pRotationInDegrees rotation of the image in degrees, rotation happens around the center of the image
   */
  public IconAttributes(int pType, int pRotationInDegrees)
  {
    this(_typeToSize(pType), _LF_ICON_COLOR, pRotationInDegrees);
  }

  /**
   * @param pSize  size of the picture in pixels
   * @param pColor color of the icon
   */
  public IconAttributes(float pSize, @NotNull Color pColor)
  {
    this(pSize, pColor, 0);
  }

  /**
   * @param pType  type of the image, e.g. BeanInfo.ICON_COLOR_16x16
   * @param pColor color of the icon
   */
  public IconAttributes(int pType, @NotNull Color pColor)
  {
    this(_typeToSize(pType), pColor, 0);
  }

  /**
   * @param pType              type of the image, e.g. BeanInfo.ICON_COLOR_16x16
   * @param pColor             color of the icon
   * @param pRotationInDegrees rotation of the image in degrees, rotation happens around the center of the image
   */
  public IconAttributes(int pType, @NotNull Color pColor, int pRotationInDegrees)
  {
    this(_typeToSize(pType), pColor, pRotationInDegrees);
  }

  /**
   * @param pSize              size of the picture in pixels
   * @param pColor             color of the icon
   * @param pRotationInDegrees rotation of the image in degrees, rotation happens around the center of the image
   */
  public IconAttributes(float pSize, @NotNull Color pColor, int pRotationInDegrees)
  {
    color = pColor;
    rotationInDegrees = pRotationInDegrees;
    size = pSize;
  }

  /**
   * @return returns <tt>true</tt>, if a dark theme is in use
   */
  public static boolean isDarkTheme()
  {
    return UIManager.getBoolean("nb.dark.theme");
  }

  /**
   * Converts a type to its matching size
   *
   * @param pType type of the icon
   * @return size of the icon in pixels, as float
   */
  private static float _typeToSize(int pType)
  {
    int size;
    if (pType == BeanInfo.ICON_COLOR_16x16 || pType == BeanInfo.ICON_MONO_16x16)
      size = 16;
    else if (pType == BeanInfo.ICON_COLOR_32x32 || pType == BeanInfo.ICON_MONO_32x32)
      size = 32;
    else
      throw new IllegalArgumentException("Can't match given type to size, type is " + pType);
    return size;
  }

  /**
   * Erstellt ein AffineTransform das das gezeichnete Objekt um die gegebene Anzahl an Grad um den Mittelpunkt rotiert
   * Creates an AffineTransform that rotates the drawn object the given number of degrees around its center
   *
   * @param pSize              size of the picture in pixels
   * @param pRotationInDegrees rotation of the image in degrees, rotation happens around the center of the image
   * @return AffineTransform object responsible for rotating the image
   */
  private static AffineTransform _createRotationTransform(float pSize, int pRotationInDegrees)
  {
    AffineTransform rotateAroundCenter = new AffineTransform();
    // TranslationOffset = size/2 -> translated by half the picture size -> center of the image
    double translationOffset = pSize / 2;
    rotateAroundCenter.translate(translationOffset, translationOffset);
    rotateAroundCenter.rotate(Math.toRadians(pRotationInDegrees));
    // reset the TranslationOffset
    rotateAroundCenter.translate(-translationOffset, -translationOffset);
    return rotateAroundCenter;
  }

  public float getSize()
  {
    return size;
  }

  public Color getColor()
  {
    return color;
  }

  public int getRotationInDegrees()
  {
    return rotationInDegrees;
  }

  /**
   * Applies the set attributes to the graphics object used to draw/create the icon
   *
   * @param pGraphics Graphics Object used to draw the icon
   */
  public void accept(Graphics2D pGraphics, Font pFont)
  {
    pGraphics.setFont(pFont.deriveFont(size));
    pGraphics.setColor(color);
    if (rotationInDegrees != 0)
      pGraphics.setTransform(_createRotationTransform(size, rotationInDegrees));
  }

  @Override
  public boolean equals(Object pObj)
  {
    if (this == pObj) return true;
    if (pObj instanceof IconAttributes)
    {
      IconAttributes otherAttributes = (IconAttributes) pObj;
      return otherAttributes.rotationInDegrees == rotationInDegrees &&
          Float.compare(otherAttributes.size, size) == 0 &&
          Objects.equals(otherAttributes.color, color);
    }
    return false;
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(color, rotationInDegrees, size);
  }

  /**
   * Builder object for IconAttributes
   */
  public static class Builder
  {

    // Default values
    private float size = 16;
    private Color color = null;
    private int rotationInDegrees = 0;

    /**
     * @param pColor color of the icon
     * @return this builder object
     */
    public Builder setColor(@Nullable Color pColor)
    {
      color = pColor;
      return this;
    }

    /**
     * sets the size of the image/icon, writes to the same date as setType
     *
     * @param pSize size of the picture in pixels
     * @return this builder object
     */
    public Builder setSize(float pSize)
    {
      size = pSize;
      return this;
    }

    /**
     * sets the size of the image/icon via its type, writes to the same date as setSize
     *
     * @param pType type of the image, e.g. BeanInfo.ICON_COLOR_16x16
     * @return this builder object
     */
    public Builder setType(int pType)
    {
      size = _typeToSize(pType);
      return this;
    }

    /**
     * @param pRotationInDegrees rotation in degrees
     * @return this builder object
     */
    public Builder setRotation(int pRotationInDegrees)
    {
      rotationInDegrees = pRotationInDegrees;
      return this;
    }

    /**
     * @return IconAttributes object with the set attributes
     */
    public IconAttributes create()
    {
      return new IconAttributes(size, color == null ? _LF_ICON_COLOR : color, rotationInDegrees);
    }

  }
}
