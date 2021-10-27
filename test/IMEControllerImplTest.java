import controller.IMEController;
import controller.IMEControllerImpl;
import model.ImageModel;
import org.junit.Test;
import view.ImageProcessorView;
import view.ImageProcessorViewImpl;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.*;

public class IMEControllerImplTest {
  @Test(expected = IllegalArgumentException.class)
  public void testControllerConstructorViewNull(){
    StringBuilder log = new StringBuilder();
    ImageModel model = new MockImageModel(log);
    IMEController controller = new IMEControllerImpl(model, new StringReader("q"), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerConstructorModelNull(){
    StringBuilder log = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(System.out, new MockImageModel(log));
    IMEController controller = new IMEControllerImpl(null, new StringReader("q"), view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerConstructorReadableNull(){
    StringBuilder log = new StringBuilder();
    ImageModel model = new MockImageModel(log);
    ImageProcessorView view = new ImageProcessorViewImpl(System.out, model);
    IMEController controller = new IMEControllerImpl(model, null, view);
  }

  @Test
  public void testControllerReceiveInputNothing() throws IOException {
    StringBuilder log = new StringBuilder();
    ImageModel model = new MockImageModel(log);
    Appendable builder = new StringBuilder();
    Readable readable = new StringReader("q");
    ImageProcessorView view  = new ImageProcessorViewImpl(builder, model);

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
    ImageProcessorView view  = new ImageProcessorViewImpl(builder, model);

    IMEController controller = new IMEControllerImpl(model, readable, view);
    controller.initProcessor();

    assertEquals("name koala filePath images/koala.ppm", log.toString());
    assertEquals("", builder.toString());
  }

  @Test
  public void testControllerReceiveSaveOnly() throws IOException {
    StringBuilder log = new StringBuilder();
    ImageModel model = new MockImageModel(log);
    Appendable builder = new StringBuilder();
    Readable readable = new StringReader("save images/koala.ppm koala\n");
    ImageProcessorView view  = new ImageProcessorViewImpl(builder, model);

    IMEController controller = new IMEControllerImpl(model, readable, view);
    controller.initProcessor();

    assertEquals("filePath images/koala.ppm origin koala", log.toString());
    assertEquals("", builder.toString());
  }
}