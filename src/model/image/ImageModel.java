package model.image;

import model.enums.FilterType;
import model.enums.FlipDirection;
import model.enums.GreyScaleValue;
import model.filter.Filter;

/**
 * The model of the Image Processor. Including operations of grey scale, brighten or darken by given
 * value, and flip the image vertically or horizontally.
 */
public interface ImageModel extends ReadOnlyImageModel {
  /**
   * Perform grey scale operation on the given image.
   *
   * @param op the grey scale operation to be performed as a GreyScaleValue
   * @return the new image data after operation as ImageModel
   */
  ImageModel greyScale(GreyScaleValue op);

  /**
   * Change the brightness of each pixel of the image by a given value.
   *
   * @param value the value to be changed on the image as an integer
   * @return the new image data after operation as ImageModel
   */
  ImageModel changeBrightness(int value);

  /**
   * Flip the image by the given direction.
   *
   * @param fd the direction of the flip operation as a FlipDirection
   * @return the new image data after operation as ImageModel
   */
  ImageModel flip(FlipDirection fd);

  /**
   * Filter the image with the specified operation.
   *
   * @param ft the type of filter operation to be performed
   * @return the new image data after operation as ImageModel
   */
  ImageModel filter(FilterType ft);

  /**
   * Return a deep copy of the current class.
   *
   * @return An ImageModel object with same content but different memory address
   */
  ImageModel copy();
}
