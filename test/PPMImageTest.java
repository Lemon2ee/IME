import model.enums.FlipDirection;
import model.enums.GreyScaleValue;
import model.image.ImageFile;
import model.image.ImageModel;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

/**
 * The JUnit test class for ImageFile class. Including tests for load and save an image and all
 * necessary image processing operations.
 */
public class PPMImageTest {
  Color[][] invalidSample;
  Color[][] sampleImage =
      new Color[][] {
        {Color.RED, Color.GREEN, Color.BLUE}, {Color.YELLOW, Color.WHITE, Color.BLACK}
      };
  Color[][] verticalFlippedSample =
      new Color[][] {
        {Color.YELLOW, Color.WHITE, Color.BLACK}, {Color.RED, Color.GREEN, Color.BLUE}
      };
  Color[][] horizontalFlippedSample =
      new Color[][] {
        {Color.BLUE, Color.GREEN, Color.RED}, {Color.BLACK, Color.WHITE, Color.YELLOW}
      };
  Color[][] diagonalFlippedSample =
      new Color[][] {
        {Color.BLACK, Color.WHITE, Color.YELLOW}, {Color.BLUE, Color.GREEN, Color.RED}
      };
  Color[][] greyScaleRImage =
      new Color[][] {
        {Color.WHITE, Color.BLACK, Color.BLACK}, {Color.WHITE, Color.WHITE, Color.BLACK}
      };
  Color[][] greyScaleGImage =
      new Color[][] {
        {Color.BLACK, Color.WHITE, Color.BLACK}, {Color.WHITE, Color.WHITE, Color.BLACK}
      };
  Color[][] greyScaleBImage =
      new Color[][] {
        {Color.BLACK, Color.BLACK, Color.WHITE}, {Color.BLACK, Color.WHITE, Color.BLACK}
      };
  Color[][] greyScaleVImage =
      new Color[][] {
        {Color.WHITE, Color.WHITE, Color.WHITE}, {Color.WHITE, Color.WHITE, Color.BLACK}
      };
  Color[][] greyScaleLImage =
      new Color[][] {
        {new Color(54, 54, 54), new Color(182, 182, 182), new Color(18, 18, 18)},
        {new Color(237, 237, 237), new Color(255, 255, 255), Color.BLACK}
      };
  Color[][] greyScaleIImage =
      new Color[][] {
        {new Color(85, 85, 85), new Color(85, 85, 85), new Color(85, 85, 85)},
        {new Color(170, 170, 170), new Color(255, 255, 255), Color.BLACK}
      };
  Color[][] brightenImage =
      new Color[][] {
        {new Color(255, 50, 50), new Color(50, 255, 50), new Color(50, 50, 255)},
        {new Color(255, 255, 50), Color.WHITE, new Color(50, 50, 50)}
      };
  Color[][] darkenImage =
      new Color[][] {
        {new Color(205, 0, 0), new Color(0, 205, 0), new Color(0, 0, 205)},
        {new Color(205, 205, 0), new Color(205, 205, 205), Color.BLACK}
      };
  Color[][] overBrightenImage =
      new Color[][] {
        {Color.WHITE, Color.WHITE, Color.WHITE}, {Color.WHITE, Color.WHITE, Color.WHITE}
      };
  Color[][] overDarkenImage =
      new Color[][] {
        {Color.BLACK, Color.BLACK, Color.BLACK}, {Color.BLACK, Color.BLACK, Color.BLACK}
      };

  ImageModel model;

  TestUtils testUtils = new TestUtils();

  @Before
  public void setup() {
    model = new ImageFile(sampleImage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidImageData() {
    new ImageFile(invalidSample);
  }

  @Test
  public void testimageArrayCopy() {
    this.testUtils.compareTwoColorArrays(sampleImage, model.imageArrayCopy());
  }

  @Test
  public void testFlippedVertical() {
    ImageModel result = model.flip(FlipDirection.Vertical);
    this.testUtils.compareTwoColorArrays(verticalFlippedSample, result.imageArrayCopy());
  }

  @Test
  public void testFlippedHorizontal() {
    ImageModel result = model.flip(FlipDirection.Horizontal);
    this.testUtils.compareTwoColorArrays(horizontalFlippedSample, result.imageArrayCopy());
  }

  @Test
  public void testFlipVerticalThenHorizontal() {
    ImageModel vertical = model.flip(FlipDirection.Vertical);
    ImageModel result = vertical.flip(FlipDirection.Horizontal);
    this.testUtils.compareTwoColorArrays(diagonalFlippedSample, result.imageArrayCopy());
  }

  @Test
  public void testFlipHorizontalThenVertical() {
    ImageModel horizontal = model.flip(FlipDirection.Horizontal);
    ImageModel result = horizontal.flip(FlipDirection.Vertical);
    this.testUtils.compareTwoColorArrays(diagonalFlippedSample, result.imageArrayCopy());
  }

  @Test
  public void testGreyScaleR() {
    ImageModel result = model.greyScale(GreyScaleValue.R);
    this.testUtils.compareTwoColorArrays(greyScaleRImage, result.imageArrayCopy());
  }

  @Test
  public void testGreyScaleG() {
    ImageModel result = model.greyScale(GreyScaleValue.G);
    this.testUtils.compareTwoColorArrays(greyScaleGImage, result.imageArrayCopy());
  }

  @Test
  public void testGreyScaleB() {
    ImageModel result = model.greyScale(GreyScaleValue.B);
    this.testUtils.compareTwoColorArrays(greyScaleBImage, result.imageArrayCopy());
  }

  @Test
  public void testGreyScaleValue() {
    ImageModel result = model.greyScale(GreyScaleValue.Value);
    this.testUtils.compareTwoColorArrays(greyScaleVImage, result.imageArrayCopy());
  }

  @Test
  public void testGreyScaleLuma() {
    ImageModel result = model.greyScale(GreyScaleValue.Luma);
    this.testUtils.compareTwoColorArrays(greyScaleLImage, result.imageArrayCopy());
  }

  @Test
  public void testGreyScaleIntensity() {
    ImageModel result = model.greyScale(GreyScaleValue.Intensity);
    this.testUtils.compareTwoColorArrays(greyScaleIImage, result.imageArrayCopy());
  }

  @Test
  public void testBrighten50() {
    ImageModel result = model.changeBrightness(50);
    this.testUtils.compareTwoColorArrays(brightenImage, result.imageArrayCopy());
  }

  @Test
  public void testDarken50() {
    ImageModel result = model.changeBrightness(-50);
    this.testUtils.compareTwoColorArrays(darkenImage, result.imageArrayCopy());
  }

  @Test
  public void testOverBrighten() {
    ImageModel result = model.changeBrightness(256);
    this.testUtils.compareTwoColorArrays(overBrightenImage, result.imageArrayCopy());
  }

  @Test
  public void testOverDarkenImage() {
    ImageModel result = model.changeBrightness(-256);
    this.testUtils.compareTwoColorArrays(overDarkenImage, result.imageArrayCopy());
  }
}
