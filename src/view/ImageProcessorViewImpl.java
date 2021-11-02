package view;

import java.io.IOException;

/** The implementation of ImageProcessorView which handles all IO related output method. */
public class ImageProcessorViewImpl implements ImageProcessorView {
  private final Appendable appendable;

  /**
   * The default constructor.
   *
   * @param appendable The appendable object which all output will be flushed to
   */
  public ImageProcessorViewImpl(Appendable appendable) {
    if (appendable == null) {
      throw new IllegalArgumentException("Requires non null arguments");
    }
    this.appendable = appendable;
  }

  /**
   * Append the given message to the appendable object.
   *
   * @param message the message to be transmitted
   * @throws IllegalArgumentException when transmission to the view fails
   */
  @Override
  public void renderMessage(String message) throws IOException {
    if (message == null) {
      throw new IllegalArgumentException("No able to render a null object");
    }
    this.appendable.append(message);
  }
}
