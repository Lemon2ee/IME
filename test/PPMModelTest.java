import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import model.ImageModel;
import model.PPMModel;
import model.enums.FlipDirection;
import model.enums.GreyScaleValue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PPMModelTest {}
/**
 * The JUnit test class for PPMModel class. Including tests for load and save an image and all
 * necessary image processing operations.
 */
public class PPMModelTest {
  Color[][] sampleImage = new Color[][]{{Color.RED, Color.GREEN, Color.BLUE},
          {Color.YELLOW, Color.WHITE, Color.BLACK}};
  Color[][] verticalFlippedSample = new Color[][]{{Color.YELLOW, Color.WHITE, Color.BLACK},
          {Color.RED, Color.GREEN, Color.BLUE}};
  Color[][] horizontalFlippedSample = new Color[][]{{Color.BLUE, Color.GREEN, Color.RED},
          {Color.BLACK, Color.WHITE, Color.YELLOW}};
  Color[][] diagonalFlippedSample = new Color[][]{{Color.BLACK, Color.WHITE, Color.YELLOW},
          {Color.BLUE, Color.GREEN, Color.RED}};
  Color[][] greyScaleRImage = new Color[][]{{Color.WHITE, Color.BLACK, Color.BLACK},
          {Color.WHITE, Color.WHITE, Color.BLACK}};
  Color[][] greyScaleGImage = new Color[][]{{Color.BLACK, Color.WHITE, Color.BLACK},
          {Color.WHITE, Color.WHITE, Color.BLACK}};
  Color[][] greyScaleBImage = new Color[][]{{Color.BLACK, Color.BLACK, Color.WHITE},
          {Color.BLACK, Color.WHITE, Color.BLACK}};
  Color[][] greyScaleVImage = new Color[][]{{Color.WHITE, Color.WHITE, Color.WHITE},
          {Color.WHITE, Color.WHITE, Color.BLACK}};
  Color[][] greyScaleLImage = new Color[][]{{new Color(54, 54, 54), new Color(182, 182, 182),
          new Color(18, 18, 18)},
          {new Color(237, 237, 237), new Color(255, 255, 255), Color.BLACK}};
  Color[][] greyScaleIImage = new Color[][]{{new Color(85, 85, 85), new Color(85, 85, 85),
          new Color(85, 85, 85)},
          {new Color(170, 170, 170), new Color(255, 255, 255), Color.BLACK}};

  ImageModel model = new PPMModel();

  @Before
  public void setup() {
    model.load("sample", "Sample.ppm");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidLoadFilePath() {
    model.load("invalid_sample", "./test/Sample.ppm");
  }

  @Test
  public void testLoadPPM() {
    Color[][] srcImage = model.getFromKey("sample");
    assertTrue(compareTwoColorArrays(sampleImage, srcImage));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFlipInvalidImage() {
    model.flip("invalid_sample", "sample_flip", FlipDirection.Horizontal);
  }

  @Test
  public void testFlippedVertical() {
    model.flip("sample", "sample_flip", FlipDirection.Vertical);
    Color[][] srcImage = model.getFromKey("sample_flip");
    assertTrue(compareTwoColorArrays(verticalFlippedSample, srcImage));
  }

  @Test
  public void testFlippedHorizontal() {
    model.flip("sample", "sample_flip", FlipDirection.Horizontal);
    Color[][] srcImage = model.getFromKey("sample_flip");
    assertTrue(compareTwoColorArrays(horizontalFlippedSample, srcImage));
  }

  @Test
  public void testFlipVerticalThenHorizontal() {
    model.flip("sample", "sample_flip", FlipDirection.Vertical);
    model.flip("sample_flip", "sample_flip_flip", FlipDirection.Horizontal);
    Color[][] srcImage = model.getFromKey("sample_flip_flip");
    assertTrue(compareTwoColorArrays(diagonalFlippedSample, srcImage));
  }

  @Test
  public void testFlipHorizontalThenVertical() {
    model.flip("sample", "sample_flip", FlipDirection.Horizontal);
    model.flip("sample_flip", "sample_flip_flip", FlipDirection.Vertical);
    Color[][] srcImage = model.getFromKey("sample_flip_flip");
    assertTrue(compareTwoColorArrays(diagonalFlippedSample, srcImage));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGreyScaleInvalidImage() {
    model.greyScale("invalid_sample", "sample_greyscale", GreyScaleValue.Luma);
  }

  @Test
  public void testGreyScaleR() {
    model.greyScale("sample", "sample_R", GreyScaleValue.R);
    Color[][] srcImage = model.getFromKey("sample_R");
    assertTrue(compareTwoColorArrays(greyScaleRImage, srcImage));
  }

  @Test
  public void testGreyScaleG() {
    model.greyScale("sample", "sample_G", GreyScaleValue.G);
    Color[][] srcImage = model.getFromKey("sample_G");
    assertTrue(compareTwoColorArrays(greyScaleGImage, srcImage));
  }

  @Test
  public void testGreyScaleB() {
    model.greyScale("sample", "sample_B", GreyScaleValue.B);
    Color[][] srcImage = model.getFromKey("sample_B");
    assertTrue(compareTwoColorArrays(greyScaleBImage, srcImage));
  }

  @Test
  public void testGreyScaleValue() {
    model.greyScale("sample", "sample_V", GreyScaleValue.Value);
    Color[][] srcImage = model.getFromKey("sample_V");
    assertTrue(compareTwoColorArrays(greyScaleVImage, srcImage));
  }

  @Test
  public void testGreyScaleLuma() {
    model.greyScale("sample", "sample_L", GreyScaleValue.Luma);
    Color[][] srcImage = model.getFromKey("sample_L");
    assertTrue(compareTwoColorArrays(greyScaleLImage, srcImage));
  }

  @Test
  public void testGreyScaleIntensity() {
    model.greyScale("sample", "sample_I", GreyScaleValue.Intensity);
    Color[][] srcImage = model.getFromKey("sample_I");
    assertTrue(compareTwoColorArrays(greyScaleIImage, srcImage));
  }

  private boolean compareTwoColorArrays(Color[][] actual, Color[][] expected) {
    for (int r = 0; r < 2; r++) {
      for (int c = 0; c < 3; c++) {
        assertEquals(actual[r][c], expected[r][c]);
      }
    }

    return true;
  }
}
