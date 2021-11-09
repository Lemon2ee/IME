import model.filter.IFilter;
import model.filter.IFilterImpl;
import model.image.ImageFile;
import model.image.ImageModel;
import org.junit.Test;

import java.awt.Color;

import static org.junit.Assert.assertTrue;

/**
 * The JUnit test class for ImageFile class. Including tests for load and save an image and all
 * necessary image processing operations.
 */
public class ModelFilterTest {

  Color[][] sampleImage =
      new Color[][] {
        {Color.RED, Color.GREEN, Color.BLUE}, {Color.YELLOW, Color.WHITE, Color.BLACK}
      };
  Color[][] whiteImage =
      new Color[][] {
        {Color.WHITE, Color.WHITE, Color.WHITE}, {Color.WHITE, Color.WHITE, Color.WHITE}
      };
  Color[][] blackImage =
      new Color[][] {
        {Color.BLACK, Color.BLACK, Color.BLACK}, {Color.BLACK, Color.BLACK, Color.BLACK}
      };

  ImageModel sampleModel = new ImageFile(sampleImage);
  UtilsTestUtils utilsTestUtils = new UtilsTestUtils();
  double[][] invalidKernel;
  IFilter filter;

  @Test(expected = IllegalArgumentException.class)
  public void testNullKernel() {
    new IFilterImpl(invalidKernel);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEvenKernelWidth() {
    double[][] evenKernel = new double[3][2];
    new IFilterImpl(evenKernel);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEvenKernelHeight() {
    double[][] evenKernel = new double[2][3];
    new IFilterImpl(evenKernel);
  }

  @Test
  public void testUnchangedKernel11() {
    filter = new IFilterImpl(new double[][] {{1.0}});
    Color[][] result = filter.execute(sampleModel.imageArrayCopy());
    boolean boo = this.utilsTestUtils.compareTwoColorArrays(sampleImage, result);
    assertTrue(boo);
  }

  @Test
  public void testUnchangedKernel13() {
    filter = new IFilterImpl(new double[][] {{0, 1.0, 0}});
    Color[][] result = filter.execute(sampleModel.imageArrayCopy());
    boolean boo = this.utilsTestUtils.compareTwoColorArrays(sampleImage, result);
    assertTrue(boo);
  }

  @Test
  public void testAllWhiteKernel33() {
    filter = new IFilterImpl(new double[][] {{1.0, 1.0, 1.0}, {1.0, 1.0, 1.0}, {1.0, 1.0, 1.0}});
    Color[][] result = filter.execute(sampleModel.imageArrayCopy());
    boolean boo = this.utilsTestUtils.compareTwoColorArrays(whiteImage, result);
    assertTrue(boo);
  }

  @Test
  public void testAllBlackKernel55() {
    filter =
        new IFilterImpl(
            new double[][] {
              {-1.0, -1.0, -1.0, -1.0, -1.0},
              {-1.0, -1.0, -1.0, -1.0, -1.0},
              {-1.0, -1.0, -1.0, -1.0, -1.0},
              {-1.0, -1.0, -1.0, -1.0, -1.0},
              {-1.0, -1.0, -1.0, -1.0, -1.0}
            });
    Color[][] result = filter.execute(sampleModel.imageArrayCopy());
    boolean boo = this.utilsTestUtils.compareTwoColorArrays(blackImage, result);
    assertTrue(boo);
  }
}
