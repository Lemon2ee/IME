package model.ImageLibrary;

import model.Image.ImageModel;

public interface ReadOnlyImageLibModel {
  ImageModel read(String key);
}
