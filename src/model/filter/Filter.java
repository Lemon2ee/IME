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
    int leftKernelInd;
    int rightKernelInd;
    int topKernelInd;
    int bottomKernelInd;

    Color[][] src = origin.imageArrayCopy();
    int srcHeight = origin.getHeight();
    int srcWidth = origin.getWidth();

    Color[][] outputArray = new Color[srcHeight][srcWidth];

    for (int r = 0; r < srcHeight; r++) {
      for (int c = 0; c < srcWidth; c++) {
        leftKernelInd = -Math.max(c - this.halfW, 0);
        rightKernelInd = 2 * halfW - Math.max(c + this.halfW - srcWidth, 0);
        topKernelInd = -Math.max(r - this.halfW, 0);
        bottomKernelInd = 2 * halfW - Math.max(r + this.halfW - srcHeight, 0);
      }
    }

    return new ImageFile(outputArray);
  }
}
