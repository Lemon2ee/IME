package model.Image;

import java.awt.*;

/**
 * The class represents the model of a PPM image processing queue. Including load image into the
 * queue, perform grey scale operation, change the image brightness, flip target image and save the
 * image to given file path.
 */
public class ImageFile extends ABSImageFile {

  public ImageFile(Color[][] image) {
    super(image);
  }

  @Override
  public ReadOnlyImageModel copyReadOnly() {
    return new ImageFile(this.imageArrayCopy());
  }
}
