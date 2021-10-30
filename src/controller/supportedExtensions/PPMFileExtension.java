package controller.supportedExtensions;

import model.Image.ImageModel;
import model.Image.ImageUtil;
import model.Image.PPMImage;

public class PPMFileExtension implements FileFormatSupport {
  @Override
  public ImageModel constructModel(String filepath) throws IllegalArgumentException {
    return new PPMImage(new ImageUtil().readPPM(filepath));
  }
}
