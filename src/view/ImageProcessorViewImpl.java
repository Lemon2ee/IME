package view;

import java.io.IOException;

public class ImageProcessorViewImpl implements ImageProcessorView {
  private final Appendable appendable;

  public ImageProcessorViewImpl(Appendable appendable) {
    if (appendable == null) {
      throw new IllegalArgumentException("Requires non null arguments");
    }
    this.appendable = appendable;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.appendable.append(message);
  }
}
