package model.feature.basics;

import model.enums.GreyScaleValue;
import model.feature.FeatureCommand;
import utils.ImageUtil;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class GreyScale implements FeatureCommand {
  protected final GreyScaleValue value;
  protected final Map<GreyScaleValue, Function<Color, Color>> greyScaleValueFunctionMap;
  protected final ImageUtil util;

  public GreyScale(GreyScaleValue value) {
    this.value = Objects.requireNonNull(value);
    this.util = new ImageUtil();
    this.greyScaleValueFunctionMap = new HashMap<>();
  }

  @Override
  public Color[][] apply(Color[][] image) {
    greyScaleValueFunctionMap.put(GreyScaleValue.R, this.util::toRed);
    greyScaleValueFunctionMap.put(GreyScaleValue.G, this.util::toGreen);
    greyScaleValueFunctionMap.put(GreyScaleValue.B, this.util::toBlue);
    greyScaleValueFunctionMap.put(GreyScaleValue.Intensity, this.util::toIntensity);
    greyScaleValueFunctionMap.put(GreyScaleValue.Luma, this.util::toLuma);
    greyScaleValueFunctionMap.put(GreyScaleValue.Value, this.util::toValue);

    Function<Color, Color> colorFunction;

    colorFunction = this.greyScaleValueFunctionMap.getOrDefault(this.value, null);

    if (colorFunction == null) {
      throw new IllegalArgumentException("Haven't support this grey scale value operation\n");
    }

    int height = image.length;
    int width = image[0].length;

    Color[][] output = new Color[height][width];

    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        output[r][c] = colorFunction.apply(image[r][c]);
      }
    }

    return output;
  }

  @Override
  public String toString() {
    return "GreyScale{" +
        "value=" + value + "}";
  }
}
