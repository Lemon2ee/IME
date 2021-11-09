import model.image.ImageFile;
import model.image.ImageModel;
import org.junit.Test;

import java.awt.Color;

import static org.junit.Assert.assertEquals;

public class ModelImageFileTest {
  @Test
  public void testGetHeight() {
    Color[][] array1 = new Color[][] {new Color[] {new Color(123)}};
    Color[][] array2 = new Color[][] {new Color[] {new Color(123)}, new Color[] {new Color(111)}};

    ImageModel model1 = new ImageFile(array1);
    ImageModel model2 = new ImageFile(array2);

    assertEquals(2, model2.getHeight());
    assertEquals(1, model1.getHeight());
  }

  @Test
  public void testGetWidth() {}

  @Test
  public void testImageArrayCopy() {}

  @Test
  public void testCopy() {}
}
