import model.feature.FeatureCommand;
import model.image.ImageModel;

import java.awt.Color;
import java.util.Objects;

public class MockImageFile implements ImageModel {
  StringBuilder log;

  public MockImageFile(StringBuilder log) {
    Objects.requireNonNull(log);
    this.log = log;
  }

  @Override
  public ImageModel copy() {
    log.append("Received Copy command\n");
    return new MockImageFile(this.log);
  }

  @Override
  public void applyFunctional(FeatureCommand command) {
    log.append(command.toString()).append("\n");
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
