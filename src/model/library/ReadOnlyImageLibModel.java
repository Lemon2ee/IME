package model.library;

import model.image.ImageModel;

/**
 * An interface represents the read only ImageLibModel which provides limited access to the model.
 */
public interface ReadOnlyImageLibModel {

  /**
   * Read from the ImageLibModel (library).
   *
   * @param key The key of ImageModel
   * @return A copy of the imageModel found in the library
   */
  ImageModel read(String key) throws IllegalArgumentException;
}
