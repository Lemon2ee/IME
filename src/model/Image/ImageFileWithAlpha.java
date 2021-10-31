package model.Image;

import model.enums.GreyScaleValue;

import java.awt.*;

public class ImageFileWithAlpha extends ABSImageFile {
  public ImageFileWithAlpha(Color[][] image) {
    super(image);
    this.greyScaleValueFunctionMap.put(GreyScaleValue.Alpha, this.util::toAlpha);
  }

  @Override
  public ReadOnlyImageModel copyReadOnly() {
    return null;
  }
}
