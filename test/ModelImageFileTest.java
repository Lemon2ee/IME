import model.feature.basics.ChangeBrightness;
import model.image.ImageFile;
import model.image.ImageModel;
import org.junit.Test;

import java.awt.Color;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

/**
 * The JUnit test class for ImageFile class. Including tests for load and save an image and all
 * necessary image processing operations.
 */
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
  public void testGetWidth() {
    Color[][] array1 = new Color[][] {new Color[] {new Color(123)}};
    Color[][] array2 = new Color[][] {new Color[] {new Color(123)}, new Color[] {new Color(111)}};

    ImageModel model1 = new ImageFile(array1);
    ImageModel model2 = new ImageFile(array2);

    assertEquals(1, model2.getWidth());
    assertEquals(1, model1.getWidth());
  }

  @Test
  public void testImageArrayCopy() {
    Color[][] array1 = new Color[][] {new Color[] {new Color(123)}};
    Color[][] array2 = new Color[][] {new Color[] {new Color(123)}, new Color[] {new Color(111)}};

    ImageModel model1 = new ImageFile(array1);
    ImageModel model2 = new ImageFile(array2);

    assertTrue(new UtilsTestUtils().compareTwoColorArrays(array1, model1.imageArrayCopy()));
    assertTrue(new UtilsTestUtils().compareTwoColorArrays(array2, model2.imageArrayCopy()));
  }

  @Test
  public void testCopy() {
    Color[][] array1 = new Color[][] {new Color[] {new Color(123)}};
    Color[][] array2 = new Color[][] {new Color[] {new Color(123)}, new Color[] {new Color(111)}};

    ImageModel model1 = new ImageFile(array1);
    ImageModel model2 = new ImageFile(array2);

    assertSame(model1, model1);
    assertNotSame(model1, model1.copy());
  }

  @Test
  public void testApplyFunction() {
    Color[][] array1 = new Color[][] {new Color[] {new Color(1, 1, 1)}};
    Color[][] array12 = new Color[][] {new Color[] {new Color(11, 11, 11)}};

    ImageModel model1 = new ImageFile(array1);

    assertTrue(new UtilsTestUtils().compareTwoColorArrays(array1, model1.imageArrayCopy()));

    model1.applyFunctional(new ChangeBrightness(10));

    assertTrue(new UtilsTestUtils().compareTwoColorArrays(array12, model1.imageArrayCopy()));
  }
}
