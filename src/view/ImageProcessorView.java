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
}
