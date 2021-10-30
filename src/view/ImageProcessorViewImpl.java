package view;

import model.Image.ImageModel;
import model.ImageLibrary.ReadOnlyImageLibModel;

import java.io.File;
import java.io.FileWriter;
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

  /**
   * Save an image to destination.
   *
   * @param imageName The given image name (also the key) to identify which image to export
   * @param filePath The given file path where the image will be stored.
   * @throws IOException when the method fails to write/create file at the given file path.
   */
  @Override
  public void save(String imageName, String filePath) throws IOException {
    ImageModel image = this.libModel.read(imageName);
    File file = new File(filePath);
    if (filePath.contains("/")) {
      boolean createParent = file.getParentFile().mkdirs();
    }

    FileWriter writer = new FileWriter(file, false);
    writer.write(image.imageToString());
    writer.flush();
    writer.close();
  }
}
