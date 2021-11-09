package model.image;

import model.enums.GreyScaleValue;
import model.feature.FeatureCommand;
import utils.ImageUtil;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * The class represents the model of an image which is being represented with a color 2d array.
 * Including load image into the queue, perform grey scale operation, change the image brightness,
 * flip target image and save the image to given file path.
 */
public class ImageFile implements ImageModel {
  protected final ImageUtil util;
  protected final Map<GreyScaleValue, Function<Color, Color>> greyScaleValueFunctionMap;
  private final int height;
  private final int width;
  private Color[][] image;

  /**
   * The default constructor of an ImageModel.
   *
   * @param image a 2d array which represents a PPM image.
   */
  public ImageFile(Color[][] image) {
    if (image == null) {
      throw new IllegalArgumentException("Require non null arguments\n");
    }

    for (Color[] row : image) {
      if (row == null) {
        throw new IllegalArgumentException("Does not accept array with null value in it\n");
      }

      if (Arrays.asList(row).contains(null)) {
        throw new IllegalArgumentException("Does not accept array with null value in it\n");
      }
    }

    this.image = image;
    this.height = this.image.length;
    this.width = this.image[0].length;
    this.util = new ImageUtil();
    this.greyScaleValueFunctionMap = new HashMap<>();
  }

  /**
   * Deep copy the PPMImage.
   *
   * @return A deep copy of this class
   */
  @Override
  public ImageModel copy() {
    return new ImageFile(this.image);
  }

  /**
   * Apply given feature command to the image array.
   *
   * @param command the command function to be applied as FeatureCommand
   */
  @Override
  public void applyFunctional(FeatureCommand command) {
    this.image = command.apply(this.image);
  }

  /**
   * Get the height of image.
   *
   * @return an integer representing the height of the image
   */
  @Override
  public int getHeight() {
    return this.height;
  }

  /**
   * Get the width of image.
   *
   * @return an integer representing the height of the image
   */
  @Override
  public int getWidth() {
    return this.width;
  }

  /**
   * Return a deep copy of the current image 2d array.
   *
   * @return
   */
  @Override
  public Color[][] imageArrayCopy() {
    Color[][] output = new Color[this.height][this.width];

    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        Color newColor = new Color(this.image[row][col].getRGB(), true);

        output[row][col] = newColor;
      }
    }
    return output;
  }
}
