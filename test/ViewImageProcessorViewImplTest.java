import org.junit.Test;
import view.ImageProcessorView;
import view.ImageProcessorViewImpl;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/** Test the ImageProcessorViewImpl, including rendering message to the given appendable. */
public class ViewImageProcessorViewImplTest {
  @Test(expected = IllegalArgumentException.class)
  public void testConstructor() {
    ImageProcessorView view = new ImageProcessorViewImpl(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRenderNull() throws IOException {
    ImageProcessorView view = new ImageProcessorViewImpl(new StringBuilder());
    view.renderMessage(null);
  }

  @Test
  public void testRenderMessage() throws IOException {
    Appendable stringBuilder = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(stringBuilder);
    view.renderMessage("This is a sample error message");
    assertEquals("This is a sample error message", stringBuilder.toString());
  }
}
