package model.ImageLibrary;

import model.Image.ImageModel;

/** A class represents the ImageLibModel with all privileges to a ImageLibModel. */
public interface ImageLibModel extends ReadOnlyImageLibModel {
  /**
   * Add the given ImageModel to the ImageLibModel (library)
   *
   * @param key access key for the ImageModel
   * @param value the given ImageModel which will be stored
   */
  void addToLib(String key, ImageModel value);
}
