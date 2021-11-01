import java.awt.*;

import static org.junit.Assert.assertEquals;

public class TestUtils {
  public void compareTwoColorArrays(Color[][] actual, Color[][] expected) {
    for (int r = 0; r < actual.length; r++) {
      for (int c = 0; c < expected.length; c++) {
        assertEquals(actual[r][c], expected[r][c]);
      }
    }
  }
}
