import java.awt.Color;
import java.util.ArrayList;

/**
 * The util class used for JUnit testing. Including abstraction of modular methods to avoid code
 * duplication.
 */
public class UtilsTestUtils {
  /**
   * Determine if two color arrays are equal.
   *
   * @param actual The actual color array
   * @param expected The expected color array
   * @return A boolean represents if two arrays are equal.
   */
  public boolean compareTwoColorArrays(Color[][] actual, Color[][] expected) {
    ArrayList<Boolean> list = new ArrayList<>();

    for (int r = 0; r < actual.length; r++) {
      for (int c = 0; c < actual[0].length; c++) {
        list.add((actual[r][c].equals(expected[r][c])));
      }
    }

    return !list.contains(false);
  }
}
