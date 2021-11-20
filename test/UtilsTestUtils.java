import java.awt.Color;

import static org.junit.Assert.assertEquals;

/**
 * The util class used for JUnit testing. Including abstraction of modular methods to avoid code
 * duplication.
 */
public class UtilsTestUtils {
  /**
   * Determine if two generic type arrays are equal.
   *
   * @param expected The expected color array
   * @param actual   The actual color array
   * @return boolean represents if two arrays are equal.
   */
  public boolean compareTwoColorArrays(Color[][] expected, Color[][] actual) {
    for (int r = 0; r < actual.length; r++) {
      for (int c = 0; c < actual[0].length; c++) {
        assertEquals(expected[r][c], actual[r][c]);
      }
    }

    return true;
  }
}
