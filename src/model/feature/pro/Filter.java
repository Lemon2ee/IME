package model.feature.pro;

import model.enums.FilterType;
import model.feature.BasicFeature;
import model.filter.IFilterImpl;
import model.filter.IFilter;

import java.awt.*;

public class Filter implements BasicFeature {
  private final FilterType type;

  public Filter(FilterType type) {
    this.type = type;
  }

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
}
