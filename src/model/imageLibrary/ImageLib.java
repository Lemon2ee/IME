package model.imageLibrary;

import model.image.ImageModel;

import java.util.HashMap;
import java.util.Map;

/**
 * A class represents the library of imageModel, capable of reading from and putting back ImageModel
 * to the library.
 */
public class ImageLib implements ImageLibModel {
  private final Map<String, ImageModel> dictionary;

  /** Default constructor which initialize the dictionary with a new empty hashmap. */
  public ImageLib() {
    this.dictionary = new HashMap<>();
  }

  /**
   * Add an ImageModel to the dictionary.
   *
   * @param key access key for the ImageModel
   * @param value the given ImageModel which will be stored
   */
  @Override
  public void addToLib(String key, ImageModel value) {
    if (key == null || value == null) {
      throw new IllegalArgumentException("Does not accept null arguments");
    }
    dictionary.put(key, value);
  }

  /**
   * Read the ImageModel correspond to the given key.
   *
   * @param key The key of ImageModel
   * @return A copy of the found ImageModel
   * @throws IllegalArgumentException when the value correspond to the given key does not exist in
   *     the dictionary
   */
  @Override
  public ImageModel read(String key) throws IllegalArgumentException {
    if (key == null) {
      throw new IllegalArgumentException("Does not accept null arguments");
    }

    ImageModel imageModel = this.dictionary.getOrDefault(key, null);

    if (imageModel == null) {
      throw new IllegalArgumentException("Image not found");
    }

    return imageModel.copy();
  }
}
