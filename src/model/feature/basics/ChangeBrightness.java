package model.feature.basics;

import model.feature.FeatureCommand;
import utils.ImageUtil;

import java.awt.*;

public class ChangeBrightness implements FeatureCommand {
  private final int value;

  public ChangeBrightness(int value) {
    this.value = value;
  }

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
}
