package model.image;

import java.awt.Color;

public class ImageGUIFile extends ImageFile implements ImageGUIModel {
  /**
   * The default constructor of an ImageGUIFile.
   *
   * @param image represents given image data as a 2d array of Color
   * @throws IllegalArgumentException if the given image data contains null
   */
  public ImageGUIFile(Color[][] image) throws IllegalArgumentException {
    super(image);
  }

  @Override
  public ImageGUIFile copy() {
    return new ImageGUIFile(this.imageArrayCopy());
  }

  @Override
  public int[][] histogram() {
    int[][] output = new int[4][256];
    for (int r = 0; r < this.height; r++) {
      for (int c = 0; c < this.width; c++) {
        Color origin = this.image[r][c];
        int R = origin.getRed();
        int G = origin.getGreen();
        int B = origin.getBlue();

        output[0][R] += 1;
        output[1][G] += 1;
        output[2][B] += 1;

        int intensity = (int) Math.round(((double) R + (double) G + (double) B) / 3);
        output[3][intensity] += 1;
      }
    }

    return output;
  }
}
