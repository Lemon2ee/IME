package model.feature.basics;

import model.enums.FlipDirection;
import model.feature.FeatureCommand;

import java.awt.*;
import java.util.Objects;

public class Flip implements FeatureCommand {
  private final FlipDirection direction;

  public Flip(FlipDirection direction) {
    this.direction = Objects.requireNonNull(direction);
  }

  @Override
  public Color[][] apply(Color[][] image) {

    int height = image.length;
    int width = image[0].length;

    Color[][] output = new Color[height][width];
    Color srcColor;
    if (this.direction == FlipDirection.Horizontal) {
      for (int r = 0; r < height; r++) {
        for (int c = 0; c < width; c++) {
          srcColor = image[r][width - 1 - c];
          output[r][c] = new Color(srcColor.getRed(), srcColor.getGreen(), srcColor.getBlue());
        }
      }
    } else {
      for (int r = 0; r < height; r++) {
        for (int c = 0; c < width; c++) {
          srcColor = image[height - 1 - r][c];
          output[r][c] = new Color(srcColor.getRed(), srcColor.getGreen(), srcColor.getBlue());
        }
      }
    }

    return output;
  }
}
