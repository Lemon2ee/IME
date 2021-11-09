package model.feature.basics;

import model.enums.FlipDirection;
import model.feature.FeatureCommand;

import java.awt.*;
import java.util.Objects;

/**
 * The class represents a flip operation that can be performed on a given image. The flip can be
 * either horizontal or vertical.
 */
public class Flip implements FeatureCommand {
  private final FlipDirection direction;

  /**
   * To create a function object of flipping an image by the target direction.
   *
   * @param direction the direction to be flipped as FlipDirection
   */
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
          output[r][c] =
              new Color(
                  srcColor.getRed(), srcColor.getGreen(), srcColor.getBlue(), srcColor.getAlpha());
        }
      }
    } else {
      for (int r = 0; r < height; r++) {
        for (int c = 0; c < width; c++) {
          srcColor = image[height - 1 - r][c];
          output[r][c] =
              new Color(
                  srcColor.getRed(), srcColor.getGreen(), srcColor.getBlue(), srcColor.getAlpha());
        }
      }
    }

    return output;
  }

  @Override
  public String toString() {
    return "Flip{" +
        "direction=" + direction +
        '}';
  }
}
