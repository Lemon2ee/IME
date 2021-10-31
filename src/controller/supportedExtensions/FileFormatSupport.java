package controller.supportedExtensions;

import model.image.ImageModel;

public interface FileFormatSupport {
  ImageModel constructModel(String filepath) throws IllegalArgumentException;
}
