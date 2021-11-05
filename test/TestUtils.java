import java.awt.Color;

import static org.junit.Assert.assertEquals;

/**
 * The util class used for JUnit testing. Including abstraction of modular methods to avoid code
 * duplication.
 */
public class TestUtils {
  /**
   * The method used to compare if two image data are the same.
   *
   * @param expected expected array of the method output as 2d array of Color
   * @param actual   actual array of the method output as 2d array of Color
   */
  public void compareTwoColorArrays(Color[][] expected, Color[][] actual) {
    int height = actual.length;
    int width = actual[0].length;
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        assertEquals(expected[r][c], actual[r][c]);
      }
    }
  }
}
