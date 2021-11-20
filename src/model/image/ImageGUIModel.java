package model.image;

public interface ImageGUIModel extends ImageModel {
  /**
   * Generate the histogram statistic of the image data.
   * 0 - R
   * 1 - G
   * 2 - B
   * 3 - Intensity
   *
   * @return a 2d int array with length 256 integrate all histogram data
   */
  int[][] histogram();
}
