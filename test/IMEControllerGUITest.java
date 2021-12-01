import controller.IMEControllerGUI;
import controller.IMEControllerProGUI;
import model.library.ImageLib;
import model.library.ImageLibModel;
import org.junit.Test;
import view.ImageProcessorView;
import view.ImageProcessorViewImpl;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/** A test class for IMEControllerGUI test which test the method contained in the interface. */
public class IMEControllerGUITest {
  @Test
  public void testReturnSupported() {
    IMEControllerGUI imeControllerGUI =
        new IMEControllerProGUI(new ImageLib(), new ImageProcessorViewImpl(new StringBuilder()));

    String[] strings = {
      "brighten",
      "sharpen",
      "green-component",
      "sepia-component",
      "save",
      "blur",
      "greyscale",
      "horizontal-flip",
      "intensity-component",
      "sepia",
      "load",
      "vertical-flip",
      "value-component",
      "blue-component",
      "luma-component",
      "alpha-component",
      "red-component"
    };
    Set<String> set = new HashSet<>(List.of(strings));

    assertEquals(set, imeControllerGUI.getSupportCommands());
  }

  @Test
  public void testAcceptCommand() {
    StringBuilder logger = new StringBuilder();
    ImageLibModel library = new MockImageLib(logger);
    Appendable string = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(string);
    IMEControllerGUI controller = new IMEControllerProGUI(library, view);
    controller.acceptCommand("load testRes/test.ppm test\nsave testRes/test.ppm test");

    assertEquals(
        "Received add command with test [[java.awt.Color[r=255,g=0,b=0], "
            + "java.awt.Color[r=0,g=255,b=0], "
            + "java.awt.Color[r=0,g=0,b=255]], "
            + "[java.awt.Color[r=255,g=255,b=0], "
            + "java.awt.Color[r=255,g=255,b=255], "
            + "java.awt.Color[r=0,g=0,b=0]]]\n"
            + "Received read command looking for test\n",
        logger.toString());
  }

  @Test
  public void controllerTestHorizontalFlipGUI() {
    StringBuilder logger = new StringBuilder();
    ImageLibModel library = new MockImageLib(logger);
    Appendable string = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(string);
    IMEControllerGUI controller = new IMEControllerProGUI(library, view);
    controller.acceptCommand("load testRes/test.ppm test\nhorizontal-flip test test-hori\n");

    assertEquals(
        "Received add command with test [[java.awt.Color[r=255,g=0,b=0], "
            + "java.awt.Color[r=0,g=255,b=0], "
            + "java.awt.Color[r=0,g=0,b=255]], "
            + "[java.awt.Color[r=255,g=255,b=0], "
            + "java.awt.Color[r=255,g=255,b=255], "
            + "java.awt.Color[r=0,g=0,b=0]]]\n"
            + "Received read command looking for test\n"
            + "Received add command with test-hori [[java.awt.Color[r=0,g=0,b=255], "
            + "java.awt.Color[r=0,g=255,b=0], "
            + "java.awt.Color[r=255,g=0,b=0]], "
            + "[java.awt.Color[r=0,g=0,b=0], "
            + "java.awt.Color[r=255,g=255,b=255], "
            + "java.awt.Color[r=255,g=255,b=0]]]\n",
        logger.toString());
  }

  @Test
  public void controllerTestHorizontalFlipGUIActionListener() {
    StringBuilder logger = new StringBuilder();
    ImageLibModel library = new MockImageLib(logger);
    Appendable string = new StringBuilder();
    IMEControllerGUI controller =
        new IMEControllerProGUI(library, new ImageProcessorViewImpl(string));

    MockIMEGUIView view = new MockIMEGUIView(controller);
    view.actionPerformed(new ActionEvent(1, 1, "load"));
    view.actionPerformed(new ActionEvent(2, 2, "flip"));

    assertEquals(
        "Received add command with test [[java.awt.Color[r=255,g=0,b=0], "
            + "java.awt.Color[r=0,g=255,b=0], "
            + "java.awt.Color[r=0,g=0,b=255]], "
            + "[java.awt.Color[r=255,g=255,b=0], "
            + "java.awt.Color[r=255,g=255,b=255], "
            + "java.awt.Color[r=0,g=0,b=0]]]\n"
            + "Received read command looking for test\n"
            + "Received add command with test-hori [[java.awt.Color[r=0,g=0,b=255], "
            + "java.awt.Color[r=0,g=255,b=0], "
            + "java.awt.Color[r=255,g=0,b=0]], "
            + "[java.awt.Color[r=0,g=0,b=0], "
            + "java.awt.Color[r=255,g=255,b=255], "
            + "java.awt.Color[r=255,g=255,b=0]]]\n",
        logger.toString());
  }
}
