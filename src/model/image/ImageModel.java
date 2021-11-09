package model.image;

import model.feature.FeatureCommand;

import java.awt.*;

/**
 * The model of the Image Processor. Including operations of grey scale, brighten or darken by given
 * value, and flip the image vertically or horizontally.
 */
public interface ImageModel {

  /**
   * To get the height of the current image.
   *
   * @return the height of the image as integer
   */
  int getHeight();

  /**
   * To get the width of the current image.
   *
   * @return the width of the image as integer
   */
  int getWidth();

  /**
   * To deep copy the current image data into a new array.
   *
   * @return deep copy of the current image as a 2d array of Color
   */
  Color[][] imageArrayCopy();

  /**
   * Return a deep copy of the current class.
   *
   * @return An ImageModel object with same content but different memory address
   */
  ImageModel copy();

  /**
   * Apply the given functional command to the current image data.
   *
   * @param command the command function to be applied as FeatureCommand
   */
  void applyFunctional(FeatureCommand command);
}
