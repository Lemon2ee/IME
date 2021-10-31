package controller.supportedExtensions;

import model.Image.ImageFile;
import model.Image.ImageModel;
import model.Image.ImageUtil;

public class PPMFileExtension implements FileFormatSupport {
  @Override
  public ImageModel constructModel(String filepath) throws IllegalArgumentException {
    return new ImageFile(new ImageUtil().readPPM(filepath));
  }
}
