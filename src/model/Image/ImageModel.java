package model.Image;

import model.enums.FlipDirection;
import model.enums.GreyScaleValue;

/**
 * The model of the Image Processor. Including operations of grey scale, brighten or darken by given
 * value, and flip the image vertically or horizontally.
 */
public interface ImageModel {
  /**
   * Perform grey scale operation on the given image.
   *
   * @param op the grey scale operation to be performed as a GreyScaleValue
   * @throws IllegalArgumentException if the given origin name does not exist
   */
  ImageModel greyScale(GreyScaleValue op) throws IllegalArgumentException;

  /**
   * Change the brightness of each pixel of the image by a given value.
   *
   * @param value the value to be changed on the image as an integer
   * @throws IllegalArgumentException if the given origin name does not exist
   */
  ImageModel changeBrightness(int value) throws IllegalArgumentException;

  /**
   * Flip the image by the given direction.
   *
   * @param fd the direction of the flip operation as a FlipDirection
   * @throws IllegalArgumentException if the given origin name does not exist
   */
  ImageModel flip(FlipDirection fd) throws IllegalArgumentException;

  /**
   *
   * @return
   */
  ImageModel copy();

  /**
   *
   * @return
   */
  String imageString();
}
