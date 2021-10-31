package view;

import model.ImageLibrary.ReadOnlyImageLibModel;

import java.io.IOException;

/** The implementation of ImageProcessorView which handles all IO related output method. */
public class ImageProcessorViewImpl implements ImageProcessorView {
  private final Appendable appendable;
  private final ReadOnlyImageLibModel libModel;

  /**
   * The default constructor.
   *
   * @param appendable The appendable object which all output will be flushed to
   * @param libModel The ImageLibrary which acts like a data source
   */
  public ImageProcessorViewImpl(Appendable appendable, ReadOnlyImageLibModel libModel) {
    if (appendable == null || libModel == null) {
      throw new IllegalArgumentException("Requires non null arguments");
    }
    this.libModel = libModel;
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
    this.appendable.append(message);
  }
}
