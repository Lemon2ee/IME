package model.feature;

import java.awt.*;

/**
 * The interface represents the Featured functionalities that are implemented by the ImageModel.
 * Open for extension easier to add new functionalities as function objects.
 */
public interface FeatureCommand {
  /**
   * Apply the target command instruction to the provided image data.
   *
   * @param image the image data to be processed as a 2d array of Color
   * @return new image data after operation as 2d array of Color
   */
  Color[][] apply(Color[][] image);
}
