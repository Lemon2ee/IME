package model.image;

import model.enums.FlipDirection;
import model.enums.GreyScaleValue;

import java.awt.*;
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
      throw new IllegalArgumentException("Require non null arguments");
    }
    this.image = image;
    this.height = this.image.length;
    this.width = this.image[0].length;
    this.util = new ImageUtil();
    this.greyScaleValueFunctionMap = new HashMap<>();
    greyScaleValueFunctionMap.put(GreyScaleValue.R, this.util::toRed);
    greyScaleValueFunctionMap.put(GreyScaleValue.G, this.util::toGreen);
    greyScaleValueFunctionMap.put(GreyScaleValue.B, this.util::toBlue);
    greyScaleValueFunctionMap.put(GreyScaleValue.Intensity, this.util::toIntensity);
    greyScaleValueFunctionMap.put(GreyScaleValue.Luma, this.util::toLuma);
    greyScaleValueFunctionMap.put(GreyScaleValue.Value, this.util::toValue);
  }

  /**
   * Apply different grey scale configuration to the image.
   *
   * @param op the grey scale operation to be performed as a GreyScaleValue
   * @return A PPMImage with the image after the modification
   */
  @Override
  public ImageModel greyScale(GreyScaleValue op) {
    Function<Color, Color> colorFunction;

    colorFunction = this.greyScaleValueFunctionMap.getOrDefault(op, null);

    if (colorFunction == null) {
      throw new IllegalArgumentException("Haven't support this grey scale value operation");
    }

    Color[][] output = new Color[this.height][this.width];

    for (int r = 0; r < this.height; r++) {
      for (int c = 0; c < this.width; c++) {
        output[r][c] = colorFunction.apply(this.image[r][c]);
      }
    }

    return new ImageFile(output);
  }

  /**
   * Change the brightness of the image with given value.
   *
   * @param value the value to be changed on the image as an integer
   * @return A PPMImage with after processed image 2d array
   */
  @Override
  public ImageModel changeBrightness(int value) {

    Color[][] src = this.image;
    int height = src.length;
    int width = src[0].length;

    Color[][] output = new Color[height][width];
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        output[r][c] = util.colorBrightness(src[r][c], value);
      }
    }

    return new ImageFile(output);
  }

  /**
   * Flip the image according to the given direction.
   *
   * @param fd the direction of the flip operation as a FlipDirection
   * @return A PPMImage with after processed image 2d array
   */
  @Override
  public ImageModel flip(FlipDirection fd) {
    Color[][] src = this.image;
    Color srcColor;
    int height = src.length;
    int width = src[0].length;

    Color[][] output = new Color[height][width];
    if (fd == FlipDirection.Horizontal) {
      for (int r = 0; r < height; r++) {
        for (int c = 0; c < width; c++) {
          srcColor = src[r][width - 1 - c];
          output[r][c] = new Color(srcColor.getRed(), srcColor.getGreen(), srcColor.getBlue());
        }
      }
    } else {
      for (int r = 0; r < height; r++) {
        for (int c = 0; c < width; c++) {
          srcColor = src[height - 1 - r][c];
          output[r][c] = new Color(srcColor.getRed(), srcColor.getGreen(), srcColor.getBlue());
        }
      }
    }

    return new ImageFile(output);
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

  /**
   * Return a copy of ReadOnlyImageModel version of this class.
   *
   * @return an ReadOnlyImageModel that has the same content but different memory address
   */
  @Override
  public abstract ReadOnlyImageModel copyReadOnly();

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