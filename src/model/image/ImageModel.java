package model.image;

import model.feature.FeatureCommand;

import java.awt.*;

/**
 * The model of the Image Processor. Including operations of grey scale, brighten or darken by given
 * value, and flip the image vertically or horizontally.
 */
public interface ImageModel {

  int getHeight();

  int getWidth();

  Color[][] imageArrayCopy();

  /**
   * Return a deep copy of the current class.
   *
   * @return An ImageModel object with same content but different memory address
   */
  ImageModel copy();

  void applyFunctional(FeatureCommand command);
}
