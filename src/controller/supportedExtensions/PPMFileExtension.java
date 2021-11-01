package controller.supportedExtensions;

import model.image.ImageFile;
import model.image.ImageModel;
import utils.ImageUtil;

public class PPMFileExtension implements FileFormatSupport {
  @Override
  public ImageModel constructModel(String filepath) throws IllegalArgumentException {
    return new ImageFile(new ImageUtil().readPPM(filepath));
  }
}
