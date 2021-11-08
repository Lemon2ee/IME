package model.feature;

import model.enums.FilterType;
import model.enums.FlipDirection;
import model.enums.GreyScaleValue;

import java.awt.*;

public interface BasicFeature {
  Color[][] apply(Color[][] image);
}
