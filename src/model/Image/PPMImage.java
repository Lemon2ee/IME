package model.Image;

import model.enums.FlipDirection;
import model.enums.GreyScaleValue;

import java.awt.Color;
import java.util.Arrays;

/**
 * The class represents the model of a PPM image processing queue. Including load image into the
 * queue, perform grey scale operation, change the image brightness, flip target image and save the
 * image to given file path.
 */
public class PPMImage implements ImageModel {
  private final Color[][] image;
  private final int height;
  private final int width;

  /**
   * The constructor of the PPMModel, takes in no argument and initialize the operation queue of the
   * model.
   *
   * <p>TODO: edit this
   */
  public PPMImage(Color[][] image) {
    if (image == null) {
      throw new IllegalArgumentException("Require non null arguments");
    }
    this.image = image;
    this.height = this.image.length;
    this.width = this.image[0].length;
  }

  @Override
  public ImageModel greyScale(GreyScaleValue op) throws IllegalArgumentException {
    Color[][] src = this.image;
    int height = src.length;
    int width = src[0].length;

    Color[][] output = new Color[height][width];

    switch (op) {
      case R:
        for (int r = 0; r < height; r++) {
          for (int c = 0; c < width; c++) {
            output[r][c] = toRed(src[r][c]);
          }
        }
        break;
      case G:
        for (int r = 0; r < height; r++) {
          for (int c = 0; c < width; c++) {
            output[r][c] = toGreen(src[r][c]);
          }
        }
        break;
      case B:
        for (int r = 0; r < height; r++) {
          for (int c = 0; c < width; c++) {
            output[r][c] = toBlue(src[r][c]);
          }
        }
        break;
      case Value:
        for (int r = 0; r < height; r++) {
          for (int c = 0; c < width; c++) {
            output[r][c] = toValue(src[r][c]);
          }
        }
        break;
      case Luma:
        for (int r = 0; r < height; r++) {
          for (int c = 0; c < width; c++) {
            output[r][c] = toLuma(src[r][c]);
          }
        }
        break;
      default:
        for (int r = 0; r < height; r++) {
          for (int c = 0; c < width; c++) {
            output[r][c] = toIntensity(src[r][c]);
          }
        }
        break;
    }

    return new PPMImage(output);
  }

  @Override
  public ImageModel changeBrightness(int value) throws IllegalArgumentException {

    Color[][] src = this.image;
    int height = src.length;
    int width = src[0].length;

    Color[][] output = new Color[height][width];
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        output[r][c] = colorBrightness(src[r][c], value);
      }
    }

    return new PPMImage(output);
  }

  @Override
  public ImageModel flip(FlipDirection fd) throws IllegalArgumentException {
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

  @Override
  public ImageModel copy() {
    return new PPMImage(this.image);
  }

  /**
   * Perform red component grey scale on a rgb pixel.
   *
   * @param origin the original pixel for grey scale as Color
   * @return the new pixel after grey scale as Color
   */
  private Color toRed(Color origin) {
    return new Color(origin.getRed(), origin.getRed(), origin.getRed());
  }

  /**
   * Perform green component grey scale on a rgb pixel.
   *
   * @param origin the original pixel for grey scale as Color
   * @return the new pixel after grey scale as Color
   */
  private Color toGreen(Color origin) {
    return new Color(origin.getGreen(), origin.getGreen(), origin.getGreen());
  }

  /**
   * Perform blue component grey scale on a rgb pixel.
   *
   * @param origin the original pixel for grey scale as Color
   * @return the new pixel after grey scale as Color
   */
  private Color toBlue(Color origin) {
    return new Color(origin.getBlue(), origin.getBlue(), origin.getBlue());
  }

  /**
   * Perform maximum component grey scale on a rgb pixel.
   *
   * @param origin the original pixel for grey scale as Color
   * @return the new pixel after grey scale as Color
   */
  private Color toValue(Color origin) {
    int maxValue = Math.max(origin.getRed(), Math.max(origin.getGreen(), origin.getBlue()));
    return new Color(maxValue, maxValue, maxValue);
  }

  /**
   * Perform intensity value grey scale on a rgb pixel.
   *
   * @param origin the original pixel for the grey scale as Color
   * @return the new pixel after grey scale as Color
   */
  private Color toIntensity(Color origin) {
    double preAvg = (origin.getRed() + origin.getGreen() + origin.getBlue()) / 3.0;
    int intAvg = (int) Math.round(preAvg);
    return new Color(intAvg, intAvg, intAvg);
  }

  /**
   * Perform luma value grey scale on a rgb pixel.
   *
   * @param origin the original pixel for the grey scale as Color
   * @return the new pixel after grey scale as Color
   */
  private Color toLuma(Color origin) {
    double luma = 0.2126 * origin.getRed() + 0.7152 * origin.getGreen() + 0.0722 * origin.getBlue();
    int intLuma = (int) Math.round(luma);
    return new Color(intLuma, intLuma, intLuma);
  }

  /**
   * Change the rgb brightness of a pixel with given value.
   *
   * @param origin the original pixel to change the brightness as Color
   * @param value the value to be changed for the color brightness
   * @return the new pixel after changing brightness as Color
   */
  private Color colorBrightness(Color origin, int value) {
    int newR = origin.getRed() + value;
    int newG = origin.getGreen() + value;
    int newB = origin.getBlue() + value;

    if (value > 0) {
      newR = Math.min(newR, 255);
      newG = Math.min(newG, 255);
      newB = Math.min(newB, 255);
    } else {
      newR = Math.max(newR, 0);
      newG = Math.max(newG, 0);
      newB = Math.max(newB, 0);
    }

    return new Color(newR, newG, newB);
  }

  @Override
  public String toString() {
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
