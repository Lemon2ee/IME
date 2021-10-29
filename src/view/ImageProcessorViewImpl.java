package view;

import model.ImageModel;

import java.io.IOException;

public class ImageProcessorViewImpl implements ImageProcessorView {
  private final Appendable appendable;
  private final ImageModel model;

  public ImageProcessorViewImpl(Appendable appendable, ImageModel model) {
    if (appendable == null || model == null) {
      throw new IllegalArgumentException("Requires non null arguments");
    }
    this.model = model;
    this.appendable = appendable;
  }

  @Override
  public void renderMessage(String message) throws IllegalArgumentException {
    try {
      this.appendable.append(message);
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to render");
    }
  }
}
