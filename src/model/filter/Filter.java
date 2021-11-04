package model.filter;

import java.awt.*;

import model.image.ImageFile;
import model.image.ImageModel;

/**
 * The class to represent a common image filter. It takes in a 2d array as filter kernel and
 * apply the filter operation to the given image.
 */
public class Filter implements IFilter {
  private final double[][] kernel;
  private final int halfW;

  /**
   * Create a filter kernel with given matrix of filter operation.
   *
   * @param kernel the kernel of the filter as a 2d array of double
   * @throws IllegalArgumentException if the provided filter kernel is null or not having a
   *                                  positive odd dimension.
   */
  public Filter(double[][] kernel) throws IllegalArgumentException {
    if (kernel == null) {
      throw new IllegalArgumentException("Provided filter kernel cannot be null.");
    }
    int width = kernel.length;
    if (width % 2 == 0) {
      throw new IllegalArgumentException("Provided filter kernel dimension cannot be even.");
    }
    this.halfW = width / 2;
    this.kernel = kernel;
  }

  @Override
  public ImageModel execute(ImageModel origin) {
    Color[][] src = origin.imageArrayCopy();
    int srcHeight = origin.getHeight();
    int srcWidth = origin.getWidth();

    Color[][] outputArray = new Color[srcHeight][srcWidth];

    for (int r = 0; r < srcHeight; r++) {
      for (int c = 0; c < srcWidth; c++) {

        Color[][] srcKernel = new Color[2 * halfW + 1][2 * halfW + 1];
        for (int kr = 0; kr <= 2 * halfW; kr++) {
          for (int kc = 0; kc <= 2 * halfW; kc++) {
            try {
              srcKernel[kr][kc] = src[r - halfW + kr][c - halfW + kc];
            } catch (IndexOutOfBoundsException ibe) {
              srcKernel[kr][kc] = Color.BLACK;
            }
            outputArray[r][c] = applyFilter(srcKernel);
          }
        }
      }
    }

    return new ImageFile(outputArray);
  }

  /**
   * Apply the provided filter to the original image data within the filter kernel.
   *
   * @param origin the original image data within the filter kernel as 2d array of Color
   * @return the value of the kernel center after filtering as Color
   */
  private Color applyFilter(Color[][] origin) {
    double newR = 0;
    double newG = 0;
    double newB = 0;
    for (int r = 0; r <= 2 * halfW; r++) {
      for (int c = 0; c <= 2 * halfW; c++) {
        Color src = origin[r][c];
        double weight = this.kernel[r][c];
        newR += src.getRed() * weight;
        newG += src.getGreen() * weight;
        newB += src.getBlue() * weight;
      }
    }

    return new Color((int) Math.round(newR), (int) Math.round(newG), (int) Math.round(newB));
  }
}
