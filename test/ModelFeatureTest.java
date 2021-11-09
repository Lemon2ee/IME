import model.enums.FilterType;
import model.enums.FlipDirection;
import model.enums.GreyScaleValue;
import model.feature.basics.ChangeBrightness;
import model.feature.basics.Flip;
import model.feature.basics.GreyScale;
import model.feature.pro.Filter;
import model.feature.pro.ProGreyScale;
import model.image.ImageFile;
import model.image.ImageModel;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import static org.junit.Assert.assertTrue;

/**
 * The JUnit test class for ImageFile class. Including tests for load and save an image and all
 * necessary image processing operations.
 */
public class ModelFeatureTest {
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
  Color[][] greyScaleSImage =
      new Color[][] {
        {new Color(100, 89, 69), new Color(196, 175, 136), new Color(48, 43, 33)},
        {new Color(255, 255, 206), new Color(255, 255, 239), Color.BLACK}
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
  Color[][] blurImage =
      new Color[][] {
        {new Color(112, 80, 16), new Color(80, 112, 64), new Color(16, 48, 80)},
        {new Color(128, 112, 32), new Color(112, 128, 80), new Color(32, 48, 64)}
      };
  Color[][] sharpenImage =
      new Color[][] {
        {new Color(255, 191, 32), new Color(191, 255, 128), new Color(0, 96, 255)},
        {new Color(255, 255, 32), new Color(255, 255, 255), new Color(0, 96, 128)}
      };

  ImageModel model;

  UtilsTestUtils utilsTestUtils = new UtilsTestUtils();

  @Before
  public void setup() {
    model = new ImageFile(sampleImage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidImageData() {
    new ImageFile(invalidSample);
  }

  @Test
  public void testImageArrayCopy() {
    boolean boo = this.utilsTestUtils.compareTwoColorArrays(sampleImage, model.imageArrayCopy());
    assertTrue(boo);
  }

  @Test
  public void testFlippedVertical() {
    boolean boo =
        this.utilsTestUtils.compareTwoColorArrays(
            verticalFlippedSample, new Flip(FlipDirection.Vertical).apply(model.imageArrayCopy()));
    assertTrue(boo);
  }

  @Test
  public void testFlippedHorizontal() {
    boolean boo =
        this.utilsTestUtils.compareTwoColorArrays(
            horizontalFlippedSample,
            new Flip(FlipDirection.Horizontal).apply(model.imageArrayCopy()));
    assertTrue(boo);
  }

  @Test
  public void testFlipVerticalThenHorizontal() {
    boolean boo =
        this.utilsTestUtils.compareTwoColorArrays(
            diagonalFlippedSample,
            new Flip(FlipDirection.Horizontal)
                .apply(new Flip(FlipDirection.Vertical).apply(model.imageArrayCopy())));
    assertTrue(boo);
  }

  @Test
  public void testFlipHorizontalThenVertical() {
    boolean boo =
        this.utilsTestUtils.compareTwoColorArrays(
            diagonalFlippedSample,
            new Flip(FlipDirection.Vertical)
                .apply(new Flip(FlipDirection.Horizontal).apply(model.imageArrayCopy())));
    assertTrue(boo);
  }

  @Test
  public void testGreyScaleR() {
    boolean boo =
        this.utilsTestUtils.compareTwoColorArrays(
            greyScaleRImage, new GreyScale(GreyScaleValue.R).apply(model.imageArrayCopy()));
    assertTrue(boo);
  }

  @Test
  public void testGreyScaleG() {
    boolean boo =
        this.utilsTestUtils.compareTwoColorArrays(
            greyScaleGImage, new GreyScale(GreyScaleValue.G).apply(model.imageArrayCopy()));
    assertTrue(boo);
  }

  @Test
  public void testGreyScaleB() {
    boolean boo =
        this.utilsTestUtils.compareTwoColorArrays(
            greyScaleBImage, new GreyScale(GreyScaleValue.B).apply(model.imageArrayCopy()));
    assertTrue(boo);
  }

  @Test
  public void testGreyScaleValue() {
    boolean boo =
        this.utilsTestUtils.compareTwoColorArrays(
            greyScaleVImage, new GreyScale(GreyScaleValue.Value).apply(model.imageArrayCopy()));
    assertTrue(boo);
  }

  @Test
  public void testGreyScaleLuma() {
    boolean boo =
        this.utilsTestUtils.compareTwoColorArrays(
            greyScaleLImage, new GreyScale(GreyScaleValue.Luma).apply(model.imageArrayCopy()));
    assertTrue(boo);
  }

  @Test
  public void testGreyScaleIntensity() {
    boolean boo =
        this.utilsTestUtils.compareTwoColorArrays(
            greyScaleIImage, new GreyScale(GreyScaleValue.Intensity).apply(model.imageArrayCopy()));
    assertTrue(boo);
  }

  @Test
  public void testGreyScaleSepia() {
    boolean boo =
        this.utilsTestUtils.compareTwoColorArrays(
            greyScaleSImage, new ProGreyScale(GreyScaleValue.Sepia).apply(model.imageArrayCopy()));
    assertTrue(boo);
  }

  @Test
  public void testBrighten50() {
    boolean boo =
        this.utilsTestUtils.compareTwoColorArrays(
            brightenImage, new ChangeBrightness(50).apply(model.imageArrayCopy()));
    assertTrue(boo);
  }

  @Test
  public void testDarken50() {
    boolean boo =
        this.utilsTestUtils.compareTwoColorArrays(
            darkenImage, new ChangeBrightness(-50).apply(model.imageArrayCopy()));
    assertTrue(boo);
  }

  @Test
  public void testOverBrighten() {
    boolean boo =
        this.utilsTestUtils.compareTwoColorArrays(
            overBrightenImage, new ChangeBrightness(256).apply(model.imageArrayCopy()));
    assertTrue(boo);
  }

  @Test
  public void testOverDarkenImage() {
    boolean boo =
        this.utilsTestUtils.compareTwoColorArrays(
            overDarkenImage, new ChangeBrightness(-256).apply(model.imageArrayCopy()));
    assertTrue(boo);
  }

  @Test
  public void testFilterBlur() {
    boolean boo =
        this.utilsTestUtils.compareTwoColorArrays(
            blurImage, new Filter(FilterType.Blur).apply(model.imageArrayCopy()));
    assertTrue(boo);
  }

  @Test
  public void testFilterSharpen() {
    boolean boo =
        this.utilsTestUtils.compareTwoColorArrays(
            sharpenImage, new Filter(FilterType.Sharpen).apply(model.imageArrayCopy()));
    assertTrue(boo);
  }
}
