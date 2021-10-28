import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import model.ImageModel;
import model.PPMModel;
import model.enums.FlipDirection;
import model.enums.GreyScaleValue;

import static org.junit.Assert.assertEquals;

public class PPMModelTest {
  Color[][] sampleImage = new Color[][]{{Color.RED, Color.GREEN, Color.BLUE},
          {Color.YELLOW, Color.WHITE, Color.BLACK}};
  Color[][] verticalFlippedSample = new Color[][]{{Color.YELLOW, Color.WHITE, Color.BLACK},
          {Color.RED, Color.GREEN, Color.BLUE}};
  Color[][] horizontalFlippedSample = new Color[][]{{Color.BLUE, Color.GREEN, Color.RED},
          {Color.BLACK, Color.WHITE, Color.YELLOW}};
  ImageModel model;

  @Before
  public void setup() {
    model = new PPMModel();
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidLoadFilePath() {
    model.load("sample", "./test/Sample.ppm");
  }

  @Test
  public void testLoadPPM() {
    model.load("sample", "Sample.ppm");
    Color[][] srcImage = model.getFromKey("sample");
    for (int r = 0; r < 2; r++) {
      for (int c = 0; c < 3; c++) {
        assertEquals(sampleImage[r][c], srcImage[r][c]);
      }
    }
  }

  @Test (expected = IllegalArgumentException.class)
  public void testFlipInvalidImage() {
    model.flip("sample", "sample_flip", FlipDirection.Horizontal);
  }

  @Test
  public void testFlippedVertical() {
    model.load("sample", "Sample.ppm");
    model.flip("sample", "sample_flip", FlipDirection.Vertical);
    Color[][] srcImage = model.getFromKey("sample_flip");
    for (int r = 0; r < 2; r++) {
      for (int c = 0; c < 3; c++) {
        assertEquals(verticalFlippedSample[r][c], srcImage[r][c]);
      }
    }
  }

  @Test
  public void testFlippedHorizontal() {
    model.load("sample", "Sample.ppm");
    model.flip("sample", "sample_flip", FlipDirection.Horizontal);
    Color[][] srcImage = model.getFromKey("sample_flip");
    for (int r = 0; r < 2; r++) {
      for (int c = 0; c < 3; c++) {
        assertEquals(horizontalFlippedSample[r][c], srcImage[r][c]);
      }
    }
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGreyScaleInvalidImage() {
    model.greyScale("sample", "sample_greyscale", GreyScaleValue.Luma);
  }
}