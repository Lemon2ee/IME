import model.image.ImageFile;
import model.image.ReadOnlyImageModel;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class ReadOnlyImageModelTest {

  @Test
  public void copyReadOnly() {
    Color[][] colorArray =
        new Color[][] {
          {new Color(255, 0, 0), new Color(0, 255, 0), new Color(0, 0, 255)},
          {new Color(255, 255, 0), new Color(255, 255, 255), new Color(0, 0, 0)}
        };
    ReadOnlyImageModel model = new ImageFile(colorArray);

    // comparing the memory address
    assertNotEquals(model.copyReadOnly(), model);
  }

  @Test
  public void getHeight() {
    Color[][] colorArray =
        new Color[][] {
          {new Color(255, 0, 0), new Color(0, 255, 0), new Color(0, 0, 255)},
          {new Color(255, 255, 0), new Color(255, 255, 255), new Color(0, 0, 0)}
        };
    ReadOnlyImageModel model = new ImageFile(colorArray);

    assertEquals(2, model.getHeight());
  }

  @Test
  public void getWidth() {
    Color[][] colorArray =
        new Color[][] {
          {new Color(255, 0, 0), new Color(0, 255, 0), new Color(0, 0, 255)},
          {new Color(255, 255, 0), new Color(255, 255, 255), new Color(0, 0, 0)}
        };
    ReadOnlyImageModel model = new ImageFile(colorArray);

    assertEquals(3, model.getWidth());
  }

  @Test
  public void imageArrayCopy() {
    Color[][] colorArray =
        new Color[][] {
          {new Color(255, 0, 0), new Color(0, 255, 0), new Color(0, 0, 255)},
          {new Color(255, 255, 0), new Color(255, 255, 255), new Color(0, 0, 0)}
        };
    ReadOnlyImageModel model = new ImageFile(colorArray);
    TestUtils utils = new TestUtils();

    utils.compareTwoColorArrays(colorArray, model.imageArrayCopy());
    colorArray[1][1] = new Color(0, 0, 0);
    assertNotEquals(model.imageArrayCopy(), colorArray);
  }
}
