package view;

import java.io.IOException;

/** An interface represents the view of the Image processor program. */
public interface ImageProcessorView {
  /**
   * Render a specific message to the provided data destination.
   *
   * @param message the message to be transmitted
   * @throws IllegalArgumentException if transmission of the board to the provided data destination
   *     fails
   */
  void renderMessage(String message) throws IOException;

  /**
   * Save an image to the given file path.
   *
   * @param imageName The given image name (also the key) to identify which image to export
   * @param filePath The given file path where the image will be stored.
   * @throws IllegalArgumentException when the method failed to write the file
   */
  void save(String imageName, String filePath) throws IOException;
}
