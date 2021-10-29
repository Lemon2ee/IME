import controller.IMEController;
import controller.IMEControllerImpl;
import model.ImageModel;
import model.PPMModel;
import org.junit.Test;
import view.ImageProcessorView;
import view.ImageProcessorViewImpl;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.*;

/** A test class for IMEControllerImpl with mock model. */
public class IMEControllerImplTest {

  // Constructor tests with invalid arguments
  @Test(expected = IllegalArgumentException.class)
  public void testControllerConstructorViewNull() {
    StringBuilder log = new StringBuilder();
    ImageModel model = new MockImageModel(log);
    IMEController controller = new IMEControllerImpl(model, new StringReader("q"), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerConstructorModelNull() {
    StringBuilder log = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(System.out, new MockImageModel(log));
    IMEController controller = new IMEControllerImpl(null, new StringReader("q"), view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerConstructorReadableNull() {
    StringBuilder log = new StringBuilder();
    ImageModel model = new MockImageModel(log);
    ImageProcessorView view = new ImageProcessorViewImpl(System.out, model);
    IMEController controller = new IMEControllerImpl(model, null, view);
  }

  // Controller input handling (one line), all valid input
  @Test
  public void testControllerReceiveInputNothing() throws IOException {
    StringBuilder log = new StringBuilder();
    ImageModel model = new MockImageModel(log);
    Appendable builder = new StringBuilder();
    Readable readable = new StringReader("q");
    ImageProcessorView view = new ImageProcessorViewImpl(builder, model);

    IMEController controller = new IMEControllerImpl(model, readable, view);
    controller.initProcessor();

    assertEquals("", log.toString());
    assertEquals("", builder.toString());
  }

  @Test
  public void testControllerReceiveLoadOnly() throws IOException {
    StringBuilder log = new StringBuilder();
    ImageModel model = new MockImageModel(log);
    Appendable builder = new StringBuilder();
    Readable readable = new StringReader("load images/koala.ppm koala\n");
    ImageProcessorView view = new ImageProcessorViewImpl(builder, model);

    IMEController controller = new IMEControllerImpl(model, readable, view);
    controller.initProcessor();

    assertEquals("name koala filePath images/koala.ppm\n", log.toString());
    assertEquals("", builder.toString());
  }

  @Test
  public void testControllerReceiveSaveOnly() throws IOException {
    StringBuilder log = new StringBuilder();
    ImageModel model = new MockImageModel(log);
    Appendable builder = new StringBuilder();
    Readable readable = new StringReader("save images/koala.ppm koala\n");
    ImageProcessorView view = new ImageProcessorViewImpl(builder, model);

    IMEController controller = new IMEControllerImpl(model, readable, view);
    controller.initProcessor();

    assertEquals("filePath images/koala.ppm origin koala\n", log.toString());
    assertEquals("", builder.toString());
  }

  @Test
  public void testControllerReceiveBrightenOnly() throws IOException {
    StringBuilder log = new StringBuilder();
    ImageModel model = new MockImageModel(log);
    Appendable builder = new StringBuilder();
    Readable readable = new StringReader("brighten 10 koala koala-brighten-10\n");
    ImageProcessorView view = new ImageProcessorViewImpl(builder, model);

    IMEController controller = new IMEControllerImpl(model, readable, view);
    controller.initProcessor();

    assertEquals("Origin koala Destination koala-brighten-10 value 10\n", log.toString());
    assertEquals("", builder.toString());
  }

  @Test
  public void testControllerReceiveHorizontalFlipOnly() throws IOException {
    StringBuilder log = new StringBuilder();
    ImageModel model = new MockImageModel(log);
    Appendable builder = new StringBuilder();
    Readable readable = new StringReader("horizontal-flip koala koala-hori\n");
    ImageProcessorView view = new ImageProcessorViewImpl(builder, model);

    IMEController controller = new IMEControllerImpl(model, readable, view);
    controller.initProcessor();

    assertEquals("Origin koala Destination koala-hori fd Horizontal\n", log.toString());
    assertEquals("", builder.toString());
  }

  @Test
  public void testControllerReceiveVerticalFlipOnly() throws IOException {
    StringBuilder log = new StringBuilder();
    ImageModel model = new MockImageModel(log);
    Appendable builder = new StringBuilder();
    Readable readable = new StringReader("vertical-flip koala koala-verti\n");
    ImageProcessorView view = new ImageProcessorViewImpl(builder, model);

    IMEController controller = new IMEControllerImpl(model, readable, view);
    controller.initProcessor();

    assertEquals("Origin koala Destination koala-verti fd Vertical\n", log.toString());
    assertEquals("", builder.toString());
  }

  // Controller input handling (multiple lines)
  @Test
  public void testControllerReceiveVerticalFlipAndSave() throws IOException {
    StringBuilder log = new StringBuilder();
    ImageModel model = new MockImageModel(log);
    Appendable builder = new StringBuilder();
    Readable readable =
        new StringReader("load koala koala-verti\nsave images/koala-backup.ppm koala\n");
    ImageProcessorView view = new ImageProcessorViewImpl(builder, model);

    IMEController controller = new IMEControllerImpl(model, readable, view);
    controller.initProcessor();

    assertEquals(
        "name koala-verti filePath koala\n" + "filePath images/koala-backup.ppm origin koala\n",
        log.toString());
  }

  @Test
  public void testControllerReceiveVerticalFlipAndSaveInvalid() throws IOException {
    StringBuilder log = new StringBuilder();
    ImageModel model = new MockImageModel(log);
    Appendable builder = new StringBuilder();
    Readable readable = new StringReader("load \nsave images/koala-backup.ppm koala\n");
    ImageProcessorView view = new ImageProcessorViewImpl(builder, model);

    IMEController controller = new IMEControllerImpl(model, readable, view);
    controller.initProcessor();

    assertEquals("Insufficient argument given\n", builder.toString());
  }

  @Test
  public void testControllerReceiveRedComponentGreenComponentBlueComponentSaveInvalid()
      throws IOException {
    StringBuilder log = new StringBuilder();
    ImageModel model = new MockImageModel(log);
    Appendable builder = new StringBuilder();
    Readable readable =
        new StringReader(
            "load Koala.ppm koala\n"
                + "red-component koala koala-red\n"
                + "green-component koala koala-green\n"
                + "blue-component koala koala-blue\n"
                + "save ");
    ImageProcessorView view = new ImageProcessorViewImpl(builder, model);

    IMEController controller = new IMEControllerImpl(model, readable, view);
    controller.initProcessor();

    assertEquals("Insufficient argument given\n", builder.toString());
  }
}
