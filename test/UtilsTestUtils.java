import java.awt.Color;

import static org.junit.Assert.assertEquals;

/**
 * The util class used for JUnit testing. Including abstraction of modular methods to avoid code
 * duplication.
 */
public class UtilsTestUtils {
  /**
   * Determine if two color arrays are equal.
   *
   * @param expected The expected color array
   * @param actual The actual color array
   * @return boolean after all value tests are passed
   */
  public boolean compareTwoColorArrays(Color[][] expected, Color[][] actual) {
    for (int r = 0; r < actual.length; r++) {
      for (int c = 0; c < actual[0].length; c++) {
        assertEquals(expected[r][c], actual[r][c]);
      }
    }

    return true;
  }

  /**
   * Determine if two int arrays are the same
   *
   * @param expected the expected int array
   * @param actual the actual int array
   * @return boolean after all value tests are passed
   */
  public boolean compareTwoIntArrays(int[][] expected, int[][] actual) {
    for (int r = 0; r < actual.length; r++) {
      for (int c = 0; c < actual[0].length; c++) {
        assertEquals(expected[r][c], actual[r][c]);
      }
    }

    return true;
  }
}
