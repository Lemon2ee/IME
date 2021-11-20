import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import model.image.ImageGUIFile;
import model.image.ImageGUIModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ModelImageGUIFileTest {
  Color[][] allWhite = new Color[][]{{Color.WHITE, Color.WHITE}, {Color.WHITE, Color.WHITE}};
  Color[][] allBlack = new Color[][]{{Color.BLACK, Color.BLACK}, {Color.BLACK, Color.BLACK}};
  Color[][] rgb = new Color[][]{{Color.RED, Color.GREEN}, {Color.BLUE, Color.BLACK}};

  int[][] histogramTemp;

  @Before
  public void setup() {
    histogramTemp = new int[4][256];
  }

  @Test
  public void testHistogramAllWhite() {
    ImageGUIModel sample = new ImageGUIFile(allWhite);

    histogramTemp[0][255] += 4;
    histogramTemp[1][255] += 4;
    histogramTemp[2][255] += 4;
    histogramTemp[3][255] += 4;

    assertTrue(compareIntArrays(histogramTemp, sample.histogram()));
  }

  @Test
  public void testHistogramAllBlack() {
    ImageGUIModel sample = new ImageGUIFile(allBlack);

    histogramTemp[0][0] += 4;
    histogramTemp[1][0] += 4;
    histogramTemp[2][0] += 4;
    histogramTemp[3][0] += 4;

    assertTrue(compareIntArrays(histogramTemp, sample.histogram()));
  }

  @Test
  public void testHistogramRGB() {
    ImageGUIModel sample = new ImageGUIFile(rgb);

    histogramTemp[0][255] += 1;
    histogramTemp[0][0] += 3;
    histogramTemp[1][255] += 1;
    histogramTemp[1][0] += 3;
    histogramTemp[2][255] += 1;
    histogramTemp[2][0] += 3;
    histogramTemp[3][85] += 3;
    histogramTemp[3][0] += 1;

    assertTrue(compareIntArrays(histogramTemp, sample.histogram()));
  }

  private boolean compareIntArrays(int[][] expected, int[][] actual) {
    for (int r = 0; r < actual.length; r++) {
      for (int c = 0; c < actual[0].length; c++) {
        assertEquals(expected[r][c], actual[r][c]);
      }
    }

    return true;
  }
}
