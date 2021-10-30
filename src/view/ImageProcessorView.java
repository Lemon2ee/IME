package view;

import java.io.IOException;

public interface ImageProcessorView {
  /**
   * Render a specific message to the provided data destination.
   *
   * @param message the message to be transmitted
   * @throws IllegalArgumentException if transmission of the board to the provided data destination fails
   */
  void renderMessage(String message) throws IllegalArgumentException;

  void save(String imageName, String filePath) throws IllegalArgumentException;
}
