import org.junit.Test;

import java.awt.Color;

import model.filter.Filter;
import model.filter.IFilter;
import model.image.ImageFile;
import model.image.ImageModel;

public class FilterTest {
  Color[][] sampleImage =
          new Color[][]{
                  {Color.RED, Color.GREEN, Color.BLUE}, {Color.YELLOW, Color.WHITE, Color.BLACK}
          };
  Color[][] whiteImage =
          new Color[][]{
                  {Color.WHITE, Color.WHITE, Color.WHITE}, {Color.WHITE, Color.WHITE, Color.WHITE}
          };
  Color[][] blackImage =
          new Color[][]{
                  {Color.BLACK, Color.BLACK, Color.BLACK}, {Color.BLACK, Color.BLACK, Color.BLACK}
          };

  ImageModel sampleModel = new ImageFile(sampleImage);
  TestUtils testUtils = new TestUtils();
  double[][] invalidKernel;
  IFilter filter;

  @Test(expected = IllegalArgumentException.class)
  public void testNullKernel() {
    new Filter(invalidKernel);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEvenKernel() {
    double[][] evenKernel = new double[2][2];
    new Filter(evenKernel);
  }

  @Test
  public void testUnchangedKernel1() {
    filter = new Filter(new double[][]{{1.0}});
    ImageModel result = filter.execute(sampleModel);
    testUtils.compareTwoColorArrays(sampleImage, result.imageArrayCopy());
  }

  @Test
  public void testAllWhiteKernel3() {
    filter = new Filter(new double[][]{{1.0, 1.0, 1.0}, {1.0, 1.0, 1.0}, {1.0, 1.0, 1.0}});
    ImageModel result = filter.execute(sampleModel);
    testUtils.compareTwoColorArrays(whiteImage, result.imageArrayCopy());
  }

  @Test
  public void testAllBlackKernel5() {
    filter = new Filter(new double[][]{{-1.0, -1.0, -1.0, -1.0, -1.0},
            {-1.0, -1.0, -1.0, -1.0, -1.0}, {-1.0, -1.0, -1.0, -1.0, -1.0},
            {-1.0, -1.0, -1.0, -1.0, -1.0}, {-1.0, -1.0, -1.0, -1.0, -1.0}});
    ImageModel result = filter.execute(sampleModel);
    testUtils.compareTwoColorArrays(blackImage, result.imageArrayCopy());
  }
}
