package model;

import java.awt.Color;

/**
 * The model of the Image Processor. Including operations of grey scale, brighten or darken by given
 * value, and flip the image vertically or horizontally.
 */
public interface ImageModel {
  /**
   * Load an image using 2d Color array into the model and mark by given name.
   *
<<<<<<< Updated upstream
   * @param name  the name of the image to be saved as a String
   * @param filePath the file path to read the image from
=======
   * @param name the name of the image to be saved as a String
   * @param filePath the path of the file to load as PPM3
>>>>>>> Stashed changes
   * @throws IllegalStateException if the file path is invalid or the image format is not P3
   */
  void load(String name, String filePath) throws IllegalArgumentException;

  /**
   * Perform grey scale operation on the given image.
   *
   * @param origin the target image name to perform the operation as String
   * @param destination the name of image to be saved as after operation
<<<<<<< Updated upstream
   * @param op          the grey scale operation to be performed as a GreyScaleValue
   * @throws IllegalArgumentException if the given origin name does not exist
   */
  void greyScale(String origin, String destination, GreyScaleValue op)
          throws IllegalArgumentException;
=======
   * @param op the grey scale operation to be performed as a GreyScaleValue
   * @throws IllegalArgumentException if the given origin name does not exist
   */
  void greyScale(String origin, String destination, GreyScaleValue op)
      throws IllegalArgumentException;
>>>>>>> Stashed changes

  /**
   * Change the brightness of each pixel of the image by a given value.
   *
   * @param origin the target image name to perform the operation as String
   * @param destination the name of image to be saved as after operation
<<<<<<< Updated upstream
   * @param value       the value to be changed on the image as an integer
   * @throws IllegalArgumentException if the given origin name does not exist
   */
  void changeBrightness(String origin, String destination, int value)
          throws IllegalArgumentException;
=======
   * @param value the value to be changed on the image as an integer
   * @throws IllegalArgumentException if the given origin name does not exist
   */
  void changeBrightness(String origin, String destination, int value)
      throws IllegalArgumentException;
>>>>>>> Stashed changes

  /**
   * Flip the image by the given direction.
   *
   * @param origin the target image name to perform the operation as String
   * @param destination the name of image to be saved as after operation
<<<<<<< Updated upstream
   * @param fd          the direction of the flip operation as a FlipDirection
=======
   * @param fd the direction of the flip operation as a FlipDirection
>>>>>>> Stashed changes
   * @throws IllegalArgumentException if the given origin name does not exist
   */
  void flip(String origin, String destination, FlipDirection fd) throws IllegalArgumentException;

  /**
   * Save the target image data to a ppm image file.
   *
   * @param filePath the file path to save the image as a String
<<<<<<< Updated upstream
   * @param origin   the name of the image to be saved
=======
   * @param origin the name of the image to be saved
>>>>>>> Stashed changes
   * @throws IllegalArgumentException if the given origin name does not exist
   */
  void save(String filePath, String origin) throws IllegalArgumentException;
}
