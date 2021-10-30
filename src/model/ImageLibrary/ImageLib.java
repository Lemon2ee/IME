package model.ImageLibrary;

import model.Image.ImageModel;

import java.util.HashMap;
import java.util.Map;

public class ImageLib implements ImageLibModel {
  private final Map<String, ImageModel> dictionary;

  public ImageLib() {
    this.dictionary = new HashMap<>();
  }

  @Override
  public void addToLib(String key, ImageModel value) {
    if (key == null || value == null) {
      throw new IllegalArgumentException("Does not accept null arguments");
    }
    dictionary.put(key, value);
  }

  @Override
  public ImageModel read(String key) {
    // TODO: add .copy in the model
    return this.dictionary.get(key).copy();
  }
}
