import model.enums.FlipDirection;
import model.enums.GreyScaleValue;
import model.image.ImageModel;
import model.image.ReadOnlyImageModel;

import java.awt.*;
import java.util.Objects;

public class MockImageFile implements ImageModel {
  StringBuilder log;

  public MockImageFile(StringBuilder log) {
    Objects.requireNonNull(log);
    this.log = log;
  }

  @Override
  public ImageModel greyScale(GreyScaleValue op) {
    log.append("Received grey scale value = ").append(op).append("\n");
    return new MockImageFile(this.log);
  }

  @Override
  public ImageModel changeBrightness(int value) {
    log.append("Received brightness change value = ").append(value).append("\n");
    return new MockImageFile(this.log);
  }

  @Override
  public ImageModel flip(FlipDirection fd) {
    log.append("Received FlipDirection = ").append(fd).append("\n");
    return new MockImageFile(this.log);
  }

  @Override
  public ImageModel copy() {
    log.append("Received Copy command\n");
    return new MockImageFile(this.log);
  }

  @Override
  public ReadOnlyImageModel copyReadOnly() {
    log.append("Received Copy Read Only command\n");
    return new MockImageFile(this.log);
  }

  @Override
  public int getHeight() {
    log.append("Received Get height command\n");

    return 0;
  }

  @Override
  public int getWidth() {
    log.append("Received Get Width command\n");

    return 0;
  }

  @Override
  public Color[][] imageArrayCopy() {
    log.append("Received copy image array command\n");
    return new Color[0][];
  }
}
