package model.feature.pro;

import model.enums.GreyScaleValue;
import model.feature.basics.GreyScale;

public class ProGreyScale extends GreyScale {
  public ProGreyScale(GreyScaleValue value) {
    super(value);
    super.greyScaleValueFunctionMap.put(GreyScaleValue.Alpha, this.util::toAlpha);
    super.greyScaleValueFunctionMap.put(GreyScaleValue.Sepia, this.util::toSepia);
  }
}
