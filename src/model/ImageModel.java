package model;

import java.awt.Color;

/**
 * The model of the Image Processor. Including operations of grey scale, brighten or darken by
 * given value, and flip the image vertically or horizontally.
 */
public interface ImageModel {
  /**
   * Load an image using 2d Color array into the model and mark by given name.
   *
   * @param name  the name of the image to be saved as a String
   * @param image the data of the image as a 2d Color array
   * @throws IllegalStateException if the file path is invalid or the image format is not P3
   */
  void load(String name, Color[][] image) throws IllegalArgumentException;

  /**
   * Perform grey scale operation on the given image.
   *
   * @param op the grey scale operation to be performed as a GreyScaleValye
   * @return the image after grey scale operation as an imageModel.
   */
  ImageModel greyScale(GreyScaleValue op);

  /**
   * Change the brightness of each pixel of the image by a given value.
   *
   * @param value the value to be changed on the image as an integer
   * @return the image after changing its brightness operation
   */
  ImageModel changeBrightness(int value);

  /**
   * Flip the image by the given direction.
   *
   * @param fd the direction of the flip operation as a FlipDirection
   * @return the image after flip
   */
  ImageModel flip(FlipDirection fd);
}
