package model.filter;

import java.awt.Color;

/**
 * The interface represents a filer. The filter has only one method to execute on the given 2d array
 * of Color.
 */
public interface IFilter {
  /**
   * Execute the filter kernel operation on the given image data.
   *
   * @param origin the original image to filter on as ImageModel
   * @return new image after filtering as ImageModel
   */
  Color[][] execute(Color[][] origin);
}
