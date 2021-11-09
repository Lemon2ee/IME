package model.feature.basics;

import model.feature.FeatureCommand;
import utils.ImageUtil;

import java.awt.Color;

/**
 * The class represents a feature to perform change brightness operation on the image. Can be either
 * brighten or darken the pixels in the given image.
 */
public class ChangeBrightness implements FeatureCommand {
  private final int value;

  /**
   * Create a function object to change the brightness of the image by given value.
   *
   * @param value the value of brightness to be changed on the image as integer
   */
  public ChangeBrightness(int value) {
    this.value = value;
  }

  /**
   * Apply brightness change to the given 2d color array which represents an image.
   *
   * @param image the image data to be processed as a 2d array of Color
   * @return An after-modification 2d array which represents an image
   */
  @Override
  public Color[][] apply(Color[][] image) {
    int height = image.length;
    int width = image[0].length;

    ImageUtil util = new ImageUtil();

    Color[][] output = new Color[height][width];
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        output[r][c] = util.colorBrightness(image[r][c], value);
      }
    }

    return output;
  }

  @Override
  public String toString() {
    return "ChangeBrightness{" + "value=" + value + "}\n";
  }
}
