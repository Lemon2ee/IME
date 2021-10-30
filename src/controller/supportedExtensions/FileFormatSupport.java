package controller.supportedExtensions;

import model.Image.ImageModel;

public interface FileFormatSupport {
  ImageModel constructModel(String filepath) throws IllegalArgumentException;
}
