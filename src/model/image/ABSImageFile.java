package model.image;

import model.enums.GreyScaleValue;
import utils.ImageUtil;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class ABSImageFile implements ImageModel {
  protected final Color[][] image;
  protected final int height;
  protected final int width;
  protected final ImageUtil util;
  protected final Map<GreyScaleValue, Function<Color, Color>> greyScaleValueFunctionMap;

  /**
   * The default constructor
   *
   * @param image a 2d array which represents a PPM image.
   */
  public ABSImageFile(Color[][] image) {
    if (image == null) {
      throw new IllegalArgumentException("Require non null arguments\n");
    }

    for (Color[] row : image) {
      if (row == null) {
        throw new IllegalArgumentException("Does not accept array with null value in it\n");
      }

      if (Arrays.asList(row).contains(null)) {
        throw new IllegalArgumentException("Does not accept array with null value in it\n");
      }
    }

    this.image = image;
    this.height = this.image.length;
    this.width = this.image[0].length;
    this.util = new ImageUtil();
    this.greyScaleValueFunctionMap = new HashMap<>();
  }

  /**
   * Deep copy the PPMImage.
   *
   * @return A deep copy of this class
   */
  @Override
  public ImageModel copy() {
    return new ImageFile(this.image);
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public Color[][] imageArrayCopy() {
    Color[][] output = new Color[this.height][this.width];

    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        Color old = this.image[row][col];
        int red = old.getRed();
        int green = old.getGreen();
        int blue = old.getBlue();
        Color newColor = new Color(red, green, blue);

        output[row][col] = newColor;
      }
    }
    return output;
  }
}
