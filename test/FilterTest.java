import org.junit.Test;

import java.awt.Color;

import model.filter.Filter;
import model.filter.IFilter;
import model.image.ImageFile;
import model.image.ImageModel;

import static org.junit.Assert.assertEquals;

public class FilterTest {
  Color[][] sampleImage =
          new Color[][]{
                  {Color.RED, Color.GREEN, Color.BLUE}, {Color.YELLOW, Color.WHITE, Color.BLACK}
          };
  ImageModel sampleModel = new ImageFile(sampleImage);
  double[][] invalidKernel;
  IFilter filter;

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidKernel() {
    new Filter(invalidKernel);
  }
}
