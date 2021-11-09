package model.feature.pro;

import model.enums.FilterType;
import model.feature.FeatureCommand;
import model.filter.IFilter;
import model.filter.IFilterImpl;

import java.awt.Color;

/**
 * The class represents a feature to perform filter operation on the image. Can be either blur or
 * sharpen the given image.
 */
public class Filter implements FeatureCommand {
  private final FilterType type;

  /**
   * Create a function object to filter the image by given filter type.
   *
   * @param type the type of filter operation to be performed as FilterType
   */
  public Filter(FilterType type) {
    this.type = type;
  }

  /**
   * Apply filters to the given 2d color array which represents an image
   *
   * @param image the image data to be processed as a 2d array of Color
   * @return An after-modification 2d array which represents an image
   */
  @Override
  public Color[][] apply(Color[][] image) {
    IFilter filter;
    switch (this.type) {
      case Blur:
        filter =
            new IFilterImpl(
                new double[][] {
                  {1.0 / 16, 1.0 / 8, 1.0 / 16},
                  {1.0 / 8, 1.0 / 4, 1.0 / 8},
                  {1.0 / 16, 1.0 / 8, 1.0 / 16}
                });
        break;
      case Sharpen:
        filter =
            new IFilterImpl(
                new double[][] {
                  {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
                  {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
                  {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
                  {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
                  {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
                });
        break;
      default:
        return image;
    }
    return filter.execute(image);
  }

  @Override
  public String toString() {
    return "Filter{" + "type=" + type + '}';
  }
}
