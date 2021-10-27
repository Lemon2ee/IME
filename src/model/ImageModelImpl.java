package model;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class ImageModelImpl implements ImageModel {
  private Map<String, Color[][]> operationQueue;

  public ImageModelImpl() {
    operationQueue = new HashMap<String, Color[][]>();
  }

  @Override
  public void load(String name, String filePath) throws IllegalArgumentException {
    Color[][] image = new ImageUtil().readPPM(filePath);
    this.operationQueue.put(name, image);
  }

  @Override
  public void greyScale(String origin, String destination, GreyScaleValue op)
          throws IllegalArgumentException {
    Color[][] src = getSourceImage(origin);
    ...
  }

  @Override
  public void changeBrightness(String origin, String destination, int value)
          throws IllegalArgumentException {
    Color[][] src = getSourceImage(origin);
    ...
  }

  @Override
  public void flip(String origin, String destination, FlipDirection fd)
          throws IllegalArgumentException {
    Color[][] src = getSourceImage(origin);
    ...
  }

  @Override
  public void save(String filePath, String origin) {
    Color[][] src = getSourceImage(origin);
    ...
  }

  /**
   * Get the source image from the queue with given name.
   *
   * @param origin the name of the source image as a String
   * @return the 2d Color array of the source image
   * @throws IllegalArgumentException if the provided name cannot be found
   */
  private Color[][] getSourceImage(String origin) throws IllegalArgumentException {
    Color[][] result = this.operationQueue.get(origin);
    if (result == null) {
      throw new IllegalArgumentException("The provided source image is invalid.");
    }

    return result;
  }
}
