import controller.IMEController;
import controller.IMEControllerBasic;
import controller.IMEControllerPro;
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
        new IMEControllerBasic(
            null, new InputStreamReader(System.in), new ImageProcessorViewImpl(System.out));
  }

  @Test(expected = IllegalArgumentException.class)
  public void controllerConstructor2() {
    ImageLib library = new ImageLib();
    IMEController controller =
        new IMEControllerBasic(library, null, new ImageProcessorViewImpl(System.out));
  }

  @Test(expected = IllegalArgumentException.class)
  public void controllerConstructor3() {
    ImageLib library = new ImageLib();
    IMEController controller =
        new IMEControllerBasic(library, new InputStreamReader(System.in), null);
  }

  @Test
  public void controllerTestLoad() {
    StringBuilder logger = new StringBuilder();
    ImageLibModel library = new MockImageLib(logger);
    Readable readable = new StringReader("load test.ppm test");
    Appendable string = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(string);
    IMEController controller = new IMEControllerBasic(library, readable, view);
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
    ImageProcessorView view = new ImageProcessorViewImpl(string);
    IMEController controller = new IMEControllerBasic(library, readable, view);
    controller.initProcessor();

    assertEquals("", logger.toString());
  }

  @Test
  public void controllerTestSave() {
    StringBuilder logger = new StringBuilder();
    ImageLibModel library = new MockImageLib(logger);
    Readable readable = new StringReader("load test.ppm test\nsave test.ppm test");
    Appendable string = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(string);
    IMEController controller = new IMEControllerBasic(library, readable, view);
    controller.initProcessor();

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
  public void controllerTestHorizontalFlip() {
    StringBuilder logger = new StringBuilder();
    ImageLibModel library = new MockImageLib(logger);
    Readable readable = new StringReader("load test.ppm test\nhorizontal-flip test test-hori");
    Appendable string = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(string);
    IMEController controller = new IMEControllerBasic(library, readable, view);
    controller.initProcessor();

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
  public void controllerTestVerticalFlip() {
    StringBuilder logger = new StringBuilder();
    ImageLibModel library = new MockImageLib(new StringBuilder());
    library.addToLib("test", new MockImageFile(logger));
    Readable readable = new StringReader("vertical-flip test test-verti\n");
    Appendable string = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(string);
    IMEController controller = new IMEControllerBasic(library, readable, view);
    controller.initProcessor();

    assertEquals(
        "Received copy image array command\n"
            + "Received Copy command\n"
            + "Flip{direction=Vertical}\n"
            + "Received copy image array command\n",
        logger.toString());
  }

  @Test
  public void controllerTestRedComponent() {
    StringBuilder logger = new StringBuilder();
    ImageLibModel library = new MockImageLib(new StringBuilder());
    library.addToLib("test", new MockImageFile(logger));
    Readable readable = new StringReader("red-component test test-red");
    Appendable string = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(string);
    IMEController controller = new IMEControllerBasic(library, readable, view);
    controller.initProcessor();

    assertEquals(
        "Received copy image array command\n"
            + "Received Copy command\n"
            + "GreyScale{value=R}\n"
            + "Received copy image array command\n",
        logger.toString());
  }

  @Test
  public void controllerTestGreenComponent() {
    StringBuilder logger = new StringBuilder();
    ImageLibModel library = new MockImageLib(new StringBuilder());
    library.addToLib("test", new MockImageFile(logger));
    Readable readable = new StringReader("green-component test test-green");
    Appendable string = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(string);
    IMEController controller = new IMEControllerBasic(library, readable, view);
    controller.initProcessor();

    assertEquals(
        "Received copy image array command\n"
            + "Received Copy command\n"
            + "GreyScale{value=G}\n"
            + "Received copy image array command\n",
        logger.toString());
  }

  @Test
  public void controllerTestBlueComponent() {
    StringBuilder logger = new StringBuilder();
    ImageLibModel library = new MockImageLib(new StringBuilder());
    library.addToLib("test", new MockImageFile(logger));
    Readable readable = new StringReader("blue-component test test-blue");
    Appendable string = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(string);
    IMEController controller = new IMEControllerBasic(library, readable, view);
    controller.initProcessor();

    assertEquals(
        "Received copy image array command\n"
            + "Received Copy command\n"
            + "GreyScale{value=B}\n"
            + "Received copy image array command\n",
        logger.toString());
  }

  @Test
  public void controllerTestValueComponent() {
    StringBuilder logger = new StringBuilder();
    ImageLibModel library = new MockImageLib(new StringBuilder());
    library.addToLib("test", new MockImageFile(logger));
    Readable readable = new StringReader("value-component test test-value");
    Appendable string = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(string);
    IMEController controller = new IMEControllerBasic(library, readable, view);
    controller.initProcessor();

    assertEquals(
        "Received copy image array command\n"
            + "Received Copy command\n"
            + "GreyScale{value=Value}\n"
            + "Received copy image array command\n",
        logger.toString());
  }

  @Test
  public void controllerTestLumaComponent() {
    StringBuilder logger = new StringBuilder();
    ImageLibModel library = new MockImageLib(new StringBuilder());
    library.addToLib("test", new MockImageFile(logger));
    Readable readable = new StringReader("luma-component test test-luma");
    Appendable string = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(string);
    IMEController controller = new IMEControllerBasic(library, readable, view);
    controller.initProcessor();

    assertEquals(
        "Received copy image array command\n"
            + "Received Copy command\n"
            + "GreyScale{value=Luma}\n"
            + "Received copy image array command\n",
        logger.toString());
  }

  @Test
  public void controllerTestIntensityComponent() {
    StringBuilder logger = new StringBuilder();
    ImageLibModel library = new MockImageLib(new StringBuilder());
    library.addToLib("test", new MockImageFile(logger));
    Readable readable = new StringReader("intensity-component test test-intensity");
    Appendable string = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(string);
    IMEController controller = new IMEControllerBasic(library, readable, view);
    controller.initProcessor();

    assertEquals(
        "Received copy image array command\n"
            + "Received Copy command\n"
            + "GreyScale{value=Intensity}\n"
            + "Received copy image array command\n",
        logger.toString());
  }

  @Test
  public void controllerTestIntensityComponentLotsOfSpaces() {
    StringBuilder logger = new StringBuilder();
    ImageLibModel library = new MockImageLib(new StringBuilder());
    library.addToLib("test", new MockImageFile(logger));
    Readable readable =
        new StringReader("intensity-component               test        " + "test-intensity");
    Appendable string = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(string);
    IMEController controller = new IMEControllerBasic(library, readable, view);
    controller.initProcessor();

    assertEquals(
        "Received copy image array command\n"
            + "Received Copy command\n"
            + "GreyScale{value=Intensity}\n"
            + "Received copy image array command\n",
        logger.toString());
  }

  @Test
  public void controllerTestIntensityComponentInComment() {
    StringBuilder logger = new StringBuilder();
    ImageLibModel library = new MockImageLib(new StringBuilder());
    library.addToLib("test", new MockImageFile(logger));
    Readable readable = new StringReader("# intensity-component test test-intensity\n");
    Appendable string = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(string);
    IMEController controller = new IMEControllerBasic(library, readable, view);
    controller.initProcessor();

    assertEquals("Received copy image array command\n", logger.toString());
  }

  @Test
  public void controllerTestNewLine() {
    StringBuilder logger = new StringBuilder();
    ImageLibModel library = new MockImageLib(new StringBuilder());
    library.addToLib("test", new MockImageFile(logger));
    Readable readable = new StringReader("\n");
    Appendable string = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(string);
    IMEController controller = new IMEControllerBasic(library, readable, view);
    controller.initProcessor();

    assertEquals("Received copy image array command\n", logger.toString());
  }

  @Test
  public void controllerTestBlur() {
    StringBuilder logger = new StringBuilder();
    ImageLibModel library = new MockImageLib(new StringBuilder());
    library.addToLib("test", new MockImageFile(logger));
    Readable readable = new StringReader("blur test test\n");
    Appendable string = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(string);
    IMEController controller = new IMEControllerPro(library, readable, view);
    controller.initProcessor();

    assertEquals(
        "Received copy image array command\n"
            + "Received Copy command\n"
            + "Filter{type=Blur}\n"
            + "Received copy image array command\n",
        logger.toString());
  }

  @Test
  public void controllerTestSharpen() {
    StringBuilder logger = new StringBuilder();
    ImageLibModel library = new MockImageLib(new StringBuilder());
    library.addToLib("test", new MockImageFile(logger));
    Readable readable = new StringReader("sharper test test\n");
    Appendable string = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(string);
    IMEController controller = new IMEControllerPro(library, readable, view);
    controller.initProcessor();

    assertEquals(
        "Received copy image array command\n"
            + "Received Copy command\n"
            + "Filter{type=Sharpen}\n"
            + "Received copy image array command\n",
        logger.toString());
  }

  @Test
  public void controllerTestSepiaComponent() {
    StringBuilder logger = new StringBuilder();
    ImageLibModel library = new MockImageLib(new StringBuilder());
    library.addToLib("test", new MockImageFile(logger));
    Readable readable = new StringReader("sepia-component test test-sepia\n");
    Appendable string = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(string);
    IMEController controller = new IMEControllerPro(library, readable, view);
    controller.initProcessor();

    assertEquals(
        "Received copy image array command\n"
            + "Received Copy command\n"
            + "GreyScale{value=Sepia}\n"
            + "Received copy image array command\n",
        logger.toString());
  }

  @Test
  public void controllerTestAlphaComponent() {
    StringBuilder logger = new StringBuilder();
    ImageLibModel library = new MockImageLib(new StringBuilder());
    library.addToLib("test", new MockImageFile(logger));
    Readable readable = new StringReader("alpha-component test test-sepia\n");
    Appendable string = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(string);
    IMEController controller = new IMEControllerPro(library, readable, view);
    controller.initProcessor();

    assertEquals(
        "Received copy image array command\n"
            + "Received Copy command\n"
            + "GreyScale{value=Alpha}\n"
            + "Received copy image array command\n",
        logger.toString());
  }
}
