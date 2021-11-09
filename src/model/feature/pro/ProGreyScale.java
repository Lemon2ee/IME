package model.feature.pro;

import model.enums.GreyScaleValue;
import model.feature.basics.GreyScale;

/**
 * The class represents GreyScale operations that are supported by purchasable Pro Edition of the
 * image processor. Including use alpha value and transform to Sepia tone.
 */
public class ProGreyScale extends GreyScale {
  /**
   * Create a GreyScale function object includes advanced grey scale operations to be applied on
   * the given image.
   *
   * @param value the grey scale value to be applied to the image as GreyScaleVAlue
   */
  public ProGreyScale(GreyScaleValue value) {
    super(value);
    super.greyScaleValueFunctionMap.put(GreyScaleValue.Alpha, this.util::toAlpha);
    super.greyScaleValueFunctionMap.put(GreyScaleValue.Sepia, this.util::toSepia);
  }
}
