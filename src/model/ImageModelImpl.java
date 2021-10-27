package model;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class ImageModelImpl implements ImageModel {
  private Map<String, Color[][]> operationQueue;

  public ImageModelImpl() {
    operationQueue = new HashMap<String, Color[][]>();
  }

  @Override
  public void load(String name, String filePath) throws IllegalArgumentException {
    Color[][] image = new ImageUtil().readPPM(filePath);
    this.operationQueue.put(name, image);
  }

  @Override
  public void greyScale(String origin, String destination, GreyScaleValue op)
          throws IllegalArgumentException {
    Color[][] src = getSourceImage(origin);
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

    this.operationQueue.put(destination, output);
  }

  @Override
  public void changeBrightness(String origin, String destination, int value)
          throws IllegalArgumentException {
    Color[][] src = getSourceImage(origin);
    int height = src.length;
    int width = src[0].length;

    Color[][] output = new Color[height][width];
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        output[r][c] = colorBrightness(src[r][c], value);
      }
    }

    this.operationQueue.put(destination, output);
  }

  @Override
  public void flip(String origin, String destination, FlipDirection fd)
          throws IllegalArgumentException {
    Color[][] src = getSourceImage(origin);
  }

  @Override
  public void save(String filePath, String origin) {
    Color[][] src = getSourceImage(origin);
  }

  /**
   * Get the source image from the queue with given name.
   *
   * @param origin the name of the source image as a String
   * @return the 2d Color array of the source image
   * @throws IllegalArgumentException if the provided name cannot be found
   */
  private Color[][] getSourceImage(String origin) throws IllegalArgumentException {
    Color[][] result = this.operationQueue.get(origin);
    if (result == null) {
      throw new IllegalArgumentException("The provided source image is invalid.");
    }

    return result;
  }

  private Color toRed(Color origin) {
    return new Color(origin.getRed(), origin.getRed(), origin.getRed());
  }

  private Color toGreen(Color origin) {
    return new Color(origin.getGreen(), origin.getGreen(), origin.getGreen());
  }

  private Color toBlue(Color origin) {
    return new Color(origin.getBlue(), origin.getBlue(), origin.getBlue());
  }

  private Color toIntensity(Color origin) {
    int avg = (origin.getRed() + origin.getGreen() + origin.getBlue()) / 3;
    return new Color(avg, avg, avg);
  }

  private Color toLuma(Color origin) {
    Double luma = 0.2126 * origin.getRed() + 0.7152 * origin.getGreen() + 0.0722 * origin.getBlue();
    int intLuma = luma.intValue();
    return new Color(intLuma, intLuma, intLuma);
  }

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
}
