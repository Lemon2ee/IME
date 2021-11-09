import model.image.ImageFile;
import model.image.ImageModel;
import org.junit.Test;
import utils.ImageUtil;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class UtilsImageUtilTest {
  ImageUtil util = new ImageUtil();

  @Test
  public void testReadPPM() {
    Color[][] colorArray =
        new Color[][] {
          {new Color(255, 0, 0), new Color(0, 255, 0), new Color(0, 0, 255)},
          {new Color(255, 255, 0), new Color(255, 255, 255), new Color(0, 0, 0)}
        };

    new UtilsTestUtils().compareTwoColorArrays(colorArray, this.util.readPPM("test.ppm"));
  }

  @Test
  public void testToRed() {
    Color color = new Color(0, 255, 255);
    Color color1 = this.util.toRed(color);
    assertEquals(new Color(0, 0, 0), color1);
  }

  @Test
  public void testToGreen() {
    Color color = new Color(0, 255, 0);
    Color color1 = this.util.toGreen(color);
    assertEquals(new Color(255, 255, 255), color1);
  }

  @Test
  public void testToBlue() {
    Color color = new Color(0, 0, 100);
    Color color1 = this.util.toBlue(color);
    assertEquals(new Color(100, 100, 100), color1);
  }

  @Test
  public void testToValue() {
    Color color = new Color(3, 8, 12);
    Color color1 = this.util.toBlue(color);
    assertEquals(new Color(12, 12, 12), color1);
  }

  @Test
  public void testToAlpha() {
    Color color = new Color(3, 8, 12);
    Color color1 = this.util.toAlpha(color);
    assertEquals(new Color(255, 255, 255), color1);
  }

  @Test
  public void testToIntensity() {
    Color color = new Color(3, 8, 12);
    Color color1 = this.util.toIntensity(color);
    // rounded up
    assertEquals(new Color(8, 8, 8), color1);
  }

  @Test
  public void testToLuma() {
    Color color = new Color(3, 8, 12);
    Color color1 = this.util.toLuma(color);
    // rounded up
    assertEquals(new Color(7, 7, 7), color1);
  }

  @Test
  public void testToSepia() {
    Color color = new Color(100, 100, 100);
    Color color1 = this.util.toSepia(color);
    assertEquals(new Color(135, 120, 94), color1);
  }

  @Test
  public void testColorBrightness() {
    Color color = new Color(3, 8, 12);
    Color color1 = this.util.colorBrightness(color, 255);
    // rounded up
    assertEquals(new Color(255, 255, 255), color1);

    Color color2 = this.util.colorBrightness(color, -255);
    // rounded up
    assertEquals(new Color(0, 0, 0), color2);

    Color color3 = this.util.colorBrightness(color, -2);
    // rounded up
    assertEquals(new Color(1, 6, 10), color3);

    Color color4 = this.util.colorBrightness(color, 12);
    // rounded up
    assertEquals(new Color(15, 20, 24), color4);
  }

  @Test
  public void testClampRangeNegative() {
    int result = this.util.clampRange(-255);
    assertEquals(0, result);
  }

  @Test
  public void testClampRangeOver255() {
    int result = this.util.clampRange(256);
    assertEquals(255, result);
  }

  @Test
  public void testClampRangeValid() {
    int result = this.util.clampRange(100);
    assertEquals(100, result);
  }

  @Test
  public void testWriteImage() {
    Color[][] colorArray =
        new Color[][] {
          {new Color(255, 0, 0), new Color(0, 255, 0), new Color(0, 0, 255)},
          {new Color(255, 255, 0), new Color(255, 255, 255), new Color(0, 0, 0)}
        };
    ImageModel model = new ImageFile(colorArray);
    this.util.writeImage("testWriteImage.ppm", model);

    new UtilsTestUtils().compareTwoColorArrays(colorArray, model.imageArrayCopy());

    new UtilsTestUtils().compareTwoColorArrays(this.util.readPPM("testWriteImage.ppm"), colorArray);
  }
}
