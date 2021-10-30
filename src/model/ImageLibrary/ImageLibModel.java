package model.ImageLibrary;

import model.Image.ImageModel;

public interface ImageLibModel extends ReadOnlyImageLibModel {
  void addToLib(String key, ImageModel value);
}
