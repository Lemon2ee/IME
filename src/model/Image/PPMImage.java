package model.Image;

import model.enums.FlipDirection;
import model.enums.GreyScaleValue;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * The class represents the model of a PPM image processing queue. Including load image into the
 * queue, perform grey scale operation, change the image brightness, flip target image and save the
 * image to given file path.
 */
public class PPMImage implements ImageModel {
  private final Color[][] image;
  private final int height;
  private final int width;
  private final ImageUtil util;

  /**
   * The default constructor
   *
   * @param image a 2d array which represents a PPM image.
   */
  public PPMImage(Color[][] image) {
    if (image == null) {
      throw new IllegalArgumentException("Require non null arguments");
    }
    this.image = image;
    this.height = this.image.length;
    this.width = this.image[0].length;
    this.util = new ImageUtil();
  }

  /**
   * Apply different grey scale configuration to the image.
   *
   * @param op the grey scale operation to be performed as a GreyScaleValue
   * @return A PPMImage with the image after the modification
   */
  @Override
  public ImageModel greyScale(GreyScaleValue op) {
    Map<GreyScaleValue, Function<Color, Color>> greyScaleValueFunctionMap = new HashMap<>();
    greyScaleValueFunctionMap.put(GreyScaleValue.R, this.util::toRed);
    greyScaleValueFunctionMap.put(GreyScaleValue.G, this.util::toGreen);
    greyScaleValueFunctionMap.put(GreyScaleValue.B, this.util::toBlue);
    greyScaleValueFunctionMap.put(GreyScaleValue.Intensity, this.util::toIntensity);
    greyScaleValueFunctionMap.put(GreyScaleValue.Luma, this.util::toLuma);
    greyScaleValueFunctionMap.put(GreyScaleValue.Value, this.util::toValue);

    Function<Color, Color> colorFunction;

    colorFunction = greyScaleValueFunctionMap.getOrDefault(op, null);

    if (colorFunction == null) {
      throw new IllegalArgumentException("Haven't support this grey scale value operation");
    }

    Color[][] output = new Color[this.height][this.width];

    for (int r = 0; r < this.height; r++) {
      for (int c = 0; c < this.width; c++) {
        output[r][c] = colorFunction.apply(this.image[r][c]);
      }
    }

    return new PPMImage(output);
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

    return new PPMImage(output);
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

    return new PPMImage(output);
  }

  /**
   * Deep copy the PPMImage.
   *
   * @return A deep copy of this class
   */
  @Override
  public ImageModel copy() {
    return new PPMImage(this.image);
  }

  /**
   * Return a copy of ReadOnlyImageModel version of this class.
   *
   * @return an ReadOnlyImageModel that has the same content but different memory address
   */
  @Override
  public ReadOnlyImageModel copyReadOnly() {
    return this.copy();
  }

  /**
   * Convert the 2d color array to a single string.
   *
   * @return A string represent the content of a PPM file
   */
  @Override
  public String imageToString() {
    StringBuilder image = new StringBuilder();
    StringBuilder header = new StringBuilder();

    for (Color[] colorRow : this.image) {
      for (Color color : colorRow) {
        int redValue = color.getRed();
        int greenValue = color.getGreen();
        int blueValue = color.getBlue();
        image.append(redValue).append("\n");
        image.append(greenValue).append("\n");
        image.append(blueValue).append("\n");
      }
    }

    header.append("P3\n");
    header.append(
        "# Created by Image Manipulation and Enhancement (IME) written by JerryGCDing "
            + "and Lemon2ee\n");
    header.append(this.width).append(" ").append(this.height).append("\n");
    header.append("255").append("\n");
    header.append("# end of the header\n");
    return header.append(image).toString();
  }
}
