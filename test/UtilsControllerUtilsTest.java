import org.junit.Test;
import utils.ControllerUtils;

import static org.junit.Assert.assertEquals;

public class UtilsControllerUtilsTest {
  ControllerUtils utils = new ControllerUtils();

  @Test
  public void getExtension() {
    assertEquals(
        ".ppm",
        this.utils.getExtension(
            "/something/sadas/asdas/pp.ppp.ppp.pppp.adas.asdasd.dasdad.thusda.reuwrhw.ppm"));

    assertEquals(".ppm", this.utils.getExtension("test.ppm"));

    assertEquals(".png", this.utils.getExtension("test.png"));
  }
}
