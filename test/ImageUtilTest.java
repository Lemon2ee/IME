import model.image.ImageFile;
import model.image.ImageModel;
import model.image.ReadOnlyImageModel;
import model.imageLibrary.ReadOnlyImageLibModel;
import org.junit.Test;
import utils.ImageUtil;

import java.awt.*;

import static org.junit.Assert.*;

public class ImageUtilTest {
  ImageUtil util = new ImageUtil();

  @Test
  public void readPPM() {
    Color[][] colorArray =
        new Color[][] {
          {new Color(255, 0, 0), new Color(0, 255, 0), new Color(0, 0, 255)},
          {new Color(255, 255, 0), new Color(255, 255, 255), new Color(0, 0, 0)}
        };

    new TestUtils().compareTwoColorArrays(colorArray, this.util.readPPM("test.ppm"));
  }

  @Test
  public void toRed() {
    Color color = new Color(0, 255, 255);
    Color color1 = this.util.toRed(color);
    assertEquals(new Color(0, 0, 0), color1);
  }

  @Test
  public void toGreen() {
    Color color = new Color(0, 255, 0);
    Color color1 = this.util.toGreen(color);
    assertEquals(new Color(255, 255, 255), color1);
  }

  @Test
  public void toBlue() {
    Color color = new Color(0, 0, 100);
    Color color1 = this.util.toBlue(color);
    assertEquals(new Color(100, 100, 100), color1);
  }

  @Test
  public void toValue() {
    Color color = new Color(3, 8, 12);
    Color color1 = this.util.toBlue(color);
    assertEquals(new Color(12, 12, 12), color1);
  }

  @Test
  public void toAlpha() {
    Color color = new Color(3, 8, 12);
    Color color1 = this.util.toAlpha(color);
    assertEquals(new Color(255, 255, 255), color1);
  }

  @Test
  public void toIntensity() {
    Color color = new Color(3, 8, 12);
    Color color1 = this.util.toIntensity(color);
    // rounded up
    assertEquals(new Color(8, 8, 8), color1);
  }

  @Test
  public void toLuma() {
    Color color = new Color(3, 8, 12);
    Color color1 = this.util.toLuma(color);
    // rounded up
    assertEquals(new Color(7, 7, 7), color1);
  }

  @Test
  public void colorBrightness() {
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
  public void writeImage() {
    Color[][] colorArray =
        new Color[][] {
          {new Color(255, 0, 0), new Color(0, 255, 0), new Color(0, 0, 255)},
          {new Color(255, 255, 0), new Color(255, 255, 255), new Color(0, 0, 0)}
        };
    ReadOnlyImageModel model = new ImageFile(colorArray);
    this.util.writeImage("testWriteImage.ppm", model);

    new TestUtils().compareTwoColorArrays(colorArray, model.imageArrayCopy());

    new TestUtils().compareTwoColorArrays(this.util.readPPM("testWriteImage.ppm"), colorArray);
  }
}
