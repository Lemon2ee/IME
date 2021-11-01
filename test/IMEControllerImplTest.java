import controller.IMEController;
import controller.IMEControllerImpl;
import model.imageLibrary.ImageLib;
import model.imageLibrary.ImageLibModel;
import org.junit.Test;
import view.ImageProcessorView;
import view.ImageProcessorViewImpl;

import java.io.InputStreamReader;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;

public class IMEControllerImplTest {

  @Test(expected = IllegalArgumentException.class)
  public void controllerConstructor1() {
    IMEController controller =
        new IMEControllerImpl(
            null,
            new InputStreamReader(System.in),
            new ImageProcessorViewImpl(System.out, new ImageLib()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void controllerConstructor2() {
    ImageLib library = new ImageLib();
    IMEController controller =
        new IMEControllerImpl(library, null, new ImageProcessorViewImpl(System.out, library));
  }

  @Test(expected = IllegalArgumentException.class)
  public void controllerConstructor3() {
    ImageLib library = new ImageLib();
    IMEController controller =
        new IMEControllerImpl(library, new InputStreamReader(System.in), null);
  }

  @Test
  public void controllerTestLoad() {
    StringBuilder logger = new StringBuilder();
    ImageLibModel library = new MockImageLib(logger);
    Readable readable = new StringReader("load test.ppm test");
    Appendable string = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(string, library);
    IMEController controller = new IMEControllerImpl(library, readable, view);
    controller.initProcessor();

    assertEquals(
        "Received add command with test [[java.awt.Color[r=255,g=0,b=0], "
            + "java.awt.Color[r=0,g=255,b=0], "
            + "java.awt.Color[r=0,g=0,b=255]], "
            + "[java.awt.Color[r=255,g=255,b=0], "
            + "java.awt.Color[r=255,g=255,b=255], "
            + "java.awt.Color[r=0,g=0,b=0]]]\n",
        logger.toString());
  }

  @Test
  public void controllerTestLoadNonExist() {
    StringBuilder logger = new StringBuilder();
    ImageLibModel library = new MockImageLib(logger);
    Readable readable = new StringReader("load non-exist.ppm test");
    Appendable string = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(string, library);
    IMEController controller = new IMEControllerImpl(library, readable, view);
    controller.initProcessor();

    assertEquals("", logger.toString());
  }

  @Test
  public void controllerTestSave() {
    StringBuilder logger = new StringBuilder();
    ImageLibModel library = new MockImageLib(logger);
    Readable readable = new StringReader("save test.ppm test");
    Appendable string = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(string, library);
    IMEController controller = new IMEControllerImpl(library, readable, view);
    controller.initProcessor();

    assertEquals(
        "Received add command with test [[java.awt.Color[r=255,g=0,b=0], "
            + "java.awt.Color[r=0,g=255,b=0], "
            + "java.awt.Color[r=0,g=0,b=255]], "
            + "[java.awt.Color[r=255,g=255,b=0], "
            + "java.awt.Color[r=255,g=255,b=255], "
            + "java.awt.Color[r=0,g=0,b=0]]]\n",
        logger.toString());
  }
}
