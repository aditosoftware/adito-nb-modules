package de.adito.swing.icon;

import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.beans.BeanInfo;
import java.util.Objects;

/**
 * @author m.kaspera, 16.07.2020
 */
public class Test_IconAttributesBuilder
{

  /**
   * tests if the data passed to the builder matches the data from the object created by the builder
   */
  @Test
  public void test_Builder()
  {
    int rotation = 25;
    float size = 12;
    Color color = Color.WHITE;
    IconAttributes attributes = new IconAttributes.Builder().setRotation(rotation).setColor(color).setSize(size).create();
    Assert.assertEquals(rotation, attributes.getRotationInDegrees());
    Assert.assertEquals(size, attributes.getSize(), 0);
    Assert.assertEquals(color, attributes.getColor());
  }

  /**
   * tests if the data passed to the builder matches the data from the object created by the builder if a type is passed instead of the size
   */
  @Test
  public void test_BuilderWithType()
  {
    int rotation = 25;
    Color color = Color.WHITE;
    IconAttributes attributes = new IconAttributes.Builder().setRotation(rotation).setColor(color).setType(BeanInfo.ICON_COLOR_16x16).create();
    Assert.assertEquals(rotation, attributes.getRotationInDegrees());
    Assert.assertEquals(16, attributes.getSize(), 0);
    Assert.assertEquals(color, attributes.getColor());
  }

  /**
   * tests if an IllegalArgumentException is thrown if a non-existent type is passed
   */
  @Test(expected = IllegalArgumentException.class)
  public void test_BuilderWrongType()
  {
    new IconAttributes.Builder().setRotation(25).setColor(Color.WHITE).setType(15).create();
  }

  /**
   * tests, if the hashCode method returns the same hashCode for IconAttributes that contain the same data. Also checks the opposite (Do two IconAttributes with different
   * data return a different hashCode)
   */
  @Test
  public void test_HashCode()
  {
    IconAttributes attributes1 = new IconAttributes.Builder().setRotation(25).setColor(Color.WHITE).setSize(12).create();
    IconAttributes attributes2 = new IconAttributes.Builder().setRotation(25).setColor(Color.WHITE).setSize(12).create();
    Assert.assertEquals(Objects.hash(attributes1), Objects.hash(attributes2));
    Assert.assertEquals(Objects.hash("test", attributes1), Objects.hash("test", attributes2));
    Assert.assertNotEquals(Objects.hash("test", attributes1), Objects.hash("test2", attributes2));
    IconAttributes attributes3 = new IconAttributes.Builder().setRotation(25).setColor(Color.WHITE).setSize(5).create();
    Assert.assertNotEquals(Objects.hash("test", attributes1), Objects.hash("test", attributes3));
    Assert.assertNotEquals(Objects.hash("test", attributes2), Objects.hash("test", attributes3));
  }

  /**
   * tests if the equals method return true for two objects containing the same data. Also checks if two IconAttributes with differing data are considered non-equal
   */
  @Test
  public void test_Equals()
  {
    IconAttributes attributes1 = new IconAttributes.Builder().setRotation(25).setColor(Color.WHITE).setSize(12).create();
    IconAttributes attributes2 = new IconAttributes.Builder().setRotation(25).setColor(Color.WHITE).setSize(12).create();
    Assert.assertEquals(attributes1, attributes2);
    IconAttributes attributes3 = new IconAttributes.Builder().setRotation(25).setColor(Color.WHITE).setSize(5).create();
    Assert.assertNotEquals(attributes1, attributes3);
    Assert.assertNotEquals(attributes2, attributes3);
  }

  /**
   * tests if the attributes passed to the IconAttribute are applied correctly when the accept method is called
   */
  @Test
  public void test_Accept()
  {
    int rotation = 180;
    float size = 32;
    Color color = Color.ORANGE;
    IconAttributes attributes = new IconAttributes.Builder().setRotation(rotation).setColor(color).setSize(size).create();
    BufferedImage bufferedImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = bufferedImage.createGraphics();
    Font font = new JLabel().getFont();
    if (font.getSize() == size)
      size++;
    if (graphics.getColor().equals(color))
      color = Color.BLACK;
    attributes.accept(graphics, font);
    Assert.assertEquals(color, graphics.getColor());
    Assert.assertEquals(size, graphics.getFont().getSize(), 0);
    // Die values here only match for 180° (or 180° + n*360°)
    Assert.assertEquals(size, graphics.getTransform().getTranslateX(), 0);
    Assert.assertEquals(size, graphics.getTransform().getTranslateY(), 0);
    Assert.assertEquals(-1, graphics.getTransform().getScaleX(), 0);
    Assert.assertEquals(-1, graphics.getTransform().getScaleY(), 0);
  }

  /**
   * tests if the attributes passed to the IconAttribute are applied correctly when the accept method is called. Contains different data as basis than test_Accept
   */
  @Test
  public void test_Accept2()
  {
    int rotation = 360;
    float size = 16;
    Color color = Color.GREEN;
    IconAttributes attributes = new IconAttributes.Builder().setRotation(rotation).setColor(color).setSize(size).create();
    BufferedImage bufferedImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = bufferedImage.createGraphics();
    Font font = new JLabel().getFont();
    if (font.getSize() == size)
      size++;
    if (graphics.getColor().equals(color))
      color = Color.BLACK;
    attributes.accept(graphics, font);
    Assert.assertEquals(color, graphics.getColor());
    Assert.assertEquals(size, graphics.getFont().getSize(), 0);
    // The values here only match for 360° (or multiples)
    Assert.assertEquals(0, graphics.getTransform().getTranslateX(), 0);
    Assert.assertEquals(0, graphics.getTransform().getTranslateY(), 0);
    Assert.assertEquals(1, graphics.getTransform().getScaleX(), 0);
    Assert.assertEquals(1, graphics.getTransform().getScaleY(), 0);
  }
}
