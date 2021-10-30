package view;

import model.Image.ImageModel;
import model.ImageLibrary.ReadOnlyImageLibModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ImageProcessorViewImpl implements ImageProcessorView {
  private final Appendable appendable;
  private final ReadOnlyImageLibModel libModel;

  public ImageProcessorViewImpl(Appendable appendable, ReadOnlyImageLibModel libModel) {
    if (appendable == null || libModel == null) {
      throw new IllegalArgumentException("Requires non null arguments");
    }
    this.libModel = libModel;
    this.appendable = appendable;
  }

  @Override
  public void renderMessage(String message) throws IllegalArgumentException {
    try {
      this.appendable.append(message);
    } catch (IOException e) {
      // TODO: add message
      throw new IllegalArgumentException("Failed to render");
    }
  }

  @Override
  public void save(String imageName, String filePath) throws IllegalArgumentException {
    ImageModel image = this.libModel.read(imageName);
    try {
      File file = new File(filePath);
      if (filePath.contains("/")) {
        boolean createParent = file.getParentFile().mkdirs();
      }

      FileWriter writer = new FileWriter(file, false);
      writer.write(image.toString());
      writer.flush();
      writer.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("Save method caused an IOException\n");
    }
  }
}
