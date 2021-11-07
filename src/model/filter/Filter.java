package model.filter;

import java.awt.Color;

import model.image.ImageFile;
import model.image.ImageModel;
import utils.ImageUtil;

/**
 * The class to represent a common image filter. It takes in a 2d array as filter kernel and apply
 * the filter operation to the given image.
 */
public class Filter implements IFilter {
  private final double[][] kernel;
  private final ImageUtil utils;
  private final int halfH;
  private final int halfW;

  /**
   * Create a filter kernel with given matrix of filter operation.
   *
   * @param kernel the kernel of the filter as a 2d array of double
   * @throws IllegalArgumentException if the provided filter kernel is null or not having an odd
   *                                  dimension.
   */
  public Filter(double[][] kernel) throws IllegalArgumentException {
    if (kernel == null) {
      throw new IllegalArgumentException("Provided filter kernel cannot be null.");
    }
    int height = kernel.length;
    int width = kernel[0].length;
    if (height % 2 == 0 || width % 2 == 0) {
      throw new IllegalArgumentException("Provided filter kernel dimension cannot be even.");
    }
    this.halfH = height / 2;
    this.halfW = width / 2;
    this.kernel = kernel;
    this.utils = new ImageUtil();
  }

  @Override
  public ImageModel execute(ImageModel origin) {
    Color[][] src = origin.imageArrayCopy();
    int srcHeight = origin.getHeight();
    int srcWidth = origin.getWidth();

    Color[][] outputArray = new Color[srcHeight][srcWidth];

    for (int r = 0; r < srcHeight; r++) {
      for (int c = 0; c < srcWidth; c++) {
        double newR = 0;
        double newG = 0;
        double newB = 0;

        for (int kr = 0; kr <= 2 * halfH; kr++) {
          for (int kc = 0; kc <= 2 * halfW; kc++) {
            try {
              Color srcColor = src[r - halfH + kr][c - halfW + kc];
              double weight = this.kernel[kr][kc];

              newR += srcColor.getRed() * weight;
              newG += srcColor.getGreen() * weight;
              newB += srcColor.getBlue() * weight;
            } catch (IndexOutOfBoundsException ibe) {
              // Do nothing
            }
          }
        }
        outputArray[r][c] = new Color(utils.clampRange((int) Math.round(newR)),
                utils.clampRange((int) Math.round(newG)), utils.clampRange((int) Math.round(newB)));
      }
    }

    return new ImageFile(outputArray);
  }
}
