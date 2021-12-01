package model.library;

import model.image.ImageModel;

import java.util.Set;

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

  /**
   * Fetch the keys of image hashmap.
   *
   * @return the keys of image in a set of string.
   */
  Set<String> getKeys();
}
