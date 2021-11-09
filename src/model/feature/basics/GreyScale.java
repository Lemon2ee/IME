package model.feature.basics;

import model.enums.GreyScaleValue;
import model.feature.FeatureCommand;
import utils.ImageUtil;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * The class represents a feature to perform grey scale operation on the image. Including standard
 * greyscale operations that are available for the users.
 */
public class GreyScale implements FeatureCommand {
  protected final GreyScaleValue value;
  protected final Map<GreyScaleValue, Function<Color, Color>> greyScaleValueFunctionMap;
  protected final ImageUtil util;

  /**
   * Create a GreyScale function object to be applied on the given image.
   *
   * @param value the target value for the grey scale operation as GreyScaleValue
   */
  public GreyScale(GreyScaleValue value) {
    this.value = Objects.requireNonNull(value);
    this.util = new ImageUtil();
    this.greyScaleValueFunctionMap = new HashMap<>();
  }

  /**
   * Convert the given color 2d array based on the greyscale component.
   *
   * @param image the image data to be processed as a 2d array of Color
   * @return An after-modification 2d array which represents an image
   */
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
    return "GreyScale{" + "value=" + value + "}\n";
  }
}
