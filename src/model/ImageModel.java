package model;

import model.enums.FlipDirection;
import model.enums.GreyScaleValue;

import java.awt.Color;
import java.io.IOException;

/**
 * The model of the Image Processor. Including operations of grey scale, brighten or darken by given
 * value, and flip the image vertically or horizontally.
 */
public interface ImageModel {
  /**
   * Load an image using 2d Color array into the model and mark by given name.
   *
   * @param name     the name of the image to be saved as a String
   * @param filePath the path of the file to load as PPM3
   * @throws IllegalStateException if the file path is invalid or the image format is not P3
   */
  void load(String name, String filePath) throws IllegalArgumentException;

  /**
   * Perform grey scale operation on the given image.
   *
   * @param origin      the target image name to perform the operation as String
   * @param destination the name of image to be saved as after operation
   * @param op          the grey scale operation to be performed as a GreyScaleValue
   * @throws IllegalArgumentException if the given origin name does not exist
   */
  void greyScale(String origin, String destination, GreyScaleValue op)
          throws IllegalArgumentException;

  /**
   * Change the brightness of each pixel of the image by a given value.
   *
   * @param origin      the target image name to perform the operation as String
   * @param destination the name of image to be saved as after operation
   * @param value       the value to be changed on the image as an integer
   * @throws IllegalArgumentException if the given origin name does not exist
   */
  void changeBrightness(String origin, String destination, int value)
          throws IllegalArgumentException;

  /**
   * Flip the image by the given direction.
   *
   * @param origin      the target image name to perform the operation as String
   * @param destination the name of image to be saved as after operation
   * @param fd          the direction of the flip operation as a FlipDirection
   * @throws IllegalArgumentException if the given origin name does not exist
   */
  void flip(String origin, String destination, FlipDirection fd) throws IllegalArgumentException;

  /**
   * Save the target image data to a ppm image file.
   *
   * @param filePath the file path to save the image as a String
   * @param origin   the name of the image to be saved
   * @throws IllegalArgumentException if the given origin name does not exist
   */
  void save(String filePath, String origin) throws IllegalArgumentException, IOException;

  /**
   * To get the target image data with given key, ONLY USED FOR TESTING. Use deep copying data
   * from the original array to avoid mutate.
   *
   * @param name the name of the image data as String
   * @return the deep copied array of the original image data as 2d array of Color
   * @throws IllegalArgumentException if the given image name does not exist
   */
  Color[][] getFromKey(String name) throws IllegalArgumentException;
}
