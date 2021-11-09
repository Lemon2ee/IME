import model.feature.FeatureCommand;
import model.image.ImageModel;

import java.awt.Color;
import java.util.Objects;

/** A mock class for ImageFile used for testing. */
public class MockImageFile implements ImageModel {
  StringBuilder log;

  /**
   * Dummy method.
   *
   * @param log does not matter.
   */
  public MockImageFile(StringBuilder log) {
    Objects.requireNonNull(log);
    this.log = log;
  }

  /**
   * Dummy method.
   *
   * @return Dummy method
   */
  @Override
  public ImageModel copy() {
    log.append("Received Copy command\n");
    return new MockImageFile(this.log);
  }

  /**
   * Dummy method.
   *
   * @param command the command function to be applied as FeatureCommand
   */
  @Override
  public void applyFunctional(FeatureCommand command) {
    log.append(command.toString());
  }

  /**
   * Dummy method.
   *
   * @return Dummy method
   */
  @Override
  public int getHeight() {
    log.append("Received Get height command\n");

    return 0;
  }

  /**
   * Dummy method.
   *
   * @return Dummy method
   */
  @Override
  public int getWidth() {
    log.append("Received Get Width command\n");

    return 0;
  }

  /**
   * Dummy method.
   *
   * @return Dummy method
   */
  @Override
  public Color[][] imageArrayCopy() {
    log.append("Received copy image array command\n");
    return new Color[0][];
  }
}
